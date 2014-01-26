package com.bypay.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.struts2.ServletActionContext;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.bypay.domain.OrderInfo;
import com.bypay.upop.QuickPayConf;
import com.bypay.upop.QuickPayUtils;
import com.bypay.util.SendMailUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.annotations.BeforeResult;

/**
 * 
 * @author e430c
 *
 */
public class ReqAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private static HttpClient httpClient;
	
	private OrderInfo orderInfo;
	
	/**
	 * entry,parameter handling
	 * @return
	 * @throws Exception
	 */
	public String toTrans() throws Exception {
		String merOrderId=orderInfo.getMerOrderId().trim();
		Double dd=Double.parseDouble(orderInfo.getMerAmt())*100;
		String merAmt=dd.intValue()+"";//转换成分
		
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		if(month>4 && month<11){
			cal.add(Calendar.HOUR, 7);//英国在每年的三月底到十月底实行夏时制，那时时差为7小时。
		}else{
			cal.add(Calendar.HOUR, 8);//其他为8
		}
		Date date = cal.getTime();
		String transTime=new SimpleDateFormat("yyyyMMddHHmmss").format(date);
		String payerNum=orderInfo.getPayerNum().trim();//用户帐号
	    String payerName=orderInfo.getPayerName().trim();//付款人姓名
	    
		StringBuffer merReservedValue=new StringBuffer();
		String split="&";
		if(null!=payerNum&&payerNum.trim().length()>0)
			merReservedValue.append("payerNum="+new BASE64Encoder().encode(payerNum.getBytes("UTF-8")));//用户帐号
		if(null!=payerName&&payerName.trim().length()>0)
			merReservedValue.append(split+"payerName="+new BASE64Encoder().encode(payerName.getBytes("UTF-8")));//付款人姓名
		trans(merOrderId,transTime,merAmt,merReservedValue.toString(),payerNum);
		return null;
	}
	
	/**
	 * assembly parameters,then jump to page UnionPay payment
	 * @param merOrderId order ID
	 * @param transTime  
	 * @param merAmt      transaction amount
	 * @param merReserved the message needs to be sent to the e-mail
	 * @param payerNum    user Account
	 */
	public void trans(String merOrderId,String transTime,String merAmt,String merReserved,String payerNum){
		String backUrl="http://planet.farnell.com/unionpay/req/backNotifi.html?"+merReserved;
		try {
			payerNum=URLEncoder.encode(payerNum,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		String frontUrl="http://planet.farnell.com/unionpay/req/fontNotifi.html?payerNum="+payerNum;
		
		//商户需要组装如下对象的数据
		String[] valueVo = new String[]{
				QuickPayConf.version,//协议版本
				QuickPayConf.charset,//字符编码
	            "01",//交易类型
	            "",//原始交易流水号
	            QuickPayConf.merCode,//商户代码
	            QuickPayConf.merName,//商户简称
	            "",//收单机构代码（仅收单机构接入需要填写）
	            "",//商户类别（收单机构接入需要填写）
	            "",//商品URL
	            "",//商品名称
	            "",//商品单价 单位：分
	            "",//商品数量
	            "0",//折扣 单位：分
	            "",//运费 单位：分
	            //new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"gw",//订单号（需要商户自己生成）
	            merOrderId,
	            //"3100",//交易金额 单位：分
	            merAmt,
	            "156",//交易币种
	            //new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()),//交易时间
	            transTime,
	            "127.0.0.1",//用户IP
	            "",//用户真实姓名
	            "",//默认支付方式
	            "",//默认银行编号
	            "300000",//交易超时时间
//	            QuickPayConf.merFrontEndUrl,// 前台回调商户URL
//				QuickPayConf.merBackEndUrl,// 后台回调商户URL
	            frontUrl,//front notification address
	            backUrl,//background notification address
	            ""//merReserved//商户保留域
		};
				
				String signType = request.getParameter("sign");
				if (!QuickPayConf.signType_SHA1withRSA.equalsIgnoreCase(signType)) {
					signType = QuickPayConf.signType;
				}
				String html = new QuickPayUtils().createPayHtml(valueVo, signType);//跳转到银联页面支付
				response.setContentType("text/html;charset="+QuickPayConf.charset);   
				response.setCharacterEncoding(QuickPayConf.charset);
				try {
					response.getWriter().write(html);
				} catch (IOException e) {
					e.getStackTrace();
				}
	}
	
	/**
	 * Front notification address
	 * After a successful return to the payment page
	 * @return
	 */
	public String fontNotifi(){
		System.out.println("---------前台通知 start------------");
		try {
			request.setCharacterEncoding(QuickPayConf.charset);
		} catch (UnsupportedEncodingException e) {
		}
		
		String[] resArr = new String[QuickPayConf.notifyVo.length]; 
		for(int i=0;i<QuickPayConf.notifyVo.length;i++){
			resArr[i] = request.getParameter(QuickPayConf.notifyVo[i]);
		}
		
		String signature = request.getParameter(QuickPayConf.signature);
		String signMethod = request.getParameter(QuickPayConf.signMethod);
		
		Boolean signatureCheck = new QuickPayUtils().checkSign(resArr,signMethod,signature);
		
		String payerNum="";
		try {
			payerNum = new String(request.getParameter("payerNum").getBytes("iso8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(!signatureCheck){
			System.out.println("method fontNotifi 签名不对。");
		}else{
			if("00".equals(resArr[10])){
				System.out.println("---------前台通知 end------------");
				request.setAttribute("payerNum", payerNum);
				request.setAttribute("sucOrErr", "success");
				request.setAttribute("merName", resArr[4]);//商户名称
				request.setAttribute("qId", resArr[9]);//流水
				request.setAttribute("orderAmount",Integer.parseInt(resArr[6]));//金额
			}else{
				request.setAttribute("payerNum", payerNum);
				request.setAttribute("sucOrErr", "error");
				request.setAttribute("merName", resArr[4]);//商户名称
				request.setAttribute("qId", resArr[9]);//流水
				request.setAttribute("orderAmount", Integer.parseInt(resArr[6]));//金额
				
				System.out.println("method fontNotifi 交易失败原因:"+resArr[11]);
			}
			return "result";
		}
		return null;
	}
	
	//Notice in the background
	public void backNotifi(){
		System.out.println("---------后台通知 start------------");

		try {
			request.setCharacterEncoding(QuickPayConf.charset);
		} catch (UnsupportedEncodingException e) {
		}
		
		String[] resArr = new String[QuickPayConf.notifyVo.length]; 
		for(int i=0;i<QuickPayConf.notifyVo.length;i++){
			resArr[i] = request.getParameter(QuickPayConf.notifyVo[i]);
		}
		String signature = request.getParameter(QuickPayConf.signature);
		String signMethod = request.getParameter(QuickPayConf.signMethod);
		
		Boolean signatureCheck = new QuickPayUtils().checkSign(resArr,signMethod,signature);

		//获取参数
		String queryString=request.getQueryString();
		Map<String,String> m=new HashMap<String, String>();
		String[] urls=queryString.split("&");
		for(String s:urls){
			try {
				String key=s.substring(0,s.indexOf("="));
				String value="";
				if(key.equals("payerNum") || key.equals("payerName") || key.equals("payerCompany")){
					value=new String(new BASE64Decoder().decodeBuffer(s.substring(s.indexOf("=")+1,s.length())),"UTF-8");
				}else{
					value=s.substring(s.indexOf("=")+1,s.length());
				}
				m.put(key, value);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		m.put("merOrderId", resArr[8]);
		m.put("merAmt", resArr[6]);
		
		
		if(!signatureCheck){
			System.out.println("method backNotifi--签名不对。");
		}else{
			if("00".equals(resArr[10])){
				queryPay("01", request.getParameter(QuickPayConf.notifyVo[8]), request.getParameter(QuickPayConf.notifyVo[12]),m);
			}else{
				System.out.println("method backNotifi--交易失败原因:"+resArr[11]);
			}
		}
			System.out.println("---------后台通知 end------------");
	}
	
	/**
	 * 
	 * @param payType
	 * @param orderId
	 * @param payTime
	 * @param reserved
	 */
	public void queryPay(String payType,String orderId,String payTime,Map<String,String> reserved){
		sendEmail(reserved);
	}
	
	/**
	 * Send mail to eluoment
	 * @param reserved
	 */
	public void sendEmail(Map<String,String> reserved){
			StringBuffer br=new StringBuffer();
			br.append("订单已经支付成功<br>订单号为："+reserved.get("merOrderId"));
			String merAmt=reserved.get("merAmt");
			Float f_merAmt=Float.parseFloat(merAmt)/100;
			
			DecimalFormat df=new DecimalFormat("##0.00");
			merAmt=df.format(f_merAmt);
			
			br.append("<br>金额为："+merAmt+"(元)");
			br.append("<br>用户帐号："+reserved.get("payerNum"));
			br.append("<br>付款人姓名："+reserved.get("payerName"));
			try{
			String head="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
					+"<html xmlns=\"http://www.w3.org/1999/xhtml\">"
					+"<head>"
					+"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />";
			String end="</head>"
					   +"</html>";
			String content=br.toString();
			content =head+content+end;
			String subject="order notice "+reserved.get("merOrderId");
			String time=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+(new Random()).nextInt(1000);
			String fileName="/home/unionpay/emails/"+time+".html";
			String cmd3=null;
				cmd3="cat "+fileName+" | formail -I \"From: E14.Upay@gmail.com\" -I \"MIME-Version:1.0\" -I \"Content-type:text/html;charset=utf-8\" -I \"Subject: "+subject+"\" | /usr/sbin/sendmail -oi GCunionpay@element14.com";
		    	File file = new File(fileName);      
		        if (!file.exists())   
		        {       
		            file.createNewFile();      
		        }      
		        OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file),"utf-8");      
		        BufferedWriter bw=new BufferedWriter(write);          
		        bw.write(content);   
		        bw.close();   
		        SendMailUtil.runCommand(cmd3,2);
		        if(file.isFile() && file.exists()){     
		            file.delete();     
		        }
			}catch (Exception e){
				e.printStackTrace();
			}
	}
			
	@BeforeResult
	public void beforeResult() {
		
	}

	/**
	 * Constructor method
	 */
	public ReqAction(){
		httpClient = new DefaultHttpClient();
		response =ServletActionContext.getResponse();
		request =ServletActionContext.getRequest();
		httpClient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 90000);
		httpClient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 120000);
		httpClient.getParams().setIntParameter(HttpConnectionParams.SOCKET_BUFFER_SIZE, 1024*4);
	}

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
		
		
	}
	
}
