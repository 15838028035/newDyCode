package com.github.binarywang.demo.wechat.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.github.binarywang.demo.wechat.service.WxOpenServiceDemo;
import com.lj.cloud.secrity.service.WeixinReportArticleService;
import com.lj.cloud.secrity.service.WeixinTaskRunLogService;
import com.lj.cloud.secrity.service.WeixinUserinfoService;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.WxMpErrorMsg;
import com.weixindev.micro.serv.common.bean.report.WeixinReportArticle;
import com.weixindev.micro.serv.common.bean.report.WeixinTaskRunLog;
import com.weixindev.micro.serv.common.bean.weixin.WeixinUserinfo;
import com.weixindev.micro.serv.common.pagination.Query;
import com.weixindev.micro.serv.common.util.DateUtil;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeArticleTotal;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeArticleTotalDetail;

/**
 * Description: 构建执行定时任务
 */
@Component
public class WeixinImgageArticleReportTask  {
	 private Logger logger = LoggerFactory.getLogger(WeixinImgageArticleReportTask.class);
	 
	 private static final  FastDateFormat dateFormat = FastDateFormat.getInstance("HH:mm:ss");  

	  private static final String DEFAULT_CRON = "0 0 1 * * ?";
	  private String cron = DEFAULT_CRON;
	  
	  @Autowired
	  private ThreadPoolTaskScheduler threadPoolTaskScheduler;
	  
	  private ScheduledFuture<?> future;
	 
		@Autowired
		private WxOpenServiceDemo wxOpenServiceDemo;
		@Autowired
		WeixinUserinfoService WeixinUserinfoService;
		
		@Autowired
		private WeixinReportArticleService weixinReportArticleService;
		
		@Autowired
		private WeixinTaskRunLogService weixinTaskRunLogService;

	    private int cronCount = 1;
	  
	    private static final  FastDateFormat sdf = FastDateFormat.getInstance("yyyy-MM-dd");  
	    

