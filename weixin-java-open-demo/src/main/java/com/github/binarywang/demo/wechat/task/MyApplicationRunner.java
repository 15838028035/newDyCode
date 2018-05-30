package com.github.binarywang.demo.wechat.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.github.binarywang.demo.wechat.service.WxOpenServiceDemo;
import com.lj.cloud.secrity.service.WeixinArticleTaskService;
import com.lj.cloud.secrity.service.WeixinImgService;
import com.lj.cloud.secrity.service.WeixinImgtextItemService;
import com.lj.cloud.secrity.service.WeixinPushLogService;
import com.lj.cloud.secrity.service.WeixinUserinfoService;
import com.weixindev.micro.serv.common.bean.weixin.WeixinArticleTask;

import me.chanjar.weixin.mp.api.WxMpService;
@Component 
public class MyApplicationRunner implements ApplicationRunner{

	@Autowired
	private WeixinArticleTaskService weixinArticleTaskService;
	@Autowired
	private TimingSendTask task;
	
	@Autowired
	private WxOpenServiceDemo wxOpenServiceDemo;
	@Autowired
	WxMpService WxMpService;

	@Autowired
	WeixinUserinfoService WeixinUserinfoService;

	@Autowired
	private WeixinImgtextItemService weixinImgtextItemService;
	
	@Autowired
	private WeixinPushLogService weixinPushLogService ;
	@Value("${appURL}")
	private String appURL;// 网站前台url

	@Value("${file_location}")
	private String file_location;// 文件存储路径
	@Value("${ctxAppWeixin}")
	private String ctxAppWeixin;// 微信网站路径
	@Autowired
	private WeixinImgService weixinImgService;
	@Autowired
	private FuturesMap futuresMap;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<WeixinArticleTask> list=weixinArticleTaskService.selectByTime(sdf.format(new Date()));
		int count=0;
		for(WeixinArticleTask w:list) {
			Map<String,Object> textId=new HashMap<String,Object>();
			textId.put("imgTextId", w.getImgTextId());
			TimingThread timingThread=new TimingThread(textId,  wxOpenServiceDemo,
					WxMpService,
					WeixinUserinfoService,
					 weixinImgtextItemService, weixinPushLogService,
					 weixinImgService,  weixinArticleTaskService,  file_location,
					 ctxAppWeixin,  appURL,w.getUserId(),w.getId());
			ScheduledFuture<?> future=task.startCron(sdf.parse(w.getTaskCron()), timingThread);
			String key=UUID.randomUUID().toString();
			futuresMap.setFutures(key, future);
			w.setMapKey(key);
			weixinArticleTaskService.updateByPrimaryKey(w);
			count++;
		}
		System.out.println("-------------------------");
		System.out.println("重新启动定时任务:"+count+"个");
		
	}

}
