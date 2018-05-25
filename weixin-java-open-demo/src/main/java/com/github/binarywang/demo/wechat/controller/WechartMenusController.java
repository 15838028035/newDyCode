package com.github.binarywang.demo.wechat.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.demo.wechat.service.WxOpenServiceDemo;
import com.github.binarywang.demo.wechat.service.util.StringUtil.weixinMenuActlistMapUtil;
import com.github.binarywang.demo.wechat.service.util.StringUtil.weixinMenuMapUtil;
import com.github.binarywang.demo.wechat.utils.FileMatchUtil;
import com.github.binarywang.demo.wechat.utils.GIfUtil;
import com.lj.cloud.secrity.service.WeixinAudioService;
import com.lj.cloud.secrity.service.WeixinImgService;
import com.lj.cloud.secrity.service.WeixinImgtextItemService;
import com.lj.cloud.secrity.service.WeixinImgtextService;
import com.lj.cloud.secrity.service.WeixinMenuItemService;
import com.lj.cloud.secrity.service.WeixinPushLogService;
import com.lj.cloud.secrity.service.WeixinUserinfoService;
import com.lj.cloud.secrity.service.WeixinVideoService;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.weixin.WeixinAudio;
import com.weixindev.micro.serv.common.bean.weixin.WeixinImg;
import com.weixindev.micro.serv.common.bean.weixin.WeixinImgtext;
import com.weixindev.micro.serv.common.bean.weixin.WeixinImgtextItem;
import com.weixindev.micro.serv.common.bean.weixin.WeixinMenuItem;
import com.weixindev.micro.serv.common.bean.weixin.WeixinPushLog;
import com.weixindev.micro.serv.common.bean.weixin.WeixinUserinfo;
import com.weixindev.micro.serv.common.bean.weixin.WeixinVideo;
import com.weixindev.micro.serv.common.pagination.Query;
import com.weixindev.micro.serv.common.util.DateUtil;
import com.weixindev.micro.serv.common.util.FileType;
import com.weixindev.micro.serv.common.util.FileTypeJudge;
import com.weixindev.micro.serv.common.util.StringUtil;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.material.WxMediaImgUploadResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterial;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews.WxMpMaterialNewsArticle;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialVideoInfoResult;
import me.chanjar.weixin.mp.bean.menu.WxMpGetSelfMenuInfoResult;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import me.chanjar.weixin.mp.bean.menu.WxMpSelfMenuInfo.WxMpSelfMenuButton;
import me.chanjar.weixin.open.bean.result.WxOpenAuthorizerInfoResult;

