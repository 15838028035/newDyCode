package com.github.binarywang.demo.wechat.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

import com.lj.cloud.secrity.service.WeixinFansInfoService;
import com.lj.cloud.secrity.service.WeixinImgtextService;
import com.lj.cloud.secrity.service.WeixinPushLogService;
import com.lj.cloud.secrity.service.WeixinReportArticleService;
import com.lj.cloud.secrity.service.WeixinUserinfoService;
import com.weixindev.micro.serv.common.bean.EchartSeries;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.report.WeixinReportArticle;
import com.weixindev.micro.serv.common.bean.weixin.WeixinImgtext;
import com.weixindev.micro.serv.common.bean.weixin.WeixinPushLog;
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
public class WeixinReportArticleController{
	@Autowired
	private WeixinReportArticleService weixinReportArticleService;
	
	@Autowired
	private WeixinPushLogService weixinPushLogService;
	 
	@Autowired	
	private WeixinImgtextService weixinImgtextService;
	
	@Autowired
	private WeixinUserinfoService weixinUserinfoService;
	
	@Autowired
	private WeixinFansInfoService weixinFansInfoService;
	
	@ApiOperation(value = "列表")
	 @RequestMapping(value = "/api/WeixinReportArticle", method = RequestMethod.GET)
	  public LayUiTableResultResponse page(@RequestParam(defaultValue = "10") int limit,
	      @RequestParam(defaultValue = "1") int offset,String name,@RequestParam Map<String, Object> params) {
			Query query= new Query(params);
			LayUiTableResultResponse LayUiTableResultResponse=   weixinReportArticleService.selectByQuery(query);
			return LayUiTableResultResponse;
	}
	
	@ApiOperation(value = "列表")
	 @RequestMapping(value = "/api/WeixinReportArticle2", method = RequestMethod.GET)
	  public LayUiTableResultResponse page2(@RequestParam(defaultValue = "10") int limit,
	      @RequestParam(defaultValue = "1") int offset,String name,@RequestParam Map<String, Object> params) {
			Query query= new Query(params);
			LayUiTableResultResponse LayUiTableResultResponse=   weixinReportArticleService.selectByQuery2(query);
			return LayUiTableResultResponse;
	}
	 
	 /** 新增   */
		@ApiOperation(value = "新增")
		@RequestMapping(value = "/api/WeixinReportArticle",method=RequestMethod.POST)
		public RestAPIResult2 create(@ModelAttribute WeixinReportArticle weixinReportArticle)  {
			 RestAPIResult2 restAPIResult = new RestAPIResult2();
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("操作成功");
				try {
					Integer createBy = 1;
					weixinReportArticle.setCreateBy(createBy);
					weixinReportArticle.setCreateByUname("admin01");
					weixinReportArticle.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					weixinReportArticleService.insertSelective(weixinReportArticle);
					
				}catch(Exception e) {
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg("失败成功:"+e.getMessage());
				}
				
				return restAPIResult;
	}
	 
	 /** 保存更新  */
		@ApiOperation(value = "更新")
		@RequestMapping(value="/api/WeixinReportArticle/{id}",method=RequestMethod.PUT)
		public RestAPIResult2 update(@PathVariable("id") Integer id ,@ModelAttribute WeixinReportArticle weixinReportArticle)  {
			 RestAPIResult2 restAPIResult = new RestAPIResult2();
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("操作成功");
				try {
					
					
					Integer createBy =1;
					weixinReportArticle.setUpdateBy(createBy);
					weixinReportArticle.setUpdateByUname("admin01");
					weixinReportArticle.setUpdateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					weixinReportArticleService.updateByPrimaryKeySelective(weixinReportArticle);
					
				}catch(Exception e) {
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg("失败成功:"+e.getMessage());
				}
				
				return restAPIResult;
	}
		
	/** 显示 */
	@ApiOperation(value = "查看")
	@RequestMapping(value="/api/WeixinReportArticle/{id}", method = RequestMethod.GET)
	public WeixinReportArticle show(@PathVariable("id") Integer id) throws Exception {
		WeixinReportArticle weixinReportArticle =weixinReportArticleService.selectByPrimaryKey(id);
		if(weixinReportArticle== null) {
			weixinReportArticle = new WeixinReportArticle();
		}
		return weixinReportArticle;
	}
		