	 //   @Scheduled(cron = "0 0 */1 * * ?")  //cron接受cron表达式，根据cron表达式确定定时规则
	    public void cronRun()  {
	        logger.info("===cronRun: 第{}次执行方法", cronCount++);
			
			String endDate = DateUtil.getNowDate(DateUtil.DATE_FOMRAT_yyyy_MM_dd);
			
			Date beginDateTime = DateUtil.rollDay(DateUtil.formatDate(endDate, DateUtil.DATE_FOMRAT_yyyy_MM_dd), -1);
			
			Map<String,Object> map = new HashMap<String,Object>();
			Query query = new Query(map);
			
			List<WeixinUserinfo> WeixinUserinfoList = WeixinUserinfoService.selectByExample(query);
			
			logger.info("查询公众账号列表数量:WeixinUserinfoList", WeixinUserinfoList.size());
			
			WeixinTaskRunLog weixinTaskRunLog = new WeixinTaskRunLog();
			weixinTaskRunLog.setTaskName("图文分析数据拉取汇总");
			 
			 for(WeixinUserinfo WeixinUserinfo:WeixinUserinfoList){
				
				 try{
				 Integer uid = WeixinUserinfo.getId();
				List<WxDataCubeArticleTotal> list=wxOpenServiceDemo.getWxOpenComponentService()
						.getWxMpServiceByAppid(WeixinUserinfo.getAuthorizerAppid())
						.getDataCubeService()
						.getArticleTotal(beginDateTime,beginDateTime);//每次只能查一天，开始时间和结束时间必须相等
				
				logger.info("图文统计接口返回数据:UID:"+uid + ",list:" + list.size()+ ",统计时间:" + beginDateTime + "list==null"+ (list==null));
				
				for(WxDataCubeArticleTotal WxDataCubeArticleTotal:list) {
					WeixinReportArticle WeixinReportArticle = new WeixinReportArticle();
					WeixinReportArticle.setWeixinUserId(uid);
					WeixinReportArticle.setMsgid(WxDataCubeArticleTotal.getMsgId());
					
					List<WxDataCubeArticleTotalDetail> detailList = WxDataCubeArticleTotal.getDetails();
					
					for(WxDataCubeArticleTotalDetail WxDataCubeArticleTotalDetail:detailList){
						WeixinReportArticle.setStatDate(sdf.parse(WxDataCubeArticleTotalDetail.getStatDate()));
						
						 BeanUtils.copyProperties(WxDataCubeArticleTotalDetail, WeixinReportArticle);//属性赋值
						 
						 WeixinReportArticle.setTitle(WxDataCubeArticleTotal.getTitle());
						 
						Map<String,Object> map2 = new HashMap<String,Object>();
						map2.put("weixinUserId", uid);
						map2.put("msgid", WxDataCubeArticleTotal.getMsgId());
						map2.put("statDate", WxDataCubeArticleTotalDetail.getStatDate());
						
						Query query2 = new Query(map2);
						 List<WeixinReportArticle> list2 = weixinReportArticleService.selectByExample(query2);
						 
						 if(list2!=null && list2.size()>0){
							 WeixinReportArticle.setId(list2.get(0).getId());
							
							 WeixinReportArticle.setStatDate(DateUtil.formatDate(WxDataCubeArticleTotalDetail.getStatDate(), DateUtil.DATE_FOMRAT_yyyy_MM_dd));
							 WeixinReportArticle.setUpdateBy(1);
							 WeixinReportArticle.setUpdateByUname("admin01");
							 WeixinReportArticle.setUpdateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
							 
							 logger.info("WeixinReportArticle 记录存在，执行更新uid"+uid+",msgid:"+WxDataCubeArticleTotal.getMsgId()
							 +",statDate="+WxDataCubeArticleTotalDetail.getStatDate()+",ID:"+WeixinReportArticle.getId());
							 
							 weixinReportArticleService.updateByPrimaryKeySelective(WeixinReportArticle);
							 
						 }else {
							 
							 logger.info("WeixinReportArticle 记录不存在，执行写入数据库操作uid"+uid+",msgid:"+WxDataCubeArticleTotal.getMsgId()
							 +",statDate="+WxDataCubeArticleTotalDetail.getStatDate()+",ID:"+WeixinReportArticle.getId());
							 
							 WeixinReportArticle.setCreateBy(1);
							 WeixinReportArticle.setCreateByUname("admin01");
							 WeixinReportArticle.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
							 weixinReportArticleService.insertSelective(WeixinReportArticle);
						 }
						
					}
				}
				 }catch(WxErrorException e) {
					 e.printStackTrace();
			    		Integer code = e.getError().getErrorCode();
						logger.info("定时查询汇总图文信息分析出现异常，异常信息:" +e.getMessage() + ",异常信息:"+ WxMpErrorMsg.findMsgByCode(code));
						
						weixinTaskRunLog.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
						weixinTaskRunLog.setLogsDesc("公众账号ID:" +WeixinUserinfo.getId()+",公众账号昵称:" +WeixinUserinfo.getNickName()+ ",定时查询汇总图文信息分析出现异常，异常信息:" +e.getMessage() + ",异常信息:"+ WxMpErrorMsg.findMsgByCode(code));
						weixinTaskRunLogService.insertSelective(weixinTaskRunLog);
			    }catch(Exception e) {
					 e.printStackTrace();
						logger.info("定时查询汇总图文信息分析出现异常，异常信息:" +e.getMessage() + ",异常信息:"+e.getMessage());
						
						weixinTaskRunLog.setLogsDesc("公众账号ID:" +WeixinUserinfo.getId()+",公众账号昵称:" +WeixinUserinfo.getNickName()+"定时查询汇总图文信息分析出现异常，异常信息:" +e.getMessage());
						weixinTaskRunLogService.insertSelective(weixinTaskRunLog);
			    }
				 
	    	}
	    }
	    
