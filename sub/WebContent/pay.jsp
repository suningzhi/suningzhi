<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/taglibs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/style.css"/>
<script type="text/javascript">
	function load(){
		var input=document.getElementById("orderInfo.merOrderId");
		input.value="SO-";
		var val = input.value; 
		input.focus(); 
		input.value = ''; 
		input.value = val; 
		document.getElementById("orderInfo.merOrderId").focus();
		//checkForm();
	}

	//验证订单号/采购单编号
	function checkMerOrderId(){
		//验证订单号/采购单编号
		var merOrderId=document.getElementById("orderInfo.merOrderId").value;
		merOrderId=merOrderId.replace(/(^\s*)|(\s*$)/g, "");//去除空白字符
		if(merOrderId=="" || merOrderId.length<0){
			document.getElementById("merOrderId").style.display="none";//隐藏
			document.getElementById("merOrderId_p").style.display = "block"; //先显示
			document.getElementById("merOrderId_p").style.color = "#e03233"; //把字体颜色变红
			document.getElementById("merOrderId_p").innerHTML="请录入订单号,格式：由SO-开头,数字,字母组成的8-32个字符输入";
			document.getElementById("orderInfo.merOrderId").focus();
			return false;
		}else{
			 var re= /^SO\-[a-zA-Z0-9]{5,29}$/;
			if(!re.test(merOrderId)){
				document.getElementById("merOrderId_p").style.display = "block"; //先显示
				document.getElementById("merOrderId_p").style.color = "#e03233"; //把字体颜色变红
				document.getElementById("merOrderId_p").innerHTML="请录入订单号,格式：由SO-开头,数字,字母组成的8-32个字符输入";
				document.getElementById("merOrderId").style.display="none";//隐藏
				document.getElementById("orderInfo.merOrderId").focus();
				return false;
			}else{
				document.getElementById("merOrderId").style.display="block";//显示
				document.getElementById("merOrderId_p").style.display="none";//隐藏
				return true;
			}
		}
	}


//验证金额
function checkMerAmt(){
	//验证金额
	var merAmt=document.getElementById("orderInfo.merAmt").value;
	merAmt=merAmt.replace(/(^\s*)|(\s*$)/g, "");//去除空白字符
	if(merAmt=="" || merAmt.length<0){
		document.getElementById("merAmt").style.display="none";//隐藏
		document.getElementById("merAmt_p").style.display = "block"; //先显示
		document.getElementById("merAmt_p").style.color = "#e03233"; //把字体颜色变红
		document.getElementById("merAmt_p").innerHTML="请输入订单金额(大于0),单位(元),格式：12或12.21";
		document.getElementById("orderInfo.merAmt").focus();
		return false;
	}else{
		if(parseFloat(merAmt)<0.000001){
			document.getElementById("merAmt_p").style.display = "block"; //显示提示信息
			document.getElementById("merAmt_p").style.color = "#e03233"; //把字体颜色变红
			document.getElementById("merAmt_p").innerHTML="请输入单金额(大于0),单位(元),格式：12或12.21";
			document.getElementById("merAmt").style.display="none";//隐藏
			document.getElementById("orderInfo.merAmt").focus();
			return false;
		}
		
		var k=merAmt.indexOf(".");
		if(k>0){
			document.getElementById("orderInfo.merAmt").setAttribute("maxlength",11);
			document.getElementById("merAmt").style.display="block";//显示
			document.getElementById("merAmt_p").style.display="none";//隐藏
			//return true;
		}
		if(parseFloat(merAmt)>10000000.00){//最大金额为10000000.00,单位（元）
			document.getElementById("merAmt_p").style.display = "block"; //把字体颜色变红
			document.getElementById("merAmt_p").style.color = "#e03233"; //把字体颜色变红
			document.getElementById("merAmt_p").innerHTML="您输入的金超出最大限额(10000000.00),单位(元),格式：12或12.21";
			document.getElementById("merAmt").style.display="none";//隐藏
			document.getElementById("orderInfo.merAmt").focus();
			return false;
		}else{
			var re= /^[0-9]+([.]\d{2})?$/;
			if(!re.test(merAmt)){
				document.getElementById("merAmt_p").style.display = "block"; //把字体颜色变红
				document.getElementById("merAmt_p").style.color = "#e03233"; //把字体颜色变红
				document.getElementById("merAmt_p").innerHTML="请输入订单金额(大于0),单位(元),格式：12或12.21";
				document.getElementById("merAmt").style.display="none";//隐藏
				document.getElementById("orderInfo.merAmt").focus();
				return false;
			}else{
				document.getElementById("merAmt").style.display="block";//显示
				document.getElementById("merAmt_p").style.display="none";//隐藏
				return true;
			}
		}
	}
}

