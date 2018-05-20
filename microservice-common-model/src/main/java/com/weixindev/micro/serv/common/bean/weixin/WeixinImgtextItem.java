package com.weixindev.micro.serv.common.bean.weixin;
import com.weixindev.micro.serv.common.base.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
*WeixinImgtextItem
*/
@ApiModel(value = "微信素材项")
public class WeixinImgtextItem extends BaseEntity{
	
	/**
	 * ID  id
	 */
	 @ApiModelProperty(value = "ID")
	private java.lang.Integer id;
	
	/**
	 * 标题  title
	 */
	 @ApiModelProperty(value = "标题")
	private String title = "";
	
	/**
	 * 文章内容  article_content
	 */
	 @ApiModelProperty(value = "文章内容")
	private String articleContent = "";
	
	/**
	 * 面封图片  headImg
	 */
	 @ApiModelProperty(value = "面封图片")
	private String headImg = "";
	
	/**
	 * mediaId  mediaId
	 */
	 @ApiModelProperty(value = "mediaId")
	private String mediaId = "";
	
	/**
	 * 文章分类  categoryId
	 */
	 @ApiModelProperty(value = " 文章分类")
	private String categoryId = "";
	
	/**
	 * 原文链接  originUrl
	 */
	 @ApiModelProperty(value = "原文链接 ")
	private String originUrl = "";
	
	/**
	 * 作者  author
	 */
	 @ApiModelProperty(value = "作者")
	private String author = "";
	
	/**
	 * 原创  yuanchuang
	 */
	 @ApiModelProperty(value = "原创  ")
	private String yuanchuang = "";
	
	/**
	 * 摘要  intro
	 */
	 @ApiModelProperty(value = "摘要")
	private String intro = "";
	
	/**
	 * 留言类型  seeType
	 */
	 @ApiModelProperty(value = "留言类型")
	private String seeType = "";
	
	/**
	 * 推送次数  tuisongCount
	 */
	 @ApiModelProperty(value = "推送次数 ")
	private java.lang.Integer tuisongCount;
	
	/**
	 * 阅读次数  readCount
	 */
	 @ApiModelProperty(value = " 阅读次数")
	private java.lang.Integer readCount;
	
	/**
	 * 分享次数  shareCount
	 */
	 @ApiModelProperty(value = "分享次数")
	private java.lang.Integer shareCount;
	
	/**
	 * 同步次数  sycCount
	 */
	 @ApiModelProperty(value = "同步次数")
	private java.lang.Integer sycCount;
	
	/**
	 * 评论次数  pingLunCount
	 */
	 @ApiModelProperty(value = "评论次数")
	private java.lang.Integer pingLunCount;
	
	/**
	 * 消息类型  msgType
	 */
	 @ApiModelProperty(value = "消息类型")
	private String msgType = "";
	
	/**
	 * 序号  sortNo
	 */
	 @ApiModelProperty(value = "序号")
	private java.lang.Integer sortNo;
	
	/**
	 * 图文id  imgText_id
	 */
	 @ApiModelProperty(value = "素材ID")
	private java.lang.Integer imgTextId;
	 
	 private String newContent;//替换以后的文件内容

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

	public String getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
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

	public String getOriginUrl() {
		return originUrl;
	}

	public void setOriginUrl(String originUrl) {
		this.originUrl = originUrl;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getYuanchuang() {
		return yuanchuang;
	}

	public void setYuanchuang(String yuanchuang) {
		this.yuanchuang = yuanchuang;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getSeeType() {
		return seeType;
	}

	public void setSeeType(String seeType) {
		this.seeType = seeType;
	}

	public java.lang.Integer getTuisongCount() {
		return tuisongCount;
	}

	public void setTuisongCount(java.lang.Integer tuisongCount) {
		this.tuisongCount = tuisongCount;
	}

	public java.lang.Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(java.lang.Integer readCount) {
		this.readCount = readCount;
	}

	public java.lang.Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(java.lang.Integer shareCount) {
		this.shareCount = shareCount;
	}

	public java.lang.Integer getSycCount() {
		return sycCount;
	}

	public void setSycCount(java.lang.Integer sycCount) {
		this.sycCount = sycCount;
	}

	public java.lang.Integer getPingLunCount() {
		return pingLunCount;
	}

	public void setPingLunCount(java.lang.Integer pingLunCount) {
		this.pingLunCount = pingLunCount;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public java.lang.Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(java.lang.Integer sortNo) {
		this.sortNo = sortNo;
	}

	public java.lang.Integer getImgTextId() {
		return imgTextId;
	}

	public void setImgTextId(java.lang.Integer imgTextId) {
		this.imgTextId = imgTextId;
	}

	public String getNewContent() {
		return newContent;
	}

	public void setNewContent(String newContent) {
		this.newContent = newContent;
	}

}

