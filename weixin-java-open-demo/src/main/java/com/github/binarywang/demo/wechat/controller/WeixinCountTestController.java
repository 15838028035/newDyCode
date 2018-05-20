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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.binarywang.demo.wechat.service.WxOpenServiceDemo;
import com.lj.cloud.secrity.service.WeixinUserinfoService;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.weixin.WeixinUserinfo;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeArticleResult;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeArticleTotal;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeMsgResult;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeUserCumulate;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeUserSummary;

@RestController
public class WeixinCountTestController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WxOpenServiceDemo wxOpenServiceDemo;
	@Autowired
	WeixinUserinfoService WeixinUserinfoService;

	/**
	 * 获取获取用户增减数据
	 * 
	 * @return
	 * @throws WxErrorException
	 */
	@RequestMapping(value = "/countTest1")
	public Map<String, Object> test1(String beginDate, String endDate, String uid) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			WeixinUserinfo weixinUserinfo = WeixinUserinfoService.selectByPrimaryKey(Integer.parseInt(uid));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date begin = sdf.parse(beginDate);
			Date end = sdf.parse(endDate);

			List<WxDataCubeUserSummary> list = wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getDataCubeService()
					.getUserSummary(begin, end);
			Integer newUser = 0;
			Integer cancelUser = 0;
			for (WxDataCubeUserSummary w : list) {
				Date date = w.getRefDate();
				/*
				 * 用户的渠道，数值代表的含义如下： 0代表其他合计 1代表公众号搜索 17代表名片分享 30代表扫描二维码
				 *  43代表图文页右上角菜单 51代表支付后关注
				 * （在支付完成页） 57代表图文页内公众号名称 75代表公众号文章广告 78代表朋友圈广告
				 */
				Integer code = w.getUserSource();

				map.put("日期", sdf.format(date));
				if(w.getNewUser()!=0) {
					map.put(UserSourceEnum.getName(code), w.getNewUser());
				}

				if(w.getCancelUser()!=0) {
					map.put("取消关注", w.getCancelUser());
				}
				newUser += w.getNewUser();
				cancelUser += w.getCancelUser();
			}
			map.put("共关注", newUser);
			map.put("共取消", cancelUser);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("测试失败:"+e.getMessage());
			map.put("error", e.getMessage());
			return map;
		}
		return map;
	}

	/**
	 * 获取累计用户数据
	 */
	@RequestMapping(value = "/countTest2")
	public List<WxDataCubeUserCumulate> test2(String beginDate, String endDate, String uid) {
		try {
			WeixinUserinfo weixinUserinfo = WeixinUserinfoService.selectByPrimaryKey(Integer.parseInt(uid));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date begin = sdf.parse(beginDate);
			Date end = sdf.parse(endDate);
			List<WxDataCubeUserCumulate> list = wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid()).getDataCubeService()
					.getUserCumulate(begin, end);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("测试失败");
		}
		return null;
	}
	
	 /**
	   * 获取图文群发每日数据
	   */
	@RequestMapping(value="/countTest3")
	public RestAPIResult2 test3(String beginDate,String endDate,String uid) {
		RestAPIResult2 RestAPIResult2 = new RestAPIResult2();
		
		try {
			WeixinUserinfo weixinUserinfo=WeixinUserinfoService.selectByPrimaryKey(Integer.parseInt(uid));
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date begin=sdf.parse(beginDate);
			Date end=sdf.parse(endDate);
			List<WxDataCubeArticleResult> list=wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid())
					.getDataCubeService()
					.getArticleSummary(begin,end);
			RestAPIResult2.setRespData(list);
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("测试失败");
			RestAPIResult2.setRespMsg("异常信息:" + e.getMessage());
		}
		return RestAPIResult2;
	}

	@RequestMapping(value="/countTest4")
	public RestAPIResult2 test4(String beginDate,String endDate,String uid) {
		RestAPIResult2 RestAPIResult2 = new RestAPIResult2();
		
		try {
			WeixinUserinfo weixinUserinfo=WeixinUserinfoService.selectByPrimaryKey(Integer.parseInt(uid));
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date begin=sdf.parse(beginDate);
			Date end=sdf.parse(endDate);
			List<WxDataCubeArticleTotal> list=wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid())
					.getDataCubeService()
					.getArticleTotal(begin,end);
			
			RestAPIResult2.setRespData(list);
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("测试失败");
			RestAPIResult2.setRespMsg("异常信息:" + e.getMessage());
		}
		return RestAPIResult2;
	}

	
	@RequestMapping(value="/countTest5")
	public RestAPIResult2 test5(String beginDate,String endDate,String uid) {
		RestAPIResult2 RestAPIResult2 = new RestAPIResult2();
		try {
			WeixinUserinfo weixinUserinfo=WeixinUserinfoService.selectByPrimaryKey(Integer.parseInt(uid));
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date begin=sdf.parse(beginDate);
			Date end=sdf.parse(endDate);
			List<WxDataCubeArticleResult> list=wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid())
					.getDataCubeService()
					.getUserRead(begin,end);
			RestAPIResult2.setRespData(list);
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("测试失败");
			RestAPIResult2.setRespMsg("异常信息:" + e.getMessage());
		}
		return RestAPIResult2;
	}

	/**
	 * * 获取图文统计分时数据（getuserreadhour）
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param uid
	 * @return
	 */

	
	@RequestMapping(value="/countTest6")
	public RestAPIResult2 test6(String beginDate,String endDate,String uid) {
		RestAPIResult2 RestAPIResult2 = new RestAPIResult2();
		try {
			WeixinUserinfo weixinUserinfo=WeixinUserinfoService.selectByPrimaryKey(Integer.parseInt(uid));
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date begin=sdf.parse(beginDate);
			Date end=sdf.parse(endDate);
			List<WxDataCubeArticleResult> list=wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid())
					.getDataCubeService()
					.getUserReadHour(begin,end);
			RestAPIResult2.setRespData(list);
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("测试失败");
			RestAPIResult2.setRespMsg("异常信息:" + e.getMessage());
		}
		return RestAPIResult2;
	}

	/**
	 * * 获取图文分享转发数据（getusershare）
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param uid
	 * @return
	 */
	@RequestMapping(value="/countTest7")
	public RestAPIResult2 test7(String beginDate,String endDate,String uid) {
		
		RestAPIResult2 RestAPIResult2 = new RestAPIResult2();
		try {
			WeixinUserinfo weixinUserinfo=WeixinUserinfoService.selectByPrimaryKey(Integer.parseInt(uid));
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date begin=sdf.parse(beginDate);
			Date end=sdf.parse(endDate);
			List<WxDataCubeArticleResult> list=wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid())
					.getDataCubeService()
					.getUserShare(begin,end);
			RestAPIResult2.setRespData(list);
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("测试失败");
			RestAPIResult2.setRespMsg("异常信息:" + e.getMessage());
		}
		return RestAPIResult2;
	}

	/**
	 * * 获取图文分享转发分时数据（getusersharehour）
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param uid
	 * @return
	 */
	@RequestMapping(value="/countTest8")
	public RestAPIResult2 test8(String beginDate,String endDate,String uid) {
		RestAPIResult2 RestAPIResult2 = new RestAPIResult2();
		try {
			WeixinUserinfo weixinUserinfo=WeixinUserinfoService.selectByPrimaryKey(Integer.parseInt(uid));
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date begin=sdf.parse(beginDate);
			Date end=sdf.parse(endDate);
			List<WxDataCubeArticleResult> list=wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid())
					.getDataCubeService()
					.getUserShareHour(begin,end);
			RestAPIResult2.setRespData(list);
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("测试失败");
			RestAPIResult2.setRespMsg("异常信息:" + e.getMessage());
		}
		return RestAPIResult2;
	}

	/**
	 * * 获取消息发送概况数据（getupstreammsg）
	 */
	@RequestMapping(value="/countTest9")
	public RestAPIResult2 test9(String beginDate,String endDate,String uid) {
		RestAPIResult2 RestAPIResult2 = new RestAPIResult2();
		try {
			WeixinUserinfo weixinUserinfo=WeixinUserinfoService.selectByPrimaryKey(Integer.parseInt(uid));
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date begin=sdf.parse(beginDate);
			Date end=sdf.parse(endDate);
			List<WxDataCubeMsgResult> list=wxOpenServiceDemo.getWxOpenComponentService()
					.getWxMpServiceByAppid(weixinUserinfo.getAuthorizerAppid())
					.getDataCubeService()
					.getUpstreamMsg(begin,end);
			RestAPIResult2.setRespData(list);
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("测试失败");
			RestAPIResult2.setRespMsg("异常信息:" + e.getMessage());
		}
		return RestAPIResult2;
	}
}
