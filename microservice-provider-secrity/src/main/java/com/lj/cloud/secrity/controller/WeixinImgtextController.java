package com.lj.cloud.secrity.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lj.cloud.secrity.service.WeixinImgtextService;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.weixin.WeixinImgtext;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
import com.weixindev.micro.serv.common.util.DateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *管理
 */
@Api(value = "图文服务", tags = "图文服务接口")
@RestController()
public class WeixinImgtextController extends BaseController{
	@Autowired
	private WeixinImgtextService weixinImgtextService;
	
	@ApiOperation(value = "分页")
	 @RequestMapping(value = "/api/WeixinImgtext", method = RequestMethod.GET)
	  public LayUiTableResultResponse page(@RequestParam(defaultValue = "10") int limit,
	      @RequestParam(defaultValue = "1") int offset,String name,@RequestParam Map<String, Object> params) {
			Query query= new Query(params);
			LayUiTableResultResponse LayUiTableResultResponse=   weixinImgtextService.selectByQuery(query);
			return LayUiTableResultResponse;
	}
	 
	 /** 新增   */
	@ApiOperation(value = "新增")
		@RequestMapping(value = "/api/WeixinImgtext",method=RequestMethod.POST)
		public RestAPIResult2 create(@ModelAttribute WeixinImgtext weixinImgtext)  {
			 RestAPIResult2 restAPIResult = new RestAPIResult2();
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("操作成功");
				try {
					Integer createBy = getLoginId();
					weixinImgtext.setCreateBy(createBy);
					weixinImgtext.setCreateByUname(getUserName());
					weixinImgtext.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					weixinImgtextService.insertSelective(weixinImgtext);
					
				}catch(Exception e) {
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg("失败成功:"+e.getMessage());
				}
				
				return restAPIResult;
	}
	 
	 /** 保存更新  */
	@ApiOperation(value = "修改")
		@RequestMapping(value="/api/WeixinImgtext/{id}",method=RequestMethod.PUT)
		public RestAPIResult2 update(@PathVariable("id") Integer id ,@ModelAttribute WeixinImgtext weixinImgtext)  {
			 RestAPIResult2 restAPIResult = new RestAPIResult2();
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("操作成功");
				try {
					
					
					Integer createBy = getLoginId();
					weixinImgtext.setUpdateBy(createBy);
					weixinImgtext.setUpdateByUname(getUserName());
					weixinImgtext.setUpdateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					weixinImgtextService.updateByPrimaryKeySelective(weixinImgtext);
					
				}catch(Exception e) {
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg("失败成功:"+e.getMessage());
				}
				
				return restAPIResult;
	}
	
	@ApiOperation(value = "创建或更新")
	@RequestMapping(value="/api/WeixinImgtext/createOrUpdate/{id}",method=RequestMethod.POST)
	public RestAPIResult2 createOrUpdate(@PathVariable("id") Integer id ,@ModelAttribute WeixinImgtext weixinImgtext)  {
		 RestAPIResult2 restAPIResult = new RestAPIResult2();
			restAPIResult.setRespCode(1);
			restAPIResult.setRespMsg("操作成功");
			try {
				WeixinImgtext weixinImgtextSelect =weixinImgtextService.selectByPrimaryKey(id);
				if(weixinImgtextSelect== null) {
					Integer createBy = getLoginId();
					weixinImgtext.setCreateBy(createBy);
					weixinImgtext.setCreateByUname(getUserName());
					weixinImgtext.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					weixinImgtextService.insert(weixinImgtext);
					
					Integer weixinImgtextItemId = weixinImgtext.getId();
					restAPIResult.setDataCode(String.valueOf(weixinImgtextItemId));
					restAPIResult.setRespMsg("创建成功");
				
				}else {
					Integer createBy = getLoginId();
					weixinImgtext.setUpdateBy(createBy);
					weixinImgtext.setUpdateByUname(getUserName());
					weixinImgtext.setUpdateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					weixinImgtextService.updateByPrimaryKeySelective(weixinImgtext);
					
					restAPIResult.setRespMsg("更新成功");
					restAPIResult.setDataCode(String.valueOf(weixinImgtext.getId()));
				}
				
			}catch(Exception e) {
				restAPIResult.setRespCode(0);
				restAPIResult.setRespMsg("失败成功:"+e.getMessage());
			}
			
			return restAPIResult;
}
	
		
	/** 显示 */
	@ApiOperation(value = "显示")
	@RequestMapping(value="/api/WeixinImgtext/{id}", method = RequestMethod.GET)
	public WeixinImgtext show(@PathVariable("id") Integer id) throws Exception {
		WeixinImgtext weixinImgtext =weixinImgtextService.selectByPrimaryKey(id);
		if(weixinImgtext== null) {
			weixinImgtext = new WeixinImgtext();
		}
		return weixinImgtext;
	}
		
	/** 逻辑删除 */
	@ApiOperation(value = "逻辑删除")
	@RequestMapping(value="/api/WeixinImgtext/{id}",method=RequestMethod.DELETE)
	public RestAPIResult2 delete(@PathVariable("id") Integer id) {
		 RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("操作成功");
		 weixinImgtextService.deleteByPrimaryKey(id);
			
		return restAPIResult;
	}

	/** 显示 */
	@ApiOperation(value = "分页")
	@RequestMapping(value="/api/WeixinImgtext/showInfo/{id}", method = RequestMethod.GET)
	public  Map<String,Object> showInfo(@PathVariable("id") Integer id) throws Exception {
		Map<String,Object> retMap =new HashMap<String,Object>();
		WeixinImgtext weixinImgtext =weixinImgtextService.selectByPrimaryKey(id);
		if(weixinImgtext== null) {
			weixinImgtext = new WeixinImgtext();
		}
		
		retMap.put("weixinImgtext", weixinImgtext);
		
		return retMap;
	}
	
}

