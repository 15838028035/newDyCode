package com.github.binarywang.demo.wechat.task;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class TimingSendTask {
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    
	
	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
	   return new ThreadPoolTaskScheduler();
	}	
	public ScheduledFuture<?> startCron(Date date,TimingThread thread) {
		ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(thread, date);
		System.out.println(future);
        System.out.println("DynamicTask.startCron()");
        return future;
     }
    public Boolean stopCron(ScheduledFuture<?> future) {
    	 Boolean iscancel=null;
        if (future != null) {
            iscancel=future.cancel(true);
        }
        System.out.println("DynamicTask.stopCron()");
        return iscancel;
     }
    public Boolean changeCron(ScheduledFuture<?> future,Date date,TimingThread thread){
    	System.out.println(future);
    	Boolean iscancel=stopCron(future);// 先停止，在开启.
    		future = threadPoolTaskScheduler.schedule(thread,date);
            System.out.println("DynamicTask.startCron10()");
            return true;
     }
}
