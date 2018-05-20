package com.lj.cloud.secrity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lj.cloud.secrity.service.SecPrivilegesConfigService;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.secrity.SecPrivilegesConfig;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.util.DateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *管理
 */
@Api(value = "菜单url", tags = "服务接口")
@RestController()
public class SecPrivilegesConfigController {
	@Autowired
	private SecPrivilegesConfigService secPrivilegesConfigService;
	
	@ApiOperation(value = "列表")
	 @RequestMapping(value = "/api/SecPrivilegesConfig", method = RequestMethod.GET)
	  public List<Map<String,Object>> page(String name) {
		
		   List<Map<String,Object>> LayUiTableResultResponse=   secPrivilegesConfigService.selectByQueryInfo(name);
			return LayUiTableResultResponse;
	}
	 
	 /** 新增   */
		@ApiOperation(value = "新增")
		@RequestMapping(value = "/api/SecPrivilegesConfig",method=RequestMethod.POST)
		public RestAPIResult2 create(@ModelAttribute SecPrivilegesConfig secPrivilegesConfig)  {
			 RestAPIResult2 restAPIResult = new RestAPIResult2();
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("操作成功");
				try {
					secPrivilegesConfig.setCreateBy(1);
					secPrivilegesConfig.setCreateByUname("admin01");
					secPrivilegesConfig.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					secPrivilegesConfigService.insertSelective(secPrivilegesConfig);
				}catch(Exception e) {
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg("失败成功:"+e.getMessage());
				}
				
				return restAPIResult;
	}
	 
	 /** 保存更新  */
		@ApiOperation(value = "更新")
		@RequestMapping(value="/api/SecPrivilegesConfig/{id}",method=RequestMethod.PUT)
		public RestAPIResult2 update(@PathVariable("id") Integer id ,@ModelAttribute SecPrivilegesConfig secPrivilegesConfig)  {
			 RestAPIResult2 restAPIResult = new RestAPIResult2();
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("操作成功");
				try {
					
					
					secPrivilegesConfig.setUpdateBy(1);
					secPrivilegesConfig.setUpdateByUname("admin01");
					secPrivilegesConfig.setUpdateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					secPrivilegesConfigService.updateByPrimaryKeySelective(secPrivilegesConfig);
					
				}catch(Exception e) {
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg("失败成功:"+e.getMessage());
				}
				
				return restAPIResult;
	}
		
	/** 显示 */
	@ApiOperation(value = "查看")
	@RequestMapping(value="/api/SecPrivilegesConfig/{id}", method = RequestMethod.GET)
	public SecPrivilegesConfig show(@PathVariable("id") Integer id) throws Exception {
		SecPrivilegesConfig secPrivilegesConfig =secPrivilegesConfigService.selectByPrimaryKey(id);
		if(secPrivilegesConfig== null) {
			secPrivilegesConfig = new SecPrivilegesConfig();
		}
		return secPrivilegesConfig;
	}
		
	/** 逻辑删除 */
	@ApiOperation(value = "删除")
	@RequestMapping(value="/api/SecPrivilegesConfig/{id}",method=RequestMethod.DELETE)
	public RestAPIResult2 delete(@PathVariable("id") Integer id) {
		 RestAPIResult2 restAPIResult = new RestAPIResult2();
		 int num = secPrivilegesConfigService.deleteById(id);
		 if(num>0) {
			 restAPIResult.setRespCode(1);
			 restAPIResult.setRespMsg("操作成功");
			 
		 }else {
			 restAPIResult.setRespCode(0);
			 restAPIResult.setRespMsg("操作失败");
		 }
		 
//		 secPrivilegesConfig.setEnableFlag("0");//失效
//		 secPrivilegesConfigService.updateByPrimaryKey(secPrivilegesConfig);
			
		return restAPIResult;
	}

	/** 显示 */
	@ApiOperation(value = "显示")
	@RequestMapping(value="/api/SecPrivilegesConfig/showInfo/{id}", method = RequestMethod.GET)
	public  Map<String,Object> showInfo(@PathVariable("id") Integer id) throws Exception {
		Map<String,Object> retMap =new HashMap<String,Object>();
		SecPrivilegesConfig secPrivilegesConfig =secPrivilegesConfigService.selectByPrimaryKey(id);
		if(secPrivilegesConfig== null) {
			secPrivilegesConfig = new SecPrivilegesConfig();
		}
		
		retMap.put("secPrivilegesConfig", secPrivilegesConfig);
		
		return retMap;
	}
	
}

