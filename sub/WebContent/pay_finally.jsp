<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/style.css"/>
<title>银联在线支付</title>
	<script type="text/javascript">
			function load(){
				var sucOrErr=document.getElementById("sucOrErr").value;
				if(sucOrErr=="success"){
					document.getElementById("success").style.display="";//显示  
				}else{
					document.getElementById("error").style.display="";//显示 
				}
			}
		</script>
</head>
<body onload="load()">
	<input type="hidden" id="sucOrErr" name="sucOrErr" value="${sucOrErr}"/>
	<div class="payHead">
        <div class="payHeadBar">
             <div class="payTitle"><h1>e络盟 - 银联在线支付快速通道</h1></div>
            <p class="payTitleHelp"></p>
        </div>
    </div>
    
    <div class="payFinally" style="display: none;"  id="error">
    	<p><strong class="orderTitle orderWrong">很抱歉，支付失败！</strong><span class="orderBack"></span><span class="clear"></span></p>
	        <div class="payOrder">
	            <ul class="payOrderList">
                <li><span>用户账号：</span>${payerNum}</li>
                <li><span>流水编号：</span>${qId}</li>
                <li><span>失败原因：</span><strong class="t_red">卡内余额不足...</strong></li>
            </ul>
        </div>	
    </div>
    
    <div class="payFinally" style="display: none;"  id="success">
    		 <p><strong class="orderTitle orderRight">恭喜您，支付成功！</strong><span class="orderBack"></span><span class="clear"></span></p>
	        <div class="payOrder">
	             <ul class="payOrderList">
                <li><span>用户账号：</span>${payerNum}</li>
                <li><span>流水编号：</span>${qId}</li>
                <li><span>金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;额：</span><strong class="t_red">
                <fmt:formatNumber value="${orderAmount/100}" type="currency" pattern="0.00"/>元</strong></li>
            </ul>
        </div>	
    </div>
    
    <div class="payFooterBar">
    	<div class="payFooter">
        	<div class="payFooterLink"><a href="#" title="">银联主站</a><span>|</span><a href="#" title="">银联在线</a></div>
            <p class="payCopyright">中国银联股份有限公司版权所有 ©2002-2012 沪 ICP备07032180号</p>
        </div>
    </div>
</body>
</html>