//验证用户账号
function checkPayerNum(){
	var payerNum=document.getElementById("orderInfo.payerNum").value;
	payerNum=payerNum.replace(/(^\s*)|(\s*$)/g, "");//去除空白字符
	var totlength = 0;
	for(var i=0;i <payerNum.length;i++){ 
		var c =payerNum.charAt(i); 
		if(escape(c).length >= 6){ 
			totlength = totlength + 2; 
		} else { 
			totlength = totlength + 1; 
		}
	} 
	if(totlength<0){
		document.getElementById("payerNum").style.display="none";//隐藏
		document.getElementById("payerNum_p").style.display="block";//显示
		document.getElementById("orderInfo.payerNum").focus();
		return false;
	}else{
		if(totlength>30){
			document.getElementById("payerNum_p").style.display = "block"; //把字体颜色变红
			document.getElementById("payerNum_p").style.color = "#e03233"; //把字体颜色变红
			document.getElementById("payerNum_p").innerHTML="请输入用户账号,格式：***@**,不得超过30个字符";
			document.getElementById("payerNum").style.display="none";//隐藏
			document.getElementById("orderInfo.payerNum").focus();
			return false;
		}else{
			//var re_email =/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,4})$/;
			var re_email=/^[\w\-\.]+@[\w\-\.]+(\.\w+)+$/;
			if(re_email.test(payerNum)){
	            document.getElementById("payerNum").style.display="block";//显示
				document.getElementById("payerNum_p").style.display="none";//隐藏
				return true;
			}else {
				document.getElementById("payerNum_p").style.display = "block"; //把字体颜色变红
				document.getElementById("payerNum_p").style.color = "#e03233"; //把字体颜色变红
				document.getElementById("payerNum_p").innerHTML="请输入用户账号,格式：***@**,不得超过30个字符";
				document.getElementById("payerNum").style.display="none";//隐藏
				document.getElementById("orderInfo.payerNum").focus();
				return false;
			}
		}
	}
}

//验证公司名称
function checkPayerCompany(){
	 var payerCompany=document.getElementById("orderInfo.payerCompany").value;
	 payerCompany=payerCompany.replace(/(^\s*)|(\s*$)/g, "");//去除空白字符
		var totlength = 0;
		for(var i=0;i <payerCompany.length;i++){ 
			var c =payerCompany.charAt(i); 
			if(escape(c).length >= 6){ 
				totlength = totlength + 2; 
			} else { 
				totlength = totlength + 1; 
			}
		} 
		
		if(payerCompany=="" || payerCompany.length<0){
			/**document.getElementById("payerCompany").style.display="none";//隐藏
			document.getElementById("payerCompany_p").style.display="block";//显示提示信息
			document.getElementById("orderInfo.payerCompany").focus();
			return false;*/
			document.getElementById("payerCompany").style.display="none";//显示
			document.getElementById("payerCompany_p").style.display="none";//显示提示信息
			return true;
		}else{
			if(totlength<=50){
				var reg = /^[\u4E00-\u9FA5\,\.\，\。a-zA-Z]{0,50}$/;  
				if(!reg.test(payerCompany)){  
					document.getElementById("payerCompany_p").style.display = "block"; //把字体颜色变红
					document.getElementById("payerCompany_p").style.color = "#e03233"; //把字体颜色变红
					document.getElementById("payerCompany_p").innerHTML="公司名称由中,英文字母,逗号,句号等组成,长度为0-50个字符";
					document.getElementById("payerCompany").style.display="none";//隐藏
					document.getElementById("orderInfo.payerCompany").focus();
					return false;
				}else{
					document.getElementById("payerCompany").style.display="block";//显示
					document.getElementById("payerCompany_p").style.display="none";//显示提示信息
					return true;
				}
			}else{
				document.getElementById("payerCompany_p").style.display = "block"; //把字体颜色变红
				document.getElementById("payerCompany_p").style.color = "#e03233"; //把字体颜色变红
				document.getElementById("payerCompany_p").innerHTML="公司名称由中,英文字母,逗号,英文句号等组成,长度为0-50个字符";
				document.getElementById("payerCompany").style.display="none";//隐藏
				document.getElementById("orderInfo.payerCompany").focus();
				return false;
			}
				
		}
}

