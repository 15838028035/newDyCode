package com.lj.cloud.secrity.service;

import java.util.List;

import com.weixindev.micro.serv.common.bean.weixin.WeixinPushLog;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;


public interface WeixinPushLogService  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinPushLog weixinPushLog);

    int insertSelective(WeixinPushLog weixinPushLog);

    WeixinPushLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinPushLog weixinPushLog);

    int updateByPrimaryKey(WeixinPushLog weixinPushLog);

    public LayUiTableResultResponse	 selectByQuery(Query query) ;

    public  List<WeixinPushLog> selectByExample(Query query);

	WeixinPushLog selectByAppidAndMediaId(String authorizerAppid, String mediaId);
}
