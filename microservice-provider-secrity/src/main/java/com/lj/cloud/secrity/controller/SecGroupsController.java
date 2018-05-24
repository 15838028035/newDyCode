package com.lj.cloud.secrity.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.support.json.JSONUtils;
import com.lj.cloud.secrity.bean.SecGroupsTree;
import com.lj.cloud.secrity.constant.SecrityCommonConstant;
import com.lj.cloud.secrity.service.SecAdminUserService;
import com.lj.cloud.secrity.service.SecGroupsService;
import com.lj.cloud.secrity.util.secGroupAndUserUtil;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.secrity.SecAdminUser;
import com.weixindev.micro.serv.common.bean.secrity.SecGroups;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
import com.weixindev.micro.serv.common.util.DateUtil;
import com.weixindev.micro.serv.common.util.TreeUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tk.mybatis.mapper.util.StringUtil;

/**
 *管理员分组管理
 */
@Api(value = "管理员分组服务", tags = "管理员分组服务接口")
@RestController
public class SecGroupsController extends BaseController{

    protected Logger logger = LoggerFactory.getLogger(SecGroupsController.class);
    @Autowired
    private SecGroupsService secGroupsService;
    
    @Autowired
	private SecAdminUserService secAdminUserService;

    @ApiOperation(value = "获取组名信息")
    @RequestMapping(value="/api/SecGroupInfo", method = RequestMethod.GET)
    public RestAPIResult2 GroupInfo() throws Exception {
    	  RestAPIResult2 restAPIResult = new RestAPIResult2();
        List<SecGroups> secGroups =secGroupsService.selectByExample(null);
        
        restAPIResult.setRespCode(1);
        restAPIResult.setRespMsg("操作成功");
        try {
     	   if(null!=secGroups&&secGroups.size()>0&&secGroups.get(0)!=null) {
     		   restAPIResult.setRespData(secGroups);
     		   
     	   }else {
     		   restAPIResult.setRespCode(0);
     	       restAPIResult.setRespMsg("操作成功");
     	   }
        }catch(Exception e) {
            restAPIResult.setRespCode(0);
            restAPIResult.setRespMsg("失败成功:"+e.getMessage());
        }

        return restAPIResult;
    }
    @ApiOperation(value = "获取所有的菜单信息")
    @RequestMapping(value="/api/SecGroupData", method = RequestMethod.GET)
    public RestAPIResult2 showInfo() throws Exception {
    	  RestAPIResult2 restAPIResult = new RestAPIResult2();
        List<Map<String,Object>> secGroups =secGroupsService.selectByInfoKeyData();
        
        restAPIResult.setRespCode(1);
        restAPIResult.setRespMsg("操作成功");
        try {
     	   if(null!=secGroups&&secGroups.size()>0&&secGroups.get(0)!=null) {
     		   restAPIResult.setRespData(secGroups);
     		   
     	   }else {
     		   restAPIResult.setRespCode(0);
     	       restAPIResult.setRespMsg("操作成功");
     	   }
        }catch(Exception e) {
            restAPIResult.setRespCode(0);
            restAPIResult.setRespMsg("失败成功:"+e.getMessage());
        }

        return restAPIResult;
    }
    /** 显示 */
    @ApiOperation(value = "获取管理员下的菜单信息")
    @RequestMapping(value="/api/SecGroupData/{id}", method = RequestMethod.GET)
    public RestAPIResult2 showInfo(@PathVariable("id") Integer id) throws Exception {
    	  RestAPIResult2 restAPIResult = new RestAPIResult2();
        List<Map<String,Object>> secGroups =secGroupsService.selectByInfoKey(id);
        restAPIResult.setRespCode(1);
        restAPIResult.setRespMsg("操作成功");
        try {
     	   if(null!=secGroups&&secGroups.size()>0&&secGroups.get(0)!=null) {
     		   restAPIResult.setRespData(secGroups);
     		   
     	   }else {
     		   restAPIResult.setRespCode(0);
     	       restAPIResult.setRespMsg("操作成功");
     	   }
        }catch(Exception e) {
            restAPIResult.setRespCode(0);
            restAPIResult.setRespMsg("失败成功:"+e.getMessage());
        }

        return restAPIResult;
    }
    
    /**
     * 根据登录用户名称获取用户的菜单信息
     * @param login
     * @return
     */
    @ApiOperation(value = "获取用户菜单信息")
    @RequestMapping(value = "/api/SecGroupsInfo", method = RequestMethod.GET)
    public RestAPIResult2 getGroupInfoByLoginNo(String loginNo)  {
        RestAPIResult2 restAPIResult = new RestAPIResult2();
       List<Map<String,Object>> groupdata=secGroupsService.selectGrouInfoByLogin(loginNo);
       restAPIResult.setRespCode(1);
       restAPIResult.setRespMsg("操作成功");
       try {
    	   if(null!=groupdata&&groupdata.size()>0&&groupdata.get(0)!=null) {
    		   restAPIResult.setRespData(JSONUtils.toJSONString(groupdata));
    		   
    	   }else {
    		   restAPIResult.setRespCode(0);
    	       restAPIResult.setRespMsg("操作成功");
    	   }
       }catch(Exception e) {
           restAPIResult.setRespCode(0);
           restAPIResult.setRespMsg("失败成功:"+e.getMessage());
       }

       return restAPIResult;
    }
    
