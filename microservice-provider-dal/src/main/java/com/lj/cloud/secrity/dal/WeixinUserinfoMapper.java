package com.lj.cloud.secrity.dal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.weixindev.micro.serv.common.bean.weixin.WeixinUserinfo;


public interface WeixinUserinfoMapper  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinUserinfo weixinUserinfo);

    int insertSelective(WeixinUserinfo weixinUserinfo);

    WeixinUserinfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinUserinfo weixinUserinfo);
    
    int updateByAppIdSelective(WeixinUserinfo weixinUserinfo);

    int updateByPrimaryKey(WeixinUserinfo weixinUserinfo);

   

    List<WeixinUserinfo> selectByExample(Object mapAndObject);
    /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<Map<String,Object>> selectByPageExample(Object mapAndObject);
    
    /**
     * map更新
     * @param map
     * @return
     */
    int updateByPrimaryByWeixinGroupsId(Map<String,Object> map);

	HashMap<String, Object> selectByAppId(String appId);

	int WeixinGroupsBatchMove(@Param("movieUseIds")String movieUseIds,@Param("weixinGroupsId")Integer weixinGroupsId);

	List selectByTyinfo(String infotype);
	
	String selectAuthorizerAppidByPrimaryKey(int id);
	Integer selectIdByName(String userName);
}
