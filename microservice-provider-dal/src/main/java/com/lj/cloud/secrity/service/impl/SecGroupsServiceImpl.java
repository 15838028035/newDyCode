package com.lj.cloud.secrity.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lj.cloud.secrity.dal.SecGroupsMapper;
import com.lj.cloud.secrity.service.SecGroupsService;
import com.weixindev.micro.serv.common.bean.secrity.SecGroups;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
@Service
public class SecGroupsServiceImpl  implements SecGroupsService{
	private Logger logger = LoggerFactory.getLogger(SecGroupsServiceImpl.class);

	@Autowired
	private SecGroupsMapper secGroupsMapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		secGroupsMapper.deleteByPrimaryGroupId(id);
		return secGroupsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SecGroups secGroups){
		return secGroupsMapper.insert(secGroups);
	}

	@Override
	public int insertSelective(SecGroups secGroups) {
		return secGroupsMapper.insertSelective(secGroups);
	}

	@Override
	public SecGroups selectByPrimaryKey(Integer id) {
		return secGroupsMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(SecGroups secGroups) {
		return secGroupsMapper.updateByPrimaryKeySelective(secGroups);
	}

	@Override
	public int updateByPrimaryKey(SecGroups secGroups) {
		return secGroupsMapper.updateByPrimaryKey(secGroups);
	}

	@Override
	 public LayUiTableResultResponse selectByQuery(Query query) {
	        com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<Map<String,Object>> list = secGroupsMapper.selectByPageExample(query);
	        return new LayUiTableResultResponse(result.getTotal(), list);
	}
	
	@Override
	public  List<SecGroups> selectByExample(Query query){
		List<SecGroups> list = secGroupsMapper.selectByExample(query);
		return list;
	}

	@Override
	public List<Map<String, Object>> selectGrouInfoByLogin(String loginNo) {
		
		List<Map<String, Object>> resultList=secGroupsMapper.selectGrouInfoByLogin(loginNo);
		
		return resultList;
	}

	@Override
	public List<Map<String, Object>> selectByInfoKey(Integer id) {
		
		List<Map<String, Object>> resultList=secGroupsMapper.selectByInfoKey(id);
		
		List<Map<String, Object>> data=secGroupsMapper.selectByInfoKeyData();
		List<Integer> list=new ArrayList<Integer>();
		for (Map<String, Object> map : data) {
			list.add(Integer.valueOf(map.get("id").toString()));
		}
		
		
		
		return resultList;
	}

	@Override
	public List<Map<String, Object>> selectByInfoKeyData() {
		return secGroupsMapper.selectByInfoKeyData();
	}

	@Override
	public void updateByPrimaryKeySelective(Integer id, List<String> urlId) {
		secGroupsMapper.deleteByPrimaryGroupId(id);
		if(urlId!=null&&urlId.size()>0) {
				for (String rulId : urlId) {
					Integer ruiId=Integer.valueOf(rulId);
					Integer gid=id;
					secGroupsMapper.insertURL(gid,ruiId);
				}
		}
	}

	@Override
	public void addByPrimaryKeySelective(SecGroups secGrops, List<String> urids,Integer gid) {
	System.out.println("------------------------------");
		if(urids!=null&&urids.size()>0) {
			for (String rulId : urids) {
				Integer ruiId=Integer.valueOf(rulId);
				secGroupsMapper.insertURL(gid,ruiId);
			}
	}
		
	}

}
