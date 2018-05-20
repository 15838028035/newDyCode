package com.lj.cloud.secrity.dal;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.weixin.WeixinImgtext;


public interface WeixinImgtextMapper  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinImgtext weixinImgtext);

    int insertSelective(WeixinImgtext weixinImgtext);

    WeixinImgtext selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinImgtext weixinImgtext);

    int updateByPrimaryKey(WeixinImgtext weixinImgtext);

   

    List<WeixinImgtext> selectByExample(Object mapAndObject);

    List<Map<String,Object>> selectByPageExample(Object mapAndObject);
}
