package com.weixindev.micro.serv.common.bean.secrity;

import java.util.List;

import com.weixindev.micro.serv.common.base.entity.BaseEntity;

/**
*SecGroups
*/
public class SecGroups extends BaseEntity{
	
	/**
	 * ID  id
	 */
	private Integer id;
	
	/**
	 * 备注  remarks
	 */
	private String remarks;
	
	/**
	 * 分组名称  group_name
	 */
	private String groupName;
	
	
	/**
	 *   parent_id
	 */
	private java.lang.Integer parentId;
	
	private String urids;

	public Integer getId() {
		return id;
	}


	public void setId(java.lang.Integer id) {
		this.id = id;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public String getGroupName() {
		return groupName;
	}


	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	public java.lang.Integer getParentId() {
		return parentId;
	}


	public void setParentId(java.lang.Integer parentId) {
		this.parentId = parentId;
	}





	public String getUrids() {
		return urids;
	}


	public void setUrids(String urids) {
		this.urids = urids;
	}

	
}

