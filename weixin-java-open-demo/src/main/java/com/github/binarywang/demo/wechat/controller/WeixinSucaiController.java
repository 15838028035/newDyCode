package com.github.binarywang.demo.wechat.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.binarywang.demo.wechat.service.WxOpenServiceDemo;
import com.github.binarywang.demo.wechat.utils.FileMatchUtil;
import com.github.binarywang.demo.wechat.utils.GIfUtil;
import com.lj.cloud.secrity.service.WeixinImgService;
import com.lj.cloud.secrity.service.WeixinImgtextItemService;
import com.lj.cloud.secrity.service.WeixinPushLogService;
import com.lj.cloud.secrity.service.WeixinUserinfoService;
import com.lj.cloud.secrity.service.WeixinVideoService;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.weixin.WeixinImg;
import com.weixindev.micro.serv.common.bean.weixin.WeixinImgtextItem;
import com.weixindev.micro.serv.common.bean.weixin.WeixinUserinfo;
import com.weixindev.micro.serv.common.pagination.Query;
import com.weixindev.micro.serv.common.util.DateUtil;
import com.weixindev.micro.serv.common.util.FileType;
import com.weixindev.micro.serv.common.util.FileTypeJudge;
import com.weixindev.micro.serv.common.util.FileUtil;
import com.weixindev.micro.serv.common.util.StringUtil;

import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpMassNews;
import me.chanjar.weixin.mp.bean.WxMpMassPreviewMessage;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.material.WxMediaImgUploadResult;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import me.chanjar.weixin.mp.bean.result.WxMpMassUploadResult;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import me.chanjar.weixin.mp.builder.kefu.NewsBuilder;

