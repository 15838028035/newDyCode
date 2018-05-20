package com.itmuch.cloud.study;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@RequestMapping("/hello")
	public String hello(){
		ServiceInstance serviceInstance = discoveryClient.getLocalServiceInstance();
		logger.info("/hello host:" + serviceInstance.getHost() + ",serviceId:" +serviceInstance.getServiceId()+ ",port:"+serviceInstance.getPort());
		return "hello";
	}
}
