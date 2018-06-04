package com.github.binarywang.demo.wechat.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.binarywang.demo.wechat.service.WxOpenServiceDemo;
import com.lj.cloud.secrity.service.WeixinFansAllCountService;
import com.lj.cloud.secrity.service.WeixinFansCountService;
import com.lj.cloud.secrity.service.WeixinFansInfoService;
import com.lj.cloud.secrity.service.WeixinPushLogService;
import com.lj.cloud.secrity.service.WeixinUserinfoService;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.report.WeixinFansAllCount;
import com.weixindev.micro.serv.common.bean.report.WeixinFansCount;
import com.weixindev.micro.serv.common.bean.report.WeixinFansInfo;
import com.weixindev.micro.serv.common.bean.weixin.WeixinUserinfo;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
import com.weixindev.micro.serv.common.util.DateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeUserCumulate;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeUserSummary;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;

@Api(value = "粉丝管理服务", tags = "粉丝管理服务")
@RestController
public class FansCountController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WeixinPushLogService weixinPushLogService;
	
	@Autowired
	private WxOpenServiceDemo wxOpenServiceDemo;
	@Autowired
	WeixinFansCountService weixinFansCountService;
	@Autowired
	WeixinUserinfoService WeixinUserinfoService;
	@Autowired
	WeixinFansInfoService weixinFansInfoService;
	@Autowired
	WeixinFansAllCountService weixinFansAllCountService;
	
	
	@Value("${appURL}")
	private String appURL;// 网站前台url

	@Value("${ctxAppWeixin}")
	private String ctxAppWeixin;// 微信网站路径
	
	@ApiOperation(value = "单用户统计")
	@RequestMapping(value = "/api/count/oneFansCount")
	public List<WeixinFansCount> oneFansCount(Integer day,String uid,Integer source) {
		Integer userId=Integer.parseInt(uid);
		Map<String,Object> map=new HashMap<String,Object>();
		String endTime=DateUtil.getNowDate("yyyy-MM-dd");
	    String beginTime=DateUtil.formatDate(DateUtil.rollDay(DateUtil.formatDate(endTime, "yyyy-MM-dd"),-day),"yyyy-MM-dd");
		logger.info("总用户统计：开始时间"+beginTime+"结束时间:"+endTime);
		map.put("createDateBegin",beginTime);
		map.put("createDateEnd",endTime);
		if(source!=null) {
			Integer addSceneSearch=0;
			Integer addSceneAccountMigration=0;
			Integer addSceneProfileCard=0;
			Integer addSceneQrCode=0;
			Integer addSceneProfileItem=0;
			Integer addScenePaid=0;
			Integer addSceneprofileLink=0;
			Integer addCircleFriends=0;
			Integer addSceneOthers=0;
			switch(source) {
			//其他
			case 0:
				map.put("addSceneOthers",addSceneOthers);
                break;  
           case 1:  
        	   map.put("addSceneSearch",addSceneSearch);
                break; 
                //名片分享 
           case 17:  
        	   map.put("addSceneProfileCard",addSceneProfileCard);
                break;  
                //扫描二维码
           case 30:  
        	   map.put("addSceneQrCode",addSceneQrCode);
                break; 
                //图文页右上角菜单 
           case 43:  
        	   map.put("addSceneProfileItem",addSceneProfileItem);
                break; 
               // 51代表支付后关注
           case 51:  
        	   map.put("addScenePaid",addScenePaid);
                break; 
                //57代表图文页内公众号名称
           case 57:  
        	   map.put("addSceneprofileLink",addSceneprofileLink);
                break; 
                //75代表公众号文章广告
           case 75: 
        	   map.put("addSceneAccountMigration",addSceneAccountMigration);
                //78代表朋友圈广告
           case 78:  
        	   map.put("addCircleFriends",addCircleFriends);
                break; 
			}
		}
		map.put("userId",userId);
		Query query=new Query(map);
		List<WeixinFansCount> list=weixinFansCountService.selectByExample(query);
		if(list==null) {
			return null;
		}
		
	return list;
	}
	
	@ApiOperation(value = "总用户统计")
	@RequestMapping(value = "/api/count/allFansCount")
	public List<WeixinFansAllCount> allFansCount(Integer day,String ids) {
			Map<String,Object> map=new HashMap<String,Object>();
			String endTime=DateUtil.getNowDate("yyyy-MM-dd");
		    String beginTime=DateUtil.formatDate(DateUtil.rollDay(DateUtil.formatDate(endTime, "yyyy-MM-dd"),-day),"yyyy-MM-dd");
			logger.info("总用户统计：开始时间"+beginTime+"结束时间:"+endTime);
			if(ids!=null&ids.endsWith(",")) {
				ids=ids.substring(0,ids.length()-1);
			}
			System.out.println("------------------allCount");
			map.put("createDateBegin",beginTime);
			map.put("createDateEnd",endTime);
			map.put("ids",ids);
			Query query=new Query(map);
			List<WeixinFansAllCount> list=weixinFansAllCountService.selectByExample(query);
		return list;
	}
	@ApiOperation(value = "表统计")
	@RequestMapping(value = "/api/count/tableCount")
	public LayUiTableResultResponse tableCount(Integer day,Integer limit,Integer page) {
			Map<String,Object> m=new HashMap<String,Object>();
			String endTime=DateUtil.getNowDate("yyyy-MM-dd");
		    String beginTime=DateUtil.formatDate(DateUtil.rollDay(DateUtil.formatDate(endTime, "yyyy-MM-dd"),-day),"yyyy-MM-dd");
			logger.info("总用户统计：开始时间"+beginTime+"结束时间:"+endTime);
			m.put("createDateBegin",beginTime);
			m.put("createDateEnd",endTime);
			m.put("page", page);
			m.put("limit", limit);
		/*	List<WeixinFansAllCount> list=weixinFansAllCountService.selectByExample(query);
			if(list==null) {
				return null;
			}*/
			Query query=new Query(m);
			LayUiTableResultResponse LayUiTableResultResponse = weixinFansAllCountService.tableCountAll(query);
//			for(WeixinFansAllCount w:list) {
//				Map<String,Object> map=new HashMap<String,Object>();
//				if(w.getNetGrowth()!=null) {
//					map.put("netGrowth()",w.getNetGrowth());
//				}else{
//					map.put("netGrowth()",0);
//				}
//				
//				if(w.getNewFans()!=null) {
//					map.put("newFans", w.getNewFans());
//				}else {
//					map.put("newFans", 0);
//				}
//				
//				if(w.getCreateTime()!=null) {
//					map.put("createTime", w.getCreateTime());
//				}else{
//					map.put("newFans", 0);
//				}
//				
//				if(w.getCount()!=null) {
//					map.put("count", w.getCount());
//				}else{
//					map.put("count",0);
//				}
//				if(w.getCancel()!=null) {
//					map.put("cancel", w.getCancel()!=null);
//				}else{
//					map.put("cancel", 0);
//				}
//				tablist.add(map);
//			}
			return LayUiTableResultResponse;
	}
	
	@ApiOperation(value = "test")
	@RequestMapping(value = "/api/count/test1")
	public Map<String,Object> test1() {
			Map<String,Object> map=new HashMap();
			map.put("test1", 10);
			map.put("test2", 20);
			map.put("test3", 30);
			map.put("test4", 50);
			map.put("test5", 60);
			map.put("test6", 70);
			
		return map;
	}
	
	@ApiOperation(value = "地理位置统计")
	@RequestMapping(value = "/api/count/positionCount")
	public List<Map<String,Object>> positionCount(Integer day,String ids) {
//		Map<String,Object> map=new HashMap<String,Object>();
//		String endTime=DateUtil.getNowDate("yyyy-MM-dd");
//	    String beginTime=DateUtil.formatDate(DateUtil.rollDay(DateUtil.formatDate(endTime, "yyyy-MM-dd"),day),"yyyy-MM-dd");
//		map.put("createDateBegin",beginTime);
//		map.put("createDateEnd",endTime);
		if(ids!=null&ids.endsWith(",")) {
			ids=ids.substring(0,ids.length()-1);
		}
		System.out.println("position-----------------------");
		System.out.println(ids);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("day", day);
		map.put("ids", ids);
		List<Map<String,Object>> list=weixinFansInfoService.selectPosition(map);
		return list;
	}
	
	@ApiOperation(value = "单用户地理位置统计")
	@RequestMapping(value = "/api/count/positionOneCount")
	public List<Map<String,Object>> positionOneCount(Integer day,String uid) {
//		Map<String,Object> map=new HashMap<String,Object>();
//		String endTime=DateUtil.getNowDate("yyyy-MM-dd");
//	    String beginTime=DateUtil.formatDate(DateUtil.rollDay(DateUtil.formatDate(endTime, "yyyy-MM-dd"),day),"yyyy-MM-dd");
//		map.put("createDateBegin",beginTime);
//		map.put("createDateEnd",endTime);
		Map<String,Object> map=new HashMap<String,Object>();
		
		map.put("uid", Integer.parseInt(uid));
		map.put("day", day);
		List<Map<String,Object>> list=weixinFansInfoService.selectPositionByUid(map);
		System.out.println("--------------------");
		System.out.println(list);
		System.out.println("--------------------");
		return list;
	}
	
	
	@ApiOperation(value = "test")
	@RequestMapping(value = "/api/count/test2")
	public RestAPIResult2 userCountCronTest(Integer day,Integer timeEnd){
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("成功");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String endDate = DateUtil.getNowDate(DateUtil.DATE_FOMRAT_yyyy_MM_dd);
			Date endDateTime = DateUtil.formatDate(endDate, DateUtil.DATE_FOMRAT_yyyy_MM_dd);
			Date beginDateTime =null;
			try {
			endDateTime = DateUtil.rollDay(sdf.parse(endDate), -1);
			beginDateTime = DateUtil.rollDay(sdf.parse(endDate), -2);
			}catch(Exception e) {
				logger.error("异常："+e.getMessage());
			}
			Date createTime=new Date();
			Date updateTime=createTime;
			SimpleDateFormat sdf1 = new SimpleDateFormat("HH");
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd");
			String beginDate = DateUtil.dateStryyyyMMdd(beginDateTime);
			logger.info("查询开始时间:" + beginDate + "查询结束时间:" + endDate);
			WeixinFansAllCount weixinFansAllCount=new  WeixinFansAllCount();
//			Integer fansAllCount=0;
//			Integer maleAllCount=0;
//			Integer femaleAllCount=0;
//			Integer chineseAllCount=0;
//			Integer notChineseAllCount=0;
//			Integer cancelAllCount=0;
//			Integer langChAllCount=0;
//			Integer langOtherAllCount=0;
//			Integer netGrowthAllCount=0;
//			Integer newFansAllCount=0;
//			Integer addSceneSearchAllCount=0;
//			Integer addSceneAccountMigrationAllCount=0;
//			Integer addSceneProfileCardAllCount=0;
//			Integer addSceneQrCodeAllCount=0;
//			Integer addSceneProfileItemAllCount=0;
//			Integer addScenePaidAllCount=0;
//			Integer addSceneprofileLinkAllCount=0;
//			Integer addCircleFriendsAllCount=0;
//			Integer addSceneOthersAllCount=0;
			List<WeixinUserinfo> WeixinUserinfoList = WeixinUserinfoService.selectByExample(new Query(new HashMap<String, Object>()));
			logger.info("查询公众账号列表数量:WeixinUserinfoList", WeixinUserinfoList.size());
			WeixinFansCount fansCount=null;
			
			Integer cancelUser = 0;
			Integer countNewUser = 0;
			//用户渠道
			Integer addSceneSearch=0;
			Integer addSceneAccountMigration=0;
			Integer addSceneProfileCard=0;
			Integer addSceneQrCode=0;
			Integer addSceneProfileItem=0;
			Integer addScenePaid=0;
			Integer addSceneprofileLink=0;
			Integer addCircleFriends=0;
			Integer addSceneOthers=0;
			 Integer male=0;
			 Integer female=0;
			 Integer chinese=0;
			 Integer notChinese=0;
			 Integer langCh=0;
			 Integer langOther=0;
			for (WeixinUserinfo weixinUserinfo : WeixinUserinfoList) {
				try {
				  fansCount=new WeixinFansCount();
				Integer uid = weixinUserinfo.getId();
				// 获取增减用户
				List<WxDataCubeUserSummary> list=null;
				try {
				list = wxOpenServiceDemo.getWxOpenComponentService()
						.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getDataCubeService()
						.getUserSummary(beginDateTime, endDateTime);
				}catch(WxErrorException e) {
					logger.error("统计公众号:"+weixinUserinfo.getNickName()+"时出现异常");
					logger.error("异常信息:"+e.getMessage());
					logger.error(e.getLocalizedMessage());
				}
				if(list==null){
					logger.error("统计公众号:"+weixinUserinfo.getNickName()+"时出现异常");
					logger.error("list为null");
					continue;
				}
				// 查出数据库最后nextOpenid
				String nextOpenId=weixinFansCountService.selectNextOpenidByUserId(weixinUserinfo.getId());
				logger.info("查询:" + weixinUserinfo.getNickName() + "开始nextOpenid为:" + nextOpenId);
				 cancelUser = 0;
				 countNewUser = 0;
				//用户渠道
				 addSceneSearch=0;
				 addSceneAccountMigration=0;
				 addSceneProfileCard=0;
				 addSceneQrCode=0;
				 addSceneProfileItem=0;
				 addScenePaid=0;
				 addSceneprofileLink=0;
				 addCircleFriends=0;
				 addSceneOthers=0;
				
				//获取当前用户新增和取消用户总数
				for (WxDataCubeUserSummary wxDataCubeUserSummary : list) {
					try {
					Integer newUser=wxDataCubeUserSummary.getNewUser();
					countNewUser += newUser;
					cancelUser += wxDataCubeUserSummary.getCancelUser();
					if(wxDataCubeUserSummary.getNewUser()!=0) {
						switch(wxDataCubeUserSummary.getUserSource()) {
						//其他
						case 0:
							addSceneOthers=newUser;
			                break;  
			           case 1:  
			        	   addSceneSearch=newUser;
			                break; 
			                //名片分享 
			           case 17:  
			        	   addSceneProfileCard=newUser;
			                break;  
			                //扫描二维码
			           case 30:  
			        	   addSceneQrCode=newUser;
			                break; 
			                //图文页右上角菜单 
			           case 43:  
			        	   addSceneProfileItem=newUser;
			                break; 
			               // 51代表支付后关注
			           case 51:  
			        	   addScenePaid=newUser;
			                break; 
			                //57代表图文页内公众号名称
			           case 57:  
			        	   addSceneprofileLink=newUser;
			                break; 
			                //75代表公众号文章广告
			           case 75: 
			        	   addSceneAccountMigration=newUser;
			                break; 
			                //78代表朋友圈广告
			           case 78:  
			        	   addCircleFriends=newUser;
			                break; 
						}
					}
					}catch(Exception e) {
					logger.error("异常:"+e.getMessage());
					}
				}
				logger.info("查询userid为"+uid+"查询新增用户为："+countNewUser+"取消用户为"+cancelUser);
				fansCount.setAddSceneSearch(addSceneSearch);
				fansCount.setAddSceneAccountMigration(addSceneAccountMigration);
				fansCount.setAddSceneProfileCard(addSceneProfileCard);
				fansCount.setAddSceneQrCode(addSceneQrCode);
				fansCount.setAddSceneProfileItem(addSceneProfileItem);
				fansCount.setAddScenePaid(addScenePaid);
				fansCount.setAddSceneprofileLink(addSceneprofileLink);
				fansCount.setAddCircleFriends(addCircleFriends);
				fansCount.setAddSceneOthers(addSceneOthers);

					//获取累计用户
					List<WxDataCubeUserCumulate> countlist=null;
					try {
					countlist = wxOpenServiceDemo.getWxOpenComponentService()
							.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getDataCubeService()
							.getUserCumulate(beginDateTime, endDateTime);
					}catch(WxErrorException e) {
						logger.error("获得累计用户出现异常:"+weixinUserinfo.getNickName());
					}
					if(countlist==null) {
						logger.error(weixinUserinfo.getNickName()+"出现错误:获得累计用户失败,id:"+weixinUserinfo.getId());
						logger.error("countlist=null");
						continue;
					}
					Integer count=countlist.get(countlist.size()-1).getCumulateUser();
					int n=0;
					WxMpUserList wxMpUserList=null;
					do {
						// 获取新关注者列表
						wxMpUserList = wxOpenServiceDemo.getWxOpenComponentService()
								.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getUserService()
								.userList(nextOpenId);
						nextOpenId=wxMpUserList.getNextOpenid();
						 male=0;
						  female=0;
						  chinese=0;
						  notChinese=0;
						  langCh=0;
						  langOther=0;
						for (String openid : wxMpUserList.getOpenids()) {
							try {
							WxMpUser userInfo = wxOpenServiceDemo.getWxOpenComponentService()
									.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getUserService()
									.userInfo(openid);
							if (userInfo == null) {
								logger.error("查询失败,无效的openid:"+openid);
							}
							Integer subscribe = userInfo.getSubscribe() ? 1 : 0;
							Integer sex = userInfo.getSex();
							if(sex==1) {
								male++;
							}else {
								female++;
							}
							String city = userInfo.getCity();
							String country = userInfo.getCountry();
							if("中国".equals(country)) {
								chinese++;
							}else {
								notChinese++;
							}
							String province = userInfo.getProvince();
							String language = userInfo.getLanguage();
							if("zh_CN".equals(language)) {
								langCh++;
							}else {
								langOther++;
							}
							Date subscribeTime=new Date(userInfo.getSubscribeTime()*1000L);
							String unionid=userInfo.getUnionId();
							Integer groupid=userInfo.getGroupId();
							
							WeixinFansInfo weixinFansInfo=new WeixinFansInfo();
							weixinFansInfo.setSubscribe(subscribe);
//							weixinFansInfo.setNickname();
							weixinFansInfo.setSex(sex);
							weixinFansInfo.setOpenid(openid);
							weixinFansInfo.setUserId(weixinUserinfo.getId());
							weixinFansInfo.setCity(city);
							weixinFansInfo.setCountry(country);
							weixinFansInfo.setProvince(province);
							weixinFansInfo.setLanguage(language);
							weixinFansInfo.setSubscribeTime(subscribeTime);
							weixinFansInfo.setUnionid(unionid);
							weixinFansInfo.setGroupid(groupid);
							weixinFansInfo.setCreateTime(createTime);
							weixinFansInfo.setUpdateTime(updateTime);
							weixinFansInfoService.insertSelective(weixinFansInfo);
						}catch(Exception e) {
							logger.error("异常："+e.getMessage());
						}
						}
						n=count-10000;
					}while(n>=10000);

					logger.info("id为" + weixinUserinfo.getId() + "的公众号新插入粉丝:" + wxMpUserList.getCount() + "结束next_openId为："
							+ wxMpUserList.getNextOpenid());
					// 获取新关注者的openid
//
//					  male=0;
//					  female=0;
//					  chinese=0;
//					  notChinese=0;
//					  langCh=0;
//					  langOther=0;
					 
//					for (String openid : openidList) {
//						try {
//						WxMpUser userInfo = wxOpenServiceDemo.getWxOpenComponentService()
//								.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getUserService()
//								.userInfo(openid);
//						if (userInfo == null) {
//							logger.error("查询失败,无效的openid:"+openid);
//						}
//						Integer subscribe = userInfo.getSubscribe() ? 1 : 0;
//						Integer sex = userInfo.getSex();
//						if(sex==1) {
//							male++;
//						}else {
//							female++;
//						}
//						String city = userInfo.getCity();
//						String country = userInfo.getCountry();
//						if("中国".equals(country)) {
//							chinese++;
//						}else {
//							notChinese++;
//						}
//						String province = userInfo.getProvince();
//						String language = userInfo.getLanguage();
//						if("zh_CN".equals(language)) {
//							langCh++;
//						}else {
//							langOther++;
//						}
//						Date subscribeTime=new Date(userInfo.getSubscribeTime()*1000L);
//						String unionid=userInfo.getUnionId();
//						Integer groupid=userInfo.getGroupId();
//						
//						WeixinFansInfo weixinFansInfo=new WeixinFansInfo();
//						weixinFansInfo.setSubscribe(subscribe);
////						weixinFansInfo.setNickname();
//						weixinFansInfo.setSex(sex);
//						weixinFansInfo.setOpenid(openid);
//						weixinFansInfo.setUserId(weixinUserinfo.getId());
//						weixinFansInfo.setCity(city);
//						weixinFansInfo.setCountry(country);
//						weixinFansInfo.setProvince(province);
//						weixinFansInfo.setLanguage(language);
//						weixinFansInfo.setSubscribeTime(subscribeTime);
//						weixinFansInfo.setUnionid(unionid);
//						weixinFansInfo.setGroupid(groupid);
//						weixinFansInfo.setCreateTime(createTime);
//						weixinFansInfo.setUpdateTime(updateTime);
//						weixinFansInfoService.insertSelective(weixinFansInfo);
//					}catch(Exception e) {
//						logger.error("异常："+e.getMessage());
//					}
//					}
					fansCount.setNikeName(weixinUserinfo.getNickName());
					fansCount.setNewFans(countNewUser);
					fansCount.setCreateTime(createTime);
					fansCount.setUpdateTime(createTime);
					fansCount.setUserId(uid);
					fansCount.setCount(count);
					fansCount.setCancel(cancelUser);
					fansCount.setMale(male);
					fansCount.setFemale(female);
					fansCount.setChinese(chinese);
					fansCount.setNotChinese(notChinese);
					fansCount.setLangCh(langCh);
					fansCount.setLangOther(langOther);
					Integer netGrowth=countNewUser-cancelUser;
					fansCount.setNetGrowth(netGrowth);
					fansCount.setNextOpenid(wxMpUserList.getNextOpenid());
//					fansAllCount+=count;
//					cancelAllCount+=cancelUser;
//					maleAllCount+=male;
//					femaleAllCount+=female;
//					chineseAllCount+=chinese;
//					notChineseAllCount+=notChinese;
//					langChAllCount+=langCh;
//					langOtherAllCount+=langOther;
//					netGrowthAllCount+=netGrowth;
//					newFansAllCount+=countNewUser;
//					
//					addSceneSearchAllCount+=addSceneSearch;
//					addSceneAccountMigrationAllCount+=addSceneAccountMigration;
//					addSceneProfileCardAllCount+=addSceneProfileCard;
//					addSceneQrCodeAllCount+=addSceneQrCode;
//					addSceneProfileItemAllCount+=addSceneProfileItem;
//					addScenePaidAllCount+=addScenePaid;
//					addSceneprofileLinkAllCount+=addSceneprofileLink;
//					addCircleFriendsAllCount+=addCircleFriends;
//					addSceneOthersAllCount+=addSceneOthers;
					weixinFansCountService.insertSelective(fansCount);
					
					Date endExcuteTime=new Date();
					Integer endTimeHour=Integer.parseInt(sdf1.format(endExcuteTime));
					Integer endTimeDay=Integer.parseInt(sdf2.format(endExcuteTime));
					logger.info("当前公众号"+weixinUserinfo.getNickName()+"统计结束");
					if(endTimeDay==day&&endTimeHour>=timeEnd) {
						logger.info("当前公众号"+weixinUserinfo.getNickName()+"统计结束");
						logger.info("统计结束,结束时间:"+endTimeHour+"点");
						break;
					}
				}catch(Exception e) {
					e.printStackTrace();
					logger.info("统计异常:"+e.getMessage());
				}

			}
//			weixinFansAllCount.setCount(fansAllCount);
//			weixinFansAllCount.setCancel(cancelAllCount);
//			weixinFansAllCount.setMale(maleAllCount);
//			weixinFansAllCount.setFemale(femaleAllCount);
//			weixinFansAllCount.setChinese(chineseAllCount);
//			weixinFansAllCount.setNotChinese(notChineseAllCount);
//			weixinFansAllCount.setLangCh(langChAllCount);
//			weixinFansAllCount.setLangOther(langOtherAllCount);
//			weixinFansAllCount.setNetGrowth(netGrowthAllCount);
//			weixinFansAllCount.setNewFans(newFansAllCount);
//			weixinFansAllCount.setAddSceneSearch(addSceneSearchAllCount);
//			weixinFansAllCount.setAddSceneAccountMigration(addSceneAccountMigrationAllCount);
//			weixinFansAllCount.setAddSceneProfileCard(addSceneProfileCardAllCount);
//			weixinFansAllCount.setAddSceneQrCode(addSceneQrCodeAllCount);
//			weixinFansAllCount.setAddSceneProfileItem(addSceneProfileItemAllCount);
//			weixinFansAllCount.setAddScenePaid(addScenePaidAllCount);
//			weixinFansAllCount.setAddSceneprofileLink(addSceneprofileLinkAllCount);
//			weixinFansAllCount.setAddCircleFriends(addCircleFriendsAllCount);
//			weixinFansAllCount.setAddSceneOthers(addSceneOthersAllCount);
			weixinFansAllCount.setCreateTime(createTime);
			weixinFansAllCount.setUpdateTime(updateTime);
			weixinFansAllCountService.insertSelective(weixinFansAllCount);

		
		return restAPIResult;
	}
	
	
	
	
	@ApiOperation(value = "test")
	@RequestMapping(value = "/api/count/test10")
	public void userCountCronTest10(Integer day,Integer timeEnd,String begin,String end){
		try {
			long timeBegin=System.currentTimeMillis();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date endDateTime = sdf.parse(begin);
			Date beginDateTime = sdf.parse(end);
			Date createTime=new Date();
			Date updateTime=createTime;
			String userNames="";
			int queryCount=0;
			
			SimpleDateFormat sdf1 = new SimpleDateFormat("HH");
			
			logger.info("查询开始时间:" + begin + "查询结束时间:" + end);

			WeixinFansAllCount weixinFansAllCount=new  WeixinFansAllCount();
			Integer fansAllCount=0;
			Integer maleAllCount=0;
			Integer femaleAllCount=0;
			Integer chineseAllCount=0;
			Integer notChineseAllCount=0;
			Integer cancelAllCount=0;
			Integer langChAllCount=0;
			Integer langOtherAllCount=0;
			Integer netGrowthAllCount=0;
			Integer newFansAllCount=0;
			
			Integer addSceneSearchAllCount=0;
			Integer addSceneAccountMigrationAllCount=0;
			Integer addSceneProfileCardAllCount=0;
			Integer addSceneQrCodeAllCount=0;
			Integer addSceneProfileItemAllCount=0;
			Integer addScenePaidAllCount=0;
			Integer addSceneprofileLinkAllCount=0;
			Integer addCircleFriendsAllCount=0;
			Integer addSceneOthersAllCount=0;
			Map<String, Object> map = new HashMap<String, Object>();
			Query query = new Query(map);
			List<WeixinUserinfo> WeixinUserinfoList = WeixinUserinfoService.selectByExample(query);
			logger.info("查询公众账号列表数量:WeixinUserinfoList", WeixinUserinfoList.size());
			for (WeixinUserinfo weixinUserinfo : WeixinUserinfoList) {
				 WeixinFansCount fansCount=new WeixinFansCount();
				Integer uid = weixinUserinfo.getId();
				// 获取增减用户
				List<WxDataCubeUserSummary> list=null;
				try {
				list = wxOpenServiceDemo.getWxOpenComponentService()
						.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getDataCubeService()
						.getUserSummary(beginDateTime, endDateTime);
				}catch(WxErrorException e) {
					logger.error("统计公众号:"+weixinUserinfo.getNickName()+"时出现异常");
					logger.error("异常信息:"+e.getMessage());
					logger.error(e.getLocalizedMessage());
				}
				if(list==null){
					logger.error("统计公众号:"+weixinUserinfo.getNickName()+"时出现异常");
					logger.error("list为null");
					continue;
				}
				// 查出数据库最后nextOpenid
				String nextOpenId=weixinFansCountService.selectNextOpenidByNikename(weixinUserinfo.getNickName());
				logger.info("查询:" + weixinUserinfo.getNickName() + "开始nextOpenid为:" + nextOpenId);
				Integer cancelUser = 0;
				Integer countNewUser = 0;
				//用户渠道
				Integer addSceneSearch=0;
				Integer addSceneAccountMigration=0;
				Integer addSceneProfileCard=0;
				Integer addSceneQrCode=0;
				Integer addSceneProfileItem=0;
				Integer addScenePaid=0;
				Integer addSceneprofileLink=0;
				Integer addCircleFriends=0;
				Integer addSceneOthers=0;
				
				//获取当前用户新增和取消用户总数
				for (WxDataCubeUserSummary wxDataCubeUserSummary : list) {
					Integer newUser=wxDataCubeUserSummary.getNewUser();
					countNewUser += newUser;
					cancelUser += wxDataCubeUserSummary.getCancelUser();
					if(wxDataCubeUserSummary.getNewUser()!=0) {
						switch(wxDataCubeUserSummary.getUserSource()) {
						//其他
						case 0:
							addSceneOthers=newUser;
			                break;  
			           case 1:  
			        	   addSceneSearch=newUser;
			                break; 
			                //名片分享 
			           case 17:  
			        	   addSceneProfileCard=newUser;
			                break;  
			                //扫描二维码
			           case 30:  
			        	   addSceneQrCode=newUser;
			                break; 
			                //图文页右上角菜单 
			           case 43:  
			        	   addSceneProfileItem=newUser;
			                break; 
			               // 51代表支付后关注
			           case 51:  
			        	   addScenePaid=newUser;
			                break; 
			                //57代表图文页内公众号名称
			           case 57:  
			        	   addSceneprofileLink=newUser;
			                break; 
			                //75代表公众号文章广告
			           case 75: 
			        	   addSceneAccountMigration=newUser;
			                break; 
			                //78代表朋友圈广告
			           case 78:  
			        	   addCircleFriends=newUser;
			                break; 
						}
					}
				}
				logger.info("查询userid为"+uid+"查询新增用户为："+countNewUser+"取消用户为"+cancelUser);
				fansCount.setAddSceneSearch(addSceneSearch);
				fansCount.setAddSceneAccountMigration(addSceneAccountMigration);
				fansCount.setAddSceneProfileCard(addSceneProfileCard);
				fansCount.setAddSceneQrCode(addSceneQrCode);
				fansCount.setAddSceneProfileItem(addSceneProfileItem);
				fansCount.setAddScenePaid(addScenePaid);
				fansCount.setAddSceneprofileLink(addSceneprofileLink);
				fansCount.setAddCircleFriends(addCircleFriends);
				fansCount.setAddSceneOthers(addSceneOthers);

					//获取累计用户
					List<WxDataCubeUserCumulate> countlist=null;
					try {
					countlist = wxOpenServiceDemo.getWxOpenComponentService()
							.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getDataCubeService()
							.getUserCumulate(beginDateTime, endDateTime);
					}catch(WxErrorException e) {
						logger.error("获得累计用户出现异常:"+weixinUserinfo.getNickName());
					}
					List<String> openidList=new ArrayList<String>();
					if(countlist==null) {
						logger.error(weixinUserinfo.getNickName()+"出现错误:获得累计用户失败,id:"+weixinUserinfo.getId());
						logger.error("countlist=null");
						break;
					}
					Integer count=countlist.get(countlist.size()-1).getCumulateUser();
					int n=0;
					WxMpUserList wxMpUserList=null;
					do {
						// 获取新关注者列表
						wxMpUserList = wxOpenServiceDemo.getWxOpenComponentService()
								.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getUserService()
								.userList(nextOpenId);
						nextOpenId=wxMpUserList.getNextOpenid();
						openidList.addAll(wxMpUserList.getOpenids());
						n=count-10000;
					}while(n>=10000);

					if(!countNewUser.equals(wxMpUserList.getCount())) {
						logger.error("查询参数不正确，新增用户总数为:"+countNewUser+"参数为:"+wxMpUserList.getCount()+"id:"+weixinUserinfo.getId());
					}
					logger.info("id为" + weixinUserinfo.getId() + "的公众号新插入粉丝:" + wxMpUserList.getCount() + "结束next_openId为："
							+ wxMpUserList.getNextOpenid());
					// 获取新关注者的openid
					List<String> newUserOpenids = wxMpUserList.getOpenids();

					 Integer male=0;
					 Integer female=0;
					 Integer chinese=0;
					 Integer notChinese=0;
					 Integer langCh=0;
					 Integer langOther=0;
					 
					for (String openid : newUserOpenids) {
						WxMpUser userInfo = wxOpenServiceDemo.getWxOpenComponentService()
								.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getUserService()
								.userInfo(openid);
						if (userInfo == null) {
							logger.error("查询失败,无效的openid:"+openid);
						}
						Integer subscribe = userInfo.getSubscribe() ? 1 : 0;
						String nikeName = userInfo.getNickname();
						Integer sex = userInfo.getSex();
						if(sex==1) {
							male++;
						}else {
							female++;
						}
						String city = userInfo.getCity();
						String country = userInfo.getCountry();
						if("中国".equals(country)) {
							chinese++;
						}else {
							notChinese++;
						}
						String province = userInfo.getProvince();
						String language = userInfo.getLanguage();
						if("zh_CN".equals(language)) {
							langCh++;
						}else {
							langOther++;
						}
						Date subscribeTime=new Date(userInfo.getSubscribeTime()*1000L);
						String unionid=userInfo.getUnionId();
						Integer groupid=userInfo.getGroupId();
						
						WeixinFansInfo weixinFansInfo=new WeixinFansInfo();
						weixinFansInfo.setSubscribe(subscribe);
						weixinFansInfo.setNickname(nikeName);
						weixinFansInfo.setSex(sex);
						weixinFansInfo.setCity(city);
						weixinFansInfo.setCountry(country);
						weixinFansInfo.setProvince(province);
						weixinFansInfo.setLanguage(language);
						weixinFansInfo.setSubscribeTime(subscribeTime);
						weixinFansInfo.setUnionid(unionid);
						weixinFansInfo.setGroupid(groupid);
						weixinFansInfo.setCreateTime(createTime);
						weixinFansInfo.setUpdateTime(updateTime);
						fansCount.setNikeName(nikeName);
						fansCount.setCreateTime(createTime);
						fansCount.setUpdateTime(createTime);
						fansCount.setNewFans(countNewUser);
						weixinFansInfoService.insertSelective(weixinFansInfo);
					}
					
					fansCount.setUserId(uid);
					fansCount.setCount(count);
					fansCount.setCancel(cancelUser);
					fansCount.setMale(male);
					fansCount.setFemale(female);
					fansCount.setChinese(chinese);
					fansCount.setNotChinese(notChinese);
					fansCount.setLangCh(langCh);
					fansCount.setLangOther(langOther);
					Integer netGrowth=countNewUser-cancelUser;
					fansCount.setNetGrowth(netGrowth);
					fansCount.setNextOpenid(wxMpUserList.getNextOpenid());
					fansAllCount+=count;
					cancelAllCount+=cancelUser;
					maleAllCount+=male;
					femaleAllCount+=female;
					chineseAllCount+=chinese;
					notChineseAllCount+=notChinese;
					langChAllCount+=langCh;
					langOtherAllCount+=langOther;
					netGrowthAllCount+=netGrowth;
					newFansAllCount+=countNewUser;
					
					addSceneSearchAllCount+=addSceneSearch;
					addSceneAccountMigrationAllCount+=addSceneAccountMigration;
					addSceneProfileCardAllCount+=addSceneProfileCard;
					addSceneQrCodeAllCount+=addSceneQrCode;
					addSceneProfileItemAllCount+=addSceneProfileItem;
					addScenePaidAllCount+=addScenePaid;
					addSceneprofileLinkAllCount+=addSceneprofileLink;
					addCircleFriendsAllCount+=addCircleFriends;
					addSceneOthersAllCount+=addSceneOthers;
					weixinFansCountService.insertSelective(fansCount);
					
					Date endExcuteTime=new Date();
					Integer endTimeHour=Integer.parseInt(sdf1.format(endExcuteTime));
					userNames+=weixinUserinfo.getNickName()+",";
					queryCount++;
					logger.info("当前公众号"+weixinUserinfo.getNickName()+"统计结束");
					logger.info("耗时:"+(System.currentTimeMillis()-timeBegin)+"毫秒");
					logger.info("已统计"+queryCount+"个公众号:"+userNames);
					if(endTimeHour>timeEnd) {
						logger.info("当前公众号"+weixinUserinfo.getNickName()+"统计结束");
						logger.info("已统计"+queryCount+"个公众号:"+userNames);
						logger.info("统计结束,结束时间:"+endTimeHour+"点");
						logger.info("耗时:"+(System.currentTimeMillis()-timeBegin)+"毫秒");
						return;
					}
			}
			weixinFansAllCount.setCount(fansAllCount);
			weixinFansAllCount.setCancel(cancelAllCount);
			weixinFansAllCount.setMale(maleAllCount);
			weixinFansAllCount.setFemale(femaleAllCount);
			weixinFansAllCount.setChinese(chineseAllCount);
			weixinFansAllCount.setNotChinese(notChineseAllCount);
			weixinFansAllCount.setLangCh(langChAllCount);
			weixinFansAllCount.setLangOther(langOtherAllCount);
			weixinFansAllCount.setNetGrowth(netGrowthAllCount);
			weixinFansAllCount.setNewFans(newFansAllCount);
			weixinFansAllCount.setAddSceneSearch(addSceneSearchAllCount);
			weixinFansAllCount.setAddSceneAccountMigration(addSceneAccountMigrationAllCount);
			weixinFansAllCount.setAddSceneProfileCard(addSceneProfileCardAllCount);
			weixinFansAllCount.setAddSceneQrCode(addSceneQrCodeAllCount);
			weixinFansAllCount.setAddSceneProfileItem(addSceneProfileItemAllCount);
			weixinFansAllCount.setAddScenePaid(addScenePaidAllCount);
			weixinFansAllCount.setAddSceneprofileLink(addSceneprofileLinkAllCount);
			weixinFansAllCount.setAddCircleFriends(addCircleFriendsAllCount);
			weixinFansAllCount.setAddSceneOthers(addSceneOthersAllCount);
			weixinFansAllCount.setCreateTime(createTime);
			weixinFansAllCount.setUpdateTime(updateTime);
			weixinFansAllCountService.insertSelective(weixinFansAllCount);

			logger.info("统计结束,耗时:"+(System.currentTimeMillis()-timeBegin)+"毫秒");
		}catch(Exception e) {
			e.printStackTrace();
			logger.info("统计异常:"+e.getMessage());
		}
	}
	
	public RestAPIResult2 test3() {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(0);
		restAPIResult.setRespMsg("失败");
		
		
		return restAPIResult;
	}
	
	@ApiOperation(value = "test")
	@RequestMapping(value = "/api/count/test5")
	public List<String> test5(String beginTime,String endTime){
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list=new ArrayList<String>();
		Query query = new Query(map);
		List<WeixinUserinfo> WeixinUserinfoList = WeixinUserinfoService.selectByExample(query);
		logger.info("查询公众账号列表数量:WeixinUserinfoList", WeixinUserinfoList.size());
		list.add("查询公众账号列表数量:WeixinUserinfoList"+WeixinUserinfoList.size());
		Integer interCount=0;
		Integer count=0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (WeixinUserinfo weixinUserinfo : WeixinUserinfoList) {
			try {
				Date beginDateTime=sdf.parse(beginTime);
				Date endDateTime =sdf.parse(endTime);
			List<WxDataCubeUserCumulate> Countlist = wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getDataCubeService()
					.getUserCumulate(beginDateTime,endDateTime);
			list.add("userid:"+weixinUserinfo.getId());
			list.add("username:"+weixinUserinfo.getNickName());
			list.add("当前公众号接口总用户量"+Countlist.get(Countlist.size()-1).getCumulateUser());
			interCount+= Countlist.get(Countlist.size()-1).getCumulateUser();
			WxMpUserList wxMpUserList = wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getUserService()
					.userList("");
			count+=wxMpUserList.getOpenids().size();
			list.add("当前公众号方法总用户量"+wxMpUserList.getOpenids().size());
			}catch(Exception e) {
				logger.error(e.getMessage());
				list.add("userid:"+weixinUserinfo.getId()+"异常："+ e.getMessage());
			}
		}
		list.add("最终用户量:"+ count);
		list.add("接口最终用户量:"+ interCount);
		
		return list;
	}

	
	@ApiOperation(value = "test")
	@RequestMapping(value = "/api/count/test6")
	public List<String> test6(String beginTime,String endTime){
		List<String> logList=new ArrayList<String>();  
		try {
			long timeBegin=System.currentTimeMillis();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Date beginDateTime=sdf.parse(beginTime);
			Date endDateTime =sdf.parse(endTime);
			
			String contextOpenId="";
			
			Date createTime=new Date();
			Date updateTime=createTime;
			logList.add("开始时间:"+beginTime);
			logList.add("结束时间:"+endTime);
			logger.info("开始时间:" + beginTime + "结束时间:" + endTime);

			Map<String, Object> map = new HashMap<String, Object>();
			Query query = new Query(map);
			List<WeixinUserinfo> WeixinUserinfoList = WeixinUserinfoService.selectByExample(query);
			logger.info("查询公众账号列表数量:WeixinUserinfoList", WeixinUserinfoList.size());
			logList.add("查询公众账号列表数量:WeixinUserinfoList"+WeixinUserinfoList.size());
			for (WeixinUserinfo weixinUserinfo : WeixinUserinfoList) {
				 WeixinFansCount fansCount=new WeixinFansCount();

				Integer uid = weixinUserinfo.getId();

				
				List<WxDataCubeUserCumulate> Countlist = wxOpenServiceDemo.getWxOpenComponentService()
						.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getDataCubeService()
						.getUserCumulate(beginDateTime, endDateTime);
					 
				Integer count=Countlist.get(Countlist.size()-1).getCumulateUser();
				logList.add("当前用户id:"+uid+"name:"+weixinUserinfo.getNickName());
				logList.add("当前用户接口总数:"+count);
				String nextOpenId="";
				List<String> openidList=new ArrayList<String>();
				int n=0;
				do {
					WxMpUserList wxMpUserList = wxOpenServiceDemo.getWxOpenComponentService()
							.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getUserService()
							.userList(nextOpenId);
					nextOpenId=wxMpUserList.getNextOpenid();
					openidList.addAll(wxMpUserList.getOpenids());
					n=count-10000;
				}while(n>=10000);
				
				
					for (String openid : openidList) {
						if(openid!=null&openid.equals(contextOpenId)) {
							logList.add("出现重复");
							continue;
						}
						WxMpUser userInfo = wxOpenServiceDemo.getWxOpenComponentService()
								.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getUserService()
								.userInfo(openid);
						if (userInfo == null) {
							continue;
						}
						Integer subscribe = userInfo.getSubscribe() ? 1 : 0;
						String nikeName = userInfo.getNickname();
						Integer sex = userInfo.getSex();
						String city = userInfo.getCity();
						String country = userInfo.getCountry();
						String province = userInfo.getProvince();
						String language = userInfo.getLanguage();
						Date subscribeTime=new Date(userInfo.getSubscribeTime());
						
						String unionid=userInfo.getUnionId();
						Integer groupid=userInfo.getGroupId();
						WeixinFansInfo weixinFansInfo=new WeixinFansInfo();
						weixinFansInfo.setSubscribe(subscribe);
						weixinFansInfo.setOpenid(openid);
						weixinFansInfo.setSex(sex);
						weixinFansInfo.setUserId(uid);
						weixinFansInfo.setCity(city);
						weixinFansInfo.setCountry(country);
						weixinFansInfo.setProvince(province);
						weixinFansInfo.setLanguage(language);
						weixinFansInfo.setSubscribeTime(subscribeTime);
						weixinFansInfo.setUnionid(unionid);
						weixinFansInfo.setGroupid(groupid);
						weixinFansInfo.setCreateTime(createTime);
						weixinFansInfo.setUpdateTime(updateTime);
						weixinFansInfoService.insertSelective(weixinFansInfo);
					}
			}

			logger.info("统计结束,耗时:"+(System.currentTimeMillis()-timeBegin)+"毫秒");
			logList.add("耗时"+ (System.currentTimeMillis()-timeBegin)+"毫秒");
		}catch(Exception e) {
			e.printStackTrace();
			logList.add("统计失败"+ e.getMessage());
			logger.info("统计失败:"+e.getMessage());
		}
		return logList;
	  }
	@ApiOperation(value = "test")
	@RequestMapping(value = "/api/count/test7")
	public List<String> test7(String beginTime,String endTime){
		List<String> logList=new ArrayList<String>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Query query = new Query(map);
			List<WeixinUserinfo> weixinUserinfoList = WeixinUserinfoService.selectByExample(query);
			logger.info("查询公众账号列表数量:WeixinUserinfoList", weixinUserinfoList.size());
			logList.add("查询公众账号列表数量:WeixinUserinfoList"+weixinUserinfoList.size());
			
			int listSize=weixinUserinfoList.size();
			List<WeixinUserinfo> l1=weixinUserinfoList.subList(0,40);
			List<WeixinUserinfo> l2=weixinUserinfoList.subList(40,80);
			List<WeixinUserinfo> l3=weixinUserinfoList.subList(80,120);
			List<WeixinUserinfo> l4=weixinUserinfoList.subList(120,160);
			List<WeixinUserinfo> l5=weixinUserinfoList.subList(160,200);
			List<WeixinUserinfo> l6=weixinUserinfoList.subList(200,240);
			List<WeixinUserinfo> l7=weixinUserinfoList.subList(240,280);
			List<WeixinUserinfo> l8=weixinUserinfoList.subList(280,320);
			List<WeixinUserinfo> l9=weixinUserinfoList.subList(320,360);
			List<WeixinUserinfo> l10=weixinUserinfoList.subList(360,400);
			List<WeixinUserinfo> l11=weixinUserinfoList.subList(400,listSize-1-400);
			
			
			
//			InsertThread insertThread1=new InsertThread(beginTime, endTime, logList, l1);
//			InsertThread insertThread2=new InsertThread(beginTime, endTime, logList, l2);
//			InsertThread insertThread3=new InsertThread(beginTime, endTime, logList, l3);
//			InsertThread insertThread4=new InsertThread(beginTime, endTime, logList, l4);
//			InsertThread insertThread5=new InsertThread(beginTime, endTime, logList, l5);
//			InsertThread insertThread6=new InsertThread(beginTime, endTime, logList, l6);
//			InsertThread insertThread7=new InsertThread(beginTime, endTime, logList, l7);
//			InsertThread insertThread8=new InsertThread(beginTime, endTime, logList, l8);
//			InsertThread insertThread9=new InsertThread(beginTime, endTime, logList, l9);
//			InsertThread insertThread10=new InsertThread(beginTime, endTime, logList, l10);
//			InsertThread insertThread11=new InsertThread(beginTime, endTime, logList, l11);
//			insertThread1.start();
//			insertThread2.start();
//			insertThread3.start();
//			insertThread4.start();
//			insertThread5.start();
//			insertThread6.start();
//			insertThread7.start();
//			insertThread8.start();
//			insertThread9.start();
//			insertThread10.start();
//			insertThread11.start();
		}catch(Exception e) {
			e.printStackTrace();
			logList.add("统计失败"+ e.getMessage());
			logger.info("统计失败:"+e.getMessage());
		}
		return logList;
	  }
	
	
	@ApiOperation(value = "test")
	@RequestMapping(value = "/api/count/test8")
	public List<String> test8(String beginTime,String endTime){
		List<String> logList=new ArrayList<String>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Query query = new Query(map);
			List<WeixinUserinfo> weixinUserinfoList = WeixinUserinfoService.selectByExample(query);
			logger.info("查询公众账号列表数量:WeixinUserinfoList", weixinUserinfoList.size());
			logList.add("查询公众账号列表数量:WeixinUserinfoList"+weixinUserinfoList.size());
			
			int listSize=weixinUserinfoList.size();
			
			
			InsertThread insertThread1=new InsertThread(beginTime, endTime, logList,weixinUserinfoList.subList(0,1),wxOpenServiceDemo,WeixinUserinfoService,weixinFansInfoService);
			InsertThread insertThread2=new InsertThread(beginTime, endTime, logList,weixinUserinfoList.subList(1,2),wxOpenServiceDemo,WeixinUserinfoService,weixinFansInfoService);
			InsertThread insertThread3=new InsertThread(beginTime, endTime, logList,weixinUserinfoList.subList(2,3),wxOpenServiceDemo,WeixinUserinfoService,weixinFansInfoService);
			InsertThread insertThread4=new InsertThread(beginTime, endTime, logList,weixinUserinfoList.subList(3,4),wxOpenServiceDemo,WeixinUserinfoService,weixinFansInfoService);
			InsertThread insertThread5=new InsertThread(beginTime, endTime, logList,weixinUserinfoList.subList(4,5),wxOpenServiceDemo,WeixinUserinfoService,weixinFansInfoService);
			
			insertThread1.start();
			insertThread2.start();
			insertThread3.start();
			insertThread4.start();
			insertThread5.start();
		}catch(Exception e) {
			e.printStackTrace();
			logList.add("统计失败"+ e.getMessage());
			logger.info("统计失败:"+e.getMessage());
		}
		return logList;
	  }
	
	
	
	@ApiOperation(value = "单用户表统计")
	@RequestMapping(value = "/api/count/tableCountOne")
	public LayUiTableResultResponse tableCountOne(Integer day,String uid) {
			Map<String,Object> m=new HashMap<String,Object>();
			String endTime=DateUtil.getNowDate("yyyy-MM-dd");
		    String beginTime=DateUtil.formatDate(DateUtil.rollDay(DateUtil.formatDate(endTime, "yyyy-MM-dd"),-day),"yyyy-MM-dd");
			logger.info("单用户统计：开始时间"+beginTime+"结束时间:"+endTime);
			m.put("createDateBegin",beginTime);
			m.put("createDateEnd",endTime);
			m.put("userId", uid);
			Query query=new Query(m);
			
			LayUiTableResultResponse LayUiTableResultResponse = weixinFansCountService.selectByQuery(query);
			return LayUiTableResultResponse;
	}
	

	@ApiOperation(value = "获取用户信息")
	@RequestMapping(value = "/api/count/userCount")
	public WeixinUserinfo userCount(String uid) {
		WeixinUserinfo weixinUserinfo=WeixinUserinfoService.selectByPrimaryKey(Integer.parseInt(uid));
		return weixinUserinfo;
	}
	
	@ApiOperation(value = "获取用户信息")
	@RequestMapping(value = "/api/count/nextopenidtest")
	public String nextopenidtest(String uid) {
		String nextOpenId=weixinFansCountService.selectNextOpenidByUserId(Integer.parseInt(uid));
		return nextOpenId;
	}
	
	@ApiOperation(value = "用户表统计")
	@RequestMapping(value = "/api/count/userTableCount")
	public LayUiTableResultResponse userTableCount(Integer page,Integer limit) {
			Map<String,Object> m=new HashMap<String,Object>();
			m.put("page", page);
			m.put("limit", limit);
			Query query=new Query(m);
			LayUiTableResultResponse LayUiTableResultResponse = weixinFansCountService.selectUserStatus(query);
			return LayUiTableResultResponse;
	}
	@ApiOperation(value = "getAppId")
	@RequestMapping(value = "/api/count/getAppId")
	public Map<String,Object> getAppId(String id) {
		Map<String,Object> map=new HashMap<String,Object>();
		String authorizerAppid=WeixinUserinfoService.selectAuthorizerAppidByPrimaryKey(Integer.parseInt(id));
		map.put("authorizerAppid", authorizerAppid);
		return map;
	}
	@ApiOperation(value = "总用户月统计")
	@RequestMapping(value = "/api/count/getMonthAllState")
	public List<WeixinFansCount> getMonthAllState(String ids) {
		Map<String,Object> map=new HashMap<String,Object>();
		String endTime=DateUtil.formatDate(DateUtil.rollDay(DateUtil.formatDate(DateUtil.getNowDate("yyyy-MM-dd"), "yyyy-MM-dd"),-30),"yyyy-MM-dd");
	    String beginTime=DateUtil.formatDate(DateUtil.rollDay(DateUtil.formatDate(DateUtil.getNowDate("yyyy-MM-dd"), "yyyy-MM-dd"),-60),"yyyy-MM-dd");
		logger.info("总用户统计：开始时间"+beginTime+"结束时间:"+endTime);
		map.put("createDateBegin",beginTime);
		map.put("createDateEnd",endTime);
		if(ids!=null&ids.endsWith(",")) {
			ids=ids.substring(0,ids.length()-1);
		}
		map.put("ids", ids);
		Query query=new Query(map);
		List<WeixinFansCount> list=weixinFansCountService.selectByExample(query);
		return list;
	}
	@ApiOperation(value = "总用户月统计")
	@RequestMapping(value = "/api/count/getMonthOneState")
	public List<WeixinFansCount> getMonthOneState(String id) {
		Map<String,Object> map=new HashMap<String,Object>();
		String endTime=DateUtil.formatDate(DateUtil.rollDay(DateUtil.formatDate(DateUtil.getNowDate("yyyy-MM-dd"), "yyyy-MM-dd"),-30),"yyyy-MM-dd");
	    String beginTime=DateUtil.formatDate(DateUtil.rollDay(DateUtil.formatDate(DateUtil.getNowDate("yyyy-MM-dd"), "yyyy-MM-dd"),-60),"yyyy-MM-dd");
		logger.info("总用户统计：开始时间"+beginTime+"结束时间:"+endTime);
		map.put("createDateBegin",beginTime);
		map.put("createDateEnd",endTime);
		map.put("userId", id);
		Query query=new Query(map);
		List<WeixinFansCount> list=weixinFansCountService.selectByExample(query);
		return list;
	}
	
	@ApiOperation(value = "获取所有用户粉丝来源")
	@RequestMapping(value = "/api/count/getFansSource")
	public Map<String,Object> getFansSource() {
		Map<String,Object> map=weixinFansAllCountService.selectFansSource();
		return map;
	}
	@ApiOperation(value = "")
	@RequestMapping(value = "/api/count/emojiInsert")
	public String emojiInsert(String content) {
		try {
			WeixinFansInfo weixinFansInfo=new WeixinFansInfo();
			weixinFansInfo.setNickname(content);
			weixinFansInfoService.insertSelective(weixinFansInfo);
		}catch(Exception e) {
			return e.getMessage();
		}
		return "success";
	}
	
	
}