package com.lj.cloud.secrity.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lj.cloud.secrity.dal.WeixinConfigMapper;
import com.lj.cloud.secrity.service.WeixinConfigService;
import com.weixindev.micro.serv.common.bean.weixin.WeixinConfig;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
@Service
public class WeixinConfigServiceImpl  implements WeixinConfigService{
	private Logger logger = LoggerFactory.getLogger(WeixinConfigServiceImpl.class);

	@Autowired
	private WeixinConfigMapper weixinConfigMapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return weixinConfigMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(WeixinConfig weixinConfig){
		return weixinConfigMapper.insert(weixinConfig);
	}

	@Override
	public int insertSelective(WeixinConfig weixinConfig) {
		return weixinConfigMapper.insertSelective(weixinConfig);
	}

	@Override
	public WeixinConfig selectByPrimaryKey(Integer id) {
		return weixinConfigMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(WeixinConfig weixinConfig) {
		return weixinConfigMapper.updateByPrimaryKeySelective(weixinConfig);
	}

	@Override
	public int updateByPrimaryKey(WeixinConfig weixinConfig) {
		return weixinConfigMapper.updateByPrimaryKey(weixinConfig);
	}

	@Override
	 public LayUiTableResultResponse selectByQuery(Query query) {
	        com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<Map<String,Object>> list  = weixinConfigMapper.selectByPageExample(query);
	        return new LayUiTableResultResponse(result.getTotal(), list);
	}

	@Override
	public List<WeixinConfig> selectByExample(Query query) {
		return weixinConfigMapper.selectByExample(query);
	}

	@Override
	public List<Map<String,Object>> selectVideoCate() {
		return weixinConfigMapper.selectVideoCate();
	}

}
