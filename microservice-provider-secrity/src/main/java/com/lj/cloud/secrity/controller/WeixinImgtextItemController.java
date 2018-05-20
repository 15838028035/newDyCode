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

import com.lj.cloud.secrity.service.WeixinImgtextItemService;
import com.lj.cloud.secrity.service.WeixinPushLogService;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.weixin.WeixinImgtextItem;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
import com.weixindev.micro.serv.common.util.DateUtil;
import com.weixindev.micro.serv.common.util.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "图文列表项服务", tags = "图文列表项服务接口")
@RestController()
public class WeixinImgtextItemController extends BaseController{
	@Autowired
	private WeixinImgtextItemService weixinImgtextItemService;
	@Autowired
	private WeixinPushLogService WeixinPushLogService;
	
	@ApiOperation(value = "列表")
	 @RequestMapping(value = "/api/WeixinImgtextItem", method = RequestMethod.GET)
	  public LayUiTableResultResponse page(@RequestParam(defaultValue = "10") int limit,
	      @RequestParam(defaultValue = "1") int offset ,@RequestParam Map<String, Object> params) {
			Query query= new Query(params);
			LayUiTableResultResponse LayUiTableResultResponse=   weixinImgtextItemService.selectByQuery2(query);
			return LayUiTableResultResponse;
	}
	 
	@ApiOperation(value = "新增")
		@RequestMapping(value = "/api/WeixinImgtextItem",method=RequestMethod.POST)
		public RestAPIResult2 create(@ModelAttribute WeixinImgtextItem weixinImgtextItem)  {
			 RestAPIResult2 restAPIResult = new RestAPIResult2();
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("成功");
				try {
					String articleContent = StringUtil.trimBlank(weixinImgtextItem.getArticleContent());
					
					if(articleContent.startsWith(",")||articleContent.startsWith("，")){
						articleContent=articleContent.substring(1, articleContent.length()-1);
						weixinImgtextItem.setArticleContent(articleContent);
					}

					List<WeixinImgtextItem> WeixinImgtextItemList =weixinImgtextItemService.selectByTemplateimdId(weixinImgtextItem.getImgTextId());
					if(WeixinImgtextItemList!=null && WeixinImgtextItemList.size()>8){
						restAPIResult.setRespCode(0);
						restAPIResult.setRespMsg("最多只能添加8个图文消息");
						return restAPIResult;
					}
					
					Integer createBy = getLoginId();
					weixinImgtextItem.setCreateBy(createBy);
					weixinImgtextItem.setCreateByUname(getUserName());
					weixinImgtextItem.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					
					Integer  createObjId =  weixinImgtextItemService.insert(weixinImgtextItem);
					
					Integer weixinImgtextItemId = weixinImgtextItem.getId();
					weixinImgtextItem.setSortNo(weixinImgtextItemId);//默认序号
					weixinImgtextItemService.updateByPrimaryKeySelective(weixinImgtextItem);
					
				}catch(Exception e) {
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg("失败"+e.getMessage());
				}
				
				return restAPIResult;
	}
	 
	@ApiOperation(value = "更新")
		@RequestMapping(value="/api/WeixinImgtextItem/{id}",method=RequestMethod.PUT)
		public RestAPIResult2 update(@PathVariable("id") Integer id ,@ModelAttribute WeixinImgtextItem weixinImgtextItem)  {
			 RestAPIResult2 restAPIResult = new RestAPIResult2();
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("成功");
				try {
					
					Integer createBy = getLoginId();
					weixinImgtextItem.setUpdateBy(createBy);
					weixinImgtextItem.setUpdateByUname(getUserName());
					weixinImgtextItem.setUpdateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					weixinImgtextItemService.updateByPrimaryKeySelective(weixinImgtextItem);
					
				}catch(Exception e) {
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg("失败:"+e.getMessage());
				}
				
				return restAPIResult;
	}
	
