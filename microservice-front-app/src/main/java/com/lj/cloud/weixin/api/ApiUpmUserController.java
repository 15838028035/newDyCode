package com.lj.cloud.weixin.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lj.cloud.weixin.entity.UpmUser;
import com.lj.cloud.weixin.secrity.feign.UpmUserHystrixClient;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "用户服务", tags = "用户服务接口")
@RestController
public class ApiUpmUserController {
  @Autowired
  private UpmUserHystrixClient upmUserHystrixClient;
  
  @ApiOperation(value = "查询用户信息登陆")
  @GetMapping("/api/upmUser/{id}")
  public UpmUser findByIdFeign(@PathVariable Long id) {
    UpmUser upmUser = this.upmUserHystrixClient.findByIdFeign(id);
    return upmUser;
  }
	
  @ApiOperation(value = "登录接口")
  @GetMapping("/api/upmUser/login/")
  public UpmUser login(@PathVariable("mobile") String mobile, @PathVariable("pwd") String pwd) {
    UpmUser upmUser = this.upmUserHystrixClient.login(mobile, pwd);
    return upmUser;
  }
  
  @ApiOperation(value = "注册接口")
	@RequestMapping(value = "/api/upmUser/signup", method = RequestMethod.POST)
	public String signup(@RequestBody UpmUser upmUser) {
		return this.upmUserHystrixClient.signup(upmUser);
	}
  
}
