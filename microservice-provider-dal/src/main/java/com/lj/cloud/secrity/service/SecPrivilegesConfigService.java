package com.lj.cloud.secrity.service;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.secrity.SecPrivilegesConfig;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;


public interface SecPrivilegesConfigService  {

    int deleteByPrimaryKey(Integer id);

    int insert(SecPrivilegesConfig secPrivilegesConfig);

    int insertSelective(SecPrivilegesConfig secPrivilegesConfig);

    SecPrivilegesConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SecPrivilegesConfig secPrivilegesConfig);

    int updateByPrimaryKey(SecPrivilegesConfig secPrivilegesConfig);

    public LayUiTableResultResponse	 selectByQuery(Query query,String name) ;

    public  List<SecPrivilegesConfig> selectByExample(Query query);

	int deleteById(Integer id);

	  List<Map<String,Object>> selectByQueryInfo(String name);
}
