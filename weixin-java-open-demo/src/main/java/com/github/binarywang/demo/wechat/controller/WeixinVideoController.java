package com.github.binarywang.demo.wechat.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.binarywang.demo.wechat.service.WxOpenServiceDemo;
import com.github.binarywang.demo.wechat.service.util.StringUtil.DateUtil;
import com.lj.cloud.secrity.service.WeixinPushLogService;
import com.lj.cloud.secrity.service.WeixinUserinfoService;
import com.lj.cloud.secrity.service.WeixinVideoService;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.WxMpErrorMsg;
import com.weixindev.micro.serv.common.bean.weixin.WeixinPushLog;
import com.weixindev.micro.serv.common.bean.weixin.WeixinUserinfo;
import com.weixindev.micro.serv.common.bean.weixin.WeixinVideo;
import com.weixindev.micro.serv.common.util.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.WxMpMassPreviewMessage;
import me.chanjar.weixin.mp.bean.WxMpMassTagMessage;
import me.chanjar.weixin.mp.bean.WxMpMassVideo;
import me.chanjar.weixin.mp.bean.material.WxMpMaterial;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import me.chanjar.weixin.mp.bean.result.WxMpMassUploadResult;

@Api(value = "视频管理服务", tags = "视频管理服务")
@RestController
public class WeixinVideoController{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WxOpenServiceDemo wxOpenServiceDemo;

	@Autowired
	WeixinUserinfoService WeixinUserinfoService;

	@Autowired
	private WeixinVideoService weixinVideoService;
	
	@Autowired
	private WeixinPushLogService weixinPushLogService;

	@Value("${appURL}")
	private String appURL;// 网站前台url

	@Value("${file_location}")
	private String file_location;// 文件存储路径
	@Value("${ctxAppWeixin}")
	private String ctxAppWeixin;// 微信网站路径

	/**
	 * 1、首先，预先将图文消息中需要用到的图片，使用上传图文消息内图片接口，上传成功并获得图片 URL；
	 * 2、上传图文消息素材，需要用到图片时，请使用上一步获取的图片 URL； 3、使用对用户标签的群发，或对 OpenID
	 * 列表的群发，将图文消息群发出去，群发时微信会进行原创校验，并返回群发操作结果；
	 * 4、在上述过程中，如果需要，还可以预览图文消息、查询群发状态，或删除已群发的消息等。
	 */
	@ApiOperation(value = "群发")
	@RequestMapping(value = "/api/video/doExexcuteBatchSend", method = {RequestMethod.POST})
	public RestAPIResult2 doExexcuteBatchSend(@RequestParam Map<String, String> map) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("成功");

		String videoId = String.valueOf(map.get("videoId"));// 视频ID
		String ids = map.get("ids");// 选择的公众号

		List<String> idsList = StringUtil.splitStringToStringList(ids);

		logger.info("StringUtil.splitStringToStringList :" + idsList.size());

		logger.info("群发消息视频 videoId:" + videoId + ",公众号ids:" + ids);
		
		StringBuffer sb = new StringBuffer("");

