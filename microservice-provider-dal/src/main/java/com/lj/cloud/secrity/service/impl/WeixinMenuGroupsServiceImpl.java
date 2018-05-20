package com.lj.cloud.secrity.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lj.cloud.secrity.dal.WeixinMenuGroupsMapper;
import com.lj.cloud.secrity.service.WeixinMenuGroupsService;
import com.weixindev.micro.serv.common.bean.weixin.WeixinMenuGroups;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
@Service
public class WeixinMenuGroupsServiceImpl  implements WeixinMenuGroupsService{
	private Logger logger = LoggerFactory.getLogger(WeixinMenuGroupsServiceImpl.class);

	@Autowired
	private WeixinMenuGroupsMapper weixinMenuGroupsMapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return weixinMenuGroupsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(WeixinMenuGroups weixinMenuGroups){
		return weixinMenuGroupsMapper.insert(weixinMenuGroups);
	}

	@Override
	public int insertSelective(WeixinMenuGroups weixinMenuGroups) {
		return weixinMenuGroupsMapper.insertSelective(weixinMenuGroups);
	}

	@Override
	public WeixinMenuGroups selectByPrimaryKey(Integer id) {
		return weixinMenuGroupsMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(WeixinMenuGroups weixinMenuGroups) {
		return weixinMenuGroupsMapper.updateByPrimaryKeySelective(weixinMenuGroups);
	}

	@Override
	public int updateByPrimaryKey(WeixinMenuGroups weixinMenuGroups) {
		return weixinMenuGroupsMapper.updateByPrimaryKey(weixinMenuGroups);
	}

	@Override
	 public LayUiTableResultResponse selectByQuery(Query query) {
	        com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<Map<String,Object>> list  = weixinMenuGroupsMapper.selectByPageExample(query);
	        return new LayUiTableResultResponse(result.getTotal(), list);
	}

	@Override
	public List<WeixinMenuGroups> selectByExample(Query query) {
		return weixinMenuGroupsMapper.selectByExample(query);
	}

}
