package com.weixindev.micro.serv.common.bean.weixin;
import com.weixindev.micro.serv.common.base.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
*WeixinConfig
*/
@ApiModel(value = "配置管理")
public class WeixinConfig extends BaseEntity{
	
	/**
	 * ID  id
	 */
	 @ApiModelProperty(value = "ID")
	private java.lang.Integer id;
	
	/**
	 * 配置名称  cfg_name
	 */
	 @ApiModelProperty(value = "配置名称 ")
	private String cfgName = "";
	
	/**
	 * 配置值  cfg_value
	 */
	 @ApiModelProperty(value = "配置值 ")
	private String cfgValue = "";
	
	/**
	 * 配置分类  cfg_class
	 */
	 @ApiModelProperty(value = "配置分类 ")
	private String cfgClass = "";
	
	/**
	 * 描述  remarks
	 */
	 @ApiModelProperty(value = "描述 ")
	private String remarks = "";

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public String getCfgName() {
		return cfgName;
	}

	public void setCfgName(String cfgName) {
		this.cfgName = cfgName;
	}

	public String getCfgValue() {
		return cfgValue;
	}

	public void setCfgValue(String cfgValue) {
		this.cfgValue = cfgValue;
	}

	public String getCfgClass() {
		return cfgClass;
	}

	public void setCfgClass(String cfgClass) {
		this.cfgClass = cfgClass;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}

