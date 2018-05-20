package com.github.binarywang.demo.wechat.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.binarywang.demo.wechat.service.WxOpenServiceDemo;
import com.lj.cloud.secrity.service.WeixinFansInfoService;
import com.lj.cloud.secrity.service.WeixinUserinfoService;
import com.weixindev.micro.serv.common.bean.report.WeixinFansInfo;
import com.weixindev.micro.serv.common.bean.weixin.WeixinUserinfo;

import me.chanjar.weixin.mp.bean.datacube.WxDataCubeUserCumulate;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;

public class InsertThread extends Thread {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	private WxOpenServiceDemo wxOpenServiceDemo;
	WeixinUserinfoService WeixinUserinfoService;
	WeixinFansInfoService weixinFansInfoService;

	Date beginTime;
	Date endTime;
	Date createTime;
	Date updateTime;
	String contextOpenId;
	List<String> logList;
	int errorNum=0;
	int successNum=0;
	List<WeixinUserinfo> weixinUserinfoList;
	
	public InsertThread(String beginTime,String endTime,List<String> logList,List<WeixinUserinfo> weixinUserinfoList
			,WxOpenServiceDemo wxOpenServiceDemo,WeixinUserinfoService weixinUserinfoService,WeixinFansInfoService weixinFansInfoService) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			this.beginTime=sdf.parse(beginTime);
			this.endTime =sdf.parse(endTime);
			this.logList=logList;
			this.createTime=new Date();
			this.updateTime=createTime;
			this.contextOpenId="";
			this.weixinUserinfoList=weixinUserinfoList;
			this.wxOpenServiceDemo=wxOpenServiceDemo;
			this.weixinFansInfoService=weixinFansInfoService;
			
			System.out.println("beginTime"+beginTime+",endTime="+endTime);
			
		}catch(Exception e) {
			logger.error("初始化参数错误:"+e.getMessage());
		}
	}
	
	@Override
	public void run() {
		try {
			System.out.println("--------------开始run-------------weixinUserinfoList"+ (weixinUserinfoList==null) + "wxOpenServiceDemo是否为空:"+ (wxOpenServiceDemo==null));
			for (WeixinUserinfo weixinUserinfo : weixinUserinfoList) {
				Integer uid = weixinUserinfo.getId();
				System.out.println("------uid:"+uid+"-----");
				List<WxDataCubeUserCumulate> Countlist=null;
				try {
				Countlist = wxOpenServiceDemo.getWxOpenComponentService()
						.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getDataCubeService()
						.getUserCumulate(beginTime, endTime);
				}catch(Exception e) {
					e.printStackTrace();
					logger.error("线程:"+(Thread.currentThread().getName())+"出现异常:"+e.getMessage());
				}
				System.out.println("------222-----Countlist==null"+(Countlist==null));
				if(Countlist==null ||Countlist.size()==0) {
					logger.info("当前用户id:"+uid+"name:"+weixinUserinfo.getNickName()+"获取累计用户失败");
					continue;
				}
				Integer count=Countlist.get(Countlist.size()-1).getCumulateUser();
				System.out.println("当前用户id:"+uid+"name:"+weixinUserinfo.getNickName()+"用户接口总数:"+count);
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
				List<WeixinFansInfo> batchInsert=new ArrayList<WeixinFansInfo>();
					for (String openid : openidList) {
						int index=0;
						if(openid!=null&openid.equals(contextOpenId)) {
							logger.info("出现重复");
							System.out.println("出现重复---------------");
							continue;
						}
						contextOpenId=openid;
						WxMpUser userInfo = wxOpenServiceDemo.getWxOpenComponentService()
								.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getUserService()
								.userInfo(openid);
						if (userInfo == null) {
							continue;
						}
						Integer subscribe = userInfo.getSubscribe() ? 1 : 0;
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
						weixinFansInfo.setCity(city);
						weixinFansInfo.setCountry(country);
						weixinFansInfo.setProvince(province);
						weixinFansInfo.setLanguage(language);
						weixinFansInfo.setSubscribeTime(subscribeTime);
						weixinFansInfo.setUnionid(unionid);
						weixinFansInfo.setUserId(uid);
						weixinFansInfo.setGroupid(groupid);
						weixinFansInfo.setCreateTime(createTime);
						weixinFansInfo.setUpdateTime(updateTime);
						batchInsert.add(weixinFansInfo);
						if((openidList.size()-index)<100){
							int num=weixinFansInfoService.insertBatch(batchInsert);
						}
						if(batchInsert.size()>=100) {
							int num=weixinFansInfoService.insertSelective(weixinFansInfo);
							if(num<=10) {
								errorNum+=100;
							}else {
								successNum+=100;
							}
							batchInsert.clear();
						}
						index++;
						
					}
					System.out.println("线程:"+(Thread.currentThread().getName())+"执行完毕,当前用户id:"+uid+"name:"+weixinUserinfo.getNickName()+"用户接口总数:"+count);
					System.out.println("当前用户id:"+uid+"name:"+weixinUserinfo.getNickName()+"插入完毕"
							+"插入了："+successNum+"条，失败："+errorNum+"条");
			}
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("线程:"+(Thread.currentThread().getName())+"出现异常:"+e.getMessage());
		}
	}

}
