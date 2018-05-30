package com.lj.cloud.secrity.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lj.cloud.secrity.dal.WeixinFansCountMapper;
import com.lj.cloud.secrity.service.WeixinFansCountService;
import com.weixindev.micro.serv.common.bean.report.WeixinFansCount;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
@Service
public class WeixinFansCountServiceImpl  implements WeixinFansCountService{
	@Autowired
	private WeixinFansCountMapper weixinFansCountMapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return weixinFansCountMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(WeixinFansCount weixinFansCount){
		return weixinFansCountMapper.insert(weixinFansCount);
	}

	@Override
	public int insertSelective(WeixinFansCount weixinFansCount) {
		return weixinFansCountMapper.insertSelective(weixinFansCount);
	}

	@Override
	public WeixinFansCount selectByPrimaryKey(Integer id) {
		return weixinFansCountMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(WeixinFansCount weixinFansCount) {
		return weixinFansCountMapper.updateByPrimaryKeySelective(weixinFansCount);
	}

	@Override
	public int updateByPrimaryKey(WeixinFansCount weixinFansCount) {
		return weixinFansCountMapper.updateByPrimaryKey(weixinFansCount);
	}

	@Override
	 public LayUiTableResultResponse selectByQuery(Query query) {
	        com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<Map<String,Object>> list  = weixinFansCountMapper.selectByPageExample(query);
	        return new LayUiTableResultResponse(result.getTotal(), list);
	}

	@Override
	public List<WeixinFansCount> selectByExample(Query query) {
		return weixinFansCountMapper.selectByExample(query);
	}

	@Override
	public String selectNextOpenidByNikename(String nikeName) {
		return weixinFansCountMapper.selectNextOpenidByNikename(nikeName);
	}


	@Override
	public List<Map<String, Object>> selectByUserIdAndTime(Map<String,Object> map) {
		List<Map<String,Object>> list=weixinFansCountMapper.selectByUserIdAndTime(map);
		return list;
	}

	@Override
	public String selectNextOpenidByUserId(Integer userId) {
		String openId=weixinFansCountMapper.selectNextOpenidByuserId(userId);
		return openId;
	}

	@Override
	public List<Map<String, Object>> selectUserStatus(Query query) {
		return weixinFansCountMapper.selectUserStatus(query);
	}
	

}
