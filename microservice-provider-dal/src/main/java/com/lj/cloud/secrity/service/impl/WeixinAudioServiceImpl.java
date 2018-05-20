package com.lj.cloud.secrity.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lj.cloud.secrity.dal.WeixinAudioMapper;
import com.lj.cloud.secrity.service.WeixinAudioService;
import com.weixindev.micro.serv.common.bean.weixin.WeixinAudio;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
@Service
public class WeixinAudioServiceImpl  implements WeixinAudioService{
	private Logger logger = LoggerFactory.getLogger(WeixinAudioServiceImpl.class);

	@Autowired
	private WeixinAudioMapper weixinAudioMapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return weixinAudioMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(WeixinAudio weixinAudio){
		return weixinAudioMapper.insert(weixinAudio);
	}

	@Override
	public int insertSelective(WeixinAudio weixinAudio) {
		return weixinAudioMapper.insertSelective(weixinAudio);
	}

	@Override
	public WeixinAudio selectByPrimaryKey(Integer id) {
		return weixinAudioMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(WeixinAudio weixinAudio) {
		return weixinAudioMapper.updateByPrimaryKeySelective(weixinAudio);
	}

	@Override
	public int updateByPrimaryKey(WeixinAudio weixinAudio) {
		return weixinAudioMapper.updateByPrimaryKey(weixinAudio);
	}

	@Override
	 public LayUiTableResultResponse selectByQuery(Query query) {
	        com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<Map<String,Object>> list  = weixinAudioMapper.selectByPageExample(query);
	        return new LayUiTableResultResponse(result.getTotal(), list);
	}

	@Override
	public List<WeixinAudio> selectByExample(Query query) {
		return weixinAudioMapper.selectByExample(query);
	}

}
