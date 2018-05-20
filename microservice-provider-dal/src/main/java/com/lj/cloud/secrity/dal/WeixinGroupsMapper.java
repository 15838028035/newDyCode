package com.lj.cloud.secrity.dal;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.weixin.WeixinGroups;
import com.weixindev.micro.serv.common.pagination.Query;


public interface WeixinGroupsMapper  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinGroups weixinGroups);

    int insertSelective(WeixinGroups weixinGroups);

    WeixinGroups selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinGroups weixinGroups);

    int updateByPrimaryKey(WeixinGroups weixinGroups);
    
    /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<WeixinGroups> selectByExample(Object mapAndObject);
    
    /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<Map<String,Object>> selectByPageExample(Object mapAndObject);
    
	/**
	 * 移动
	 * @param query
	 * @return
	 */
	 public  List<WeixinGroups> selectForMovie(Query query);
}
