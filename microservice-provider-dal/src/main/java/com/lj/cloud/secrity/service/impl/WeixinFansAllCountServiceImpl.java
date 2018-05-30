package com.lj.cloud.secrity.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lj.cloud.secrity.dal.WeixinFansAllCountMapper;
import com.lj.cloud.secrity.service.WeixinFansAllCountService;
import com.weixindev.micro.serv.common.bean.report.WeixinFansAllCount;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
@Service
public class WeixinFansAllCountServiceImpl  implements WeixinFansAllCountService{
	private Logger logger = LoggerFactory.getLogger(WeixinFansAllCountServiceImpl.class);

	@Autowired
	private WeixinFansAllCountMapper weixinFansAllCountMapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return weixinFansAllCountMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(WeixinFansAllCount weixinFansAllCount){
		return weixinFansAllCountMapper.insert(weixinFansAllCount);
	}

	@Override
	public int insertSelective(WeixinFansAllCount weixinFansAllCount) {
		return weixinFansAllCountMapper.insertSelective(weixinFansAllCount);
	}

	@Override
	public WeixinFansAllCount selectByPrimaryKey(Integer id) {
		return weixinFansAllCountMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(WeixinFansAllCount weixinFansAllCount) {
		return weixinFansAllCountMapper.updateByPrimaryKeySelective(weixinFansAllCount);
	}

	@Override
	public int updateByPrimaryKey(WeixinFansAllCount weixinFansAllCount) {
		return weixinFansAllCountMapper.updateByPrimaryKey(weixinFansAllCount);
	}

	@Override
	 public LayUiTableResultResponse selectByQuery(Query query) {
		 com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<Map<String,Object>> list =weixinFansAllCountMapper.selectByPageExample(query);
	        return new LayUiTableResultResponse(result.getTotal(), list);
	}

	@Override
	public List<WeixinFansAllCount> selectByExample(Query query) {
		return weixinFansAllCountMapper.selectByExample(query);
	}

	@Override
	public LayUiTableResultResponse selectByPageExampleMaxTime(Query query) {
		 com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<Map<String,Object>> list =weixinFansAllCountMapper.selectByPageExample(query);
	        return new LayUiTableResultResponse(result.getTotal(), list);
	}

	@Override
	public Map<String, Object> selectFansSource() {
		return weixinFansAllCountMapper.selectFansSource();
	}

	@Override
	public LayUiTableResultResponse tableCountAll(Query query) {
		com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
		List<Map<String, Object>> list=weixinFansAllCountMapper.tableCountAll(query);
		return new LayUiTableResultResponse(result.getTotal(), list);
	}


}
