package com.github.binarywang.demo.wechat.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.github.binarywang.demo.wechat.service.WxOpenServiceDemo;
import com.lj.cloud.secrity.service.WeixinFansAllCountService;
import com.lj.cloud.secrity.service.WeixinFansCountService;
import com.lj.cloud.secrity.service.WeixinFansInfoService;
import com.lj.cloud.secrity.service.WeixinTaskRunLogService;
import com.lj.cloud.secrity.service.WeixinUserinfoService;
import com.weixindev.micro.serv.common.bean.WxMpErrorMsg;
import com.weixindev.micro.serv.common.bean.report.WeixinFansCount;
import com.weixindev.micro.serv.common.bean.report.WeixinFansInfo;
import com.weixindev.micro.serv.common.bean.report.WeixinTaskRunLog;
import com.weixindev.micro.serv.common.bean.weixin.WeixinUserinfo;
import com.weixindev.micro.serv.common.pagination.Query;
import com.weixindev.micro.serv.common.util.DateUtil;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeUserCumulate;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeUserSummary;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;

@Component
public class WeixinUserTask {
	private Logger logger = LoggerFactory.getLogger(WeixinImgageArticleReportTask.class);

	@Autowired
	private WxOpenServiceDemo wxOpenServiceDemo;
	@Autowired
	WeixinUserinfoService WeixinUserinfoService;

	@Autowired
	WeixinFansCountService weixinFansCountService;

	@Autowired
	WeixinFansInfoService weixinFansInfoService;
	@Autowired
	WeixinFansAllCountService weixinFansAllCountService;
	@Autowired
	WeixinTaskRunLogService weixinTaskRunLogService;
	

