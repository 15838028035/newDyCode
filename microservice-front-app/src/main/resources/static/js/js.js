
var ctx = window.location.protocol + "//" + window.location.host;
//
var hostname = window.location.protocol + "//" + window.location.hostname;


//var ctxApp = "http://192.168.0.138:8022";
//var ctxAppSec = "http://192.168.0.138:8050/sec";
//var ctxAppSec2 = "http://192.168.0.138:8002";
//var ctxAppWeixin = "http://weixin.xrtz.org:8050/weixin";

var ctxApp = "http://weixin.xrtz.org:8022";
var ctxAppSec = "http://weixin.xrtz.org:8050/sec";
var ctxAppSec2 = "http://weixin.xrtz.org:8002";
var ctxAppWeixin = "http://weixin.xrtz.org:8050/weixin";


//if(hostname == window.location.protocol + "//" +"127.0.0.1" || hostname == window.location.protocol + "//" +"localhost"){
  
//}
//else {
//    ctxApp = "http://weixin.xrtz.org:8022";
//    ctxAppSec = "http://weixin.xrtz.org:8050/sec";
//    ctxAppSec2 = "http://weixin.xrtz.org:8002";
//    ctxAppWeixin = "http://weixin.xrtz.org:8050/weixin";
//
//}

//windows sever
    /* ctxApp = "http://180.76.107.192:8022";
     ctxAppSec = "http://180.76.107.192:8050/sec";
     ctxAppSec2 = "http://180.76.107.192:8002";*/

/*$.ajax({
    url: ctxAppSec+"/api/loginstatus",
    type: "GET",
    dataType: "json",
    success: function(data){
        if(data.respCode == 0){
            window.location.href=ctxApp+'/login.html';
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
});*/


var acekeystoken = $.cookie('bearcktkaeskey');

var getConfigURL = ctxAppSec + "/api/WeixinConfig/showInfoList";//获得系统配置

var vido_category = "vido_category";//视频分类
var vido_tag = "vido_tag";//视频标签
var audio_category = "audio_category";
var imgtext_category = "imgtext_category";//图文分类 


