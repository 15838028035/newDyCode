package com.github.binarywang.demo.wechat.task;

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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.github.binarywang.demo.wechat.service.WxOpenServiceDemo;
import com.github.binarywang.demo.wechat.utils.FileMatchUtil;
import com.github.binarywang.demo.wechat.utils.GIfUtil;
import com.lj.cloud.secrity.service.WeixinImgService;
import com.lj.cloud.secrity.service.WeixinImgtextItemService;
import com.lj.cloud.secrity.service.WeixinPushLogService;
import com.lj.cloud.secrity.service.WeixinUserinfoService;
import com.weixindev.micro.serv.common.bean.WxMpErrorMsg;
import com.weixindev.micro.serv.common.bean.weixin.WeixinImg;
import com.weixindev.micro.serv.common.bean.weixin.WeixinImgtextItem;
import com.weixindev.micro.serv.common.bean.weixin.WeixinPushLog;
import com.weixindev.micro.serv.common.bean.weixin.WeixinUserinfo;
import com.weixindev.micro.serv.common.pagination.Query;
import com.weixindev.micro.serv.common.util.DateUtil;
import com.weixindev.micro.serv.common.util.FileType;
import com.weixindev.micro.serv.common.util.FileTypeJudge;
import com.weixindev.micro.serv.common.util.StringUtil;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpMassNews;
import me.chanjar.weixin.mp.bean.WxMpMassTagMessage;
import me.chanjar.weixin.mp.bean.material.WxMediaImgUploadResult;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import me.chanjar.weixin.mp.bean.result.WxMpMassUploadResult;

public class TimingThread implements Runnable {

	private Map<String,String> map;
	
	@Autowired
	private Logger logger;
	@Autowired
	private WxOpenServiceDemo wxOpenServiceDemo;
	@Autowired
	WxMpService WxMpService;

	@Autowired
	WeixinUserinfoService WeixinUserinfoService;

	@Autowired
	private WeixinImgtextItemService weixinImgtextItemService;
	
	@Autowired
	private WeixinPushLogService weixinPushLogService ;
	
	@Autowired
	private WeixinImgService weixinImgService;
	
	@Value("${file_location}")
	private String file_location;// 文件存储路径
	@Value("${ctxAppWeixin}")
	private String ctxAppWeixin;// 微信网站路径
	@Value("${appURL}")
	private String appURL;// 网站前台url
	
	public void setMap(Map<String, String> map) {
		this.map = map;
	}


