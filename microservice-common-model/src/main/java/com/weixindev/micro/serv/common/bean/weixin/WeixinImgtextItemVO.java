package com.weixindev.micro.serv.common.bean.weixin;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
*WeixinImgtextItem
*/
@ApiModel(value = "微信素材项视图VO")
public class WeixinImgtextItemVO {
	
	/**
	 * 图文id  imgText_id
	 */
	 @ApiModelProperty(value = "素材ID")
	private java.lang.Integer imgTextId;
	 
	 @ApiModelProperty(value = "素材项目列表")
	 private List<WeixinImgtextItem> WeixinImgtextItemList = new ArrayList<WeixinImgtextItem>();

	public java.lang.Integer getImgTextId() {
		return imgTextId;
	}

	public void setImgTextId(java.lang.Integer imgTextId) {
		this.imgTextId = imgTextId;
	}

	public List<WeixinImgtextItem> getWeixinImgtextItemList() {
		return WeixinImgtextItemList;
	}

	public void setWeixinImgtextItemList(List<WeixinImgtextItem> weixinImgtextItemList) {
		WeixinImgtextItemList = weixinImgtextItemList;
	}
	 
}