@RestController
public class WeixinSucaiController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WxOpenServiceDemo wxOpenServiceDemo;
	@Autowired
	WxMpService WxMpService;

	@Autowired
	WeixinUserinfoService WeixinUserinfoService;

	@Autowired
	private WeixinImgtextItemService weixinImgtextItemService;

	@Autowired
	private WeixinVideoService weixinVideoService;
	
	@Autowired
	private WeixinPushLogService weixinPushLogService ;
	
	@Autowired
	private WeixinImgService weixinImgService;

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

	@GetMapping("/test/uploadimg")
	public WxMediaImgUploadResult testUploadImg() {
		File file = new java.io.File("/web/ddyingyuanweixinmg_oa/pnt.png");
		WeixinUserinfo weixinUserinfo = WeixinUserinfoService.selectByPrimaryKey(37);
		try {
			WxMediaImgUploadResult res = wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getMaterialService()
					.mediaImgUpload(file);
			return res;
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 预览 旧版 暂时弃用版
	 */
	@GetMapping("/getopenidbyqqmobilewx")
	public String SendpreviewToOppenId(String nike_name, Integer templateid, HttpServletRequest request) {
		WeixinUserinfo weixinUserinfo = WeixinUserinfoService.selectByPrimaryKey(37);
		String host = request.getHeader("host");
		String previewurl = "http://" + host + ":8022/privew.html?id=";
		try {
			WxMpUserList wxUserList = wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getUserService().userList("");
			System.out.println(wxUserList);
			String lang = "zh_CN"; // 语言
			for (String oppenid : wxUserList.getOpenids()) {
				System.out.println("--------------------------------------------------------------------");
				System.out.println(oppenid);
				System.out.println("--------------------------------------------------------------------");
				WxMpUser wxMpUser = wxOpenServiceDemo.getWxOpenComponentService()
						.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getUserService().userInfo(oppenid);
				System.out.println("--------------------------------------------------------------------");
				System.out.println(wxMpUser);
				System.out.println("--------------------------------------------------------------------");
				if (wxMpUser.getNickname().equals(nike_name)) {
					List<WeixinImgtextItem> weixinImgtextItems = weixinImgtextItemService
							.selectByTemplateimdId(templateid);
					NewsBuilder newsBuilder = WxMpKefuMessage.NEWS();
					for (WeixinImgtextItem weixinImgtextItem : weixinImgtextItems) {
						WxMpKefuMessage.WxArticle article = new WxMpKefuMessage.WxArticle();
						article.setUrl(previewurl + weixinImgtextItem.getId());
						article.setPicUrl(weixinImgtextItem.getHeadImg());
						article.setDescription(weixinImgtextItem.getIntro());
						article.setTitle(weixinImgtextItem.getTitle());

						newsBuilder.toUser(oppenid).addArticle(article);
					}

					WxMpKefuMessage message = newsBuilder.build();

					boolean wxMpKefuMessage = wxOpenServiceDemo.getWxOpenComponentService()
							.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getKefuService()
							.sendKefuMessage(message);

					System.out.println("--------------------------------------------------------------------");
					System.out.println(wxMpKefuMessage);
					System.out.println("--------------------------------------------------------------------");
					return "success";
				}
			}
		} catch (WxErrorException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return "hasnone";
	}

	/*
	 * 预览 新版 在用版
	 */
	@GetMapping("/wxpreviewnew")
	public RestAPIResult2 SendpreviewToOppenIdnew(String nike_name, Integer templateid,Integer uid, HttpServletRequest request) {
		WeixinUserinfo weixinUserinfo = WeixinUserinfoService.selectByPrimaryKey(uid);
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(0);
		restAPIResult.setRespMsg("预览发送失败");
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("imgTextId", templateid);
			// 查看图文列表项数据
			Query query = new Query(params);
			List<WeixinImgtextItem> list = weixinImgtextItemService.selectByExample(query);

			logger.info("imgTextId= " + templateid + " 的记录数据是list.size :" + list.size());

			for (WeixinImgtextItem WeixinImgtextItem : list) {
				if (StringUtils.isBlank(WeixinImgtextItem.getHeadImg())) {
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg("封面图片为空，不允许发送");
					return restAPIResult;
				}
			}

			// (1)处理图文消息正文内部的图片、视频、音频等文件信息替换 上传图文消息的正文图片(返回的url拼在正文的<img>标签中)

			for (WeixinImgtextItem WeixinImgtextItem : list) {

				if (StringUtil.isNotBlank(WeixinImgtextItem.getArticleContent())) {// 更新media
					try {

						List<String> strContentList = FileMatchUtil
								.getImgSrcList(WeixinImgtextItem.getArticleContent());
						String retContent = "";
						retContent = WeixinImgtextItem.getArticleContent();
						
						WeixinImgtextItem.setNewContent(retContent);// 设置替换以后新的内容,防止查询不到内容，出现内容为空

						if (strContentList != null) {// 替换图片
							for (String str : strContentList) {
								System.out.println("图片src=" + str);

								String headImgRepl = str.replaceFirst(appURL, file_location);

								File fileTmp = new File(headImgRepl);
								
								if(fileTmp!=null &&fileTmp.exists())	{
									WxMediaImgUploadResult res = wxOpenServiceDemo.getWxOpenComponentService()
											.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getMaterialService()
											.mediaImgUpload(fileTmp);
									String url = res.getUrl();
									retContent = retContent.replaceFirst(str, url);// 替换后的内容
	
									WeixinImgtextItem.setNewContent(retContent);// 设置替换以后新的内容
									weixinImgtextItemService.updateByPrimaryKeySelective(WeixinImgtextItem);
									logger.info(" 替换图片img WeixinImgtextItem.getId 的ID为=" + WeixinImgtextItem.getId()
											+ ",的imgTextId=" + templateid + ", url:" + res.getUrl() + ",retContent:"
											+ retContent);
								}else {
									
									WeixinImgtextItem.setNewContent(retContent);// 设置替换以后新的内容
									logger.info(" 替换图片文件路径不存在" + headImgRepl + ",str="+str);
									
									//网络图片地址
									/*URL fileUrl = new URL(str);
									URLConnection rulConnection = fileUrl.openConnection();// 此处的urlConnection对象实际上是根据URL的
									HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
									InputStream inputStream = httpUrlConnection.getInputStream();
									InputStream inputStream2 = httpUrlConnection.getInputStream();//两个流，防止被关闭
									
									String extName = FileTypeJudge.getType2(inputStream).name().toLowerCase();
									String fileName = System.nanoTime()+"."+extName; 
									String filePath = file_location +fileName;
									
									FileUtil.createFile(inputStream2, filePath);*/
									
									URL netUrl = new URL(str);   
							        URLConnection rulConnection = netUrl.openConnection();// 此处的urlConnection对象实际上是根据URL的
									HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
									InputStream inputStream = httpUrlConnection.getInputStream();
									
								 ByteArrayOutputStream baos = new ByteArrayOutputStream();  
								 byte[] buffer = new byte[1024];  
								 int len;  
								 while ((len = inputStream.read(buffer)) > -1 ) {  
								     baos.write(buffer, 0, len);  
								 }  
								 baos.flush();                
								   
								 InputStream inputStream1 = new ByteArrayInputStream(baos.toByteArray());  
								 InputStream inputStream2 = new ByteArrayInputStream(baos.toByteArray());  
								
								FileType FileType = FileTypeJudge.getType(inputStream1);
								
								String extName = FileType.name().toLowerCase();//文件扩展名
								
								String fileName = System.nanoTime()+"."+extName; 
								String filePath = file_location +fileName;
						        
						        if(FileType.GIF.name().toLowerCase().equals(extName)){
									GIfUtil.saveGif(inputStream2,filePath);
								}else {
									BufferedImage image = null; 
									image = ImageIO.read(netUrl);    
							        ImageIO.write(image, extName, new File(filePath));   
								}
										
									WeixinImg weixinImg = new WeixinImg();
									
									Integer createBy = 1;
									weixinImg.setCreateBy(createBy);
									weixinImg.setCreateByUname("admin01");
									weixinImg.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
									
									String headImg = appURL+"/"+fileName;
									weixinImg.setHeadImg(headImg);
									weixinImgService.insertSelective(weixinImg);
									
									
									 fileTmp = new File(filePath);
									 WxMediaImgUploadResult res = wxOpenServiceDemo.getWxOpenComponentService()
												.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getMaterialService()
												.mediaImgUpload(fileTmp);
										String url = res.getUrl();
										retContent = retContent.replaceFirst(str, url);// 替换后的内容
		
										WeixinImgtextItem.setNewContent(retContent);// 设置替换以后新的内容
										weixinImgtextItemService.updateByPrimaryKeySelective(WeixinImgtextItem);
										logger.info(" 替换图片img WeixinImgtextItem.getId 的ID为=" + WeixinImgtextItem.getId()
												+ ",的imgTextId=" + templateid + ", url:" + res.getUrl() + ",retContent:"
												+ retContent);
									
								}
							}
						}

						/**
						 * 不进行音频视频url地址过滤 // 替换视频video strContentList =
						 * FileMatchUtil.getVideoSrcList(retContent); retContent =
						 * WeixinImgtextItem.getNewContent();
						 * 
						 * if (strContentList != null) {// 替换视频video for (String str : strContentList) {
						 * System.out.println("视频src=" + str);
						 * 
						 * String headImgRepl = str.replaceFirst(appURL, file_location);
						 * 
						 * File fileTmp = new File(headImgRepl); String mediaType = "video";// voice
						 * String name = WeixinImgtextItem.getTitle(); String videoTitle =
						 * WeixinImgtextItem.getTitle(); String videoIntroduction =
						 * WeixinImgtextItem.getIntro();
						 * 
						 * WxMpMaterial WxMpMaterial = new WxMpMaterial(name, fileTmp, videoTitle,
						 * videoIntroduction);
						 * 
						 * WxMpMaterialUploadResult res = wxOpenServiceDemo.getWxOpenComponentService()
						 * .getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getMaterialService()
						 * .materialFileUpload(mediaType, WxMpMaterial);
						 * 
						 * String mediaId = res.getMediaId();
						 * 
						 * WxMpMaterialVideoInfoResult materialVideoInfo =
						 * wxOpenServiceDemo.getWxOpenComponentService()
						 * .getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getMaterialService().materialVideoInfo(
						 * mediaId);
						 * 
						 * String url = materialVideoInfo.getDownUrl();//视频url retContent =
						 * retContent.replaceFirst(str, url);// 替换后的内容
						 * 
						 * WeixinImgtextItem.setNewContent(retContent);// 设置替换以后新的内容
						 * weixinImgtextItemService.updateByPrimaryKeySelective(WeixinImgtextItem);
						 * logger.info("替换视频video WeixinImgtextItem.getId 的ID为=" +
						 * WeixinImgtextItem.getId() + ",的imgTextId=" + templateid + ", url:" + url +
						 * ",retContent:" + retContent +",mediaId:"+mediaId); } }
						 * 
						 * // 替换音频audio strContentList = FileMatchUtil.getAudioSrcList(retContent);
						 * retContent = WeixinImgtextItem.getNewContent();
						 * 
						 * if (strContentList != null) {// 替换音频audio for (String str : strContentList) {
						 * System.out.println("替换音频audio src=" + str);
						 * 
						 * String headImgRepl = str.replaceFirst(appURL, file_location);
						 * 
						 * File fileTmp = new File(headImgRepl); String mediaType = "voice"; String name
						 * = WeixinImgtextItem.getTitle(); String videoTitle =
						 * WeixinImgtextItem.getTitle(); String videoIntroduction =
						 * WeixinImgtextItem.getIntro();
						 * 
						 * WxMpMaterial WxMpMaterial = new WxMpMaterial(name, fileTmp, videoTitle,
						 * videoIntroduction);
						 * 
						 * WxMpMaterialUploadResult res = wxOpenServiceDemo.getWxOpenComponentService()
						 * .getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getMaterialService()
						 * .materialFileUpload(mediaType, WxMpMaterial);
						 * 
						 * String mediaId = res.getMediaId(); // String url = ctxAppWeixin +
						 * "/getVoice?mediaId="+mediaId; // retContent = retContent.replaceFirst(str,
						 * url);// 替换后的内容
						 * 
						 * 
						 * WeixinImgtextItem.setNewContent(retContent);// 设置替换以后新的内容
						 * weixinImgtextItemService.updateByPrimaryKeySelective(WeixinImgtextItem);
						 * logger.info("替换视频audio WeixinImgtextItem.getId 的ID为=" +
						 * WeixinImgtextItem.getId() + ",的imgTextId=" + templateid + ",retContent:" +
						 * retContent +",mediaId:"+mediaId); } }
						 */

					} catch (Exception e) {
						e.printStackTrace();
						logger.error("处理图文消息正文内部的图片、视频、音频等文件信息替换，异常信息:" + e.getMessage());
						restAPIResult.setRespCode(0);
						restAPIResult.setRespMsg("处理图文消息正文内部的图片、视频、音频等文件信息替换，异常信息:" + e.getMessage());
					}

				}
			}

			// (2) 上传图文消息的封面的图片
			int jj = 0;
			for (WeixinImgtextItem WeixinImgtextItem : list) {

				if (StringUtil.isNotBlank(WeixinImgtextItem.getHeadImg())
						) {// 更新media
					try {
						URL fileUrl = new URL(WeixinImgtextItem.getHeadImg());

						URLConnection rulConnection = fileUrl.openConnection();// 此处的urlConnection对象实际上是根据URL的
						HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;

						InputStream inputStream = httpUrlConnection.getInputStream();

						// 动态判断mediaType
						String headImg = WeixinImgtextItem.getHeadImg();

						String mediaType = "image";// 图片类型的素材
						String mediaId = "";

						String imgType = "bmp、jpg、jpeg、png、gif";

						String voiceType = "avi,rmvb,rm,asf,divx,mpg,mpeg,mpe,wmv,mp4,mkv,vob";

						String fileType = StringUtil.getExtension(headImg);// 获得文件的扩展名

						WxMediaUploadResult res = wxOpenServiceDemo.getWxOpenComponentService()
								.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getMaterialService()
								.mediaUpload(mediaType, fileType, inputStream);

						mediaId = res.getMediaId();

						logger.info("上传图文消息的封面的mediaId=" + mediaId + ",mediaType=" + mediaType + ",fileType=" + fileType
								+ ",headImg=" + headImg);

						WeixinImgtextItem.setMediaId(mediaId);
						weixinImgtextItemService.updateByPrimaryKeySelective(WeixinImgtextItem);

						logger.info("上传图文消息的封面的图片  WeixinImgtextItem.getId 的ID为=" + WeixinImgtextItem.getId()
								+ ",的imgTextId=" + templateid + ", mediaId= " + mediaId);

						if (inputStream != null) {
							inputStream.close();
						}

					} catch (Exception e) {
						e.printStackTrace();
						logger.error("上传图文消息的封面的图片，异常信息:" + e.getMessage());
						restAPIResult.setRespCode(0);
						restAPIResult.setRespMsg("上传图文消息的封面的图片，异常信息:" + e.getMessage());
					}

				}
				jj++;
			}

			params.put("imgTextId", templateid);
			// 查看上传成功以后的信息
			Query query2 = new Query(params);
			// List<WeixinImgtextItem> list2 =
			// weixinImgtextItemService.selectByExample(query);//上传成功过以后，获得列表

			logger.info("imgTextId= " + templateid + " 的记录数据是list2.size :" + list.size());

			WxMpMassNews news = new WxMpMassNews();
			Integer i = 0;
			try {
				for (WeixinImgtextItem WeixinImgtextItem : list) {
					WxMpMassNews.WxMpMassNewsArticle article2 = new WxMpMassNews.WxMpMassNewsArticle();
					article2.setTitle(WeixinImgtextItem.getTitle());
					article2.setContent(WeixinImgtextItem.getNewContent());// 文章内容为替换以后的文章内容
					article2.setThumbMediaId(WeixinImgtextItem.getMediaId());
					/*
					 * if (i == 0) {// 默认第一张是封面 article2.setShowCoverPic(false); } else {
					 * article2.setShowCoverPic(false); }
					 */
					article2.setShowCoverPic(false);
					article2.setAuthor(WeixinImgtextItem.getAuthor());
					article2.setContentSourceUrl(WeixinImgtextItem.getOriginUrl());
					article2.setDigest(WeixinImgtextItem.getIntro());
					news.addArticle(article2);
					i++;
				}

				WxMpMassUploadResult massUploadResult = wxOpenServiceDemo.getWxOpenComponentService()
						.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getMassMessageService()
						.massNewsUpload(news);

				logger.info(
						"预览文件素材结果 type:" + massUploadResult.getType() + ",mediaId=" + massUploadResult.getMediaId());

				WxMpMassPreviewMessage wxMpMassPreviewMessage = new WxMpMassPreviewMessage();
				wxMpMassPreviewMessage.setToWxUserName(nike_name);
				wxMpMassPreviewMessage.setMsgType("mpnews");
				wxMpMassPreviewMessage.setMediaId(massUploadResult.getMediaId());

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

			} catch (Exception e2) {
				logger.error("预览异常，异常信息22222222222222:" + e2.getMessage());
				restAPIResult.setRespCode(0);
				restAPIResult.setRespMsg("预览异常，异常信息:" + e2.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("预览异常，异常信息:" + e.getMessage());
			restAPIResult.setRespCode(0);
			restAPIResult.setRespMsg("预览异常，异常信息:" + e.getMessage());
		}

		return restAPIResult;
	}

}
