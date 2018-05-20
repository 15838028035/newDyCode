package com.lj.cloud.secrity.service;

import java.util.List;

import com.weixindev.micro.serv.common.bean.weixin.WeixinConfig;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;


public interface WeixinConfigService  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinConfig weixinConfig);

    int insertSelective(WeixinConfig weixinConfig);

    WeixinConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinConfig weixinConfig);

    int updateByPrimaryKey(WeixinConfig weixinConfig);

    public LayUiTableResultResponse	 selectByQuery(Query query) ;

    public  List<WeixinConfig> selectByExample(Query query);
}
