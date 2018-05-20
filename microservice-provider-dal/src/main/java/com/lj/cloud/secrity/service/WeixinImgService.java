package com.lj.cloud.secrity.service;

import java.util.List;

import com.weixindev.micro.serv.common.bean.weixin.WeixinImg;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;


public interface WeixinImgService  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinImg weixinImg);

    int insertSelective(WeixinImg weixinImg);

    WeixinImg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinImg weixinImg);

    int updateByPrimaryKey(WeixinImg weixinImg);

    public LayUiTableResultResponse	 selectByQuery(Query query) ;

    public  List<WeixinImg> selectByExample(Query query);
}
