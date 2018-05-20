package com.lj.cloud.secrity.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lj.cloud.secrity.bean.CommonTree;
import com.lj.cloud.secrity.constant.SecrityCommonConstant;
import com.lj.cloud.secrity.service.WeixinGroupsService;
import com.lj.cloud.secrity.service.WeixinUserinfoService;
import com.lj.cloud.secrity.util.secGroupAndUserUtil;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.secrity.SecAdminUser;
import com.weixindev.micro.serv.common.bean.weixin.WeixinGroups;
import com.weixindev.micro.serv.common.bean.weixin.WeixinUserinfo;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
import com.weixindev.micro.serv.common.util.DateUtil;
import com.weixindev.micro.serv.common.util.StringUtil;
import com.weixindev.micro.serv.common.util.TreeUtil;
import com.weixindev.micro.serv.common.vo.LayuiXtree;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *管理
 */
@Api(value = "公众账号分组服务", tags = "公众账号分组服务接口")
@RestController()
public class WeixinGroupsController extends BaseController{
	@Autowired
	private WeixinGroupsService weixinGroupsService;
	
	@Autowired
	private WeixinUserinfoService weixinUserinfoService;
	
	@ApiOperation(value = "分页")
	 @RequestMapping(value = "/api/WeixinGroups", method = RequestMethod.GET)
	  public LayUiTableResultResponse page(@RequestParam(defaultValue = "10") int limit,
	      @RequestParam(defaultValue = "1") int offset,String name,@RequestParam Map<String, Object> params) {
			Query query= new Query(params);
			LayUiTableResultResponse LayUiTableResultResponse=   weixinGroupsService.selectByQuery(query);
			return LayUiTableResultResponse;
	}
	 
	 /** 新增   */
	@ApiOperation(value = "新增")
		@RequestMapping(value = "/api/WeixinGroups",method=RequestMethod.POST)
		public RestAPIResult2 create(WeixinGroups weixinGroups)  {
			 RestAPIResult2 restAPIResult = new RestAPIResult2();
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("操作成功");
				try {
					Integer createBy = getLoginId();
					weixinGroups.setCreateBy(createBy);
					weixinGroups.setCreateByUname(getUserName());
					weixinGroups.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					
					Integer parentId =  weixinGroups.getParentId();
					WeixinGroups weixinGroupsParent =weixinGroupsService.selectByPrimaryKey(parentId);
					if(weixinGroupsParent==null){
						weixinGroups.setLevel(0);
					}else{
					//计算父ID
					weixinGroups.setLevel(weixinGroupsParent.getLevel()+1);
					}
					
					if(weixinGroups.getLevel()>=3){
						restAPIResult.setRespCode(0);
						restAPIResult.setRespMsg("不允许添加下级 ");
						return restAPIResult;
					}
					
					Map<String, Object> params = new HashMap<String,Object>();
					params.put("groupName", weixinGroups.getGroupName());
					Query query = new Query(params);
					
					List<WeixinGroups> WeixinGroupsList = weixinGroupsService.selectByExample(query);
					
					if(WeixinGroupsList!=null&& WeixinGroupsList.size()>0){
						restAPIResult.setRespCode(0);
						restAPIResult.setRespMsg("分组名称已经存在,不允许添加");
						return restAPIResult;
					}
					weixinGroupsService.insertSelective(weixinGroups);
					
				}catch(Exception e) {
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg("失败成功:"+e.getMessage());
				}
				
				return restAPIResult;
	}
	 
	 /** 保存更新  */
	@ApiOperation(value = "修改")
		@RequestMapping(value="/api/WeixinGroups/{id}",method=RequestMethod.PUT)
		public RestAPIResult2 update(@PathVariable("id") Integer id ,WeixinGroups weixinGroups)  {
			 RestAPIResult2 restAPIResult = new RestAPIResult2();
				restAPIResult.setRespCode(1);
				restAPIResult.setRespMsg("操作成功");
				try {
					
					Integer createBy = getLoginId();
					weixinGroups.setUpdateBy(createBy);
					weixinGroups.setUpdateByUname(getUserName());
					weixinGroups.setUpdateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
					
					Integer parentId =  weixinGroups.getParentId();
					WeixinGroups weixinGroupsParent =weixinGroupsService.selectByPrimaryKey(parentId);
					
					if(weixinGroupsParent==null){
						weixinGroups.setLevel(0);
					}else{
					//计算父ID
					weixinGroups.setLevel(weixinGroupsParent.getLevel()+1);
					}
					
					if(weixinGroups.getLevel()>=3){
						restAPIResult.setRespCode(0);
						restAPIResult.setRespMsg("不允许添加下级 ");
						return restAPIResult;
					}
					
					Map<String, Object> params = new HashMap<String,Object>();
					params.put("groupName", weixinGroups.getGroupName());
					params.put("keyword", weixinGroups.getId());
					
					Query query = new Query(params);
					
					 List<Map<String,Object>> list = weixinGroupsService.selectByPageExample(query);
					
					if(list!=null&& list.size()>0){
						restAPIResult.setRespCode(0);
						restAPIResult.setRespMsg("分组名称已经存在,不允许添加");
						return restAPIResult;
					}
					
					
					weixinGroupsService.updateByPrimaryKeySelective(weixinGroups);
					
				}catch(Exception e) {
					restAPIResult.setRespCode(0);
					restAPIResult.setRespMsg("失败成功:"+e.getMessage());
				}
				
				return restAPIResult;
	}
		
