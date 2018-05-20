package com.lj.cloud.secrity.service;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;

/**
 * 
 * @author palmble
 *
 */
public interface AdminAuthortyService {

	
	public LayUiTableResultResponse getMenuList(Query query);

	/**
	 * @author malingbing
	 * 获取
	 * @return
	 */
	public List<Map<String, Object>> getAllMenu();
	
	public void addAuthorityAssignment(Integer groupId, String roleIds);
}