    @ApiOperation(value = "分页")
    @RequestMapping(value = "/api/SecGroups", method = RequestMethod.GET)
    public LayUiTableResultResponse page(@RequestParam(defaultValue = "10") int limit,
                                         @RequestParam(defaultValue = "1") int offset,String name, @RequestParam Map<String, Object> params) {
        Query query= new Query(params);
        LayUiTableResultResponse LayUiTableResultResponse=   secGroupsService.selectByQuery(query);
        return LayUiTableResultResponse;
    }

    /** 新增   */
    @ApiOperation(value = "新增")
    @RequestMapping(value = "/api/SecGroups",method=RequestMethod.POST)
    public RestAPIResult2 create(SecGroups secGroups)  {
        RestAPIResult2 restAPIResult = new RestAPIResult2();
        restAPIResult.setRespCode(1);
        restAPIResult.setRespMsg("操作成功");
        try {
            Integer createBy = getLoginId();
            secGroups.setCreateBy(createBy);
            secGroups.setCreateByUname(getUserName());
            secGroups.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
            secGroupsService.insertSelective(secGroups);

        }catch(Exception e) {
            restAPIResult.setRespCode(0);
            restAPIResult.setRespMsg("失败成功:"+e.getMessage());
        }

        return restAPIResult;
    }
    
    @ApiOperation(value = "新增")
    @RequestMapping(value = "/api/SecGroupsAdd",method=RequestMethod.POST)
    public RestAPIResult2 create2(@ModelAttribute SecGroups secGroups)  {
        RestAPIResult2 restAPIResult = new RestAPIResult2();
        restAPIResult.setRespCode(1);
        restAPIResult.setRespMsg("操作成功");
        try {
        	if(StringUtil.isEmpty(secGroups.getGroupName())) {
        		 restAPIResult.setRespCode(0);
                 restAPIResult.setRespMsg("分组名称不为空");
                 return restAPIResult;
        	}
        	/*if(StringUtil.isEmpty(secGroups.getRemarks())) {
       		 restAPIResult.setRespCode(0);
                restAPIResult.setRespMsg("描述不为空");
                return restAPIResult;
       	     }*/
        	String data =DateUtil.getNowDateYYYYMMddHHMMSS();
        	secGroups.setCreateDate(data);
        	String str[] = secGroups.getUrids().split(",");
        	secGroupsService.insert(secGroups);
        	Integer gid=secGroups.getId();
        	secGroupsService.addByPrimaryKeySelective(secGroups,Arrays.asList(str),gid);

        }catch(Exception e) {
            restAPIResult.setRespCode(0);
            restAPIResult.setRespMsg("失败:"+e.getMessage());
        }

        return restAPIResult;
    }
    /** 保存更新  */
    @ApiOperation(value = "修改权限")
    @RequestMapping(value="/api/SecGroupsUrl/{id}",method=RequestMethod.PUT)
    public RestAPIResult2 update(@PathVariable("id") Integer id ,String urids,String remarks,String groupName)  {
        RestAPIResult2 restAPIResult = new RestAPIResult2();
        restAPIResult.setRespCode(1);
        restAPIResult.setRespMsg("操作成功");
        try {
        	String str[] = urids.split(",");
//        	return 
        	secGroupsService.updateByPrimaryKeySelective(id, Arrays.asList(str),remarks,groupName);
        	

        }catch(Exception e) {
            restAPIResult.setRespCode(0);
            restAPIResult.setRespMsg("失败:"+e.getMessage());
        }

        return restAPIResult;
    }
    /** 保存更新  */
    @ApiOperation(value = "修改")
    @RequestMapping(value="/api/SecGroups/{id}",method=RequestMethod.PUT)
    public RestAPIResult2 update(@PathVariable("id") Integer id ,SecGroups secGroups)  {
        RestAPIResult2 restAPIResult = new RestAPIResult2();
        restAPIResult.setRespCode(1);
        restAPIResult.setRespMsg("操作成功");
        try {

        	if(secGroups.getId()==null){
        		secGroups.setId(id);
			}
        	
            Integer createBy = getLoginId();
            secGroups.setUpdateBy(createBy);
            secGroups.setUpdateByUname(getUserName());
            secGroups.setUpdateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
            secGroupsService.updateByPrimaryKeySelective(secGroups);

        }catch(Exception e) {
            restAPIResult.setRespCode(0);
            restAPIResult.setRespMsg("失败成功:"+e.getMessage());
        }

        return restAPIResult;
    }

