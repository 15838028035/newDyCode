package com.weixindev.micro.serv.common.bean.weixin;

import com.weixindev.micro.serv.common.base.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * WeixinUserinfo
 */
@ApiModel(value = "微信用户")
public class WeixinUserinfo extends BaseEntity {

	/**
	 * ID id
	 */
	@ApiModelProperty(value = "ID")
	private java.lang.Integer id;

	/**
	 * 公众号昵称 nick_name
	 */
	@ApiModelProperty(value = "公众号昵称")
	private String nickName = "";

	/**
	 * 公众号图像 head_img
	 */
	@ApiModelProperty(value = " 公众号图像")
	private String headImg = "";

	/**
	 * 公众账号类型(0订阅号，1升级订阅号，2服务号) service_type_info
	 */
	@ApiModelProperty(value = "公众账号类型(0订阅号，1升级订阅号，2服务号)  service_type_info")
	private String serviceTypeInfo = "";

	/**
	 * 授权方认证类型，-1代表未认证，0代表微信认证，1代表新浪微博认证，2代表腾讯微博认证，3代表已资质认证通过但还未通过名称认证，4代表已资质认证通过、还未通过名称认证，但通过了新浪微博认证，5代表已资质认证通过、还未通过名称认证，但通过了腾讯微博认证
	 * verify_type_info
	 */
	@ApiModelProperty(value = "权方认证类型")
	private java.lang.Integer verifyTypeInfo;

	/**
	 * 授权方公众号的原始ID user_name
	 */
	@ApiModelProperty(value = "授权方公众号的原始ID")
	private String userName = "";

	/**
	 * 公众号的主体名称 principal_name
	 */
	@ApiModelProperty(value = " 公众号的主体名称 ")
	private String principalName = "";

	/**
	 * 授权方公众号所设置的微信号，可能为空 alias
	 */
	@ApiModelProperty(value = "授权方公众号所设置的微信号")
	private String alias = "";

	/**
	 * 用以了解以下功能的开通状况（0代表未开通，1代表已开通）： open_store:是否开通微信门店功能 open_scan:是否开通微信扫商品功能
	 * open_pay:是否开通微信支付功能 open_card:是否开通微信卡券功能 open_shake:是否开通微信摇一摇功能 business_info
	 */
	@ApiModelProperty(value = "开通状况（0代表未开通，1代表已开通）")
	private String businessInfo = "";

	/**
	 * 注备 remarks
	 */
	@ApiModelProperty(value = "注备")
	private String remarks = "";

	/**
	 * 二维码图像 qrcode_url
	 */
	@ApiModelProperty(value = "二维码图像 ")
	private String qrcodeUrl = "";

	/**
	 * 公众号说说
	 */
	@ApiModelProperty(value = "公众号说说")
	private String signature = "";

	/**
	 * 授权信息 authorization_info
	 */
	@ApiModelProperty(value = "授权信息")
	private String authorizationInfo = "";

	/**
	 * 授权方appid authorization_appid
	 */
	@ApiModelProperty(value = "授权方")
	private String authorizationAppid = "";

	/**
	 * 公众号账号分组 weixin_groups_id
	 */
	@ApiModelProperty(value = "公众号账号分组")
	private java.lang.Integer weixinGroupsId;

	/**
	 * 粉丝数量 starsCount
	 */
	@ApiModelProperty(value = "粉丝数量")
	private java.lang.Integer starsCount;

	/**
	 * 微信三方平台的appid componentAppId
	 */
	@ApiModelProperty(value = "微信三方平台的appid")
	private String componentAppId = "";

	/**
	 * 微信三方平台的app secret componentSecret
	 */
	@ApiModelProperty(value = "微信三方平台的app secret")
	private String componentSecret = "";

	/**
	 * 微信三方平台的token componentToken
	 */
	@ApiModelProperty(value = "微信三方平台的token")
	private String componentToken = "";

	/**
	 * 微信三方平台的EncodingAESKey componentAesKey
	 */
	@ApiModelProperty(value = "微信三方平台的EncodingAESKey")
	private String componentAesKey = "";

	/**
	 * 授权方appid authorizerAppid
	 */
	@ApiModelProperty(value = "授权方appid")
	private String authorizerAppid = "";

	/**
	 * 授权方accesstocken authorizerAccessToken
	 */
	@ApiModelProperty(value = "授权方accesstocken")
	private String authorizerAccessToken = "";

	/**
	 * 上次产生时间戳
	 */
	@ApiModelProperty(value = "accesstoken上次产生时间戳")
	private String accesstokencteatetimespan = "";
	
	public String getAccesstokencteatetimespan() {
		return accesstokencteatetimespan;
	}

	public void setAccesstokencteatetimespan(String accesstokencteatetimespan) {
		this.accesstokencteatetimespan = accesstokencteatetimespan;
	}

	/**
	 * 过期时间 expiresIn
	 */
	@ApiModelProperty(value = "过期时间  expiresIn")
	private String expiresIn = "";

