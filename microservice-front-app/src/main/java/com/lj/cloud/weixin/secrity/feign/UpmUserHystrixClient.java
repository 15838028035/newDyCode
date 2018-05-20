package com.lj.cloud.weixin.secrity.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lj.cloud.weixin.entity.UpmUser;
import com.lj.cloud.weixin.secrity.feign.UpmUserHystrixClient.UpmUserHystrixClientFallback;

/**
 * 使用@FeignClient注解的fallback属性，指定fallback类,name属性的值不区分大小写
 */
@FeignClient(name = "microservice-provider-secrity", fallback = UpmUserHystrixClientFallback.class)
public interface UpmUserHystrixClient {
	
	 @RequestMapping("/upmUser/{id}")
	  public UpmUser findByIdFeign(@RequestParam("id") Long id);
	 
		@RequestMapping(value = "/upmUser/login/", method = {RequestMethod.GET})
		public UpmUser login(@RequestParam("mobile") String mobile, @RequestParam("pwd") String pwd) ;

		 /** 注册
		 * 
		 * @param mobile
		 * @param pwd
		 * @return
		 */
		@RequestMapping(value = "/upmUser/signup", method = RequestMethod.POST)
		public String signup(@RequestBody UpmUser upmUser) ;
		
		/**
		@RequestMapping("api/upmGroups/{id}")
		  public UpmGroups findById( Integer id) ;*/
		

	  /**
	   * 这边采取了和Spring Cloud官方文档相同的做法，将fallback类作为内部类放入Feign的接口中，当然也可以单独写一个fallback类。
	   * @author eacdy
	   */
	  @Component
	  static class UpmUserHystrixClientFallback implements UpmUserHystrixClient {
	    private static final Logger LOGGER = LoggerFactory.getLogger(UpmUserHystrixClientFallback.class);

		@Override
		public UpmUser login(String mobile, String pwd) {
			UpmUserHystrixClientFallback.LOGGER.info("异常发生，进入fallback方法，接收的参数：id = {}", mobile);
		      UpmUser UpmUser = new UpmUser();
		      UpmUser.setId(-1);
		      UpmUser.setUserName("not found");
		      return UpmUser;
		}
		

		@Override
		public String signup(UpmUser upmUser) {
			return null;
		}


		/**
	     * hystrix fallback方法
	     * @param id id
	     * @return 默认的用户
	     */
	    @Override
	    public UpmUser findByIdFeign(Long id) {
	    	UpmUserHystrixClientFallback.LOGGER.info("异常发生，进入fallback方法，接收的参数：id = {}", id);
	      UpmUser UpmUser = new UpmUser();
	      UpmUser.setId(-1);
	      UpmUser.setUserName("not found");
	      return UpmUser;
	    }
	    
	  }
}
