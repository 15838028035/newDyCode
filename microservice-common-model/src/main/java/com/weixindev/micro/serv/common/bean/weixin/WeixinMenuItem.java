package com.weixindev.micro.serv.common.bean.weixin;
import com.weixindev.micro.serv.common.base.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
*WeixinMenuItem
*/
@ApiModel(value = "WeixinMenuItem")
public class WeixinMenuItem extends BaseEntity{
	
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
	 * 推送uRL  url
	 */
	@ApiModelProperty(value = "推送uRL")
		private String url = "";
	
	/**
	 * 菜单的响应动作类型，view表示网页类型，click表示点击类型，miniprogram表示小程序类型  type
	 */
	@ApiModelProperty(value = "菜单的响应动作类型，view表示网页类型，click表示点击类型，miniprogram表示小程序类型")
		private String type = "";
	
	/**
	 * 分组名称  menuName
	 */
	@ApiModelProperty(value = "分组名称")
		private String menuName = "";
	
	/**
	 *   enable_flag
	 */
	@ApiModelProperty(value = "")
		private String enableFlag = "";
	
	/**
	 * 分组ID  group_id
	 */
	@ApiModelProperty(value = "分组ID")
	private java.lang.Integer groupId;
	
	/**
	 *   parent_id
	 */
	@ApiModelProperty(value = "")
	private java.lang.Integer parentId;
	
	/**
	 * 菜单模板内容  menucontent
	 */
	@ApiModelProperty(value = "菜单模板内容")
		private String menucontent = "";

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

	public java.lang.Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(java.lang.Integer groupId) {
		this.groupId = groupId;
	}

	public java.lang.Integer getParentId() {
		return parentId;
	}

	public void setParentId(java.lang.Integer parentId) {
		this.parentId = parentId;
	}

	public String getMenucontent() {
		return menucontent;
	}

	public void setMenucontent(String menucontent) {
		this.menucontent = menucontent;
	}
	
}

