package com.lj.cloud.secrity.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lj.cloud.secrity.dal.WeixinImgtextMapper;
import com.lj.cloud.secrity.service.WeixinImgtextService;
import com.weixindev.micro.serv.common.bean.weixin.WeixinImgtext;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
@Service
public class WeixinImgtextServiceImpl  implements WeixinImgtextService{
	private Logger logger = LoggerFactory.getLogger(WeixinImgtextServiceImpl.class);

	@Autowired
	private WeixinImgtextMapper weixinImgtextMapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return weixinImgtextMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(WeixinImgtext weixinImgtext){
		return weixinImgtextMapper.insert(weixinImgtext);
	}

	@Override
	public int insertSelective(WeixinImgtext weixinImgtext) {
		return weixinImgtextMapper.insertSelective(weixinImgtext);
	}

	@Override
	public WeixinImgtext selectByPrimaryKey(Integer id) {
		return weixinImgtextMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(WeixinImgtext weixinImgtext) {
		return weixinImgtextMapper.updateByPrimaryKeySelective(weixinImgtext);
	}

	@Override
	public int updateByPrimaryKey(WeixinImgtext weixinImgtext) {
		return weixinImgtextMapper.updateByPrimaryKey(weixinImgtext);
	}

	@Override
	 public LayUiTableResultResponse selectByQuery(Query query) {
	        com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<Map<String,Object>> list  = weixinImgtextMapper.selectByPageExample(query);
	        return new LayUiTableResultResponse(result.getTotal(), list);
	}

	@Override
	public List<WeixinImgtext> selectByExample(Query query) {
		return weixinImgtextMapper.selectByExample(query);
	}

}
