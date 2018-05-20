// JavaScript Document
// 用于存放一些通用变量
var baseDomain = window.location.protocol +"//"+ window.location.host;
var domain = baseDomain+"/admin/";
var adminDomainUrl = baseDomain+"/admin/api/";
// 图片服务器地址
var imgUploadDomain = "http://10.0.0.8/common/img/upload/appdir2/";
var loginHtmlPage = domain + "static/login.html";
var indexHtmlPage = domain + "static/pages/index.html";

// APP客户端接口 ======================================================
// var appDomainUrl = baseDomain+"/api";
var appDomainUrl = "http://10.0.0.7/api";
var getNewsListUrl = appDomainUrl + "/m/news/list/v2";
var getDistrictUrl = appDomainUrl + "/data/list/district";
var getAreaUrl = appDomainUrl + "/data/list/area";
var getTowardUrl = appDomainUrl +  "/data/list/toward";
var getTagsUrl = appDomainUrl + "/data/list/tags";
// 获取邻里的社区生活列表
var getExpertLifeListUrl = appDomainUrl + "/m/expert/community/life/list/v2";
// 获取邻里的社区
var getExpertCommunityUrl = appDomainUrl + "/em/e-community/v2";
// 获取邻里的社区介绍列表
var getExpertCommunityInfoUrl = appDomainUrl + "/em/e-community/info/list/v2";

// 默认logo图片地址
var defaultImgUrl = 'imgs/logo.jpg';