//验证付款人姓名
function checkPayerName(){
	var payerName=document.getElementById("orderInfo.payerName").value;
	payerName=payerName.replace(/(^\s*)|(\s*$)/g, "");//去除空白字符
	
	var totlength = 0;
	for(var i=0;i <payerName.length;i++){ 
		var c =payerName.charAt(i); 
		if(escape(c).length >= 6){ 
			totlength = totlength + 2; 
		} else { 
			totlength = totlength + 1; 
		}
	} 
	
	if(payerName=="" || payerName.length<0){
		document.getElementById("payerName").style.display="none";//隐藏
		document.getElementById("payerName_p").style.display="block";//显示提示信息
		document.getElementById("orderInfo.payerName").focus();
		return false;
	}else{
		if(totlength>32){
			document.getElementById("payerName_p").style.display = "block"; //把字体颜色变红
			document.getElementById("payerName_p").style.color = "#e03233"; //把字体颜色变红
			document.getElementById("payerName_p").innerHTML="付款人姓名不包括特殊字符,32个字符以内";
			document.getElementById("payerName").style.display="none";//隐藏
			document.getElementById("orderInfo.payerName").focus();
			return false;
		}else{
			 var pattern = new RegExp("[`~!@#$%^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）&;|{}【】‘；：”“'。，、？+-]");
			if(pattern.test(payerName)){  
				document.getElementById("payerName_p").style.display = "block"; //把字体颜色变红
				document.getElementById("payerName_p").style.color = "#e03233"; //把字体颜色变红
				document.getElementById("payerName_p").innerHTML="付款人姓名不包括特殊字符,32个字符以内";
				document.getElementById("payerName").style.display="none";//隐藏
				document.getElementById("orderInfo.payerName").focus();
				return false;
			}else{
				document.getElementById("payerName").style.display="block";//显示
				document.getElementById("payerName_p").style.display="none";//隐藏
				return true;
			}
		}
	} 
}


