package com.weixindev.micro.serv.common.bean.secrity;
import com.weixindev.micro.serv.common.base.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
*SecPrivilegesConfig
*/
@ApiModel(value = "SecPrivilegesConfig")
public class SecPrivilegesConfig extends BaseEntity{
	
	/**
	 *   id
	 */
	@ApiModelProperty(value = "")
	private java.lang.Integer id;
	
	/**
	 * 权限路径  sec_previleges_url
	 */
	@ApiModelProperty(value = "权限路径")
		private String secPrevilegesUrl = "";
	
	/**
	 * 权限所属模块  sec_previlege_module
	 */
	@ApiModelProperty(value = "权限所属模块")
		private String secPrevilegeModule = "";

	private Integer secParentId;
	
	private String menuName;

	public Integer getSecParentId() {
		return secParentId;
	}

	public void setSecParentId(Integer secParentId) {
		this.secParentId = secParentId;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setSecPrevilegesUrl(String value) {
		this.secPrevilegesUrl = value;
	}
	
	public String getSecPrevilegesUrl() {
		return this.secPrevilegesUrl;
	}
	public void setSecPrevilegeModule(String value) {
		this.secPrevilegeModule = value;
	}
	
	public String getSecPrevilegeModule() {
		return this.secPrevilegeModule;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	
}

