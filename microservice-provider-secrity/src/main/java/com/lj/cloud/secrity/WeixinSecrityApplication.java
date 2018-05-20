package com.lj.cloud.secrity;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lj.cloud.secrity.service.SecAdminUserService;
import com.lj.cloud.secrity.util.JwtUtil;
import com.netflix.client.http.HttpRequest;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.secrity.SecAdminUser;
import com.weixindev.micro.serv.common.util.Encrypt;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class WeixinSecrityApplication {
	
	@Autowired
	
	SecAdminUserService	secAdminUserService;

	@GetMapping("/api/usloginaes")
    public @ResponseBody Object usLoginaes() {
        return "checkingrigth";
    }
	
	@GetMapping("/api/protected")
	public @ResponseBody Object hellWorld() {
		
		return "Hello World! This is a protected api";
	}

	@PostMapping("/login")
	public Object login(HttpServletResponse response,HttpServletRequest request,  @RequestBody final Account account) throws IOException {
		RestAPIResult2  RestAPIResult2 = new RestAPIResult2();
		if (isValidPassword(account)) {
			String loginNo = account.username;
			String jwt = JwtUtil.generateToken(account.username);
			RestAPIResult2.setRespCode(1);
			RestAPIResult2.setLoginNo(loginNo);
			RestAPIResult2.setRespMsg("登录成功");
			RestAPIResult2.setToken(jwt);
			request.getSession().setAttribute("loginNo",loginNo);
			return RestAPIResult2;
		} else {
			RestAPIResult2.setRespCode(0);
			RestAPIResult2.setRespMsg("登录失败");
			return RestAPIResult2;
		}
	}

	@Bean
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
		registrationBean.setFilter(filter);
		return registrationBean;
	}

	private boolean isValidPassword(Account ac) {
		String enPwd = Encrypt.getEncrypt(ac.password, "SHA-256");
		
		SecAdminUser secAdminUser = secAdminUserService.login(ac.username,enPwd);
		
		if(secAdminUser==null || (secAdminUser!=null &&secAdminUser.getId()==null)) {
		return false;
		}
		
		return true;
	}

	public static class Account {
		public String username;
		public String password;
	}

	public static void main(String[] args) {
		SpringApplication.run(WeixinSecrityApplication.class, args);
	}
}
