package com.lj.cloud.secrity.dal;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.weixin.WeixinSubscribe;

public interface WeixinSubscribeMapper {
    int insert(WeixinSubscribe weixinSubscribe);
    List<WeixinSubscribe> select(Map<String,Object> map);
}