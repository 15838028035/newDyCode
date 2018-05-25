package com.lj.cloud.secrity.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lj.cloud.secrity.service.SecAdminUserService;
import com.lj.cloud.secrity.service.SecGroupsService;
import com.lj.cloud.secrity.util.JwtUtil;
import com.lj.cloud.secrity.util.RandomValidateCodeUtil;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.secrity.SecAdminUser;
import com.weixindev.micro.serv.common.bean.secrity.SecGroups;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
import com.weixindev.micro.serv.common.util.DateUtil;
import com.weixindev.micro.serv.common.util.Encrypt;
import com.weixindev.micro.serv.common.util.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *用户管理
 */
@Api(value = "管理员用户服务", tags = "管理员用户服务接口")
@RestController()
public class SecAdminUserController extends BaseController{

	@Autowired
	private SecAdminUserService secAdminUserService;
	@Autowired
    private SecGroupsService secGroupsService;
	
	/**
	 * 登陆
	 * 
	 * @param phone
	 * @param password
	 * @return
	 */
	@ApiOperation(value = "登录接口")
	@RequestMapping(value = "/api/SecAdminUser/doLogin", method = {RequestMethod.GET,RequestMethod.POST})
	public RestAPIResult2 doLogin( String loginiNo, String pwd, String imgCode, HttpSession session) {
		
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(0);
		restAPIResult.setRespMsg("登录失败");
		
		if(StringUtil.isBlank(loginiNo) || StringUtil.isBlank(pwd)|| StringUtil.isBlank(imgCode)){
			restAPIResult.setRespMsg("输入不能为空");
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
		 
		SecAdminUser secAdminUser = secAdminUserService.login(loginiNo, enPwd);
		
		if(secAdminUser==null || (secAdminUser!=null &&secAdminUser.getId()==null)) {
			restAPIResult.setRespMsg("账号或密码错误");
			return restAPIResult;
		}
		if(!secAdminUser.getEnableFlag().equals("1")) {
			restAPIResult.setRespMsg("账号已失效!");
			return restAPIResult;
		}
		restAPIResult.setRespCode(1);
	    restAPIResult.setRespMsg("登录成功");
	    
	    
	    String jwt = JwtUtil.generateToken(loginiNo);
	    restAPIResult.setToken(jwt);
	    
	    session.setAttribute("islogin", "1");
        
	    return restAPIResult;
	}
	
	 @ApiOperation(value = "分页")
	 @RequestMapping(value = "/api/SecAdminUser", method = RequestMethod.GET)
	  public LayUiTableResultResponse page(@RequestParam(defaultValue = "10") int limit,
	      @RequestParam(defaultValue = "1") int offset,String name,@RequestParam Map<String, Object> params) {
			Query query= new Query(params);
			LayUiTableResultResponse LayUiTableResultResponse=   secAdminUserService.selectByQuery(query);
			return LayUiTableResultResponse;
	}
	 
	 @ApiOperation(value = "新增")
	 /** 新增   */
		@RequestMapping(value = "/api/SecAdminUser",method=RequestMethod.POST)
		public RestAPIResult2 create(SecAdminUser secAdminUser)  {
			 RestAPIResult2 restAPIResult = new RestAPIResult2();
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("操作成功");
				try {
					String enPwd = Encrypt.getEncrypt(secAdminUser.getPwd(), "SHA-256");
					secAdminUser.setPwd(enPwd);
					Integer createBy = getLoginId();
					secAdminUser.setCreateBy(createBy);
					secAdminUser.setCreateByUname(getUserName());
					secAdminUser.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					String userNo=secAdminUser.getLoginiNo();
					SecAdminUser loginInfo=secAdminUserService.getUserInfoByLoginNo(userNo);
					
					if(null!=loginInfo) {
						if(loginInfo.getEnableFlag().equals("0")) {
							restAPIResult.setRespCode(0);
							restAPIResult.setRespMsg("设置失败，用户已存在  失效账户");
						}else{
						restAPIResult.setRespCode(0);
						restAPIResult.setRespMsg("设置失败，用户已存在");
						}
					}else {
						secAdminUser.setEnableFlag("1");
						secAdminUserService.insertSelective(secAdminUser);
					}
					
				}catch(Exception e) {
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg("设置失败，用户已存在:"+e.getMessage());
				}
				
				return restAPIResult;
	}
	 
	 @ApiOperation(value = "修改")
	 /** 保存更新  */
		@RequestMapping(value="/api/SecAdminUser/{id}",method=RequestMethod.PUT)
		public RestAPIResult2 update(@PathVariable("id") Integer id ,SecAdminUser secAdminUser)  {
			 RestAPIResult2 restAPIResult = new RestAPIResult2();
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("操作成功");
				try {
					
					if(secAdminUser.getId()==null){
						secAdminUser.setId(id);
					}
					String enPwd = Encrypt.getEncrypt(secAdminUser.getPwd(), "SHA-256");
					secAdminUser.setPwd(enPwd);
					Integer createBy = getLoginId();
					secAdminUser.setUpdateBy(createBy);
					secAdminUser.setUpdateByUname(getUserName());
					secAdminUser.setUpdateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					SecAdminUser loginInfo=secAdminUserService.findAdminUserQuery(secAdminUser.getLoginiNo());
					
					if(null==loginInfo) {
						secAdminUserService.updateByPrimaryKeySelective(secAdminUser);
					}else {
						if(loginInfo.getLoginiNo().equals(secAdminUser.getLoginiNo())) {
							restAPIResult.setRespCode(0);
							restAPIResult.setRespMsg("设置失败，用户已存在 ");
//							return 		restAPIResult;
						}
						
					}
					
				}catch(Exception e) {
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg("失败成功:"+e.getMessage());
				}
				
				return restAPIResult;
	}
		
	/** 显示 */
	 @ApiOperation(value = "查看")
	@RequestMapping(value="/api/SecAdminUser/{id}", method = RequestMethod.GET)
	public SecAdminUser show(@PathVariable("id") Integer id) throws Exception {
			SecAdminUser secAdminUser = secAdminUserService.selectByPrimaryKey(id);
			if(secAdminUser==null){
				secAdminUser = new SecAdminUser();
			}
			return secAdminUser;
	}
		
	/** 逻辑删除 */
	 @ApiOperation(value = "删除")
	@RequestMapping(value="/api/SecAdminUser/{id}",method=RequestMethod.DELETE)
	public RestAPIResult2 delete(@PathVariable("id") Integer id) {
		 RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("操作成功");
		 SecAdminUser secAdminUser = secAdminUserService.selectByPrimaryKey(id);
		 secAdminUser.setEnableFlag("0");//失效
		 secAdminUserService.updateByPrimaryKey(secAdminUser);
			
		return restAPIResult;
	}
	
	 @ApiOperation(value = "查看")
	@RequestMapping(value="/api/SecAdminUser/showInfo/{id}", method = RequestMethod.GET)
	public Map<String,Object> showInfo(@PathVariable("id") Integer id) throws Exception {
			Map<String,Object> retMap =new HashMap<String,Object>();
			
			SecAdminUser secAdminUser = secAdminUserService.selectByPrimaryKey(id);
			if(secAdminUser==null){
				secAdminUser = new SecAdminUser();
			}
			
			Map<String,Object> params =new HashMap<String,Object>();
			Query query= new Query(params);
			
			 List<SecGroups> secGroupsList = secGroupsService.selectByExample(query);
			 if(secGroupsList==null){
				 secGroupsList = new ArrayList<SecGroups>();
			 }
			 
			retMap.put("secAdminUser", secAdminUser);
			retMap.put("secGroupsList", secGroupsList);
			return retMap;
	}
	 
	/**  取消拦截，由zuul统一拦截 @Bean
	    public FilterRegistrationBean jwtFilter() {
	        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
	        registrationBean.setFilter(filter);
	        return registrationBean;
	    }
	 * @return */
	 
	
	 @RequestMapping(value="/api/findAdminInfo",method=RequestMethod.GET)
	 public SecAdminUser findAdminInfo(@RequestParam String loginNo) {
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("loginNo", loginNo);
		SecAdminUser adminUser = secAdminUserService.findAdminUserQuery(loginNo);
		return adminUser;
	}
	 @RequestMapping(value="/api/updatePassword",method=RequestMethod.POST)
	 public RestAPIResult2 updatePassword(@RequestParam Map<String, String> map) {
		 RestAPIResult2 restAPIResult = new RestAPIResult2();
		 String oldpwd=map.get("oldpwd");
		 String newpwd=map.get("newpwd");
		 int id=Integer.parseInt(map.get("id"));
		 SecAdminUser secAdminUser = secAdminUserService.selectByPrimaryKey(id);
		 String enPwd = Encrypt.getEncrypt(oldpwd, "SHA-256");
		 newpwd = Encrypt.getEncrypt(newpwd, "SHA-256");
		 if (secAdminUser.getPwd().equals(enPwd)) {
			 secAdminUser.setPwd(newpwd);
			 secAdminUserService.updateByPrimaryKeySelective(secAdminUser);
			 restAPIResult.setDataCode("1");
				restAPIResult.setRespMsg("密码修改成功");
		}else {
			restAPIResult.setDataCode("0");
			restAPIResult.setRespMsg("初始密码输入错误");
		}
		 return restAPIResult;
	} 
	 
}
