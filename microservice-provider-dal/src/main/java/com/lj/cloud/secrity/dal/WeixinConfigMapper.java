package com.lj.cloud.secrity.dal;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.weixin.WeixinConfig;


public interface WeixinConfigMapper  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinConfig weixinConfig);

    int insertSelective(WeixinConfig weixinConfig);

    WeixinConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinConfig weixinConfig);

    int updateByPrimaryKey(WeixinConfig weixinConfig);

   
    List<WeixinConfig> selectByExample(Object mapAndObject);

    List<Map<String,Object>> selectByPageExample(Object mapAndObject);
}
