package com.lj.cloud.secrity.service;

import java.util.List;

import com.weixindev.micro.serv.common.bean.weixin.WeixinArticleTask;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;


public interface WeixinArticleTaskService  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinArticleTask weixinArticleTask);

    int insertSelective(WeixinArticleTask weixinArticleTask);

    WeixinArticleTask selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinArticleTask weixinArticleTask);

    int updateByPrimaryKey(WeixinArticleTask weixinArticleTask);

    public LayUiTableResultResponse	 selectByQuery(Query query) ;

    public  List<WeixinArticleTask> selectByExample(Query query);
}
