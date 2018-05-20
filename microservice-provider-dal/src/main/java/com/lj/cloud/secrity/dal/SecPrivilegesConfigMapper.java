package com.lj.cloud.secrity.dal;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.secrity.SecPrivilegesConfig;


public interface SecPrivilegesConfigMapper  {

    int deleteByPrimaryKey(Integer id);

    int insert(SecPrivilegesConfig secPrivilegesConfig);

    int insertSelective(SecPrivilegesConfig secPrivilegesConfig);

    SecPrivilegesConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SecPrivilegesConfig secPrivilegesConfig);

    int updateByPrimaryKey(SecPrivilegesConfig secPrivilegesConfig);

   

   /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<SecPrivilegesConfig> selectByExample(Object mapAndObject);

   /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<Map<String,Object>> selectByPageExample(Object mapAndObject,String name);

    SecPrivilegesConfig selectConfig(Integer id);

    List<Map<String,Object>>selectByQueryInfo(String name);
}
