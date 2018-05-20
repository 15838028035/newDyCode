package com.lj.cloud.secrity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lj.cloud.secrity.service.WeixinAudioService;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.weixin.WeixinAudio;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
import com.weixindev.micro.serv.common.util.DateUtil;
import com.weixindev.micro.serv.common.util.FileUtil;
import com.weixindev.micro.serv.common.util.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 管理
 */
@Api(value = "音频服务", tags = "音频服务接口")
@RestController()
public class WeixinAudioController extends BaseController {
	@Autowired
	private WeixinAudioService weixinAudioService;

	@Value("${appURL}")
	private String appURL[];// 网站前台url

	@Value("${file_location}")
	private String file_location;// 文件存储路径

	@ApiOperation(value = "分页")
	@RequestMapping(value = "/api/WeixinAudio", method = RequestMethod.GET)
	public LayUiTableResultResponse page(@RequestParam(defaultValue = "10") int limit,
			@RequestParam(defaultValue = "1") int offset, String name, @RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		LayUiTableResultResponse LayUiTableResultResponse = weixinAudioService.selectByQuery(query);
		return LayUiTableResultResponse;
	}

	/** 新增 */
	@ApiOperation(value = "新增")
	@RequestMapping(value = "/api/WeixinAudio", method = RequestMethod.POST)
	public RestAPIResult2 create(WeixinAudio weixinAudio) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("操作成功");
		try {
			Integer createBy = getLoginId();
			weixinAudio.setCreateBy(createBy);
			weixinAudio.setCreateByUname(getUserName());
			weixinAudio.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
			weixinAudioService.insertSelective(weixinAudio);

		} catch (Exception e) {
			restAPIResult.setRespCode(0);
			restAPIResult.setRespMsg("失败成功:" + e.getMessage());
		}

		return restAPIResult;
	}

	/** 保存更新 */
	@ApiOperation(value = "修改")
	@RequestMapping(value = "/api/WeixinAudio/{id}", method = RequestMethod.PUT)
	public RestAPIResult2 update(@PathVariable("id") Integer id, WeixinAudio weixinAudio) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("操作成功");
		try {

			Integer createBy = getLoginId();
			weixinAudio.setUpdateBy(createBy);
			weixinAudio.setUpdateByUname(getUserName());
			weixinAudio.setUpdateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
			weixinAudioService.updateByPrimaryKeySelective(weixinAudio);

		} catch (Exception e) {
			restAPIResult.setRespCode(0);
			restAPIResult.setRespMsg("失败成功:" + e.getMessage());
		}

		return restAPIResult;
	}

	/** 显示 */
	@ApiOperation(value = "修改")
	@RequestMapping(value = "/api/WeixinAudio/{id}", method = RequestMethod.GET)
	public WeixinAudio show(@PathVariable("id") Integer id) throws Exception {
		WeixinAudio weixinAudio = weixinAudioService.selectByPrimaryKey(id);
		if (weixinAudio == null) {
			weixinAudio = new WeixinAudio();
		}
		return weixinAudio;
	}

	/** 逻辑删除 */
	@ApiOperation(value = "删除")
	@RequestMapping(value = "/api/WeixinAudio/{id}", method = RequestMethod.DELETE)
	public RestAPIResult2 delete(@PathVariable("id") Integer id) {
		RestAPIResult2 restAPIResult = batchDelete("" + id);
		return restAPIResult;
	}

	/** 显示 */
	@ApiOperation(value = "显示")
	@RequestMapping(value = "/api/WeixinAudio/showInfo/{id}", method = RequestMethod.GET)
	public Map<String, Object> showInfo(@PathVariable("id") Integer id) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();
		WeixinAudio weixinAudio = weixinAudioService.selectByPrimaryKey(id);
		if (weixinAudio == null) {
			weixinAudio = new WeixinAudio();
		}

		retMap.put("weixinAudio", weixinAudio);

		return retMap;
	}

	@ApiOperation(value = "批量删除")
	@RequestMapping(value = "/api/WeixinAudio/batchDelete", method = RequestMethod.POST)
	public RestAPIResult2 batchDelete(@RequestParam String ids) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("操作成功");

		List<String> idsList = StringUtil.splitStringToStringList(ids);
		for (String str : idsList) {
			Integer id = Integer.parseInt(str);
			WeixinAudio weixinAudio = weixinAudioService.selectByPrimaryKey(id);
			if (weixinAudio == null) {
				continue;
			}
			String headImg = weixinAudio.getHeadImg();// 获得图片

			if (StringUtils.isNotBlank(headImg)) {
				String filePath = StringUtil.replaceStr(headImg, appURL, file_location);
				FileUtil.delete(filePath);
			}

			weixinAudioService.deleteByPrimaryKey(id);
		}

		return restAPIResult;
	}

}
