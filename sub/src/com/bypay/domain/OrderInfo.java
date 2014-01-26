package com.bypay.domain;

/**
 * @author
 */
public class OrderInfo  implements java.io.Serializable{
    private String merOrderId;		 //商户订单号	MER_ORDER_ID
    private String merAmt; 				//交易金额	MER_AMT
    private String payerNum	;//用户帐号
    private String payerCompany;//付款人公司
    private String payerName;//付款人姓名
    private String phone;//付款人电话
    private String payerForOther;//是否代付
    private String payExplanatin;//付款说明
	public String getMerOrderId() {
		return merOrderId;
	}
	public void setMerOrderId(String merOrderId) {
		this.merOrderId = merOrderId;
	}
	public String getMerAmt() {
		return merAmt;
	}
	public void setMerAmt(String merAmt) {
		this.merAmt = merAmt;
	}
	public String getPayerNum() {
		return payerNum;
	}
	public void setPayerNum(String payerNum) {
		this.payerNum = payerNum;
	}
	public String getPayerCompany() {
		return payerCompany;
	}
	public void setPayerCompany(String payerCompany) {
		this.payerCompany = payerCompany;
	}
	public String getPayerName() {
		return payerName;
	}
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPayerForOther() {
		return payerForOther;
	}
	public void setPayerForOther(String payerForOther) {
		this.payerForOther = payerForOther;
	}
	public String getPayExplanatin() {
		return payExplanatin;
	}
	public void setPayExplanatin(String payExplanatin) {
		this.payExplanatin = payExplanatin;
	}
    
    
}
