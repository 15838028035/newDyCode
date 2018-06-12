package com.lj.cloud.secrity.service;

import java.util.Map;

import com.weixindev.micro.serv.common.bean.weixin.WeixinSubscribe;


public interface WeixinSubscribeService  {

    int insert(WeixinSubscribe weixinSubscribe);

    WeixinSubscribe select(Map<String,Object> map);

}
