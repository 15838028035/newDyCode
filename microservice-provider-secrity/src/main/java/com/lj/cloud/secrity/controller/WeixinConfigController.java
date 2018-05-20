package com.lj.cloud.secrity.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lj.cloud.secrity.service.WeixinConfigService;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.weixin.WeixinConfig;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
import com.weixindev.micro.serv.common.util.DateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *管理
 */
@Api(value = "配置管理服务", tags = "配置管理服务接口")
@RestController()
public class WeixinConfigController extends BaseController{
	@Autowired
	private WeixinConfigService weixinConfigService;
	
	@ApiOperation(value = "分页")
	 @RequestMapping(value = "/api/WeixinConfig", method = RequestMethod.GET)
	  public LayUiTableResultResponse page(@RequestParam(defaultValue = "10") int limit,
	      @RequestParam(defaultValue = "1") int offset,String name,@RequestParam Map<String, Object> params) {
			Query query= new Query(params);
			LayUiTableResultResponse LayUiTableResultResponse=   weixinConfigService.selectByQuery(query);
			return LayUiTableResultResponse;
	}
	 
	 /** 新增   */
	@ApiOperation(value = "新增 ")
		@RequestMapping(value = "/api/WeixinConfig",method=RequestMethod.POST)
		public RestAPIResult2 create(WeixinConfig weixinConfig)  {
			 RestAPIResult2 restAPIResult = new RestAPIResult2();
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("操作成功");
				try {
					Integer createBy = getLoginId();
					weixinConfig.setCreateBy(createBy);
					weixinConfig.setCreateByUname(getUserName());
					weixinConfig.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					weixinConfigService.insertSelective(weixinConfig);
					
				}catch(Exception e) {
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg("失败成功:"+e.getMessage());
				}
				
				return restAPIResult;
	}
	 
	 /** 保存更新  */
	@ApiOperation(value = "修改")
		@RequestMapping(value="/api/WeixinConfig/{id}",method=RequestMethod.PUT)
		public RestAPIResult2 update(@PathVariable("id") Integer id ,WeixinConfig weixinConfig)  {
			 RestAPIResult2 restAPIResult = new RestAPIResult2();
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("操作成功");
				try {
					
					
					Integer createBy = getLoginId();
					weixinConfig.setUpdateBy(createBy);
					weixinConfig.setUpdateByUname(getUserName());
					weixinConfig.setUpdateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					weixinConfigService.updateByPrimaryKeySelective(weixinConfig);
					
				}catch(Exception e) {
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg("失败成功:"+e.getMessage());
				}
				
				return restAPIResult;
	}
		
	/** 显示 */
	@ApiOperation(value = "查看")
	@RequestMapping(value="/api/WeixinConfig/{id}", method = RequestMethod.GET)
	public WeixinConfig show(@PathVariable("id") Integer id) throws Exception {
		WeixinConfig weixinConfig =weixinConfigService.selectByPrimaryKey(id);
		if(weixinConfig== null) {
			weixinConfig = new WeixinConfig();
		}
		return weixinConfig;
	}
		
	/** 逻辑删除 */
	@ApiOperation(value = "物理删除")
	@RequestMapping(value="/api/WeixinConfig/{id}",method=RequestMethod.DELETE)
	public RestAPIResult2 delete(@PathVariable("id") Integer id) {
		 RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("操作成功");
		 weixinConfigService.deleteByPrimaryKey(id);
		return restAPIResult;
	}

	/** 显示 */
	@ApiOperation(value = "显示")
	@RequestMapping(value="/api/WeixinConfig/showInfo/{id}", method = RequestMethod.GET)
	public  Map<String,Object> showInfo(@PathVariable("id") Integer id) throws Exception {
		Map<String,Object> retMap =new HashMap<String,Object>();
		WeixinConfig weixinConfig =weixinConfigService.selectByPrimaryKey(id);
		if(weixinConfig== null) {
			weixinConfig = new WeixinConfig();
		}
		
		retMap.put("weixinConfig", weixinConfig);
		
		return retMap;
	}
	
	/** 显示 */
	@ApiOperation(value = "动态查询列表")
	@RequestMapping(value="/api/WeixinConfig/showInfoList", method = RequestMethod.GET)
	public  Map<String,Object> showInfoList(@RequestParam Map<String, Object> params) throws Exception {
		Map<String,Object> retMap =new HashMap<String,Object>();
		Query query= new Query(params);
		
		 List<WeixinConfig> list = weixinConfigService.selectByExample(query);
		 if(list==null){
			 list = new ArrayList<WeixinConfig>();
		 }
		 
		retMap.put("list", list);
		
		return retMap;
	}
	
}

