package com.lj.cloud.secrity.dal;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.weixindev.micro.serv.common.bean.weixin.WeixinPushLog;


public interface WeixinPushLogMapper  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinPushLog weixinPushLog);

    int insertSelective(WeixinPushLog weixinPushLog);

    WeixinPushLog selectByPrimaryKey(Integer id);
    
    WeixinPushLog selectByAppidAndMediaId(@Param("authorizerAppid") String authorizerAppid, @Param("mediaId") String mediaId);

    int updateByPrimaryKeySelective(WeixinPushLog weixinPushLog);

    int updateByPrimaryKey(WeixinPushLog weixinPushLog);

   

   /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<WeixinPushLog> selectByExample(Object mapAndObject);

   /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<Map<String,Object>> selectByPageExample(Object mapAndObject);
}
