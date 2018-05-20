package com.lj.cloud.secrity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lj.cloud.secrity.service.AdminAuthortyService;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.secrity.SecPrivilegesConfig;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "权限管理", tags = "管理员权限接口")
@RestController
public class AdminAuthorityController {
	@Autowired
	AdminAuthortyService  authorityService;
	
	@ApiOperation(value = "获取登录主页面菜单")

	 @RequestMapping(value = "/api/getLeftMenuList", method = RequestMethod.GET)
	public LayUiTableResultResponse menuList(@RequestParam String loginNo,@RequestParam(defaultValue = "100")int limit) {
		RestAPIResult2 result=new RestAPIResult2<>();
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("loginNo", loginNo);
		Query query=new Query(params);
		query.setLimit(100);
		return authorityService.getMenuList(query);
	}
	

//	@RequestMapping("/api")
	private void getAuthorityList(@RequestParam String loginNo) {

		
	}

	/*@ApiOperation(value = "权限连接分配")
	@RequestMapping("/api/addtAuthorityAssignment")
	public void addtAuthorityAssignment(@RequestParam Integer groupId , String roleIds) {
		RestAPIResult2 result=new RestAPIResult2<>();
		String resultInfo="";
		Map<String, Object> params=new HashMap<String,Object>();
=======
	@ApiOperation(value = "权限连接分配")

	//@RequestMapping("/api")
	public void getAuthorityAssignment(@RequestParam Integer groupId , Integer id) {

>>>>>>> 5876d0e6b4bcd45d9b709da734620cacadb14b20
		
		authorityService.addAuthorityAssignment(groupId,roleIds);
		
	}*/
	
	@ApiOperation(value = "添加左侧菜单")
	@RequestMapping("/api/addLeftMenu")
	public void addLeftMenu(@RequestParam SecPrivilegesConfig secPrivilegesConfig) {
		
	}
	
	@ApiOperation(value = "获取所有菜单")

	//@RequestMapping("/api/getLeftMenu")
	public RestAPIResult2 getLeftMenu() {
		RestAPIResult2 result=new RestAPIResult2();
		 List<Map<String,Object>>  meunList= authorityService.getAllMenu();
		
		return result;
	}
	
	
	
	
	
}