	/** 显示 */
	@ApiOperation(value = "显示")
	@RequestMapping(value="/api/WeixinGroups/{id}", method = RequestMethod.GET)
	public WeixinGroups show(@PathVariable("id") Integer id) throws Exception {
		WeixinGroups weixinGroups =weixinGroupsService.selectByPrimaryKey(id);
		if(weixinGroups== null) {
			weixinGroups = new WeixinGroups();
		}
		return weixinGroups;
	}
		
	/** 删除 */
	@ApiOperation(value = "物理删除，删除分组后，该组用户转移到默认分组")
	@RequestMapping(value="/api/WeixinGroups/{id}",method=RequestMethod.DELETE)
	public RestAPIResult2 delete(@PathVariable("id") Integer id) {
		 RestAPIResult2 restAPIResult =  weixinGroupsService.tranDelete(id, getLoginId(), getUserName());
		return restAPIResult;
	}
	
	/** 显示 */
	@ApiOperation(value = "显示")
	@RequestMapping(value="/api/WeixinGroups/showInfo/{id}", method = RequestMethod.GET)
	public Map<String,Object> showInfo(@PathVariable("id") Integer id) throws Exception {
		Map<String,Object> retMap =new HashMap<String,Object>();
		
		WeixinGroups weixinGroups =weixinGroupsService.selectByPrimaryKey(id);
		if(weixinGroups== null) {
			weixinGroups = new WeixinGroups();
		}
		
		Map<String,Object> params =new HashMap<String,Object>();
		params.put("id", id);
		Query query= new Query(params);
		
		 List<WeixinGroups> secGroupsList = weixinGroupsService.selectForMovie(query);
		 if(secGroupsList==null){
			 secGroupsList = new ArrayList<WeixinGroups>();
		 }
		 
		retMap.put("weixinGroups", weixinGroups);
		retMap.put("secGroupsList", secGroupsList);
		return retMap;
	}
	
	/**
	*json 树数据
	*/
	@ApiOperation(value = "layui树")
	@RequestMapping(value="/api/WeixinGroups/tree/{extId}", method = RequestMethod.GET)
	public List<Map<String, Object>>  treeData(@PathVariable("extId") String extId) throws Exception {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		Map<String,Object> params =new HashMap<String,Object>();
		Query query= new Query(params);
		List<WeixinGroups> list = weixinGroupsService.selectByExample(query);
		Map<Integer, Integer> weixingroupUsercounts = new HashMap<>();
		weixingroupUsercounts.put(-1, 0);
		for (int i=0; i<list.size(); i++){
			WeixinGroups e = list.get(i);
			if (StringUtil.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && String.valueOf(e.getParentId()).indexOf(","+extId+",")==-1)){
				if (e.getLevel() == 1) {
					weixingroupUsercounts.put(-1,weixingroupUsercounts.get(-1) + e.getCount());
				}
				if (e.getLevel() == 2) {
					if (weixingroupUsercounts.get(e.getParentId()) == null) {
						weixingroupUsercounts.put(e.getParentId(), e.getCount());
					}
					else {
						weixingroupUsercounts.put(e.getParentId(),weixingroupUsercounts.get(e.getParentId()) + e.getCount());
					}
					weixingroupUsercounts.put(-1,weixingroupUsercounts.get(-1) + e.getCount());
				}
			}
		}
		
		System.out.println("-----------------------------------------------");
		System.out.println(weixingroupUsercounts);
		System.out.println("-----------------------------------------------");
		