	@ApiOperation(value = "创建或者更新 ")
	@RequestMapping(value="/api/WeixinImgtextItem/createOrUpdate/{id}",method=RequestMethod.POST)
	public RestAPIResult2 createOrUpdate(@PathVariable("id") Integer id ,@ModelAttribute WeixinImgtextItem weixinImgtextItem)  {
		 RestAPIResult2 restAPIResult = new RestAPIResult2();
			restAPIResult.setRespCode(1);
			restAPIResult.setRespMsg("成功");
			try {
				
				String articleContent = StringUtil.trimBlank(weixinImgtextItem.getArticleContent());
				
				if(articleContent.startsWith(",")||articleContent.startsWith("，")){
					articleContent=articleContent.substring(1, articleContent.length()-1);
					weixinImgtextItem.setArticleContent(articleContent);
				}
				
				List<WeixinImgtextItem> WeixinImgtextItemList =weixinImgtextItemService.selectByTemplateimdId(weixinImgtextItem.getImgTextId());
				if(WeixinImgtextItemList!=null && WeixinImgtextItemList.size()>8){
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg("最多只能添加8个图文消息");
					return restAPIResult;
				}
				
				WeixinImgtextItem weixinImgtextItemSelect =weixinImgtextItemService.selectByPrimaryKey(id);
				if(weixinImgtextItemSelect== null) {
					Integer createBy = getLoginId();
					weixinImgtextItem.setCreateBy(createBy);
					weixinImgtextItem.setCreateByUname(getUserName());
					weixinImgtextItem.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					
					Integer  createObjId =  weixinImgtextItemService.insert(weixinImgtextItem);
					
					Integer weixinImgtextItemId = weixinImgtextItem.getId();
					weixinImgtextItem.setSortNo(weixinImgtextItemId);//默认序号
					weixinImgtextItemService.updateByPrimaryKeySelective(weixinImgtextItem);
					
					restAPIResult.setDataCode(String.valueOf(weixinImgtextItemId));
					
					restAPIResult.setRespMsg("创建成功");
				}else {
				
					Integer createBy = getLoginId();
					weixinImgtextItem.setUpdateBy(createBy);
					weixinImgtextItem.setUpdateByUname(getUserName());
					weixinImgtextItem.setUpdateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					weixinImgtextItemService.updateByPrimaryKeySelective(weixinImgtextItem);
					
					restAPIResult.setRespMsg("更新成功");
					restAPIResult.setDataCode(String.valueOf(weixinImgtextItem.getId()));
				}
				
			}catch(Exception e) {
				restAPIResult.setRespCode(0);
				restAPIResult.setRespMsg("失败:"+e.getMessage());
			}
			
			return restAPIResult;
}
	
	@ApiOperation(value = "查看")
	@RequestMapping(value="/api/WeixinImgtextItem/{id}", method = RequestMethod.GET)
	public WeixinImgtextItem show(@PathVariable("id") Integer id) throws Exception {
		WeixinImgtextItem weixinImgtextItem =weixinImgtextItemService.selectByPrimaryKey(id);
		if(weixinImgtextItem== null) {
			weixinImgtextItem = new WeixinImgtextItem();
		}
		return weixinImgtextItem;
	}
		
	@ApiOperation(value = "物理删除")
	@RequestMapping(value="/api/WeixinImgtextItem/{id}",method=RequestMethod.DELETE)
	public RestAPIResult2 delete(@PathVariable("id") Integer id) {
		 RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("成功");
		 weixinImgtextItemService.deleteByPrimaryKey(id);
			
		return restAPIResult;
	}
	
	@ApiOperation(value = "物理删除,1,2,3形式")
	@RequestMapping(value="/api/WeixinImgtextItem/batchDelete",method=RequestMethod.DELETE)
	public RestAPIResult2 batchDelete(String id) {
		 RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("成功");
		Integer deleteCount =  weixinImgtextItemService.batchDelete(id);
		restAPIResult.setDataCode(String.valueOf(deleteCount));
			
		return restAPIResult;
	}
	
	@ApiOperation(value = "根据 素材ID物理删除,1形式")
	@RequestMapping(value="/api/WeixinImgtextItem/deleteByTmpId",method=RequestMethod.DELETE)
	public RestAPIResult2 deleteByTmpId(String tempId) {
		 RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("成功");
		Integer deleteCount =  weixinImgtextItemService.deleteByTmpId(tempId);
		restAPIResult.setDataCode(String.valueOf(deleteCount));
			
		return restAPIResult;
	}

	@ApiOperation(value = "显示")
	@RequestMapping(value="/api/WeixinImgtextItem/showInfo/{id}", method = RequestMethod.GET)
	public  Map<String,Object> showInfo(@PathVariable("id") Integer id) throws Exception {
		Map<String,Object> retMap =new HashMap<String,Object>();
		WeixinImgtextItem weixinImgtextItem =weixinImgtextItemService.selectByPrimaryKey(id);
		if(weixinImgtextItem== null) {
			weixinImgtextItem = new WeixinImgtextItem();
		}
		
		retMap.put("weixinImgtextItem", weixinImgtextItem);
		
		return retMap;
	}
	
	@ApiOperation(value = "显示列表")
	@RequestMapping(value="/api/WeixinImgtextItem/showInfoList", method = RequestMethod.GET)
	public  Map<String,Object> showInfoList(@RequestParam Map<String, Object> params) throws Exception {
		Map<String,Object> retMap =new HashMap<String,Object>();
		Query query= new Query(params);
	    List list = weixinImgtextItemService.selectByExample(query);
		
	    retMap.put("list", list);
		return retMap;
	}
	
	@ApiOperation(value = "物理移动")
	@RequestMapping(value="/api/WeixinImgtextItem/moveFromAToB",method=RequestMethod.POST)
	public RestAPIResult2 moveFromAToB(@RequestParam Integer a, @RequestParam Integer b) {
		 RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("成功");
		  weixinImgtextItemService.moveFromAToB(a,b);
			
		return restAPIResult;
	}
}

