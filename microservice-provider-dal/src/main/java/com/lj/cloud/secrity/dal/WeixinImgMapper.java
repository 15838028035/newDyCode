package com.lj.cloud.secrity.dal;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.weixin.WeixinImg;


public interface WeixinImgMapper  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinImg weixinImg);

    int insertSelective(WeixinImg weixinImg);

    WeixinImg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinImg weixinImg);

    int updateByPrimaryKey(WeixinImg weixinImg);

   

    List<WeixinImg> selectByExample(Object mapAndObject);

    List<Map<String,Object>> selectByPageExample(Object mapAndObject);
}