//验证电话
function checkPhone(){
	var phone=document.getElementById("orderInfo.phone").value;
	phone=phone.replace(/(^\s*)|(\s*$)/g, "");//去除空白字符
	//var m = /^0(([1-9]\d)|([3-9]\d{2}))\d{8}$/;//验证电话号码为7-8位数字并带有区号
	var m = /(^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$)|(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
	if(phone.length>0){
		if(!m.test(phone)){
			document.getElementById("phone_p").style.display="block";//先显示
			document.getElementById("phone_p").style.color = "#e03233"; //把字体颜色变红
			document.getElementById("phone_p").innerHTML="请输入正确的电话号码,格式为: 区号-号码(-分机号)/手机号";
			document.getElementById("phone").style.display="none";//隐藏
			document.getElementById("orderInfo.phone").focus();
			return false;
		}else{
			document.getElementById("phone").style.display="block";//显示
			document.getElementById("phone_p").style.display="none";//隐藏
			return true;
		}
	}else{
		document.getElementById("phone").style.display="none";//隐藏
		document.getElementById("phone_p").style.display="none";//隐藏
		return true;
	}
	
} 

//判断是否为代付，代付为1，否则为0
function checkPayerForOther(){
	var payerForOther=null;
	var payExplanatin=document.getElementById("orderInfo.payExplanatin").value;
	payExplanatin=payExplanatin.replace(/(^\s*)|(\s*$)/g, "");//去除空白字符
	var radios=document.getElementsByName("orderInfo.payerForOther");
	  for(var i=0;i<radios.length;i++){     
	        if(radios[i].checked){     
	            payerForOther= radios[i].value;     
	             }     
	         }    
	
		var totlength = 0;
		for(var i=0;i <payExplanatin.length;i++){ 
			var c =payExplanatin.charAt(i); 
			if(escape(c).length >= 6){ 
				totlength = totlength + 2; 
			} else { 
				totlength = totlength + 1; 
			}
		} 
	if(totlength>50){
		document.getElementById("payExplanatin_p").style.display="block";//先显示
		document.getElementById("payExplanatin_p").style.color = "#e03233"; //把字体颜色变红
		document.getElementById("payExplanatin_p").innerHTML="50个字符以内";
		document.getElementById("orderInfo.payExplanatin").focus();
		return false;
	 }else {
		 if(payerForOther=="1"){//是代付，则需在补充说明里面填写采购单编号
 			 if(payExplanatin=="" || payExplanatin.length<0){
 				document.getElementById("payExplanatin_p").style.display="block";//先显示
				document.getElementById("payExplanatin_p").style.color = "#e03233"; //把字体颜色变红
				document.getElementById("payExplanatin_p").innerHTML="请在付款说明格里说明采购单编号";
				return false;
 			 }else{
				document.getElementById("payExplanatin_p").style.display="none";//隐藏
 				return true;
 			 }
 		 }else {
 			document.getElementById("payExplanatin_p").style.display="block";//先显示
 			document.getElementById("payExplanatin_p").style.color = ""; //把字体颜色变红
			document.getElementById("payExplanatin_p").innerHTML="如联系方法，商品要求、数量等。50个字符以内";
			return true;
		 };
	 }
	
}
//提交时验证
function checkForm(){
	var orderId=checkMerOrderId();
	if(!orderId){
		return false;
	}else if(!checkMerAmt()){
		return false;
	}else if(!checkPayerNum()){
		return false;
	}else if(!checkPayerName()){
		return false;
	}else if(!checkPayerCompany()){
		return false;
	}else if(!checkPhone()){
		return false;
	}else{
		return checkPayerForOther();
	}
}

// Firefox, Google Chrome, Opera, Safari, Internet Explorer from version 9
    function OnInput (event) {
    	var payExplanatin=document.getElementById("orderInfo.payExplanatin").value;
    	var totlength = 0;
		for(var i=0;i <payExplanatin.length;i++){ 
			var c =payExplanatin.charAt(i); 
			if(escape(c).length >= 6){ 
				totlength = totlength + 2; 
			} else { 
				totlength = totlength + 1; 
			}
		} 
		if(totlength>50){
			document.getElementById("payExplanatin_p").style.display="block";//先显示
			document.getElementById("payExplanatin_p").style.color = "#e03233"; //把字体颜色变红
			document.getElementById("payExplanatin_p").innerHTML="50个字符以内";
			document.getElementById("orderInfo.payExplanatin").focus();
			return false;
		}
    }
	// Internet Explorer
    function OnPropChanged (event) {
    	var payExplanatin=document.getElementById("orderInfo.payExplanatin").value;
    	var totlength = 0;
		for(var i=0;i <payExplanatin.length;i++){ 
			var c =payExplanatin.charAt(i); 
			if(escape(c).length >= 6){ 
				totlength = totlength + 2; 
			} else { 
				totlength = totlength + 1; 
			}
		} 
        if (event.propertyName.toLowerCase () == "value") {
        	if(totlength>50){
            	document.getElementById("payExplanatin_p").style.display="block";//先显示
				document.getElementById("payExplanatin_p").style.color = "#e03233"; //把字体颜色变红
 				document.getElementById("payExplanatin_p").innerHTML="50个字符以内";
 				document.getElementById("orderInfo.payExplanatin").focus();
 				return false;
        	}
        }
    } 
</script>
    
<title>e络盟 - 银联在线支付快速通道</title>
</head>
<body onload="load()">
	<div class="payHead">
		<div class="payHeadBar">
			<div class="payTitle">
				<h1>e络盟 - 银联在线支付快速通道</h1>
			</div>
			<p class="payTitleHelp"></p>
		</div>
	</div>

	<div class="payBar">

		<div class="payChannel">
			<p class="payChannelBar">
				<span class="active">填写订单信息</span>
			</p>
		</div>
		<div class="formBar">
			<form method="post" action="req/toTrans.html" name="paySubmint" onsubmit="return checkForm();" AUTOCOMPLETE="OFF">
				<div class="contentForm">
					<label for="cardNum">收款方：</label>
					<p class="input_txt">
						<strong>e络盟</strong> (<a href="http://cn.element14.com">http://cn.element14.com</a>)
					</p>
				</div>
				<div class="contentForm">
					<label>销售订单号 ：</label> <input name=orderInfo.merOrderId
						id="orderInfo.merOrderId" type="text" value="" class="long"
						maxlength=32 onkeyup="checkMerOrderId()" /> <span
						style="color: red; float: left; margin: 8px 0 0 4px;">*</span> <span
						class="tipsRight" style="display: none" id="merOrderId"></span>
					<p class="tips" style="display: block" id="merOrderId_p">请录入订单号,格式：由SO-开头,数字,字母组成的8-32个字符输入</p>
				</div>
				<div class="contentForm">
					<label>订单合计：</label> <input name="orderInfo.merAmt"
						id="orderInfo.merAmt" type="text" value="" class="long"
						maxlength="11" onkeyup="checkMerAmt()" /> <span
						style="color: red; float: left; margin: 8px 0 0 4px;">*</span> <span
						class="tipsRight" style="display: none" id="merAmt"></span>
					<p class="tips" id="merAmt_p">请输入订单金额(大于0),单位(元),格式：12或12.21</p>
				</div>
				<div class="contentForm">
					<label>用户帐号：</label> <input name="orderInfo.payerNum"
						id="orderInfo.payerNum" type="text" value="" class="long"
						maxlength="31" onkeyup="checkPayerNum()" /> <span
						style="color: red; float: left; margin: 8px 0 0 4px;">*</span> <span
						class="tipsRight" style="display: none" id="payerNum"></span>
					<p class="tips" style="display: block" id="payerNum_p">请输入用户账号,格式：***@**,不得超过30个字符</p>
				</div>
				<div class="contentForm">
					<label>付款人姓名：</label> 
					<input name="orderInfo.payerName" id="orderInfo.payerName" type="text" value="" class="long" maxlength="33" onkeyup="checkPayerName()" /> 
					<span style="color: red; float: left; margin: 8px 0 0 4px;">*</span> 
					<span class="tipsRight" style="display: none" id="payerName"></span>
					<p class="tips" style="display: block" id="payerName_p">付款人姓名不包括特殊字符,32个字符以内</p>
				</div>
				<div class="contentForm">
					<label>公司名称：</label> <input name="orderInfo.payerCompany"
						id="orderInfo.payerCompany" type="text" value="" class="long"
						maxlength="51" onkeyup="checkPayerCompany()" /> <span
						class="tipsRight" style="display: none" id="payerCompany"></span>
					<p class="tips" style="display: none" id="payerCompany_p">请输入公司名称,必须是0-50个字符</p>
				</div>

				<div class="contentForm">
					<label>付款人联系电话：</label> <input name="orderInfo.phone"
						id="orderInfo.phone" type="text" value="" class="long"
						maxlength=18 onkeyup="checkPhone()" /> <span class="tipsRight"
						style="display: none" id="phone"></span>
					<p class="tips" id="phone_p">格式为: 区号-号码(-分机号)/手机号</p>
				</div>
				<div class="contentForm">
					<label>是否代替别人或公司：</label>
					<p class="input_txt">
						<label><input type="radio" name="orderInfo.payerForOther"
							value="1" />是</label> <label><input type="radio"
							name="orderInfo.payerForOther" value="0" checked="checked" />否</label>
					</p>
					<p class="tips">如果是替别人或公司付款， 请在付款说明格里说明采购单编号</p>
				</div>
				<div class="contentForm">
					<label>付款说明：</label>
					<textarea name="orderInfo.payExplanatin"
						id="orderInfo.payExplanatin" class="input_txt" maxlength="51"
						onkeyup="checkPayerForOther()" oninput="OnInput (event)" onpropertychange="OnPropChanged (event)"
						 style="width: 320px; height: 90px;"></textarea>
					<p class="tips" id="payExplanatin_p">如联系方法，商品要求、数量等。50个字符以内</p>
				</div>

				<div class="formBtnBar">
					<input type="submit" class="formBtn" value="进入银联在线支付付款"
						onmouseover="this.className='formBtnH'"
						onmouseout="this.className='formBtn'" />
				</div>
				<div class="treatyBox" style="border: 1px solid #CCC;">
					如果您点击"进入银联在线支付付款”按钮，即表示您已经接受“银联在线支付服务协议”，同意向卖家购买 此物品。
					您有责任查阅完整的物品登录资料，包括卖家的说明和接受的付款方式。卖家必须承担物品信息正确登录的 责任！</div>

			</form>
			<div class="pay_note">
				<h3>小贴士:</h3>
				<div>
					<p>本通道为e络盟客户专用，采用银联在线付款。请在支付前与本网站达成一致。</p>
					<p>请务必与e络盟确认好订单和货款后，再付款。可以在快速付款通道里的“付款概要”和“订单金额”中填入相应的订单信息。</p>
				</div>
				<table width="100%" border="1">
					<tr>
						<td>连络我们：</td>
						<td>上海：+86 21 6196 1388</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>北京：+86 10 5632 3688</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>成都：+86 28 6685 1888</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>深圳：+86 755 8305 4888</td>
					</tr>
					<tr>
						<td>全国免费客服热线：</td>
						<td>+86 800 820 5279</td>
					</tr>
				</table>
			</div>
		</div>

	</div>
	<div class="clear"></div>

	<div class="payFooterBar">
		<div class="payFooter">
			<div class="payFooterLink">
				<a href="#" title="">银联主站</a><span>|</span><a href="#" title="">银联在线</a>
			</div>
			<p class="payCopyright">中国银联股份有限公司版权所有 ©2002-2012 沪 ICP备07032180号</p>
		</div>
	</div>
</body>
</html>

