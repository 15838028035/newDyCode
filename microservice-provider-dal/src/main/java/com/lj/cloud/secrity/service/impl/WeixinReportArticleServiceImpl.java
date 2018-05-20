package com.lj.cloud.secrity.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lj.cloud.secrity.dal.WeixinReportArticleMapper;
import com.lj.cloud.secrity.service.WeixinReportArticleService;
import com.weixindev.micro.serv.common.bean.report.WeixinReportArticle;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
@Service
public class WeixinReportArticleServiceImpl  implements WeixinReportArticleService{
	private Logger logger = LoggerFactory.getLogger(WeixinReportArticleServiceImpl.class);

	@Autowired
	private WeixinReportArticleMapper weixinReportArticleMapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return weixinReportArticleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(WeixinReportArticle weixinReportArticle){
		return weixinReportArticleMapper.insert(weixinReportArticle);
	}

	@Override
	public int insertSelective(WeixinReportArticle weixinReportArticle) {
		return weixinReportArticleMapper.insertSelective(weixinReportArticle);
	}

	@Override
	public WeixinReportArticle selectByPrimaryKey(Integer id) {
		return weixinReportArticleMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(WeixinReportArticle weixinReportArticle) {
		return weixinReportArticleMapper.updateByPrimaryKeySelective(weixinReportArticle);
	}

	@Override
	public int updateByPrimaryKey(WeixinReportArticle weixinReportArticle) {
		return weixinReportArticleMapper.updateByPrimaryKey(weixinReportArticle);
	}

	@Override
	 public LayUiTableResultResponse selectByQuery(Query query) {
	        com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<Map<String,Object>> list  = weixinReportArticleMapper.selectByPageExample(query);
	        return new LayUiTableResultResponse(result.getTotal(), list);
	}
	
	@Override
	 public LayUiTableResultResponse selectByQuery2(Query query) {
	        com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<Map<String,Object>> list  = weixinReportArticleMapper.selectByPageExample2(query);
	        return new LayUiTableResultResponse(result.getTotal(), list);
	}

	@Override
	public List<WeixinReportArticle> selectByExample(Query query) {
		return weixinReportArticleMapper.selectByExample(query);
	}

	@Override
	public List<Map<String, Object>> selectByPageExample(Object mapAndObject) {
		 return  weixinReportArticleMapper.selectByPageExample(mapAndObject);
	}

	@Override
	public List<Map<String, Object>> selectByPageExample2(Object mapAndObject) {
		 return  weixinReportArticleMapper.selectByPageExample2(mapAndObject);
	}
	
	@Override
	public List<Map<String, Object>> selectByPageExample3(Object mapAndObject) {
		 return  weixinReportArticleMapper.selectByPageExample3(mapAndObject);
	}
	
	@Override
	public List<Map<String, Object>> selectByPageExample4(Object mapAndObject) {
		 return  weixinReportArticleMapper.selectByPageExample4(mapAndObject);
	}
	
	@Override
	public Integer selectByPageExample5(Object mapAndObject) {
		 return  weixinReportArticleMapper.selectByPageExample5(mapAndObject);
	}
	
}
