layui.use('layer', function() {
	var layer = layui.layer,
		$ = layui.$;

	//发送消息导航

	$('.tab_navs_wrp').on('click', '.tab_nav', function() {
		var _this = $(this);
		var data_tab = _this.attr('data-tab').substr(1);
		_this.siblings().removeClass('selected');
		_this.addClass('selected');
		$('.tab_panel').find('.tab_content').hide();
		$('.' + data_tab).parent('.tab_content').show();
	})
	
	//已有素材选择弹框
	$('.jsMsgSenderPopBt').on('click',function(){
		var data_type=$(this).attr('data-type');
		switch(data_type){//<!--type 10图文 2图片  3语音 15视频 11商品消息-->
			case '10':
				customopen("选择素材",2,"apptuwen.html");
			  	break;
			case '2':
				customopen("选择图片",2,"appimg.html");
			  	break;
			case '3':
				customopen("选择音频",2,"appaudio.html");
			  	break;
			case '15':
				customopen("选择视频",2,"appvideo.html");
			  	break;
		}
	})
	
	//替换素材
	$('.js_replace_media').on('click',function(){
		var data_type=$(this).attr('data-rel');
		switch(data_type){//<!--type 10图文 2图片  3语音 15视频 11商品消息-->
			case '10':
				customopen("选择素材",2,"apptuwen.html");
			  	break;
			case '2':
				customopen("选择图片",2,"appimg.html");
			  	break;
			case '3':
				customopen("选择音频",2,"appaudio.html");
			  	break;
			case '15':
				customopen("选择视频",2,"appvideo.html");
			  	break;
		}
	})
	
	
	
/*	$('.js_menu_name').on('blur',function(){
		
		var _val=$(this).val();
		if($.trim(_val)=='' ){
			$('.frm_msg.js_titlenoTips').show();
			$(this).val('').focus()
		}else{
			if(_val.length>8){
				$('.frm_msg.js_titleEorTips').show();
				_val=_val.slice(0,7);
				setTimeout(function(){$('.frm_msg.js_titleEorTips').hide()},800)
				$(this).val(_val)
			}
			
			$('.global_info').html(_val)
			if($(this).parents('.js_setNameBox').find('.js_menuTitle').html()=='菜单名称'){
				
				$('#menuList').find('.selected').find('.js_l1Title').html(_val)
			}else{
				$('#menuList').find('.selected').find('.js_l2Title').html(_val)
			}
			
			$('.frm_msg.js_titlenoTips').hide()
		}
		
		
	})*/

	
	//添加子菜单事件
	$('.js_addMenuBox').on('click',function(){
		
	})
	function customopen(title,type,content){
	  	layer.open({
	    	title :title,
			type : type,
			content:content,
			area : [ '800px' , '640px' ],
			/*end : function() {
				location.reload();
			}*/
	  	})
	}
	
	$('#menu-content').on('click','.jsmsgSenderDelBt',function(){
		$(this).parent().prev().show();
		$(this).parent().hide();
	})
	
	$('#menu-content').on('click', '.edit_mask', function() {
		$('.m_li_selected').removeClass('m_li_selected')
		$(this).parents('.appmsg').addClass('m_li_selected');
	})
	
	$(function () {
        
        $('body').on('click','#viewClose',function(){
        	layer.closeAll();
        })
    })

	$(document).ready(function() {
	//showMsg('1000358');
	})
	function showMsg(mid){
		
		

		$.ajax({
			type: 'get',
						url: 'datas/data_parts.js',
			/*'data': {
				offset: 1,
				num: 10,
			//	"Authorization": acekeystoken
			},*/
			'dataType': 'json',
			success: function(data) {
				
				var obj=data.list;
				var count = obj.length;
					if(count != 0) {
						var html = '';
								var list = obj[0];
									html+='<div id="">';
									html+='<div class="appmsg single has_first_cover" >';
									html+='<div class="appmsg_content">';
									html+='<div class="appmsg_info">';
									html+='	<em class="appmsg_date">更新于 '+list.updateDate+'</em>';
									html+='	</div>';
									html+='<div class="appmsg_item simple_card_media">';
									html+='<div class="card_appmsg">';
									html+='	<div class="card_appmsg_inner">';
									html+='<div class="weui-desktop-vm_primary card_appmsg_hd">';
									html+='<strong class="card_appmsg_title js_title">';
									html+='<a href="" target="_blank" >'+list.title+' </a> </strong>';
									html+='<div class="weui-desktop-vm_default card_appmsg_bd">';
									html+='<div class="card_appmsg_thumb" style="background-image:url('+list.headImg+');" ></div>';
									html+='	</div>';
									html+='</div>';
									html+='</div>';
									html+='<a href="" class="edit_mask preview_mask js_preview">';
									html+='	<div class="edit_mask_content">';
									html+='		<p class="">预览文章 </p>';
									html+='	</div>';
									html+='	<span class="vm_box"></span>';
									html+='	</a>';
									html+='	</div>';
									html+='	</div>';
									html+='	</div>';
									html+='	</div>';
									html+='	<a href="javascript:;" class="jsmsgSenderDelBt link_dele" data-type="10" onclick="return false;">删除</a>';
									html+='</div>';
									$('.jsMsgSendTab').next().remove();
									$('.jsMsgSendTab').after(html)
						}
					}
			
		});
		
	}
	
});