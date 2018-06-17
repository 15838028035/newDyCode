package com.lj.cloud.secrity.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lj.cloud.secrity.dal.WeixinSendHistoryMapper;
import com.lj.cloud.secrity.service.WeixinSendHistoryService;
import com.weixindev.micro.serv.common.bean.report.WeixinSendHistory;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
@Service
public class WeixinSendHistoryServiceImpl implements WeixinSendHistoryService{
	@Autowired
	private WeixinSendHistoryMapper weixinSendHistoryMapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return weixinSendHistoryMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(WeixinSendHistory record) {
		return weixinSendHistoryMapper.insert(record);
	}

	@Override
	public int insertSelective(WeixinSendHistory record) {
		return weixinSendHistoryMapper.insertSelective(record);
	}

	@Override
	public WeixinSendHistory selectByPrimaryKey(Integer id) {
		return weixinSendHistoryMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(WeixinSendHistory record) {
		return weixinSendHistoryMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(WeixinSendHistory record) {
		return weixinSendHistoryMapper.updateByPrimaryKey(record);
	}

	@Override
	public LayUiTableResultResponse selectByQuery(Query query) {
		com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<Map<String,Object>> list  = weixinSendHistoryMapper.selectByPageExample(query);
        return new LayUiTableResultResponse(result.getTotal(), list);
	}

}
