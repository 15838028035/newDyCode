package com.lj.cloud.secrity.dal;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.weixin.WeixinMenuItem;


public interface WeixinMenuItemMapper  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinMenuItem weixinMenuItem);

    int insertSelective(WeixinMenuItem weixinMenuItem);

    WeixinMenuItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinMenuItem weixinMenuItem);

    int updateByPrimaryKey(WeixinMenuItem weixinMenuItem);

   

    List<WeixinMenuItem> selectByExample(Object mapAndObject);

    List<Map<String,Object>> selectByPageExample(Object mapAndObject);
}