// JavaScript Document
$(function () {
    $('.forder li a').click(function () {
        $(this).addClass('acver').parents('li').siblings().find('a').removeClass('acver')
    })
    $('.app_Guest li').click(function () {
        $(this).addClass('avcet').siblings().removeClass('avcet')
    })

    $('.khlst').click(function () {
        layer.open({
            title: false
            , className: 'aopn'
            , content: $('#jinof').html()
        });
    })
    $('body').on('click', '.nodet', function () {

        layer.closeAll()
    })
    /*搜索遮罩层*/

    var bodyheight = $(window).height()

    var bodywidth = $(window).width()

    $('.Mask').width(bodywidth)

    $('.Mask').height(bodyheight - 90 + 'px')

    $('.Mask1').width(bodywidth)

    $('.Mask1').height(bodyheight)

    $('.web_sku').height(bodyheight)


    $('.mindor_3').click(function () {
        $(this).css('background-image', 'url(img/sx_xz.png)')
        $('.Mask1').show()
        $('.web_sku').css('right', '0')
        $('.Mask').hide()
        $('.web_Price').hide()
        $('.web_Star').hide()
    })
    $('.aist').click(function () {
        $('.Mask1').hide()
        $('.web_sku').css('right', '-100%')

    })

    $('.hist').click(function () {
        layer.open({
            content: '领取成功'
            , skin: 'msg'
            , time: 2 //2秒后自动关闭
        });
    })

    $('body').on('click', '.onctor_a', function () {

        layer.closeAll()
    })


    $('body').on('click', '.remsint', function () {
        $(this).parents('.Occupant').remove()
    })

    $('.biem_cont').click(function () {
        $(this).addClass('biatacve').siblings().removeClass('biatacve')

    })

    /*收藏*/
    $('body').on('click', '.like', function () {
        if ($(this).hasClass('j')) {
            $(this).attr('src', 'static/img/xi02@2xx.png')
            $(this).removeClass('j')
        } else {
            $(this).attr('src', 'static/img/xi01@2xx.png')
            $(this).addClass('j')
        }
    })


//通用提示框
    $(".spoert").click(function () {
        var msg = $(this).attr("msg-tite")
        layer.open({
            className: 'smtn',
            content: msg
            , btn: ['确认', '取消']
            , skin: 'footer'
            , yes: function (index) {
                layer.open({
                    content: '提交成功'
                    , skin: 'msg'
                    , time: 2 //2秒后自动关闭
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
$.serializeObject = function (form) {
    var o = {};
    $.each(form.serializeArray(), function (index) {
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
function initConfigSelect(cfgClass, obj, titleElementId) {

    $.ajax({
        url: getConfigURL + "?cfgClass=" + cfgClass,
        data: {"Authorization":$.cookie('bearcktkaeskey')},
        type: "GET",
        dataType: "json",
        async: false,
        success: function (data) {
            console.log("fn.getDistrict....." + data);

            var html = "";

            var datas = data.list;

            for (var i = 0; i < datas.length; i++) {

                html += '<option value=' + datas[i].cfgValue + '>' + datas[i].cfgName + '</option>';
            }

            console.log("html======" + html);
            $("#" + obj).html(html);

            if (typeof obj != "undefined" && obj != null) {
                $("#" + titleElementId).val(obj);
                //if($("#"+titleElementId)) $("#"+titleElementId).attr("value",$("#"+$this.attr("id")+" option:selected").text());
            }

        },
        error: function (data) {
            try {
                layer.msg("请求异常");
                return false;
            } catch (e) {
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
    $("#" + tableId).empty();

    $.ajax({
        url: getConfigURL + "?cfgClass=" + cfgClass,
        data: {"Authorization":$.cookie('bearcktkaeskey')},
        type: "GET",
        dataType: "json",
        async: false,
        success: function (data) {
            console.log("fn.initConfigCheckbox....." + data);

            var datas = data.list;
            var li = "";
            var html = "";

            for (var i = 0; i < datas.length; i++) {
                var input_checkbox = "<input id='tag_id_" + datas[i].id + "' type='checkbox' lay-skin='primary' title='" + datas[i].cfgName + "' name='" + checkboxName + "' value='" + datas[i].cfgValue + "'/>";
                html += input_checkbox;

            }
            $("#" + tableId).html(html);
        },
        error: function (data) {
            try {
                layer.msg("请求异常");
                return false;
            } catch (e) {
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
    $("#" + tableId).empty();

    $.ajax({
        url: getConfigURL + "?cfgClass=" + cfgClass,
        data: {"Authorization":$.cookie('bearcktkaeskey')},
        type: "GET",
        dataType: "json",
        async: false,
        success: function (data) {
            var datas = data.list;
            var li = "";
            var html = "";

            for (var i = 0; i < datas.length; i++) {
                var input_checkbox = "<input id='tag_id_" + datas[i].id + "' type='radio'  title='" + datas[i].cfgName + "' name='" + checkboxName + "' value='" + datas[i].cfgValue + "'/>";
                html += input_checkbox;

            }

            console.log(html);
            $("#" + tableId).html(html);
        },
        error: function (data) {
            try {
                layer.msg("请求异常");
                return false;
            } catch (e) {
                console.log(e);
            }
        }
    });
}


function commonLayerFrame(title, url) {
    layer.open({
        title: title,
        type: 2,
        maxmin: true,
        area: ['95%', '95%'],
        content: url,
// 下面这句是,添加页面关闭后,刷新本页面.
        end: function () {
            location.reload();
        }
    });
}


function commonLayerFrame2(title, url) {
    layer.open({
        title: title,
        type: 2,
        maxmin: true,
        area: ['95%', '95%'],
        content: url,
// 下面这句是,添加页面关闭后,刷新本页面.
        end: function () {
        }
    });
}

//产生随机数
function randomString(len) {
    len = len || 32;
    var $chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    var maxPos = $chars.length;
    var pwd = '';
    for (i = 0; i < len; i++) {
        //0~32的整数
        pwd += $chars.charAt(Math.floor(Math.random() * (maxPos+1)));
    }
    return pwd;
}

String.prototype.endWith=function(s){
	  if(s==null||s==""||this.length==0||s.length>this.length)
	     return false;
	  if(this.substring(this.length-s.length)==s)
	     return true;
	  else
	     return false;
	  return true;
	 }

	 String.prototype.startWith=function(s){
	  if(s==null||s==""||this.length==0||s.length>this.length)
	   return false;
	  if(this.substr(0,s.length)==s)
	     return true;
	  else
	     return false;
	  return true;
	 }

function initForList(obj, titleElementId) {

    $.ajax({
        url: ctxAppSec + "/api/WeixinConfig/forList",
        data: {"Authorization":$.cookie('bearcktkaeskey')},
        type: "GET",
        dataType: "json",
        async: false,
        success: function (data) {
            console.log(data);
            var html = "";


            for (var i = 0; i < data.length; i++) {
                console.log(data[i]);
                html += '<option value=' + data[i].cfg_class + '>' + data[i].cfg_class + '</option>';
            }

            console.log("html======" + html);
            $("#" + obj).html(html);

            if (typeof obj != "undefined" && obj != null) {
                $("#" + titleElementId).val(obj);
                //if($("#"+titleElementId)) $("#"+titleElementId).attr("value",$("#"+$this.attr("id")+" option:selected").text());
            }

        },
        error: function (data) {
            try {
                layer.msg("请求异常");
                return false;
            } catch (e) {
                console.log(e);
            }
        }
    });

}

function initConfigRodia(){
    $.ajax({
        url: ctxAppSec + "/api/WeixinConfig/showInfoList",
        data: {"Authorization":$.cookie('bearcktkaeskey')},
        type: "GET",
        dataType: "json",
        async: false,
        success: function (data) {
            console.log(data);
            
            var html = "";

            var datas = data.list;

            for (var i = 0; i < datas.length; i++) {
                console.log(datas[i]);
                html += '<option value=' + datas[i].cfgValue + '>' + datas[i].cfgName + '</option>';
            }

            console.log("html======" + html);
            $("#" + obj).html(html);

            if (typeof obj != "undefined" && obj != null) {
                $("#" + titleElementId).val(obj);
                //if($("#"+titleElementId)) $("#"+titleElementId).attr("value",$("#"+$this.attr("id")+" option:selected").text());
            }

        },
        error: function (data) {
            try {
                layer.msg("请求异常");
                return false;
            } catch (e) {
                console.log(e);
            }
        }
    });
}

//$.ajax({
//	  url: ctxAppSec+"/chick",
//	  async: false,
//	  function(data,status, xhr){
//		  console.log(xhr);
//	  }
//	})

//$.ajaxSetup({
//	beforeSend:function(XMLHttpRequest){
//		$.ajax();
//	}
//});

//$.ajaxSetup( {      
//    //设置ajax请求结束后的执行动作      
//    complete : function(XMLHttpRequest, textStatus) {  
//        // 通过XMLHttpRequest取得响应头，REDIRECT      
//        var redirect = XMLHttpRequest;//若HEADER中含有REDIRECT说明后端想重定向    
//        console.log(redirect);
//        if (redirect == "REDIRECT") { 
//        	console.log(111);
//            var win = window;      
//            while (win != win.top){    
//                win = win.top;    
//            }  
//            //将后端重定向的地址取出来,使用win.location.href去实现重定向的要求    
//            layerAlert(layer,"您的登录信息已超时，请重新登录<p class='ps'>ps:在客户端闲置10分钟后系统会自动登出</p>",'登录超时提示',5,'',0,function(){  
//                win.location.href= XMLHttpRequest.getResponseHeader("CONTEXTPATH");  
//            })  
//        }  
//    }
//});   