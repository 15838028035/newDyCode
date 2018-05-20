package com.lj.cloud.secrity.dal;

import java.util.List;
import java.util.Map;


import com.weixindev.micro.serv.common.bean.secrity.SecAdminUser;
import com.weixindev.micro.serv.common.pagination.Query;

public interface SecAdminUserMapper {
    
	int deleteByPrimaryKey(Integer id);

    int insert(SecAdminUser record);

    int insertSelective(SecAdminUser record);

    SecAdminUser selectByPrimaryKey(Integer id);
    
    List<SecAdminUser> selectByExample(Query query);

    int updateByPrimaryKeySelective(SecAdminUser record);

    int updateByPrimaryKey(SecAdminUser record);
    
    /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<SecAdminUser> selectByExample(Object mapAndObject);
    
    List<SecAdminUser> selectByExampleQuery(Object mapAndObject);
    
    /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<Map<String,Object>> selectByPageExample(Object mapAndObject);

	int updateBySecGroupId(Map<String, Object> map);

	List<SecAdminUser> updateBySecGroupId(SecAdminUser secAdminUser);

	SecAdminUser findAdminUserQuery(String loginNo);
    
}