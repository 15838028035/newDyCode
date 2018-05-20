package com.weixindev.micro.serv.common.bean.weixin;
import com.weixindev.micro.serv.common.base.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
*WeixinPushLog
*/
@ApiModel(value = "WeixinPushLog")
public class WeixinPushLog extends BaseEntity{
	
	/**
	 * ID  id
	 */
	@ApiModelProperty(value = "ID")
	private java.lang.Integer id;
	
	/**
	 * 分类  categoryId
	 */
	@ApiModelProperty(value = "分类")
		private String categoryId = "";
	/**
	 *   msg_id
	 */
	@ApiModelProperty(value = "")
		private String msgId = "";
	
	/**
	 *   msg_data_id
	 */
	@ApiModelProperty(value = "")
		private String msgDataId = "";
	
	/**
	 * 素材类型,0:临时素材，1永久素材
	 */
	 @ApiModelProperty(value = "素材类型,0:临时素材，1永久素材")
	 private String mediaCategory;
	 
	 @ApiModelProperty(value = "素材ID")
	 private Integer imgTextId;
	 
	 @ApiModelProperty(value = "素材类型ID")
	 private String mediaId;
	 
	 /**
	 * 公众账号ID  userId
	 */
	@ApiModelProperty(value = "公众账号ID")
	private java.lang.Integer userId;
	
	/**
	 * 授权方APPID  authorizerAppid
	 */
	@ApiModelProperty(value = "授权方APPID")
		private String authorizerAppid = "";

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getMsgDataId() {
		return msgDataId;
	}

	public void setMsgDataId(String msgDataId) {
		this.msgDataId = msgDataId;
	}

	public String getMediaCategory() {
		return mediaCategory;
	}

	public void setMediaCategory(String mediaCategory) {
		this.mediaCategory = mediaCategory;
	}

	public Integer getImgTextId() {
		return imgTextId;
	}

	public void setImgTextId(Integer imgTextId) {
		this.imgTextId = imgTextId;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public java.lang.Integer getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Integer userId) {
		this.userId = userId;
	}

	public String getAuthorizerAppid() {
		return authorizerAppid;
	}

	public void setAuthorizerAppid(String authorizerAppid) {
		this.authorizerAppid = authorizerAppid;
	}
	
}

