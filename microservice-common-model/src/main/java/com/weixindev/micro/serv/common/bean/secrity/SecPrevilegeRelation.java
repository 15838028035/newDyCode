package com.weixindev.micro.serv.common.bean.secrity;
import com.weixindev.micro.serv.common.base.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
*SecPrevilegeRelation
*/
@ApiModel(value = "SecPrevilegeRelation")
public class SecPrevilegeRelation extends BaseEntity{
	
	/**
	 *   id
	 */
	@ApiModelProperty(value = "")
	private java.lang.Integer id;
	
	/**
	 * sec_admin_user 或者 sec_groups ID  sec_ag_id
	 */
	@ApiModelProperty(value = "sec_admin_user 或者 sec_groups ID")
	private java.lang.Integer secAgId;
	
	/**
	 *   sec_privilege_id
	 */
	@ApiModelProperty(value = "")
	private java.lang.Integer secPrivilegeId;
	
	private Integer state;

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setSecAgId(java.lang.Integer value) {
		this.secAgId = value;
	}
	
	public java.lang.Integer getSecAgId() {
		return this.secAgId;
	}
	public void setSecPrivilegeId(java.lang.Integer value) {
		this.secPrivilegeId = value;
	}
	
	public java.lang.Integer getSecPrivilegeId() {
		return this.secPrivilegeId;
	}

	
}

