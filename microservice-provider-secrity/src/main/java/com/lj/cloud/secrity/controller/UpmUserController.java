package com.lj.cloud.secrity.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lj.cloud.secrity.service.UpmUserService;
import com.lj.cloud.secrity.util.RandomValidateCodeUtil;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.secrity.UpmUser;
import com.weixindev.micro.serv.common.constant.SystemConstants;
import com.weixindev.micro.serv.common.util.Encrypt;
import com.weixindev.micro.serv.common.util.StringUtil;

/**
 *用户管理
 */
@RestController
public class UpmUserController {

	@Autowired
	UpmUserService upmUserService;
	
	/**
	 * 登陆
	 * 
	 * @param phone
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/upmUser/doLogin", method = {RequestMethod.GET,RequestMethod.POST})
	public RestAPIResult2 doLogin( String mobile, String pwd, String imgCode, HttpSession session) {
		
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(0);
		restAPIResult.setRespMsg("登录失败");
		
		if(StringUtil.isBlank(mobile) || StringUtil.isBlank(pwd)|| StringUtil.isBlank(imgCode)){
			restAPIResult.setRespMsg("参数不能为空");
			return restAPIResult;
		}
		
		//校验验证码
		String random = (String) session.getAttribute(RandomValidateCodeUtil.RANDOMCODEKEY);
		
		/** 取消验证码校验，获得验证码一直未空，有问题
		 if(!StringUtil.isEqualsIgnoreCase(random, imgCode)){
			restAPIResult.setRespMsg("验证码错误");
			return restAPIResult;
		}*/
		
		String enPwd = Encrypt.getEncrypt(pwd, "SHA-256");
		 
		UpmUser upmUser = upmUserService.login(mobile, enPwd);
		
		if(upmUser==null || (upmUser!=null &&upmUser.getId()==null)) {
			restAPIResult.setRespMsg("账号或密码错误");
			return restAPIResult;
		}
		
		restAPIResult.setRespCode(1);
	    restAPIResult.setRespMsg("登录成功");
	    return restAPIResult;
	}
	
	/**
	 * 退出
	 * 
	 * @return
	 */
	@RequestMapping(value = "/upmUser/doLoginOut", method = {RequestMethod.GET,RequestMethod.POST})
	public RestAPIResult2  doLoginOutJsonP( HttpSession session, HttpServletRequest request) throws Exception {
		
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.success(restAPIResult);;
		return restAPIResult;
	}
	
	/**
	 * 登陆
	 * 
	 * @param phone
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/upmUser/login/{mobile}/{pwd}", method = {RequestMethod.GET,RequestMethod.POST})
	public UpmUser login(@PathVariable String mobile, @PathVariable String pwd) {
		UpmUser result = upmUserService.login(mobile, pwd);
		return result;
	}

	/**
	 * 注册
	 * 
	 * @param mobile
	 * @param pwd
	 * @return
	 */
	@RequestMapping(value = "/upmUser/signup", method = RequestMethod.GET)
	public String signup(String mobile, String pwd) {
		boolean result = upmUserService.signup(mobile, pwd);
		return result ? SystemConstants.Code.SUCCESS : SystemConstants.Code.FAIL;
	}
	
	 /**
	   * 注：@GetMapping("/{id}")是spring 4.3的新注解等价于：
	   * @RequestMapping(value = "/id", method = RequestMethod.GET)
	   * 类似的注解还有@PostMapping等等
	   * @param id
	   * @return user信息
	   */
	  @GetMapping("/upmUser/{id}")
	  public UpmUser findById(@PathVariable Integer id) {
		  UpmUser findOne = this.upmUserService.selectByPrimaryKey(id);
	    return findOne;
	  }
	  
}
