package com.lj.cloud.secrity.service;

import java.util.List;

import com.weixindev.micro.serv.common.bean.weixin.WeixinImgtext;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;


public interface WeixinImgtextService  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinImgtext weixinImgtext);

    int insertSelective(WeixinImgtext weixinImgtext);

    WeixinImgtext selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinImgtext weixinImgtext);

    int updateByPrimaryKey(WeixinImgtext weixinImgtext);

    public LayUiTableResultResponse	 selectByQuery(Query query) ;

    public  List<WeixinImgtext> selectByExample(Query query);
}
