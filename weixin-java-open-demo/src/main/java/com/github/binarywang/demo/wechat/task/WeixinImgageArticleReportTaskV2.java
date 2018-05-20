package com.github.binarywang.demo.wechat.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.weixindev.micro.serv.common.bean.report.WeixinReportArticle;
import com.weixindev.micro.serv.common.bean.weixin.WeixinUserinfo;
import com.weixindev.micro.serv.common.pagination.Query;
import com.weixindev.micro.serv.common.util.DateUtil;

import me.chanjar.weixin.mp.bean.datacube.WxDataCubeArticleTotal;

/**
 * Description: 构建执行定时任务
 */
@Component
public class WeixinImgageArticleReportTaskV2  {
	 private Logger logger = LoggerFactory.getLogger(WeixinImgageArticleReportTaskV2.class);
	 
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
			
			  //建立线程池  
	        ExecutorService es = Executors.newCachedThreadPool();  
	        
	        BlockingDeque<WxDataCubeArticleTotalVO> queue = new LinkedBlockingDeque<WxDataCubeArticleTotalVO>(100); 
	        
	        ImgArticleProducer ImgArticleProducer1 = new ImgArticleProducer(queue);
	        ImgArticleProducer ImgArticleProducer2 = new ImgArticleProducer(queue);
	        ImgArticleProducer ImgArticleProducer3 = new ImgArticleProducer(queue);
	        
	        WeixinImgConsumer consumer1 = new WeixinImgConsumer(queue);  
	        WeixinImgConsumer consumer2 = new WeixinImgConsumer(queue);  
	        WeixinImgConsumer consumer3 = new WeixinImgConsumer(queue);  
	        WeixinImgConsumer consumer4 = new WeixinImgConsumer(queue);  
	        WeixinImgConsumer consumer5 = new WeixinImgConsumer(queue); 
	        
	        //运行消费者  
	        es.execute(consumer1);  
	        es.execute(consumer2);  
	        es.execute(consumer3);  
	        es.execute(consumer4);  
	        es.execute(consumer5); 
			 
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
					
					WxDataCubeArticleTotalVO WxDataCubeArticleTotalVO = (WxDataCubeArticleTotalVO)WxDataCubeArticleTotal;
					WxDataCubeArticleTotalVO.setWeixinUserId(uid);
					
					 int max = 3;
				     int min = 1;
				     Random random = new Random();
				    int s = random.nextInt(max)%(max-min+1) + min;
			        
				    if(s==1){
				    	ImgArticleProducer1.putData(WxDataCubeArticleTotalVO);
				    }else if(s==2){
				    	ImgArticleProducer2.putData(WxDataCubeArticleTotalVO);
				    }else{
				    	ImgArticleProducer3.putData(WxDataCubeArticleTotalVO);
				    }
					
				}
				
				 }catch(Exception e) {
					 e.printStackTrace();
				 }
				 
	    	}
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
