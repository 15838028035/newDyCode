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

import com.lj.cloud.secrity.service.WeixinImgGroupsService;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.weixin.WeixinImgGroups;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
import com.weixindev.micro.serv.common.util.DateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *图片分组管理
 */
@Api(value = "图片分组服务", tags = "图片分组服务接口")
@RestController()
public class WeixinImgGroupsController extends BaseController{
	@Autowired
	private WeixinImgGroupsService weixinImgGroupsService;
	
	@ApiOperation(value = "分页")
	 @RequestMapping(value = "/api/WeixinImgGroups", method = RequestMethod.GET)
	  public LayUiTableResultResponse page(@RequestParam(defaultValue = "10") int limit,
	      @RequestParam(defaultValue = "1") int offset,String name,@RequestParam Map<String, Object> params) {
			Query query= new Query(params);
			LayUiTableResultResponse LayUiTableResultResponse=   weixinImgGroupsService.selectByQuery(query);
			return LayUiTableResultResponse;
	}
	 
	 /** 新增   */
	@ApiOperation(value = "新增")
		@RequestMapping(value = "/api/WeixinImgGroups",method=RequestMethod.POST)
		public RestAPIResult2 create(WeixinImgGroups weixinImgGroups)  {
			 RestAPIResult2 restAPIResult = new RestAPIResult2();
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("操作成功");
				try {
					Integer createBy = getLoginId();
					weixinImgGroups.setCreateBy(createBy);
					weixinImgGroups.setCreateByUname(getUserName());
					weixinImgGroups.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					weixinImgGroupsService.insertSelective(weixinImgGroups);
					
				}catch(Exception e) {
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg("失败成功:"+e.getMessage());
				}
				
				return restAPIResult;
	}
	 
	 /** 保存更新  */
	@ApiOperation(value = "修改 ")
		@RequestMapping(value="/api/WeixinImgGroups/{id}",method=RequestMethod.PUT)
		public RestAPIResult2 update(@PathVariable("id") Integer id ,WeixinImgGroups weixinImgGroups)  {
			 RestAPIResult2 restAPIResult = new RestAPIResult2();
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("操作成功");
				try {
					
					
					Integer createBy = getLoginId();
					weixinImgGroups.setUpdateBy(createBy);
					weixinImgGroups.setUpdateByUname(getUserName());
					weixinImgGroups.setUpdateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					weixinImgGroupsService.updateByPrimaryKeySelective(weixinImgGroups);
					
				}catch(Exception e) {
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg("失败成功:"+e.getMessage());
				}
				
				return restAPIResult;
	}
		
	/** 显示 */
	@ApiOperation(value = "显示")
	@RequestMapping(value="/api/WeixinImgGroups/{id}", method = RequestMethod.GET)
	public WeixinImgGroups show(@PathVariable("id") Integer id) throws Exception {
		WeixinImgGroups weixinImgGroups =weixinImgGroupsService.selectByPrimaryKey(id);
		if(weixinImgGroups== null) {
			weixinImgGroups = new WeixinImgGroups();
		}
		return weixinImgGroups;
	}
		
	/** 逻辑删除 */
	@ApiOperation(value = "逻辑删除")
	@RequestMapping(value="/api/WeixinImgGroups/{id}",method=RequestMethod.DELETE)
	public RestAPIResult2 delete(@PathVariable("id") Integer id) {
		 RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("操作成功");
		 WeixinImgGroups weixinImgGroups = weixinImgGroupsService.selectByPrimaryKey(id);
		 weixinImgGroups.setEnableFlag("0");//失效
		 weixinImgGroupsService.updateByPrimaryKey(weixinImgGroups);
			
		return restAPIResult;
	}

	/** 显示 */
	@ApiOperation(value = "显示")
	@RequestMapping(value="/api/WeixinImgGroups/showInfo/{id}", method = RequestMethod.GET)
	public  Map<String,Object> showInfo(@PathVariable("id") Integer id) throws Exception {
		Map<String,Object> retMap =new HashMap<String,Object>();
		WeixinImgGroups weixinImgGroups =weixinImgGroupsService.selectByPrimaryKey(id);
		if(weixinImgGroups== null) {
			weixinImgGroups = new WeixinImgGroups();
		}
		
		retMap.put("weixinImgGroups", weixinImgGroups);
		
		return retMap;
	}
	
	/** 显示 */
	@ApiOperation(value = "显示")
	@RequestMapping(value="/api/WeixinImgGroups/showInfoList", method = RequestMethod.GET)
	public  Map<String,Object> showInfoList() throws Exception {
		Map<String,Object> retMap =new HashMap<String,Object>();
		Map<String,Object> params =new HashMap<String,Object>();
		Query query= new Query(params);
		
		 List<WeixinImgGroups> list = weixinImgGroupsService.selectByExample(query);
		 if(list==null){
			 list = new ArrayList<WeixinImgGroups>();
		 }
		 
		retMap.put("list", list);
		
		return retMap;
	}
	
}

