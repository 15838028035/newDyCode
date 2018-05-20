// 用于存放一些通用变量
var ctx = window.location.protocol + "//" + window.location.host;
//
var hostname = window.location.protocol + "//" + window.location.hostname;

var ctxApp = "http://weixin.xrtz.org:8022";
var ctxAppSec = "http://weixin.xrtz.org:8050/sec";
var ctxAppSec2 = "http://weixin.xrtz.org:8002";
var ctxAppWeixin = "http://weixin.xrtz.org:8050/weixin";

console.log(window.location.protocol+ "//" +"127.0.0.1");

if(hostname == window.location.protocol + "//" +"127.0.0.1" || hostname == window.location.protocol + "//" +"localhost"){

    ctxApp = "http://127.0.0.1:8022";
    ctxAppSec = "http://127.0.0.1:8050/sec";
    ctxAppSec2 = "http://127.0.0.1:8002";

    ctxAppWeixin = "http://127.0.0.1:8050/weixin";

    //windows sever
    /* ctxApp = "http://180.76.107.192:8022";
     ctxAppSec = "http://180.76.107.192:8050/sec";
     ctxAppSec2 = "http://180.76.107.192:8002";*/

}
else {
    //linux
    ctxApp = "http://weixin.xrtz.org:8022";
    ctxAppSec = "http://weixin.xrtz.org:8050/sec";
    ctxAppSec2 = "http://weixin.xrtz.org:8002";
    ctxAppWeixin = "http://weixin.xrtz.org:8050/weixin";

}


var getConfigURL = ctxAppSec + "/api/WeixinConfig/showInfoList";//获得系统配置

var vido_category = "vido_category";//视频分类
var vido_tag = "vido_tag";//视频标签
var audio_category = "audio_category";
var imgtext_category = "imgtext_category";//图文分类 

// JavaScript Document
$(function(){
$('.forder li a').click(function(){	
	$(this).addClass('acver').parents('li').siblings().find('a').removeClass('acver')	
})
$('.app_Guest li').click(function(){
	$(this).addClass('avcet').siblings().removeClass('avcet')	
})

$('.khlst').click(function(){
	layer.open({
    title: false
    ,className:'aopn'
    ,content:$('#jinof').html()
 });
})
$('body').on('click','.nodet',function(){
	
	layer.closeAll()
})
/*搜索遮罩层*/

var bodyheight= $(window).height()

var bodywidth= $(window).width()

$('.Mask').width(bodywidth)

$('.Mask').height(bodyheight-90+'px')

$('.Mask1').width(bodywidth)

$('.Mask1').height(bodyheight)

$('.web_sku').height(bodyheight)


$('.mindor_3').click(function(){
	$(this).css('background-image','url(img/sx_xz.png)')
	$('.Mask1').show()
	$('.web_sku').css('right','0')
	$('.Mask').hide()
	$('.web_Price').hide()
	$('.web_Star').hide()
})
$('.aist').click(function(){
	$('.Mask1').hide()
	$('.web_sku').css('right','-100%')
	
})

$('.hist').click(function(){
	layer.open({
    content: '领取成功'
    ,skin: 'msg'
    ,time: 2 //2秒后自动关闭
 });
})

$('body').on('click','.onctor_a',function(){
	
	layer.closeAll()
})


$('body').on('click','.remsint',function(){
	$(this).parents('.Occupant').remove()
})

$('.biem_cont').click(function(){
	$(this).addClass('biatacve').siblings().removeClass('biatacve')
	
})

/*收藏*/
$('body').on('click','.like',function(){
	if($(this).hasClass('j')){
		$(this).attr('src','static/img/xi02@2xx.png')   
	    $(this).removeClass('j')
	}else{
		$(this).attr('src','static/img/xi01@2xx.png')
	    $(this).addClass('j')
	}		
}) 
	 


//通用提示框
$(".spoert").click(function(){
	var msg = $(this).attr("msg-tite")
	layer.open({	
	className:'smtn',
    content: msg
    ,btn: ['确认', '取消']
    ,skin: 'footer'
    ,yes: function(index){	
     layer.open({
    content: '提交成功'
    ,skin: 'msg'
    ,time: 2 //2秒后自动关闭
  });
    }
  });		
})	




})

