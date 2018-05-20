package com.weixindev.micro.serv.common.bean.weixin;

import com.weixindev.micro.serv.common.base.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
*WeixinGroups
*/
@ApiModel(value = "图片分组")
public class WeixinGroups extends BaseEntity {
	
	/**
	 * ID  id
	 */
	 @ApiModelProperty(value = "ID")
	private java.lang.Integer id;
	
	/**
	 * 备注  remarks
	 */
	 @ApiModelProperty(value = "备注")
	private String remarks = "";
	
	/**
	 * 分组名称  group_name
	 */
	 @ApiModelProperty(value = "分组名称 ")
	private String groupName = "";
	
	/**
	 *   parent_id 父ID
	 */
	 @ApiModelProperty(value = "父ID ")
	private java.lang.Integer parentId;
	/**
	 *   level 层级
	 */ 
	 @ApiModelProperty(value = "层级 ")
	private java.lang.Integer level;
	 /**
		 *   公众号求和
		 */ 
		 @ApiModelProperty(value = "求和 ")
		private java.lang.Integer count;
	 

	public java.lang.Integer getCount() {
		return count;
	}

	public void setCount(java.lang.Integer count) {
		this.count = count;
	}

	public java.lang.Integer getId() {
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

	public java.lang.Integer getLevel() {
		return level;
	}

	public void setLevel(java.lang.Integer level) {
		this.level = level;
	}
	
}

