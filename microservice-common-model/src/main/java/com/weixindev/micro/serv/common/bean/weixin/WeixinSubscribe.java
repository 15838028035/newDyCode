package com.weixindev.micro.serv.common.bean.weixin;

import com.weixindev.micro.serv.common.base.entity.BaseEntity;

public class WeixinSubscribe extends BaseEntity{
	private Integer id;
	private String openid;
	private String userid;
	private String createTime;
	private Integer event;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getEvent() {
		return event;
	}
	public void setEvent(Integer event) {
		this.event = event;
	}
	
}
