package com.github.binarywang.demo.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.github.binarywang.demo.wechat.service.WxOpenServiceDemo;
import com.lj.cloud.secrity.service.WeixinUserinfoService;

@RestController
public class WeixinCountController {

	@Autowired
	private WxOpenServiceDemo wxOpenServiceDemo;
	@Autowired
	WeixinUserinfoService WeixinUserinfoService;
}
