package com.github.binarywang.demo.wechat.task;

import me.chanjar.weixin.mp.bean.datacube.WxDataCubeArticleTotal;

public class WxDataCubeArticleTotalVO extends WxDataCubeArticleTotal{
	
	private Integer weixinUserId;

	public Integer getWeixinUserId() {
		return weixinUserId;
	}

	public void setWeixinUserId(Integer weixinUserId) {
		this.weixinUserId = weixinUserId;
	}
	
	
}
