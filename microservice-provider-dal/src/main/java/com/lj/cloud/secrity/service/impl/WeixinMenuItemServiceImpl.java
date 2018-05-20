package com.lj.cloud.secrity.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lj.cloud.secrity.dal.WeixinMenuItemMapper;
import com.lj.cloud.secrity.service.WeixinMenuItemService;
import com.weixindev.micro.serv.common.bean.weixin.WeixinMenuItem;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
@Service
public class WeixinMenuItemServiceImpl  implements WeixinMenuItemService{
	private Logger logger = LoggerFactory.getLogger(WeixinMenuItemServiceImpl.class);

	@Autowired
	private WeixinMenuItemMapper weixinMenuItemMapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return weixinMenuItemMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(WeixinMenuItem weixinMenuItem){
		return weixinMenuItemMapper.insert(weixinMenuItem);
	}

	@Override
	public int insertSelective(WeixinMenuItem weixinMenuItem) {
		return weixinMenuItemMapper.insertSelective(weixinMenuItem);
	}

	@Override
	public WeixinMenuItem selectByPrimaryKey(Integer id) {
		return weixinMenuItemMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(WeixinMenuItem weixinMenuItem) {
		return weixinMenuItemMapper.updateByPrimaryKeySelective(weixinMenuItem);
	}

	@Override
	public int updateByPrimaryKey(WeixinMenuItem weixinMenuItem) {
		return weixinMenuItemMapper.updateByPrimaryKey(weixinMenuItem);
	}

	@Override
	 public LayUiTableResultResponse selectByQuery(Query query) {
	        com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<Map<String,Object>> list  = weixinMenuItemMapper.selectByPageExample(query);
	        return new LayUiTableResultResponse(result.getTotal(), list);
	}

	@Override
	public List<WeixinMenuItem> selectByExample(Query query) {
		return weixinMenuItemMapper.selectByExample(query);
	}

}