		try {
			Integer id = Integer.parseInt(videoId);
			WeixinVideo weixinVideo = weixinVideoService.selectByPrimaryKey(id);

			if (weixinVideo == null || (weixinVideo != null && StringUtil.isBlank(weixinVideo.getHeadImg()))) {
				restAPIResult.setRespCode(0);
				restAPIResult.setRespMsg("视频不能为空,请上传视频");
				return restAPIResult;
			}

			String mediaId = "";

			// 动态判断mediaType
			String headImg = weixinVideo.getHeadImg();// 视频地址

			String fileType = StringUtil.getExtension(headImg);// 获得文件的扩展名

			URL fileUrl = new URL(weixinVideo.getHeadImg());// 地址
			
			// 群发
			for (String str : idsList) {
				
				if (StringUtils.isNotBlank(str)) {// 过滤授权状态为空的
					String appId = str;
					try{
						URLConnection rulConnection = fileUrl.openConnection();// 此处的urlConnection对象实际上是根据URL的
						HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
						InputStream inputStream = httpUrlConnection.getInputStream();
					// 上传视频video
					WxMediaUploadResult uploadMediaRes = wxOpenServiceDemo.getWxOpenComponentService()
							.getWxMpServiceByAppid(str).getMaterialService()
							.mediaUpload(WxConsts.MediaFileType.VIDEO, fileType, inputStream);
					
					mediaId=uploadMediaRes.getMediaId();
					logger.info("上传视频素材消息的mediaId=" + mediaId + ",fileType=" + fileType + ",headImg=" + headImg);

					// 把视频变成可被群发的媒体
					WxMpMassVideo video = new WxMpMassVideo();
					video.setTitle(weixinVideo.getTitle());
					video.setDescription(weixinVideo.getIntro());
					video.setMediaId(mediaId);
					WxMpMassUploadResult uploadResult = wxOpenServiceDemo.getWxOpenComponentService()
							.getWxMpServiceByAppid(appId).getMassMessageService().massVideoUpload(video);
					
					mediaId=uploadResult.getMediaId();
					
					logger.info("群发文件素材视频 type:" + uploadResult.getType() + ",mediaId=" + uploadResult.getMediaId());

					WxMpMassTagMessage WxMpMassTagMessage = new WxMpMassTagMessage();
					WxMpMassTagMessage.setMsgType("mpvideo");
					WxMpMassTagMessage.setMediaId(uploadResult.getMediaId());

					WxMpMassSendResult massResult = wxOpenServiceDemo.getWxOpenComponentService()
							.getWxMpServiceByAppid(appId).getMassMessageService()
							.massGroupMessageSend(WxMpMassTagMessage);

					logger.info("群发文件素材视频str= " + str + ",结果：" + massResult.getErrorCode() + ","
							+ massResult.getErrorMsg() + "," + massResult.getMsgId());
					
					WeixinUserinfo WeixinUserinfoFilter = WeixinUserinfoService.selectByauthorizerAppid(appId);
					WeixinPushLog weixinPushLog = new WeixinPushLog();
					weixinPushLog.setCategoryId("video");
					weixinPushLog.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					weixinPushLog.setCreateBy(1);
					weixinPushLog.setCreateByUname("admin01");
					weixinPushLog.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					weixinPushLog.setMsgId(massResult.getMsgId());
					weixinPushLog.setMsgDataId(massResult.getMsgDataId());
					weixinPushLog.setUserId(WeixinUserinfoFilter.getId());
					weixinPushLog.setAuthorizerAppid(appId);
					
					weixinPushLogService.insertSelective(weixinPushLog);
					}catch(WxErrorException e) {
						 e.printStackTrace();
				    		Integer code = e.getError().getErrorCode();
							logger.info(str + "群发异常:" +e.getMessage() + ",异常信息:"+ WxMpErrorMsg.findMsgByCode(code)+"<br/>");
							sb.append("异常信息:"+ WxMpErrorMsg.findMsgByCode(code)+"<br/>");
				    }catch(Exception e){
						sb.append("群发异常:" +e.getMessage()+"<br/>");
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("群发异常，异常信息:" + e.getMessage());
			restAPIResult.setRespCode(0);
			restAPIResult.setRespMsg("群发异常，异常信息:" + e.getMessage());
			sb.append("群发异常:" +e.getMessage()+"<br/>");
		}
		

		if(StringUtils.isNoneBlank(sb.toString())){
			restAPIResult.setRespCode(0);
			restAPIResult.setRespMsg(sb.toString());
		}

		return restAPIResult;
	}

	@ApiOperation(value = "同步")
	@RequestMapping(value = "/api/video/doExexcuteBatchSync", method = {RequestMethod.POST})
	public RestAPIResult2 doExexcuteBatchSync(@RequestParam Map<String, String> map) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("成功");

		
		String videoId = String.valueOf(map.get("videoId"));// 视频ID
		String ids = map.get("ids");// 选择的公众号
		
		List<String> idsList = StringUtil.splitStringToStringList(ids);
		
		logger.info("StringUtil.splitStringToStringList :" + idsList.size());

		logger.info("同步消息视频videoId:" + videoId + ",公众号ids:" + ids);

		Integer id = Integer.parseInt(videoId);
		WeixinVideo weixinVideo = weixinVideoService.selectByPrimaryKey(id);

		if (weixinVideo == null || (weixinVideo != null && StringUtil.isBlank(weixinVideo.getHeadImg()))) {
			restAPIResult.setRespCode(0);
			restAPIResult.setRespMsg("视频不能为空,请上传视频");
			return restAPIResult;
		}

		String mediaId = "";
		String fileType = "";
		// 刷新accessToken
		
		StringBuffer sb = new StringBuffer("");
		
		try {
			for (String str : idsList) {
				if (StringUtils.isNotBlank(str)) {// 过滤授权状态为空的
					try{
					// 动态判断mediaType
					String headImg = weixinVideo.getHeadImg();// 视频地址
					String headImgRepl = headImg.replaceFirst(appURL, file_location);

					fileType = StringUtil.getExtension(headImg);// 获得文件的扩展名

					String fileName = headImg.substring(headImg.lastIndexOf("/") + 1);
					File fileTmp = new File(headImgRepl);
					if(!fileTmp.exists()) {
						restAPIResult.setRespCode(0);
						restAPIResult.setRespMsg("视频不存在");
						return restAPIResult;
					}
					String mediaType = WxConsts.MaterialType.VIDEO;
					String videoTitle = weixinVideo.getTitle();
					// 上传视频video
					WxMpMaterial wxMpMaterial = new WxMpMaterial();
					wxMpMaterial.setFile(fileTmp);
					wxMpMaterial.setName(fileName);
					wxMpMaterial.setVideoTitle(videoTitle);

					WxMpMaterialUploadResult uploadMediaRes = wxOpenServiceDemo.getWxOpenComponentService()
							.getWxMpServiceByAppid(str).getMaterialService()
							.materialFileUpload(mediaType, wxMpMaterial);
					
					mediaId = uploadMediaRes.getMediaId();

					logger.info("上传视频消息的mediaId=" + mediaId + ",fileType=" + fileType + ",headImg=" + headImg);

					WeixinUserinfo WeixinUserinfoFilter = WeixinUserinfoService.selectByauthorizerAppid(str);
					
					WeixinPushLog weixinPushLog = new WeixinPushLog();
					weixinPushLog.setCategoryId("audio");//图文消息
					weixinPushLog.setMediaCategory("1");//永久素材
					weixinPushLog.setMediaId(uploadMediaRes.getMediaId());
					weixinPushLog.setImgTextId(weixinVideo.getId());
					weixinPushLog.setCreateBy(1);
					weixinPushLog.setCreateByUname("admin01");
					weixinPushLog.setUserId(WeixinUserinfoFilter.getId());
					weixinPushLog.setAuthorizerAppid(str);
					
					weixinPushLog.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					
					weixinPushLogService.insertSelective(weixinPushLog);
					}catch(WxErrorException e) {
						 e.printStackTrace();
				    		Integer code = e.getError().getErrorCode();
							logger.info(str + "同步异常:" +e.getMessage() + ",异常信息:"+ WxMpErrorMsg.findMsgByCode(code)+"<br/>");
							sb.append("同步异常:"+ WxMpErrorMsg.findMsgByCode(code)+"<br/>");
				    }catch(Exception e){
						sb.append("同步异常:" +e.getMessage()+"<br/>");
					}
					
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传视频消息，异常信息:" + e.getMessage());
			restAPIResult.setRespCode(0);
			restAPIResult.setRespMsg("上传异常，异常信息:" + e.getMessage());
			sb.append("同步异常:" +e.getMessage()+"<br/>");
		}

		if(StringUtils.isNoneBlank(sb.toString())){
			restAPIResult.setRespCode(0);
			restAPIResult.setRespMsg(sb.toString());
		}
		
		return restAPIResult;
	}

	/**
	 * 视频预览
	 * 
	 * @return
	 */
	@ApiOperation(value = "视频预览")
	@GetMapping("/wxVideoPreview")
	public RestAPIResult2 videoPreview(String videoId, String nikeName,String uid) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(0);
		restAPIResult.setRespMsg("预览发送失败");
		try {
			Integer id = Integer.parseInt(videoId);
			WeixinVideo weixinVideo = weixinVideoService.selectByPrimaryKey(id);

			logger.info("视频预览信息:weixinVideoId= " + id);
			if (StringUtils.isBlank(weixinVideo.getHeadImg())) {
				restAPIResult.setRespCode(0);
				restAPIResult.setRespMsg("视频不能为空");
				return restAPIResult;
			}
			WeixinUserinfo weixinUserinfo = WeixinUserinfoService.selectByPrimaryKey(Integer.parseInt(uid));
			String headImg = weixinVideo.getHeadImg();// 视频地址
			URL fileUrl = new URL(headImg);// 视频地址

			URLConnection rulConnection = fileUrl.openConnection();// 此处的urlConnection对象实际上是根据URL的
			HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;

			InputStream inputStream = httpUrlConnection.getInputStream();

			// 动态判断mediaType

			String fileType = StringUtil.getExtension(headImg);// 获得文件的扩展名

			WxMediaUploadResult uploadMediaRes = wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getMaterialService()
					.mediaUpload(WxConsts.MediaFileType.VIDEO, "mp4", inputStream);

			String mediaId = uploadMediaRes.getMediaId();


			logger.info("预览上传视频消息的----++++++++++++++--------------------------mediaId=" + mediaId + ",fileType=" + fileType + ",headImg=" + headImg);
			
			// 把视频变成可被群发的媒体
			WxMpMassVideo video = new WxMpMassVideo();
			video.setTitle(weixinVideo.getTitle());
			video.setDescription("Description");
			video.setMediaId(mediaId);
			WxMpMassUploadResult uploadResult = wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getMassMessageService()
					.massVideoUpload(video);


			logger.info("预览素材视频 -------------------------+++++++++++++-type:" + uploadResult.getType() + ",mediaId="
					+ uploadResult.getMediaId());
			
			WxMpMassPreviewMessage wxMpMassPreviewMessage = new WxMpMassPreviewMessage();
			wxMpMassPreviewMessage.setToWxUserName(nikeName);
			wxMpMassPreviewMessage.setMsgType(WxConsts.MassMsgType.MPVIDEO);
			wxMpMassPreviewMessage.setMediaId(uploadResult.getMediaId());
			WxMpMassSendResult wxMpMassSendResult = wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getMassMessageService()
					.massMessagePreview(wxMpMassPreviewMessage);
			System.out.println("---------------------------------预览消息结果--------------------------------");
			System.out.println(wxMpMassPreviewMessage.toJson());
			System.out.println(wxMpMassSendResult.getMsgId());
			System.out.println(wxMpMassSendResult.getMsgDataId());
			System.out.println(wxMpMassSendResult.getErrorCode());
			System.out.println(wxMpMassSendResult.getErrorMsg());
			System.out.println("---------------------------------预览消息结果--------------------------------");
			if (wxMpMassSendResult.getErrorCode().equals("0")) {
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("preview success");
			} else {
				restAPIResult.setRespCode(0);
				restAPIResult.setRespMsg(
						"预览异常，异常信息:" + WxMpErrorMsg.findMsgByCode(Integer.parseInt(wxMpMassSendResult.getErrorCode())));
			}

			if (inputStream != null) {
				inputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("预览异常" + e.getMessage());
			restAPIResult.setRespCode(0);
			restAPIResult.setRespMsg("预览异常" + e.getMessage());
		}
		return restAPIResult;
	}
	
	/**
	 * 视频预览
	 */
	@GetMapping("/test5")
	public RestAPIResult2 test5(Integer uid) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(0);
		restAPIResult.setRespMsg("预览发送失败");
		try {

			WeixinUserinfo weixinUserinfo = WeixinUserinfoService.selectByPrimaryKey(uid);
			String headImgRepl = file_location+"1656502113248417.mp4";
			
			File file = new File(headImgRepl);
			InputStream inputStream = new FileInputStream(file);
			
			//InputStream inputStream = ClassLoader.getSystemResourceAsStream("mm.mp4");

			String fileType = StringUtil.getExtension(headImgRepl);// 获得文件的扩展名
			
			WxMediaUploadResult uploadMediaRes = wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getMaterialService()
					.mediaUpload(WxConsts.MediaFileType.VIDEO, fileType, inputStream);

			String mediaId = uploadMediaRes.getMediaId();
			
			restAPIResult.setDataCode(mediaId);

			logger.info("上传视频消息的mediaId=" + mediaId + ",fileType=" + fileType + ",headImg=" + headImgRepl);
			

			if (inputStream != null) {
				inputStream.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("预览异常"+ e.getMessage());
			restAPIResult.setRespCode(0);
			restAPIResult.setRespMsg("预览异常"+ e.getMessage());
		}
		return restAPIResult;
	}
	
	
	/**
	 * 视频预览
	 */
	@GetMapping("/test6")
	public RestAPIResult2 test6(String mediaId,Integer uid) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(0);
		restAPIResult.setRespMsg("预览发送失败");
		try {

			WeixinUserinfo weixinUserinfo = WeixinUserinfoService.selectByPrimaryKey(uid);
			String headImgRepl = file_location+"1656502113248417.mp4";
			
			// 把视频变成可被群发的媒体
			WxMpMassVideo video = new WxMpMassVideo();
			video.setTitle("TEst ");
			video.setDescription("Test aa");
			video.setMediaId(mediaId);
			WxMpMassUploadResult uploadResult = wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid())
					.getMassMessageService().massVideoUpload(video);

			logger.info("群发文件素材视频 type:" + uploadResult.getType() + ",mediaId="
					+ uploadResult.getMediaId());
			
			restAPIResult.setDataCode(uploadResult.getMediaId());

		}catch(Exception e) {
			e.printStackTrace();
			logger.error("预览异常"+ e.getMessage());
			restAPIResult.setRespCode(0);
			restAPIResult.setRespMsg("预览异常"+ e.getMessage());
		}
		return restAPIResult;
	}
	
	/**
	 * 视频预览
	 */
	@GetMapping("/test7")
	public RestAPIResult2 test7(String nikeName,  String mediaId) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(0);
		restAPIResult.setRespMsg("预览发送失败");
		try {
			WeixinUserinfo weixinUserinfo = WeixinUserinfoService.selectByPrimaryKey(37);
			
			WxMpMassPreviewMessage wxMpMassPreviewMessage = new WxMpMassPreviewMessage();
			wxMpMassPreviewMessage.setToWxUserName(nikeName);
			wxMpMassPreviewMessage.setMsgType("mpvideo");
			wxMpMassPreviewMessage.setMediaId(mediaId);
			WxMpMassSendResult wxMpMassSendResult = wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getMassMessageService()
					.massMessagePreview(wxMpMassPreviewMessage);
			System.out.println("---------------------------------预览消息结果--------------------------------");
			System.out.println(wxMpMassSendResult.getMsgId());
			System.out.println(wxMpMassSendResult.getErrorCode());
			System.out.println(wxMpMassSendResult.getErrorMsg());
			System.out.println("---------------------------------预览消息结果--------------------------------");
			
			restAPIResult.setDataCode(wxMpMassSendResult.getErrorCode());
			if (wxMpMassSendResult.getErrorCode().equals("0")) {
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("preview success");
			} else {
				restAPIResult.setRespCode(0);
				restAPIResult.setRespMsg(
						"预览异常，异常信息:" + wxMpMassSendResult.getErrorMsg() + wxMpMassSendResult.getErrorCode());
			}

		}catch(Exception e) {
			e.printStackTrace();
			logger.error("预览异常"+ e.getMessage());
			restAPIResult.setRespCode(0);
			restAPIResult.setRespMsg("预览异常"+ e.getMessage());
		}
		return restAPIResult;
	}

