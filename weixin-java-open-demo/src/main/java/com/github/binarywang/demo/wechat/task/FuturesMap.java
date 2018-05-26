package com.github.binarywang.demo.wechat.task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class FuturesMap {
	private Map<String,ScheduledFuture<?>> futures;
	public FuturesMap() {
		futures=new HashMap<String,ScheduledFuture<?>>();
	}
	public Map<String, ScheduledFuture<?>> getFutures() {
		return futures;
	}
	public void setFutures(String key,ScheduledFuture<?> future) {
		futures.put(key, future);
	}
}
