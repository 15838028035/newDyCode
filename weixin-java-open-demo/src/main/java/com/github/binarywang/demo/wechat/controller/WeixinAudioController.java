package com.github.binarywang.demo.wechat.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
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
import com.lj.cloud.secrity.service.WeixinAudioService;
import com.lj.cloud.secrity.service.WeixinPushLogService;
import com.lj.cloud.secrity.service.WeixinUserinfoService;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.WxMpErrorMsg;
import com.weixindev.micro.serv.common.bean.weixin.WeixinAudio;
import com.weixindev.micro.serv.common.bean.weixin.WeixinPushLog;
import com.weixindev.micro.serv.common.bean.weixin.WeixinUserinfo;
import com.weixindev.micro.serv.common.util.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.WxMpMassPreviewMessage;
import me.chanjar.weixin.mp.bean.WxMpMassTagMessage;
import me.chanjar.weixin.mp.bean.material.WxMpMaterial;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;

@Api(value = "音频管理服务", tags = "音频管理服务")
@RestController
public class WeixinAudioController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WxOpenServiceDemo wxOpenServiceDemo;

	@Autowired
	WeixinUserinfoService WeixinUserinfoService;

	@Autowired
	private WeixinAudioService weixinAudioService;

	@Value("${appURL}")
	private String appURL;// 网站前台url
	@Autowired
	private WeixinPushLogService weixinPushLogService;

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
	@RequestMapping(value = "/api/audio/doExexcuteBatchSend", method = {RequestMethod.POST})
	public RestAPIResult2 doExexcuteBatchSend(@RequestParam Map<String, String> map) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("成功");

		String audioId = String.valueOf(map.get("audioId"));// 音频ID
		String ids = map.get("ids");// 选择的公众号

		List<String> idsList = StringUtil.splitStringToStringList(ids);

		logger.info("StringUtil.splitStringToStringList :" + idsList.size());

		logger.info("群发消息音频audioId:" + audioId + ",公众号ids:" + ids);
		
		StringBuffer sb = new StringBuffer("");

		try {
			Integer id = Integer.parseInt(audioId);
			WeixinAudio weixinAudio = weixinAudioService.selectByPrimaryKey(id);
			if (weixinAudio == null || (weixinAudio != null && StringUtil.isBlank(weixinAudio.getHeadImg()))) {
				restAPIResult.setRespCode(0);
				restAPIResult.setRespMsg("音频不能为空,请上传音频");
				return restAPIResult;
			}

			String mediaId = "";
			String fileType = "";
			// 刷新accessToken

			// 动态判断mediaType
			String headImg = weixinAudio.getHeadImg();// 音频地址

			fileType = StringUtil.getExtension(headImg);// 获得文件的扩展名

			URL fileUrl = new URL(weixinAudio.getHeadImg());// 图片地址
		

			for (String str : idsList) {
				try{
					URLConnection rulConnection = fileUrl.openConnection();// 此处的urlConnection对象实际上是根据URL的
					HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;

					InputStream inputStream = httpUrlConnection.getInputStream();
					
					// 上传音频audio
					WxMediaUploadResult uploadMediaRes = wxOpenServiceDemo.getWxOpenComponentService()
							.getWxMpServiceByAppid(str).getMaterialService()
							.mediaUpload(WxConsts.MediaFileType.VOICE, fileType, inputStream);
	
					mediaId = uploadMediaRes.getMediaId();
	
					logger.info("上传音频消息的mediaId=" + mediaId + ",fileType=" + fileType + ",headImg=" + headImg);
	
					weixinAudio.setMediaId(mediaId);
					weixinAudioService.updateByPrimaryKeySelective(weixinAudio);
	
					// 群发
					if (StringUtils.isNotBlank(str)) {// 过滤授权状态为空的
						String appId = str;
						WxMpMassTagMessage audio = new WxMpMassTagMessage();
						audio.setMsgType(WxConsts.MassMsgType.VOICE);
						audio.setMediaId(mediaId);
						WxMpMassSendResult massResult = wxOpenServiceDemo.getWxOpenComponentService()
								.getWxMpServiceByAppid(appId).getMassMessageService().massGroupMessageSend(audio);
	
						logger.info("群发文件素材音频 type:" + fileType + ",mediaId=" + mediaId);
	
						logger.info("群发文件素材音频str= " + str + ",结果：" + massResult.getErrorCode() + ","
								+ massResult.getErrorMsg() + "," + massResult.getMsgId());
						
						WeixinUserinfo WeixinUserinfoFilter = WeixinUserinfoService.selectByauthorizerAppid(str);
						
						WeixinPushLog weixinPushLog = new WeixinPushLog();
						weixinPushLog.setCategoryId("audio");
						weixinPushLog.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
						weixinPushLog.setCreateBy(1);
						weixinPushLog.setCreateByUname("admin01");
						weixinPushLog.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
						weixinPushLog.setMsgId(massResult.getMsgId());
						weixinPushLog.setMsgDataId(massResult.getMsgDataId());
						weixinPushLog.setUserId(WeixinUserinfoFilter.getId());
						weixinPushLog.setAuthorizerAppid(appId);
						
	
						weixinPushLogService.insertSelective(weixinPushLog);
					}
				}catch(WxErrorException e) {
					 e.printStackTrace();
			    		Integer code = e.getError().getErrorCode();
						logger.info(str + "群发异常:" +e.getMessage() + ",异常信息:"+ WxMpErrorMsg.findMsgByCode(code)+"<br/>");
						sb.append("群发异常:"+ WxMpErrorMsg.findMsgByCode(code)+"<br/>");
			    }
				catch(Exception e){
					sb.append("群发异常:" +e.getMessage()+"<br/>");
				}
				
		}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("群发异常，异常信息:" + e.getMessage());
			restAPIResult.setRespCode(0);
			restAPIResult.setRespMsg("群发异常，异常信息:" + e.getMessage());
			sb.append("群发异常:"+  e.getMessage()+"<br/>");
		}

		if(StringUtils.isNoneBlank(sb.toString())){
			restAPIResult.setRespCode(0);
			restAPIResult.setRespMsg(sb.toString());
		}
		
		return restAPIResult;
	}

	@ApiOperation(value = "同步")
	@RequestMapping(value = "/api/audio/doExexcuteBatchSync", method = {RequestMethod.POST})
	public RestAPIResult2 doExexcuteBatchSync(@RequestParam Map<String, String> map) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("成功");
		
		StringBuffer sb = new StringBuffer("");

		try {
			String audioId = String.valueOf(map.get("audioId"));// 音频ID
			String ids = map.get("ids");// 选择的公众号

			List<String> idsList = StringUtil.splitStringToStringList(ids);

			logger.info("StringUtil.splitStringToStringList :" + idsList.size());

			logger.info("群发消息音频audioId:" + audioId + ",公众号ids:" + ids);

			Integer id = Integer.parseInt(audioId);
			WeixinAudio weixinAudio = weixinAudioService.selectByPrimaryKey(id);

			if (weixinAudio == null || (weixinAudio != null && StringUtil.isBlank(weixinAudio.getHeadImg()))) {
				restAPIResult.setRespCode(0);
				restAPIResult.setRespMsg("音频不能为空,请上传音频");
				return restAPIResult;
			}

			String mediaId = "";
			String fileType = "";
			// 刷新accessToken
			for (String str : idsList) {
				if (StringUtils.isNotBlank(str)) {// 过滤授权状态为空的
					
					String appId = str;
					try{

					// 动态判断mediaType
					String headImg = weixinAudio.getHeadImg();// 音频地址
					String headImgRepl = headImg.replaceFirst(appURL, file_location);

					fileType = StringUtil.getExtension(headImg);// 获得文件的扩展名

					//// WxMpMaterialUploadResult materialFileUpload(String mediaType, WxMpMaterial
					//// material);
					// 上传音频audio

					File fileTmp = new File(headImgRepl);
					String mediaType = "voice";// voice
					String name = headImg.substring(headImg.lastIndexOf("/") + 1);

					WxMpMaterial wxMpMaterial = new WxMpMaterial();
					wxMpMaterial.setFile(fileTmp);
					wxMpMaterial.setName(name);
					WxMpMaterialUploadResult uploadMediaRes = wxOpenServiceDemo.getWxOpenComponentService()
							.getWxMpServiceByAppid(appId).getMaterialService()
							.materialFileUpload(mediaType, wxMpMaterial);

					mediaId = uploadMediaRes.getMediaId();
					
					logger.info("上传音频消息的mediaId=" + mediaId + ",fileType=" + fileType + ",图片地址headImg=" + headImg);
					
					WeixinUserinfo WeixinUserinfoFilter = WeixinUserinfoService.selectByauthorizerAppid(str);
					
					WeixinPushLog weixinPushLog = new WeixinPushLog();
					weixinPushLog.setCategoryId("audio");//图文消息
					weixinPushLog.setMediaCategory("1");//永久素材
					weixinPushLog.setMediaId(uploadMediaRes.getMediaId());
					weixinPushLog.setImgTextId(weixinAudio.getId());
					weixinPushLog.setCreateBy(1);
					weixinPushLog.setCreateByUname("admin01");
					weixinPushLog.setUserId(WeixinUserinfoFilter.getId());
					weixinPushLog.setAuthorizerAppid(appId);
					
					weixinPushLog.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					
					weixinPushLogService.insertSelective(weixinPushLog);
					
					}catch(WxErrorException e) {
						 e.printStackTrace();
				    		Integer code = e.getError().getErrorCode();
							logger.info(str + "同步异常:" +e.getMessage() + ",异常信息:"+ WxMpErrorMsg.findMsgByCode(code)+"<br/>");
							sb.append("同步异常:" + WxMpErrorMsg.findMsgByCode(code)+"<br/>");
				    }
					catch(Exception e){
						sb.append("同步异常:" +e.getMessage()+"<br/>");
					}
					
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传音频消息，异常信息:" + e.getMessage());
			restAPIResult.setRespCode(0);
			restAPIResult.setRespMsg("上传异常，异常信息:" + e.getMessage());
			sb.append("同步异常:"+ e.getMessage()+"<br/>");
		}
		
		if(StringUtils.isNoneBlank(sb.toString())){
			restAPIResult.setRespCode(0);
			restAPIResult.setRespMsg(sb.toString());
		}

		return restAPIResult;
	}

	/**
	 * 音频预览
	 * 
	 * @return
	 */
	@GetMapping("/wxAudioPreview")
	public RestAPIResult2 audioPreview(String audioId, String nikeName, String uid) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(0);
		restAPIResult.setRespMsg("预览发送失败");
		StringBuffer sb = new StringBuffer("");
		
		try {
			Integer id = Integer.parseInt(audioId);
			WeixinAudio weixinAudio = weixinAudioService.selectByPrimaryKey(id);

			logger.info("上传音频信息:weixinAudioId= " + id);
			if (StringUtils.isBlank(weixinAudio.getHeadImg())) {
				restAPIResult.setRespCode(0);
				restAPIResult.setRespMsg("音频不能为空");
				return restAPIResult;
			}
			WeixinUserinfo weixinUserinfo = WeixinUserinfoService.selectByPrimaryKey(Integer.parseInt(uid));
			// 动态判断mediaType
			String headImg = weixinAudio.getHeadImg();// 音频地址

			String fileType = StringUtil.getExtension(headImg);// 获得文件的扩展名

			URL fileUrl = new URL(weixinAudio.getHeadImg());// 图片地址

			URLConnection rulConnection = fileUrl.openConnection();// 此处的urlConnection对象实际上是根据URL的
			HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;

			InputStream inputStream = httpUrlConnection.getInputStream();

			// 上传音频audio
			WxMediaUploadResult uploadMediaRes = wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getMaterialService()
					.mediaUpload(WxConsts.MediaFileType.VOICE, fileType, inputStream);

			// WxMpMaterialUploadResult materialFileUpload(String mediaType, WxMpMaterial
			// material);
			String mediaId = uploadMediaRes.getMediaId();

			logger.info("上传音频消息:mediaId=" + mediaId + ",fileType=" + fileType + ",headImg=" + headImg);

			WxMpMassPreviewMessage wxMpMassPreviewMessage = new WxMpMassPreviewMessage();
			wxMpMassPreviewMessage.setToWxUserName(nikeName);
			wxMpMassPreviewMessage.setMsgType(WxConsts.MediaFileType.VOICE);
			wxMpMassPreviewMessage.setMediaId(mediaId);

			WxMpMassSendResult wxMpMassSendResult = wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getMassMessageService()
					.massMessagePreview(wxMpMassPreviewMessage);
			System.out.println("---------------------------------预览消息结果--------------------------------");
			System.out.println(wxMpMassSendResult.getMsgId());
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

		}catch(WxErrorException e) {
			 e.printStackTrace();
	    		Integer code = e.getError().getErrorCode();
				logger.info("预览异常，异常信息" +e.getMessage() + ",异常信息:"+ WxMpErrorMsg.findMsgByCode(code)+"<br/>");
				sb.append("预览异常:" + WxMpErrorMsg.findMsgByCode(code)+"<br/>");
	    }
		catch (Exception e) {
			e.printStackTrace();
			logger.error("预览异常" + e.getMessage());
			restAPIResult.setRespCode(0);
			sb.append("预览异常:" + e.getMessage()+"<br/>");
		}
		
		if(StringUtils.isNoneBlank(sb.toString())){
			restAPIResult.setRespCode(0);
			restAPIResult.setRespMsg(sb.toString());
		}
		
		return restAPIResult;
	}

	@GetMapping("/test05")
	public RestAPIResult2 test5(String uid) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(0);
		restAPIResult.setRespMsg("预览发送失败");
		try {

			WeixinUserinfo weixinUserinfo = WeixinUserinfoService.selectByPrimaryKey(Integer.parseInt(uid));
			String headImgRepl = file_location + "1645106545938605.mp3";

			File file = new File(headImgRepl);
			InputStream inputStream = new FileInputStream(file);

			String fileType = StringUtil.getExtension(headImgRepl);// 获得文件的扩展名

			WxMediaUploadResult uploadMediaRes = wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getMaterialService()
					.mediaUpload(WxConsts.MediaFileType.VOICE, fileType, inputStream);

			String mediaId = uploadMediaRes.getMediaId();

			restAPIResult.setDataCode(mediaId);

			logger.info("上传音频消息的mediaId=" + mediaId + ",fileType=" + fileType + ",headImg=" + headImgRepl);

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
	 * 音频预览
	 */
	@GetMapping("/test08")
	public RestAPIResult2 test8(String uid, String mediaId, String openId) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(0);
		restAPIResult.setRespMsg("群发测试失败");
		try {
			WeixinUserinfo weixinUserinfo = WeixinUserinfoService.selectByPrimaryKey(Integer.parseInt(uid));

			WxMpMassOpenIdsMessage massMessage = new WxMpMassOpenIdsMessage();
			massMessage.setMsgType(WxConsts.MassMsgType.VOICE);
			massMessage.setMediaId(mediaId);
			massMessage.getToUsers().add(openId);
			WxMpMassSendResult massResult = wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getMassMessageService()
					.massOpenIdsMessageSend(massMessage);

			restAPIResult.setDataCode(massResult.getErrorCode());
			if (massResult.getErrorCode().equals("0")) {
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("test success");
			} else {
				restAPIResult.setRespCode(0);
				restAPIResult.setRespMsg("群发异常，异常信息:" + massResult.getErrorMsg() + massResult.getErrorCode());
			}

			restAPIResult.setDataCode(massResult.getErrorCode());

			WeixinPushLog weixinPushLog = new WeixinPushLog();
			weixinPushLog.setCategoryId("audio");
			weixinPushLog.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
			weixinPushLog.setCreateBy(1);
			weixinPushLog.setCreateByUname("admin01");
			weixinPushLog.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
			weixinPushLog.setMsgId(massResult.getMsgId());
			weixinPushLog.setMsgDataId(massResult.getMsgDataId());

			weixinPushLogService.insertSelective(weixinPushLog);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("群发异常，异常信息:" + e.getMessage());
			restAPIResult.setRespCode(0);
			restAPIResult.setRespMsg("群发异常，异常信息:" + e.getMessage());
		}
		return restAPIResult;
	}

	/**
	 * 获得某个公众账号的关注着列表
	 * 
	 * @param uid
	 * @return
	 */
	@GetMapping("/test09")
	public List<WxMpUser> test09(Integer uid) {
		WeixinUserinfo weixinUserinfo = WeixinUserinfoService.selectByPrimaryKey(uid);
		List<WxMpUser> wxMpUserList = new ArrayList<WxMpUser>();

		if (weixinUserinfo == null) {
			return wxMpUserList;
		}

		try {
			WxMpUserList wxUserList = wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getUserService().userList("");

			for (String oppenid : wxUserList.getOpenids()) {
				WxMpUser wxMpUser = wxOpenServiceDemo.getWxOpenComponentService()
						.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getUserService().userInfo(oppenid);
				wxMpUserList.add(wxMpUser);
			}

		} catch (WxErrorException e1) {
			e1.printStackTrace();
		}
		return wxMpUserList;
	}

	@GetMapping("/test010")
	public RestAPIResult2 test9(String mediaId, Integer uid, String openId) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(0);
		restAPIResult.setRespMsg("预览测试失败");
		try {
			WeixinUserinfo weixinUserinfo = WeixinUserinfoService.selectByPrimaryKey(uid);

			if (openId == null) {
				openId = "opMHkjoemb8wQeuKe2UepFqUs2Ks";
			}

			// WxMpMassOpenIdsMessage massMessage = new WxMpMassOpenIdsMessage();
			// massMessage.setMsgType(WxConsts.p);
			// massMessage.setMediaId(mediaId);
			// massMessage.getToUsers().add(openIdA);
			// massMessage.getToUsers().add(openIdB);

			WxMpMassPreviewMessage previewMsg = new WxMpMassPreviewMessage();
			previewMsg.setMediaId(mediaId);
			previewMsg.setMsgType(WxConsts.MassMsgType.VOICE);
			previewMsg.setToWxUserOpenid(openId);

			WxMpMassSendResult massResult = wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getMassMessageService()
					.massMessagePreview(previewMsg);

			restAPIResult.setDataCode(massResult.getErrorCode());
			if (massResult.getErrorCode().equals("0")) {
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("preview success");
			} else {
				restAPIResult.setRespCode(0);
				restAPIResult.setRespMsg("预览异常，异常信息:" + massResult.getErrorMsg() + massResult.getErrorCode());
			}

			restAPIResult.setDataCode(massResult.getErrorCode());

			WeixinPushLog weixinPushLog = new WeixinPushLog();
			weixinPushLog.setCategoryId("voice");
			weixinPushLog.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
			weixinPushLog.setCreateBy(1);
			weixinPushLog.setCreateByUname("admin01");
			weixinPushLog.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
			weixinPushLog.setMsgId(massResult.getMsgId());
			weixinPushLog.setMsgDataId(massResult.getMsgDataId());

			weixinPushLogService.insertSelective(weixinPushLog);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("预览异常，异常信息:" + e.getMessage());
			restAPIResult.setRespCode(0);
			restAPIResult.setRespMsg("预览异常，异常信息:" + e.getMessage());
		}
		return restAPIResult;
	}

}