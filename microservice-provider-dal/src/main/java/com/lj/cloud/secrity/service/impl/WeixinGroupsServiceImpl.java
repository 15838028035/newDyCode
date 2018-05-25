package com.lj.cloud.secrity.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lj.cloud.secrity.dal.WeixinGroupsMapper;
import com.lj.cloud.secrity.dal.WeixinUserinfoMapper;
import com.lj.cloud.secrity.service.WeixinGroupsService;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.weixin.WeixinGroups;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
import com.weixindev.micro.serv.common.util.DateUtil;
@Service
public class WeixinGroupsServiceImpl  implements WeixinGroupsService{
	private Logger logger = LoggerFactory.getLogger(WeixinGroupsServiceImpl.class);

	@Autowired
	private WeixinGroupsMapper weixinGroupsMapper;
	@Autowired
	private WeixinUserinfoMapper weixinUserinfoMapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return weixinGroupsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(WeixinGroups weixinGroups){
		return weixinGroupsMapper.insert(weixinGroups);
	}

	@Override
	public int insertSelective(WeixinGroups weixinGroups) {
		return weixinGroupsMapper.insertSelective(weixinGroups);
	}

	@Override
	public WeixinGroups selectByPrimaryKey(Integer id) {
//		String appId=id;
//		Map<String,Object> map=weixinUserinfoMapper.selectByAppId(appId);
		return weixinGroupsMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(WeixinGroups weixinGroups) {
		return weixinGroupsMapper.updateByPrimaryKeySelective(weixinGroups);
	}

	@Override
	public int updateByPrimaryKey(WeixinGroups weixinGroups) {
		return weixinGroupsMapper.updateByPrimaryKey(weixinGroups);
	}

	@Override
	 public LayUiTableResultResponse selectByQuery(Query query) {
	        com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<Map<String,Object>> list = weixinGroupsMapper.selectByPageExample(query);
	        return new LayUiTableResultResponse(result.getTotal(), list);
	}

	@Override
	public List<WeixinGroups> selectByExample(Query query) {
		List<WeixinGroups> wgList = weixinGroupsMapper.selectByExample(query);
		if (query.get("authorizerAppid") != null) {
			String appId = query.get("authorizerAppid").toString();
			Map<String, Object> map = weixinUserinfoMapper.selectByAppId(appId);
			Integer pid = Integer.valueOf(map.get("parentId").toString());
			Integer id = Integer.valueOf(map.get("groupId").toString());

			if (wgList != null && wgList.size() > 0) {
				for (WeixinGroups weixinGroups : wgList) {
					if (weixinGroups.getParentId() == pid) {
						weixinGroups.setChildflag("true");
						;
					}
					if (weixinGroups.getId() == pid) {
						weixinGroups.setParentflag("true");
					}
				}
			}

		}
		
		return wgList;
	}

	@Transactional
	@Override
	public RestAPIResult2 tranDelete(Integer id, Integer updateBy,String updateByUname) {
		 RestAPIResult2 restAPIResult = new RestAPIResult2();
			restAPIResult.setRespCode(1);
			restAPIResult.setRespMsg("操作成功");
			
			if(id!=null && id.intValue()<=1){
				restAPIResult.setRespCode(0);
				restAPIResult.setRespMsg("默认分组不允许删除");
				return restAPIResult;
			}
			
			Map<String,Object> params =new HashMap<String,Object>();
			params.put("parentId", id);
			Query query= new Query(params);
			
			 List<WeixinGroups> secGroupsList = selectByExample(query);
			 
			 if(secGroupsList!=null && secGroupsList.size()>=1){
				 restAPIResult.setRespCode(0);
				 restAPIResult.setRespMsg("删除失败,当前分组下有"+secGroupsList.size()+"个分组,请先删除子分组");
				 return restAPIResult;
			 }
			 
			 Map<String, Object> map = new HashMap<String,Object>();
			 map.put("weixinGroupsId",id);
			 map.put("weixinGroupsRoootId",1);//默认分组ID 为1
			 map.put("updateDate", DateUtil.getNowDateYYYYMMddHHMMSS());
			 map.put("updateBy",updateBy);
			 map.put("updateByUname",updateByUname);
			 
			 weixinUserinfoMapper.updateByPrimaryByWeixinGroupsId(map);
			 
			 deleteByPrimaryKey(id);
			return restAPIResult;		
	}

	@Override
	public List<WeixinGroups> selectForMovie(Query query) {
		return weixinGroupsMapper.selectForMovie(query);
	}

	@Override
	public List<Map<String, Object>> selectByPageExample(Object mapAndObject) {
		return weixinGroupsMapper.selectByPageExample(mapAndObject);
	}
	
}
