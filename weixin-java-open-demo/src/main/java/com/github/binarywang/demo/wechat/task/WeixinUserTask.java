package com.github.binarywang.demo.wechat.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.weixindev.micro.serv.common.util.StringUtil;

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
	private WeixinUserinfoService WeixinUserinfoService;

	@Autowired
	private WeixinFansCountService weixinFansCountService;

	@Autowired
	private WeixinFansInfoService weixinFansInfoService;
	@Autowired
	private WeixinTaskRunLogService weixinTaskRunLogService;
	
	private int male;
	private int female;
	private int chinese;
	private int notChinese;
	private int langCh;
	private int langOther;
	private int addSceneSearch;
	private int addSceneAccountMigration;
	private int addSceneProfileCard;
	private int addSceneQrCode;
	private int addSceneProfileItem;
	private int addScenePaid;
	private int addSceneprofileLink;
	private int addCircleFriends;
	private int addSceneOthers;
	private int cancelUser;
	private int countNewUser;
	private List<String> openidList=new ArrayList<String>();

	private int cronCount = 1;
	@Scheduled(cron = "0 0 0 * * ?") // cron接受cron表达式，根据cron表达式确定定时规则
	public void userCountCronTest() {
		logger.info("===initialDelay: 第{}次执行方法", cronCount++);
		long timeBegin = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("HH");
		Date endDateTime=null;
		Date beginDateTime = null;
		try {
			endDateTime = DateUtil.rollDay(new Date(), -1);
			beginDateTime = DateUtil.rollDay(new Date(), -2);
		} catch (Exception e) {
			logger.error("异常：" + e.getMessage());
		}
		StringBuffer userNames = new StringBuffer();
		logger.info("查询开始时间:" + sdf.format(beginDateTime) + "查询结束时间:" + sdf.format(endDateTime));
		List<WeixinUserinfo> WeixinUserinfoList = WeixinUserinfoService
				.selectByExample(new Query(new HashMap<String, Object>()));
		logger.info("查询公众账号列表数量:WeixinUserinfoList", WeixinUserinfoList.size());
		WeixinTaskRunLog weixinTaskRunLog=new WeixinTaskRunLog();
		WeixinFansCount fansCount = new WeixinFansCount();
		String logTemplate="";
		for (WeixinUserinfo weixinUserinfo : WeixinUserinfoList) {
			initParams();
			weixinTaskRunLog.setTaskName("用户分析数据拉取");
			weixinTaskRunLog.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
			
			logTemplate="公众号ID"+weixinUserinfo.getId()+","+weixinUserinfo.getNickName()
			+"出现异常:";
			try {
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
				// 获取当前用户新增和取消用户总数
				for (WxDataCubeUserSummary wxDataCubeUserSummary : list) {
					try {
						Integer newUser = wxDataCubeUserSummary.getNewUser();
						countNewUser += newUser;
						cancelUser += wxDataCubeUserSummary.getCancelUser();
						if (newUser!=null&&newUser != 0&&wxDataCubeUserSummary.getUserSource()!=null) {
							logger.info("------------------------------------\n当前公众号:"+weixinUserinfo.getNickName()
								+"新增用户渠道:"+wxDataCubeUserSummary.getUserSource());
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
							default:
								addSceneOthers += newUser;
							}
						}
					} catch (Exception e) {
						logger.error("异常:" + e.getMessage());
						weixinTaskRunLog.setLogsDesc(logTemplate+e.getMessage());
						weixinTaskRunLogService.insert(weixinTaskRunLog);
					}
				}
				logger.info("查询userid为" + weixinUserinfo.getId() + "查询新增用户为：" + countNewUser + "取消用户为" + cancelUser);

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
				String nextOpenId =null;
				if(!openidList.isEmpty()) {
					openidList.clear();
				}
				int n = 0;
				do {
					// 获取新关注者列表
					wxMpUserList = wxOpenServiceDemo.getWxOpenComponentService()
							.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getUserService()
							.userList(nextOpenId);
					if(wxMpUserList.getNextOpenid()!=null&&wxMpUserList.getNextOpenid()!="") {
						nextOpenId = wxMpUserList.getNextOpenid();
					}
					List<String> newOpenids=getNewOpenids(weixinUserinfo.getId(),wxMpUserList.getOpenids());
					if(newOpenids!=null) {
						openidList.addAll(newOpenids);
					}else {
						openidList.addAll(wxMpUserList.getOpenids());
					}
					n = count - 10000;
				} while (n >= 10000);
				logger.info("------------------------------------\n当前公众号:"+weixinUserinfo.getNickName()
				+"新增用户为:"+countNewUser+"openidListSize为"+openidList.size());
					for (String openid : openidList) {
						try {
							WxMpUser userInfo = wxOpenServiceDemo.getWxOpenComponentService()
									.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getUserService()
									.userInfo(openid);
							if (userInfo == null) {
								logger.error("查询失败,无效的openid:" + openid);
								weixinTaskRunLog.setLogsDesc(logTemplate+"查询失败,无效的openid:" + openid);
								weixinTaskRunLogService.insert(weixinTaskRunLog);
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
							weixinTaskRunLogService.insert(weixinTaskRunLog);
						}
					}
					openidList.clear();

				logger.info("id为" + weixinUserinfo.getId() + "的公众号新插入粉丝:" + wxMpUserList.getCount());
				fansCount.setAddSceneSearch(addSceneSearch);
				fansCount.setAddSceneAccountMigration(addSceneAccountMigration);
				fansCount.setAddSceneProfileCard(addSceneProfileCard);
				fansCount.setAddSceneQrCode(addSceneQrCode);
				fansCount.setAddSceneProfileItem(addSceneProfileItem);
				fansCount.setAddScenePaid(addScenePaid);
				fansCount.setAddSceneprofileLink(addSceneprofileLink);
				fansCount.setAddCircleFriends(addCircleFriends);
				fansCount.setAddSceneOthers(addSceneOthers);
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
				fansCount.setNextOpenid(org.apache.commons.lang.StringUtils.join(list.toArray(),","));
				weixinFansCountService.insertSelective(fansCount);
				Integer nowHour = Integer.parseInt(sdf1.format(new Date()));
				userNames.append(weixinUserinfo.getNickName() + ",");
				logger.info("当前公众号" + weixinUserinfo.getNickName() + "统计结束");
				logger.info("耗时:" + (System.currentTimeMillis() - timeBegin) + "毫秒");
				logger.info("已统计公众号:" + userNames);
				if (nowHour!=null&& nowHour >= 9 && nowHour < 18) {
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
	public List<String> getNewOpenids(Integer userid,List<String> openIdList){
		String oldOpenIdStr=weixinFansCountService.selectNextOpenidByUserId(userid);
		if(oldOpenIdStr==null||oldOpenIdStr.equals("")) {
			return null;
		}
		List<String> oldOpenIdList=StringUtil.splitStringToStringList(oldOpenIdStr);
		openIdList.removeAll(oldOpenIdList);
		return openIdList;
	}
	private void initParams() {
		 male=0;
		 female=0;
		 chinese=0;
		 notChinese=0;
		 langCh=0;
		 langOther=0;
		 addSceneSearch=0;
		 addSceneAccountMigration=0;
		 addSceneProfileCard=0;
		 addSceneQrCode=0;
		 addSceneProfileItem=0;
		 addScenePaid=0;
		 addSceneprofileLink=0;
		 addCircleFriends=0;
		 addSceneOthers=0;
		 cancelUser=0;
		 countNewUser=0;
		 openidList.clear();
	}

}
