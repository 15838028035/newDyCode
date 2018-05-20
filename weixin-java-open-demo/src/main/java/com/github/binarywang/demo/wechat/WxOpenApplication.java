package com.github.binarywang.demo.wechat;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
@SpringBootApplication()
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
@ComponentScan(basePackages={"com.lj.cloud.secrity","com.github.binarywang.demo.wechat","me.chanjar.weixin"})
public class WxOpenApplication {
	
	@Bean
	public Queue queue() {
		return new Queue("hello");
	}
	
    public static void main(String[] args) {
        SpringApplication.run(WxOpenApplication.class, args);
    }
    
    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
    	Integer availableProcessors = Runtime.getRuntime().availableProcessors();
    	ThreadPoolTaskScheduler  taskScheduler = new ThreadPoolTaskScheduler();
    	taskScheduler.setPoolSize(availableProcessors*2);//设置线程池大小
    	taskScheduler.setThreadNamePrefix("springboot-task");
    	
    	return taskScheduler;
    }
}
