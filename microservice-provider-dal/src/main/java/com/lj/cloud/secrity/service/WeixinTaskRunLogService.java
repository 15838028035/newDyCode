package com.lj.cloud.secrity.service;

import java.util.List;

import com.weixindev.micro.serv.common.bean.report.WeixinTaskRunLog;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;


public interface WeixinTaskRunLogService  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinTaskRunLog weixinTaskRunLog);

    int insertSelective(WeixinTaskRunLog weixinTaskRunLog);

    WeixinTaskRunLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinTaskRunLog weixinTaskRunLog);

    int updateByPrimaryKey(WeixinTaskRunLog weixinTaskRunLog);

    public LayUiTableResultResponse	 selectByQuery(Query query) ;

    public  List<WeixinTaskRunLog> selectByExample(Query query);
}
