package com.lj.cloud.secrity.dal;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.secrity.SecPrevilegeRelation;


public interface AdminAuthortyMapper {

	
	public List<Map<String,Object>> getMenuList(Object mapAndObject);

	public void addAuthorityAssignment(SecPrevilegeRelation secPrevilege);
}
