package com.lj.cloud.secrity.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lj.cloud.secrity.dal.WeixinVideoMapper;
import com.lj.cloud.secrity.service.WeixinVideoService;
import com.weixindev.micro.serv.common.bean.weixin.WeixinVideo;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
@Service
public class WeixinVideoServiceImpl  implements WeixinVideoService{
	private Logger logger = LoggerFactory.getLogger(WeixinVideoServiceImpl.class);

	@Autowired
	private WeixinVideoMapper weixinVideoMapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return weixinVideoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(WeixinVideo weixinVideo){
		return weixinVideoMapper.insert(weixinVideo);
	}

	@Override
	public int insertSelective(WeixinVideo weixinVideo) {
		return weixinVideoMapper.insertSelective(weixinVideo);
	}

	@Override
	public WeixinVideo selectByPrimaryKey(Integer id) {
		return weixinVideoMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(WeixinVideo weixinVideo) {
		return weixinVideoMapper.updateByPrimaryKeySelective(weixinVideo);
	}

	@Override
	public int updateByPrimaryKey(WeixinVideo weixinVideo) {
		return weixinVideoMapper.updateByPrimaryKey(weixinVideo);
	}

	@Override
	 public LayUiTableResultResponse selectByQuery(Query query) {
	        com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<Map<String,Object>> list  = weixinVideoMapper.selectByPageExample(query);
	        return new LayUiTableResultResponse(result.getTotal(), list);
	}

	@Override
	public List<WeixinVideo> selectByExample(Query query) {
		return weixinVideoMapper.selectByExample(query);
	}

	@Override
	public void deleteByIds(Integer[] ids) {
		 weixinVideoMapper.deleteByIds(ids);
	}
}
