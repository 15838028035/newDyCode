package com.lj.cloud.secrity.dal;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.report.WeixinFansCount;


public interface WeixinFansCountMapper  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinFansCount weixinFansCount);

    int insertSelective(WeixinFansCount weixinFansCount);

    WeixinFansCount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinFansCount weixinFansCount);

    int updateByPrimaryKey(WeixinFansCount weixinFansCount);

   

   /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<WeixinFansCount> selectByExample(Object mapAndObject);

   /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<Map<String,Object>> selectByPageExample(Object mapAndObject);
    String selectNextOpenidByNikename(String nikeName);
    String selectNextOpenidByuserId(Integer userId);
    List<Map<String,Object>> selectByUserIdAndTime(Map<String,Object> map);
    List<Map<String,Object>> selectUserStatus(Object mapAndObject);
    WeixinFansCount selectByuserIdMaxTime(int userId);
}
