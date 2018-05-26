package com.github.binarywang.demo.wechat.task;

import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
public class TimingSendTask {
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private ScheduledFuture<?> future;
    private String cronStr = "";
	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
	   return new ThreadPoolTaskScheduler();
	}	
    public String getCronStr() {
		return cronStr;
	}
	public void setCronStr(Map<String,String> map) {
		String year="*";
		String month=map.get("month");
		if(month==null||month=="") {
			month="?";
		}
		String day=map.get("day");
		if(day==null||day=="") {
			day="?";
		}
		String hour=map.get("hour");
		if(hour==null||hour=="") {
			hour="?";
		}
		String minute=map.get("minute");
		if(minute==null||minute=="") {
			minute="?";
		}
		String second=map.get("second");
		if(second==null||second=="") {
			second="?";
		}
		this.cronStr = second+" "+minute+" "+hour+" "+day+" "+month+" "+year;
	}
	public String startCron() {
        future = threadPoolTaskScheduler.schedule(new TimingThread(), new CronTrigger(cronStr));
        System.out.println("DynamicTask.startCron()");
        return "startCron";
     }
    public Boolean stopCron() {
    	 Boolean iscancel=null;
        if (future != null) {
            iscancel=future.cancel(true);
        }
        System.out.println("DynamicTask.stopCron()");
        return iscancel;
     }
    public String changeCron(String cron) {
        stopCron();// 先停止，在开启.
        future = threadPoolTaskScheduler.schedule(new TimingThread(), new CronTrigger(cron));
        System.out.println("DynamicTask.startCron10()");
        return "changeCron";
     }
}