@RestController
@RequestMapping("/wechat/menu")
public class WechartMenusController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private WxOpenServiceDemo wxOpenServiceDemo;
	@Autowired
	public WeixinUserinfoService weixinUserinfoService;

	@Autowired
	private WeixinImgtextItemService weixinImgtextItemService;

	@Autowired
	private WeixinVideoService weixinVideoService;

	@Autowired
	private WeixinImgService weixinImgService;

	@Autowired
	private WeixinAudioService weixinAudioService;

	@Autowired
	private WeixinPushLogService weixinPushLogService;

	@Autowired
	private WeixinImgtextService weixinImgtextService;

	@Autowired
	private WeixinMenuItemService weixinMenuItemService;

	@Value("${appURL}")
	private String appURL;// 网站前台url

	@Value("${file_location}")
	private String file_location;// 文件存储路径
	@Value("${ctxAppWeixin}")
	private String ctxAppWeixin;// 微信网站路径

	/**
	 * <pre>
	 * 自定义菜单创建接口
	 * 详情请见：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141013&token=&lang=zh_CN
	 * 如果要创建个性化菜单，请设置matchrule属性
	 * 详情请见：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455782296&token=&lang=zh_CN
	 * </pre>
	 *
	 * @param menu
	 * @return 如果是个性化菜单，则返回menuid，否则返回null
	 */
	@PostMapping("/createtest")
	public String menuCreate(@RequestBody WxMenu menu, @RequestParam("id") Integer id) throws WxErrorException {
		WeixinUserinfo weixinUserinfo = weixinUserinfoService.selectByPrimaryKey(id);
		return wxOpenServiceDemo.getWxOpenComponentService().getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid())
				.getMenuService().menuCreate(menu);
	}

	/*
	 * 单个同步
	 */
	@PostMapping("/create")
	public RestAPIResult2 menuCreateSample(@RequestParam("authorizerAppid") String authorizerAppid,
			@RequestBody List<weixinMenuMapUtil> weixinMenuMapUtil) throws WxErrorException {
		RestAPIResult2 restAPIResult2 = new RestAPIResult2();
		restAPIResult2.setRespCode(0);
		restAPIResult2.setRespMsg("success");
		HashMap<String, Object> weixinUserinfo = weixinUserinfoService.selectByAppId(authorizerAppid);
		if (weixinUserinfo == null) {
			restAPIResult2.setRespCode(4001);
			restAPIResult2.setRespMsg("查询authorzizer失败");
			return restAPIResult2;
		}
		String cityid=weixinUserinfo.get("city_id")==null||weixinUserinfo.get("city_id").equals("")?"":weixinUserinfo.get("city_id")+"";
		String cinemaid=weixinUserinfo.get("cinema_id")==null||weixinUserinfo.get("cinema_id").equals("")?"":weixinUserinfo.get("cinema_id")+"";
		System.out.println("----------------------------城市ID"+cityid);
		System.out.println("----------------------------影院ID"+cinemaid);
		WxOpenAuthorizerInfoResult wxOpenAuthorizerInfoResult = wxOpenServiceDemo.getWxOpenComponentService()
				.getAuthorizerInfo(authorizerAppid);
		if (wxOpenAuthorizerInfoResult.getAuthorizerInfo().getVerifyTypeInfo() == -1) {
			restAPIResult2.setRespCode(5001);
			restAPIResult2.setRespMsg("请先对此公众号进行认证,再进行自定义菜单编辑");
			return restAPIResult2;
		}
		System.out.println("------------------------------------------");
		// [{"name":"添加菜单","type":"0","act_list":[],"subButtons":[{"name":"添加子菜单","type":"1","act_list":[{"type":""}],"subButtons":[]},{"name":"添加子菜单","type":"1","act_list":[{"type":""}],"subButtons":[]}]},{"name":"添加菜单","type":"0","act_list":[],"subButtons":[{"name":"添加子菜单","type":"1","act_list":[{"type":""}],"subButtons":[]},{"name":"添加子菜单","type":"1","act_list":[{"type":""}],"subButtons":[]}]},{"name":"添加菜单","type":"0","act_list":[{"type":""}]}]
		System.out.println(weixinMenuMapUtil);
		System.out.println("------------------------------------------");

		WxMenu menu = new WxMenu();

		for (weixinMenuMapUtil weixinMenuMapUtilMainMenu : weixinMenuMapUtil) {
			WxMenuButton MainButton = new WxMenuButton();
			MainButton.setName(weixinMenuMapUtilMainMenu.getName());
			if (weixinMenuMapUtilMainMenu.getSubButtons() != null
					&& weixinMenuMapUtilMainMenu.getSubButtons().size() > 0) {
				menu.getButtons().add(MainButton);
				for (weixinMenuMapUtil weixinMenuMapUtilSubMenu : weixinMenuMapUtilMainMenu.getSubButtons()) {
					String mediaType = weixinMenuMapUtilSubMenu.getAct_list().get(0).getType();
					WxMenuButton subMenuButton = new WxMenuButton();
					subMenuButton.setName(weixinMenuMapUtilSubMenu.getName());
					if (mediaType.equals("1")) {
						subMenuButton.setType("view");
						String url=weixinMenuMapUtilSubMenu.getAct_list().get(0).getValue();
						url=url.replace("{cityid}", cityid);
						System.out.println("----------------------------"+url);
						url=url.replace("{cinemaid}",cinemaid);
						System.out.println("----------------------------"+url);
						subMenuButton.setUrl(url);
					} else {
						String mediaMenuType = "media_id";
						String mediaId = "";
						switch (mediaType) {// <!--type 1超链接 10图文 2图片 3语音 15视频-->
						case "10":
							mediaId = newEverImgText(weixinMenuMapUtilSubMenu.getAct_list().get(0).getValue(),
									authorizerAppid);
							if (mediaId == null) {
								restAPIResult2.setRespCode(4002);
								restAPIResult2.setRespMsg("新增永久图文素材失败");
								return restAPIResult2;
							}
							break;
						case "2":
							mediaId = newEverImage(weixinMenuMapUtilSubMenu.getAct_list().get(0).getValue(),
									authorizerAppid);
							if (mediaId == null) {
								restAPIResult2.setRespCode(4003);
								restAPIResult2.setRespMsg("新增永久图片素材失败");
								return restAPIResult2;
							}
							break;
						case "3":
							mediaId = newEverVoice(weixinMenuMapUtilSubMenu.getAct_list().get(0).getValue(),
									authorizerAppid);
							if (mediaId == null) {
								restAPIResult2.setRespCode(4004);
								restAPIResult2.setRespMsg("新增永久音频素材失败");
								return restAPIResult2;
							}
							break;
						case "15":
							mediaId = newEverVedio(weixinMenuMapUtilSubMenu.getAct_list().get(0).getValue(),
									authorizerAppid);
							if (mediaId == null) {
								restAPIResult2.setRespCode(4005);
								restAPIResult2.setRespMsg("新增永久视频素材失败");
								return restAPIResult2;
							}
							break;
						}
						subMenuButton.setType(mediaMenuType);
						subMenuButton.setMediaId(mediaId);
					}
					MainButton.getSubButtons().add(subMenuButton);
				}
			} else {
				String mediaType = weixinMenuMapUtilMainMenu.getAct_list().get(0).getType();
				if (mediaType.equals("1")) {
					MainButton.setType("view");
					String url=weixinMenuMapUtilMainMenu.getAct_list().get(0).getValue();
					url=url.replace("{cityid}", cityid);
					url=url.replace("{cinemaid}",cinemaid);
					MainButton.setUrl(url);
				} else {
					String mediaMenuType = "media_id";
					String mediaId = "";
					switch (mediaType) {// <!--type 10图文 2图片 3语音 15视频-->
					case "10":
						mediaId = newEverImgText(weixinMenuMapUtilMainMenu.getAct_list().get(0).getValue(),
								authorizerAppid);
						if (mediaId == null) {
							restAPIResult2.setRespCode(4002);
							restAPIResult2.setRespMsg("新增永久图文素材失败");
							return restAPIResult2;
						}
						break;
					case "2":
						mediaId = newEverImage(weixinMenuMapUtilMainMenu.getAct_list().get(0).getValue(),
								authorizerAppid);
						if (mediaId == null) {
							restAPIResult2.setRespCode(4003);
							restAPIResult2.setRespMsg("新增永久图片素材失败");
							return restAPIResult2;
						}
						break;
					case "3":
						mediaId = newEverVoice(weixinMenuMapUtilMainMenu.getAct_list().get(0).getValue(),
								authorizerAppid);
						if (mediaId == null) {
							restAPIResult2.setRespCode(4004);
							restAPIResult2.setRespMsg("新增永久音频素材失败");
							return restAPIResult2;
						}
						break;
					case "15":
						mediaId = newEverVedio(weixinMenuMapUtilMainMenu.getAct_list().get(0).getValue(),
								authorizerAppid);
						if (mediaId == null) {
							restAPIResult2.setRespCode(4005);
							restAPIResult2.setRespMsg("新增永久视频素材失败");
							return restAPIResult2;
						}
						break;
					}
					MainButton.setType(mediaMenuType);
					MainButton.setMediaId(mediaId);
				}
				menu.getButtons().add(MainButton);
			}
		}

		System.out.println("-----------menu打印输出最终结果-------------------------------");
		// [{"name":"添加菜单","type":"0","act_list":[],"subButtons":[{"name":"添加子菜单","type":"1","act_list":[{"type":""}],"subButtons":[]},{"name":"添加子菜单","type":"1","act_list":[{"type":""}],"subButtons":[]}]},{"name":"添加菜单","type":"0","act_list":[],"subButtons":[{"name":"添加子菜单","type":"1","act_list":[{"type":""}],"subButtons":[]},{"name":"添加子菜单","type":"1","act_list":[{"type":""}],"subButtons":[]}]},{"name":"添加菜单","type":"0","act_list":[{"type":""}]}]
		String jb = JSONObject.toJSONString(menu);
		System.out.println(jb);
		System.out.println("------------------------------------------");
		try {
			String wxmpmenuResString = wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(authorizerAppid).getMenuService().menuCreate(menu);
			if (wxmpmenuResString == null) {
				return restAPIResult2;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("菜单同步失败，异常信息:" + e.getMessage());
			restAPIResult2.setRespCode(4006);
			restAPIResult2.setRespMsg("菜单同步失败");
			return restAPIResult2;
		}
		return restAPIResult2;
	}

	/*
	 * 批量同步
	 */
	@PostMapping("/createbatch")
	public RestAPIResult2 menuCreateSampleBatch(@RequestParam Map<String, String> map) throws WxErrorException {
		RestAPIResult2 restAPIResult2 = new RestAPIResult2();
		restAPIResult2.setRespCode(0);
		restAPIResult2.setRespMsg("success");

		String menuid = String.valueOf(map.get("menuid"));// 音频ID
		String ids = map.get("ids");// 选择的公众号

		List<String> idsList = StringUtil.splitStringToStringList(ids);

		logger.info("StringUtil.splitStringToStringList :" + idsList.size());

		logger.info("批量同步菜单:" + menuid + ",公众号ids:" + ids);
		
		WeixinMenuItem weixinMenuItem = weixinMenuItemService.selectByPrimaryKey(Integer.parseInt(menuid));

		String menuJsonString = weixinMenuItem.getMenucontent();
		
		List<weixinMenuMapUtil> weixinMenuMapUtil = JSONArray.parseArray(menuJsonString, weixinMenuMapUtil.class);

		System.out.println("---------------------------------");
		System.out.println(weixinMenuMapUtil);
		for (weixinMenuMapUtil weixinMenuMapUtilMainMenutest : weixinMenuMapUtil) {
			System.out.println(weixinMenuMapUtilMainMenutest);
		}
		System.out.println("---------------------------------");
		
		for (String str : idsList) {
			HashMap<String, Object> weixinUserinfo = weixinUserinfoService.selectByAppId(str);
			if (weixinUserinfo == null) {
				restAPIResult2.setRespCode(4001);
				restAPIResult2.setRespMsg("查询有效公众号失败,请检查所勾选的公众号权限和有效性");
				return restAPIResult2;
			}
			String cityid=weixinUserinfo.get("city_id")==null||weixinUserinfo.get("city_id").equals("")?"":weixinUserinfo.get("city_id")+"";
			String cinemaid=weixinUserinfo.get("cinema_id")==null||weixinUserinfo.get("cinema_id").equals("")?"":weixinUserinfo.get("cinema_id")+"";
			System.out.println("----------------------------城市ID"+cityid);
			System.out.println("----------------------------影院ID"+cinemaid);
			WxOpenAuthorizerInfoResult wxOpenAuthorizerInfoResult = wxOpenServiceDemo.getWxOpenComponentService()
					.getAuthorizerInfo(str);
			if (wxOpenAuthorizerInfoResult.getAuthorizerInfo().getVerifyTypeInfo() == -1) {
				restAPIResult2.setRespCode(5001);
				restAPIResult2.setRespMsg("请先对所勾选的公众号进行认证,再进行自定义菜单编辑");
				return restAPIResult2;
			}
			WxMenu menu = new WxMenu();
			for (weixinMenuMapUtil weixinMenuMapUtilMainMenu : weixinMenuMapUtil) {
				WxMenuButton MainButton = new WxMenuButton();
				MainButton.setName(weixinMenuMapUtilMainMenu.getName());
				if (weixinMenuMapUtilMainMenu.getSubButtons() != null
						&& weixinMenuMapUtilMainMenu.getSubButtons().size() > 0) {
					menu.getButtons().add(MainButton);
					for (weixinMenuMapUtil weixinMenuMapUtilSubMenu : weixinMenuMapUtilMainMenu.getSubButtons()) {
						String mediaType = weixinMenuMapUtilSubMenu.getAct_list().get(0).getType();
						WxMenuButton subMenuButton = new WxMenuButton();
						subMenuButton.setName(weixinMenuMapUtilSubMenu.getName());
						if (mediaType.equals("1")) {
							subMenuButton.setType("view");
							String url=weixinMenuMapUtilSubMenu.getAct_list().get(0).getValue();
							url=url.replace("{cityid}", cityid);
							url=url.replace("{cinemaid}",cinemaid);
							subMenuButton.setUrl(url);
							System.out.println("----------------------------"+url);
						} else {
							String mediaMenuType = "media_id";
							String mediaId = "";
							switch (mediaType) {// <!--type 1超链接 10图文 2图片 3语音 15视频-->
							case "10":
								mediaId = newEverImgText(weixinMenuMapUtilSubMenu.getAct_list().get(0).getValue(),
										str);
								if (mediaId == null) {
									restAPIResult2.setRespCode(4002);
									restAPIResult2.setRespMsg("新增永久图文素材失败,请重新编辑自定菜单中子菜单所选图文素材");
									return restAPIResult2;
								}
								break;
							case "2":
								mediaId = newEverImage(weixinMenuMapUtilSubMenu.getAct_list().get(0).getValue(),
										str);
								if (mediaId == null) {
									restAPIResult2.setRespCode(4003);
									restAPIResult2.setRespMsg("新增永久图片素材失败,请重新编辑自定菜单中子菜单所选图片素材");
									return restAPIResult2;
								}
								break;
							case "3":
								mediaId = newEverVoice(weixinMenuMapUtilSubMenu.getAct_list().get(0).getValue(),
										str);
								if (mediaId == null) {
									restAPIResult2.setRespCode(4004);
									restAPIResult2.setRespMsg("新增永久音频素材失败,请重新编辑自定菜单中子菜单所选音频素材");
									return restAPIResult2;
								}
								break;
							case "15":
								mediaId = newEverVedio(weixinMenuMapUtilSubMenu.getAct_list().get(0).getValue(),
										str);
								if (mediaId == null) {
									restAPIResult2.setRespCode(4005);
									restAPIResult2.setRespMsg("新增永久视频素材失败,请重新编辑自定菜单中子菜单所选视频素材");
									return restAPIResult2;
								}
								break;
							}
							subMenuButton.setType(mediaMenuType);
							subMenuButton.setMediaId(mediaId);
						}
						MainButton.getSubButtons().add(subMenuButton);
					}
				} else {
					String mediaType = weixinMenuMapUtilMainMenu.getAct_list().get(0).getType();
					if (mediaType.equals("1")) {
						MainButton.setType("view");
						String url=weixinMenuMapUtilMainMenu.getAct_list().get(0).getValue();
						url=url.replace("{cityid}", cityid);
						url=url.replace("{cinemaid}",cinemaid);
						System.out.println("----------------------------"+url);
						MainButton.setUrl(url);
					} else {
						String mediaMenuType = "media_id";
						String mediaId = "";
						switch (mediaType) {// <!--type 10图文 2图片 3语音 15视频-->
						case "10":
							mediaId = newEverImgText(weixinMenuMapUtilMainMenu.getAct_list().get(0).getValue(),
									str);
							if (mediaId == null) {
								restAPIResult2.setRespCode(4002);
								restAPIResult2.setRespMsg("新增永久图文素材失败,请重新编辑自定菜单的主菜单中中所选图文素材");
								return restAPIResult2;
							}
							break;
						case "2":
							mediaId = newEverImage(weixinMenuMapUtilMainMenu.getAct_list().get(0).getValue(),
									str);
							if (mediaId == null) {
								restAPIResult2.setRespCode(4003);
								restAPIResult2.setRespMsg("新增永久图片素材失败,请重新编辑自定菜单的主菜单中中所选图片素材");
								return restAPIResult2;
							}
							break;
						case "3":
							mediaId = newEverVoice(weixinMenuMapUtilMainMenu.getAct_list().get(0).getValue(),
									str);
							if (mediaId == null) {
								restAPIResult2.setRespCode(4004);
								restAPIResult2.setRespMsg("新增永久音频素材失败,请重新编辑自定菜单的主菜单中中所选音频素材");
								return restAPIResult2;
							}
							break;
						case "15":
							mediaId = newEverVedio(weixinMenuMapUtilMainMenu.getAct_list().get(0).getValue(),
									str);
							if (mediaId == null) {
								restAPIResult2.setRespCode(4005);
								restAPIResult2.setRespMsg("新增永久视频素材失败,请重新编辑自定菜单的主菜单中中所选视频素材");
								return restAPIResult2;
							}
							break;
						}
						MainButton.setType(mediaMenuType);
						MainButton.setMediaId(mediaId);
					}
					menu.getButtons().add(MainButton);
				}
			}

			System.out.println("-----------menu打印输出最终结果-------------------------------");
			// [{"name":"添加菜单","type":"0","act_list":[],"subButtons":[{"name":"添加子菜单","type":"1","act_list":[{"type":""}],"subButtons":[]},{"name":"添加子菜单","type":"1","act_list":[{"type":""}],"subButtons":[]}]},{"name":"添加菜单","type":"0","act_list":[],"subButtons":[{"name":"添加子菜单","type":"1","act_list":[{"type":""}],"subButtons":[]},{"name":"添加子菜单","type":"1","act_list":[{"type":""}],"subButtons":[]}]},{"name":"添加菜单","type":"0","act_list":[{"type":""}]}]
			String jb = JSONObject.toJSONString(menu);
			System.out.println(jb);
			System.out.println("------------------------------------------");
			try {
				String wxmpmenuResString = wxOpenServiceDemo.getWxOpenComponentService()
						.getWxMpServiceByAppid(str).getMenuService().menuCreate(menu);
				if (wxmpmenuResString == null) {
					return restAPIResult2;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("菜单同步失败，异常信息:" + e.getMessage());
				restAPIResult2.setRespCode(4006);
				restAPIResult2.setRespMsg("菜单同步失败");
				return restAPIResult2;
			}
			return restAPIResult2;
		}
		return restAPIResult2;
	}

	private String newEverVedio(String value, String authorizerAppid) {
		String videoId = value;// 视频ID
		String appId = authorizerAppid;// 选择的公众号

		logger.info("同步消息视频videoId:" + videoId + ",公众号appId:" + appId);

		Integer id = Integer.parseInt(videoId);
		WeixinVideo weixinVideo = weixinVideoService.selectByPrimaryKey(id);

		if (weixinVideo == null || (weixinVideo != null && StringUtil.isBlank(weixinVideo.getHeadImg()))) {
			return null;
		}

		String mediaId = "";
		String fileType = "";
		// 刷新accessToken
		try {
			if (StringUtils.isNotBlank(appId)) {// 过滤授权状态为空的
				// 动态判断mediaType
				String headImg = weixinVideo.getHeadImg();// 视频地址
				String headImgRepl = headImg.replaceFirst(appURL, file_location);

				fileType = StringUtil.getExtension(headImg);// 获得文件的扩展名

				String fileName = headImg.substring(headImg.lastIndexOf("/") + 1);
				File fileTmp = new File(headImgRepl);
				if (!fileTmp.exists()) {
					return null;
				}
				String mediaType = WxConsts.MaterialType.VIDEO;
				String videoTitle = weixinVideo.getTitle();
				// 上传视频video
				WxMpMaterial wxMpMaterial = new WxMpMaterial();
				wxMpMaterial.setFile(fileTmp);
				wxMpMaterial.setName(fileName);
				wxMpMaterial.setVideoTitle(videoTitle);
				wxMpMaterial.setVideoIntroduction(weixinVideo.getIntro());

				WxMpMaterialUploadResult uploadMediaRes = wxOpenServiceDemo.getWxOpenComponentService()
						.getWxMpServiceByAppid(appId).getMaterialService().materialFileUpload(mediaType, wxMpMaterial);

				mediaId = uploadMediaRes.getMediaId();

				logger.info("上传视频消息的mediaId=" + mediaId + ",fileType=" + fileType + ",headImg=" + headImg);

				WeixinPushLog weixinPushLog = new WeixinPushLog();
				weixinPushLog.setCategoryId(mediaType);// 视频消息
				weixinPushLog.setMediaCategory("1");// 永久素材
				weixinPushLog.setMediaId(uploadMediaRes.getMediaId());
				weixinPushLog.setImgTextId(weixinVideo.getId());
				weixinPushLog.setCreateBy(1);
				weixinPushLog.setCreateByUname("admin01");

				weixinPushLog.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());

				weixinPushLog.setAuthorizerAppid(authorizerAppid);

				weixinPushLogService.insertSelective(weixinPushLog);

				return uploadMediaRes.getMediaId();

			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传视频消息，异常信息:" + e.getMessage());
			return null;
		}

		return null;
	}

	private String newEverVoice(String value, String authorizerAppid) {
		try {
			String audioId = value;// 音频ID
			String appId = authorizerAppid;// 选择的公众号

			logger.info("群发消息音频audioId:" + audioId + ",公众号appId:" + appId);

			Integer id = Integer.parseInt(audioId);
			WeixinAudio weixinAudio = weixinAudioService.selectByPrimaryKey(id);

			if (weixinAudio == null || (weixinAudio != null && StringUtil.isBlank(weixinAudio.getHeadImg()))) {
				return null;
			}

			String mediaId = "";
			String fileType = "";
			// 刷新accessToken
			if (StringUtils.isNotBlank(appId)) {// 过滤授权状态为空的

				// 动态判断mediaType
				String headImg = weixinAudio.getHeadImg();// 音频地址
				String headImgRepl = headImg.replaceFirst(appURL, file_location);

				fileType = StringUtil.getExtension(headImg);// 获得文件的扩展名

				// 上传音频audio

				File fileTmp = new File(headImgRepl);
				String mediaType = WxConsts.MediaFileType.VOICE;
				String name = headImg.substring(headImg.lastIndexOf("/") + 1);

				WxMpMaterial wxMpMaterial = new WxMpMaterial();
				wxMpMaterial.setFile(fileTmp);
				wxMpMaterial.setName(name);
				WxMpMaterialUploadResult uploadMediaRes = wxOpenServiceDemo.getWxOpenComponentService()
						.getWxMpServiceByAppid(appId).getMaterialService().materialFileUpload(mediaType, wxMpMaterial);

				mediaId = uploadMediaRes.getMediaId();

				logger.info("上传音频消息的mediaId=" + mediaId + ",fileType=" + fileType + ",音频地址headImg=" + headImg);

				WeixinPushLog weixinPushLog = new WeixinPushLog();
				weixinPushLog.setCategoryId(mediaType);// 音频消息
				weixinPushLog.setMediaCategory("1");// 永久素材
				weixinPushLog.setMediaId(uploadMediaRes.getMediaId());
				weixinPushLog.setImgTextId(weixinAudio.getId());
				weixinPushLog.setCreateBy(1);
				weixinPushLog.setCreateByUname("admin01");

				weixinPushLog.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());

				weixinPushLog.setAuthorizerAppid(authorizerAppid);

				weixinPushLogService.insertSelective(weixinPushLog);

				return uploadMediaRes.getMediaId();

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传音频消息，异常信息:" + e.getMessage());
			return null;
		}
		return null;
	}

	private String newEverImage(String value, String authorizerAppid) {

		Integer imgId = Integer.parseInt(value);
		String appId = authorizerAppid;// 选择的公众号
		WeixinImg weixinImg = weixinImgService.selectByPrimaryKey(imgId);

		try {
			logger.info("weixinImgId= " + imgId);
			if (StringUtils.isNotBlank(appId)) {// 过滤授权状态为空的
				if (StringUtils.isBlank(weixinImg.getHeadImg())) {
					return null;
				}

				// 动态判断mediaType
				String headImg = weixinImg.getHeadImg();// 图片地址
				String headImgRepl = headImg.replaceFirst(appURL, file_location);

				String fileType = StringUtil.getExtension(headImg);// 获得文件的扩展名

				String fileName = headImg.substring(headImg.lastIndexOf("/") + 1);
				File fileTmp = new File(headImgRepl);
				if (!fileTmp.exists()) {
					return null;
				}
				String mediaType = WxConsts.MaterialType.IMAGE;

				// 上传图片
				WxMpMaterial wxMpMaterial = new WxMpMaterial();
				wxMpMaterial.setFile(fileTmp);
				wxMpMaterial.setName(fileName);
				WxMpMaterialUploadResult uploadMediaRes = wxOpenServiceDemo.getWxOpenComponentService()
						.getWxMpServiceByAppid(appId).getMaterialService().materialFileUpload(mediaType, wxMpMaterial);

				String mediaId = uploadMediaRes.getMediaId();

				logger.info("上传图片的mediaId=" + mediaId + ",fileType=" + fileType + ",headImg=" + headImg);

				WeixinPushLog weixinPushLog = new WeixinPushLog();
				weixinPushLog.setCategoryId(mediaType);// 图文消息
				weixinPushLog.setMediaCategory("1");// 永久素材
				weixinPushLog.setMediaId(uploadMediaRes.getMediaId());
				weixinPushLog.setImgTextId(imgId);
				weixinPushLog.setCreateBy(1);
				weixinPushLog.setCreateByUname("admin01");

				weixinPushLog.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());

				weixinPushLog.setAuthorizerAppid(authorizerAppid);

				weixinPushLogService.insertSelective(weixinPushLog);

				return uploadMediaRes.getMediaId();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("新增永久图片异常" + e.getMessage());
			return null;
		}
		return null;
	}

	private String newEverImgText(String value, String authorizerAppid) {
		String imgTextId = value;// 图文素材ID
		String appid = authorizerAppid;// 选择的公众号appid

		logger.info("同步消息 imgTextId:" + imgTextId + ",公众号appid:" + authorizerAppid);

		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("imgTextId", imgTextId);
			// 查看图文列表项数据
			Query query = new Query(params);
			List<WeixinImgtextItem> list = weixinImgtextItemService.selectByExample(query);

			logger.info("imgTextId= " + imgTextId + " 的记录数据是list.size :" + list.size());

			for (WeixinImgtextItem WeixinImgtextItem : list) {
				if (StringUtils.isBlank(WeixinImgtextItem.getHeadImg())) {
					return null;
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

						if (strContentList != null) {// 替换图片
							for (String strCon : strContentList) {
								System.out.println("图片strCon=" + strCon);

								String headImgRepl = strCon.replaceFirst(appURL, file_location);

								File fileTmp = new File(headImgRepl);

								if (fileTmp != null && fileTmp.exists()) {
									WxMediaImgUploadResult res = wxOpenServiceDemo.getWxOpenComponentService()
											.getWxMpServiceByAppid(authorizerAppid).getMaterialService()
											.mediaImgUpload(fileTmp);
									String url = res.getUrl();
									retContent = retContent.replaceFirst(strCon, url);// 替换后的内容

									WeixinImgtextItem.setNewContent(retContent);// 设置替换以后新的内容
									weixinImgtextItemService.updateByPrimaryKeySelective(WeixinImgtextItem);
									logger.info(" 替换图片img WeixinImgtextItem.getId 的ID为=" + WeixinImgtextItem.getId()
											+ ",的imgTextId=" + imgTextId + ", url:" + res.getUrl() + ",retContent:"
											+ retContent);
								} else {
									logger.info(" 替换图片文件路径不存在" + headImgRepl + ",str=" + strCon);

									String extName = StringUtil.getExtension(strCon).toLowerCase();// 文件扩展名
									String fileName = System.nanoTime() + "." + extName;
									String filePath = file_location + fileName;

									URL netUrl = new URL(strCon);

									if (FileType.GIF.name().toLowerCase().equals(extName)) {
										URLConnection rulConnection = netUrl.openConnection();// 此处的urlConnection对象实际上是根据URL的
										HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
										InputStream inputStream = httpUrlConnection.getInputStream();

										GIfUtil.saveGif(inputStream, filePath);
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

									fileTmp = new File(filePath);
									WxMediaImgUploadResult res = wxOpenServiceDemo.getWxOpenComponentService()
											.getWxMpServiceByAppid(authorizerAppid).getMaterialService()
											.mediaImgUpload(fileTmp);
									String url = res.getUrl();
									retContent = retContent.replaceFirst(strCon, url);// 替换后的内容

									WeixinImgtextItem.setNewContent(retContent);// 设置替换以后新的内容
									weixinImgtextItemService.updateByPrimaryKeySelective(WeixinImgtextItem);
									logger.info(" 替换图片img WeixinImgtextItem.getId 的ID为=" + WeixinImgtextItem.getId()
											+ ",的imgTextId=" + imgTextId + ", url:" + res.getUrl() + ",retContent:"
											+ retContent);
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("处理图文消息正文内部的图片、视频、音频等文件信息替换，异常信息:" + e.getMessage());
						return null;
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
							return null;
						}
						String mediaType = WxConsts.MediaFileType.THUMB;

						// 上传图片
						WxMpMaterial wxMpMaterial = new WxMpMaterial();
						wxMpMaterial.setFile(fileTmp);
						wxMpMaterial.setName(fileName);

						WxMpMaterialUploadResult res = wxOpenServiceDemo.getWxOpenComponentService()
								.getWxMpServiceByAppid(appid).getMaterialService()
								.materialFileUpload(mediaType, wxMpMaterial);

						String mediaId = res.getMediaId();
						logger.info("上传图文消息的封面的mediaId=" + mediaId + ",mediaType=" + mediaType + ",fileType=" + fileType
								+ ",headImg=" + headImg);

						WeixinImgtextItem.setMediaId(mediaId);
						weixinImgtextItemService.updateByPrimaryKeySelective(WeixinImgtextItem);

						logger.info("上传图文消息的封面的图片  WeixinImgtextItem.getId 的ID为=" + WeixinImgtextItem.getId()
								+ ",的imgTextId=" + imgTextId + ", mediaId= " + mediaId);

					} catch (Exception e) {
						e.printStackTrace();
						logger.error("上传图文消息的封面的图片，异常信息:" + e.getMessage());
					}

				}
			}

			if (StringUtils.isNotBlank(appid)) {// 过滤授权状态为空的
				if (list != null) {
					String appId = appid;

					try {

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

						WxMpMaterialUploadResult resSingle = wxOpenServiceDemo.getWxOpenComponentService()
								.getWxMpServiceByAppid(appId).getMaterialService()
								.materialNewsUpload(wxMpMaterialNewsSingle);

						logger.info("同步文件素材结果 url:" + resSingle.getUrl() + ",mediaId=" + resSingle.getMediaId());

						WeixinPushLog weixinPushLog = new WeixinPushLog();
						weixinPushLog.setCategoryId("mpnews");// 图文消息
						weixinPushLog.setMediaCategory("1");// 永久素材
						weixinPushLog.setMediaId(resSingle.getMediaId());
						weixinPushLog.setImgTextId(Integer.parseInt(imgTextId));
						weixinPushLog.setCreateBy(1);
						weixinPushLog.setCreateByUname("admin01");

						weixinPushLog.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());

						weixinPushLog.setAuthorizerAppid(authorizerAppid);

						weixinPushLogService.insertSelective(weixinPushLog);

						return resSingle.getMediaId();

					} catch (Exception e2) {
						logger.error("同步永久图文异常，异常信息22222222222222:" + e2.getMessage());
						return null;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("同步异常，异常信息:" + e.getMessage());
			return null;
		}

		return null;
	}

	/**
	 * <pre>
	 * 自定义菜单创建接口
	 * 详情请见： https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141013&token=&lang=zh_CN
	 * 如果要创建个性化菜单，请设置matchrule属性
	 * 详情请见：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455782296&token=&lang=zh_CN
	 * </pre>
	 *
	 * @param json
	 * @return 如果是个性化菜单，则返回menuid，否则返回null
	 */
	/*
	 * @GetMapping("/create/{json}") public String menuCreate(@PathVariable String
	 * json, @RequestParam("id") Integer id) throws WxErrorException {
	 * WeixinUserinfo weixinUserinfo = weixinUserinfoService.selectByPrimaryKey(id);
	 * return wxOpenServiceDemo.getWxOpenComponentService().getWxMpServiceByAppid(
	 * weixinUserinfo.getAuthorizerAppid()) .getMenuService().menuCreate(json); }
	 */

	/**
	 * <pre>
	 * 自定义菜单删除接口
	 * 详情请见: https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141015&token=&lang=zh_CN
	 * </pre>
	 */
	/*
	 * @GetMapping("/delete") public void menuDelete(@RequestParam("id") Integer id)
	 * throws WxErrorException { WeixinUserinfo weixinUserinfo =
	 * weixinUserinfoService.selectByPrimaryKey(id);
	 * wxOpenServiceDemo.getWxOpenComponentService().getWxMpServiceByAppid(
	 * weixinUserinfo.getAuthorizerAppid()) .getMenuService().menuDelete(); }
	 */

	/**
	 * <pre>
	 * 删除个性化菜单接口
	 * 详情请见: https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455782296&token=&lang=zh_CN
	 * </pre>
	 *
	 * @param menuId
	 *            个性化菜单的menuid
	 */
	/*
	 * @GetMapping("/delete/{menuId}") public void menuDelete(@PathVariable String
	 * menuId, @RequestParam("id") Integer id) throws WxErrorException {
	 * WeixinUserinfo weixinUserinfo = weixinUserinfoService.selectByPrimaryKey(id);
	 * wxOpenServiceDemo.getWxOpenComponentService().getWxMpServiceByAppid(
	 * weixinUserinfo.getAuthorizerAppid()) .getMenuService().menuDelete(menuId); }
	 */
	
	@GetMapping("/getmenu")
	public String menuGetcontent(@RequestParam("id") Integer id) {
		WeixinMenuItem weixinMenuItem = weixinMenuItemService.selectByPrimaryKey(id);
		if (weixinMenuItem != null) {
			return weixinMenuItem.getMenucontent();
		}
		return null;
	}
	
	
	@GetMapping("/getItemMenu")
	public Map<String, Object> getItemMenu(@RequestParam("id") Integer id) {
		WeixinMenuItem weixinMenuItem = weixinMenuItemService.selectByPrimaryKey(id);
		if (weixinMenuItem != null) {
			Map<String, Object> map=new HashMap<String,Object>();
			map.put("menuName", weixinMenuItem.getMenuName());
			map.put("menucontent", weixinMenuItem.getMenucontent());
			map.put("remarks", weixinMenuItem.getRemarks());
			return map;
		}
		return null;
	}

	/**
	 * <pre>
	 * 自定义菜单查询接口 配置接口 公众后后台编辑菜单
	 * 详情请见： https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141014&token=&lang=zh_CN
	 * </pre>
	 */
	@GetMapping("/getlast")
	public List<weixinMenuMapUtil> menuGetLast(@RequestParam("authorizerAppid") String authorizerAppid) {

		List<weixinMenuMapUtil> weixinMenuMapUtilsAllList = new ArrayList<weixinMenuMapUtil>();
		try {
			weixinMenuMapUtilsAllList = menuGetApi(authorizerAppid);
			if (weixinMenuMapUtilsAllList == null) {
				weixinMenuMapUtilsAllList = menuGet(authorizerAppid);
			}
			return weixinMenuMapUtilsAllList;
		} catch (WxErrorException e) {
			e.printStackTrace();
			return weixinMenuMapUtilsAllList;
		}
	}

	/**
	 * <pre>
	 * 自定义菜单查询接口 配置接口 公众后后台编辑菜单
	 * 详情请见： https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141014&token=&lang=zh_CN
	 * </pre>
	 */
	public List<weixinMenuMapUtil> menuGet(String authorizerAppid) throws WxErrorException {
		// 拉取所有菜单
		WxMpGetSelfMenuInfoResult wxMpGetSelfMenuInfoResult = wxOpenServiceDemo.getWxOpenComponentService()
				.getWxMpServiceByAppid(authorizerAppid).getMenuService().getSelfMenuInfo();
		List<weixinMenuMapUtil> weixinMenuMapUtilsAllList = new ArrayList<weixinMenuMapUtil>();
		if (wxMpGetSelfMenuInfoResult.getIsMenuOpen() == 0 || wxMpGetSelfMenuInfoResult.getSelfMenuInfo() == null) {
			return weixinMenuMapUtilsAllList;
		}
		// 提取主菜单
		List<WxMpSelfMenuButton> wxmpMenuMainButtons = wxMpGetSelfMenuInfoResult.getSelfMenuInfo().getButtons();
		if (wxmpMenuMainButtons.size() > 0) {
			for (WxMpSelfMenuButton wxMenuMainButton : wxmpMenuMainButtons) {
				weixinMenuMapUtil weixinMainMenuMapUtil = new weixinMenuMapUtil();
				weixinMainMenuMapUtil.setName(wxMenuMainButton.getName());
				// 有子菜单
				if (wxMenuMainButton.getSubButtons() != null) {
					if (wxMenuMainButton.getSubButtons().getSubButtons().size() > 0) {
						List<weixinMenuMapUtil> weixinSubMenuMapUtilsList = new ArrayList<weixinMenuMapUtil>();
						for (WxMpSelfMenuButton wxMenuSubButton : wxMenuMainButton.getSubButtons().getSubButtons()) {
							weixinMenuMapUtil weixinSubMenuMapUtil = new weixinMenuMapUtil();
							weixinSubMenuMapUtil.setName(wxMenuSubButton.getName());
							List<weixinMenuActlistMapUtil> weixinMenuActlistMapUtilList = new ArrayList<weixinMenuActlistMapUtil>();
							weixinMenuActlistMapUtil weixinMenuActlistMapUtil = new weixinMenuActlistMapUtil();
							String mediaType = wxMenuSubButton.getType();
							switch (mediaType) {
							case "view":
								weixinMenuActlistMapUtil.setType("1");
								weixinMenuActlistMapUtil.setValue(wxMenuSubButton.getUrl());
								weixinMenuActlistMapUtilList.add(weixinMenuActlistMapUtil);
								weixinSubMenuMapUtil.setAct_list(weixinMenuActlistMapUtilList);
								break;
							case "news":
								Integer newWeixinPushLogSubId = pullNewsFromWxMenu(authorizerAppid,
										wxMenuSubButton.getValue());
								if (newWeixinPushLogSubId != 0) {
									weixinMenuActlistMapUtil.setType("10");
									weixinMenuActlistMapUtil.setValue(newWeixinPushLogSubId.toString());
									weixinMenuActlistMapUtilList.add(weixinMenuActlistMapUtil);
									weixinSubMenuMapUtil.setAct_list(weixinMenuActlistMapUtilList);
								}
								break;
							case "img":
								weixinMenuActlistMapUtil weixinMenuActlistMapUtilImg = pullImgOrVoiceFromWxOnline(
										authorizerAppid, wxMenuSubButton.getValue());
								if (weixinMenuActlistMapUtilImg != null) {
									weixinMenuActlistMapUtil.setType(weixinMenuActlistMapUtilImg.getType());
									weixinMenuActlistMapUtil.setValue(weixinMenuActlistMapUtilImg.getValue());
									weixinMenuActlistMapUtilList.add(weixinMenuActlistMapUtil);
									weixinSubMenuMapUtil.setAct_list(weixinMenuActlistMapUtilList);
								}
								break;
							case "voice":
								weixinMenuActlistMapUtil weixinMenuActlistMapUtilVoice = pullImgOrVoiceFromWxOnline(
										authorizerAppid, wxMenuSubButton.getValue());
								if (weixinMenuActlistMapUtilVoice != null) {
									weixinMenuActlistMapUtil.setType(weixinMenuActlistMapUtilVoice.getType());
									weixinMenuActlistMapUtil.setValue(weixinMenuActlistMapUtilVoice.getValue());
									weixinMenuActlistMapUtilList.add(weixinMenuActlistMapUtil);
									weixinSubMenuMapUtil.setAct_list(weixinMenuActlistMapUtilList);
								}
								break;
							case "video":
								Integer weixinPushLogSubVedioId = pullVedioFromWxMenuVedioUrl(authorizerAppid,
										wxMenuSubButton.getValue());
								if (weixinPushLogSubVedioId != 0) {
									weixinMenuActlistMapUtil.setType("2");
									weixinMenuActlistMapUtil.setValue(weixinPushLogSubVedioId.toString());
									weixinMenuActlistMapUtilList.add(weixinMenuActlistMapUtil);
									weixinSubMenuMapUtil.setAct_list(weixinMenuActlistMapUtilList);
								}
								break;
							default:
								break;
							}
							weixinSubMenuMapUtilsList.add(weixinSubMenuMapUtil);
						}
						weixinMainMenuMapUtil.setSubButtons(weixinSubMenuMapUtilsList);
						weixinMenuMapUtilsAllList.add(weixinMainMenuMapUtil);
					}
				} else {// 该主菜单没有子菜单
					List<weixinMenuActlistMapUtil> weixinMenuActlistMapUtilList = new ArrayList<weixinMenuActlistMapUtil>();
					weixinMenuActlistMapUtil weixinMenuActlistMapUtil = new weixinMenuActlistMapUtil();
					String mediaType = wxMenuMainButton.getType();
					switch (mediaType) {
					case "view":
						weixinMenuActlistMapUtil.setType("1");
						weixinMenuActlistMapUtil.setValue(wxMenuMainButton.getUrl());
						weixinMenuActlistMapUtilList.add(weixinMenuActlistMapUtil);
						weixinMainMenuMapUtil.setAct_list(weixinMenuActlistMapUtilList);
						break;
					case "news":
						Integer newWeixinPushLogId = pullNewsFromWxMenu(authorizerAppid, wxMenuMainButton.getValue());
						if (newWeixinPushLogId != 0) {
							weixinMenuActlistMapUtil.setType("10");
							weixinMenuActlistMapUtil.setValue(newWeixinPushLogId.toString());
							weixinMenuActlistMapUtilList.add(weixinMenuActlistMapUtil);
							weixinMainMenuMapUtil.setAct_list(weixinMenuActlistMapUtilList);
						}
						break;
					case "img":
						weixinMenuActlistMapUtil weixinMenuActlistMapUtilImg = pullImgOrVoiceFromWxOnline(
								authorizerAppid, wxMenuMainButton.getValue());
						if (weixinMenuActlistMapUtilImg != null) {
							weixinMenuActlistMapUtil.setType(weixinMenuActlistMapUtilImg.getType());
							weixinMenuActlistMapUtil.setValue(weixinMenuActlistMapUtilImg.getValue());
							weixinMenuActlistMapUtilList.add(weixinMenuActlistMapUtil);
							weixinMainMenuMapUtil.setAct_list(weixinMenuActlistMapUtilList);
						}
						break;
					case "voice":
						weixinMenuActlistMapUtil weixinMenuActlistMapUtilVoice = pullImgOrVoiceFromWxOnline(
								authorizerAppid, wxMenuMainButton.getValue());
						if (weixinMenuActlistMapUtilVoice != null) {
							weixinMenuActlistMapUtil.setType(weixinMenuActlistMapUtilVoice.getType());
							weixinMenuActlistMapUtil.setValue(weixinMenuActlistMapUtilVoice.getValue());
							weixinMenuActlistMapUtilList.add(weixinMenuActlistMapUtil);
							weixinMainMenuMapUtil.setAct_list(weixinMenuActlistMapUtilList);
						}
						break;
					case "video":
						Integer weixinPushLogSubVedioId = pullVedioFromWxMenuVedioUrl(authorizerAppid,
								wxMenuMainButton.getValue());
						if (weixinPushLogSubVedioId != 0) {
							weixinMenuActlistMapUtil.setType("2");
							weixinMenuActlistMapUtil.setValue(weixinPushLogSubVedioId.toString());
							weixinMenuActlistMapUtilList.add(weixinMenuActlistMapUtil);
							weixinMainMenuMapUtil.setAct_list(weixinMenuActlistMapUtilList);
						}
						break;
					default:
						break;
					}
					weixinMenuMapUtilsAllList.add(weixinMainMenuMapUtil);
				}
			}
		}
		return weixinMenuMapUtilsAllList;
	}

	/**
	 * <pre>
	 * 自定义菜单查询接口 查询通过api生成的菜单
	 * 详情请见： https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141014&token=&lang=zh_CN
	 * </pre>
	 */
	public List<weixinMenuMapUtil> menuGetApi(String authorizerAppid) throws WxErrorException {
		WxMpMenu wxMpMenu = wxOpenServiceDemo.getWxOpenComponentService().getWxMpServiceByAppid(authorizerAppid)
				.getMenuService().menuGet();
		List<weixinMenuMapUtil> weixinMenuMapUtilsAllList = new ArrayList<weixinMenuMapUtil>();
		if (wxMpMenu == null) {
			return null;
		}
		List<WxMenuButton> wxmpMenuMainButtons = wxMpMenu.getMenu().getButtons();
		if (wxmpMenuMainButtons.size() > 0) {
			for (WxMenuButton wxMenuMainButton : wxmpMenuMainButtons) {
				weixinMenuMapUtil weixinMainMenuMapUtil = new weixinMenuMapUtil();
				weixinMainMenuMapUtil.setName(wxMenuMainButton.getName());
				// 有子菜单
				if (wxMenuMainButton.getSubButtons().size() > 0) {
					List<weixinMenuMapUtil> weixinSubMenuMapUtilsList = new ArrayList<weixinMenuMapUtil>();
					for (WxMenuButton wxMenuSubButton : wxMenuMainButton.getSubButtons()) {
						weixinMenuMapUtil weixinSubMenuMapUtil = new weixinMenuMapUtil();
						weixinSubMenuMapUtil.setName(wxMenuSubButton.getName());
						List<weixinMenuActlistMapUtil> weixinMenuActlistMapUtilList = new ArrayList<weixinMenuActlistMapUtil>();
						weixinMenuActlistMapUtil weixinMenuActlistMapUtil = new weixinMenuActlistMapUtil();
						String mediaType = wxMenuSubButton.getType();
						switch (mediaType) {
						case "view":
							weixinMenuActlistMapUtil.setType("1");
							weixinMenuActlistMapUtil.setValue(wxMenuSubButton.getUrl());
							weixinMenuActlistMapUtilList.add(weixinMenuActlistMapUtil);
							weixinSubMenuMapUtil.setAct_list(weixinMenuActlistMapUtilList);
							break;
						case "media_id":
							WeixinPushLog weixinPushLog = weixinPushLogService.selectByAppidAndMediaId(authorizerAppid,
									wxMenuSubButton.getMediaId());
							// 之前新增永久素材存储过
							if (weixinPushLog != null) {
								switch (weixinPushLog.getCategoryId()) {
								case "mpnews":
									weixinMenuActlistMapUtil.setType("10");
									weixinMenuActlistMapUtil.setValue(weixinPushLog.getImgTextId().toString());
									weixinMenuActlistMapUtilList.add(weixinMenuActlistMapUtil);
									weixinSubMenuMapUtil.setAct_list(weixinMenuActlistMapUtilList);
									break;
								case WxConsts.MaterialType.IMAGE:
									weixinMenuActlistMapUtil.setType("2");
									weixinMenuActlistMapUtil.setValue(weixinPushLog.getImgTextId().toString());
									weixinMenuActlistMapUtilList.add(weixinMenuActlistMapUtil);
									weixinSubMenuMapUtil.setAct_list(weixinMenuActlistMapUtilList);
									break;
								case WxConsts.MaterialType.VOICE:
									weixinMenuActlistMapUtil.setType("3");
									weixinMenuActlistMapUtil.setValue(weixinPushLog.getImgTextId().toString());
									weixinMenuActlistMapUtilList.add(weixinMenuActlistMapUtil);
									weixinSubMenuMapUtil.setAct_list(weixinMenuActlistMapUtilList);
									break;
								case WxConsts.MaterialType.VIDEO:
									weixinMenuActlistMapUtil.setType("15");
									weixinMenuActlistMapUtil.setValue(weixinPushLog.getImgTextId().toString());
									weixinMenuActlistMapUtilList.add(weixinMenuActlistMapUtil);
									weixinSubMenuMapUtil.setAct_list(weixinMenuActlistMapUtilList);
									break;
								default:
									break;
								}
							} else {
								// 探测 图文类型
								try {
									Integer newWeixinPushLogId = pullNewsFromWxMenu(authorizerAppid,
											wxMenuSubButton.getMediaId());
									weixinMenuActlistMapUtil.setType("10");
									weixinMenuActlistMapUtil.setValue(newWeixinPushLogId.toString());
									weixinMenuActlistMapUtilList.add(weixinMenuActlistMapUtil);
									weixinSubMenuMapUtil.setAct_list(weixinMenuActlistMapUtilList);
								} catch (Exception e) {
									e.printStackTrace();
									logger.error("菜单拉取异常-图文按钮异常，异常信息:" + e.getMessage());
								}
								// 探测 音频 图片类型
								try {
									weixinMenuActlistMapUtil weixinPushLogImgOrVoiceutil = pullImgOrVoiceFromWxOnline(
											authorizerAppid, wxMenuSubButton.getMediaId());
									weixinMenuActlistMapUtil.setType(weixinPushLogImgOrVoiceutil.getType());
									weixinMenuActlistMapUtil.setValue(weixinPushLogImgOrVoiceutil.getValue());
									weixinMenuActlistMapUtilList.add(weixinMenuActlistMapUtil);
									weixinSubMenuMapUtil.setAct_list(weixinMenuActlistMapUtilList);
								} catch (Exception e) {
									e.printStackTrace();
									logger.error("菜单拉取异常-图片音频按钮异常，异常信息:" + e.getMessage());
								}
								// 探测 视频类型
								try {
									Integer weixinPushLogVedioId = pullVedioFromWxMenu(authorizerAppid,
											wxMenuSubButton.getMediaId());
									weixinMenuActlistMapUtil.setType("2");
									weixinMenuActlistMapUtil.setValue(weixinPushLogVedioId.toString());
									weixinMenuActlistMapUtilList.add(weixinMenuActlistMapUtil);
									weixinSubMenuMapUtil.setAct_list(weixinMenuActlistMapUtilList);
								} catch (Exception e) {
									e.printStackTrace();
									logger.error("菜单拉取异常-图片音频按钮异常，异常信息:" + e.getMessage());
								}
							}
							break;
						default:
							break;
						}

						weixinSubMenuMapUtilsList.add(weixinSubMenuMapUtil);

					}

					weixinMainMenuMapUtil.setSubButtons(weixinSubMenuMapUtilsList);

					weixinMenuMapUtilsAllList.add(weixinMainMenuMapUtil);

				} else {
					List<weixinMenuActlistMapUtil> weixinMenuActlistMapUtilList = new ArrayList<weixinMenuActlistMapUtil>();
					weixinMenuActlistMapUtil weixinMenuActlistMapUtil = new weixinMenuActlistMapUtil();
					String mediaType = wxMenuMainButton.getType();
					switch (mediaType) {
					case "view":
						weixinMenuActlistMapUtil.setType("1");
						weixinMenuActlistMapUtil.setValue(wxMenuMainButton.getUrl());
						weixinMenuActlistMapUtilList.add(weixinMenuActlistMapUtil);
						weixinMainMenuMapUtil.setAct_list(weixinMenuActlistMapUtilList);
						break;
					case "media_id":
						WeixinPushLog weixinPushLog = weixinPushLogService.selectByAppidAndMediaId(authorizerAppid,
								wxMenuMainButton.getMediaId());
						// 之前新增永久素材存储过
						if (weixinPushLog != null) {
							switch (weixinPushLog.getCategoryId()) {
							case "mpnews":
								weixinMenuActlistMapUtil.setType("10");
								weixinMenuActlistMapUtil.setValue(weixinPushLog.getImgTextId().toString());
								weixinMenuActlistMapUtilList.add(weixinMenuActlistMapUtil);
								weixinMainMenuMapUtil.setAct_list(weixinMenuActlistMapUtilList);
								break;
							case WxConsts.MaterialType.IMAGE:
								weixinMenuActlistMapUtil.setType("2");
								weixinMenuActlistMapUtil.setValue(weixinPushLog.getImgTextId().toString());
								weixinMenuActlistMapUtilList.add(weixinMenuActlistMapUtil);
								weixinMainMenuMapUtil.setAct_list(weixinMenuActlistMapUtilList);
								break;
							case WxConsts.MaterialType.VOICE:
								weixinMenuActlistMapUtil.setType("3");
								weixinMenuActlistMapUtil.setValue(weixinPushLog.getImgTextId().toString());
								weixinMenuActlistMapUtilList.add(weixinMenuActlistMapUtil);
								weixinMainMenuMapUtil.setAct_list(weixinMenuActlistMapUtilList);
								break;
							case WxConsts.MaterialType.VIDEO:
								weixinMenuActlistMapUtil.setType("15");
								weixinMenuActlistMapUtil.setValue(weixinPushLog.getImgTextId().toString());
								weixinMenuActlistMapUtilList.add(weixinMenuActlistMapUtil);
								weixinMainMenuMapUtil.setAct_list(weixinMenuActlistMapUtilList);
								break;
							default:
								break;
							}
						} else {
							// 探测 图文类型
							try {
								Integer newWeixinPushLogId = pullNewsFromWxMenu(authorizerAppid,
										wxMenuMainButton.getMediaId());
								weixinMenuActlistMapUtil.setType("10");
								weixinMenuActlistMapUtil.setValue(newWeixinPushLogId.toString());
								weixinMenuActlistMapUtilList.add(weixinMenuActlistMapUtil);
								weixinMainMenuMapUtil.setAct_list(weixinMenuActlistMapUtilList);
							} catch (Exception e) {
								e.printStackTrace();
								logger.error("菜单拉取异常-图文按钮异常，异常信息:" + e.getMessage());
							}
							// 探测 音频 图片类型
							try {
								weixinMenuActlistMapUtil weixinPushLogImgOrVoiceutil = pullImgOrVoiceFromWxOnline(
										authorizerAppid, wxMenuMainButton.getMediaId());
								weixinMenuActlistMapUtil.setType(weixinPushLogImgOrVoiceutil.getType());
								weixinMenuActlistMapUtil.setValue(weixinPushLogImgOrVoiceutil.getValue());
								weixinMenuActlistMapUtilList.add(weixinMenuActlistMapUtil);
								weixinMainMenuMapUtil.setAct_list(weixinMenuActlistMapUtilList);
							} catch (Exception e) {
								e.printStackTrace();
								logger.error("菜单拉取异常-图片音频按钮异常，异常信息:" + e.getMessage());
							}
							// 探测 视频类型
							try {
								Integer weixinPushLogVedioId = pullVedioFromWxMenu(authorizerAppid,
										wxMenuMainButton.getMediaId());
								weixinMenuActlistMapUtil.setType("2");
								weixinMenuActlistMapUtil.setValue(weixinPushLogVedioId.toString());
								weixinMenuActlistMapUtilList.add(weixinMenuActlistMapUtil);
								weixinMainMenuMapUtil.setAct_list(weixinMenuActlistMapUtilList);
							} catch (Exception e) {
								e.printStackTrace();
								logger.error("菜单拉取异常-图片音频按钮异常，异常信息:" + e.getMessage());
							}
						}
						break;
					default:
						break;
					}

					weixinMenuMapUtilsAllList.add(weixinMainMenuMapUtil);

				}
			}
		}

		return weixinMenuMapUtilsAllList;

	}

	private Integer pullVedioFromWxMenu(String authorizerAppid, String mediaId) {
		WxMpMaterialVideoInfoResult wxMpMaterialVideoInfoResult;
		try {
			wxMpMaterialVideoInfoResult = wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(authorizerAppid).getMaterialService().materialVideoInfo(mediaId);

			URL videoFileUrl;
			try {
				videoFileUrl = new URL(wxMpMaterialVideoInfoResult.getDownUrl());

				URLConnection rulConnection;
				try {
					rulConnection = videoFileUrl.openConnection();
					HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;

					InputStream videoInputStream = httpUrlConnection.getInputStream();

					String fileName = System.nanoTime() + ".MP4";
					String filePath = file_location + fileName;
					// 创建实际路径
					OutputStream os = new FileOutputStream(filePath);
					int bytesRead = 0;
					byte[] buffer = new byte[8192];
					while ((bytesRead = videoInputStream.read(buffer, 0, 8192)) != -1) {
						os.write(buffer, 0, bytesRead);
					}
					os.close();
					File f = new File(filePath);
					long fileSize = f.length();
					Integer fileSizeKb = (int) (fileSize / 1024);

					WeixinVideo weixinVideo = new WeixinVideo();
					weixinVideo.setCreateBy(1);
					weixinVideo.setCreateByUname("admin01");
					weixinVideo.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					weixinVideo.setHeadImg(appURL + "/" + fileName);
					weixinVideo.setFileSize(fileSizeKb.toString());
					weixinVideo.setTitle(wxMpMaterialVideoInfoResult.getTitle());
					weixinVideoService.insert(weixinVideo);

					WeixinPushLog weixinPushLogVedio = new WeixinPushLog();
					weixinPushLogVedio.setCategoryId(WxConsts.MaterialType.VIDEO);// 图片消息
					weixinPushLogVedio.setMediaCategory("1");// 永久素材
					weixinPushLogVedio.setMediaId(mediaId);
					weixinPushLogVedio.setImgTextId(weixinVideo.getId());
					weixinPushLogVedio.setCreateBy(1);
					weixinPushLogVedio.setCreateByUname("admin01");

					weixinPushLogVedio.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());

					weixinPushLogVedio.setAuthorizerAppid(authorizerAppid);

					weixinPushLogService.insert(weixinPushLogVedio);
					return weixinPushLogVedio.getId();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("菜单拉取异常-视频素材存储异常，异常信息:" + e.getMessage());
					return 0;
				} // 此处的urlConnection对象实际上是根据URL的
			} catch (MalformedURLException e) {
				e.printStackTrace();
				logger.error("菜单拉取异常-视频素材存储异常，异常信息:" + e.getMessage());
				return 0;
			} // 视频地址
		} catch (WxErrorException e) {
			e.printStackTrace();
			logger.error("菜单拉取异常-视频素材拉取异常，异常信息:" + e.getMessage());
			return 0;
		}
	}

	private Integer pullVedioFromWxMenuVedioUrl(String authorizerAppid, String mediaUrl) {
		try {
			URL videoFileUrl = new URL(mediaUrl);
			URLConnection rulConnection;
			try {
				rulConnection = videoFileUrl.openConnection();
				HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;

				InputStream videoInputStream = httpUrlConnection.getInputStream();

				String fileName = System.nanoTime() + ".MP4";
				String filePath = file_location + fileName;
				// 创建实际路径
				OutputStream os = new FileOutputStream(filePath);
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				while ((bytesRead = videoInputStream.read(buffer, 0, 8192)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
				os.close();
				File f = new File(filePath);
				long fileSize = f.length();
				Integer fileSizeKb = (int) (fileSize / 1024);

				WeixinVideo weixinVideo = new WeixinVideo();
				weixinVideo.setCreateBy(1);
				weixinVideo.setCreateByUname("admin01");
				weixinVideo.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
				weixinVideo.setHeadImg(appURL + "/" + fileName);
				weixinVideo.setFileSize(fileSizeKb.toString());
				weixinVideoService.insert(weixinVideo);

				File fileTmp = new File(filePath);
				if (!fileTmp.exists()) {
					return 0;
				}
				String mediaType = WxConsts.MaterialType.VIDEO;
				String videoTitle = weixinVideo.getTitle();
				// 上传视频video
				WxMpMaterial wxMpMaterial = new WxMpMaterial();
				wxMpMaterial.setFile(fileTmp);
				wxMpMaterial.setName(fileName);
				wxMpMaterial.setVideoTitle(videoTitle);
				wxMpMaterial.setVideoIntroduction(weixinVideo.getIntro());

				WxMpMaterialUploadResult uploadMediaRes;
				try {
					uploadMediaRes = wxOpenServiceDemo.getWxOpenComponentService()
							.getWxMpServiceByAppid(authorizerAppid).getMaterialService()
							.materialFileUpload(mediaType, wxMpMaterial);
					String mediaId = uploadMediaRes.getMediaId();

					logger.info("上传视频消息的mediaId=" + mediaId + ",fileType=MP4" + ",headImg=" + filePath);

					WeixinPushLog weixinPushLogVedio = new WeixinPushLog();
					weixinPushLogVedio.setCategoryId(WxConsts.MaterialType.VIDEO);// 图片消息
					weixinPushLogVedio.setMediaCategory("1");// 永久素材
					weixinPushLogVedio.setMediaId(mediaId);
					weixinPushLogVedio.setImgTextId(weixinVideo.getId());
					weixinPushLogVedio.setCreateBy(1);
					weixinPushLogVedio.setCreateByUname("admin01");

					weixinPushLogVedio.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());

					weixinPushLogVedio.setAuthorizerAppid(authorizerAppid);

					weixinPushLogService.insert(weixinPushLogVedio);
					return weixinPushLogVedio.getId();
				} catch (WxErrorException e) {
					e.printStackTrace();
					logger.error("菜单拉取异常-视频素材上传到微信异常，异常信息:" + e.getMessage());
					return 0;
				}
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("菜单拉取异常-视频素材存储异常，异常信息:" + e.getMessage());
				return 0;
			} // 此处的urlConnection对象实际上是根据URL的
		} catch (MalformedURLException e) {
			e.printStackTrace();
			logger.error("菜单拉取异常-视频素材存储异常，异常信息:" + e.getMessage());
			return 0;
		} // 视频地址
	}

	private weixinMenuActlistMapUtil pullImgOrVoiceFromWxOnline(String authorizerAppid, String mediaId) {
		InputStream imgOrVoiceInputStream;
		try {
			imgOrVoiceInputStream = wxOpenServiceDemo.getWxOpenComponentService().getWxMpServiceByAppid(authorizerAppid)
					.getMaterialService().materialImageOrVoiceDownload(mediaId);

			FileType FileType;
			try {
				FileType = FileTypeJudge.getType2(imgOrVoiceInputStream);

				String fileName = System.nanoTime() + "." + FileType.name().toLowerCase();
				String filePath = file_location + fileName;
				// 创建实际路径
				OutputStream os = new FileOutputStream(filePath);
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				while ((bytesRead = imgOrVoiceInputStream.read(buffer, 0, 8192)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
				os.close();
				File f = new File(filePath);
				long fileSize = f.length();
				Integer fileSizeKb = (int) (fileSize / 1024);

				weixinMenuActlistMapUtil weixinMenuActlistMapUtilimgorvoice = new weixinMenuActlistMapUtil();

				String[] imgFileTypeArray = new String[] { "bmp", "png", "jpeg", "jpg", "gif" };
				String[] voiceFileTypeArray = new String[] { "mp3", "wma", "wav", "amr" };
				if (StringUtil.findMatchArray(imgFileTypeArray, FileType.name().toLowerCase())) {
					WeixinImg weixinImg = new WeixinImg();
					weixinImg.setCreateBy(1);
					weixinImg.setCreateByUname("admin01");
					weixinImg.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					weixinImg.setHeadImg(appURL + "/" + fileName);
					weixinImg.setFileSize(fileSizeKb.toString());
					weixinImgService.insert(weixinImg);

					WeixinPushLog weixinPushLogImg = new WeixinPushLog();
					weixinPushLogImg.setCategoryId(WxConsts.MaterialType.IMAGE);// 图片消息
					weixinPushLogImg.setMediaCategory("1");// 永久素材
					weixinPushLogImg.setMediaId(mediaId);
					weixinPushLogImg.setImgTextId(weixinImg.getId());
					weixinPushLogImg.setCreateBy(1);
					weixinPushLogImg.setCreateByUname("admin01");

					weixinPushLogImg.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());

					weixinPushLogImg.setAuthorizerAppid(authorizerAppid);

					weixinPushLogService.insert(weixinPushLogImg);

					weixinMenuActlistMapUtilimgorvoice.setType("2");
					weixinMenuActlistMapUtilimgorvoice.setValue(weixinPushLogImg.getId().toString());
				} else if (StringUtil.findMatchArray(voiceFileTypeArray, FileType.name().toLowerCase())) {
					WeixinAudio weixinAudio = new WeixinAudio();
					weixinAudio.setCreateBy(1);
					weixinAudio.setCreateByUname("admin01");
					weixinAudio.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					weixinAudio.setHeadImg(appURL + "/" + fileName);
					weixinAudio.setFileSize(fileSizeKb.toString());
					weixinAudioService.insert(weixinAudio);

					WeixinPushLog weixinPushLogAudio = new WeixinPushLog();
					weixinPushLogAudio.setCategoryId(WxConsts.MaterialType.VOICE);// 音频消息
					weixinPushLogAudio.setMediaCategory("1");// 永久素材
					weixinPushLogAudio.setMediaId(mediaId);
					weixinPushLogAudio.setImgTextId(weixinAudio.getId());
					weixinPushLogAudio.setCreateBy(1);
					weixinPushLogAudio.setCreateByUname("admin01");

					weixinPushLogAudio.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());

					weixinPushLogAudio.setAuthorizerAppid(authorizerAppid);

					weixinPushLogService.insert(weixinPushLogAudio);

					weixinMenuActlistMapUtilimgorvoice.setType("3");
					weixinMenuActlistMapUtilimgorvoice.setValue(weixinPushLogAudio.getId().toString());
				}
				return weixinMenuActlistMapUtilimgorvoice;
			} catch (IOException e) {
				logger.error("菜单拉取异常-图片或音频素材存储异常，异常信息:" + e.getMessage());
				e.printStackTrace();
				return null;
			}
		} catch (WxErrorException e) {
			e.printStackTrace();
			logger.error("菜单拉取异常-图片或音频素材拉取异常，异常信息:" + e.getMessage());
			return null;
		}
	}

	private Integer pullNewsFromWxMenu(String authorizerAppid, String mediaId) {
		WxMpMaterialNews wxMpMaterialNews;
		try {
			wxMpMaterialNews = wxOpenServiceDemo.getWxOpenComponentService().getWxMpServiceByAppid(authorizerAppid)
					.getMaterialService().materialNewsInfo(mediaId);
			if (wxMpMaterialNews.getArticles().size() > 0) {
				WeixinImgtext weixinImgtext = new WeixinImgtext();
				weixinImgtext.setCreateBy(1);
				weixinImgtext.setCreateByUname("admin01");
				weixinImgtext.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
				weixinImgtextService.insert(weixinImgtext);

				for (WxMpMaterialNewsArticle wxMpMaterialNewsArticle : wxMpMaterialNews.getArticles()) {
					WeixinImgtextItem weixinImgtextItem = new WeixinImgtextItem();
					weixinImgtextItem.setCreateBy(1);
					weixinImgtextItem.setCreateByUname("admin01");
					weixinImgtextItem.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					weixinImgtextItem.setImgTextId(weixinImgtext.getId());// 图文模板ID

					weixinImgtextItem.setAuthor(wxMpMaterialNewsArticle.getAuthor());
					weixinImgtextItem.setTitle(wxMpMaterialNewsArticle.getTitle());

					// 封面重新拉取
					InputStream imgInputStream = wxOpenServiceDemo.getWxOpenComponentService()
							.getWxMpServiceByAppid(authorizerAppid).getMaterialService()
							.materialImageOrVoiceDownload(wxMpMaterialNewsArticle.getThumbMediaId());

					try {
						FileType FileType = FileTypeJudge.getType2(imgInputStream);

						String fileName = System.nanoTime() + "." + FileType.name().toLowerCase();
						String filePath = file_location + fileName;
						// 创建实际路径
						OutputStream os = new FileOutputStream(filePath);
						int bytesRead = 0;
						byte[] buffer = new byte[8192];
						while ((bytesRead = imgInputStream.read(buffer, 0, 8192)) != -1) {
							os.write(buffer, 0, bytesRead);
						}
						os.close();

						weixinImgtextItem.setHeadImg(appURL + "/" + fileName);

						weixinImgtextItem.setMediaId(wxMpMaterialNewsArticle.getThumbMediaId());
						weixinImgtextItem.setArticleContent(wxMpMaterialNewsArticle.getContent());
						weixinImgtextItem.setIntro(wxMpMaterialNewsArticle.getDigest());

						weixinImgtextItemService.insert(weixinImgtextItem);

						weixinImgtextItem.setSortNo(weixinImgtextItem.getId());// 默认序号
						weixinImgtextItem.setId(weixinImgtextItem.getId());

						weixinImgtextItemService.updateByPrimaryKeySelective(weixinImgtextItem);
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("菜单拉取异常-图文存储异常，异常信息:" + e.getMessage());
						return 0;
					}
				}

				WeixinPushLog newWeixinPushLog = new WeixinPushLog();
				newWeixinPushLog.setCategoryId("mpnews");// 图文消息
				newWeixinPushLog.setMediaCategory("1");// 永久素材
				newWeixinPushLog.setMediaId(mediaId);
				newWeixinPushLog.setImgTextId(weixinImgtext.getId());
				newWeixinPushLog.setCreateBy(1);
				newWeixinPushLog.setCreateByUname("admin01");

				newWeixinPushLog.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());

				newWeixinPushLog.setAuthorizerAppid(authorizerAppid);

				weixinPushLogService.insert(newWeixinPushLog);

				return newWeixinPushLog.getId();
			}
		} catch (WxErrorException e) {
			e.printStackTrace();
			logger.error("菜单拉取异常-图文拉取异常，异常信息:" + e.getMessage());
			return 0;
		}

		return 0;
	}

	/*
	 * 保存到自主服务器
	 */
	@PostMapping("/saveinmyservice")
	public RestAPIResult2 saveTomyIdc(@RequestParam("menuName") String menuName,
			@RequestParam("remarks") String remarks, @RequestParam("menus") String menus) {
		RestAPIResult2 restAPIResult2 = new RestAPIResult2();
		restAPIResult2.setRespCode(0);
		restAPIResult2.setRespMsg("success");

		System.out.println("---------------------------------------------------------");
		System.out.println(menuName);
		System.out.println(remarks);
		System.out.println(menus);
		System.out.println("---------------------------------------------------------");

		WeixinMenuItem weixinMenuItem = new WeixinMenuItem();
		weixinMenuItem.setCreateBy(1);
		weixinMenuItem.setCreateByUname("admin");
		weixinMenuItem.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
		weixinMenuItem.setMenuName(menuName);
		weixinMenuItem.setRemarks(remarks);
		weixinMenuItem.setMenucontent(menus);
		weixinMenuItemService.insert(weixinMenuItem);
		Integer newWeixinMenuId = weixinMenuItem.getId();
		restAPIResult2.setRespData(newWeixinMenuId);

		return restAPIResult2;
	}

	/*
	 * 更新到自主服务器
	 */
	@PostMapping("/saveinmyserviceupdate")
	public RestAPIResult2 saveTomyIdcUpdate(@RequestParam("menuid") Integer menuid,
			@RequestParam("menuName") String menuName, @RequestParam("remarks") String remarks,
			@RequestParam("menus") String menus) {
		RestAPIResult2 restAPIResult2 = new RestAPIResult2();
		restAPIResult2.setRespCode(0);
		restAPIResult2.setRespMsg("success");

		System.out.println("---------------------------------------------------------");
		System.out.println(menuid);
		System.out.println(menuName);
		System.out.println(remarks);
		System.out.println(menus);
		System.out.println("---------------------------------------------------------");

		WeixinMenuItem weixinMenuItem = weixinMenuItemService.selectByPrimaryKey(menuid);
		weixinMenuItem.setCreateBy(1);
		weixinMenuItem.setCreateByUname("admin");
		weixinMenuItem.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
		weixinMenuItem.setMenuName(menuName);
		weixinMenuItem.setRemarks(remarks);
		weixinMenuItem.setMenucontent(menus);
		weixinMenuItemService.updateByPrimaryKeySelective(weixinMenuItem);
		Integer newWeixinMenuId = weixinMenuItem.getId();
		restAPIResult2.setRespData(newWeixinMenuId);

		return restAPIResult2;
	}

	/**
	 * <pre>
	 * 获取自定义菜单配置接口
	 * 本接口将会提供公众号当前使用的自定义菜单的配置，如果公众号是通过API调用设置的菜单，则返回菜单的开发配置，而如果公众号是在公众平台官网通过网站功能发布菜单，则本接口返回运营者设置的菜单配置。
	 * 请注意：
	 * 1、第三方平台开发者可以通过本接口，在旗下公众号将业务授权给你后，立即通过本接口检测公众号的自定义菜单配置，并通过接口再次给公众号设置好自动回复规则，以提升公众号运营者的业务体验。
	 * 2、本接口与自定义菜单查询接口的不同之处在于，本接口无论公众号的接口是如何设置的，都能查询到接口，而自定义菜单查询接口则仅能查询到使用API设置的菜单配置。
	 * 3、认证/未认证的服务号/订阅号，以及接口测试号，均拥有该接口权限。
	 * 4、从第三方平台的公众号登录授权机制上来说，该接口从属于消息与菜单权限集。
	 * 5、本接口中返回的图片/语音/视频为临时素材（临时素材每次获取都不同，3天内有效，通过素材管理-获取临时素材接口来获取这些素材），本接口返回的图文消息为永久素材素材（通过素材管理-获取永久素材接口来获取这些素材）。
	 *  接口调用请求说明:
	 * http请求方式: GET（请使用https协议）
	 * https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=ACCESS_TOKEN
	 * </pre>
	 */
	@GetMapping("/getSelfMenuInfo")
	public WxMpGetSelfMenuInfoResult getSelfMenuInfo(@RequestParam("id") Integer id) throws WxErrorException {
		WeixinUserinfo weixinUserinfo = weixinUserinfoService.selectByPrimaryKey(id);
		return wxOpenServiceDemo.getWxOpenComponentService().getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid())
				.getMenuService().getSelfMenuInfo();
	}

}
