package com.lj.cloud.secrity.service;

import java.util.List;

import com.weixindev.micro.serv.common.bean.weixin.WeixinMenuItem;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;


public interface WeixinMenuItemService  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinMenuItem weixinMenuItem);

    int insertSelective(WeixinMenuItem weixinMenuItem);

    WeixinMenuItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinMenuItem weixinMenuItem);

    int updateByPrimaryKey(WeixinMenuItem weixinMenuItem);

    public LayUiTableResultResponse	 selectByQuery(Query query) ;

    public  List<WeixinMenuItem> selectByExample(Query query);
}
