package com.lj.cloud.secrity.service;

import java.util.List;

import com.weixindev.micro.serv.common.bean.weixin.WeixinVideo;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;


public interface WeixinVideoService  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinVideo weixinVideo);

    int insertSelective(WeixinVideo weixinVideo);

    WeixinVideo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinVideo weixinVideo);

    int updateByPrimaryKey(WeixinVideo weixinVideo);

    public LayUiTableResultResponse	 selectByQuery(Query query) ;

    public  List<WeixinVideo> selectByExample(Query query);

	void deleteByIds(Integer  []ids);
	
}
