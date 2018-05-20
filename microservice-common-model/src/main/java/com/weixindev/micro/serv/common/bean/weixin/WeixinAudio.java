package com.weixindev.micro.serv.common.bean.weixin;

import com.weixindev.micro.serv.common.base.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * WeixinAudio
 */
@ApiModel(value = "音频管理")
public class WeixinAudio extends BaseEntity {

	/**
	 * ID id
	 */
	@ApiModelProperty(value = "ID")
	private java.lang.Integer id;

	/**
	 * 标题 title
	 */
	@ApiModelProperty(value = "标题")
	private String title = "";

	/**
	 * 面封图片 headImg
	 */
	@ApiModelProperty(value = "面封图片")
	private String headImg = "";

	/**
	 * mediaId mediaId
	 */
	@ApiModelProperty(value = "mediaId")
	private String mediaId = "";

	/**
	 * 分类 categoryId
	 */
	@ApiModelProperty(value = "categoryId")
	private String categoryId = "";

	/**
	 * 消息类型 msgType
	 */
	@ApiModelProperty(value = "消息类型")
	private String msgType = "";
	/**
	 * 图片分组 img_group_id
	 */
	@ApiModelProperty(value = "图片分组")
	private java.lang.Integer imgGroupId;

	/**
	 * 文件大小 file_size
	 */
	@ApiModelProperty(value = "文件大小")
	private String fileSize = "";

	/**
	 * 视频时长 video_length
	 */
	@ApiModelProperty(value = "视频时长")
	private String videoLength = "";

	/**
	 * 标签 tag
	 */
	@ApiModelProperty(value = "标签")
	private String tag = "";

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public java.lang.Integer getImgGroupId() {
		return imgGroupId;
	}

	public void setImgGroupId(java.lang.Integer imgGroupId) {
		this.imgGroupId = imgGroupId;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getVideoLength() {
		return videoLength;
	}

	public void setVideoLength(String videoLength) {
		this.videoLength = videoLength;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
