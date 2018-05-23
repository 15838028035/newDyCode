package com.lj.cloud.weixin;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
@Component 
public class LoginFilter extends ZuulFilter {
	@Override
	public Object run() {
		 RequestContext ctx = RequestContext.getCurrentContext();  
	     HttpServletRequest request = ctx.getRequest();  
			String basePath = request.getScheme()+"://"+request.getServerName(); 
	     String loginNo=(String)request.getSession().getAttribute("loginNo");
	     if(loginNo==null||"".equals(loginNo)) {
	    	 try {
	    		 HttpServletResponse response=ctx.getResponse();
//	    		 response.setHeader("REDIRECT", "REDIRECT");//告诉ajax这是重定向    
//                 response.setHeader("CONTEXTPATH", "http://weixin.xrtz.org:8022/login.html");//重定向地址    
                 ctx.setSendZuulResponse(false);
                 ctx.set("isSuccess", false);
//                 ctx.setResponseStatusCode(HttpServletResponse.SC_FORBIDDEN);
//	    		 res.setHeader("", value);
                 
                 String type = request.getHeader("X-Requested-With")==null?"":request.getHeader("X-Requested-With");  
                 if ("XMLHttpRequest".equals(type)) {
                	 System.out.println("111");
                     return false;  
                 }else{//如果不是ajax请求，则直接重定向  
                	 ctx.setResponseStatusCode(200);  
                     response.sendRedirect("http://weixin.xrtz.org:8022/login.html");    
                     return false;    
                 }    
                 
			} catch (Exception e) {
				e.printStackTrace();
			};
	     }
		return null;
	}

	@Override
	public boolean shouldFilter() {
		 RequestContext ctx = RequestContext.getCurrentContext();  
	     HttpServletRequest request = ctx.getRequest();
	     String path = request.getContextPath();  

	     String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
	     if(basePath!=null&&path.endsWith(".html")) {
	    	 return true;
	     }else {
	    	 return true;
	     }
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public String filterType() {
		return "pre";
	}

}
