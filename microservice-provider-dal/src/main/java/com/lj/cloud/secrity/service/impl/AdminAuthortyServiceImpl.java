package com.lj.cloud.secrity.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lj.cloud.secrity.dal.AdminAuthortyMapper;
import com.weixindev.micro.serv.common.bean.secrity.SecPrevilegeRelation;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;

@Service
public class AdminAuthortyServiceImpl implements com.lj.cloud.secrity.service.AdminAuthortyService {

	@Autowired
	AdminAuthortyMapper adminAuthortyMapper;
	
	@Override
	public LayUiTableResultResponse getMenuList(Query query) {
		com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
		List<Map<String, Object>> list = adminAuthortyMapper.getMenuList(query);
		return new LayUiTableResultResponse(result.getTotal(), list);
	}

	@Override
	public List<Map<String, Object>> getAllMenu() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void addAuthorityAssignment(Integer groupId, String roleIds) {
	}

}
