package com.lj.cloud.secrity.service;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.weixin.WeixinGroups;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;


public interface WeixinGroupsService  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinGroups weixinGroups);

    int insertSelective(WeixinGroups weixinGroups);

    WeixinGroups selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinGroups weixinGroups);

    int updateByPrimaryKey(WeixinGroups weixinGroups);

    public LayUiTableResultResponse	 selectByQuery(Query query) ;
    
    public  List<WeixinGroups> selectByExample(Query query);
    
    /**
     * 删除分组
     * @param id
     * @param updateBy
     * @param updateByUname
     * @return
     */
	public RestAPIResult2 tranDelete(Integer id, Integer updateBy,String updateByUname) ;
	
	
	/**
	 * 移动
	 * @param query
	 * @return
	 */
	 public  List<WeixinGroups> selectForMovie(Query query);
	 
	  /**
	     * 根据条件查询列表
	     *
	     * @param example
	     */
	    List<Map<String,Object>> selectByPageExample(Object mapAndObject);
}
