package com.lj.cloud.secrity.dal;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.weixin.WeixinAudio;


public interface WeixinAudioMapper  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinAudio weixinAudio);

    int insertSelective(WeixinAudio weixinAudio);

    WeixinAudio selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinAudio weixinAudio);

    int updateByPrimaryKey(WeixinAudio weixinAudio);

   

    List<WeixinAudio> selectByExample(Object mapAndObject);

    List<Map<String,Object>> selectByPageExample(Object mapAndObject);
}
