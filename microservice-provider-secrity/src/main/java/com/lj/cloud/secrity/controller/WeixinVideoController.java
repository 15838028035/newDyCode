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

import com.lj.cloud.secrity.service.WeixinVideoService;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.weixin.WeixinVideo;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
import com.weixindev.micro.serv.common.util.DateUtil;
import com.weixindev.micro.serv.common.util.FileUtil;
import com.weixindev.micro.serv.common.util.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *管理
 */
@Api(value = "视频服务", tags = "视频服务接口")
@RestController()
public class WeixinVideoController extends BaseController{
	@Autowired
	private WeixinVideoService weixinVideoService;
	@Value("${appURL}")
	private String appURL[];// 网站前台url

	@Value("${file_location}")
	private String file_location;// 文件存储路径
	
	@ApiOperation(value = "分页")
	 @RequestMapping(value = "/api/WeixinVideo", method = RequestMethod.GET)
	  public LayUiTableResultResponse page(@RequestParam(defaultValue = "10") int limit,
	      @RequestParam(defaultValue = "1") int offset,String name,@RequestParam Map<String, Object> params) {
			Query query= new Query(params);
			LayUiTableResultResponse LayUiTableResultResponse=   weixinVideoService.selectByQuery(query);
			return LayUiTableResultResponse;
	}
	 
	 /** 新增   */
	@ApiOperation(value = "新增")
		@RequestMapping(value = "/api/WeixinVideo",method=RequestMethod.POST)
		public RestAPIResult2 create(WeixinVideo weixinVideo)  {
			 RestAPIResult2 restAPIResult = new RestAPIResult2();
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("操作成功");
				try {
					Integer createBy = getLoginId();
					weixinVideo.setCreateBy(createBy);
					weixinVideo.setCreateByUname(getUserName());
					weixinVideo.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					weixinVideoService.insertSelective(weixinVideo);
					
				}catch(Exception e) {
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg("失败成功:"+e.getMessage());
				}
				
				return restAPIResult;
	}
	 
	 /** 保存更新  */
	@ApiOperation(value = "更新")
		@RequestMapping(value="/api/WeixinVideo/{id}",method=RequestMethod.PUT)
		public RestAPIResult2 update(@PathVariable("id") Integer id ,WeixinVideo weixinVideo)  {
			 RestAPIResult2 restAPIResult = new RestAPIResult2();
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("操作成功");
				try {
					
					
					Integer createBy = getLoginId();
					weixinVideo.setUpdateBy(createBy);
					weixinVideo.setUpdateByUname(getUserName());
					weixinVideo.setUpdateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					weixinVideoService.updateByPrimaryKeySelective(weixinVideo);
					
				}catch(Exception e) {
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg("失败成功:"+e.getMessage());
				}
				
				return restAPIResult;
	}
		
	/** 显示 */
	@ApiOperation(value = "显示")
	@RequestMapping(value="/api/WeixinVideo/{id}", method = RequestMethod.GET)
	public WeixinVideo show(@PathVariable("id") Integer id) throws Exception {
		WeixinVideo weixinVideo =weixinVideoService.selectByPrimaryKey(id);
		if(weixinVideo== null) {
			weixinVideo = new WeixinVideo();
		}
		return weixinVideo;
	}
		
	@ApiOperation(value = "物理删除")
	@RequestMapping(value="/api/WeixinVideo/{id}",method=RequestMethod.DELETE)
	public RestAPIResult2 delete(@PathVariable("id") Integer id) {
		 RestAPIResult2 restAPIResult = batchdelete(""+id);
			
		return restAPIResult;
	}

	/** 显示 */
	@ApiOperation(value = "显示")
	@RequestMapping(value="/api/WeixinVideo/showInfo/{id}", method = RequestMethod.GET)
	public  Map<String,Object> showInfo(@PathVariable("id") Integer id) throws Exception {
		Map<String,Object> retMap =new HashMap<String,Object>();
		WeixinVideo weixinVideo =weixinVideoService.selectByPrimaryKey(id);
		if(weixinVideo== null) {
			weixinVideo = new WeixinVideo();
		}
		
		retMap.put("weixinVideo", weixinVideo);
		
		return retMap;
	}
	@ApiOperation(value = "批量删除")
	@RequestMapping(value="/api/WeixinVideo/batchDelete",method=RequestMethod.POST)
	public RestAPIResult2 batchdelete(@RequestParam String  ids) {
		// RestAPIResult2 restAPIResult = deleteByIds(ids);
		// weixinVideoService.deleteFilesByName(fileNames);
		 RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("操作成功");
		
		List<String> idsList = StringUtil.splitStringToStringList(ids);
		for(String str: idsList) {
			Integer id = Integer.parseInt(str);
			WeixinVideo weixinVideo =weixinVideoService.selectByPrimaryKey(id);
			if(weixinVideo== null) {
				continue;
			}
			String headImg = weixinVideo.getHeadImg();//获得图片
			
			if(StringUtils.isNotBlank(headImg)){
				 String filePath = StringUtil.replaceStr(headImg, appURL, file_location);
				FileUtil.delete(filePath);
			}
			
			 weixinVideoService.deleteByPrimaryKey(id);
		}
		
		return restAPIResult;
	}
}

