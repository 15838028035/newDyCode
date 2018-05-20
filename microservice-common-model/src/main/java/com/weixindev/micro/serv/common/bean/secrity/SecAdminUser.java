package com.weixindev.micro.serv.common.bean.secrity;

import com.weixindev.micro.serv.common.base.entity.BaseEntity;

/**
*管理员用户表
*/
public class SecAdminUser extends BaseEntity{
	
	/**
	 * ID  id
	 */
	private java.lang.Integer id;
	
	/**
	 * 登录账号  logini_no
	 */
	private String loginiNo = "";
	
	/**
	 * 手机号码  mobile
	 */
	private String mobile  = "";
	
	/**
	 * 登录密码  pwd
	 */
	private String pwd  = "";
	
	/**
	 * QQ  qq
	 */
	private String qq  = "";
	
	/**
	 * 微信号码  weixinNo
	 */
	private String weixinNo  = "";
	
	/**
	 * 姓名  user_name
	 */
	private String userName  = "";
	
	/**
	 * 最近登录日期  last_login_date 
	 */
	private java.util.Date lastLoginDate;
	/**
	 * 最近登录IP  last_login_ip
	 */
	private String lastLoginIp  = "";
	
	/**
	 * 用户图像  headImg
	 */
	private String headImg  = "";
	/**
	 * 管理员分组ID  sec_group_id
	 */
	private java.lang.Integer secGroupId;

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public String getLoginiNo() {
		return loginiNo;
	}

	public void setLoginiNo(String loginiNo) {
		this.loginiNo = loginiNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWeixinNo() {
		return weixinNo;
	}

	public void setWeixinNo(String weixinNo) {
		this.weixinNo = weixinNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public java.util.Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(java.util.Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public java.lang.Integer getSecGroupId() {
		return secGroupId;
	}

	public void setSecGroupId(java.lang.Integer secGroupId) {
		this.secGroupId = secGroupId;
	}
	
}

