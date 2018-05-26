package com.lj.cloud.secrity;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lj.cloud.secrity.service.SecAdminUserService;
import com.lj.cloud.secrity.util.JwtUtil;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.secrity.SecAdminUser;
import com.weixindev.micro.serv.common.util.Encrypt;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableRedisHttpSession
public class WeixinSecrityApplication {
	
	@Autowired
	SecAdminUserService	secAdminUserService;
	
	@Autowired
	RedisBusiness r;
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
		if(checkVerify(account.inputStr)==false) {
			RestAPIResult2.setRespCode(3);
			return RestAPIResult2;
		}
		if (isValidPassword(request,account)) {
			String loginNo = account.username;
			String jwt = JwtUtil.generateToken(account.username);
			RestAPIResult2.setRespCode(1);
			RestAPIResult2.setRespMsg("登录成功");
			RestAPIResult2.setToken(jwt);
			try {
				String enPwd = Encrypt.getEncrypt(account.password, "SHA-256");
				SecAdminUser secAdminUser = secAdminUserService.login(account.username,enPwd);
				RestAPIResult2.setRespData(secAdminUser);
				
				r.set("loginNo_"+secAdminUser.getLoginiNo(), loginNo);
				r.set("loginId_"+secAdminUser.getId(), String.valueOf(secAdminUser.getId()));
				
			} catch (Exception e) {
				System.out.println("---------------");
				System.out.println(r);
				e.printStackTrace();
			}
			return RestAPIResult2;
		} else {
			RestAPIResult2.setRespCode(0);
			RestAPIResult2.setRespMsg("登录失败");
			return RestAPIResult2;
		}
	}
	
	
	public boolean checkVerify(String inputStr) {
		 try{
		  //从session中获取随机数
		  String random = r.get(inputStr);
		  if (random == null) {
		   return false;
		  }
		  if (random.equals(inputStr)) {
		   return true;
		  } else {
		   return false;
		  }
		 }catch (Exception e){
			 System.out.println("--------------------------");
			 System.out.println(r);
		 System.out.println(e.getMessage());
		 }
		return false;
		}

	@Bean
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
		registrationBean.setFilter(filter);
		return registrationBean;
	}

	public boolean isValidPassword(HttpServletRequest request,Account ac) {
		String enPwd = Encrypt.getEncrypt(ac.password, "SHA-256");
		
		SecAdminUser secAdminUser = secAdminUserService.login(ac.username,enPwd);
		
		if(secAdminUser==null || (secAdminUser!=null &&secAdminUser.getId()==null)) {
		return false;
		}
		Date date=new Date();
		String loginIp = getIpAddr(request);
		secAdminUser.setLastLoginIp(loginIp);
		secAdminUser.setLastLoginDate(date);
		secAdminUserService.updateByPrimaryKeySelective(secAdminUser);
		return true;
	}
	
	public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "本地";
        }
       return ip;
    }
	public static class Account {
		public String username;
		public String password;
		public String inputStr;
	}

	public static void main(String[] args) {
		SpringApplication.run(WeixinSecrityApplication.class, args);
	}
}
