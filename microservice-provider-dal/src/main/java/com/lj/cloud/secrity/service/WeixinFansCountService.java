package com.lj.cloud.secrity.service;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.report.WeixinFansCount;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;


public interface WeixinFansCountService  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinFansCount weixinFansCount);

    int insertSelective(WeixinFansCount weixinFansCount);

    WeixinFansCount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinFansCount weixinFansCount);

    int updateByPrimaryKey(WeixinFansCount weixinFansCount);

    public LayUiTableResultResponse	 selectByQuery(Query query) ;

    public  List<WeixinFansCount> selectByExample(Query query);
    
    public String selectNextOpenidByNikename(String nikeName);
    
    public String selectNextOpenidByUserId(Integer userId);
    public List<Map<String,Object>> selectByUserIdAndTime(Map<String,Object> map);

}
