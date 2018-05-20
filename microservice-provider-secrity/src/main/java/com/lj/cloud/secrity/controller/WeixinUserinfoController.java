package com.lj.cloud.secrity.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lj.cloud.secrity.service.WeixinUserinfoService;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.weixin.WeixinUserinfo;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
import com.weixindev.micro.serv.common.util.DateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "微信用户服务", tags = "微信用户服务接口")
@RestController()
public class WeixinUserinfoController extends BaseController {
	@Autowired
	private WeixinUserinfoService weixinUserinfoService;

	@ApiOperation(value = "分页")
	@RequestMapping(value = "/api/WeixinUserinfo", method = RequestMethod.GET)
	public LayUiTableResultResponse page(@RequestParam(defaultValue = "10") int limit,
			@RequestParam(defaultValue = "1") int offset, String name, @RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		LayUiTableResultResponse LayUiTableResultResponse = weixinUserinfoService.selectByQuery(query);
		return LayUiTableResultResponse;
	}

	@ApiOperation(value = "回调")
	@GetMapping(value = "/api/WeixinUserinfoInsert/{aaa}")
	public RestAPIResult2 createInsert(@PathVariable("aaa") String aaa) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg(aaa);
		return restAPIResult;
	}

	@ApiOperation(value = "更新")
	@RequestMapping(value = "/api/WeixinUserinfo/{authorizerAppid}", method = RequestMethod.PUT)
	public RestAPIResult2 update(@PathVariable("authorizerAppid") String authorizerAppid, WeixinUserinfo weixinUserinfo) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("操作成功");
		try {
			Integer createBy = getLoginId();
			weixinUserinfo.setUpdateBy(createBy);
			weixinUserinfo.setUpdateByUname(getUserName());
			weixinUserinfo.setUpdateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
			weixinUserinfo.setAuthorizerAppid(authorizerAppid);
			weixinUserinfoService.updateByAppIdSelective(weixinUserinfo);
		} catch (Exception e) {
			restAPIResult.setRespCode(0);
			restAPIResult.setRespMsg("失败成功:" + e.getMessage());
		}

		return restAPIResult;
	}

	@ApiOperation(value = "显示")
	@RequestMapping(value = "/api/WeixinUserinfo/{authorizerAppid}", method = RequestMethod.GET)
	public HashMap<String, Object> show(@PathVariable("authorizerAppid") String authorizerAppid) throws Exception {
		return weixinUserinfoService.selectByAppId(authorizerAppid);
	}
	
	@ApiOperation(value = "显示二维码")
	@RequestMapping(value = "/api/WeixinUserinfoQrcode_url/{authorizerAppid}", method = RequestMethod.GET)
	public void showQrcode_url(@PathVariable("authorizerAppid") String authorizerAppid,HttpServletResponse response) throws Exception {
		HashMap<String, Object> weixinusermap = weixinUserinfoService.selectByAppId(authorizerAppid);
		FileInputStream fis = null;  
	    File file = new File(weixinusermap.get("qrcode_url").toString());  
	    fis = new FileInputStream(file);  
	    response.setContentType("image/jpg"); //设置返回的文件类型     
	    response.setHeader("Access-Control-Allow-Origin", "*");//设置该图片允许跨域访问  
	    IOUtils.copy(fis, response.getOutputStream());   
	}

	/** 逻辑删除 */
	@ApiOperation(value = "物理删除")
	@RequestMapping(value = "/api/WeixinUserinfo/{id}", method = RequestMethod.DELETE)
	public RestAPIResult2 delete(@PathVariable("id") Integer id) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("操作成功");
		Integer deleteRes = weixinUserinfoService.deleteByPrimaryKey(id);

		return restAPIResult;
	}

	@ApiOperation(value = "显示")
	@RequestMapping(value = "/api/WeixinUserinfo/showInfo/{id}", method = RequestMethod.GET)
	public Map<String, Object> showInfo(@PathVariable("id") Integer id) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();
		WeixinUserinfo weixinUserinfo = weixinUserinfoService.selectByPrimaryKey(id);
		if (weixinUserinfo == null) {
			weixinUserinfo = new WeixinUserinfo();
		}

		retMap.put("weixinUserinfo", weixinUserinfo);

		return retMap;
	}
	
	/** 批量移动用户  
	 *  Integer weixinGroupsId
	 * String movieUseIds*/
	@ApiOperation(value = "批量移动用户")
	@RequestMapping(value = "/api/WeixinUserinfo/batchMove", method = RequestMethod.POST)
	public RestAPIResult2 batchMove(String movieUseIds,Integer weixinGroupsId) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("操作成功");
		String weixininfoid[]=movieUseIds.split(","); 
		Integer updateCount = 0;
        for (int i = 0; i < weixininfoid.length; i++) {  
    		 weixinUserinfoService.WeixinGroupsBatchMove(weixininfoid[i],weixinGroupsId);
    		 updateCount+=1;
        }
		restAPIResult.setDataCode(String.valueOf(updateCount));

		return restAPIResult;
	}
	
	/** 批量删除用户  
	 *  Integer weixinGroupsId
	 * String movieUseIds*/
	@ApiOperation(value = "批量删除用户")
	@RequestMapping(value = "/api/WeixinUserinfo/batchDelete", method = RequestMethod.POST)
	public RestAPIResult2 batchDelete(String movieUseIds) {
		RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("操作成功");
		String weixininfoid[]=movieUseIds.split(",");
		System.out.println(weixininfoid);
		Integer updateCount = 0;
        for (int i = 0; i < weixininfoid.length; i++) {
    		 weixinUserinfoService.deleteByPrimaryKey(Integer.parseInt(weixininfoid[i]));
    		 updateCount+=1;
        }
		restAPIResult.setDataCode(String.valueOf(updateCount));

		return restAPIResult;
	}

}
