package com.bypay.upop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;

/**
 * Name: Payment Tools 
 * Function: tools, you can generate a form of payment, etc. 
 * Class attribute: public class 
 * Version: 1.0 
 * Date :2011-03-11 
 * Author: China UnionPay UPOP team 
 * Copyright: China UnionPay 
 * Note: The following code is just sample code to test for the convenience provided by merchants, businesses can require their own websites, according to technical writing, 
 * not necessarily to use the code. The code is for reference only. 
 * */
public class QuickPayUtils {
	
	/**
	 * Generate packets sent China UnionPay page
	 * @param map
	 * @param signature
	 * @return
	 */
	public String createPayHtml(String[] valueVo, String signType) 
	{
		return createPayHtml(valueVo, null, signType);
	}
	
	/**
	 * Go directly to the payment page of Bank of China
	 * @param map
	 * @param signature
	 * @return
	 */
	public String createPayHtml(String[] valueVo, String bank, String signType) 
	{
		
		Map<String, String> map = new TreeMap<String, String>();
		for(int i=0;i<QuickPayConf.reqVo.length;i++){
			map.put(QuickPayConf.reqVo[i], valueVo[i]);
		}
		
        map.put("signature", signMap(map, signType));
        map.put("signMethod", signType);
        if (bank != null && !"".equals(bank)) {
            map.put("bank", bank);
        }
		
		String payForm = generateAutoSubmitForm(QuickPayConf.gateWay, map);
		
		return payForm;
	}
    
	public String createBackStr(String[] valueVo, String[] keyVo) 
	{
        
		Map<String, String> map = new TreeMap<String, String>();
		for(int i=0;i<keyVo.length;i++){
			map.put(keyVo[i], valueVo[i]);
		}
		map.put("signature", signMap(map,QuickPayConf.signType));
		map.put("signMethod", QuickPayConf.signType);
		return joinMapValue(map, '&');
	}
	
	/**
	 * 查询验证签名
	 * @param valueVo
	 * @return 0:验证失败 1验证成功 2没有签名信息（报文格式不对）
	 */
	public int checkSecurity(String[] valueVo) {
		Map<String, String> map = new TreeMap<String, String>();
		for (int i = 0; i < valueVo.length; i++) {
			String[] keyValue = valueVo[i].split("=");
			map.put(keyValue[0], keyValue.length >= 2 ? valueVo[i].substring(keyValue[0].length()+1) : "");
		}
		if ("".equals(map.get("signature"))) {
			return 2;
		}
		String signature = map.get("signature");
		boolean isValid = false;
		if (QuickPayConf.signType.equalsIgnoreCase(map.get("signMethod"))) {
			map.remove("signature");
			map.remove("signMethod");
			isValid = signature.equals(md5(joinMapValue(map, '&') + md5(QuickPayConf.securityKey)));
		} else {
			isValid = verifyWithRSA(map.get("signMethod"), md5(joinMapValue(map, '&') + md5(QuickPayConf.securityKey)), signature);
		}

		return (isValid ? 1 : 0);
	}
	
	
	/**
	 * 生成加密钥
	 * @param map
	 * @param secretKey 商城密钥
	 * @return
	 */
	private String signMap(Map<String, String> map,  String signMethod) {
        if (QuickPayConf.signType.equalsIgnoreCase(signMethod)) {
            return md5(joinMapValue(map, '&') + md5(QuickPayConf.securityKey));
        } else {
            return signWithRSA(md5(joinMapValue(map, '&') + md5(QuickPayConf.securityKey)));
        }
	}
	
	private String signWithRSA(String signData) {
       return "";
	}
	
    private boolean verifyWithRSA(String algorithm, String signData, String signature) {
       return false;
    }
	
	/**
	 * Verify signatures
	 * @param map
	 * @param secretKey    Key Mall
	 * @return
	 */
	public  boolean checkSign(String[] valueVo, String signMethod, String signature) {
		
		Map<String, String> map = new TreeMap<String, String>();
		for(int i=0;i<QuickPayConf.notifyVo.length;i++){
			map.put(QuickPayConf.notifyVo[i], valueVo[i]);
		}
		
		if(signature == null) return false;
		if(QuickPayConf.signType.equalsIgnoreCase(signMethod)){
			System.out.println(">>>"+joinMapValue(map, '&') + md5(QuickPayConf.securityKey));
			System.out.println(">>>"+signature.equals(md5(joinMapValue(map, '&') + md5(QuickPayConf.securityKey))));
			return signature.equals(md5(joinMapValue(map, '&') + md5(QuickPayConf.securityKey)));
		} else {
			return verifyWithRSA(signMethod, md5(joinMapValue(map, '&') + md5(QuickPayConf.securityKey)), signature);
		}
		
	}
	
	
	public static String[] getResArr(String str) {
		String regex = "(.*?cupReserved\\=)(\\{[^}]+\\})(.*)";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);

		String reserved = "";
		if (matcher.find()) {
			reserved = matcher.group(2);
		}

