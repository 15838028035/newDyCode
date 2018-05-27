package com.weixindev.micro.serv.common.bean.weixin;
import com.weixindev.micro.serv.common.base.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
*WeixinArticleTask
*/
@ApiModel(value = "WeixinArticleTask")
public class WeixinArticleTask extends BaseEntity{
	
	/**
	 * ID  id
	 */
	@ApiModelProperty(value = "ID")
	private java.lang.Integer id;
	
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
	 * 素材类型,0:临时素材，1永久素材  media_category
	 */
	@ApiModelProperty(value = "素材类型,0:临时素材，1永久素材")
		private String mediaCategory = "";
	
	/**
	 * 素材ID  imgTextId
	 */
	@ApiModelProperty(value = "素材ID")
	private java.lang.Integer imgTextId;
	
	/**
	 * 素材类型ID  media_id
	 */
	@ApiModelProperty(value = "素材类型ID")
		private String mediaId = "";
	
	/**
	 * 公众账号ID  user_id
	 */
	@ApiModelProperty(value = "公众账号ID")
		private String userId = "";
	
	/**
	 * 授权方APPID  authorizerAppid
	 */
	@ApiModelProperty(value = "授权方APPID")
		private String authorizerAppid = "";
	
	/**
	 * 是否有效  enable_flag
	 */
	@ApiModelProperty(value = "是否有效")
		private String enableFlag = "";
	
	/**
	 * 定时任务时间  task_cron
	 */
	@ApiModelProperty(value = "定时任务时间")
		private String taskCron = "";
	
	/**
	 * 任务状态  task_status
	 */
	@ApiModelProperty(value = "任务状态")
		private String taskStatus = "";
	
	/**
	 * 执行结果  execute_result
	 */
	@ApiModelProperty(value = "执行结果")
		private String executeResult = "";
	
	/**
	 *   to_user_name
	 */
	@ApiModelProperty(value = "")
		private String toUserName = "";
	
	/**
	 *   map_key
	 */
	@ApiModelProperty(value = "")
		private String mapKey = "";

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
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

	public java.lang.Integer getImgTextId() {
		return imgTextId;
	}

	public void setImgTextId(java.lang.Integer imgTextId) {
		this.imgTextId = imgTextId;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAuthorizerAppid() {
		return authorizerAppid;
	}

	public void setAuthorizerAppid(String authorizerAppid) {
		this.authorizerAppid = authorizerAppid;
	}

	public String getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

	public String getTaskCron() {
		return taskCron;
	}

	public void setTaskCron(String taskCron) {
		this.taskCron = taskCron;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getExecuteResult() {
		return executeResult;
	}

	public void setExecuteResult(String executeResult) {
		this.executeResult = executeResult;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getMapKey() {
		return mapKey;
	}

	public void setMapKey(String mapKey) {
		this.mapKey = mapKey;
	}
	
}