    /** 显示 */
    @ApiOperation(value = "显示")
    @RequestMapping(value="/api/SecGroups/{id}", method = RequestMethod.GET)
    public SecGroups show(@PathVariable("id") Integer id) throws Exception {
        SecGroups secGroups =secGroupsService.selectByPrimaryKey(id);
        if(secGroups==null){
        	secGroups = new SecGroups();
        }
        return secGroups;
    }
    
    /** 树状显示 */
    @ApiOperation(value = "树状显示")
    @RequestMapping(value="/api/weixinSecGroupusers", method = RequestMethod.GET)
    public List<secGroupAndUserUtil> showAll() throws Exception {
    	List<secGroupAndUserUtil> secGroupsAndUsers = new ArrayList<secGroupAndUserUtil>();
    	Map<String,Object> params = new HashMap<String, Object>();
    	params.put("id", 0);
    	Query query = new Query(params);
        List<SecGroups> secGroups =secGroupsService.selectByExample(query);
        if(secGroups!=null){
        	for (SecGroups secOneGroup : secGroups) {
        		
        		secGroupAndUserUtil secGroupAndUserUtil_group = new secGroupAndUserUtil();
        		secGroupAndUserUtil_group.setTitle(secOneGroup.getGroupName());
        		secGroupAndUserUtil_group.setValue(secOneGroup.getId().toString());
        		
        		Map<String,Object> params2 = new HashMap<String, Object>();
            	params2.put("secGroupId", secOneGroup.getId());
            	Query query2 = new Query(params2);
				List<SecAdminUser> secAdminUsersList = secAdminUserService.selectByExampleQuery(query2);
				if (secAdminUsersList!=null) {
					List<secGroupAndUserUtil> secGroupAndUserUtilsList_userList = new ArrayList<secGroupAndUserUtil>();
					for (SecAdminUser secAdminUser : secAdminUsersList) {
						secGroupAndUserUtil secGroupAndUserUtil_user = new secGroupAndUserUtil();
						secGroupAndUserUtil_user.setTitle(secAdminUser.getLoginiNo());
						secGroupAndUserUtil_user.setValue(secAdminUser.getId().toString());
						List<secGroupAndUserUtil> secGroupAndUserUtilNull = new ArrayList<secGroupAndUserUtil>();
						secGroupAndUserUtil_user.setData(secGroupAndUserUtilNull);
						
						secGroupAndUserUtilsList_userList.add(secGroupAndUserUtil_user);
					}
					secGroupAndUserUtil_group.setData(secGroupAndUserUtilsList_userList);
				}
				
				secGroupsAndUsers.add(secGroupAndUserUtil_group);
			}
        }
        return secGroupsAndUsers;
    }

    /** 物理删除 */
    @ApiOperation(value = "删除分组")
    @RequestMapping(value="/api/SecGroups/{id}",method=RequestMethod.DELETE)
    public RestAPIResult2 delete(@PathVariable("id") Integer id) {
        RestAPIResult2 restAPIResult = new RestAPIResult2();
        restAPIResult.setRespCode(1);
        restAPIResult.setRespMsg("操作成功");
        
        if(id!=null && id<=1){
        	 restAPIResult.setRespCode(0);
             restAPIResult.setRespMsg("默认分组不可以删除");
             return restAPIResult;
    	}

        Map<String, Object> map =new HashMap<String,Object>();
        map.put("secGroupId", id);
        map.put("secGroupIdDefault", 1);//默认分组
        map.put("updateDate", DateUtil.getNowDateYYYYMMddHHMMSS());
		map.put("updateBy",getLoginId());
	    map.put("updateByUname",getUserName());
        
		secAdminUserService.updateBySecGroupId(map);
        secGroupsService.deleteByPrimaryKey(id);

        return restAPIResult;
    }

    @ApiOperation(value = "layui树")
    @RequestMapping(value = "/api/SecGroups/tree", method = RequestMethod.GET)
    public List<SecGroupsTree> tree( Map<String, Object> params) {
        Query query= new Query(params);
        return  getTree(secGroupsService.selectByExample(query), SecrityCommonConstant.ROOT);
    }

    private List<SecGroupsTree> getTree(List<SecGroups> groups,int root) {
        List<SecGroupsTree> trees = new ArrayList<SecGroupsTree>();
        SecGroupsTree node = null;
        for (SecGroups group : groups) {
            node = new SecGroupsTree();
            node.setLabel(group.getGroupName());
            node.setName(group.getGroupName());
            BeanUtils.copyProperties(group, node);
            trees.add(node);
        }
        return TreeUtil.bulid(trees,root) ;
    }
}

