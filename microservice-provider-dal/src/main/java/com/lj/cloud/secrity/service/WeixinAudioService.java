package com.lj.cloud.secrity.service;

import java.util.List;

import com.weixindev.micro.serv.common.bean.weixin.WeixinAudio;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;


public interface WeixinAudioService  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinAudio weixinAudio);

    int insertSelective(WeixinAudio weixinAudio);

    WeixinAudio selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinAudio weixinAudio);

    int updateByPrimaryKey(WeixinAudio weixinAudio);

    public LayUiTableResultResponse	 selectByQuery(Query query) ;

    public  List<WeixinAudio> selectByExample(Query query);
}
