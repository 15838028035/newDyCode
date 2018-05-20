package com.lj.cloud.weixin.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "微信公众号服务", tags = "系统服务接口(临时测试用)")
@Controller
public class WeixinFunsServices {
	
	private Logger logger = LoggerFactory.getLogger(WeixinFunsServices.class);
	@ApiOperation(value="授权后分组设置")
	@GetMapping("/weixinUserInfoIngroup/{id}")
	public String GetComponentVerifyTicket(@PathVariable("id") String id) {
		return "weixinUserinfoUpdate";
	}
	
}
