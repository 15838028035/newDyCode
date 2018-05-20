var regPhone =  /^1[3|4|5|7|8]\d{9}$/;
var regZNum=  /^[1-9][0-9]*$/;
var regZTONum=  /^[0-1]\.[0-9]*$/;
var regEmial=  /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
var regUcard = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
var regMoney = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
var regPassword = /^[A-Za-z0-9|\w]{5,16}$/; 
var regRate= /^([1-9][\d]{0,7}|0)(\.[\d]{1,3})?$/;
var regTel = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;

/** 
 * 校验字段是否允许为空
 * message为提示语关键字
 * @author chunyu.xia
 */
function isEmpty(param,message){
	if(param==''){
		layer.msg(message+'不能为空');				
		return false;
	}else{
		return true;
	}
}

/** 
 * 校验邮箱号
 * message为提示语关键字
 * @author chunyu.xia
 */
/*function checkEmail(param,message){
	if(param!=''){
		if(!regEmial.test(param)){
			layer.msg(message+'格式不正确');				
			return false;
		}else{
			return true;
		}
	}else{
		layer.msg(message+'不能为空');				
		return false;
	}
		
}*/

function checkEmail(param,message){
	if(param!=''){
		if(!regEmial.test(param)){
			layer.msg(message+'格式不正确');				
			return false;
		}else{
			return true;
		}
	}
}

/** 
 * 校验手机号码
 * message为提示语关键字
 * @author chunyu.xia
 */
function checkPhone(param,message){
	if(param!=''){
		if(!regPhone.test(param)){
			layer.msg(message+'格式不正确');				
			return false;
		}else{
			return true;
		}
	}else{
		layer.msg(message+'不能为空');				
		return false;
	}
	
}

/** 
 * 校验身份证号
 * message为提示语关键字
 * @author chunyu.xia
 */
function checkUcard(param,message){
	if(param!=''){
		if(!regUcard.test(param)){
			layer.msg(message+'格式不正确');			
			return false;
		}else{
			return true;
		}
	}else{
		layer.msg(message+'不能为空');	
		return false;
	}
}

/** 
 * 校验数字是否为正整数
 * message为提示语关键字
 * @author chunyu.xia
 */
function checkZNum(param,message){
	if(param!=''){
		if(!regZNum.test(param)){
			layer.msg(message+'格式不正确');
			return false;
		}else{
			return true;
		}
	}else{
		layer.msg(message+'不能为空');	
		return false;
	}
}

/** 
 * 校验数字是否为0-1之间的数字
 * message为提示语关键字
 * @author chunyu.xia
 */
function checkZTONum(param,message){
	if(param!=''){
		if(!regZTONum.test(param)){
			layer.msg(message+'请输入0-1之间的数字');
			return false;
		}else{
			return true;
		}
	}else{
		layer.msg(message+'不能为空');	
		return false;
	}
}

/** 
 * 校验金额格式
 * message为提示语关键字
 * @author chunyu.xia
 */
function checkMoney(param,message){
	if(param != ''){
		if(!regMoney.test(param)){
			layer.msg(message+'格式不正确');
			return false;
		}else{
			return true;
		}
	}else{
		layer.msg(message+'不能为空');	
		return false;
	}
}

/** 
 * 校验密码格式
 * message为提示语关键字
 * @author chunyu.xia
 */
function checkPassword(param,message){
	if(param!=''){
		if(!regPassword.test(param)){
			layer.msg(message+'格式不正确');
			return false;
		}else{
			return true;
		}
	}else{
		layer.msg(message+'不能为空');	
		return false;
	}
}

/** 
 * 校验比例
 * message为提示语关键字
 * @author chunyu.xia
 */
function checkRate(param,message){
	if(param != ''){
		if(!regRate.test(param)){
			layer.msg(message+'格式不正确');
			return false;
		}else{
			return true;
		}
	}else{
		layer.msg(message+'不能为空');	
		return false;
	}
}

/** 
 * 校验字段是否为电话号码
 * message为提示语关键字
 */
function isTel(param,message){
	if(param != ''){
		if(!regPhone.test(param)){
			layer.msg(message+'格式不正确');
			return false;
		}else{
			return true;
		}
	}else{
		layer.msg(message+'不能为空');	
		return false;
	}
}




