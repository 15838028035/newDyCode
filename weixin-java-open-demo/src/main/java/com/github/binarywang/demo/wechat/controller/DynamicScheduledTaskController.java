package com.github.binarywang.demo.wechat.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.binarywang.demo.wechat.task.WeixinImgageArticleReportTask;
import com.github.binarywang.demo.wechat.task.WeixinImgageArticleReportTaskV2;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.util.DateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "定时任务管理服务", tags = "定时任务管理服务")
@RestController
public class DynamicScheduledTaskController {

	@Autowired
	private WeixinImgageArticleReportTask weixinImgageArticleReportTask;
	

	@Autowired
	private WeixinImgageArticleReportTaskV2 WeixinImgageArticleReportTaskV2;
	
	@ApiOperation(value = "动态图文分析定时任务执行表达式")
	@RequestMapping(value="/cron", method = {RequestMethod.POST})
	public RestAPIResult2 cron(String cron) {
		RestAPIResult2 RestAPIResult2 = new RestAPIResult2();
		RestAPIResult2.setRespCode(1);
		RestAPIResult2.setRespMsg("成功");
		try {
			weixinImgageArticleReportTask.setCron(cron);
		}
		catch(Exception e) {
			e.printStackTrace();
			RestAPIResult2.setRespCode(0);
			RestAPIResult2.setRespMsg("异常信息:" + e.getMessage());
		}
		return RestAPIResult2;
	}
	
	@ApiOperation(value = "单个执行图文分析汇总")
	@RequestMapping(value="/cronUser", method = {RequestMethod.POST})
	public RestAPIResult2 cronUser(String  beginDateTime, Integer userId) {
		Date beginDate = DateUtil.formatDate(beginDateTime, DateUtil.DATE_FOMRAT_yyyy_MM_dd);
		RestAPIResult2 RestAPIResult2 = weixinImgageArticleReportTask.cronRun(beginDate, userId);
		return RestAPIResult2;
	}
	
	@ApiOperation(value = "动态图文分析定时任务执行表达式")
	@RequestMapping(value="/cronV2", method = {RequestMethod.POST})
	public RestAPIResult2 cronV2(String cron) {
		RestAPIResult2 RestAPIResult2 = new RestAPIResult2();
		RestAPIResult2.setRespCode(1);
		RestAPIResult2.setRespMsg("成功");
		
		try {
			WeixinImgageArticleReportTaskV2.setCron(cron);
		}
		catch(Exception e) {
			e.printStackTrace();
			RestAPIResult2.setRespCode(0);
			RestAPIResult2.setRespMsg("异常信息:" + e.getMessage());
		}
		return RestAPIResult2;
	}
}