	/** 逻辑删除 */
	@ApiOperation(value = "删除")
	@RequestMapping(value="/api/WeixinReportArticle/{id}",method=RequestMethod.DELETE)
	public RestAPIResult2 delete(@PathVariable("id") Integer id) {
		 RestAPIResult2 restAPIResult = new RestAPIResult2();
		restAPIResult.setRespCode(1);
		restAPIResult.setRespMsg("操作成功");
		 WeixinReportArticle weixinReportArticle = weixinReportArticleService.selectByPrimaryKey(id);
		 weixinReportArticle.setEnableFlag("0");//失效
		 weixinReportArticleService.updateByPrimaryKey(weixinReportArticle);
			
		return restAPIResult;
	}

	/** 显示 */
	@ApiOperation(value = "显示")
	@RequestMapping(value="/api/WeixinReportArticle/showInfo/{id}", method = RequestMethod.GET)
	public  Map<String,Object> showInfo(@PathVariable("id") Integer id) throws Exception {
		Map<String,Object> retMap =new HashMap<String,Object>();
		WeixinReportArticle weixinReportArticle =weixinReportArticleService.selectByPrimaryKey(id);
		if(weixinReportArticle== null) {
			weixinReportArticle = new WeixinReportArticle();
		}
		
		retMap.put("weixinReportArticle", weixinReportArticle);
		
		return retMap;
	}
	
	/** 显示 */
	@ApiOperation(value = "显示")
	@RequestMapping(value="/api/WeixinReportArticle/showInfo2/{id}", method = RequestMethod.GET)
	public  Map<String,Object> showInfo2(@PathVariable("id") Integer id) throws Exception {
		Map<String,Object> retMap =new HashMap<String,Object>();
		WeixinReportArticle weixinReportArticle =weixinReportArticleService.selectByPrimaryKey(id);
		if(weixinReportArticle== null) {
			weixinReportArticle = new WeixinReportArticle();
		}
		
		Map<String,Object> filterMap = new HashMap<String,Object>();
		filterMap.put("msgId", weixinReportArticle.getMsgid());
		
		Query query = new Query(filterMap);
		
		 List<WeixinPushLog> weixinPushLogList = weixinPushLogService.selectByExample(query);
		 
		 WeixinPushLog weixinPushLog = new WeixinPushLog();
		 
		 if(weixinPushLogList!=null && weixinPushLogList.size()>0){
			 weixinPushLog = weixinPushLogList.get(0);
		 }
		 
		 WeixinImgtext weixinImgtext =  weixinImgtextService.selectByPrimaryKey(weixinPushLog.getImgTextId());
		 
		 if(weixinImgtext==null) {
			 weixinImgtext = new WeixinImgtext();
		 }
		 
		retMap.put("weixinReportArticle", weixinReportArticle);
		retMap.put("weixinPushLog", weixinPushLog);
		retMap.put("weixinImgtext", weixinImgtext);
		
		return retMap;
	}
	
