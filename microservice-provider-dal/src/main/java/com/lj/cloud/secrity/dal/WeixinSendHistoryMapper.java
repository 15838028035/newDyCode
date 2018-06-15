package com.lj.cloud.secrity.dal;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.report.WeixinSendHistory;

public interface WeixinSendHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WeixinSendHistory record);

    int insertSelective(WeixinSendHistory record);

    WeixinSendHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinSendHistory record);

    int updateByPrimaryKey(WeixinSendHistory record);
    List<Map<String,Object>> selectByPageExample(Object mapAndObject);
}