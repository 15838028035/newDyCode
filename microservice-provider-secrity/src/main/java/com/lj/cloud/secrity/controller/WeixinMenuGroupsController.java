package com.lj.cloud.secrity.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lj.cloud.secrity.service.WeixinMenuGroupsService;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.weixin.WeixinMenuGroups;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
import com.weixindev.micro.serv.common.util.DateUtil;

/**
 *管理
 */
@RestController()
public class WeixinMenuGroupsController extends BaseController{
	@Autowired
	private WeixinMenuGroupsService weixinMenuGroupsService;
	
	 @RequestMapping(value = "/api/WeixinMenuGroups", method = RequestMethod.GET)
	  public LayUiTableResultResponse page(@RequestParam(defaultValue = "10") int limit,
	      @RequestParam(defaultValue = "1") int offset,String name,@RequestParam Map<String, Object> params) {
			Query query= new Query(params);
			LayUiTableResultResponse LayUiTableResultResponse=   weixinMenuGroupsService.selectByQuery(query);
			return LayUiTableResultResponse;
	}
	 
	 /** 新增   */
		@RequestMapping(value = "/api/WeixinMenuGroups",method=RequestMethod.POST)
		public RestAPIResult2 create(WeixinMenuGroups weixinMenuGroups)  {
			 RestAPIResult2 restAPIResult = new RestAPIResult2();
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("操作成功");
				try {
					Integer createBy = getLoginId();
					weixinMenuGroups.setCreateBy(createBy);
					weixinMenuGroups.setCreateByUname(getUserName());
					weixinMenuGroups.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					weixinMenuGroupsService.insertSelective(weixinMenuGroups);
					
				}catch(Exception e) {
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg("失败成功:"+e.getMessage());
				}
				
				return restAPIResult;
	}
	 
	 /** 保存更新  */
		@RequestMapping(value="/api/WeixinMenuGroups/{id}",method=RequestMethod.PUT)
		public RestAPIResult2 update(@PathVariable("id") Integer id ,WeixinMenuGroups weixinMenuGroups)  {
			 RestAPIResult2 restAPIResult = new RestAPIResult2();
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("操作成功");
				try {
					
					
					Integer createBy = getLoginId();
					weixinMenuGroups.setUpdateBy(createBy);
					weixinMenuGroups.setUpdateByUname(getUserName());
					weixinMenuGroups.setUpdateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					weixinMenuGroupsService.updateByPrimaryKeySelective(weixinMenuGroups);
					
				}catch(Exception e) {
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg("失败成功:"+e.getMessage());
				}
				
				return restAPIResult;
	}
		
	/** 显示 */
	@RequestMapping(value="/api/WeixinMenuGroups/{id}", method = RequestMethod.GET)
	public WeixinMenuGroups show(@PathVariable("id") Integer id) throws Exception {
		WeixinMenuGroups weixinMenuGroups =weixinMenuGroupsService.selectByPrimaryKey(id);
		if(weixinMenuGroups== null) {
			weixinMenuGroups = new WeixinMenuGroups();
		}
		return weixinMenuGroups;
	}
		
	/** 逻辑删除 */
	@RequestMapping(value="/api/WeixinMenuGroups/{id}",method=RequestMethod.DELETE)
	public RestAPIResult2 delete(@PathVariable("id") Integer id) {
		 RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("操作成功");
		 WeixinMenuGroups weixinMenuGroups = weixinMenuGroupsService.selectByPrimaryKey(id);
		 weixinMenuGroups.setEnableFlag("0");//失效
		 weixinMenuGroupsService.updateByPrimaryKey(weixinMenuGroups);
			
		return restAPIResult;
	}

	/** 显示 */
	@RequestMapping(value="/api/WeixinMenuGroups/showInfo/{id}", method = RequestMethod.GET)
	public  Map<String,Object> showInfo(@PathVariable("id") Integer id) throws Exception {
		Map<String,Object> retMap =new HashMap<String,Object>();
		WeixinMenuGroups weixinMenuGroups =weixinMenuGroupsService.selectByPrimaryKey(id);
		if(weixinMenuGroups== null) {
			weixinMenuGroups = new WeixinMenuGroups();
		}
		
		retMap.put("weixinMenuGroups", weixinMenuGroups);
		
		return retMap;
	}
	
}

