package com.lj.cloud.secrity.service;

import java.util.List;

import com.weixindev.micro.serv.common.bean.weixin.WeixinImgtextItem;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;


public  interface WeixinImgtextItemService  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinImgtextItem weixinImgtextItem);

    int insertSelective(WeixinImgtextItem weixinImgtextItem);

    WeixinImgtextItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinImgtextItem weixinImgtextItem);

    int updateByPrimaryKey(WeixinImgtextItem weixinImgtextItem);

    public LayUiTableResultResponse	 selectByQuery(Query query) ;

    public  List<WeixinImgtextItem> selectByExample(Query query);
       
	 public LayUiTableResultResponse selectByQuery2(Query query) ;
	 
	public  int batchDelete(String id);

	Integer deleteByTmpId(String tempId);

	public void   moveFromAToB(Integer a, Integer b);


	List<WeixinImgtextItem> selectByTemplateimdId(Integer templateid);
	 
}
