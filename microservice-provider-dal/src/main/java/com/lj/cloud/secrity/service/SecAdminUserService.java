package com.lj.cloud.secrity.service;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.secrity.SecAdminUser;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;

public interface SecAdminUserService {
	    
	int deleteByPrimaryKey(Integer id);

    int insert(SecAdminUser record);

    int insertSelective(SecAdminUser record);

    SecAdminUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SecAdminUser record);

    int updateByPrimaryKey(SecAdminUser record);
    
	    public SecAdminUser login(String loginiNo, String pwd);
	    
		 public LayUiTableResultResponse	 selectByQuery(Query query) ;
		
		 
	/**
	 * 更新分组
	 * @param map
	 * @return
	 */
	public int  updateBySecGroupId(Map<String,Object> map);

	List<SecAdminUser> selectByExample(SecAdminUser secAdminUser);

	List<SecAdminUser> selectByExampleQuery(Query query);

	SecAdminUser findAdminUserQuery(String loginNo);
	    
}
