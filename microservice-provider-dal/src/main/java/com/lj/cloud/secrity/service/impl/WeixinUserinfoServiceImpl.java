package com.lj.cloud.secrity.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lj.cloud.secrity.dal.WeixinUserinfoMapper;
import com.lj.cloud.secrity.service.WeixinUserinfoService;
import com.weixindev.micro.serv.common.bean.weixin.WeixinUserinfo;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
@Service
public class WeixinUserinfoServiceImpl  implements WeixinUserinfoService{
	private Logger logger = LoggerFactory.getLogger(WeixinUserinfoServiceImpl.class);

	@Autowired
	private WeixinUserinfoMapper weixinUserinfoMapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return weixinUserinfoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(WeixinUserinfo weixinUserinfo){
		return weixinUserinfoMapper.insert(weixinUserinfo);
	}

	@Override
	public int insertSelective(WeixinUserinfo weixinUserinfo) {
		return weixinUserinfoMapper.insertSelective(weixinUserinfo);
	}

	@Override
	public WeixinUserinfo selectByPrimaryKey(Integer id) {
		return weixinUserinfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(WeixinUserinfo weixinUserinfo) {
		return weixinUserinfoMapper.updateByPrimaryKeySelective(weixinUserinfo);
	}
	
	@Override
	public int updateByAppIdSelective(WeixinUserinfo weixinUserinfo) {
		return weixinUserinfoMapper.updateByAppIdSelective(weixinUserinfo);
	}

	@Override
	public int updateByPrimaryKey(WeixinUserinfo weixinUserinfo) {
		return weixinUserinfoMapper.updateByPrimaryKey(weixinUserinfo);
	}

	@Override
	 public LayUiTableResultResponse selectByQuery(Query query) {
	        com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<Map<String,Object>> list  = weixinUserinfoMapper.selectByPageExample(query);
	        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(list);
	        return new LayUiTableResultResponse(pageInfo.getTotal(), list);
	}

	@Override
	public List<WeixinUserinfo> selectByExample(Query query) {
		return weixinUserinfoMapper.selectByExample(query);
	}

	@Override
	public int updateByPrimaryByWeixinGroupsId(Map<String, Object> map) {
		return weixinUserinfoMapper.updateByPrimaryByWeixinGroupsId(map);
	}

	@Override
	public HashMap<String, Object> selectByAppId(String appId) {
		// TODO Auto-generated method stub
		return weixinUserinfoMapper.selectByAppId(appId);
	}

	@Override
	public int WeixinGroupsBatchMove(String movieUseIds,Integer weixinGroupsId) {
		return weixinUserinfoMapper.WeixinGroupsBatchMove(movieUseIds,weixinGroupsId);
	}

	@Override
	public List selectByTyinfo(String infotype) {
		// TODO Auto-generated method stub
		return weixinUserinfoMapper.selectByTyinfo(infotype);
	}

	@Override
	public int deleteByPrimaryKey(String weixininfoid) {
		return 0;
	}

	@Override
	public String selectAuthorizerAppidByPrimaryKey(int id) {
		return weixinUserinfoMapper.selectAuthorizerAppidByPrimaryKey(id);
	}
	public WeixinUserinfo selectByauthorizerAppid(String authorizerAppid) {
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("authorizerAppid", authorizerAppid);
		Query query = new Query(map);
		
		List<WeixinUserinfo> list = weixinUserinfoMapper.selectByExample(query);
		if(list!=null && list.size()>0) {
			return list.get(0);
		}
		return new WeixinUserinfo();
	}

	@Override
	public Integer selectIdByName(String userName) {
		return weixinUserinfoMapper.selectIdByName(userName);
	}

}
