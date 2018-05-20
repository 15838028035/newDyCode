package com.lj.cloud.secrity.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lj.cloud.secrity.dal.WeixinPushLogMapper;
import com.lj.cloud.secrity.service.WeixinPushLogService;
import com.weixindev.micro.serv.common.bean.weixin.WeixinPushLog;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
@Service
public class WeixinPushLogServiceImpl  implements WeixinPushLogService{
	private Logger logger = LoggerFactory.getLogger(WeixinPushLogServiceImpl.class);

	@Autowired
	private WeixinPushLogMapper weixinPushLogMapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return weixinPushLogMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(WeixinPushLog weixinPushLog){
		return weixinPushLogMapper.insert(weixinPushLog);
	}

	@Override
	public int insertSelective(WeixinPushLog weixinPushLog) {
		return weixinPushLogMapper.insertSelective(weixinPushLog);
	}

	@Override
	public WeixinPushLog selectByPrimaryKey(Integer id) {
		return weixinPushLogMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(WeixinPushLog weixinPushLog) {
		return weixinPushLogMapper.updateByPrimaryKeySelective(weixinPushLog);
	}

	@Override
	public int updateByPrimaryKey(WeixinPushLog weixinPushLog) {
		return weixinPushLogMapper.updateByPrimaryKey(weixinPushLog);
	}

	@Override
	 public LayUiTableResultResponse selectByQuery(Query query) {
	        com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<Map<String,Object>> list  = weixinPushLogMapper.selectByPageExample(query);
	        return new LayUiTableResultResponse(result.getTotal(), list);
	}

	@Override
	public List<WeixinPushLog> selectByExample(Query query) {
		return weixinPushLogMapper.selectByExample(query);
	}

	@Override
	public WeixinPushLog selectByAppidAndMediaId(String authorizerAppid, String mediaId) {
		return weixinPushLogMapper.selectByAppidAndMediaId(authorizerAppid, mediaId);
	}

}
