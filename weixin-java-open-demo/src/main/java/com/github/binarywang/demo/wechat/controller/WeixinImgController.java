package com.github.binarywang.demo.wechat.controller;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.binarywang.demo.wechat.service.WxOpenServiceDemo;
import com.lj.cloud.secrity.service.WeixinImgService;
import com.lj.cloud.secrity.service.WeixinUserinfoService;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.weixin.WeixinImg;
import com.weixindev.micro.serv.common.bean.weixin.WeixinUserinfo;
import com.weixindev.micro.serv.common.util.StringUtil;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpMassPreviewMessage;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;

@RestController
public class WeixinImgController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private WxOpenServiceDemo wxOpenServiceDemo;
	@Autowired
	WxMpService WxMpService;

	@Autowired
	WeixinUserinfoService WeixinUserinfoService;

	@Autowired
	private WeixinImgService weixinImgService;

	@Value("${appURL}")
	private String appURL;// 网站前台url

	@Value("${file_location}")
	private String file_location;// 文件存储路径
	@Value("${ctxAppWeixin}")
	private String ctxAppWeixin;//微信网站路径
	/**
	 * 图片预览
	 * @return
	 */
	@GetMapping("/wxImgPreview")
	public RestAPIResult2 imgPreview(String imgId,String nikeName) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(0);
		restAPIResult.setRespMsg("预览发送失败");
		try {
			Integer id=Integer.parseInt(imgId);
			WeixinImg weixinImg = weixinImgService.selectByPrimaryKey(id);

			logger.info("weixinImgId= " + id);
			if (StringUtils.isBlank(weixinImg.getHeadImg())) {
				restAPIResult.setRespCode(0);
				restAPIResult.setRespMsg("图片不能为空");
				return restAPIResult;
			}
			WeixinUserinfo weixinUserinfo = WeixinUserinfoService.selectByPrimaryKey(37);
			
			URL fileUrl = new URL(weixinImg.getHeadImg());//图片地址

			URLConnection rulConnection = fileUrl.openConnection();// 此处的urlConnection对象实际上是根据URL的
			HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;

			InputStream inputStream = httpUrlConnection.getInputStream();

			// 动态判断mediaType
			String headImg = weixinImg.getHeadImg();// 图片地址

			String fileType = StringUtil.getExtension(headImg);// 获得文件的扩展名
			// 上传图片
			WxMediaUploadResult uploadMediaRes = wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getMaterialService()
					.mediaUpload(WxConsts.MediaFileType.IMAGE, fileType, inputStream);

			String mediaId = uploadMediaRes.getMediaId();

			logger.info("上传图片消息的mediaId=" + mediaId + ",fileType=" + fileType + ",headImg=" + headImg);

			if (inputStream != null) {
				inputStream.close();
			}
			
			WxMpMassPreviewMessage wxMpMassPreviewMessage = new WxMpMassPreviewMessage();
			wxMpMassPreviewMessage.setToWxUserName(nikeName);
			wxMpMassPreviewMessage.setMsgType(WxConsts.MediaFileType.IMAGE);
			wxMpMassPreviewMessage.setMediaId(uploadMediaRes.getMediaId());

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
}
