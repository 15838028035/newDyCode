package com.lj.cloud.secrity.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lj.cloud.secrity.dal.SecGroupsMapper;
import com.lj.cloud.secrity.service.SecGroupsService;
import com.weixindev.micro.serv.common.bean.secrity.SecGroups;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
import com.weixindev.micro.serv.common.vo.TreeBean;
@Service
public class SecGroupsServiceImpl  implements SecGroupsService{
	private Logger logger = LoggerFactory.getLogger(SecGroupsServiceImpl.class);

	@Autowired
	private SecGroupsMapper secGroupsMapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		secGroupsMapper.deleteByPrimaryGroupId(id);
		return secGroupsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SecGroups secGroups){
		return secGroupsMapper.insert(secGroups);
	}

	@Override
	public int insertSelective(SecGroups secGroups) {
		return secGroupsMapper.insertSelective(secGroups);
	}

	@Override
	public SecGroups selectByPrimaryKey(Integer id) {
		return secGroupsMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(SecGroups secGroups) {
		return secGroupsMapper.updateByPrimaryKeySelective(secGroups);
	}

	@Override
	public int updateByPrimaryKey(SecGroups secGroups) {
		return secGroupsMapper.updateByPrimaryKey(secGroups);
	}

	@Override
	 public LayUiTableResultResponse selectByQuery(Query query) {
	        com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<Map<String,Object>> list = secGroupsMapper.selectByPageExample(query);
	        return new LayUiTableResultResponse(result.getTotal(), list);
	}
	
	@Override
	public  List<SecGroups> selectByExample(Query query){
		List<SecGroups> list = secGroupsMapper.selectByExample(query);
		return list;
	}

	@Override
	public List<Map<String, Object>> selectGrouInfoByLogin(String loginNo) {
		
		List<Map<String, Object>> resultList=secGroupsMapper.selectGrouInfoByLogin(loginNo);
		
		return resultList;
	}

	@Override
	public List<Map<String, Object>> selectByInfoKey(Integer id) {
		List<Integer> gids=secGroupsMapper.selectGrouipId(id);//获取改菜单的子菜单id
		
		List<Integer> pids=secGroupsMapper.getParentId();//获取所有的父菜单id
		
		
		
		List<Map<String,Object>> parentInfo=secGroupsMapper.selectGroupInfoByPareintId(pids);//获取fu
		
		for (Map<String, Object> map : parentInfo) {
			 Integer parentId=Integer.valueOf(map.get("id").toString());
			 List<Map<String,Object>> classInfo=secGroupsMapper.getClassInfoByparaenId(parentId);
			 //获取子级信息
			 Map<String,Object> dataMap=this.setGroupInfo(classInfo, gids);
			 boolean flag=Boolean.valueOf(dataMap.get("flag").toString());
			 if(flag) {
				 map.put("parentFlag", true);
			 }else {
				 map.put("parentFlag", false);
			 }
			 map.put("childrenData", dataMap.get("data"));
		}
		
		
		return parentInfo;
		
		
		
//		List<Map<String,Object>> data=secGroupsMapper.selectByInfoKeyData();//查询所有的菜单
//		
//		
//		
//		
//		
//		
//		
//		
////		List<TreeBean> treeList=new ArrayList<TreeBean>();
////		SecGroups grous=new SecGroups();
////		grous.setId(id);
////		List<SecGroups> dataList=secGroupsMapper.selectByExample(grous);//获取所有的菜单
////		List<Integer> gids=secGroupsMapper.selectGrouipId(id);
////		if(null!=dataList&&dataList.size()>0) {
////			for (SecGroups sp : dataList) {
////				TreeBean treeBean=new TreeBean();
////				if(sp.getId()!=0) {
////					treeBean.setId(sp.getId());
////					treeBean.setpId(sp.getParentId());
////					treeBean.setParent(false);
////					treeBean.setLeaf(false);
////					if(gids.contains(sp.getId())) {
////					    treeBean.setChoiseRequir(true);
////					}else {
////						 treeBean.setChoiseRequir(false);
////					}
////					treeBean.setType("菜单1");
////					treeList.add(treeBean);
////				}
////			}
////		}
//		
//		
//		
//		
//		
//		List<Map<String,Object>>	resultList=new ArrayList<Map<String,Object>>();
//		
//		
//		for (Map<String, Object> map : resultList) {
//			if(gids.contains(map.get("id"))) {
//				map.put("flag", "true");
//				
//			}else {
//				map.put("flag", "false");
//			}
//			
//		}
		
	}
private Map<String,Object> setGroupInfo(List<Map<String,Object>> classInfo,List<Integer> gids){
	
	Map<String,Object> map=new HashMap<String, Object>();
	boolean flag=false;
	 if(classInfo!=null&&classInfo.size()>0) {
		 for (Map<String, Object> map2 : classInfo) {
			 if(gids.contains(map2.get("id"))){
				 map2.put("childrenFlag", "true");
				 flag=true;
			 }else {
				 map2.put("childrenFlag", "false");
			 }
		 }
	 }
	 map.put("data",classInfo);
	 map.put("flag", flag);
	 return map;
	 
}
	@Override
	public List<Map<String, Object>> selectByInfoKeyData() {
	   List<Integer> pids=secGroupsMapper.getParentId();//获取所有的父菜单id
		List<Map<String,Object>> parentInfo=secGroupsMapper.selectGroupInfoByPareintId(pids);//获取fu
		for (Map<String, Object> map : parentInfo) {
			 Integer parentId=Integer.valueOf(map.get("id").toString());
			 List<Map<String,Object>> classInfo=secGroupsMapper.getClassInfoByparaenId(parentId);
			 //获取子级信息
			 map.put("childrenData", classInfo);
		}
		return parentInfo;
	}

	@Override
	public void updateByPrimaryKeySelective(Integer id, List<String> urlId,String remarks,String groupName) {
		secGroupsMapper.deleteByPrimaryGroupId(id);
		if(urlId!=null&&urlId.size()>0) {
			SecGroups group=new SecGroups();
			group.setRemarks(remarks);
			group.setGroupName(groupName);;
			group.setId(id);
			secGroupsMapper.updateByPrimaryKeySelective(group);
				for (String rulId : urlId) {
					Integer ruiId=Integer.valueOf(rulId);
					Integer gid=id;
					secGroupsMapper.insertURL(gid,ruiId);
				}
		}
	}

	@Override
	public void addByPrimaryKeySelective(SecGroups secGrops, List<String> urids,Integer gid) {
	System.out.println("------------------------------");
		if(urids!=null&&urids.size()>0) {
			for (String rulId : urids) {
				Integer ruiId=Integer.valueOf(rulId);
				secGroupsMapper.insertURL(gid,ruiId);
			}
	}
		
	}

}
