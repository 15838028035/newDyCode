package com.lj.cloud.secrity.dal;

import java.util.Map;

import com.weixindev.micro.serv.common.bean.weixin.WeixinSubscribe;

public interface WeixinSubscribeMapper {
    int insert(WeixinSubscribe weixinSubscribe);
    WeixinSubscribe select(Map<String,Object> map);
}