package com.github.binarywang.demo.wechat.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingDeque;

import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.github.binarywang.demo.wechat.SpringTool;
import com.lj.cloud.secrity.service.WeixinReportArticleService;
import com.lj.cloud.secrity.service.WeixinTaskRunLogService;
import com.weixindev.micro.serv.common.bean.report.WeixinReportArticle;
import com.weixindev.micro.serv.common.bean.report.WeixinTaskRunLog;
import com.weixindev.micro.serv.common.pagination.Query;
import com.weixindev.micro.serv.common.util.DateUtil;

import me.chanjar.weixin.mp.bean.datacube.WxDataCubeArticleTotalDetail;

public class WeixinImgConsumer  implements Runnable {  
    private BlockingDeque<WxDataCubeArticleTotalVO> queue;  
    
    private Logger logger = LoggerFactory.getLogger(WeixinImgageArticleReportTaskV2.class);
	 
	private 	WeixinReportArticleService weixinReportArticleService =(WeixinReportArticleService) SpringTool.getApplicationContext().getBean("WeixinReportArticleService");
		
	private 	WeixinTaskRunLogService weixinTaskRunLogService =(WeixinTaskRunLogService) SpringTool.getApplicationContext().getBean("WeixinTaskRunLogService");

    private static final  FastDateFormat sdf = FastDateFormat.getInstance("yyyy-MM-dd");   
    
    public WeixinImgConsumer(BlockingDeque<WxDataCubeArticleTotalVO> queue) {  
        this.queue = queue;  
    }  
    
    public void run() {  
        System.out.println("start Consumer id : "+Thread.currentThread().getId());  
        Random r = new Random();  
        try {  
            while (true){ 
            	
            	
            	WxDataCubeArticleTotalVO data = queue.take();  
                if (null != data){ 
                	WeixinTaskRunLog weixinTaskRunLog = new WeixinTaskRunLog();
        			weixinTaskRunLog.setTaskName("图文分析数据拉取汇总");
        			Integer uid = data.getWeixinUserId();
        			
                	try{
                	WeixinReportArticle WeixinReportArticle = new WeixinReportArticle();
					WeixinReportArticle.setWeixinUserId(uid);
					WeixinReportArticle.setMsgid(data.getMsgId());
					
					List<WxDataCubeArticleTotalDetail> detailList = data.getDetails();
					
					for(WxDataCubeArticleTotalDetail WxDataCubeArticleTotalDetail:detailList){
						WeixinReportArticle.setStatDate(sdf.parse(WxDataCubeArticleTotalDetail.getStatDate()));
						
						 BeanUtils.copyProperties(WxDataCubeArticleTotalDetail, WeixinReportArticle);//属性赋值
						 
						 WeixinReportArticle.setTitle(data.getTitle());
						 
						Map<String,Object> map2 = new HashMap<String,Object>();
						map2.put("weixinUserId", uid);
						map2.put("msgid", data.getMsgId());
						map2.put("statDate", WxDataCubeArticleTotalDetail.getStatDate());
						
						Query query2 = new Query(map2);
						 List<WeixinReportArticle> list2 = weixinReportArticleService.selectByExample(query2);
						 
						 if(list2!=null && list2.size()>0){
							 WeixinReportArticle.setId(list2.get(0).getId());
							
							 WeixinReportArticle.setStatDate(DateUtil.formatDate(WxDataCubeArticleTotalDetail.getStatDate(), DateUtil.DATE_FOMRAT_yyyy_MM_dd));
							 WeixinReportArticle.setUpdateBy(1);
							 WeixinReportArticle.setUpdateByUname("admin01");
							 WeixinReportArticle.setUpdateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
							 
							 logger.info("WeixinReportArticle 记录存在，执行更新uid"+uid+",msgid:"+data.getMsgId()
							 +",statDate="+WxDataCubeArticleTotalDetail.getStatDate()+",ID:"+WeixinReportArticle.getId());
							 
							 weixinReportArticleService.updateByPrimaryKeySelective(WeixinReportArticle);
							 
						 }else {
							 
							 logger.info("WeixinReportArticle 记录不存在，执行写入数据库操作uid"+uid+",msgid:"+data.getMsgId()
							 +",statDate="+WxDataCubeArticleTotalDetail.getStatDate()+",ID:"+WeixinReportArticle.getId());
							 
							 WeixinReportArticle.setCreateBy(1);
							 WeixinReportArticle.setCreateByUname("admin01");
							 WeixinReportArticle.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
							 weixinReportArticleService.insertSelective(WeixinReportArticle);
						 }
					}
				 
			    }catch(Exception e) {
					 e.printStackTrace();
						logger.info("定时查询汇总图文信息分析出现异常，异常信息:" +e.getMessage() + ",异常信息:"+e.getMessage());
						weixinTaskRunLog.setLogsDesc("公众账号ID:" +uid+"定时查询汇总图文信息分析出现异常，异常信息:" +e.getMessage());
						weixinTaskRunLogService.insertSelective(weixinTaskRunLog);
			    }
               }
                	
            }  
        }catch (InterruptedException e){  
            e.printStackTrace();  
            Thread.currentThread().interrupt();  
        }  
    }  

}
