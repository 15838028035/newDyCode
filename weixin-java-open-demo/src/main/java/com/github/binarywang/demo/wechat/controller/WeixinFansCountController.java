package com.github.binarywang.demo.wechat.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lj.cloud.secrity.service.WeixinFansCountService;
import com.weixindev.micro.serv.common.bean.report.WeixinFansCount;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *管理
 */
@Api(value = "服务", tags = "服务接口")
@RestController()
public class WeixinFansCountController {
	@Autowired
	private WeixinFansCountService weixinFansCountService;
	
	@ApiOperation(value = "列表")
	 @RequestMapping(value = "/api/WeixinFansCount", method = RequestMethod.GET)
	  public LayUiTableResultResponse page(@RequestParam(defaultValue = "10") int limit,
	      @RequestParam(defaultValue = "1") int offset,String name,@RequestParam Map<String, Object> params) {
			Query query= new Query(params);
			LayUiTableResultResponse LayUiTableResultResponse=   weixinFansCountService.selectByQuery(query);
			return LayUiTableResultResponse;
	}
	/** 显示 */
	@ApiOperation(value = "查看")
	@RequestMapping(value="/api/WeixinFansCount/{id}", method = RequestMethod.GET)
	public WeixinFansCount show(@PathVariable("id") Integer id) throws Exception {
		WeixinFansCount weixinFansCount =weixinFansCountService.selectByPrimaryKey(id);
		if(weixinFansCount== null) {
			weixinFansCount = new WeixinFansCount();
		}
		return weixinFansCount;
	}
	
}

