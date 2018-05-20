package com.lj.cloud.secrity.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.weixin.WeixinUserinfo;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;


public interface WeixinUserinfoService  {

    int deleteByPrimaryKey(String weixininfoid);

    int insert(WeixinUserinfo weixinUserinfo);

    int insertSelective(WeixinUserinfo weixinUserinfo);

    WeixinUserinfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinUserinfo weixinUserinfo);

    int updateByPrimaryKey(WeixinUserinfo weixinUserinfo);

    public LayUiTableResultResponse	 selectByQuery(Query query) ;

    public  List<WeixinUserinfo> selectByExample(Query query);
    
    /**
     * map更新
     * @param map
     * @return
     */
    int updateByPrimaryByWeixinGroupsId(Map<String,Object> map);

	public HashMap<String, Object> selectByAppId(String appId);
	
	int WeixinGroupsBatchMove(String movieUseIds, Integer weixinGroupsId);

	List selectByTyinfo(String infotype);

	int deleteByPrimaryKey(Integer id);

	int updateByAppIdSelective(WeixinUserinfo weixinUserinfo);
	
	String selectAuthorizerAppidByPrimaryKey(int id);
	public WeixinUserinfo selectByauthorizerAppid(String authorizerAppid);
}
