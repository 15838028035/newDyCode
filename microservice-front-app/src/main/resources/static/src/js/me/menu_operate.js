var authorizerAppid = getQueryString("authorizerAppid");
var acekeystoken = $.cookie('bearcktkaeskey');
var ref=/(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;
/**
 * 填充购票链接,链接后缀增加城市编号,影院编号
 *
 */
 function checkUrl(obj) { 
	 console.log(obj);
     if (obj.checked == true) {
		url="http://h5.dadicinema.com/wapportal/wechat/buy.do?cityid={cityid}&cinemaid={cinemaid}";
    	$("#url").val(url);
     }else{
    		 var url=$("#url").val("");
     }
 }
 
 


layui.use(['layer', 'table'], function() {
	var layer = layui.layer;
	var $ = layui.$;
	var table = layui.table;
	$("#autoUrl").hide();//
	$("#autoUrlBox").hide();
	//-type 10图文 2图片  3语音 15视频 11商品消息-->
	var dataObj=[{
			text: "图文消息",
			acl: "can_app_msg",
			className: "tab_appmsg",
			selector: "js_appmsgArea",
			type: 10
		}, {
			text: "文字",
			acl: "can_text_msg",
			className: "tab_text",
			selector: "js_textArea",
			innerClassName: "no_extra",
			type: 1
		}, {
			text: "图片",
			acl: "can_image_msg",
			className: "tab_img",
			selector: "js_imgArea",
			type: 2
		}, {
			text: "语音",
			acl: "can_voice_msg",
			className: "tab_audio",
			selector: "js_audioArea",
			type: 3
		}, {
			text: "视频",
			acl: "can_video_msg",
			className: "tab_video",
			selector: "js_videoArea",
			type: 15
		}];
		
	var info= null;
	var menu = null,
		responseData = null,
		responselist = {};
		$(document).ready(function() {
			
			getWxMenuData();
		});
	//wxMenuInit();

	function getWxMenuData() {
		$.ajax({
			type: 'get',
			dataType: 'JSON',
			url: ctxAppWeixin + "/wechat/menu/getlast?authorizerAppid="+authorizerAppid,
			success: function(result) {
				
				//if(true) {
				console.log(result)
				//menu = result.data && result.data.button ? result.data.button : [];
				//menu = result.selfMenuInfo && result.selfMenuInfo.buttons ? result.selfMenuInfo.buttons : [];
				menu = result  ? result : [];
				
				console.log(menu)
				responseData = result.selfMenuInfo && result.selfMenuInfo.msgs ? result.selfMenuInfo.msgs : [];
				$.each(responseData, function(_, v) {
					//                            responselist[v.id]=v.title;
					responselist[v.id] = {
						title: v.title,
						msgType: v.type
					}
				});
				wxMenuInit();
				//}
			}

		});
	}

	function wxMenuInit() {
		String.prototype.subByte = function(start, bytes) {
			for(var i = start; bytes > 0; i++) {
				var code = this.charCodeAt(i);
				bytes -= code < 256 ? 1 : 2;
			}
			return this.slice(start, i + bytes)
		};
		var getLength = function(str) {  
	        var realLength = 0, len = str.length, charCode = -1;  
	        for (var i = 0; i < len; i++) {  
	            charCode = str.charCodeAt(i);  
	            if (charCode >= 0 && charCode <= 128)  
	                realLength += 1;  
	            else  
	                realLength += 2;  
	        }  
	        return realLength;  
	    }; 
		var init_menu = function(menu) {
			var str = "";
			var items = menu; //
			var type = "",
				action = "",
				msgType = "";
			for(i in items) {
				 //子菜单items对象
				if(items[i]['subButtons'].length>0) {
					//alert(items[i]['type']);
					//type = action = "";
					//type = items[i]['type'];
					type='0';
					action="",
					msgtype="";
				} else {
					type = items[i]['type'];
					/*if(items[i].act_list != undefined)
						action = "key|" + items[i].act_list[0].type+"|"+items[i].act_list[0].value;
					if(items[i].act_list != undefined && items[i].act_list[0].type=='1')	
					action = "url|" + items[i].act_list[0].value;*/
					msgtype="";
					if(items[i].act_list.length>0){
						if(items[i].act_list[0].type != '1'){
							action = "key|" + items[i].act_list[0].type+"|"+items[i].act_list[0].value;
						}else{
							action = "url|" + items[i].act_list[0].value;
						}
					}
					/*if(items[i]['url'] != undefined)
						action = "url|" + items[i]['url'];*/
					/*if(items[i]['msgId'] != undefined)
						action = "key|" + items[i]['msgId'];*/
					/*if(items[i]['url'] != undefined)
						action = "url|" + items[i]['url'];
					if(items[i]['msgId'] != undefined)
						action = "key|" + items[i]['msgId'];
					if(items[i]['msgType'] != undefined)
						msgType = items[i]['msgType'];*/
				}
				str += '<li id="menu-' + i + '" class="menu-item" data-type="' + type + '" data-msgtype="' + msgType + '" data-action="' + action + '" data-name="' + items[i]['name'] + '"> <a href="javascript:;" class="menu-link"> <i class="icon-menu-dot"></i> <i class="weixin-icon sort-gray"></i> <span class="title">' + items[i]['name'] + '</span> </a>';
				var tem = '';
				
				if(items[i]['subButtons'].length>0) {
					var sub_menu = items[i]['subButtons'];
					for(j in sub_menu) {
						type = sub_menu[j]['type'];
						/*if(sub_menu[j].act_list != undefined && sub_menu[j].act_list[0].type!=1)
							action = "key|" + sub_menu[j].act_list[0].type+'|'+sub_menu[j].act_list[0].value;
						if(sub_menu[j].act_list != undefined && sub_menu[j].act_list[0].type==1){
							action = "url|" + sub_menu[j].act_list[0].value;
						}*/
						if(sub_menu[j].act_list.length>0 && sub_menu[j].act_list[0].type!=1)
							action = "key|" + sub_menu[j].act_list[0].type+'|'+sub_menu[j].act_list[0].value;
						if(sub_menu[j].act_list.length>0 && sub_menu[j].act_list[0].type==1){
							action = "url|" + sub_menu[j].act_list[0].value;
						}
							
						
						/*if(sub_menu[j]['url'] != undefined)
							action = "url|" + sub_menu[j]['url'];*/
						/*if(sub_menu[j]['url'] != undefined)
							action = "url|" + sub_menu[j]['url'];
						if(sub_menu[j]['key'] != undefined)
							action = "key|" + sub_menu[j]['msgId'];
						if(sub_menu[j]['msgType'] != undefined)
							msgType = sub_menu[j]['msgType'];*/
						tem += '<li id="sub-menu-' + j + '" class="sub-menu-item" data-type="' + type + '" data-msgtype="' + msgType + '" data-action="' + action + '" data-name="' + sub_menu[j]['name'] + '"> <a href="javascript:;"> <i class="weixin-icon sort-gray"></i><span class="sub-title">' + sub_menu[j]['name'] + '</span></a> </li>';
					}
				}
				str += '<div class="sub-menu-box" style="' + (i != 0 ? 'display:none;' : '') + '"> <ul class="sub-menu-list">' + tem + '<li class=" add-sub-item"><a href="javascript:;" title="添加子菜单"><span class=" "><i class="weixin-icon add-gray"></i></span></a></li> </ul> <i class="arrow arrow-out"></i> <i class="arrow arrow-in"></i></div>';
				str += '</li>';
			}
			$("#add-item").before(str);
			
		};
		var preview_menu = function(menu) {
		var str = "";
		var items = menu;
		for(i in items) {
			k = items.length
			var classN = 'pre_menu_item grid_item size1of' + k + ' jsViewLi ';
			if(i == items.length - 1) {
				classN = 'pre_menu_item grid_item size1of' + k + ' jsViewLi no_extra_flex';
			}
			if(items[i]['subButtons'] != undefined) {
				str += '<li class="' + classN + '" id="menu_' + i + '"><a href="javascript:void(0);" class="jsView pre_menu_link" title="' + items[i].name + '" draggable="false">	<i class="icon_menu_dot"></i>' + items[i].name + '	</a>';
			} else {
				str += '<li class="' + classN + '" id="menu_' + i + '"><a href="javascript:void(0);" class="jsView pre_menu_link" title="' + items[i].name + '" draggable="false">	' + items[i].name + '	</a>';
			}
			var tem = '';
			if(items[i]['subButtons'] != undefined) {
				var sub_menu = items[i]['subButtons'];
				for(j in sub_menu) {
					tem += '<li id="subMenu_menu_' + i + '_' + j + '"><a href="javascript:void(0);" class="jsSubView" title="' + sub_menu[j].name + '" draggable="false">' + sub_menu[j].name + '</a></li>';
				}
				str += '<div class="sub_pre_menu_box jsSubViewDiv" style="display:none"><ul class="sub_pre_menu_list">' + tem + '<i class="arrow arrow_out"></i><i class="arrow arrow_in"></i></ul></div>';
			}
			str += '</li>'
		}
		$('#viewList').empty().html(str);
	};
		var refresh_type = function() {
			$('.msg_sender').removeClass('error')
			$('#js_errTips').hide();
			if($('input[name=type]:checked').val() == 'view') {
				var url=$("#url").val();
				$("#autoUrl").show();
				$("#autoUrlBox").show();
				if(url.indexOf("{cityid}")>0){
					$("#autoUrlBox").prop("checked","checked");
				}else{
					$("#autoUrlBox").prop("checked",false);
				}
				$(".is-view").show();
				$(".is-click").hide();
			} else {
				$("#autoUrl").hide();
				$("#autoUrlBox").hide();
				$(".is-view").hide();
				$(".is-click").show();
			}
		};
		//初始化菜单
		init_menu(menu);
		//拖动排序
		new Sortable($("#menu-list")[0], {
			draggable: 'li.menu-item'
		});
		$(".sub-menu-list").each(function() {
			new Sortable(this, {
				draggable: 'li.sub-menu-item'
			});
		});
		//添加主菜单
		$("#menu-container").on('click', '#add-item', function() {
			$("#autoUrl").hide();
			$("#autoUrlBox").hide();
			$("#autoUrlBox").attr("checked",false);
			var menu_item_total = $(".menu-item").size();
			if(menu_item_total < 3) {
				var item = '<li class="menu-item" data-type="0" data-action="key|10|" data-name="添加菜单" > <a href="javascript:;" class="menu-link"> <i class="icon-menu-dot"></i> <i class="weixin-icon sort-gray"></i> <span class="title">添加菜单</span> </a> <div class="sub-menu-box" style=""> <ul class="sub-menu-list"><li class=" add-sub-item"><a href="javascript:;" title="最多添加5个子菜单"><span class=" "><i class="weixin-icon add-gray"></i></span></a></li> </ul> <i class="arrow arrow-out"></i> <i class="arrow arrow-in"></i> </div></li>';
				var itemDom = $(item);
				itemDom.insertBefore(this);
				itemDom.trigger("click");
				$(".sub-menu-box", itemDom).show();
				new Sortable($(".sub-menu-list", itemDom)[0], {
					draggable: 'li.sub-menu-item'
				});
			}
		});
		
		$('#viewList').on('click','.jsViewLi',function(){
			if($(this).children('.jsSubViewDiv')){
				$('.jsSubViewDiv').hide();
				$(this).children('.jsSubViewDiv').toggle()
			}
		})
		$("#menu-container").on('change', 'input[name=type]', function() {
			refresh_type();
		});
		$("#menu-container").on('click', '#item_delete', function() {
			var current = $("#menu-list li.current");
			var prev = current.prev("li[data-type]");
			var next = current.next("li[data-type]");
			var last;
			if(prev.size() == 0 && next.size() == 0 && $(".sub-menu-box", current).size() == 0) {
				last = current.closest(".menu-item");
				last.attr('data-action','key|10|');
			} else if(prev.size() > 0 || next.size() > 0) {
				last = prev.size() > 0 ? prev : next;
			} else {
				last = null;
				$(".weixin-content-wrap").hide();
				$(".no-weixin-content").show();
			}
			$("#menu-list li.current").remove();
			if(last) {
				last.trigger('click');
			} else {
				$("input[name='item-title']").val('');
			}
			updateChangeMenu();
		});

		function getMsgByIdAndType(mid, mtype) {
			var d = {};
			$.each(responseData, function(_, v) {
				if(v.id == mid && v.type == mtype) {
					d = v;
					return false;
				}
			});
			return d;
		}
		$('#url').on('blur',function(){
			var val=$(this).val();
			var reg=/(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;
			if(val==''){
				$('.menu_url').html('链接不能为空').show();
			}else if(!reg.test(val)){
				$('.menu_url').html('链接不是正确的url').show();
			}else{
				$('.menu_url').hide()
			}
		})
		$("input[name='item-title']").on('keydown',function(){
			if($('#current-item-option .is-sub-item').is(':hidden')){
				if($(this).val()==''){
					$('.frm_msg').hide();
					$('.js_titlenoTips').html('请输入子菜单名称').show()
				}else if(getLength($.trim($(this).val()))>8){
					$('.frm_msg').hide();
					$('.js_titleEorTips').show() 
				}else{
					$('.frm_msg').hide();
				}
			}else{
				if($(this).val()==''){
					$('.frm_msg').hide();
					$('.js_titlenoTips').html('请输入菜单名称').show()
				}else if(getLength($.trim($(this).val()))>16){
					$('.frm_msg').hide();
					$('.js_titleEorTips').show() 
				}else{
					$('.frm_msg').hide();
				}
			}
		})

		//更新修改与变动
		var updateChangeMenu = function() {
			var title = $("input[name='item-title']").val();
			if($('#current-item-option .is-sub-item').is(':hidden')){
				if($.trim(title)==''){
					return false;
				}else if(getLength($.trim(title))>8){
					$('.frm_msg').hide();
					title=$.trim(title).subByte(0,8);
				}else{
					$('.frm_msg').hide();
				}
			}else{
				if($.trim(title)==''){
					$('.frm_msg').hide();
					$('.js_titlenoTips').html('请输入子菜单名称').show()
					return false;
				}else if(getLength($.trim(title))>16){
					$('.frm_msg').hide();
					title=$.trim(title).subByte(0,16);
				}else{
					$('.frm_msg').hide();
				}
			}
			var type = $("input[name='type']:checked").val();
			var key = "",
				value,value1,value2;
			if(type == 'view') {//链接
				key = 'url';
				$("#autoUrl").show();
				$("#autoUrlBox").show();
				value = $("input[name='" + key + "']").val();
			} else {//其他四种
				key = 'key';
				$("#url").val("");
				$("#autoUrl").hide();
				$("#autoUrlBox").hide();
				value1 = $("input[name='" + key + "']").val();
				value2 = $("input[name='" + key + "']").attr('to_id');
			}
			
			//var msgtype = $("input[name='" + key + "']").attr("data-msgtype");
			//if(key == 'key') {
//				var msg = getMsgByIdAndType(value, msgtype);
//				var keytitle = msg.title ? msg.title : '无标题';
//				var cont = $(".is-click .create-click:first");
//				$(".keytitle", cont).remove();
//				cont.append('<div class="keytitle">资源名:' + keytitle + '</div>');
		//	}
			var currentItem = $("#menu-list li.current");
			if(currentItem.size() > 0) {
				if(type=="view"){
					var reg=/(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;
					if(value==''){
						$('.menu_url').html('链接不能为空').show();
						return false;
					}else if(!reg.test(value)){
						$('.menu_url').html('链接不是正确的url').show();
						return false;
					}
					currentItem.attr('data-type', 1);
					
					currentItem.attr('data-action', "url|" + value);
				}
				if(type=="click"){
					currentItem.attr('data-type', 1);
					if(value1 && value2)
					currentItem.attr('data-action', "key|" + value1+"|"+value2);
				}
				//currentItem.attr('data-type', type);
			//	currentItem.attr('data-msgtype', msgtype);
				//currentItem.attr('data-action', key + "|" + value);
				if(currentItem.siblings().size() == 4) {
					$(".add-sub-item").show();
				} else if(false) {

				}
				currentItem.children("a").find("span").text(title.subByte(0, 16));
				$("input[name='item-title']").val(title);
				currentItem.attr('data-name', title);
				$('#current-item-name').text(title);
			}
			// menuUpdate();
		};
		//更新菜单数据
		var menuUpdate = function() {
			//TODO:操作中保存菜单数据（暂时废弃）
			$.ajax({
				url: "",
				data: {
					menu: JSON.stringify(getMenuList())
				},
				success: function(result) {
					if(!result.success) {
						layer.msg("保存更改异常");
					}
				}
			});
		};
		//获取菜单数据
		var getMenuList = function() {
			var menus = new Array();
			var sub_button = new Array();
			var menu_i = 0;
			var sub_menu_i = 0;
			var item;
			$("#menu-list li").each(function(i) {
				item = $(this);
				var name = item.attr('data-name');
				var type = item.attr('data-type');
				var action = item.attr('data-action');
				//var msgType = item.attr('data-msgtype');
				if(name != null) {
					var actions = action.split('|');
					if(item.hasClass('menu-item')) {
						sub_menu_i = 0;
						if(item.find('.sub-menu-item').size() > 0) {
							menus[menu_i] = {
								"name": name,
								"type":type,
								"act_list":[],
								"subButtons": "subButtons"
							}
						} else {
							if(actions[0] == 'url')
								menus[menu_i] = {
									"name": name,
									"type": type,
									"act_list":[{
										"type":1,
										"value":actions[1]
										}],
										"subButtons":[]
								};
							else
								menus[menu_i] = {
									"name": name,
									"type": type,
									"act_list":[{
										"type":actions[1],
										"value":actions[2]
									}],"subButtons":[]
									/*"msgId": actions[1],
									"msgType": msgType*/
								};
						}
						if(menu_i > 0) {
							if(menus[menu_i - 1]['subButtons'] == "subButtons")
								menus[menu_i - 1]['subButtons'] = sub_button;
							else
								menus[menu_i - 1]['subButtons'];
						}
						sub_button = new Array();
						menu_i++;
					} else {
						if(actions[0] == 'url')
							sub_button[sub_menu_i++] = {
								"name": name,
								"type": type,
								"act_list":[{
										"type":1,
										"value":actions[1]
										}],
										"subButtons":[]
							};
						else
							sub_button[sub_menu_i++] = {
								"name": name,
								"type": type,
								"act_list":[{
										"type":actions[1],
										"value":actions[2]
									}],
								"subButtons":[]
								/*"msgId": actions[1],
								"msgType": msgType*/
								
							};
					}
				}
			});
			if(sub_button.length > 0) {
				var len = menus.length;
				menus[len - 1]['subButtons'] = sub_button;
			}
			return menus;
		};
		//添加子菜单
		$("#menu-container").on('click', ".add-sub-item", function() {
			$(this).parents('.menu-item').attr('data-action','');
			$("#autoUrl").hide();
			$("#autoUrlBox").hide();
			var sub_menu_item_total = $(this).parent().find(".sub-menu-item").size();
			if(sub_menu_item_total < 5) {
				var item = '<li class="sub-menu-item" data-type="1" data-action="key|10|" data-name="添加子菜单"><a href="javascript:;"><span class=" "><i class="weixin-icon sort-gray"></i><span class="sub-title">添加子菜单</span></span></a></li>';
				var itemDom = $(item);
				itemDom.insertBefore(this);
				itemDom.trigger("click");
				$('.tab_content').hide();
				$('.js_appmsgArea').parents('.tab_content').show()
				if(sub_menu_item_total == 4) {
					$(this).hide();
				}
			}
			return false;
		});
		//显示图文方法
		function showTuwen(theid){
			$.ajax({
				    	type:"get",
				    	url:ctxAppSec+"/api/WeixinImgtextItem/showInfoList?imgTextId="+theid+"&Authorization="+acekeystoken,
				    	dataType:"json",
				    	success:function(data){
				    		var data1=data.list[0]
				    		var cover=data1.headImg;
				    		var title=data1.title;
				    		var time=data1.updateDate;
				    		$('#msgSender_media_16_10').find('.appmsg_date').html('更新于'+time)
				    		//$('#msgSender_media_16_10').find('.preview_mask').attr('href',privew.html?id=1000927)
				    		$('#msgSender_media_16_10').find('.card_appmsg_title').children('a').html(title)
				    		$('#msgSender_media_16_10').find('.card_appmsg_thumb').css('background-image','url('+cover+')')
				    		$('#msgSender_media_16_10').show()
				    		$('.js_appmsgArea').children('.tab_cont_cover').hide()
				    	}
				    	
				    });
		}
		//显示图片方法
		function showImg(theid){
			$.ajax({
		    	type:"get",
		    	url:ctxAppSec+"/api/WeixinImg/showInfo/"+theid+"?Authorization="+acekeystoken,
		    	dataType:"json",
		    	success:function(data){
		    		var data1=data.weixinImg;
		    		var imgSrc=data1.headImg;
		    		$('#msgSender_media_2_2').find('.preview_img').attr('src',imgSrc)
		    		$('#msgSender_media_2_2').show()
		    		$('.js_imgArea').children('.tab_cont_cover').hide()
		    	}
		    	
		    });
		}
		//显示音频方法
		function showAudio(theid){  
			$.ajax({
		    	type:"get",
		    	url:ctxAppSec+"/api/WeixinAudio/showInfo/"+theid+"?Authorization="+acekeystoken,
		    	dataType:"json",
		    	success:function(data){
		    		var data1=data.weixinAudio;
		    		var time=data1.fileSize?data1.fileSize:'未知';
		    		var title=data1.title;
		    		$('#msgSender_media_7_3').find('.preview_audio_title').html(title)
		    		$('#msgSender_media_7_3').find('.preview_audio_desc').html(time)
		    		$('#msgSender_media_7_3').show()
		    		$('.js_audioArea').children('.tab_cont_cover').hide()
		    	}
		    	
		    });
		}
	//显示视频方法
		function showVideo(theid){
			$.ajax({
		    	type:"get",
		    	url:ctxAppSec+"/api/WeixinVideo/showInfo/"+theid+"?Authorization="+acekeystoken,
		    	dataType:"json",
		    	success:function(data){
		    		var data1=data.weixinVideo;
		    		//var cover=data1.headImg;
		    		var title=data1.title;
		    		var time=data1.fileSize;
		    		//$('#msgSender_media_16_15').find('.preview_video_hd').attr(src,'更新于'+time)
		    		$('#msgSender_media_16_15').find('.preview_video_length').html(time)
		    		$('#msgSender_media_16_15').find('.preview_video_title').html(title)
		    		$('#msgSender_media_16_15').show()
		    		$('.js_videoArea').children('.tab_cont_cover').hide()
		    	}
		    	
		    });
		}
	
		//主菜单子菜单点击事件
		$("#menu-container").on('click', ".menu-item, .sub-menu-item", function() {
			$('.msg_sender').removeClass('error')
			$('#js_errTips').hide();
			$('.frm_msg').hide()
			if($(this).hasClass("sub-menu-item")) {
				$('.js_titleNolTips').html('字数不超过8个汉字或16个字母').show()
				$("#menu-list li").removeClass('current');
				$(".is-item").show();
				$(".is-sub-item").show();
			} else {
				$('.js_titleNolTips').html('字数不超过4个汉字或8个字母').show()
				$("#menu-list li").removeClass('current');
				$("#menu-list > li").not(this).find(".sub-menu-box").hide();
				$(".sub-menu-box", this).toggle();
				//如果当前还没有子菜单
				if($(".sub-menu-item", this).size() == 0) {
					$(".is-item").show();
					$(".is-sub-item").show();
				} else {
					$(".is-item").hide();
					$(".is-sub-item").hide();
				}
			}
			
			$(this).addClass('current');
			var type = $(this).attr('data-type');
			var action = $(this).attr('data-action');
			var title = $(this).attr('data-name');
			var msgtype = $(this).attr('data-msgtype');

			var actions = action.split('|');
			var click_val=(type==0?'url':'click');
			$("input[name=type][value='" + click_val + "']").prop("checked", true);
			$("input[name='item-title']").val(title);
			$('#current-item-name').text(title);
			
			if(actions[0] == 'url') {
				$('input[name=key]').val('');
			} else {
				$('input[name=url]').val('');
				$('input[name=url]').attr('to_id','');
			}
			$("input[name='" + actions[0] + "']").val(actions[1]);
			if(actions[0] == 'key') {
				//10图文 2图片  3语音 15视频
				$('input[name=url]').attr('to_id',actions[2]);
				switch(actions[1])	{
					case '10':
					
					$(".tab_navs_wrp").find('.selected').removeClass('selected')
					$("li[data-tab='.js_appmsgArea']").addClass('selected')
					$('.tab_content').hide()
					if(actions[2]!=''){
						$('.tab_content').hide()
					    $('.js_appmsgArea').parent().show()
					    showTuwen(actions[2])
					    
					}else{
						$('.js_appmsgArea').parent('.tab_content').show()
						$('.js_appmsgArea').find('.tab_cont_cover').show()
						$('#msgSender_media_16_10').hide();
					   // $('.js_appmsgArea').parent().hide()
					}
					break;
					
					case '2':
					
					$(".tab_navs_wrp").find('.selected').removeClass('selected')
					$("li[data-tab='.js_imgArea']").addClass('selected')
					if(actions[2]!=''){
						$('.tab_content').hide()
					    $('.js_imgArea').parent().show()
					    showImg(actions[2])
					}else{
						$('.js_imgArea').parent('.tab_content').show()
						$('.js_imgArea').find('.tab_cont_cover').show()
					    $('#msgSender_media_2_2').hide()
					}
					
					break;
					case '3':
					
					$(".tab_navs_wrp").find('.selected').removeClass('selected')
					$("li[data-tab='.js_audioArea']").addClass('selected')
					if(actions[2]!=''){
						$('.tab_content').hide()
					    $('.js_audioArea').parent().show()
					     showAudio(actions[2])
					}else{
						$('.js_audioArea').parent('.tab_content').show()
					    $('.js_audioArea').find('.tab_cont_cover').hide()
					    $('#msgSender_media_7_3').hide();
					}
					
					break;
					case '15':
					
					$(".tab_navs_wrp").find('.selected').removeClass('selected')
					$("li[data-tab='.js_videoArea']").addClass('selected')
					
					if(actions[2]!=''){
						$('.tab_content').hide()
					    $('.js_videoArea').parent().show()
					    showVideo(actions[2])
					}else{
						
						$('.js_videoArea').parent('.tab_content').show()
					    $('.js_videoArea').find('.tab_cont_cover').hide()
					    $('#msgSender_media_16_15').hide();
					}
					break;
					
					
				}
			} 
			if(actions[0] == 'url'){
				$('input[name=type][value=view]').trigger('click')
			}

			$(".weixin-content-wrap").show();
			$(".no-weixin-content").hide();

			refresh_type();

			return false;
		});
		function validateMenu(){
			$('.menu-list li').each(function(){
				var _this=$(this);
				if(_this.attr('class').indexOf('add')==-1){
					if(_this.attr('data-action')!=''){
						var action=_this.attr('data-action');
						var _arr=action.split('|')
						if(_arr.length==2){
							var reg=/(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;
							if(!reg.test(_arr[1])){
								$('.sub-menu-box').hide()
								_this.parents('.sub-menu-box').show()
								_this.trigger('click')
								$('.msg_sender').addClass('error')
								$('#js_errTips').show();
								return false;
							}
						}else{
							if(_arr[1]!='' && _arr[2]!='' ){
								//alert('正常')
							}else{
								//alert('异常')
								$('.sub-menu-box').hide()
								_this.parents('.sub-menu-box').show()
								
								_this.trigger('click')
								$('.msg_sender').addClass('error')
								$('#js_errTips').show();
								return false;
							}
						}
					
						
					}
				}
				
			})
			
			var menus = getMenuList();
			console.log(menus)
		}
		$("form#form-item").on('change', "input,textarea", function() {
			updateChangeMenu();
		});
		$("#menu-container").on('click', "#menuSave", function() {
			validateMenu()
			var menus = getMenuList();
			console.log(menus)
			//return false;
			$.ajax({
				url: ctxAppWeixin+'/wechat/menu/create?authorizerAppid='+authorizerAppid,
                type: "post",
				data: JSON.stringify(menus),
                dataType: "json",
                contentType: "application/json; charset=utf-8",
				success: function(result) {
					//{"success":true,"msg":"信息操作成功!","data":null,"page":null}
					if(result.respCode == 0) {
                        layer.msg("菜单保存成功");
					} else {
						layer.msg("菜单保存失败");
					}
				}
			});
		});
		
		$("#preview").click(function () {
        //	console.log($("#mobileDiv").html())
        	var preview_data=getMenuList();
        	preview_menu(preview_data);
            layer.open({
                type: 1,
                closeBtn: true,
                shift: 2,
                shadeClose: false,
                content: $("#mobileDiv"),
                
            });
        })
		
		
		$("#menu-container").on('click', "#menuSyn", function() {
			$.ajax({
				url: '/wxapi/doPublishMenu',
				data: {},
				success: function(result) {
					//{"success":true,"msg":"信息操作成功!","data":null,"page":null}
					if(result.success) {
						showAlert("菜单同步更新成功，生效时间看微信官网说明，或者重新关注微信号！");
					} else {
						layer.msg("菜单同步失败");
					}
				}
			});
		});
		var refreshkey = function(data) {
			$("input[name=key]").val(data.id).attr("data-msgtype", data.type).trigger("change");
			layer.closeAll();
		};

		
		function getresponseDataBytype(type) {
			var data = [];
			$.each(responseData, function(_, v) {
				if(v.type == type) {
					data.push(v);
				}
			});
			return data;
		}
	}
	
	$("#autoUrl").click(function(){
		 if ($("#autoUrlBox").is(':checked')) {
			 	console.log("选中----------没选中");
			 	$("#url").val("");
			 	$("#autoUrlBox").prop("checked",false);
	     }else{
	    	 	console.log("没选中---------选中");
	    	 	$("#autoUrlBox").prop("checked","checked");
	    		url="http://h5.dadicinema.com/wapportal/wechat/buy.do?cityid={cityid}&cinemaid={cinemaid}";
		    	$("#url").val(url);
	     }
	})
	
});