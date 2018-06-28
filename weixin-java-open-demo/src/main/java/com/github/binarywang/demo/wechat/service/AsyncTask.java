package com.github.binarywang.demo.wechat.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.github.binarywang.demo.wechat.utils.FileMatchUtil;
import com.github.binarywang.demo.wechat.utils.GIfUtil;
import com.lj.cloud.secrity.service.WeixinImgService;
import com.lj.cloud.secrity.service.WeixinImgtextItemService;
import com.lj.cloud.secrity.service.WeixinPushLogService;
import com.lj.cloud.secrity.service.WeixinSendHistoryService;
import com.lj.cloud.secrity.service.WeixinUserinfoService;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.WxMpErrorMsg;
import com.weixindev.micro.serv.common.bean.report.WeixinSendHistory;
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
import me.chanjar.weixin.mp.bean.material.WxMpMaterial;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import me.chanjar.weixin.mp.bean.result.WxMpMassUploadResult;

@Component
public class AsyncTask {
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
	private WeixinPushLogService weixinPushLogService;
	@Value("${appURL}")
	private String appURL;// 网站前台url

	@Value("${file_location}")
	private String file_location;// 文件存储路径
	@Value("${ctxAppWeixin}")
	private String ctxAppWeixin;// 微信网站路径
	@Autowired
	private WeixinImgService weixinImgService;
	@Autowired
	private WeixinSendHistoryService weixinSendHistoryService;

	@Autowired
	private RedisBusiness r;

