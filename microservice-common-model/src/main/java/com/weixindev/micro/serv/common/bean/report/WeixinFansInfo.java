package com.weixindev.micro.serv.common.bean.report;
import com.weixindev.micro.serv.common.base.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
*WeixinFansInfo
*/
@ApiModel(value = "WeixinFansInfo")
public class WeixinFansInfo extends BaseEntity{
	
	/**
	 *   id
	 */
	@ApiModelProperty(value = "")
	private java.lang.Integer id;
	
	/**
	 * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。  subscribe
	 */
	@ApiModelProperty(value = "用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。")
	private java.lang.Integer subscribe;
	
	/**
	 * 用户的标识，对当前公众号唯一  openid
	 */
	@ApiModelProperty(value = "用户的标识，对当前公众号唯一")
		private String openid = "";
	
	/**
	 * 用户的昵称  nickname
	 */
	@ApiModelProperty(value = "用户的昵称")
		private String nickname = "";
	
	/**
	 *   sex
	 */
	@ApiModelProperty(value = "")
	private java.lang.Integer sex;
	
	/**
	 * 城市  city
	 */
	@ApiModelProperty(value = "城市")
		private String city = "";
	
	/**
	 * 国家  country
	 */
	@ApiModelProperty(value = "国家")
		private String country = "";
	
	/**
	 * 省份  province
	 */
	@ApiModelProperty(value = "省份")
		private String province = "";
	
	/**
	 * 用户的语言，简体中文为zh_CN  language
	 */
	@ApiModelProperty(value = "用户的语言，简体中文为zh_CN")
		private String language = "";
	
	/**
	 * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间  subscribe_time
	 */
	@ApiModelProperty(value = "用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间")
	private java.util.Date subscribeTime;
	
	 /**
	 * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间Begin
	 */
	private String  subscribeTimeBegin;
	/**
	 * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间End
	 */
	private String subscribeTimeEnd;
	/**
	 * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。  unionid
	 */
	@ApiModelProperty(value = "只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。")
		private String unionid = "";
	
	/**
	 * 用户所在的分组ID（兼容旧的用户分组接口）  groupid
	 */
	@ApiModelProperty(value = "用户所在的分组ID（兼容旧的用户分组接口）")
	private java.lang.Integer groupid;
	
	/**
	 * 返回用户关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENEPROFILE LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_OTHERS 其他  subscribe_scene
	 */
	@ApiModelProperty(value = "返回用户关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENEPROFILE LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_OTHERS 其他")
		private String subscribeScene = "";
	
	/**
	 *   create_time
	 */
	@ApiModelProperty(value = "")
	private java.util.Date createTime;
	
	 /**
	 * Begin
	 */
	private String  createTimeBegin;
	/**
	 * End
	 */
	private String createTimeEnd;
	/**
	 *   update_time
	 */
	@ApiModelProperty(value = "")
	private java.util.Date updateTime;
	
	 /**
	 * Begin
	 */
	private String  updateTimeBegin;
	/**
	 * End
	 */
	private String updateTimeEnd;
	
	private Integer userId;

	public String getSubscribeTimeBegin() {
		return subscribeTimeBegin;
	}

	public void setSubscribeTimeBegin(String subscribeTimeBegin) {
		this.subscribeTimeBegin = subscribeTimeBegin;
	}

	public String getSubscribeTimeEnd() {
		return subscribeTimeEnd;
	}

	public void setSubscribeTimeEnd(String subscribeTimeEnd) {
		this.subscribeTimeEnd = subscribeTimeEnd;
	}

	public String getCreateTimeBegin() {
		return createTimeBegin;
	}

	public void setCreateTimeBegin(String createTimeBegin) {
		this.createTimeBegin = createTimeBegin;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getUpdateTimeBegin() {
		return updateTimeBegin;
	}

	public void setUpdateTimeBegin(String updateTimeBegin) {
		this.updateTimeBegin = updateTimeBegin;
	}

	public String getUpdateTimeEnd() {
		return updateTimeEnd;
	}

	public void setUpdateTimeEnd(String updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setSubscribe(java.lang.Integer value) {
		this.subscribe = value;
	}
	
	public java.lang.Integer getSubscribe() {
		return this.subscribe;
	}
	public void setOpenid(String value) {
		this.openid = value;
	}
	
	public String getOpenid() {
		return this.openid;
	}
	public void setNickname(String value) {
		this.nickname = value;
	}
	
	public String getNickname() {
		return this.nickname;
	}
	public void setSex(java.lang.Integer value) {
		this.sex = value;
	}
	
	public java.lang.Integer getSex() {
		return this.sex;
	}
	public void setCity(String value) {
		this.city = value;
	}
	
	public String getCity() {
		return this.city;
	}
	public void setCountry(String value) {
		this.country = value;
	}
	
	public String getCountry() {
		return this.country;
	}
	public void setProvince(String value) {
		this.province = value;
	}
	
	public String getProvince() {
		return this.province;
	}
	public void setLanguage(String value) {
		this.language = value;
	}
	
	public String getLanguage() {
		return this.language;
	}
	public void setSubscribeTime(java.util.Date value) {
		this.subscribeTime = value;
	}
	
	public java.util.Date getSubscribeTime() {
		return this.subscribeTime;
	}
	public void setUnionid(String value) {
		this.unionid = value;
	}
	
	public String getUnionid() {
		return this.unionid;
	}
	public void setGroupid(java.lang.Integer value) {
		this.groupid = value;
	}
	
	public java.lang.Integer getGroupid() {
		return this.groupid;
	}
	public void setSubscribeScene(String value) {
		this.subscribeScene = value;
	}
	
	public String getSubscribeScene() {
		return this.subscribeScene;
	}
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	
}