		for (int i=0; i<list.size(); i++){
			WeixinGroups e = list.get(i);
			if (StringUtil.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && String.valueOf(e.getParentId()).indexOf(","+extId+",")==-1)){
				Map<String, Object> map = new HashMap<String,Object>();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("groupName", e.getGroupName());
				map.put("remarks", e.getRemarks());
				map.put("createByUname", e.getCreateByUname());
				map.put("createDate", e.getCreateDate());
				map.put("parentId", e.getParentId());
				map.put("level", e.getLevel());
				if (e.getLevel() == 1 || e.getLevel() == 0) {
					if (weixingroupUsercounts.get(e.getId()) == null) {
						weixingroupUsercounts.put(e.getId(),0);
					}
					Integer count = 0;
					if (e.getCount() != null) {
						count = e.getCount();
					}
					map.put("count", count+weixingroupUsercounts.get(e.getId()));
				}
				else{
					map.put("count", e.getCount());
				}
				mapList.add(map);
			}
		}
		
		System.out.println("-----------------------------------------------");
		System.out.println(mapList);
		System.out.println("-----------------------------------------------");

		return mapList;
	}
	
	@ApiOperation(value = "ztree树")
	  @RequestMapping(value = "/api/WeixinGroups/tree", method = RequestMethod.GET)
	    public List<CommonTree> tree(@RequestParam Map<String, Object> params) {
	        Query query= new Query(params);
	        return  getTree(weixinGroupsService.selectByExample(query), SecrityCommonConstant.ROOT);
	    }

	    private List<CommonTree> getTree(List<WeixinGroups> groups,int root) {
	        List<CommonTree> trees = new ArrayList<CommonTree>();
	        CommonTree node = null;
	        for (WeixinGroups group : groups) {
	            node = new CommonTree();
	            node.setLabel(group.getGroupName());
	            node.setName(group.getGroupName());
	            BeanUtils.copyProperties(group, node);
	            trees.add(node);
	        }
	        return TreeUtil.bulid(trees,root) ;
	    }
	    
    	@ApiOperation(value = "动态查询列表")
	    @RequestMapping(value = "/api/WeixinGroups/list", method = RequestMethod.GET)
	    public List<WeixinGroups> list(@RequestParam Map<String, Object> params) {
    	 Query query= new Query(params);
    	 List<WeixinGroups> secGroupsList = weixinGroupsService.selectByExample(query);
		 if(secGroupsList==null){
			 secGroupsList = new ArrayList<WeixinGroups>();
		 }
		 return secGroupsList;
	    }
    	
    	@ApiOperation(value = "layuiXtree树")
  	  @RequestMapping(value = "/api/WeixinGroups/layuiXtree", method = RequestMethod.GET)
  	    public List<LayuiXtree> layuiXtree(@RequestParam Map<String, Object> params) {
  	        Query query= new Query(params);
  	      //  return  getLayuiXtre(weixinGroupsService.selectByExample(query), SecrityCommonConstant.ROOT);
  	        
  	      List<LayuiXtree> secGroupsAndUsers = new ArrayList<LayuiXtree>();
          List<WeixinGroups> weixinGroupsList =weixinGroupsService.selectByExample(query);
          
          if(weixinGroupsList!=null){
          	for (WeixinGroups weixinGroups : weixinGroupsList) {
          		
          		LayuiXtree layuiXtree = new LayuiXtree();
          		layuiXtree.setTitle(weixinGroups.getGroupName());
          		layuiXtree.setValue("gro_"+weixinGroups.getId().toString());
          		
          		Map<String,Object> params2 = new HashMap<String, Object>();
              	params2.put("weixinGroupsId", weixinGroups.getId());
              	Query query2 = new Query(params2);
  				List<WeixinUserinfo> weixinUserinfoList = weixinUserinfoService.selectByExample(query2);
  				
  				if (weixinUserinfoList!=null) {
  					List<LayuiXtree> layuiXtreeUserList = new ArrayList<LayuiXtree>();
  					for (WeixinUserinfo weixinUserinfo : weixinUserinfoList) {
  						LayuiXtree layuiXtreeUser = new LayuiXtree();
  						layuiXtreeUser.setTitle(weixinUserinfo.getNickName());
  						layuiXtreeUser.setValue("uid_"+weixinUserinfo.getId().toString());
  					/*	List<LayuiXtree> LayuiXtreeNull = new ArrayList<LayuiXtree>();
  						layuiXtreeUser.setData(LayuiXtreeNull);*/
  						
  						layuiXtreeUserList.add(layuiXtreeUser);
  					}
  					
  					layuiXtree.setData(layuiXtreeUserList);
  				}
  				
  				secGroupsAndUsers.add(layuiXtree);
  			}
          }
          return secGroupsAndUsers;
  	    }
	
	/*  private List<LayuiXtree> getLayuiXtre(List<WeixinGroups> groups,int root) {
        List<LayuiXtree> trees = new ArrayList<LayuiXtree>();
        LayuiXtree node = null;
        for (WeixinGroups group : groups) {
            node = new LayuiXtree();
          node.setTitle(group.getGroupName());
            node.setValue(String.valueOf(group.getId()));
          node.setParentId(String.valueOf(group.getParentId()));
            trees.add(node);
        }
        return TreeUtil.bulidXtree(trees,root) ;
    }*/
}

