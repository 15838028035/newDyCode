package com.lj.cloud.secrity.dal;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.weixin.WeixinMenuGroups;


public interface WeixinMenuGroupsMapper  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinMenuGroups weixinMenuGroups);

    int insertSelective(WeixinMenuGroups weixinMenuGroups);

    WeixinMenuGroups selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinMenuGroups weixinMenuGroups);

    int updateByPrimaryKey(WeixinMenuGroups weixinMenuGroups);

   

    List<WeixinMenuGroups> selectByExample(Object mapAndObject);

    List<Map<String,Object>> selectByPageExample(Object mapAndObject);
}