	/** 显示 */
	@ApiOperation(value = "显示")
	@RequestMapping(value="/api/WeixinReportArticle/showInfo3", method = RequestMethod.GET)
	public  Map<String,Object> showInf3(String ids, String uids) throws Exception {
		Map<String,Object> retMap =new HashMap<String,Object>();
		
		Map<String,Object> filterMap = new HashMap<String,Object>();
		filterMap.put("ids", ids);
		filterMap.put("uids", uids);
		 Query query= new Query(filterMap);
	    List<Map<String, Object>> list1 = weixinReportArticleService.selectByPageExample3(query);//查询全部数据
	    
	    retMap.put("rate11", 0);
    	retMap.put("rate12", 0);
    	retMap.put("rate13", 0);
    	retMap.put("rate14", 0);
    	
    	retMap.put("rate21", 0);
     	retMap.put("rate22", 0);
     	retMap.put("rate23", 0);
     	retMap.put("rate24", 0);
     	
     	retMap.put("rate31", 0);
     	retMap.put("rate32", 0);
     	retMap.put("rate33", 0);
     	retMap.put("rate34", 0);
     	
     	retMap.put("rate41", 0);
     	retMap.put("rate42", 0);
     	retMap.put("rate43", 0);
     	retMap.put("rate44", 0);
	    
	    if(list1!=null&& list1.size()>0 && list1.get(0)!=null){
		    String int_page_read_count_str1 = list1.get(0).get("int_page_read_count")==null? "0":String.valueOf(list1.get(0).get("int_page_read_count"));
		    BigDecimal int_page_read_count1 = new BigDecimal(int_page_read_count_str1);
		    
		    String ori_page_read_count_str1 = list1.get(0).get("ori_page_read_count")==null? "0":String.valueOf(list1.get(0).get("ori_page_read_count"));
		    BigDecimal ori_page_read_count1 = new BigDecimal(ori_page_read_count_str1);
		    
		    String feed_share_from_feed_cnt_str1 = list1.get(0).get("feed_share_from_feed_cnt")==null? "0":String.valueOf(list1.get(0).get("feed_share_from_feed_cnt"));
		    BigDecimal feed_share_from_feed_cnt1 = new BigDecimal(feed_share_from_feed_cnt_str1);
		    
		    String add_to_fav_count_str1 = list1.get(0).get("add_to_fav_count")==null? "0":String.valueOf(list1.get(0).get("add_to_fav_count"));
		    BigDecimal add_to_fav_count1 = new BigDecimal(add_to_fav_count_str1);
		    
		    retMap.put("rate11", int_page_read_count1);
	    	retMap.put("rate12", ori_page_read_count1);
	    	retMap.put("rate13", feed_share_from_feed_cnt1);
	    	retMap.put("rate14", add_to_fav_count1);
	    }
	   
	    
	    //查询当天数据
	    //createDateBeginA
	    
		Map<String,Object> filterMap2 = new HashMap<String,Object>();
		filterMap2.put("createDateBeginA", DateUtil.getNowDate(DateUtil.DATE_FOMRAT_yyyy_MM_dd));
		filterMap2.put("ids", ids);
		filterMap2.put("uids", uids);
		Query query2= new Query(filterMap2);
	    List<Map<String, Object>> list2 = weixinReportArticleService.selectByPageExample3(query2);//查询今天数据
	    
		Map<String,Object> filterMap3 = new HashMap<String,Object>();
		filterMap3.put("createDateBeginA", DateUtil.rollDay(new Date(), -1));//前一天
		filterMap3.put("createDateEndA", DateUtil.getNowDate(DateUtil.DATE_FOMRAT_yyyy_MM_dd));
		filterMap3.put("ids", ids);
		filterMap3.put("uids", uids);
		Query query3= new Query(filterMap3);
	    List<Map<String, Object>> list3 = weixinReportArticleService.selectByPageExample3(query3);//查看昨天
	   
    	
	    if(list3==null || list3.size()==0 || list3.get(0)==null){
	    	retMap.put("rate21", 100);
	    	retMap.put("rate22", 100);
	    	retMap.put("rate23", 100);
	    	retMap.put("rate24", 100);
	    }
	    
	    if(list2.size()>0&& list2.get(0)!=null && list3.size()>0&& list3.get(0)!=null){
	    
		    String int_page_read_count_str2 = list2.get(0).get("int_page_read_count")==null? "0":String.valueOf(list2.get(0).get("int_page_read_count"));
		    BigDecimal int_page_read_count2 = new BigDecimal(int_page_read_count_str2);
		    
		    String ori_page_read_count_str2 = list2.get(0).get("ori_page_read_count")==null? "0":String.valueOf(list2.get(0).get("ori_page_read_count"));
		    BigDecimal ori_page_read_count2 = new BigDecimal(ori_page_read_count_str2);
		    
		    String feed_share_from_feed_cnt_str2 = list2.get(0).get("feed_share_from_feed_cnt")==null? "0":String.valueOf(list2.get(0).get("feed_share_from_feed_cnt"));
		    BigDecimal feed_share_from_feed_cnt2 = new BigDecimal(feed_share_from_feed_cnt_str2);
		    
		    String add_to_fav_count_str2 = list2.get(0).get("add_to_fav_count")==null? "0":String.valueOf(list2.get(0).get("add_to_fav_count"));
		    BigDecimal add_to_fav_count2 = new BigDecimal(add_to_fav_count_str2);
		    
		    String int_page_read_count_str3 = list3.get(0).get("int_page_read_count")==null? "0":String.valueOf(list3.get(0).get("int_page_read_count"));
		    BigDecimal int_page_read_count3 = new BigDecimal(int_page_read_count_str3);
		    
		    String ori_page_read_count_str3 = list3.get(0).get("ori_page_read_count")==null? "0":String.valueOf(list3.get(0).get("ori_page_read_count"));
		    BigDecimal ori_page_read_count3 = new BigDecimal(ori_page_read_count_str3);
		    
		    String feed_share_from_feed_cnt_str3 = list3.get(0).get("feed_share_from_feed_cnt")==null? "0":String.valueOf(list3.get(0).get("feed_share_from_feed_cnt"));
		    BigDecimal feed_share_from_feed_cnt3 = new BigDecimal(feed_share_from_feed_cnt_str3);
		    
		    String add_to_fav_count_str3 = list3.get(0).get("add_to_fav_count")==null? "0":String.valueOf(list3.get(0).get("add_to_fav_count"));
		    BigDecimal add_to_fav_count3 = new BigDecimal(add_to_fav_count_str3);
		    
		    if(int_page_read_count3.compareTo(new BigDecimal(0))>0){
		    	BigDecimal rate21 = ((int_page_read_count2.subtract(int_page_read_count3)).divide(int_page_read_count3,2,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
		    	retMap.put("rate21", rate21);
		    }
		    
		    if(ori_page_read_count3.compareTo(new BigDecimal(0))>0){
		    	BigDecimal rate22 = ((ori_page_read_count2.subtract(ori_page_read_count3)).divide(ori_page_read_count3,2,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
		    	retMap.put("rate22", rate22);
		    }
		    
		    if(feed_share_from_feed_cnt3.compareTo(new BigDecimal(0))>0){
		    	BigDecimal rate23 = ((feed_share_from_feed_cnt2.subtract(feed_share_from_feed_cnt3)).divide(feed_share_from_feed_cnt3,2,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
		    	retMap.put("rate3", rate23);
		    }
		    if(add_to_fav_count3.compareTo(new BigDecimal(0))>0){
		    	BigDecimal rate24 = ((add_to_fav_count2.subtract(add_to_fav_count3)).divide(add_to_fav_count3,2,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
		    	retMap.put("rate24", rate24);
		    }
		    
	    }
	    
	    //查询本周 
	    Map<String,Object> filterMap4 = new HashMap<String,Object>();
		filterMap4.put("createDateBeginA",DateUtil.formatDate(DateUtil.getBeginDayOfWeek(),DateUtil.DATE_FOMRAT_yyyy_MM_dd));
		filterMap4.put("ids", ids);
		filterMap4.put("uids", uids);
		Query query4= new Query(filterMap4);
	    List<Map<String, Object>> list4 = weixinReportArticleService.selectByPageExample3(query4);//
	    
	    //查询上一周
	    Date lastDate = DateUtil.rollDay(DateUtil.getBeginDayOfWeek(),-7);
	    
		Map<String,Object> filterMap5 = new HashMap<String,Object>();
		filterMap5.put("createDateBeginA", DateUtil.formatDate(lastDate,DateUtil.DATE_FOMRAT_yyyy_MM_dd));//
		filterMap5.put("createDateEndA", DateUtil.formatDate(DateUtil.getBeginDayOfWeek(),DateUtil.DATE_FOMRAT_yyyy_MM_dd));
		filterMap5.put("ids", ids);
		filterMap5.put("uids", uids);
		Query query5= new Query(filterMap5);
	    List<Map<String, Object>> list5 = weixinReportArticleService.selectByPageExample3(query5);
	    
	    if(list5==null || list5.size()==0 || list5.get(0)==null){
	    	retMap.put("rate31", 100);
	    	retMap.put("rate32", 100);
	    	retMap.put("rate33", 100);
	    	retMap.put("rate34", 100);
	    }
		
	    if(list4.size()>0 && list4.get(0)!=null&& list5.size()>0 && list5.get(0)!=null){
		    
		    String int_page_read_count_str4 = list4.get(0).get("int_page_read_count")==null? "0":String.valueOf(list4.get(0).get("int_page_read_count"));
		    BigDecimal int_page_read_count4 = new BigDecimal(int_page_read_count_str4);
		    
		    String ori_page_read_count_str4 = list4.get(0).get("ori_page_read_count")==null? "0":String.valueOf(list4.get(0).get("ori_page_read_count"));
		    BigDecimal ori_page_read_count4 = new BigDecimal(ori_page_read_count_str4);
		    
		    String feed_share_from_feed_cnt_str4 = list4.get(0).get("feed_share_from_feed_cnt")==null? "0":String.valueOf(list4.get(0).get("feed_share_from_feed_cnt"));
		    BigDecimal feed_share_from_feed_cnt4 = new BigDecimal(feed_share_from_feed_cnt_str4);
		    
		    String add_to_fav_count_str4 = list4.get(0).get("add_to_fav_count")==null? "0":String.valueOf(list4.get(0).get("add_to_fav_count"));
		    BigDecimal add_to_fav_count4 = new BigDecimal(add_to_fav_count_str4);
		    
		    String int_page_read_count_str5 = list5.get(0).get("int_page_read_count")==null? "0":String.valueOf(list5.get(0).get("int_page_read_count"));
		    BigDecimal int_page_read_count5 = new BigDecimal(int_page_read_count_str5);
		    
		    String ori_page_read_count_str5 = list5.get(0).get("ori_page_read_count")==null? "0":String.valueOf(list5.get(0).get("ori_page_read_count"));
		    BigDecimal ori_page_read_count5 = new BigDecimal(ori_page_read_count_str5);
		    
		    String feed_share_from_feed_cnt_str5 = list5.get(0).get("feed_share_from_feed_cnt")==null? "0":String.valueOf(list5.get(0).get("feed_share_from_feed_cnt"));
		    BigDecimal feed_share_from_feed_cnt5 = new BigDecimal(feed_share_from_feed_cnt_str5);
		    
		    String add_to_fav_count_str5 = list5.get(0).get("add_to_fav_count")==null? "0":String.valueOf(list5.get(0).get("add_to_fav_count"));
		    BigDecimal add_to_fav_count5 = new BigDecimal(add_to_fav_count_str5);
		    
		    if(int_page_read_count5.compareTo(new BigDecimal(0))>0){
		    	BigDecimal rate31 = ((int_page_read_count4.subtract(int_page_read_count5)).divide(int_page_read_count5,2,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
		    	retMap.put("rate31", rate31);
		    }
		    
		    if(ori_page_read_count5.compareTo(new BigDecimal(0))>0){
		    	BigDecimal rate32 = ((ori_page_read_count4.subtract(ori_page_read_count5)).divide(ori_page_read_count5,2,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
		    	retMap.put("rate32", rate32);
		    }
		    
		    if(feed_share_from_feed_cnt5.compareTo(new BigDecimal(0))>0){
		    	BigDecimal rate33 = ((feed_share_from_feed_cnt4.subtract(feed_share_from_feed_cnt5)).divide(feed_share_from_feed_cnt5,2,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
		    	retMap.put("rate33", rate33);
		    }
		    if(add_to_fav_count5.compareTo(new BigDecimal(0))>0){
		    	BigDecimal rate34 = ((add_to_fav_count4.subtract(add_to_fav_count5)).divide(add_to_fav_count5,2,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
		    	retMap.put("rate34", rate34);
		    }
		    
	    }
	    
	    
	    //查询本月
	    Map<String,Object> filterMap6 = new HashMap<String,Object>();
	    filterMap6.put("createDateBeginA",DateUtil.formatDate(DateUtil.getBeginDayOfMonth(),DateUtil.DATE_FOMRAT_yyyy_MM_dd));
	    filterMap6.put("ids", ids);
	    filterMap6.put("uids", uids);
		Query query6= new Query(filterMap6);
	    List<Map<String, Object>> list6 = weixinReportArticleService.selectByPageExample3(query6);//
	    
	    //查询上一个月
	    Date lastMonthDate = DateUtil.rollDay(DateUtil.getBeginDayOfWeek(),-30);
	    
		Map<String,Object> filterMap7 = new HashMap<String,Object>();
		filterMap7.put("createDateBeginA", DateUtil.formatDate(lastMonthDate,DateUtil.DATE_FOMRAT_yyyy_MM_dd));//
		filterMap7.put("createDateEndA", DateUtil.formatDate(DateUtil.getBeginDayOfMonth(),DateUtil.DATE_FOMRAT_yyyy_MM_dd));
		filterMap7.put("ids", ids);
		filterMap7.put("uids", uids);
		Query query7= new Query(filterMap7);
	    List<Map<String, Object>> list7 = weixinReportArticleService.selectByPageExample3(query7);
	    
	    if(list7==null || list7.size()==0 || list7.get(0)==null){
	    	retMap.put("rate41", 100);
	    	retMap.put("rate42", 100);
	    	retMap.put("rate43", 100);
	    	retMap.put("rate44", 100);
	    }
	    
	    if(list6.size()>0 && list6.get(0)!=null && list7.size()>0 && list7.get(0)!=null){
		    
		    String int_page_read_count_str6 = list6.get(0).get("int_page_read_count")==null? "0":String.valueOf(list6.get(0).get("int_page_read_count"));
		    BigDecimal int_page_read_count6 = new BigDecimal(int_page_read_count_str6);
		    
		    String ori_page_read_count_str6 = list6.get(0).get("ori_page_read_count")==null? "0":String.valueOf(list6.get(0).get("ori_page_read_count"));
		    BigDecimal ori_page_read_count6 = new BigDecimal(ori_page_read_count_str6);
		    
		    String feed_share_from_feed_cnt_str6 = list6.get(0).get("feed_share_from_feed_cnt")==null? "0":String.valueOf(list6.get(0).get("feed_share_from_feed_cnt"));
		    BigDecimal feed_share_from_feed_cnt6 = new BigDecimal(feed_share_from_feed_cnt_str6);
		    
		    String add_to_fav_count_str6 = list6.get(0).get("add_to_fav_count")==null? "0":String.valueOf(list6.get(0).get("add_to_fav_count"));
		    BigDecimal add_to_fav_count6 = new BigDecimal(add_to_fav_count_str6);
		    
		    String int_page_read_count_str7 = list7.get(0).get("int_page_read_count")==null? "0":String.valueOf(list7.get(0).get("int_page_read_count"));
		    BigDecimal int_page_read_count7 = new BigDecimal(int_page_read_count_str7);
		    
		    String ori_page_read_count_str7 = list7.get(0).get("ori_page_read_count")==null? "0":String.valueOf(list7.get(0).get("ori_page_read_count"));
		    BigDecimal ori_page_read_count7 = new BigDecimal(ori_page_read_count_str7);
		    
		    String feed_share_from_feed_cnt_str7 = list7.get(0).get("feed_share_from_feed_cnt")==null? "0":String.valueOf(list7.get(0).get("feed_share_from_feed_cnt"));
		    BigDecimal feed_share_from_feed_cnt7 = new BigDecimal(feed_share_from_feed_cnt_str7);
		    
		    String add_to_fav_count_str7 = list7.get(0).get("add_to_fav_count")==null? "0":String.valueOf(list7.get(0).get("add_to_fav_count"));
		    BigDecimal add_to_fav_count7 = new BigDecimal(add_to_fav_count_str7);
		    
		    if(int_page_read_count7.compareTo(new BigDecimal(0))>0){
		    	BigDecimal rate41 = ((int_page_read_count6.subtract(int_page_read_count7)).divide(int_page_read_count7,2,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
		    	retMap.put("rate41", rate41);
		    }
		    
		    if(ori_page_read_count7.compareTo(new BigDecimal(0))>0){
		    	BigDecimal rate42 = ((ori_page_read_count6.subtract(ori_page_read_count7)).divide(ori_page_read_count7,2,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
		    	retMap.put("rate42", rate42);
		    }
		    
		    if(feed_share_from_feed_cnt7.compareTo(new BigDecimal(0))>0){
		    	BigDecimal rate43 = ((feed_share_from_feed_cnt6.subtract(feed_share_from_feed_cnt7)).divide(feed_share_from_feed_cnt7,2,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
		    	retMap.put("rate43", rate43);
		    }
		    if(add_to_fav_count7.compareTo(new BigDecimal(0))>0){
		    	BigDecimal rate44 = ((add_to_fav_count6.subtract(add_to_fav_count7)).divide(add_to_fav_count7,2,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
		    	retMap.put("rate44", rate44);
		    }
		    
	    }
		
		return retMap;
	}
	
	@RequestMapping("getReportData")
    public Map<String,Object> getReportData(@RequestParam Map<String, Object> params) {
		
		String selectDays = String.valueOf(params.get("selectDays"));
		if(selectDays==null || ("null".equals(selectDays))){
			selectDays = "30";//默认查询30天的
		}
		
		Integer selectDaysInt = Integer.parseInt(selectDays);
		
		List<String> axis = new ArrayList<String>();
		for(int i=selectDaysInt;i>=0;i--){
			Date date = DateUtil.rollDay(new Date(), -i);
			String strDate = DateUtil.formatDate(date, DateUtil.DATE_FOMRAT_yyyy_MM_dd);
			axis.add(strDate);
		}
		
        List<String> legend = new ArrayList<String>(Arrays.asList(new String[] { "图文总阅读人数","图文总阅读次数",
        		"原文阅读人数","原文阅读次数","朋友圈阅读人数","朋友圈阅读次数","分享人数","分享次数","收藏人数","收藏次数"}));
        List<EchartSeries> series = new ArrayList<EchartSeries>();
        
        
        String nowDate = DateUtil.getNowDate(DateUtil.DATE_FOMRAT_yyyy_MM_dd);//当前时间
        Date dateBegin = DateUtil.rollDay(new Date(), -selectDaysInt);
        String beginDate = DateUtil.formatDate(dateBegin, DateUtil.DATE_FOMRAT_yyyy_MM_dd);//开始时间
        
        params.put("createDateBegin", beginDate);
        params.put("createDateEnd", nowDate);
        
        Query query= new Query(params);
        
        List<Map<String, Object>> list = weixinReportArticleService.selectByPageExample(query);
        
        List<BigDecimal> list1 = new ArrayList<BigDecimal>();
        List<BigDecimal> list2 = new ArrayList<BigDecimal>();
        List<BigDecimal> list3 = new ArrayList<BigDecimal>();
        List<BigDecimal> list4 = new ArrayList<BigDecimal>();
        List<BigDecimal> list5 = new ArrayList<BigDecimal>();
        List<BigDecimal> list6 = new ArrayList<BigDecimal>();
        List<BigDecimal> list7 = new ArrayList<BigDecimal>();
        List<BigDecimal> list8 = new ArrayList<BigDecimal>();
        List<BigDecimal> list9 = new ArrayList<BigDecimal>();
        List<BigDecimal> list10 = new ArrayList<BigDecimal>();
        
        for(String axisStr:axis){
        	
        	Map<String, Object> retMap = find( axisStr,  list);
	        	
        	String str1 = retMap.get("int_page_read_user")==null? "0": String.valueOf(retMap.get("int_page_read_user"));
        	String str2 = retMap.get("int_page_read_count")==null? "0": String.valueOf(retMap.get("int_page_read_count"));
        	String str3 = retMap.get("ori_page_read_user")==null? "0": String.valueOf(retMap.get("ori_page_read_user"));
        	String str4 = retMap.get("ori_page_read_count")==null? "0": String.valueOf(retMap.get("ori_page_read_count"));
        	String str5 = retMap.get("int_page_from_friends_read_user")==null? "0": String.valueOf(retMap.get("int_page_from_friends_read_user"));
        	String str6 = retMap.get("int_page_from_friends_read_count")==null? "0": String.valueOf(retMap.get("int_page_from_friends_read_count"));
        	String str7 = retMap.get("share_user")==null? "0": String.valueOf(retMap.get("share_user"));
        	String str8 = retMap.get("share_count")==null? "0": String.valueOf(retMap.get("share_count"));
        	String str9 = retMap.get("add_to_fav_user")==null? "0": String.valueOf(retMap.get("add_to_fav_user"));
        	String str10 = retMap.get("add_to_fav_count")==null? "0": String.valueOf(retMap.get("add_to_fav_count"));
        	
        	BigDecimal data1 = BigDecimal.valueOf(Long.valueOf(String.valueOf(str1)));
        	BigDecimal data2 = BigDecimal.valueOf(Long.valueOf(String.valueOf(str2)));
        	BigDecimal data3 = BigDecimal.valueOf(Long.valueOf(String.valueOf(str3)));
        	BigDecimal data4 = BigDecimal.valueOf(Long.valueOf(String.valueOf(str4)));
        	BigDecimal data5 = BigDecimal.valueOf(Long.valueOf(String.valueOf(str5)));
        	BigDecimal data6 = BigDecimal.valueOf(Long.valueOf(String.valueOf(str6)));
        	BigDecimal data7 = BigDecimal.valueOf(Long.valueOf(String.valueOf(str7)));
        	BigDecimal data8 = BigDecimal.valueOf(Long.valueOf(String.valueOf(str8)));
        	BigDecimal data9 = BigDecimal.valueOf(Long.valueOf(String.valueOf(str9)));
        	BigDecimal data10 = BigDecimal.valueOf(Long.valueOf(String.valueOf(str10)));
        	
        	list1.add(data1);
        	list2.add(data2);
        	list3.add(data3);
        	list4.add(data4);
        	list5.add(data5);
        	list6.add(data6);
        	list7.add(data7);
        	list8.add(data8);
        	list9.add(data9);
        	list10.add(data10);
        }
        
        series.add(new EchartSeries("图文阅读人数", "line","图文阅读人数",list1));
        series.add(new EchartSeries("图文总阅读次数", "line","图文总阅读次数",list2));
        series.add(new EchartSeries("原文阅读人数", "line","原文阅读人数",list3));
        series.add(new EchartSeries("原文阅读次数", "line","原文阅读次数",list4));
        series.add(new EchartSeries("朋友圈阅读人数", "line","朋友圈阅读人数",list5));
        series.add(new EchartSeries("朋友圈阅读次数", "line","朋友圈阅读次数",list6));
        series.add(new EchartSeries("分享人数", "line","分享人数",list7));
        series.add(new EchartSeries("分享次数", "line","分享次数",list8));
        series.add(new EchartSeries("收藏人数", "line","收藏人数",list9));
        series.add(new EchartSeries("收藏次数", "line","收藏次数",list10));
        
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("legend", legend);
        map.put("axis", axis);
        map.put("series", series);
        return map;
    }
	
	@RequestMapping("getReportData2")
    public Map<String,Object> getReportData2(@RequestParam Map<String, Object> params) {
		
        List<String> legend = new ArrayList<String>(Arrays.asList(new String[] { "图文总阅读人数","图文总阅读次数",
        		"原文阅读人数","原文阅读次数","朋友圈阅读人数","朋友圈阅读次数","分享人数","分享次数","收藏人数","收藏次数"}));
        List<EchartSeries> series = new ArrayList<EchartSeries>();
        Query query= new Query(params);
        
        List<Map<String, Object>> list = weixinReportArticleService.selectByPageExample3(query);
        
        List<BigDecimal> list1 = new ArrayList<BigDecimal>();
        
        for(Map<String, Object> retMap:list){
	        	
        	String str1 = retMap.get("int_page_read_user")==null? "0": String.valueOf(retMap.get("int_page_read_user"));
        	String str2 = retMap.get("int_page_read_count")==null? "0": String.valueOf(retMap.get("int_page_read_count"));
        	String str3 = retMap.get("ori_page_read_user")==null? "0": String.valueOf(retMap.get("ori_page_read_user"));
        	String str4 = retMap.get("ori_page_read_count")==null? "0": String.valueOf(retMap.get("ori_page_read_count"));
        	String str5 = retMap.get("int_page_from_friends_read_user")==null? "0": String.valueOf(retMap.get("int_page_from_friends_read_user"));
        	String str6 = retMap.get("int_page_from_friends_read_count")==null? "0": String.valueOf(retMap.get("int_page_from_friends_read_count"));
        	String str7 = retMap.get("share_user")==null? "0": String.valueOf(retMap.get("share_user"));
        	String str8 = retMap.get("share_count")==null? "0": String.valueOf(retMap.get("share_count"));
        	String str9 = retMap.get("add_to_fav_user")==null? "0": String.valueOf(retMap.get("add_to_fav_user"));
        	String str10 = retMap.get("add_to_fav_count")==null? "0": String.valueOf(retMap.get("add_to_fav_count"));
        	
        	BigDecimal data1 = BigDecimal.valueOf(Long.valueOf(String.valueOf(str1)));
        	BigDecimal data2 = BigDecimal.valueOf(Long.valueOf(String.valueOf(str2)));
        	BigDecimal data3 = BigDecimal.valueOf(Long.valueOf(String.valueOf(str3)));
        	BigDecimal data4 = BigDecimal.valueOf(Long.valueOf(String.valueOf(str4)));
        	BigDecimal data5 = BigDecimal.valueOf(Long.valueOf(String.valueOf(str5)));
        	BigDecimal data6 = BigDecimal.valueOf(Long.valueOf(String.valueOf(str6)));
        	BigDecimal data7 = BigDecimal.valueOf(Long.valueOf(String.valueOf(str7)));
        	BigDecimal data8 = BigDecimal.valueOf(Long.valueOf(String.valueOf(str8)));
        	BigDecimal data9 = BigDecimal.valueOf(Long.valueOf(String.valueOf(str9)));
        	BigDecimal data10 = BigDecimal.valueOf(Long.valueOf(String.valueOf(str10)));
        	
        	list1.add(data1);
        	list1.add(data2);
        	list1.add(data3);
        	list1.add(data4);
        	list1.add(data5);
        	list1.add(data6);
        	list1.add(data7);
        	list1.add(data8);
        	list1.add(data9);
        	list1.add(data10);
        }
        
        series.add(new EchartSeries("图文阅读人数", "bar","图文阅读人数",list1));
        
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("legend", legend);
        map.put("series", series);
        return map;
    }
	
	/**
	 * 查找是否找到对应的日期
	 * @param axisStr
	 * @param list
	 * @return
	 */
	public Map<String,Object> find(String axisStr, List<Map<String, Object>> list){
		Map<String, Object> retMap = new HashMap<String,Object>();
		
	 for(Map<String, Object> map: list) {
     	
     	String stat_date = String.valueOf(map.get("stat_date"));
     	
     	if(axisStr.equals(stat_date)){
	        	retMap = map;
	        	return retMap;
     	}
     }
	 
	 return retMap;
	}
	
	@RequestMapping("getReportData3")
    public List<Map<String, Object>> getReportData3(@RequestParam Map<String, Object> params) {
        Query query= new Query(params);
        List<Map<String, Object>> list = weixinReportArticleService.selectByPageExample4(query);
        return list;
    }
	
	/**
	 * 首页统计数据
	 * @param params
	 * @return
	 */
	@RequestMapping("getReportDataMain")
    public Map<String, Object> getReportDataMain(@RequestParam Map<String, Object> params) {
        Query query= new Query(params);
        Integer titleCounts  = weixinReportArticleService.selectByPageExample5(query);
        
		LayUiTableResultResponse LayUiTableResultResponse = weixinUserinfoService.selectByQuery(query);
		
        Integer selecCountAllFans = weixinFansInfoService.selecCountAllFans();
        Long weixinUserCount = LayUiTableResultResponse.getCount();
        
        Map<String, Object> retMap = new HashMap<String,Object>();
        retMap.put("titleCounts", titleCounts);
        retMap.put("weixinUserCount", weixinUserCount);
        retMap.put("selecCountAllFans", selecCountAllFans);
        
        return retMap;
    }
}