	@Async
	public void BatchSyncAsyncTask(Map<String, String> map) {
		boolean isSuccess = true;
		WeixinSendHistory w = new WeixinSendHistory();
		w.setCategory(0);
		StringBuffer errorIds = new StringBuffer();
		StringBuffer errorNames = new StringBuffer();
		double count = 1;// 同步成功的微信公众号数量初始化
		StringBuffer sb = new StringBuffer("");
		String imgTextId = String.valueOf(map.get("imgTextId"));// 图文素材ID
		String ids = map.get("ids");// 选择的公众号
		String name = map.get("userName");
		String key = name + "tongbuNum";
		try {
			r.set(name + "isExecuting", "1");
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		w.setImgtextid(Integer.parseInt(imgTextId));
		w.setCreateDate(new Date());
		w.setCreateByUname(name);
		w.setToUserName(map.get("toUser"));
		w.setAuthorizerappid(ids);
		List<String> idsList = StringUtil.splitStringToStringList(ids);

		logger.info("StringUtil.splitStringToStringList :" + idsList.size());

		logger.info("同步消息 imgTextId:" + imgTextId + ",公众号ids:" + ids);

		/* try { */
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("imgTextId", imgTextId);
		// 查看图文列表项数据
		Query query = new Query(params);
		List<WeixinImgtextItem> list = weixinImgtextItemService.selectByExample(query);

		logger.info("imgTextId= " + imgTextId + " 的记录数据是list.size :" + list.size());
		for (WeixinImgtextItem WeixinImgtextItem : list) {
			if (StringUtils.isBlank(WeixinImgtextItem.getHeadImg())) {
				w.setExecuteResult("封面图片为空，不允许发送");
				return;
			}
		}
		NumberFormat aaa = NumberFormat.getPercentInstance();
		double number = 0 / idsList.size();
		String rates = aaa.format(number);
		try {
			r.setEx(key, rates, 600);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		Integer successCount = 0;// 同步成功条数
		Integer errorCount = 0;// 同步失败条数
		String status = name + "status";
		for (String str : idsList) {
			// (1)处理图文消息正文内部的图片、视频、音频等文件信息替换 上传图文消息的正文图片(返回的url拼在正文的<img>标签中)
			WeixinUserinfo weixin = WeixinUserinfoService.selectByauthorizerAppid(str);
			try {
				r.setEx(status, "正在同步:" + weixin.getNickName() + "...", 600);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			try {
				for (WeixinImgtextItem WeixinImgtextItem : list) {

					if (StringUtil.isNotBlank(WeixinImgtextItem.getArticleContent())) {// 更新media
						try {
							List<String> strContentList = FileMatchUtil
									.getImgSrcList(WeixinImgtextItem.getArticleContent());
							String retContent = "";
							retContent = WeixinImgtextItem.getArticleContent();
							WeixinImgtextItem.setNewContent(retContent);// 设置替换以后新的内容
							if (strContentList != null) {// 替换图片
								for (String strCon : strContentList) {
									System.out.println("图片strCon=" + strCon);

									String headImgRepl = strCon.replaceFirst(appURL, file_location);

									File fileTmp = new File(headImgRepl);

									if (fileTmp != null && fileTmp.exists()) {

										WxMediaImgUploadResult res = wxOpenServiceDemo.getWxOpenComponentService()
												.getWxMpServiceByAppid(str).getMaterialService()
												.mediaImgUpload(fileTmp);
										String url = res.getUrl();
										retContent = retContent.replace(strCon, url);// 替换后的内容

										WeixinImgtextItem.setNewContent(retContent);// 设置替换以后新的内容
										weixinImgtextItemService.updateByPrimaryKeySelective(WeixinImgtextItem);
										logger.info(" 替换图片img WeixinImgtextItem.getId 的ID为=" + WeixinImgtextItem.getId()
												+ ",的imgTextId=" + imgTextId + ", url:" + res.getUrl() + ",retContent:"
												+ retContent);
									} else {
										WeixinImgtextItem.setNewContent(retContent);// 设置替换以后新的内容
										logger.info(" 替换图片文件路径不存在" + headImgRepl + ",str=" + str);

										// 网络图片地址
										/*
										 * URL fileUrl = new URL(str); URLConnection rulConnection =
										 * fileUrl.openConnection();// 此处的urlConnection对象实际上是根据URL的 HttpURLConnection
										 * httpUrlConnection = (HttpURLConnection) rulConnection; InputStream
										 * inputStream = httpUrlConnection.getInputStream(); InputStream inputStream2 =
										 * httpUrlConnection.getInputStream();//两个流，防止被关闭
										 * 
										 * String extName = FileTypeJudge.getType2(inputStream).name().toLowerCase();
										 * String fileName = System.nanoTime()+"."+extName; String filePath =
										 * file_location +fileName;
										 * 
										 * FileUtil.createFile(inputStream2, filePath);
										 */

										URL netUrl = new URL(strCon);
										URLConnection rulConnection = netUrl.openConnection();// 此处的urlConnection对象实际上是根据URL的
										HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
										InputStream inputStream = httpUrlConnection.getInputStream();

										ByteArrayOutputStream baos = new ByteArrayOutputStream();
										byte[] buffer = new byte[1024];
										int len;
										while ((len = inputStream.read(buffer)) > -1) {
											baos.write(buffer, 0, len);
										}
										baos.flush();

										InputStream inputStream1 = new ByteArrayInputStream(baos.toByteArray());
										InputStream inputStream2 = new ByteArrayInputStream(baos.toByteArray());

										FileType FileType = FileTypeJudge.getType(inputStream1);

										String extName = FileType.name().toLowerCase();// 文件扩展名
										String fileName = System.nanoTime() + "." + extName;
										String filePath = file_location + fileName;

										if (FileType.GIF.name().toLowerCase().equals(extName)) {
											GIfUtil.saveGif(inputStream2, filePath);
										} else {
											BufferedImage image = null;
											image = ImageIO.read(netUrl);
											ImageIO.write(image, extName, new File(filePath));
										}

										WeixinImg weixinImg = new WeixinImg();
										Integer createBy = 1;
										weixinImg.setCreateBy(createBy);
										weixinImg.setCreateByUname("admin01");
										weixinImg.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());

										String headImg = appURL + "/" + fileName;
										weixinImg.setHeadImg(headImg);
										weixinImgService.insertSelective(weixinImg);

										WxMediaImgUploadResult res = wxOpenServiceDemo.getWxOpenComponentService()
												.getWxMpServiceByAppid(str).getMaterialService()
												.mediaImgUpload(new File(filePath));
										String url = res.getUrl();
										logger.info("old-retContent:" + retContent + ",url" + url + ",strCon:" + strCon);
										String oldRetContent = retContent;
										retContent = retContent.replaceFirst(escapeExprSpecialWord(strCon), url);// 替换后的内容
										if (oldRetContent.equals(retContent)) {
											// errorIds.append(str+",");
											// errorNames.append(weixin.getNickName()+",");
											// sb.append("同步失败公众号:"+weixin.getNickName()+"<br/>");
											// sb.append("失败原因:图片替换出现异常<br/>时间:"+DateUtil.getNowDateYYYYMMddHHMMSS()+"<br/>");
											logger.info("图片替换出现异常oldRetContent:" + oldRetContent + ",new-retContent:"
													+ retContent + ",url" + url + ",strCon:" + strCon);
											// errorCount++;
											// breaCk;
										}
										logger.info(
												"new-retContent:" + retContent + ",url" + url + ",strCon:" + strCon);
										WeixinImgtextItem.setNewContent(retContent);// 设置替换以后新的内容
										weixinImgtextItemService.updateByPrimaryKeySelective(WeixinImgtextItem);
										logger.info(" 替换图片img WeixinImgtextItem.getId 的ID为=" + WeixinImgtextItem.getId()
												+ ",的imgTextId=" + imgTextId + ", url:" + res.getUrl() + ",retContent:"
												+ retContent);
									}
								}
							}
						} catch (WxErrorException e) {
							e.printStackTrace();
							Integer code = e.getError().getErrorCode();
							if(isSuccess) {
								isSuccess = false;
								errorCount++;
								errorIds.append(str + ",");
								errorNames.append(weixin.getNickName() + ",");
								if (code != 0) {
									sb.append("同步失败公众号:" + weixin.getNickName() + "<br/>");
									sb.append("失败原因:" + WxMpErrorMsg.findMsgByCode(code) + "<br/>时间:"
											+ DateUtil.getNowDateYYYYMMddHHMMSS() + "<br/>");
								}else {
									sb.append("同步失败公众号:" + weixin.getNickName() + "<br/>");
									sb.append("失败原因:图片替换出现异常<br/>时间:"+ DateUtil.getNowDateYYYYMMddHHMMSS() + "<br/>");
								}
							}
								logger.info(str + "上传图文消息的封面的图片异常:" + e.getMessage() + ",异常信息:"
										+ WxMpErrorMsg.findMsgByCode(code) + "<br/>");
						} catch (Exception e) {
							e.printStackTrace();
							if(isSuccess) {
								isSuccess = false;
								errorCount++;
								errorIds.append(str + ",");
								errorNames.append(weixin.getNickName() + ",");
								e.printStackTrace();
								logger.error("处理图文消息正文内部的图片、视频、音频等文件信息替换，异常信息:" + e.getMessage());
								sb.append("同步失败公众号:" + weixin.getNickName() + "<br/>");
								sb.append("失败原因:图片替换出现异常<br/>时间:"+ DateUtil.getNowDateYYYYMMddHHMMSS() + "<br/>");
							}
							// if (!userName.equals(weixin.getNickName())) {
							// errorCount++;
							// userName=weixin.getNickName();
							// }
						}

					}
				}

				// (2) 上传图文消息的封面的图片
				for (WeixinImgtextItem WeixinImgtextItem : list) {
					if (StringUtil.isNotBlank(WeixinImgtextItem.getHeadImg())) {// 更新media
						try {
							// 动态判断mediaType
							String headImg = WeixinImgtextItem.getHeadImg();// 图片地址
							String headImgRepl = headImg.replaceFirst(appURL, file_location);
							String fileType = StringUtil.getExtension(headImg);// 获得文件的扩展名
							String fileName = headImg.substring(headImg.lastIndexOf("/") + 1);
							File fileTmp = new File(headImgRepl);
							if (!fileTmp.exists()) {
								throw new Exception("封面图片不存在,headImgRepl" + headImgRepl);
							}
							String mediaType = WxConsts.MediaFileType.THUMB;
							// 上传图片
							WxMpMaterial wxMpMaterial = new WxMpMaterial();
							wxMpMaterial.setFile(fileTmp);
							wxMpMaterial.setName(fileName);

							WxMpMaterialUploadResult res = wxOpenServiceDemo.getWxOpenComponentService()
									.getWxMpServiceByAppid(str).getMaterialService()
									.materialFileUpload(mediaType, wxMpMaterial);

							String mediaId = res.getMediaId();
							logger.info("上传图文消息的封面的mediaId=" + mediaId + ",mediaType=" + mediaType + ",fileType="
									+ fileType + ",headImg=" + headImg);
							WeixinImgtextItem.setMediaId(mediaId);
							weixinImgtextItemService.updateByPrimaryKeySelective(WeixinImgtextItem);
							logger.info("上传图文消息的封面的图片  WeixinImgtextItem.getId 的ID为=" + WeixinImgtextItem.getId()
									+ ",的imgTextId=" + imgTextId + ", mediaId= " + mediaId);
						} catch (WxErrorException e) {
							e.printStackTrace();
							Integer code = e.getError().getErrorCode();
							logger.info(str + "上传图文消息的封面的图片异常:" + e.getMessage() + ",异常信息:"
									+ WxMpErrorMsg.findMsgByCode(code) + "<br/>");
							if (isSuccess) {
								isSuccess = false;
								errorCount++;
								errorIds.append(str + ",");
								errorNames.append(weixin.getNickName() + ",");
								if(code!=0) {
									sb.append("同步失败公众号:" + weixin.getNickName() + "<br/>");
									sb.append("失败原因:" + WxMpErrorMsg.findMsgByCode(code));
								}else {
									sb.append("同步失败公众号:" + weixin.getNickName() + "<br/>");
									sb.append("失败原因:" + "上传封面失败<br/>时间:"+ DateUtil.getNowDateYYYYMMddHHMMSS() + "<br/>");
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							if (isSuccess) {
								errorCount++;
								isSuccess = false;
								sb.append("失败原因:" + "上传封面失败<br/>");
								errorIds.append(str + ",");
								errorNames.append(weixin.getNickName() + ",");
							}
							e.printStackTrace();
							logger.error("上传图文消息的封面的图片，异常信息:" + e.getMessage());
						}

					}
				}

				if (StringUtils.isNotBlank(str)) {// 过滤授权状态为空的
					if (list != null) {
						String appId = str;

						WxMpMaterialNews wxMpMaterialNewsSingle = new WxMpMaterialNews();

						for (WeixinImgtextItem WeixinImgtextItem : list) {

							WxMpMaterialNews.WxMpMaterialNewsArticle mpMaterialNewsArticleSingle = new WxMpMaterialNews.WxMpMaterialNewsArticle();
							mpMaterialNewsArticleSingle.setAuthor(WeixinImgtextItem.getAuthor());
							mpMaterialNewsArticleSingle.setThumbMediaId(WeixinImgtextItem.getMediaId());
							mpMaterialNewsArticleSingle.setTitle(WeixinImgtextItem.getTitle());
							mpMaterialNewsArticleSingle.setContent(WeixinImgtextItem.getNewContent());
							mpMaterialNewsArticleSingle.setContentSourceUrl(WeixinImgtextItem.getOriginUrl());
							mpMaterialNewsArticleSingle.setShowCoverPic(false);
							mpMaterialNewsArticleSingle.setDigest(WeixinImgtextItem.getIntro());
							wxMpMaterialNewsSingle.addArticle(mpMaterialNewsArticleSingle);
						}
						// try {
						WxMpMaterialUploadResult resSingle = wxOpenServiceDemo.getWxOpenComponentService()
								.getWxMpServiceByAppid(appId).getMaterialService()
								.materialNewsUpload(wxMpMaterialNewsSingle);

						logger.info("同步文件素材结果 url:" + resSingle.getUrl() + ",mediaId=" + resSingle.getMediaId());

						WeixinPushLog weixinPushLog = new WeixinPushLog();
						weixinPushLog.setCategoryId("mpnews");// 图文消息
						weixinPushLog.setMediaCategory("mpnews");// 永久素材
						weixinPushLog.setMediaId(resSingle.getMediaId());
						weixinPushLog.setImgTextId(Integer.parseInt(imgTextId));
						weixinPushLog.setCreateBy(1);
						weixinPushLog.setCreateByUname("admin01");

						weixinPushLog.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());

						weixinPushLog.setAuthorizerAppid(str);

						weixinPushLogService.insertSelective(weixinPushLog);
						// } catch(WxErrorException e) {
						// Integer code = e.getError().getErrorCode();
						// if (isSuccess) {
						// isSuccess = false;
						// sb.append("同步失败公众号:" + weixin.getNickName() + "<br/>");
						// sb.append("失败原因:" + WxMpErrorMsg.findMsgByCode(code));
						// if (!userName.equals(weixin.getNickName())) {
						// errorCount++;
						// userName = weixin.getNickName();
						// }
						// }
						// }catch (Exception e) {
						// e.printStackTrace();
						// sb.append("同步失败公众号:"+weixin.getNickName()+"<br/>");
						// sb.append("失败原因:【处理图文消息正文内部的媒体文件信息替换异常】<br/>时间:"+DateUtil.getNowDateYYYYMMddHHMMSS()+"<br/>");
						// }
					}
				}
				number = count / idsList.size();
				System.out.println("--------------------------" + count + "------------------------------------");
				rates = aaa.format(number);
				try {
					r.setEx(key, rates, 600);
					// r.setEx(key, value, expire);
				} catch (Exception e) {
					e.printStackTrace();
				}
				count++;
				successCount++;
			} catch (WxErrorException e) {
				e.printStackTrace();
				Integer code = e.getError().getErrorCode();
				if (isSuccess) {
					isSuccess = false;
					errorCount++;
					errorIds.append(str + ",");
					errorNames.append(weixin.getNickName() + ",");
					logger.error(str + "上传图文消息的封面的图片异常:" + e.getMessage() + ",异常信息:" + WxMpErrorMsg.findMsgByCode(code)
							+ "<br/>");
					if (code.equals(0)) {
						logger.error(e.getMessage());
						sb.append("同步失败公众号:" + weixin.getNickName() + "<br/>");
						sb.append("失败原因:上传素材失败<br/>时间:"+ DateUtil.getNowDateYYYYMMddHHMMSS() + "<br/>");
					} else {
						errorCount++;
						sb.append("同步失败公众号:" + weixin.getNickName() + "<br/>");
						sb.append("失败原因:" + WxMpErrorMsg.findMsgByCode(code) + "<br/>时间:"
								+ DateUtil.getNowDateYYYYMMddHHMMSS() + "<br/>");
					}
				}
			} catch (Exception e) {
				if (isSuccess) {
					isSuccess = false;
					errorCount++;
					sb.append("同步失败公众号:" + weixin.getNickName() + "<br/>");
					sb.append("失败原因:上传素材失败<br/>时间:"+ DateUtil.getNowDateYYYYMMddHHMMSS() + "<br/>");
					errorIds.append(str + ",");
					errorNames.append(weixin.getNickName() + ",");
					logger.error("处理图文消息正文内部的图片、视频、音频等文件信息替换，异常信息:" + e.getMessage());
				}
//				errorIds.append(str + ",");
//				errorNames.append(weixin.getNickName() + ",");
//				e.printStackTrace();
//				logger.error("处理图文消息正文内部的图片、视频、音频等文件信息替换，异常信息:" + e.getMessage());
//				errorCount++;
//				if (!userName.equals(weixin.getNickName())) {
//					userName = weixin.getNickName();
//				}
			}
		}
		try {
			r.setEx(name + "isExecuting", "0", 60);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (sb.toString() == null && sb.toString() == "") {
			sb.append("未知异常");
		}
		w.setExecuteResult(sb.toString());
		w.setErrorUserId(errorIds.toString());
		w.setErrorUserName(errorNames.toString());
		if (errorCount != 0) {
			sb.insert(0, "本次共同步公众号" + idsList.size() + "条;同步:" + successCount + "条;失败:" + errorCount
					+ "条</br><font  color=\"red\">失败信息明细:</font><br/>");
		} else {
			sb.insert(0, "本次共同步公众号" + idsList.size() + "条;同步:" + successCount + "条;失败:" + errorCount + "条</br>");
		}

		/*
		 * } catch (Exception e) { count++; e.printStackTrace();
		 * logger.error("同步异常，异常信息:" + e.getMessage()); restAPIResult.setRespCode(0);
		 * restAPIResult.setRespMsg("同步异常，异常信息:" + e.getMessage()); sb.append("同步异常:"+
		 * e.getMessage()+"<br/>"); }
		 */
		w.setStatus("同步:" + successCount + "条;失败:" + errorCount + "条");
		weixinSendHistoryService.insert(w);
		try {
			r.setEx(status, sb.toString(), 600);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	@Async
	public RestAPIResult2 doExexcuteBatchSend(Map<String, String> map) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		double count = 1;
		String imgTextId = String.valueOf(map.get("imgTextId"));// 图文素材ID
		String ids = map.get("ids");// 选择的公众号
		String name = map.get("userName");
		String key = name + "tongbuNum";
		Integer successCount = 0;// 同步成功条数
		Integer errorCount = 0;// 同步失败条数
		String status = name + "status";
		WeixinSendHistory w = new WeixinSendHistory();
		w.setCategory(1);
		StringBuffer errorIds = new StringBuffer();
		StringBuffer errorNames = new StringBuffer();
		w.setImgtextid(Integer.parseInt(imgTextId));
		w.setCreateDate(new Date());
		w.setToUserName(map.get("toUser"));
		w.setCreateByUname(name);
		w.setAuthorizerappid(ids);

		List<String> idsList = StringUtil.splitStringToStringList(ids);

		logger.info("StringUtil.splitStringToStringList :" + idsList.size());

		logger.info("群发消息 imgTextId:" + imgTextId + ",公众号ids:" + ids);

		StringBuffer sb = new StringBuffer("");
		StringBuffer mag = new StringBuffer("");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("imgTextId", imgTextId);
		// 查看图文列表项数据
		Query query = new Query(params);
		List<WeixinImgtextItem> list = weixinImgtextItemService.selectByExample(query);

		logger.info("imgTextId= " + imgTextId + " 的记录数据是list.size :" + list.size());

		for (WeixinImgtextItem WeixinImgtextItem : list) {
			if (StringUtils.isBlank(WeixinImgtextItem.getHeadImg())) {
				restAPIResult.setRespCode(0);
				restAPIResult.setRespMsg("封面图片为空，不允许发送");
				mag.append("封面图片为空，不允许发送");
				try {
					r.setEx(status, mag.toString(), 600);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				return restAPIResult;
			}
		}

		NumberFormat aaa = NumberFormat.getPercentInstance();
		double number = 0 / idsList.size();
		String rates = aaa.format(number);
		try {
			r.setEx(key, rates, 600);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		for (String str : idsList) {
			int i = 0;
			if (StringUtils.isNotBlank(str)) {// 过滤授权状态为空的
				if (list != null) {
					String appId = str;
					WeixinUserinfo weixin = WeixinUserinfoService.selectByauthorizerAppid(str);
					try {
						r.setEx(status, "发送到" + weixin.getNickName() + "...", 600);
					} catch (Exception e1) {
						e1.printStackTrace();
					}

					WxMpMassNews news = new WxMpMassNews();

					try {
						for (WeixinImgtextItem WeixinImgtextItem : list) {

							String thumbMediaId = "";

							// 替换图片
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

										if (fileTmp != null && fileTmp.exists()) {
											WxMediaImgUploadResult res = wxOpenServiceDemo.getWxOpenComponentService()
													.getWxMpServiceByAppid(str).getMaterialService()
													.mediaImgUpload(fileTmp);
											String url = res.getUrl();
											retContent = retContent.replace(strCont, url);// 替换后的内容

											WeixinImgtextItem.setNewContent(retContent);// 设置替换以后新的内容
											weixinImgtextItemService.updateByPrimaryKeySelective(WeixinImgtextItem);
											logger.info(" 替换图片img WeixinImgtextItem.getId 的ID为="
													+ WeixinImgtextItem.getId() + ",的imgTextId=" + imgTextId + ", url:"
													+ url + ",retContent:" + retContent);
										} else {
											logger.info(" 替换图片文件路径不存在" + headImgRepl + ",str=" + str);

											// 网络图片地址
											/*
											 * URL fileUrl = new URL(str); URLConnection rulConnection =
											 * fileUrl.openConnection();// 此处的urlConnection对象实际上是根据URL的
											 * HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
											 * InputStream inputStream = httpUrlConnection.getInputStream(); InputStream
											 * inputStream2 = httpUrlConnection.getInputStream();//两个流，防止被关闭
											 * 
											 * String extName =
											 * FileTypeJudge.getType2(inputStream).name().toLowerCase(); String fileName
											 * = System.nanoTime()+"."+extName; String filePath = file_location
											 * +fileName;
											 * 
											 * FileUtil.createFile(inputStream2, filePath);
											 */

											URL netUrl = new URL(strCont);
											URLConnection rulConnection = netUrl.openConnection();// 此处的urlConnection对象实际上是根据URL的
											HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
											InputStream inputStream = httpUrlConnection.getInputStream();

											ByteArrayOutputStream baos = new ByteArrayOutputStream();
											byte[] buffer = new byte[1024];
											int len;
											while ((len = inputStream.read(buffer)) > -1) {
												baos.write(buffer, 0, len);
											}
											baos.flush();

											InputStream inputStream1 = new ByteArrayInputStream(baos.toByteArray());
											InputStream inputStream2 = new ByteArrayInputStream(baos.toByteArray());

											FileType FileType = FileTypeJudge.getType(inputStream1);

											String extName = FileType.name().toLowerCase();// 文件扩展名
											String fileName = System.nanoTime() + "." + extName;
											String filePath = file_location + fileName;

											if (FileType.GIF.name().toLowerCase().equals(extName)) {
												GIfUtil.saveGif(inputStream2, filePath);
											} else {
												BufferedImage image = null;
												image = ImageIO.read(netUrl);
												ImageIO.write(image, extName, new File(filePath));
											}

											WeixinImg weixinImg = new WeixinImg();

											Integer createBy = 1;
											weixinImg.setCreateBy(createBy);
											weixinImg.setCreateByUname("admin01");
											weixinImg.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());

											String headImg = appURL + "/" + fileName;
											weixinImg.setHeadImg(headImg);
											weixinImgService.insertSelective(weixinImg);
											String mediaType = WxConsts.MediaFileType.IMAGE;

											fileTmp = new File(filePath);
											WxMediaImgUploadResult res = wxOpenServiceDemo.getWxOpenComponentService()
													.getWxMpServiceByAppid(str).getMaterialService()
													.mediaImgUpload(fileTmp);
											String url = res.getUrl();
											String oldRetContent = retContent;
											retContent = retContent.replaceFirst(escapeExprSpecialWord(strCont), url);// 替换后的内容
											if (oldRetContent.equals(retContent)) {
												// errorIds.append(str+",");
												// errorNames.append(weixin.getNickName()+",");
												// sb.append("同步失败公众号:"+weixin.getNickName()+"<br/>");
												logger.error(
														"图片替换出现异常oldRetContent:" + oldRetContent + ",new-retContent:"
																+ retContent + ",url" + url + ",strCon:" + strCont);
												// sb.append("失败原因:图片替换出现异常<br/>时间:"+DateUtil.getNowDateYYYYMMddHHMMSS()+"<br/>");
												// errorCount++;
												// break;
											}
											WeixinImgtextItem.setNewContent(retContent);// 设置替换以后新的内容
											weixinImgtextItemService.updateByPrimaryKeySelective(WeixinImgtextItem);
											logger.info(" 替换图片img WeixinImgtextItem.getId 的ID为="
													+ WeixinImgtextItem.getId() + ",的imgTextId=" + imgTextId + ", url:"
													+ res.getUrl() + ",retContent:" + retContent);
										}
									}
								}
							}

							// 上传图文
							// try {
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

							logger.info("上传图文消息的封面的mediaId=" + mediaId + ",mediaType=" + mediaType + ",fileType="
									+ fileType + ",headImg=" + headImg);

							thumbMediaId = mediaId;

							WeixinImgtextItem.setMediaId(mediaId);
							weixinImgtextItemService.updateByPrimaryKeySelective(WeixinImgtextItem);

							logger.info("上传图文消息的封面的图片  WeixinImgtextItem.getId 的ID为=" + WeixinImgtextItem.getId()
									+ ",的imgTextId=" + imgTextId + ", mediaId= " + mediaId);

							if (inputStream != null) {
								inputStream.close();
							}
							// }
							// catch(WxErrorException e) {
							// e.printStackTrace();
							// Integer code = e.getError().getErrorCode();
							// logger.info(str + "上传图文消息的封面的图片异常:" +e.getMessage() + ",异常信息:"+
							// WxMpErrorMsg.findMsgByCode(code)+"<br/>");
							// sb.append("上传图文消息的封面的图片异常:"+ WxMpErrorMsg.findMsgByCode(code)+"<br/>");
							// mag.append("上传图文消息的封面的图片异常:"+ WxMpErrorMsg.findMsgByCode(code)+"<br/>");
							// } catch (Exception e) {
							// e.printStackTrace();
							// logger.error("上传图文消息的封面的图片，异常信息:" + e.getMessage());
							// restAPIResult.setRespCode(0);
							// restAPIResult.setRespMsg("上传图文消息的封面的图片，异常信息:" + e.getMessage());
							// }

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
						weixinPushLog.setCategoryId("mpnews");// 图文消息
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
					} catch (WxErrorException e) {
						e.printStackTrace();
						Integer code = e.getError().getErrorCode();
						errorCount++;
						errorIds.append(str + ",");
						errorNames.append(weixin.getNickName() + ",");
						logger.info(str + "群发异常:" + e.getMessage() + ",异常信息:" + WxMpErrorMsg.findMsgByCode(code) + "<br/>");
						sb.append("异常信息:" + WxMpErrorMsg.findMsgByCode(code) + "<br/>");
						mag.append("群发失败公众号:" + weixin.getNickName() + "<br/>");
						String errorMsg = WxMpErrorMsg.findMsgByCode(code);
						mag.append("失败原因:<br/>");
						if (errorMsg.length() > 20) {
							int beginIndex = 0;
							int endIndex = 20;
							while (true) {
								if (endIndex > errorMsg.length()) {
									endIndex = errorMsg.length();
									if (endIndex == beginIndex) {
										break;
									}
									mag.append(errorMsg.substring(beginIndex, endIndex) + "<br/>");
									break;
								}
								mag.append(errorMsg.substring(beginIndex, endIndex) + "<br/>");
								beginIndex += 20;
								endIndex += 20;
							}
						} else {
							mag.append(errorMsg + "<br/>");
						}

					} catch (Exception e) {
						errorIds.append(str);
						errorNames.append(weixin.getNickName());
						sb.append(str + "群发异常:" + e.getMessage() + "<br/>");
						mag.append("群发失败公众号:" + weixin.getNickName() + "<br/>");
						mag.append(str + "群发异常:未知异常<br/>");
					}
				}
				try {
					r.setEx(name + "isExecuting", "0", 60);
				} catch (Exception e) {
					e.printStackTrace();
				}

				number = count / idsList.size();
				System.out.println("--------------------------" + count + "------------------------------------");
				rates = aaa.format(number);
				try {
					r.setEx(key, rates, 600);
				} catch (Exception e) {
					e.printStackTrace();
				}
				count++;
				successCount++;

			}
		}

		if (StringUtils.isNoneBlank(sb.toString())) {
			restAPIResult.setRespCode(0);
			restAPIResult.setRespMsg(sb.toString());
		}
		if (sb.toString() == null && sb.toString() == "") {
			sb.append("未知异常");
		}
		w.setExecuteResult(mag.toString());
		w.setErrorUserId(errorIds.toString());
		w.setErrorUserName(errorNames.toString());
		if (errorCount != 0) {
			mag.insert(0, "本次共群发公众号" + idsList.size() + "条;群发:" + successCount + "条;失败:" + errorCount
					+ "条</br><font  color=\"red\">失败信息明细:</font><br/>");
		} else {
			mag.insert(0, "本次共群发公众号" + idsList.size() + "条;群发:" + successCount + "条;失败:" + errorCount + "条</br>");
		}
		w.setStatus("同步:" + successCount + "条;失败:" + errorCount + "条");
		weixinSendHistoryService.insert(w);
		try {
			r.setEx(status, mag.toString(), 600);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return restAPIResult;
	}

	public static String escapeExprSpecialWord(String keyword) {
		if (StringUtils.isNotBlank(keyword)) {
			String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };
			for (String key : fbsArr) {
				if (keyword.contains(key)) {
					keyword = keyword.replace(key, "\\" + key);
				}
			}
		}
		return keyword;
	}
}
