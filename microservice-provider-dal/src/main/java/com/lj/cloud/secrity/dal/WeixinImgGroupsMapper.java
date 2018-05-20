package com.lj.cloud.secrity.dal;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.weixin.WeixinImgGroups;


public interface WeixinImgGroupsMapper  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinImgGroups weixinImgGroups);

    int insertSelective(WeixinImgGroups weixinImgGroups);

    WeixinImgGroups selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinImgGroups weixinImgGroups);

    int updateByPrimaryKey(WeixinImgGroups weixinImgGroups);

   

    List<WeixinImgGroups> selectByExample(Object mapAndObject);

    List<Map<String,Object>> selectByPageExample(Object mapAndObject);
}