/**
 * 获得url参数中名称的值
 * @param name
 * @returns
 */
function getQueryString(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}

/**
 * 将form表单元素的值序列化成对象
 */
$.serializeObject = function(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'].trim();
		} else {
			o[this['name']] = this['value'].trim();
		}
	});
	return o;
};



/**
 * 下拉列表
 * @param cfgClass
 * @param obj
 * @param titleElementId
 */
function initConfigSelect(cfgClass,obj, titleElementId) {
	
	$.ajax({
	    url: getConfigURL+"?cfgClass="+cfgClass,
	    data: {},
	    type: "GET",
	    dataType: "json",
	    async: false,
	    success: function(data){
	    	console.log("fn.getDistrict....."+data);
			
			var html = "";
	        
	        var datas = data.list;
	        
	        for(var i=0; i< datas.length;i++){
	            
	            html+='<option value='+datas[i].cfgValue+'>'+datas[i].cfgName+'</option>';
	        }
	        
	        console.log("html======"+html);
	        $("#"+obj).html(html);
			
			if(typeof obj != "undefined" && obj != null){
				$("#"+titleElementId).val(obj);
				//if($("#"+titleElementId)) $("#"+titleElementId).attr("value",$("#"+$this.attr("id")+" option:selected").text());
			}
			
	      },
	      error: function(data){
	          try {
	             layer.msg("请求异常");
	             return false;
	          }catch (e) {
	             console.log(e);
	          }
	      }
	 });
	
}

/**
 * checkbox
 * @param cfgClass
 * @param obj
 * @param titleElementId
 */
function initConfigCheckbox(cfgClass, checkboxName, tableId) {
	$("#"+tableId).empty();
	
	$.ajax({
	    url: getConfigURL+"?cfgClass="+cfgClass,
	    data: {},
	    type: "GET",
	    dataType: "json",
	    async: false,
	    success: function(data){
	    	console.log("fn.initConfigCheckbox....."+data);
	        
	        var datas = data.list;
	        var li ="";
	        var html = "";
	        
	        for(var i=0; i< datas.length;i++){
	            var input_checkbox = "<input id='tag_id_"+datas[i].id+"' type='checkbox' lay-skin='primary' title='"+datas[i].cfgName+"' name='"+checkboxName+ "' value='"+datas[i].cfgValue+"'/>";
	    		html +=input_checkbox;
	    		
	        }
	        $("#"+tableId).html(html);
	      },
	      error: function(data){
	          try {
	             layer.msg("请求异常");
	             return false;
	          }catch (e) {
	             console.log(e);
	          }
	      }
	 });
	
}

/**
 * initConfigRadio
 * @param cfgClass
 * @param obj
 * @param titleElementId
 */
function initConfigRadio(cfgClass, checkboxName, tableId) {
	$("#"+tableId).empty();
	
	$.ajax({
	    url: getConfigURL+"?cfgClass="+cfgClass,
	    data: {},
	    type: "GET",
	    dataType: "json",
	    async: false,
	    success: function(data){
	        var datas = data.list;
	        var li ="";
	        var html = "";
	        
	        for(var i=0; i< datas.length;i++){
	            var input_checkbox = "<input id='tag_id_"+datas[i].id+"' type='radio'  title='"+datas[i].cfgName+"' name='"+checkboxName+"' value='"+datas[i].cfgValue+"'/>";
	    		html +=input_checkbox;
	    		
	        }
	        
	        console.log(html);
	        $("#"+tableId).html(html);
	      },
	      error: function(data){
	          try {
	             layer.msg("请求异常");
	             return false;
	          }catch (e) {
	             console.log(e);
	          }
	      }
	 });
}


