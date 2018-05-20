package com.lj.cloud.secrity.dal;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.report.WeixinTaskRunLog;


public interface WeixinTaskRunLogMapper  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinTaskRunLog weixinTaskRunLog);

    int insertSelective(WeixinTaskRunLog weixinTaskRunLog);

    WeixinTaskRunLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinTaskRunLog weixinTaskRunLog);

    int updateByPrimaryKey(WeixinTaskRunLog weixinTaskRunLog);

   

   /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<WeixinTaskRunLog> selectByExample(Object mapAndObject);

   /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<Map<String,Object>> selectByPageExample(Object mapAndObject);
}