	@Override
	public void run() {
		String imgTextId = String.valueOf(map.get("imgTextId"));// 图文素材ID
		String ids = map.get("ids");// 选择的公众号

		List<String> idsList = StringUtil.splitStringToStringList(ids);
		logger.info("定时群发开始执行...");
		logger.info("StringUtil.splitStringToStringList :" + idsList.size());
		logger.info("群发消息 imgTextId:" + imgTextId + ",公众号ids:" + ids);
		StringBuffer sb = new StringBuffer("");
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("imgTextId", imgTextId);
			// 查看图文列表项数据
			Query query = new Query(params);
			List<WeixinImgtextItem> list = weixinImgtextItemService.selectByExample(query);

			logger.info("imgTextId= " + imgTextId + " 的记录数据是list.size :" + list.size());

			for (WeixinImgtextItem WeixinImgtextItem : list) {
				if (StringUtils.isBlank(WeixinImgtextItem.getHeadImg())) {
					logger.error("封面图片为空，不允许发送");
				}
			}

			
			for (String str : idsList) {
				int i = 0;
				if (StringUtils.isNotBlank(str)) {// 过滤授权状态为空的
					if (list != null) {
						String appId = str;
						
						WxMpMassNews news = new WxMpMassNews();
						try {
							for (WeixinImgtextItem WeixinImgtextItem : list) {
								
								String thumbMediaId = "";
								
								//替换图片 
								if (StringUtil.isNotBlank(WeixinImgtextItem.getArticleContent())) {// 更新media
									List<String> strContentList = FileMatchUtil
											.getImgSrcList(WeixinImgtextItem.getArticleContent());
									String retContent = "";
									retContent = WeixinImgtextItem.getArticleContent();
									WeixinImgtextItem.setNewContent(retContent);// 设置替换以后新的内容
	
									if (strContentList != null) {// 替换图片
										for (String strCont : strContentList) {
											System.out.println("图片strCont=" + strCont);
	
											String headImgRepl = strCont.replaceFirst(appURL, file_location);
	
											File fileTmp = new File(headImgRepl);
	
											if(fileTmp!=null &&fileTmp.exists())	{
												WxMediaImgUploadResult res = wxOpenServiceDemo.getWxOpenComponentService()
														.getWxMpServiceByAppid(str).getMaterialService()
														.mediaImgUpload(fileTmp);
												String url = res.getUrl();
												retContent = retContent.replaceFirst(strCont, url);// 替换后的内容
		
												WeixinImgtextItem.setNewContent(retContent);// 设置替换以后新的内容
												weixinImgtextItemService.updateByPrimaryKeySelective(WeixinImgtextItem);
												logger.info(" 替换图片img WeixinImgtextItem.getId 的ID为=" + WeixinImgtextItem.getId()
														+ ",的imgTextId=" + imgTextId + ", url:" + url + ",retContent:" + retContent);
											}else {
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
										        
												 URL netUrl = new URL(strCont);   
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
												String mediaType = WxConsts.MediaFileType.IMAGE;

												 fileTmp = new File(filePath);
												 WxMediaImgUploadResult res = wxOpenServiceDemo.getWxOpenComponentService()
															.getWxMpServiceByAppid(str).getMaterialService()
															.mediaImgUpload(fileTmp);
													String url = res.getUrl();
													retContent = retContent.replaceFirst(strCont, url);// 替换后的内容
					
													WeixinImgtextItem.setNewContent(retContent);// 设置替换以后新的内容
													weixinImgtextItemService.updateByPrimaryKeySelective(WeixinImgtextItem);
													logger.info(" 替换图片img WeixinImgtextItem.getId 的ID为=" + WeixinImgtextItem.getId()
															+ ",的imgTextId=" + imgTextId + ", url:" + res.getUrl() + ",retContent:"
															+ retContent);
											}
										}
									}
								}
									
								//上传图文
									try {
										URL fileUrl = new URL(WeixinImgtextItem.getHeadImg());

										URLConnection rulConnection = fileUrl.openConnection();// 此处的urlConnection对象实际上是根据URL的
										HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;

										InputStream inputStream = httpUrlConnection.getInputStream();

										// 动态判断mediaType
										String headImg = WeixinImgtextItem.getHeadImg();

										String mediaType = "image";// 图片类型的素材
										String fileType = StringUtil.getExtension(headImg);// 获得文件的扩展名

										WxMediaUploadResult res = wxOpenServiceDemo.getWxOpenComponentService()
												.getWxMpServiceByAppid(str).getMaterialService()
												.mediaUpload(mediaType, fileType, inputStream);

										String mediaId = res.getMediaId();

										logger.info("上传图文消息的封面的mediaId=" + mediaId + ",mediaType=" + mediaType + ",fileType=" + fileType
												+ ",headImg=" + headImg);
										
										thumbMediaId = mediaId;
										
										WeixinImgtextItem.setMediaId(mediaId);
										weixinImgtextItemService.updateByPrimaryKeySelective(WeixinImgtextItem);

										logger.info("上传图文消息的封面的图片  WeixinImgtextItem.getId 的ID为=" + WeixinImgtextItem.getId()
												+ ",的imgTextId=" + imgTextId + ", mediaId= " + mediaId);

										if (inputStream != null) {
											inputStream.close();
										}
									}
										catch(WxErrorException e) {
											 e.printStackTrace();
									    		Integer code = e.getError().getErrorCode();
												logger.info(str + "上传图文消息的封面的图片异常:" +e.getMessage() + ",异常信息:"+ WxMpErrorMsg.findMsgByCode(code)+"<br/>");
												sb.append("上传图文消息的封面的图片异常:"+ WxMpErrorMsg.findMsgByCode(code)+"<br/>");
									} catch (Exception e) {
										e.printStackTrace();
										logger.error("上传图文消息的封面的图片，异常信息:" + e.getMessage());
										logger.error("上传图文消息的封面的图片，异常信息:" + e.getMessage());
										sb.append("群发异常:" +e.getMessage()+"<br/>");
									}

								
								WxMpMassNews.WxMpMassNewsArticle article2 = new WxMpMassNews.WxMpMassNewsArticle();
								article2.setTitle(WeixinImgtextItem.getTitle());
								article2.setContent(WeixinImgtextItem.getNewContent());// 文章内容为替换以后的文章内容
								article2.setThumbMediaId(thumbMediaId);
								// if (i == 0) {// 默认第一张是封面
								// article2.setShowCoverPic(true);
								// } else {
								// article2.setShowCoverPic(false);
								// }
								article2.setShowCoverPic(false);
								article2.setAuthor(WeixinImgtextItem.getAuthor());
								article2.setContentSourceUrl(WeixinImgtextItem.getOriginUrl());
								article2.setDigest(WeixinImgtextItem.getIntro());
								news.addArticle(article2);
								i++;
							}

							WxMpMassUploadResult massUploadResult = wxOpenServiceDemo.getWxOpenComponentService()
									.getWxMpServiceByAppid(appId).getMassMessageService().massNewsUpload(news);

							logger.info("群发文件素材结果 type:" + massUploadResult.getType() + ",mediaId="
									+ massUploadResult.getMediaId());


							WxMpMassTagMessage WxMpMassTagMessage = new WxMpMassTagMessage();
							WxMpMassTagMessage.setMsgType("mpnews");// 群发 WxConsts.MASS_MSG_NEWS
							WxMpMassTagMessage.setMediaId(massUploadResult.getMediaId());
							// WxMpMassTagMessage.setSendAll(true);

							WxMpMassSendResult massResult = wxOpenServiceDemo.getWxOpenComponentService()
									.getWxMpServiceByAppid(appId).getMassMessageService()
									.massGroupMessageSend(WxMpMassTagMessage);
							
							
							WeixinUserinfo WeixinUserinfoFilter = WeixinUserinfoService.selectByauthorizerAppid(appId);
							
							WeixinPushLog weixinPushLog = new WeixinPushLog();
							weixinPushLog.setCategoryId("mpnews");//图文消息
							weixinPushLog.setMsgId(massResult.getMsgId());
							weixinPushLog.setMsgDataId(massResult.getMsgDataId());
							weixinPushLog.setCreateBy(1);
							weixinPushLog.setCreateByUname("admin01");
							weixinPushLog.setUserId(WeixinUserinfoFilter.getId());
							weixinPushLog.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
							weixinPushLog.setAuthorizerAppid(appId);
							
							weixinPushLogService.insertSelective(weixinPushLog);

							logger.info("群发消息str= " + str + ",结果：" + massResult.getErrorCode() + ","
									+ massResult.getErrorMsg() + "," + massResult.getMsgId());
						}catch(WxErrorException e) {
							 e.printStackTrace();
					    		Integer code = e.getError().getErrorCode();
								logger.info(str + "群发异常:" +e.getMessage() + ",异常信息:"+ WxMpErrorMsg.findMsgByCode(code)+"<br/>");
								sb.append("异常信息:"+ WxMpErrorMsg.findMsgByCode(code)+"<br/>");
					    }catch(Exception e){
							sb.append(str + "群发异常:" +e.getMessage()+"<br/>");
						}
						
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("群发异常，异常信息:" + e.getMessage());
			sb.append("群发异常:" +e.getMessage()+"<br/>");
		}

	}

}
