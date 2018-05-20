package com.lj.cloud.secrity.dal;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.weixin.WeixinVideo;


public interface WeixinVideoMapper  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinVideo weixinVideo);

    int insertSelective(WeixinVideo weixinVideo);

    WeixinVideo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinVideo weixinVideo);

    int updateByPrimaryKey(WeixinVideo weixinVideo);

    List<WeixinVideo> selectByExample(Object mapAndObject);
    List<Map<String,Object>> selectByPageExample(Object mapAndObject);
    
    void deleteByIds(Integer  []ids);
}
