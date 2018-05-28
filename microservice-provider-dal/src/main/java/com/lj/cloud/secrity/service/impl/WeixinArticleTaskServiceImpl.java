package com.lj.cloud.secrity.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lj.cloud.secrity.dal.WeixinArticleTaskMapper;
import com.lj.cloud.secrity.service.WeixinArticleTaskService;
import com.weixindev.micro.serv.common.bean.weixin.WeixinArticleTask;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;

@Service
public class WeixinArticleTaskServiceImpl  implements WeixinArticleTaskService{
	private Logger logger = LoggerFactory.getLogger(WeixinArticleTaskServiceImpl.class);

	@Autowired
	private WeixinArticleTaskMapper weixinArticleTaskMapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return weixinArticleTaskMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(WeixinArticleTask weixinArticleTask){
		return weixinArticleTaskMapper.insert(weixinArticleTask);
	}

	@Override
	public int insertSelective(WeixinArticleTask weixinArticleTask) {
		return weixinArticleTaskMapper.insertSelective(weixinArticleTask);
	}

	@Override
	public WeixinArticleTask selectByPrimaryKey(Integer id) {
		return weixinArticleTaskMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(WeixinArticleTask weixinArticleTask) {
		return weixinArticleTaskMapper.updateByPrimaryKeySelective(weixinArticleTask);
	}
	@Override
	public int updateBySelective(WeixinArticleTask weixinArticleTask) {
		return weixinArticleTaskMapper.updateByPrimaryKeySelective(weixinArticleTask);
	}

	@Override
	public int updateByPrimaryKey(WeixinArticleTask weixinArticleTask) {
		return weixinArticleTaskMapper.updateByPrimaryKey(weixinArticleTask);
	}

	@Override
	 public LayUiTableResultResponse selectByQuery(Query query) {
	        com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<Map<String,Object>> list  = weixinArticleTaskMapper.selectByPageExample(query);
	        return new LayUiTableResultResponse(result.getTotal(), list);
	}

	@Override
	public List<WeixinArticleTask> selectByExample(Query query) {
		return weixinArticleTaskMapper.selectByExample(query);
	}

	@Override
	public String selectMapKeyByExample(Query query) {
		return weixinArticleTaskMapper.selectMapKeyByExample(query);
	}

	@Override
	public List<WeixinArticleTask> selectByTime(String dateStr) {
		return weixinArticleTaskMapper.selectByTime(dateStr);
	}

}