	/**
	 * 视频群发
	 */
	@GetMapping("/test8")
	public RestAPIResult2 test8(String mediaId,Integer uid) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(0);
		restAPIResult.setRespMsg("群发测试失败");
		try {
			WeixinUserinfo weixinUserinfo = WeixinUserinfoService.selectByPrimaryKey(uid);
			
			String openIdA = "opMHkjqxTsajjTSAkHDcmxmdJgnU";
			String openIdB = "opMHkjoemb8wQeuKe2UepFqUs2Ks";
			
			WxMpMassOpenIdsMessage massMessage = new WxMpMassOpenIdsMessage();
			massMessage.setMsgType(WxConsts.MassMsgType.MPVIDEO);
			massMessage.setMediaId(mediaId);
			massMessage.getToUsers().add(openIdA);
			massMessage.getToUsers().add(openIdB);

			WxMpMassSendResult massResult = wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid())
					.getMassMessageService().massOpenIdsMessageSend(massMessage);
			
			restAPIResult.setDataCode(massResult.getErrorCode());
			if (massResult.getErrorCode().equals("0")) {
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("send success");
			} else {
				restAPIResult.setRespCode(0);
				restAPIResult.setRespMsg(
						"群发异常，异常信息:" + massResult.getErrorMsg() + massResult.getErrorCode());
			}
			
