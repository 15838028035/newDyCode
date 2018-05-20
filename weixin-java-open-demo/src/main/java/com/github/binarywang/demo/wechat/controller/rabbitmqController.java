package com.github.binarywang.demo.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class rabbitmqController {
	        @Autowired
	        private rabbitmqProvider helloSender;
	        
	        @GetMapping("/test/rabbitmq")
	        public void hello() throws Exception {
	            helloSender.send();
	        }
}
