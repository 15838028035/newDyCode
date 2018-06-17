package com.lj.cloud.secrity.service;

import com.weixindev.micro.serv.common.bean.report.WeixinSendHistory;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;

public interface WeixinSendHistoryService {
    int deleteByPrimaryKey(Integer id);

    int insert(WeixinSendHistory record);

    int insertSelective(WeixinSendHistory record);

    WeixinSendHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinSendHistory record);

    int updateByPrimaryKey(WeixinSendHistory record);
    
    public LayUiTableResultResponse	 selectByQuery(Query query) ;
}