	    /**
	     * 对某个公众号某天的记录进行统计
	     * @param beginDateTime
	     * @param userId
	     */
	    public RestAPIResult2 cronRun(Date beginDateTime, Integer userId)  {
	    	RestAPIResult2 restAPIResult = new RestAPIResult2();
			restAPIResult.setRespCode(1);
			restAPIResult.setRespMsg("成功");
			
			StringBuffer sb = new StringBuffer("");
			
	        logger.info("===initialDelay: 第{}次执行方法", cronCount++);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id",userId);
			Query query = new Query(map);
			List<WeixinUserinfo> WeixinUserinfoList = WeixinUserinfoService.selectByExample(query);
			
			logger.info("查询公众账号列表数量:WeixinUserinfoList", WeixinUserinfoList.size());
			
			 for(WeixinUserinfo WeixinUserinfo:WeixinUserinfoList){
				
				 try{
				 Integer uid = WeixinUserinfo.getId();
				List<WxDataCubeArticleTotal> list=wxOpenServiceDemo.getWxOpenComponentService()
						.getWxMpServiceByAppid(WeixinUserinfo.getAuthorizerAppid())
						.getDataCubeService()
						.getArticleTotal(beginDateTime,beginDateTime);//每次只能查一天，开始时间和结束时间必须相等
				
				logger.info("图文统计接口返回数据:UID:"+uid + ",list:" + list.size()+ ",统计时间:" + beginDateTime + "list==null"+ (list==null));
				
				for(WxDataCubeArticleTotal WxDataCubeArticleTotal:list) {
					WeixinReportArticle WeixinReportArticle = new WeixinReportArticle();
					WeixinReportArticle.setWeixinUserId(uid);
					WeixinReportArticle.setMsgid(WxDataCubeArticleTotal.getMsgId());
					
					List<WxDataCubeArticleTotalDetail> detailList = WxDataCubeArticleTotal.getDetails();
					
					for(WxDataCubeArticleTotalDetail WxDataCubeArticleTotalDetail:detailList){
						WeixinReportArticle.setStatDate(sdf.parse(WxDataCubeArticleTotalDetail.getStatDate()));
						
						 BeanUtils.copyProperties(WxDataCubeArticleTotalDetail, WeixinReportArticle);//属性赋值
						 
						 WeixinReportArticle.setTitle(WxDataCubeArticleTotal.getTitle());
						 
						Map<String,Object> map2 = new HashMap<String,Object>();
						map2.put("weixinUserId", uid);
						map2.put("msgid", WxDataCubeArticleTotal.getMsgId());
						map2.put("statDate", WxDataCubeArticleTotalDetail.getStatDate());
						
						Query query2 = new Query(map2);
						 List<WeixinReportArticle> list2 = weixinReportArticleService.selectByExample(query2);
						 
						 if(list2!=null && list2.size()>0){
							 WeixinReportArticle.setId(list2.get(0).getId());
							
							 WeixinReportArticle.setStatDate(DateUtil.formatDate(WxDataCubeArticleTotalDetail.getStatDate(), DateUtil.DATE_FOMRAT_yyyy_MM_dd));
							 WeixinReportArticle.setUpdateBy(1);
							 WeixinReportArticle.setUpdateByUname("admin01");
							 WeixinReportArticle.setUpdateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
							 
							 logger.info("WeixinReportArticle 记录存在，执行更新uid"+uid+",msgid:"+WxDataCubeArticleTotal.getMsgId()
							 +",statDate="+WxDataCubeArticleTotalDetail.getStatDate()+",ID:"+WeixinReportArticle.getId());
							 
							 weixinReportArticleService.updateByPrimaryKeySelective(WeixinReportArticle);
							 
						 }else {
							 
							 logger.info("WeixinReportArticle 记录不存在，执行写入数据库操作uid"+uid+",msgid:"+WxDataCubeArticleTotal.getMsgId()
							 +",statDate="+WxDataCubeArticleTotalDetail.getStatDate()+",ID:"+WeixinReportArticle.getId());
							 
							 WeixinReportArticle.setCreateBy(1);
							 WeixinReportArticle.setCreateByUname("admin01");
							 WeixinReportArticle.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
							 weixinReportArticleService.insertSelective(WeixinReportArticle);
						 }
						
					}
				}
				 }catch(WxErrorException e) {
					 e.printStackTrace();
			    		Integer code = e.getError().getErrorCode();
						logger.info("定时查询汇总图文信息分析出现异常，异常信息:" +e.getMessage() + ",异常信息:"+ WxMpErrorMsg.findMsgByCode(code));
						
						sb.append(userId + "汇总图文信息分析出现异常，异常信:" +e.getMessage() + ",异常信息:"+ WxMpErrorMsg.findMsgByCode(code)+"<br/>");
			    }catch(Exception e) {
					 e.printStackTrace();
						logger.info("定时查询汇总图文信息分析出现异常，异常信息:" +e.getMessage() + ",异常信息:"+e.getMessage());
						sb.append(userId + "定时查询汇总图文信息分析出现异常，异常信息:" +e.getMessage() +"<br/>");
			    }
				 
	    	}
			 
			 if(StringUtils.isNoneBlank(sb.toString())){
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg(sb.toString());
			}
	    	
	    	return restAPIResult;
	    }
	    
	    public void setCron(String cron) {
	    	
	    	this.cron = cron;
	    	stopCron();
	    	future = threadPoolTaskScheduler.schedule(new Runnable() {

		    	@Override
		    	public void run() {
		    	try {
		    		  cronRun();
		    		  logger.info("动态修改定时任务cron参数，当前时间：" + dateFormat.format(new Date()));
		    	} catch (Exception e) {
		    		e.printStackTrace();
		    		logger.error("动态任务异常 ，异常信息:" + e.getMessage() );
		    	}
		    	}
		    	}, new Trigger() {
		    	@Override
		    	public Date nextExecutionTime(TriggerContext triggerContext) {
		    	if ("".equals(cron) || cron == null)
		    	return null;
		    	CronTrigger trigger = new CronTrigger(cron);// 定时任务触发，可修改定时任务的执行周期
		    	Date nextExecDate = trigger.nextExecutionTime(triggerContext);
		    	return nextExecDate;
		    	}
		    	});
	    	}


    	public void stopCron() {
	    	if (future != null) {
	    		future.cancel(true);//取消任务调度
	    	}
    	}
	
}
