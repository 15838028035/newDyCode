package com.lj.cloud.secrity.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lj.cloud.secrity.dal.WeixinFansInfoMapper;
import com.lj.cloud.secrity.service.WeixinFansInfoService;
import com.weixindev.micro.serv.common.bean.report.WeixinFansInfo;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
@Service
public class WeixinFansInfoServiceImpl  implements WeixinFansInfoService{
	private Logger logger = LoggerFactory.getLogger(WeixinFansInfoServiceImpl.class);

	@Autowired
	private WeixinFansInfoMapper weixinFansInfoMapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return weixinFansInfoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(WeixinFansInfo weixinFansInfo){
		return weixinFansInfoMapper.insert(weixinFansInfo);
	}

	@Override
	public int insertSelective(WeixinFansInfo weixinFansInfo) {
		return weixinFansInfoMapper.insertSelective(weixinFansInfo);
	}

	@Override
	public WeixinFansInfo selectByPrimaryKey(Integer id) {
		return weixinFansInfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(WeixinFansInfo weixinFansInfo) {
		return weixinFansInfoMapper.updateByPrimaryKeySelective(weixinFansInfo);
	}

	@Override
	public int updateByPrimaryKey(WeixinFansInfo weixinFansInfo) {
		return weixinFansInfoMapper.updateByPrimaryKey(weixinFansInfo);
	}

	@Override
	 public LayUiTableResultResponse selectByQuery(Query query) {
	        com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<Map<String,Object>> list  = weixinFansInfoMapper.selectByPageExample(query);
	        return new LayUiTableResultResponse(result.getTotal(), list);
	}

	@Override
	public List<WeixinFansInfo> selectByExample(Query query) {
		return weixinFansInfoMapper.selectByExample(query);
	}

	@Override
	public int insertBatch(List<WeixinFansInfo> list) {
		int num=weixinFansInfoMapper.insertBatch(list);
		return num;
	}

	@Override
	public List<Map<String, Object>> selectPosition(Map<String,Object> map) {
		List<Map<String, Object>> list=weixinFansInfoMapper.selectPosition(map);
		return list;
	}

	@Override
	public List<Map<String, Object>> selectPositionByUid(Map<String, Object> map) {
		List<Map<String, Object>> list=weixinFansInfoMapper.selectPositionByUid(map);
		return list;
	}

	@Override
	public Integer selecCountAllFans() {
		return weixinFansInfoMapper.selecCountAllFans();
	}

}
