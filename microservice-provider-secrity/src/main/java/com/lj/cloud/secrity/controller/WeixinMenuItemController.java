package com.lj.cloud.secrity.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lj.cloud.secrity.service.WeixinMenuItemService;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.weixin.WeixinMenuItem;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
import com.weixindev.micro.serv.common.util.DateUtil;

/**
 * 管理
 */
@RestController()
public class WeixinMenuItemController extends BaseController {
	@Autowired
	private WeixinMenuItemService weixinMenuItemService;

	@RequestMapping(value = "/api/WeixinMenuItem", method = RequestMethod.GET)
	public LayUiTableResultResponse page(@RequestParam(defaultValue = "10") int limit,
			@RequestParam(defaultValue = "1") int offset, String name, @RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		LayUiTableResultResponse LayUiTableResultResponse = weixinMenuItemService.selectByQuery(query);
		return LayUiTableResultResponse;
	}

	/** 新增 */
	@RequestMapping(value = "/api/WeixinMenuItem", method = RequestMethod.POST)
	public RestAPIResult2 create(WeixinMenuItem weixinMenuItem) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("操作成功");
		try {
			Integer createBy = getLoginId();
			weixinMenuItem.setCreateBy(createBy);
			weixinMenuItem.setCreateByUname(getUserName());
			weixinMenuItem.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
			weixinMenuItemService.insertSelective(weixinMenuItem);

		} catch (Exception e) {
			restAPIResult.setRespCode(0);
			restAPIResult.setRespMsg("失败成功:" + e.getMessage());
		}

		return restAPIResult;
	}

	/** 保存更新 */
	@RequestMapping(value = "/api/WeixinMenuItem/{id}", method = RequestMethod.PUT)
	public RestAPIResult2 update(@PathVariable("id") Integer id, WeixinMenuItem weixinMenuItem) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("操作成功");
		try {

			Integer createBy = getLoginId();
			weixinMenuItem.setUpdateBy(createBy);
			weixinMenuItem.setUpdateByUname(getUserName());
			weixinMenuItem.setUpdateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
			weixinMenuItemService.updateByPrimaryKeySelective(weixinMenuItem);

		} catch (Exception e) {
			restAPIResult.setRespCode(0);
			restAPIResult.setRespMsg("失败成功:" + e.getMessage());
		}

		return restAPIResult;
	}

	/** 显示 */
	@RequestMapping(value = "/api/WeixinMenuItem/{id}", method = RequestMethod.GET)
	public WeixinMenuItem show(@PathVariable("id") Integer id) throws Exception {
		WeixinMenuItem weixinMenuItem = weixinMenuItemService.selectByPrimaryKey(id);
		if (weixinMenuItem == null) {
			weixinMenuItem = new WeixinMenuItem();
		}
		return weixinMenuItem;
	}

	/** 逻辑删除 */
	@RequestMapping(value = "/api/WeixinMenuItem/{id}", method = RequestMethod.DELETE)
	public RestAPIResult2 delete(@PathVariable("id") Integer id) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("操作成功");
		Integer weixinMenuItem = weixinMenuItemService.deleteByPrimaryKey(id);
		
		return restAPIResult;
	}

	/** 显示 */
	@RequestMapping(value = "/api/WeixinMenuItem/showInfo/{id}", method = RequestMethod.GET)
	public Map<String, Object> showInfo(@PathVariable("id") Integer id) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();
		WeixinMenuItem weixinMenuItem = weixinMenuItemService.selectByPrimaryKey(id);
		if (weixinMenuItem == null) {
			weixinMenuItem = new WeixinMenuItem();
		}

		retMap.put("weixinMenuItem", weixinMenuItem);

		return retMap;
	}

}