	/**
	 * 授权方用于刷新tocken authorizerRefreshToken
	 */
	@ApiModelProperty(value = "授权方用于刷新tocken")
	private String authorizerRefreshToken = "";

	/**
	 * 公众号授权给开发者的权限集列表，ID为1到15时分别代表： 1.消息管理权限 2.用户管理权限 3.帐号服务权限 4.网页服务权限 5.微信小店权限
	 * 6.微信多客服权限 7.群发与通知权限 8.微信卡券权限 9.微信扫一扫权限 10.微信连WIFI权限 11.素材管理权限 12.微信摇周边权限
	 * 13.微信门店权限 14.微信支付权限 15.自定义菜单权限 请注意：
	 * 1）该字段的返回不会考虑公众号是否具备该权限集的权限（因为可能部分具备），请根据公众号的帐号类型和认证情况，来判断公众号的接口权限。 func_info
	 */
	@ApiModelProperty(value = " 公众号授权给开发者的权限集列表")
	private String funcInfo = "";

	/**
	 * unauthorized是取消授权，updateauthorized是更新授权，authorized是授权成功通知 InfoType
	 */
	@ApiModelProperty(value = "unauthorized是取消授权，updateauthorized是更新授权，authorized是授权成功通知  InfoType")
	private String infoType = "";
	@ApiModelProperty(value = "城市Id")
	private String cityId = "";
	@ApiModelProperty(value = "影院id")
	private String cinemaId = "";
	/*
	 * 授权码
	 */
	private String AuthorizationCode = "";

	/**
	 * PreAuthCode 预授权码
	 * 
	 * @return
	 */
	@ApiModelProperty(value = "预授权码")
	private String PreAuthCode = "";

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getServiceTypeInfo() {
		return serviceTypeInfo;
	}

	public void setServiceTypeInfo(String serviceTypeInfo) {
		this.serviceTypeInfo = serviceTypeInfo;
	}

	public java.lang.Integer getVerifyTypeInfo() {
		return verifyTypeInfo;
	}

	public void setVerifyTypeInfo(java.lang.Integer verifyTypeInfo) {
		this.verifyTypeInfo = verifyTypeInfo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getBusinessInfo() {
		return businessInfo;
	}

	public void setBusinessInfo(String businessInfo) {
		this.businessInfo = businessInfo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getQrcodeUrl() {
		return qrcodeUrl;
	}

	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}

	public String getAuthorizationInfo() {
		return authorizationInfo;
	}

	public void setAuthorizationInfo(String authorizationInfo) {
		this.authorizationInfo = authorizationInfo;
	}

	public String getAuthorizationAppid() {
		return authorizationAppid;
	}

	public void setAuthorizationAppid(String authorizationAppid) {
		this.authorizationAppid = authorizationAppid;
	}

	public java.lang.Integer getWeixinGroupsId() {
		return weixinGroupsId;
	}

	public void setWeixinGroupsId(java.lang.Integer weixinGroupsId) {
		this.weixinGroupsId = weixinGroupsId;
	}

	public java.lang.Integer getStarsCount() {
		return starsCount;
	}

	public void setStarsCount(java.lang.Integer starsCount) {
		this.starsCount = starsCount;
	}

	public String getComponentAppId() {
		return componentAppId;
	}

	public void setComponentAppId(String componentAppId) {
		this.componentAppId = componentAppId;
	}

	public String getComponentSecret() {
		return componentSecret;
	}

	public void setComponentSecret(String componentSecret) {
		this.componentSecret = componentSecret;
	}

	public String getComponentToken() {
		return componentToken;
	}

	public void setComponentToken(String componentToken) {
		this.componentToken = componentToken;
	}

	public String getComponentAesKey() {
		return componentAesKey;
	}

	public void setComponentAesKey(String componentAesKey) {
		this.componentAesKey = componentAesKey;
	}

	public String getAuthorizerAppid() {
		return authorizerAppid;
	}

	public void setAuthorizerAppid(String authorizerAppid) {
		this.authorizerAppid = authorizerAppid;
	}

	public String getAuthorizerAccessToken() {
		return authorizerAccessToken;
	}

	public void setAuthorizerAccessToken(String authorizerAccessToken) {
		this.authorizerAccessToken = authorizerAccessToken;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getAuthorizerRefreshToken() {
		return authorizerRefreshToken;
	}

	public void setAuthorizerRefreshToken(String authorizerRefreshToken) {
		this.authorizerRefreshToken = authorizerRefreshToken;
	}

	public String getFuncInfo() {
		return funcInfo;
	}

	public void setFuncInfo(String funcInfo) {
		this.funcInfo = funcInfo;
	}

	public String getInfoType() {
		return infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getPreAuthCode() {
		return PreAuthCode;
	}

	public void setPreAuthCode(String preAuthCode) {
		PreAuthCode = preAuthCode;
	}

	public String getAuthorizationCode() {
		return AuthorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		AuthorizationCode = authorizationCode;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(String cinemaId) {
		this.cinemaId = cinemaId;
	}

}
