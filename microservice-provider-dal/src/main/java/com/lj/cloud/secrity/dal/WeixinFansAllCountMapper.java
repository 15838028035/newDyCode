package com.lj.cloud.secrity.dal;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.report.WeixinFansAllCount;


public interface WeixinFansAllCountMapper  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinFansAllCount weixinFansAllCount);

    int insertSelective(WeixinFansAllCount weixinFansAllCount);

    WeixinFansAllCount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinFansAllCount weixinFansAllCount);

    int updateByPrimaryKey(WeixinFansAllCount weixinFansAllCount);

   

   /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<WeixinFansAllCount> selectByExample(Object mapAndObject);

   /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<Map<String,Object>> selectByPageExample(Object mapAndObject);
    List<Map<String,Object>> selectByPageExample2(Object mapAndObject);
    
    List<Map<String,Object>> selectByPageExampleMaxTime(Object mapAndObject);
    Map<String,Object> selectFansSource();
    List<Map<String,Object>> tableCountAll(Object mapAndObject);
    
}
