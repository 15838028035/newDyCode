package com.lj.cloud.secrity.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lj.cloud.secrity.dal.WeixinImgMapper;
import com.lj.cloud.secrity.service.WeixinImgService;
import com.weixindev.micro.serv.common.bean.weixin.WeixinImg;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
@Service
public class WeixinImgServiceImpl  implements WeixinImgService{
	private Logger logger = LoggerFactory.getLogger(WeixinImgServiceImpl.class);

	@Autowired
	private WeixinImgMapper weixinImgMapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return weixinImgMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(WeixinImg weixinImg){
		return weixinImgMapper.insert(weixinImg);
	}

	@Override
	public int insertSelective(WeixinImg weixinImg) {
		return weixinImgMapper.insertSelective(weixinImg);
	}

	@Override
	public WeixinImg selectByPrimaryKey(Integer id) {
		return weixinImgMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(WeixinImg weixinImg) {
		return weixinImgMapper.updateByPrimaryKeySelective(weixinImg);
	}

	@Override
	public int updateByPrimaryKey(WeixinImg weixinImg) {
		return weixinImgMapper.updateByPrimaryKey(weixinImg);
	}

	@Override
	 public LayUiTableResultResponse selectByQuery(Query query) {
	        com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<Map<String,Object>> list  = weixinImgMapper.selectByPageExample(query);
	        return new LayUiTableResultResponse(result.getTotal(), list);
	}

	@Override
	public List<WeixinImg> selectByExample(Query query) {
		return weixinImgMapper.selectByExample(query);
	}

}