		String result = str.replaceFirst(regex, "$1$3");
		String[] resArr = result.split("&");
		for (int i = 0; i < resArr.length; i++) {
			if("cupReserved=".equals(resArr[i])){
				resArr[i] +=reserved;
			}
		}
		return resArr;
	}

	private String joinMapValue(Map<String, String> map, char connector) {
		StringBuffer b = new StringBuffer();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			b.append(entry.getKey());
			b.append('=');
			if (entry.getValue() != null) {
				b.append(entry.getValue());
			}
			b.append(connector);
		}
		return b.toString();
	}
	
	/**
	 * get the md5 hash of a string
	 * 
	 * @param str
	 * @return
	 */
	private String md5(String str) {

		if (str == null) {
			return null;
		}

		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance(QuickPayConf.signType);
			messageDigest.reset();
			messageDigest.update(str.getBytes(QuickPayConf.charset));
		} catch (NoSuchAlgorithmException e) {
			
			return str;
		} catch (UnsupportedEncodingException e) {
			return str;
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}
	
	
	// Clean up resources
	public void destroy() {
	}

	/**
	 * 查询方法
	 * @param strURL
	 * @param req
	 * @return
	 */
	/*
	public String doPostQueryCmd(String strURL, String[] valueVo, String[] keyVo) {
		

		PostMethod post = null;
		try {
			post = (PostMethod) new UTF8PostMethod(strURL);
			//URL uRL = new URL(strURL);
			System.out.println("URL:" + strURL);
			post.setContentChunked(true);
			//post.setPath(uRL.getPath());
			
			// Get HTTP client
			HttpClient httpclient = new HttpClient();
			
			NameValuePair[] params = new NameValuePair[keyVo.length];
			for (int i = 0; i < keyVo.length; i++) {
				params[i] = new NameValuePair(keyVo[i], valueVo[i]);
			}
			
			//httpclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,QuickPayConf.charset); 
			
			post.setRequestBody(params);
			
			// 设置超时时间
			httpclient.setTimeout(30000);
			//httpclient.getHostConfiguration().setHost(uRL.getHost(), uRL.getPort());

			int result = httpclient.executeMethod(post);

			post.getRequestCharSet();
			byte[] resultInputByte;
			if (result == 200) {
				resultInputByte = post.getResponseBody();
				return new String(resultInputByte,QuickPayConf.charset);
			} else {
				System.out.println("返回错误");
			}
		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			post.releaseConnection();
		}
		return null;
	}
*/

	/**
	 * 查询方法
	 * @param strURL
	 * @param req
	 * @return
	 */
	public String doPostQueryCmd(String strURL, String req) {
		String result = null;
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			URL url = new URL(strURL);
			URLConnection con = url.openConnection();
//			if (con instanceof HttpsURLConnection) {
//				((HttpsURLConnection) con).setHostnameVerifier(new HostnameVerifier() {
//					public boolean verify(String hostname, SSLSession session) {
//						return true;
//					}
//				});
//			}
			con.setUseCaches(false);
			con.setDoInput(true);
			con.setDoOutput(true);
			out = new BufferedOutputStream(con.getOutputStream());
			byte outBuf[] = req.getBytes(QuickPayConf.charset);
			out.write(outBuf);
			out.close();
			in = new BufferedInputStream(con.getInputStream());
			result = ReadByteStream(in);
		} catch (Exception ex) {
			System.out.print(ex);
			return "";
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		if (result == null)
			return "";
		else
			return result;
	}

	private static String ReadByteStream(BufferedInputStream in) throws IOException {
		LinkedList<Mybuf> bufList = new LinkedList<Mybuf>();
		int size = 0;
		byte buf[];
		do {
			buf = new byte[128];
			int num = in.read(buf);
			if (num == -1)
				break;
			size += num;
			bufList.add(new Mybuf(buf, num));
		} while (true);
		buf = new byte[size];
		int pos = 0;
		for (ListIterator<Mybuf> p = bufList.listIterator(); p.hasNext();) {
			Mybuf b = p.next();
			for (int i = 0; i < b.size;) {
				buf[pos] = b.buf[i];
				i++;
				pos++;
			}

		}

		return new String(buf,QuickPayConf.charset);
	}
	
    /**
     * Generate an form, auto submit data to the given <code>actionUrl</code>
     * 
     * @param actionUrl
     * @param paramMap
     * @return
     */
	private static String generateAutoSubmitForm(String actionUrl, Map<String, String> paramMap) {
        StringBuilder html = new StringBuilder();
        html.append("<script language=\"javascript\">window.onload=function(){document.pay_form.submit();}</script>\n");
        html.append("<form id=\"pay_form\" name=\"pay_form\" action=\"").append(actionUrl).append("\" method=\"post\">\n");
        
        for (String key : paramMap.keySet()) {
            	html.append("<input type=\"hidden\" name=\"" + key + "\" id=\"" + key + "\" value=\"" + paramMap.get(key) + "\">\n");
        }
        html.append("</form>\n");
        return html.toString();
    }
	
	public static void main( String[] aaa){
		String a="charset=UTF-8&cupReserved=&exchangeDate=&exchangeRate=&merAbbr=联动优势&merId=100000000000025&orderAmount=1&orderCurrency=156&orderNumber=9002111465&qid=201106030000005928402&respCode=00&respMsg=Success!&respTime=20110603214534&settleAmount=1&settleCurrency=156&settleDate=0419&traceNumber=592840&traceTime=0603000000&transType=01&version=1.0.0&8ddcff3a80f4189ca1c9d4d902c3c909";
		System.out.print(new QuickPayUtils().md5(a));
	}

}

class Mybuf
{

	public byte buf[];
	public int size;

	public Mybuf(byte b[], int s)
	{
		buf = b;
		size = s;
	}
}