			restAPIResult.setDataCode(massResult.getErrorCode());
			
			WeixinPushLog weixinPushLog = new WeixinPushLog();
			weixinPushLog.setCategoryId("video");
			weixinPushLog.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
			weixinPushLog.setCreateBy(1);
			weixinPushLog.setCreateByUname("admin01");
			weixinPushLog.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
			weixinPushLog.setMsgId(massResult.getMsgId());
			weixinPushLog.setMsgDataId(massResult.getMsgDataId());
			
			weixinPushLogService.insertSelective(weixinPushLog);

		}catch(Exception e) {
			e.printStackTrace();
			logger.error("群发异常，异常信息:"+ e.getMessage());
			restAPIResult.setRespCode(0);
			restAPIResult.setRespMsg("群发异常，异常信息:"+ e.getMessage());
		}
		return restAPIResult;
	}
	
	@GetMapping("/test10")
	public RestAPIResult2 test9(String mediaId,Integer uid,String openId) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(0);
		restAPIResult.setRespMsg("预览测试失败");
		try {
			WeixinUserinfo weixinUserinfo = WeixinUserinfoService.selectByPrimaryKey(uid);
			
			if(openId==null) {
				 openId = "opMHkjoemb8wQeuKe2UepFqUs2Ks";
			}
			
//			WxMpMassOpenIdsMessage massMessage = new WxMpMassOpenIdsMessage();
//			massMessage.setMsgType(WxConsts.p);
//			massMessage.setMediaId(mediaId);
//			massMessage.getToUsers().add(openIdA);
//			massMessage.getToUsers().add(openIdB);

			WxMpMassPreviewMessage previewMsg=new WxMpMassPreviewMessage();
			previewMsg.setMediaId(mediaId);
			previewMsg.setMsgType(WxConsts.MassMsgType.MPVIDEO);
			previewMsg.setToWxUserOpenid(openId);
			
			WxMpMassSendResult massResult = wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid())
					.getMassMessageService().massMessagePreview(previewMsg);
			
			restAPIResult.setDataCode(massResult.getErrorCode());
			if (massResult.getErrorCode().equals("0")) {
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("preview success");
			} else {
				restAPIResult.setRespCode(0);
				restAPIResult.setRespMsg(
						"预览异常，异常信息:" + massResult.getErrorMsg() + massResult.getErrorCode());
			}
			
			restAPIResult.setDataCode(massResult.getErrorCode());
			
			WeixinPushLog weixinPushLog = new WeixinPushLog();
			weixinPushLog.setCategoryId("video");
			weixinPushLog.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
			weixinPushLog.setCreateBy(1);
			weixinPushLog.setCreateByUname("admin01");
			weixinPushLog.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
			weixinPushLog.setMsgId(massResult.getMsgId());
			weixinPushLog.setMsgDataId(massResult.getMsgDataId());
			
			weixinPushLogService.insertSelective(weixinPushLog);

		}catch(Exception e) {
			e.printStackTrace();
			logger.error("预览异常，异常信息:"+ e.getMessage());
			restAPIResult.setRespCode(0);
			restAPIResult.setRespMsg("预览异常，异常信息:"+ e.getMessage());
		}
		return restAPIResult;
	}
}
