package com.weixindev.micro.serv.common.bean.weixin;
import com.weixindev.micro.serv.common.base.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
*图片分组
*/
@ApiModel(value = "图片分组")
public class WeixinImgGroups extends BaseEntity{
	
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
	@ApiModelProperty(value = "分组名称")
	private String groupName = "";
	
	/**
	 *   parent_id
	 */
	@ApiModelProperty(value = "父ID")
	private java.lang.Integer parentId;

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
}

