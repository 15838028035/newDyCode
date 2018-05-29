package com.lj.cloud.secrity.service;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.report.WeixinFansInfo;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;


public interface WeixinFansInfoService  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinFansInfo weixinFansInfo);

    int insertSelective(WeixinFansInfo weixinFansInfo);

    WeixinFansInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinFansInfo weixinFansInfo);

    int updateByPrimaryKey(WeixinFansInfo weixinFansInfo);

    public LayUiTableResultResponse	 selectByQuery(Query query) ;

    public  List<WeixinFansInfo> selectByExample(Query query);
    public int insertBatch(List<WeixinFansInfo> list);
    
    public List<Map<String,Object>> selectPosition(Map<String,Object> map);
    
    List<Map<String,Object>> selectPositionByUid(Map<String,Object> map);
    
    public Integer selecCountAllFans();
}
