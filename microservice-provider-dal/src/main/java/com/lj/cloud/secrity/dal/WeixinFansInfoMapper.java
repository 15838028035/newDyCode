package com.lj.cloud.secrity.dal;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.report.WeixinFansInfo;


public interface WeixinFansInfoMapper  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinFansInfo weixinFansInfo);

    int insertSelective(WeixinFansInfo weixinFansInfo);

    WeixinFansInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinFansInfo weixinFansInfo);

    int updateByPrimaryKey(WeixinFansInfo weixinFansInfo);

   

   /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<WeixinFansInfo> selectByExample(Object mapAndObject);

   /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<Map<String,Object>> selectByPageExample(Object mapAndObject);
    
    int insertBatch(List<WeixinFansInfo> list);
    
    List<Map<String,Object>> selectPosition(Map<String,Object> map);
    
    List<Map<String,Object>> selectPositionByUid(Map<String,Object> map);

	Integer selecCountAllFans();
}
