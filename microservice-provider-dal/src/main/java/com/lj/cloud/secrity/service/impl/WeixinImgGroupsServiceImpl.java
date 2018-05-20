package com.lj.cloud.secrity.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lj.cloud.secrity.dal.WeixinImgGroupsMapper;
import com.lj.cloud.secrity.service.WeixinImgGroupsService;
import com.weixindev.micro.serv.common.bean.weixin.WeixinImgGroups;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
@Service
public class WeixinImgGroupsServiceImpl  implements WeixinImgGroupsService{
	private Logger logger = LoggerFactory.getLogger(WeixinImgGroupsServiceImpl.class);

	@Autowired
	private WeixinImgGroupsMapper weixinImgGroupsMapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return weixinImgGroupsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(WeixinImgGroups weixinImgGroups){
		return weixinImgGroupsMapper.insert(weixinImgGroups);
	}

	@Override
	public int insertSelective(WeixinImgGroups weixinImgGroups) {
		return weixinImgGroupsMapper.insertSelective(weixinImgGroups);
	}

	@Override
	public WeixinImgGroups selectByPrimaryKey(Integer id) {
		return weixinImgGroupsMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(WeixinImgGroups weixinImgGroups) {
		return weixinImgGroupsMapper.updateByPrimaryKeySelective(weixinImgGroups);
	}

	@Override
	public int updateByPrimaryKey(WeixinImgGroups weixinImgGroups) {
		return weixinImgGroupsMapper.updateByPrimaryKey(weixinImgGroups);
	}

	@Override
	 public LayUiTableResultResponse selectByQuery(Query query) {
	        com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<Map<String,Object>> list  = weixinImgGroupsMapper.selectByPageExample(query);
	        return new LayUiTableResultResponse(result.getTotal(), list);
	}

	@Override
	public List<WeixinImgGroups> selectByExample(Query query) {
		return weixinImgGroupsMapper.selectByExample(query);
	}

}
