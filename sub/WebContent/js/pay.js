
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