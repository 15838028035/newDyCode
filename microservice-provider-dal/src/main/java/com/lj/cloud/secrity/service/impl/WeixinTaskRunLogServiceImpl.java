package com.lj.cloud.secrity.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lj.cloud.secrity.dal.WeixinTaskRunLogMapper;
import com.lj.cloud.secrity.service.WeixinTaskRunLogService;
import com.weixindev.micro.serv.common.bean.report.WeixinTaskRunLog;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;

@Service
public class WeixinTaskRunLogServiceImpl  implements WeixinTaskRunLogService{
	private Logger logger = LoggerFactory.getLogger(WeixinTaskRunLogServiceImpl.class);

	@Autowired
	private WeixinTaskRunLogMapper weixinTaskRunLogMapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return weixinTaskRunLogMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(WeixinTaskRunLog weixinTaskRunLog){
		return weixinTaskRunLogMapper.insert(weixinTaskRunLog);
	}

	@Override
	public int insertSelective(WeixinTaskRunLog weixinTaskRunLog) {
		return weixinTaskRunLogMapper.insertSelective(weixinTaskRunLog);
	}

	@Override
	public WeixinTaskRunLog selectByPrimaryKey(Integer id) {
		return weixinTaskRunLogMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(WeixinTaskRunLog weixinTaskRunLog) {
		return weixinTaskRunLogMapper.updateByPrimaryKeySelective(weixinTaskRunLog);
	}

	@Override
	public int updateByPrimaryKey(WeixinTaskRunLog weixinTaskRunLog) {
		return weixinTaskRunLogMapper.updateByPrimaryKey(weixinTaskRunLog);
	}

	@Override
	 public LayUiTableResultResponse selectByQuery(Query query) {
	        com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<Map<String,Object>> list  = weixinTaskRunLogMapper.selectByPageExample(query);
	        return new LayUiTableResultResponse(result.getTotal(), list);
	}

	@Override
	public List<WeixinTaskRunLog> selectByExample(Query query) {
		return weixinTaskRunLogMapper.selectByExample(query);
	}

}
