package com.lj.cloud.secrity.service;

import java.util.List;

import com.weixindev.micro.serv.common.bean.weixin.WeixinMenuGroups;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;


public interface WeixinMenuGroupsService  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinMenuGroups weixinMenuGroups);

    int insertSelective(WeixinMenuGroups weixinMenuGroups);

    WeixinMenuGroups selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinMenuGroups weixinMenuGroups);

    int updateByPrimaryKey(WeixinMenuGroups weixinMenuGroups);

    public LayUiTableResultResponse	 selectByQuery(Query query) ;

    public  List<WeixinMenuGroups> selectByExample(Query query);
}
