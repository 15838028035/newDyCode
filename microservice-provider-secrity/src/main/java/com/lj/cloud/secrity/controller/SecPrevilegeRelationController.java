package com.lj.cloud.secrity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lj.cloud.secrity.service.SecPrevilegeRelationService;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.secrity.SecPrevilegeRelation;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
import com.weixindev.micro.serv.common.util.DateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *管理
 */
@Api(value = "服务", tags = "服务接口")
@RestController()
public class SecPrevilegeRelationController extends BaseController{
	@Autowired
	private SecPrevilegeRelationService secPrevilegeRelationService;
	
	@ApiOperation(value = "列表")
	 @RequestMapping(value = "/api/SecPrevilegeRelation", method = RequestMethod.GET)
	  public LayUiTableResultResponse page(@RequestParam(defaultValue = "10") int limit,
	      @RequestParam(defaultValue = "1") int offset,String name,@RequestParam Map<String, Object> params) {
			Query query= new Query(params);
			query.setLimit(1000);
			LayUiTableResultResponse LayUiTableResultResponse=   secPrevilegeRelationService.selectByQuery(query);
			return LayUiTableResultResponse;
	}
	 
	 /** 新增   */
		@ApiOperation(value = "新增权限")
		@RequestMapping(value = "/api/addtAuthorityAssignment",method=RequestMethod.POST)
		public RestAPIResult2 create(@RequestParam Integer groupId,String roleIds)  {
			 RestAPIResult2 restAPIResult =null;
			 Map<String, Object> params=new HashMap<String,Object>();
			 params.put("secAgId", groupId);
			 Query query=new Query(params);
			 query.setLimit(1000);
			 List<SecPrevilegeRelation> list = secPrevilegeRelationService.selectByExample(query);
			 if (list!=null&&list.size()>0) {
				for (SecPrevilegeRelation secPrevilegeRelation : list) {
					secPrevilegeRelationService.deleteByPrimaryKey(secPrevilegeRelation.getId());
				}
			}
				try {
					restAPIResult=secPrevilegeRelationService.insertSelective(groupId,roleIds);
				}catch(Exception e) {
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg("失败成功:"+e.getMessage());
				}
				return restAPIResult;
	}
	 
	 /** 保存更新  */
		@ApiOperation(value = "更新")
		@RequestMapping(value="/api/SecPrevilegeRelation/{id}",method=RequestMethod.PUT)
		public RestAPIResult2 update(@PathVariable("id") Integer id ,@ModelAttribute SecPrevilegeRelation secPrevilegeRelation)  {
			 RestAPIResult2 restAPIResult = new RestAPIResult2();
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("操作成功");
				try {
					
					
					Integer createBy = getLoginId();
					secPrevilegeRelation.setUpdateBy(createBy);
					secPrevilegeRelation.setUpdateByUname(getUserName());
					secPrevilegeRelation.setUpdateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					secPrevilegeRelationService.updateByPrimaryKeySelective(secPrevilegeRelation);
					
				}catch(Exception e) {
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg("失败成功:"+e.getMessage());
				}
				
				return restAPIResult;
	}
		
	/** 显示 */
	@ApiOperation(value = "查看")
	@RequestMapping(value="/api/SecPrevilegeRelation/{id}", method = RequestMethod.GET)
	public SecPrevilegeRelation show(@PathVariable("id") Integer id) throws Exception {
		SecPrevilegeRelation secPrevilegeRelation =secPrevilegeRelationService.selectByPrimaryKey(id);
		if(secPrevilegeRelation== null) {
			secPrevilegeRelation = new SecPrevilegeRelation();
		}
		return secPrevilegeRelation;
	}
		
	/** 逻辑删除 */
	@ApiOperation(value = "删除")
	@RequestMapping(value="/api/SecPrevilegeRelation/{id}",method=RequestMethod.DELETE)
	public RestAPIResult2 delete(@PathVariable("id") Integer id) {
		 RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("操作成功");
		 SecPrevilegeRelation secPrevilegeRelation = secPrevilegeRelationService.selectByPrimaryKey(id);
		 secPrevilegeRelation.setEnableFlag("0");//失效
		 secPrevilegeRelationService.updateByPrimaryKey(secPrevilegeRelation);
			
		return restAPIResult;
	}

	/** 显示 */
	@ApiOperation(value = "显示")
	@RequestMapping(value="/api/SecPrevilegeRelation/showInfo/{id}", method = RequestMethod.GET)
	public  Map<String,Object> showInfo(@PathVariable("id") Integer id) throws Exception {
		Map<String,Object> retMap =new HashMap<String,Object>();
		SecPrevilegeRelation secPrevilegeRelation =secPrevilegeRelationService.selectByPrimaryKey(id);
		if(secPrevilegeRelation== null) {
			secPrevilegeRelation = new SecPrevilegeRelation();
		}
		
		retMap.put("secPrevilegeRelation", secPrevilegeRelation);
		
		return retMap;
	}
	
}

