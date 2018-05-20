package com.lj.cloud.secrity.service;

import java.util.List;

import com.weixindev.micro.serv.common.bean.weixin.WeixinImgGroups;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;


public interface WeixinImgGroupsService  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinImgGroups weixinImgGroups);

    int insertSelective(WeixinImgGroups weixinImgGroups);

    WeixinImgGroups selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinImgGroups weixinImgGroups);

    int updateByPrimaryKey(WeixinImgGroups weixinImgGroups);

    public LayUiTableResultResponse	 selectByQuery(Query query) ;

    public  List<WeixinImgGroups> selectByExample(Query query);
}
