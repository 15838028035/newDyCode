package com.lj.cloud.secrity.service;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.weixin.WeixinSubscribe;


public interface WeixinSubscribeService  {

    int insert(WeixinSubscribe weixinSubscribe);

    List<WeixinSubscribe> select(Map<String,Object> map);

}