$(function() {
	/**
	 * 用一个全局的方法来处理，session超时要跳转的页面
	 * (类似拦截器的概念)
	 */
	$.ajaxSetup({
		contentType:"application/x-www-form-urlencoded;charset=utf-8",
		complete:function(XMLHttpRequest,textStatus){
			var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); //通过XMLHttpRequest取得响应头，sessionstatus，
			// alert(sessionstatus);
			if(sessionstatus=="timeout"){
				layer.msg("登录超时,请重新登录！");
				//如果超时就处理 ，指定要跳转的页面
				location.href=domain + "static/login.html";
			}
		}
	});
	/** 获取当前页码 */
	$.getCurrentPage = function (currentPage, $selectpage) {
		if(typeof(currentPage)=="undefined") return 1;
		if(currentPage>0) return currentPage;
		if(currentPage==-1) { // GO
			var gotoPageNumValue = $selectpage.find('input[name="gotoPageNum"]').val();
			if(gotoPageNumValue=="" || gotoPageNumValue<1) gotoPageNumValue=1;
			console.log("gotoPageNum:"+gotoPageNumValue);
			return gotoPageNumValue;
		}
		return 1;
	};
	/** 设置页面翻页工具条内容 */
	$.setSelectpage = function ($selectpage, pageInfo, callback){
        $selectpage.empty();
		if(pageInfo.navigatepageNums.length==0) return;
		// 左侧信息
		var divLeftElArr = [];
		divLeftElArr.push('<div class="col-md-3">');
		divLeftElArr.push('<div class="dataTables_info">');
		divLeftElArr.push('共&nbsp;'+pageInfo.total+'&nbsp;条记录，&nbsp;'+pageInfo.totalPage+'&nbsp;页');
		divLeftElArr.push('</div>');
		divLeftElArr.push('</div>');
		// 中间跳转按钮
		var centreElArr = [];
		centreElArr.push('<div class="col-md-3">');
		centreElArr.push('<div class="input-group">');
		centreElArr.push('<input type="number" class="form-control" placeholder="当前第'+pageInfo.currentPage+'页" name="gotoPageNum" value="">');
		centreElArr.push('<span class="input-group-btn">');
		centreElArr.push('<button class="btn btn-default go_button" type="button" onclick="'+callback+'(-1)">GO！</button>');
		centreElArr.push('</span>');
		centreElArr.push('</div>');
		centreElArr.push('</div>');
		// 右侧按钮
		var navPrevElStr = '';
		var navNextElStr = '';
		if(pageInfo.hasPreviousPage==false) {
            navPrevElStr = '<li class="paginate_button disabled"><a href="javascript:void(0)">上一页</a></li>';
		}else {
            navPrevElStr = '<li class="paginate_button"><a href="javascript:void(0)" onclick="'+callback+'('+(pageInfo.currentPage-1)+')">上一页</a></li>';
		}
		if(pageInfo.hasNextPage==false) {
            navNextElStr = '<li class="paginate_button disabled"><a href="javascript:void(0)">下一页</a></li>';
		}else {
            navNextElStr = '<li class="paginate_button"><a href="javascript:void(0)" onclick="'+callback+'('+(pageInfo.currentPage+1)+')">下一页</a></li>';
		}
		var navNumsElArr = [];
		$.each(pageInfo.navigatepageNums, function (index, item) {
			if(pageInfo.currentPage==item) {
				navNumsElArr.push('<li class="paginate_button active"><a href="javascript:void(0)">'+item+'</a></li>');
			}else {
				navNumsElArr.push('<li class="paginate_button"><a href="javascript:void(0)" onclick="'+callback+'('+item+')">'+item+'</a></li>');
			}
		});
		var divRightElArr = [];
		divRightElArr.push('<div class="col-md-6">');
		divRightElArr.push('<div class="dataTables_paginate paging_simple_numbers">');
		divRightElArr.push('<ul class="pagination">');
		divRightElArr.push(navPrevElStr);
		divRightElArr.push(navNumsElArr.join(''));
		divRightElArr.push(navNextElStr);
		divRightElArr.push('</ul>');
		divRightElArr.push('</div>');
		divRightElArr.push('</div>');

		$selectpage.html(divLeftElArr.join('')+centreElArr.join('')+divRightElArr.join(''));
	};
	/** 设置导航栏 level-层级，activeLi-2级或2级以上的li对象 */
	$.setBreadcrumb = function(level, level2_text, dataKey, dataValue) {
		var side_menu_active_text = $("#side-menu a.gotopagefun.active").text();
		switch(level) {
			case 1:
				$(".breadcrumb").empty().append('<li class="active">'+side_menu_active_text+'</li>');
				break;
			case 2:
				if(dataKey) paramstr = 'data-'+dataKey+'='+dataValue;
				$(".breadcrumb").empty()
					.append('<li><a href="javascript:void(0);" onclick="$.loadSubPageWrapperSelf();">'+side_menu_active_text+'</a></li>')
					.append('<li class="active" '+paramstr+'>'+level2_text+'</li>');
				break;
			default:
				$(".breadcrumb").empty().append('<li class="active">'+side_menu_active_text+'</li>');
		};
	};
	/** 设置选中行的样式 */
	$.fn.setSelectTheLine = function() {
		$(this).addClass("zyc_table_selected")
			.siblings().removeClass("zyc_table_selected");
		return;
	};

	/**
	 * 获取区域
	 * obj - 要选中区域id值
	 * titleElementId - 需要设置的选中标题元素id
	 */
	$.fn.getDistrict = function(obj, titleElementId) {
		console.log("fn.getDistrict....."+obj+","+titleElementId);
		var $this = $(this);
		$.getJSON(getDistrictUrl+"?cityId=1", function(data){
			console.log("fn.getDistrict....."+data);
			$.each(data.dataList, function(index, item){
				$this.append("<option value='"+item.id+"'>"+item.name+"</option>");  //为Select追加一个Option(下拉项)
			});
			console.log("getDistrict obj:"+obj);
			if(typeof obj != "undefined" && obj != null){
				$this.val(obj);
				if($("#"+titleElementId)) $("#"+titleElementId).attr("value",$("#"+$this.attr("id")+" option:selected").text());
			}
		});
		return;
	};

	// 获取地段
	// obj - 要选中地段id值
	// titleElementId - 需要设置的选中标题元素id
	$.fn.getArea = function(districtId, obj, titleElementId) {
		console.log("fn.getArea....."+districtId+","+obj+","+titleElementId);
		var $this = $(this);
		$.getJSON(getAreaUrl+'?districtId='+districtId, function(data){
			$this.empty().append("<option value='0' selected='selected'>选择地段</option>");
			$.each(data.dataList, function(index, item){
				$this.append("<option value='"+item.id+"'>"+item.name+"</option>");  //为Select追加一个Option(下拉项)
			});
			if(typeof obj != "undefined" && obj != null){
				$this.val(obj);
				if($("#"+titleElementId)) $("#"+titleElementId).attr("value",$("#"+$this.attr("id")+" option:selected").text());
			}
		});
		return;
	};
	/** 获取朝向 */
	$.getToward = function(tableIdStr) {
		$.getJSON(getTowardUrl, function(data){
			$("#"+tableIdStr+" #towardId").empty();
			$.each(data.dataList, function(){
				$("#"+tableIdStr+" #towardId").append("<option value='"+this.id+"'>"+this.title+"</option>");  //为Select追加一个Option(下拉项)
			});
			$("#"+tableIdStr+" #towardTitle").attr("value", data.dataList[0].title);
		});
	};
	/** 获取房源标签 */
	$.getTags = function(type, tableId) {
		$.getJSON(getTagsUrl+'?type='+type, function(data){
			$("#"+tableId+" #tagsId_ul_id").empty();
			$.each(data.dataList, function(){
				var li=$("<li></li>");
				var input_checkbox = $("<input id='tag_id_"+this.id+"' type='checkbox' name='tagsId' value='"+this.id+"'/>");
				var label = $("<label id='tag_id_for_"+this.id+"' for='tag_id_"+this.id+"'>"+this.title+"</label>");

				li.append(input_checkbox);
				li.append(label);
				$("#"+tableId+" #tagsId_ul_id").append(li);
			});
		});
	};

	// 方法二： 获取url中的参数
	$.getUrlParam = function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	};
	// 方法二：
	//var xx = $.getUrlParam('reurl');
	// 方法一：
	// var xx = getUrlParam('reurl');
	//alert(xx);

	/** 清空表单数据 */
	$.fn.clearForm = function(){
		$(':input', this).not(':button, :submit, :reset, :hidden')
			.val('')
			.removeAttr('checked')
			.removeAttr('selected');
		return;
	};
	/** 模态框隐藏时，清空表单数据 */
	$.modalHideFormClear = function ($modal) {
		$modal.on('hide.bs.modal',function () {
			$modal.find('form').clearForm();
		});
	};
	/** 表单验证 */
	$.validateForm = (function ($form) {
		return $form.validate({
			// 更改错误信息显示的位置
			errorPlacement: function (error, element) {
				$(element).tips({
					msg: $(error).text()
				});
			}
		}).form();
	});
	/** 表单提交 */
	$.formPost = (function($form, url, $modal, callback, data) {
		loadingOpen();
		var options = {
			url: url,
			type:'post',
			dataType: 'json',
			success:function(result, status) {
				console.log(result.status+", "+status);
				loadingClose();
				if(result.status!=1) {
					alertRequestError(result.msg);
					return;
				}
				alertRequestSuccess(result.msg);
				$form.clearForm();
				if($modal) $modal.modal('hide'); // 当变量不存在时，定义它为 undefined
				// if(callback) callback();
				if(callback) callback(result); // 20170214 修改，可能会导致有些接口访问错误，出错时，修改此处
			},
			error:function(result) {
				loadingClose();
				alertRequestError('操作失败');
			}
		};
		if(data) options = $.extend({}, options, {data:data});
		$form.ajaxSubmit(options);
		return false;   //阻止表单默认提交
	});
	/** 上传图片 */
	$.uploadImg = (function($form, callback){
		loadingOpen();
		var options = {
			url: imgUploadDomain,
			type:'post',
			dataType: 'json',
			success:function(result, status) {
				loadingClose();
				if(result.status!=1) {
					alertRequestError('图片上传失败');
					return;
				}
				callback(result);
			},
			error:function(result) {
				loadingClose();
				alertRequestError('图片上传失败');
			}
		};
		$form.ajaxSubmit(options);
		return false;   //阻止表单默认提交
	});
	/** 封装 post 请求 */
	$.postJSON = (function (url, data, callback, callbakOptions) {
		$.ajax({
			url: url,
			type: "post",
			data:data,
			dataType:"json",
			error: function(data){
				loadingClose();
				alertRequestError("操作失败");
			},
			success:function(data){
				loadingClose();
				if(data.status!=1) {
					alertRequestError(data.msg);
					return;
				}
				if(callbakOptions) callback(data, callbakOptions);
				else if(callback) callback(data);
			}
		});
	});
	/** 初始化fileinput控件（第一次初始化） */
	$.initFileInput = (function ($ctrlName, callback) {
		// 加载fileinput ,文件上传
		$ctrlName.fileinput({
			language: 'zh', //设置语言
			maxFileCount: 3, // 最大上传文件个数
			uploadUrl: imgUploadDomain, //上传的地址
			allowedFileExtensions : ['jpg', 'png'],//接收的文件后缀
			showPreview: true, //是否显示预览按钮
			showUpload: true, //是否显示上传按钮
			showCaption: false,//是否显示标题
			browseClass: "btn btn-primary", //按钮样式
			previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
			msgFilesTooMany: "图片数量超出最大范围！",
			previewFileType:'any'
		}).on("fileuploaded", function(event, data){
			console.log("上传回调： "+data.response);
			console.log("上传回调 status： "+data.response.status);
			console.log("上传回调 dataList： "+data.response.dataList);
			if(data.response.status!=1) {
				alertRequestError('图片上传失败');
				return;
			}
			if(callback) callback(data.response);
		});
	});

});


// 方法一：
// 获取url中的参数
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
}
function getStrParam(str, name) {
	// str 必须是url中?之后的字符串
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = str.match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
}
/** 检查是否纯数字 */
function isDigit(str) {
	var reg = /^[0-9]{1,11}$/;
	return reg.test(str);
}
/** 操作失败 提示信息框 */
function alertRequestError(msg) {
	layer.msg(msg,{icon: 2,time:2000});
}
/** 操作成功 提示信息框 */
function alertRequestSuccess(msg) {
	layer.msg(msg,{icon: 1,time:2000});
}
/** 打开加载层 */
function loadingOpen() {
	layer.load();
}
/** 关闭加载层 */
function loadingClose() {layer.closeAll('loading');}