	// @Scheduled(cron = "0 0/10 * * * ?") // cron接受cron表达式，根据cron表达式确定定时规则
	// public void testCron(){
	// try {
	// logger.info("===initialDelay: 第{}次执行方法", cronCount++);
	// long timeBegin=System.currentTimeMillis();
	// // WeixinUserinfo
	// //
	// weixinUserinfo=WeixinUserinfoService.selectByPrimaryKey(Integer.parseInt(uid));
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	// String endDate = DateUtil.getNowDate(DateUtil.DATE_FOMRAT_yyyy_MM_dd);
	// Date endDateTime = DateUtil.formatDate(endDate,
	// DateUtil.DATE_FOMRAT_yyyy_MM_dd);
	// Date beginDateTime = DateUtil.rollDay(sdf.parse(endDate), -1);
	// Date createTime=new Date();
	// Date updateTime=createTime;
	// String userNames="";
	// int queryCount=0;
	//
	// SimpleDateFormat sdf1 = new SimpleDateFormat("HH");
	//
	// String beginDate = DateUtil.dateStryyyyMMdd(beginDateTime);
	// logger.info("查询开始时间:" + beginDate + "查询结束时间:" + endDate);
	//
	// WeixinFansAllCount weixinFansAllCount=new WeixinFansAllCount();
	// Integer fansAllCount=0;
	// Integer maleAllCount=0;
	// Integer femaleAllCount=0;
	// Integer chineseAllCount=0;
	// Integer notChineseAllCount=0;
	// Integer cancelAllCount=0;
	// Integer langChAllCount=0;
	// Integer langOtherAllCount=0;
	// Integer netGrowthAllCount=0;
	// Integer newFansAllCount=0;
	//
	// Integer addSceneSearchAllCount=0;
	// Integer addSceneAccountMigrationAllCount=0;
	// Integer addSceneProfileCardAllCount=0;
	// Integer addSceneQrCodeAllCount=0;
	// Integer addSceneProfileItemAllCount=0;
	// Integer addScenePaidAllCount=0;
	// Integer addSceneprofileLinkAllCount=0;
	// Integer addCircleFriendsAllCount=0;
	// Integer addSceneOthersAllCount=0;
	// Map<String, Object> map = new HashMap<String, Object>();
	// Query query = new Query(map);
	// List<WeixinUserinfo> WeixinUserinfoList =
	// WeixinUserinfoService.selectByExample(query);
	// logger.info("查询公众账号列表数量:WeixinUserinfoList", WeixinUserinfoList.size());
	// for (WeixinUserinfo weixinUserinfo : WeixinUserinfoList) {
	// WeixinFansCount fansCount=new WeixinFansCount();
	// Integer uid = weixinUserinfo.getId();
	// // 获取增减用户
	// List<WxDataCubeUserSummary> list=null;
	// try {
	// list = wxOpenServiceDemo.getWxOpenComponentService()
	// .getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getDataCubeService()
	// .getUserSummary(beginDateTime, endDateTime);
	// }catch(WxErrorException e) {
	// logger.error("统计公众号:"+weixinUserinfo.getNickName()+"时出现异常");
	// logger.error("异常信息:"+e.getMessage());
	// logger.error(e.getLocalizedMessage());
	// }
	// if(list==null){
	// logger.error("统计公众号:"+weixinUserinfo.getNickName()+"时出现异常");
	// logger.error("list为null");
	// break;
	// }
	// // 查出数据库最后nextOpenid
	// String
	// nextOpenId=weixinFansCountService.selectNextOpenidByNikename(weixinUserinfo.getNickName());
	// logger.info("查询:" + weixinUserinfo.getNickName() + "开始nextOpenid为:" +
	// nextOpenId);
	// Integer cancelUser = 0;
	// Integer countNewUser = 0;
	// //用户渠道
	// Integer addSceneSearch=0;
	// Integer addSceneAccountMigration=0;
	// Integer addSceneProfileCard=0;
	// Integer addSceneQrCode=0;
	// Integer addSceneProfileItem=0;
	// Integer addScenePaid=0;
	// Integer addSceneprofileLink=0;
	// Integer addCircleFriends=0;
	// Integer addSceneOthers=0;
	//
	// //获取当前用户新增和取消用户总数
	// for (WxDataCubeUserSummary wxDataCubeUserSummary : list) {
	// Integer newUser=wxDataCubeUserSummary.getNewUser();
	// countNewUser += newUser;
	// cancelUser += wxDataCubeUserSummary.getCancelUser();
	// if(wxDataCubeUserSummary.getNewUser()!=0) {
	// switch(wxDataCubeUserSummary.getUserSource()) {
	// //其他
	// case 0:
	// addSceneOthers=newUser;
	// break;
	// case 1:
	// addSceneSearch=newUser;
	// break;
	// //名片分享
	// case 17:
	// addSceneProfileCard=newUser;
	// break;
	// //扫描二维码
	// case 30:
	// addSceneQrCode=newUser;
	// break;
	// //图文页右上角菜单
	// case 43:
	// addSceneProfileItem=newUser;
	// break;
	// // 51代表支付后关注
	// case 51:
	// addScenePaid=newUser;
	// break;
	// //57代表图文页内公众号名称
	// case 57:
	// addSceneprofileLink=newUser;
	// break;
	// //75代表公众号文章广告
	// case 75:
	// addSceneAccountMigration=newUser;
	// break;
	// //78代表朋友圈广告
	// case 78:
	// addCircleFriends=newUser;
	// break;
	// default:
	// }
	// }
	// }
	// logger.info("查询userid为"+uid+"查询新增用户为："+countNewUser+"取消用户为"+cancelUser);
	// fansCount.setAddSceneSearch(addSceneSearch);
	// fansCount.setAddSceneAccountMigration(addSceneAccountMigration);
	// fansCount.setAddSceneProfileCard(addSceneProfileCard);
	// fansCount.setAddSceneQrCode(addSceneQrCode);
	// fansCount.setAddSceneProfileItem(addSceneProfileItem);
	// fansCount.setAddScenePaid(addScenePaid);
	// fansCount.setAddSceneprofileLink(addSceneprofileLink);
	// fansCount.setAddCircleFriends(addCircleFriends);
	// fansCount.setAddSceneOthers(addSceneOthers);
	//
	// if (countNewUser != 0) {
	// //获取累计用户
	// List<WxDataCubeUserCumulate> countlist=null;
	// try {
	// countlist = wxOpenServiceDemo.getWxOpenComponentService()
	// .getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getDataCubeService()
	// .getUserCumulate(beginDateTime, endDateTime);
	// }catch(WxErrorException e) {
	// logger.error("获得累计用户出现异常:"+weixinUserinfo.getNickName());
	// }
	// List<String> openidList=new ArrayList<String>();
	// if(countlist==null) {
	// logger.error(weixinUserinfo.getNickName()+"出现错误:获得累计用户失败,id:"+weixinUserinfo.getId());
	// logger.error("countlist=null");
	// break;
	// }
	// Integer count=countlist.get(countlist.size()-1).getCumulateUser();
	// int n=0;
	// WxMpUserList wxMpUserList=null;
	// do {
	// // 获取新关注者列表
	// wxMpUserList = wxOpenServiceDemo.getWxOpenComponentService()
	// .getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getUserService()
	// .userList(nextOpenId);
	// nextOpenId=wxMpUserList.getNextOpenid();
	// openidList.addAll(wxMpUserList.getOpenids());
	// n=count-10000;
	// }while(n>=10000);
	//
	// if(!countNewUser.equals(wxMpUserList.getCount())) {
	// logger.error("查询参数不正确，新增用户总数为:"+countNewUser+"参数为:"+wxMpUserList.getCount()+"id:"+weixinUserinfo.getId());
	// }
	// logger.info("id为" + weixinUserinfo.getId() + "的公众号新插入粉丝:" +
	// wxMpUserList.getCount() + "结束next_openId为："
	// + wxMpUserList.getNextOpenid());
	// // 获取新关注者的openid
	// List<String> newUserOpenids = wxMpUserList.getOpenids();
	//
	// Integer male=0;
	// Integer female=0;
	// Integer chinese=0;
	// Integer notChinese=0;
	// Integer langCh=0;
	// Integer langOther=0;
	//
	// for (String openid : newUserOpenids) {
	// WxMpUser userInfo = wxOpenServiceDemo.getWxOpenComponentService()
	// .getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getUserService()
	// .userInfo(openid);
	// if (userInfo == null) {
	// logger.error("查询失败,无效的openid:"+openid);
	// }
	// Integer subscribe = userInfo.getSubscribe() ? 1 : 0;
	// String nikeName = userInfo.getNickname();
	// Integer sex = userInfo.getSex();
	// if(sex==1) {
	// male++;
	// }else {
	// female++;
	// }
	// String city = userInfo.getCity();
	// String country = userInfo.getCountry();
	// if("中国".equals(country)) {
	// chinese++;
	// }else {
	// notChinese++;
	// }
	// String province = userInfo.getProvince();
	// String language = userInfo.getLanguage();
	// if("zh_CN".equals(language)) {
	// langCh++;
	// }else {
	// langOther++;
	// }
	// Date subscribeTime=new Date(userInfo.getSubscribeTime()*1000L);
	// String unionid=userInfo.getUnionId();
	// Integer groupid=userInfo.getGroupId();
	//
	// WeixinFansInfo weixinFansInfo=new WeixinFansInfo();
	// weixinFansInfo.setSubscribe(subscribe);
	// weixinFansInfo.setNickname(nikeName);
	// weixinFansInfo.setSex(sex);
	// weixinFansInfo.setCity(city);
	// weixinFansInfo.setCountry(country);
	// weixinFansInfo.setProvince(province);
	// weixinFansInfo.setLanguage(language);
	// weixinFansInfo.setSubscribeTime(subscribeTime);
	// weixinFansInfo.setUnionid(unionid);
	// weixinFansInfo.setGroupid(groupid);
	// weixinFansInfo.setCreateTime(createTime);
	// weixinFansInfo.setUpdateTime(updateTime);
	// fansCount.setNikeName(nikeName);
	// fansCount.setCreateTime(createTime);
	// fansCount.setUpdateTime(createTime);
	// fansCount.setNewFans(countNewUser);
	// weixinFansInfoService.insertSelective(weixinFansInfo);
	// }
	//
	// fansCount.setUserId(uid);
	// fansCount.setCount(count);
	// fansCount.setCancel(cancelUser);
	// fansCount.setMale(male);
	// fansCount.setFemale(female);
	// fansCount.setChinese(chinese);
	// fansCount.setNotChinese(notChinese);
	// fansCount.setLangCh(langCh);
	// fansCount.setLangOther(langOther);
	// Integer netGrowth=countNewUser-cancelUser;
	// fansCount.setNetGrowth(netGrowth);
	// fansCount.setNextOpenid(wxMpUserList.getNextOpenid());
	//
	//
	//
	// fansAllCount+=count;
	// cancelAllCount+=cancelUser;
	// maleAllCount+=male;
	// femaleAllCount+=female;
	// chineseAllCount+=chinese;
	// notChineseAllCount+=notChinese;
	// langChAllCount+=langCh;
	// langOtherAllCount+=langOther;
	// netGrowthAllCount+=netGrowth;
	// newFansAllCount+=countNewUser;
	//
	// addSceneSearchAllCount+=addSceneSearch;
	// addSceneAccountMigrationAllCount+=addSceneAccountMigration;
	// addSceneProfileCardAllCount+=addSceneProfileCard;
	// addSceneQrCodeAllCount+=addSceneQrCode;
	// addSceneProfileItemAllCount+=addSceneProfileItem;
	// addScenePaidAllCount+=addScenePaid;
	// addSceneprofileLinkAllCount+=addSceneprofileLink;
	// addCircleFriendsAllCount+=addCircleFriends;
	// addSceneOthersAllCount+=addSceneOthers;
	// weixinFansCountService.insertSelective(fansCount);
	//
	// Date endExcuteTime=new Date();
	// Integer endTimeHour=Integer.parseInt(sdf1.format(endExcuteTime));
	// userNames+=weixinUserinfo.getNickName();
	// queryCount++;
	// logger.info("当前公众号"+weixinUserinfo.getNickName()+"统计结束");
	// logger.info("耗时:"+(System.currentTimeMillis()-timeBegin)+"毫秒");
	// logger.info("已统计"+queryCount+"个公众号:"+userNames);
	// if(endTimeHour>7) {
	// logger.info("当前公众号"+weixinUserinfo.getNickName()+"统计结束");
	// logger.info("已统计"+queryCount+"个公众号:"+userNames);
	// logger.info("统计结束,结束时间:"+endTimeHour+"点");
	// logger.info("耗时:"+(System.currentTimeMillis()-timeBegin)+"毫秒");
	// return;
	// }
	// }
	// }
	// weixinFansAllCount.setCount(fansAllCount);
	// weixinFansAllCount.setCancel(cancelAllCount);
	// weixinFansAllCount.setMale(maleAllCount);
	// weixinFansAllCount.setFemale(femaleAllCount);
	// weixinFansAllCount.setChinese(chineseAllCount);
	// weixinFansAllCount.setNotChinese(notChineseAllCount);
	// weixinFansAllCount.setLangCh(langChAllCount);
	// weixinFansAllCount.setLangOther(langOtherAllCount);
	// weixinFansAllCount.setNetGrowth(netGrowthAllCount);
	// weixinFansAllCount.setNewFans(newFansAllCount);
	// weixinFansAllCount.setAddSceneSearch(addSceneSearchAllCount);
	// weixinFansAllCount.setAddSceneAccountMigration(addSceneAccountMigrationAllCount);
	// weixinFansAllCount.setAddSceneProfileCard(addSceneProfileCardAllCount);
	// weixinFansAllCount.setAddSceneQrCode(addSceneQrCodeAllCount);
	// weixinFansAllCount.setAddSceneProfileItem(addSceneProfileItemAllCount);
	// weixinFansAllCount.setAddScenePaid(addScenePaidAllCount);
	// weixinFansAllCount.setAddSceneprofileLink(addSceneprofileLinkAllCount);
	// weixinFansAllCount.setAddCircleFriends(addCircleFriendsAllCount);
	// weixinFansAllCount.setAddSceneOthers(addSceneOthersAllCount);
	// weixinFansAllCount.setCreateTime(createTime);
	// weixinFansAllCount.setUpdateTime(updateTime);
	// weixinFansAllCountService.insertSelective(weixinFansAllCount);
	//
	// logger.info("统计结束,耗时:"+(System.currentTimeMillis()-timeBegin)+"毫秒");
	// }catch(Exception e) {
	// e.printStackTrace();
	// logger.info("统计异常:"+e.getMessage());
	// }
	// }
	private int cronCount = 1;
	@Scheduled(cron = "0 0 0 * * ?") // cron接受cron表达式，根据cron表达式确定定时规则
	public void userCountCronTest() {
		logger.info("===initialDelay: 第{}次执行方法", cronCount++);
		long timeBegin = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String endDate = DateUtil.getNowDate(DateUtil.DATE_FOMRAT_yyyy_MM_dd);
		Date endDateTime = DateUtil.formatDate(endDate, DateUtil.DATE_FOMRAT_yyyy_MM_dd);
		Date beginDateTime = null;
		try {
			endDateTime = DateUtil.rollDay(sdf.parse(endDate), -1);
			beginDateTime = DateUtil.rollDay(sdf.parse(endDate), -2);
		} catch (Exception e) {
			logger.error("异常：" + e.getMessage());
		}
		StringBuffer userNames = new StringBuffer();
		SimpleDateFormat sdf1 = new SimpleDateFormat("HH");
		Integer nowHour = 0;
		
		logger.info("查询开始时间:" + DateUtil.dateStryyyyMMdd(beginDateTime) + "查询结束时间:" + endDate);
		// WeixinFansAllCount weixinFansAllCount=new WeixinFansAllCount();
		// Integer fansAllCount=0;
		// Integer maleAllCount=0;
		// Integer femaleAllCount=0;
		// Integer chineseAllCount=0;
		// Integer notChineseAllCount=0;
		// Integer cancelAllCount=0;
		// Integer langChAllCount=0;
		// Integer langOtherAllCount=0;
		// Integer netGrowthAllCount=0;
		// Integer newFansAllCount=0;
		//
		// Integer addSceneSearchAllCount=0;
		// Integer addSceneAccountMigrationAllCount=0;
		// Integer addSceneProfileCardAllCount=0;
		// Integer addSceneQrCodeAllCount=0;
		// Integer addSceneProfileItemAllCount=0;
		// Integer addScenePaidAllCount=0;
		// Integer addSceneprofileLinkAllCount=0;
		// Integer addCircleFriendsAllCount=0;
		// Integer addSceneOthersAllCount=0;
		List<WeixinUserinfo> WeixinUserinfoList = WeixinUserinfoService
				.selectByExample(new Query(new HashMap<String, Object>()));
		logger.info("查询公众账号列表数量:WeixinUserinfoList", WeixinUserinfoList.size());
		Integer male = 0;
		Integer female = 0;
		Integer chinese = 0;
		Integer notChinese = 0;
		Integer langCh = 0;
		Integer langOther = 0;
		Integer addSceneSearch = 0;
		Integer addSceneAccountMigration = 0;
		Integer addSceneProfileCard = 0;
		Integer addSceneQrCode = 0;
		Integer addSceneProfileItem = 0;
		Integer addScenePaid = 0;
		Integer addSceneprofileLink = 0;
		Integer addCircleFriends = 0;
		Integer addSceneOthers = 0;
		WeixinFansCount fansCount=null;
		WeixinTaskRunLog weixinTaskRunLog;
		for (WeixinUserinfo weixinUserinfo : WeixinUserinfoList) {
			weixinTaskRunLog=new WeixinTaskRunLog();
			weixinTaskRunLog.setTaskName("用户分析数据拉取");
			weixinTaskRunLog.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
			String logTemplate="公众号ID"+weixinUserinfo.getId()+","+weixinUserinfo.getNickName()
			+"出现异常:";
			try {
				 fansCount = new WeixinFansCount();
				// 获取增减用户
				List<WxDataCubeUserSummary> list = null;
				try {
					list = wxOpenServiceDemo.getWxOpenComponentService()
							.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getDataCubeService()
							.getUserSummary(beginDateTime, endDateTime);
				} catch (WxErrorException e) {
					logger.error("统计公众号:" + weixinUserinfo.getNickName() + "时出现异常");
					logger.error("异常信息:" + e.getMessage());
					weixinTaskRunLog.setLogsDesc(logTemplate+WxMpErrorMsg.findMsgByCode(e.getError().getErrorCode()));
					weixinTaskRunLogService.insert(weixinTaskRunLog);
				}
				if (list == null) {
					logger.error("统计公众号:" + weixinUserinfo.getNickName() + "时出现异常");
					logger.error("list为null");
					continue;
				}
				// 查出数据库最后nextOpenid
				String nextOpenId = weixinFansCountService.selectNextOpenidByUserId(weixinUserinfo.getId());
				logger.info("查询:" + weixinUserinfo.getNickName() + "开始nextOpenid为:" + nextOpenId);
				Integer cancelUser = 0;
				Integer countNewUser = 0;
				// 用户渠道
				 addSceneSearch = 0;
				 addSceneAccountMigration = 0;
				 addSceneProfileCard = 0;
				 addSceneQrCode = 0;
				 addSceneProfileItem = 0;
				 addScenePaid = 0;
				 addSceneprofileLink = 0;
				 addCircleFriends = 0;
				 addSceneOthers = 0;

				// 获取当前用户新增和取消用户总数
				for (WxDataCubeUserSummary wxDataCubeUserSummary : list) {
					try {
						Integer newUser = wxDataCubeUserSummary.getNewUser();
						countNewUser += newUser;
						cancelUser += wxDataCubeUserSummary.getCancelUser();
						if (wxDataCubeUserSummary.getNewUser() != 0) {
							switch (wxDataCubeUserSummary.getUserSource()) {
							// 其他
							case 0:
								addSceneOthers += newUser;
								break;
							case 1:
								addSceneSearch += newUser;
								break;
							// 名片分享
							case 17:
								addSceneProfileCard += newUser;
								break;
							// 扫描二维码
							case 30:
								addSceneQrCode += newUser;
								break;
							// 图文页右上角菜单
							case 43:
								addSceneProfileItem += newUser;
								break;
							// 51代表支付后关注
							case 51:
								addScenePaid += newUser;
								break;
							// 57代表图文页内公众号名称
							case 57:
								addSceneprofileLink += newUser;
								break;
							// 75代表公众号文章广告
							case 75:
								addSceneAccountMigration += newUser;
								break;
							// 78代表朋友圈广告
							case 78:
								addCircleFriends += newUser;
								break;
							}
						}
					} catch (Exception e) {
						logger.error("异常:" + e.getMessage());
						weixinTaskRunLog.setLogsDesc(logTemplate+e.getMessage());
						weixinTaskRunLogService.insert(weixinTaskRunLog);
					}
				}
				logger.info("查询userid为" + weixinUserinfo.getId() + "查询新增用户为：" + countNewUser + "取消用户为" + cancelUser);
				fansCount.setAddSceneSearch(addSceneSearch);
				fansCount.setAddSceneAccountMigration(addSceneAccountMigration);
				fansCount.setAddSceneProfileCard(addSceneProfileCard);
				fansCount.setAddSceneQrCode(addSceneQrCode);
				fansCount.setAddSceneProfileItem(addSceneProfileItem);
				fansCount.setAddScenePaid(addScenePaid);
				fansCount.setAddSceneprofileLink(addSceneprofileLink);
				fansCount.setAddCircleFriends(addCircleFriends);
				fansCount.setAddSceneOthers(addSceneOthers);

				// 获取累计用户
				List<WxDataCubeUserCumulate> countlist = null;
				try {
					countlist = wxOpenServiceDemo.getWxOpenComponentService()
							.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getDataCubeService()
							.getUserCumulate(beginDateTime, endDateTime);
				} catch (WxErrorException e) {
					logger.error("获得累计用户出现异常:" + weixinUserinfo.getNickName());
					weixinTaskRunLog.setLogsDesc(logTemplate+WxMpErrorMsg.findMsgByCode(e.getError().getErrorCode()));
					weixinTaskRunLogService.insert(weixinTaskRunLog);
				}
				if (countlist == null) {
					logger.error(weixinUserinfo.getNickName() + "出现错误:获得累计用户失败,id:" + weixinUserinfo.getId());
					logger.error("countlist=null");
					continue;
				}
				Integer count = countlist.get(countlist.size() - 1).getCumulateUser();
				WxMpUserList wxMpUserList = null;
				int n = 0;
				do {
					// 获取新关注者列表
					wxMpUserList = wxOpenServiceDemo.getWxOpenComponentService()
							.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getUserService()
							.userList(nextOpenId);
					if(wxMpUserList.getNextOpenid()!=null&&wxMpUserList.getNextOpenid()!="") {
						nextOpenId = wxMpUserList.getNextOpenid();
					}
					male = 0;
					female = 0;
					chinese = 0;
					notChinese = 0;
					langCh = 0;
					langOther = 0;
					for (String openid : wxMpUserList.getOpenids()) {
						try {
							WxMpUser userInfo = wxOpenServiceDemo.getWxOpenComponentService()
									.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getUserService()
									.userInfo(openid);
							if (userInfo == null) {
								logger.error("查询失败,无效的openid:" + openid);
								continue;
							}
							Integer sex = userInfo.getSex();
							if (sex == 1) {
								male++;
							} else {
								female++;
							}
							String city = userInfo.getCity();
							String country = userInfo.getCountry();
							if ("中国".equals(country)) {
								chinese++;
							} else {
								notChinese++;
							}
							String province = userInfo.getProvince();
							String language = userInfo.getLanguage();
							if ("zh_CN".equals(language)) {
								langCh++;
							} else {
								langOther++;
							}
							Date subscribeTime = new Date(userInfo.getSubscribeTime() * 1000L);

							WeixinFansInfo weixinFansInfo = new WeixinFansInfo();
							weixinFansInfo.setSubscribe(userInfo.getSubscribe() ? 1 : 0);
							weixinFansInfo.setSex(sex);
							weixinFansInfo.setOpenid(openid);
							weixinFansInfo.setUserId(weixinUserinfo.getId());
							weixinFansInfo.setCity(city);
							weixinFansInfo.setCountry(country);
							weixinFansInfo.setProvince(province);
							weixinFansInfo.setLanguage(language);
							weixinFansInfo.setSubscribeTime(subscribeTime);
							weixinFansInfo.setUnionid(userInfo.getUnionId());
							weixinFansInfo.setGroupid(userInfo.getGroupId());
							weixinFansInfo.setCreateTime(new Date());
							weixinFansInfo.setUpdateTime(new Date());
							weixinFansInfoService.insertSelective(weixinFansInfo);
						} catch (Exception e) {
							logger.error("异常：" + e.getMessage());
							weixinTaskRunLog.setLogsDesc(logTemplate+e.getMessage());
						}
					}
					n = count - 10000;
				} while (n >= 10000);

				logger.info("id为" + weixinUserinfo.getId() + "的公众号新插入粉丝:" + wxMpUserList.getCount() + "结束next_openId为："
						+ wxMpUserList.getNextOpenid());
				fansCount.setNikeName(weixinUserinfo.getNickName());
				fansCount.setNewFans(countNewUser);
				fansCount.setCreateTime(new Date());
				fansCount.setUpdateTime(new Date());
				fansCount.setUserId(weixinUserinfo.getId());
				fansCount.setCount(count);
				fansCount.setCancel(cancelUser);
				fansCount.setMale(male);
				fansCount.setFemale(female);
				fansCount.setChinese(chinese);
				fansCount.setNotChinese(notChinese);
				fansCount.setLangCh(langCh);
				fansCount.setLangOther(langOther);
				fansCount.setNetGrowth(countNewUser - cancelUser);
				fansCount.setNextOpenid(wxMpUserList.getNextOpenid());
				weixinFansCountService.insertSelective(fansCount);
				nowHour = Integer.parseInt(sdf1.format(new Date()));
				userNames.append(weixinUserinfo.getNickName() + ",");
				logger.info("当前公众号" + weixinUserinfo.getNickName() + "统计结束");
				logger.info("耗时:" + (System.currentTimeMillis() - timeBegin) + "毫秒");
				logger.info("已统计" + cronCount + "个公众号:" + userNames);
				if (nowHour >= 9 && nowHour < 18) {
					break;
				}
				System.gc();
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("统计异常:" + e.getMessage());
				weixinTaskRunLog.setLogsDesc(logTemplate+e.getMessage());
				weixinTaskRunLogService.insert(weixinTaskRunLog);
			}

		}
		logger.info("统计结束,耗时:" + (System.currentTimeMillis() - timeBegin) + "毫秒");
	}

}
