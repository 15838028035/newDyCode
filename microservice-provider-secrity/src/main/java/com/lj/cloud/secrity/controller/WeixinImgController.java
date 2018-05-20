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

import com.lj.cloud.secrity.service.WeixinImgService;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.weixin.WeixinImg;
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
@Api(value = "图片服务", tags = "图片服务接口")
@RestController()
public class WeixinImgController extends BaseController{
	@Autowired
	private WeixinImgService weixinImgService;
	
	@Value("${appURL}")
	private String appURL[];// 网站前台url

	@Value("${file_location}")
	private String file_location;// 文件存储路径
	
	
	@ApiOperation(value = "分页")
	 @RequestMapping(value = "/api/WeixinImg", method = RequestMethod.GET)
	  public LayUiTableResultResponse page(@RequestParam(defaultValue = "10") int limit,
	      @RequestParam(defaultValue = "1") int offset,String name,@RequestParam Map<String, Object> params) {
			Query query= new Query(params);
			LayUiTableResultResponse LayUiTableResultResponse=   weixinImgService.selectByQuery(query);
			System.out.println("-----------------");
			System.out.println(params);
			return LayUiTableResultResponse;
	}
	 
	 /** 新增   */
	@ApiOperation(value = "新增")
		@RequestMapping(value = "/api/WeixinImg",method=RequestMethod.POST)
		public RestAPIResult2 create(WeixinImg weixinImg)  {
			 RestAPIResult2 restAPIResult = new RestAPIResult2();
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("操作成功");
				try {
					Integer createBy = getLoginId();
					weixinImg.setCreateBy(createBy);
					weixinImg.setCreateByUname(getUserName());
					weixinImg.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					weixinImgService.insertSelective(weixinImg);
					
				}catch(Exception e) {
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg("失败成功:"+e.getMessage());
				}
				
				return restAPIResult;
	}
	 
	 /** 保存更新  */
	@ApiOperation(value = "更新")
		@RequestMapping(value="/api/WeixinImg/{id}",method=RequestMethod.PUT)
		public RestAPIResult2 update(@PathVariable("id") Integer id ,WeixinImg weixinImg)  {
			 RestAPIResult2 restAPIResult = new RestAPIResult2();
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("操作成功");
				try {
					
					
					Integer createBy = getLoginId();
					weixinImg.setUpdateBy(createBy);
					weixinImg.setUpdateByUname(getUserName());
					weixinImg.setUpdateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					weixinImgService.updateByPrimaryKeySelective(weixinImg);
					
				}catch(Exception e) {
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg("失败成功:"+e.getMessage());
				}
				
				return restAPIResult;
	}
		
	/** 显示 */
	@ApiOperation(value = "显示")
	@RequestMapping(value="/api/WeixinImg/{id}", method = RequestMethod.GET)
	public WeixinImg show(@PathVariable("id") Integer id) throws Exception {
		WeixinImg weixinImg =weixinImgService.selectByPrimaryKey(id);
		if(weixinImg== null) {
			weixinImg = new WeixinImg();
		}
		return weixinImg;
	}
		
	/** 物理删除 */
	@ApiOperation(value = "物理删除")
	@RequestMapping(value="/api/WeixinImg/{id}",method=RequestMethod.DELETE)
	public RestAPIResult2 delete(@PathVariable("id") Integer id) {
		 RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("操作成功");
		
		WeixinImg weixinImg =weixinImgService.selectByPrimaryKey(id);
		if(weixinImg== null) {
			restAPIResult.setRespCode(0);
			restAPIResult.setRespMsg("删除失败，记录不存在");
			return restAPIResult;
		}
		
		String headImg = weixinImg.getHeadImg();//获得图片
		
		if(StringUtils.isNotBlank(headImg)){
			 String filePath = StringUtil.replaceStr(headImg, appURL, file_location);
			FileUtil.delete(filePath);
		}
		
		 weixinImgService.deleteByPrimaryKey(id);
			
		return restAPIResult;
	}
	
	@ApiOperation(value = "批量物理删除")
	@RequestMapping(value="/api/batchDelete",method=RequestMethod.DELETE)
	public RestAPIResult2 batchDelete( String[] ids) {
		 RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("操作成功");
		
		for(String str:ids){
			Integer id = Integer.parseInt(str);
			WeixinImg weixinImg =weixinImgService.selectByPrimaryKey(id);
			if(weixinImg== null) {
				restAPIResult.setRespCode(0);
				restAPIResult.setRespMsg("删除失败，记录不存在");
				continue;
			}
			
			String headImg = weixinImg.getHeadImg();//获得图片
			
			if(StringUtils.isNotBlank(headImg)){
				 String filePath = StringUtil.replaceStr(headImg, appURL, file_location);
				FileUtil.delete(filePath);
			}
			
			 weixinImgService.deleteByPrimaryKey(id);
		}
			
		return restAPIResult;
	}

	/** 显示 */
	@ApiOperation(value = "显示")
	@RequestMapping(value="/api/WeixinImg/showInfo/{id}", method = RequestMethod.GET)
	public  Map<String,Object> showInfo(@PathVariable("id") Integer id) throws Exception {
		Map<String,Object> retMap =new HashMap<String,Object>();
		WeixinImg weixinImg =weixinImgService.selectByPrimaryKey(id);
		if(weixinImg== null) {
			weixinImg = new WeixinImg();
		}
		
		retMap.put("weixinImg", weixinImg);
		
		return retMap;
	}
	
}

