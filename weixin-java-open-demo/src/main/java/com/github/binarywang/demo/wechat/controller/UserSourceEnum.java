package com.github.binarywang.demo.wechat.controller;
/*
 * 用户的渠道，数值代表的含义如下： 0代表其他合计 1代表公众号搜索 
 * 17代表名片分享 30代表扫描二维码 43代表图文页右上角菜单 51代表支付后关注（在支付完成页） 
 * 57代表图文页内公众号名称 75代表公众号文章广告 78代表朋友圈广告
 */
public enum UserSourceEnum {
	OTHER(0,"其他合计"),SEARCH(1,"公众号搜索 "),SHARE(17,"名片分享"),
	SCAN(30,"扫描二维码"),MENU(43,"图文页右上角菜单"),PAY(51,"图文页右上角菜单"),
	NAME(57,"图文页内公众号名称"),WENZHANGGUANGGAO(75,"公众号文章广告"),
	PENGYOUQUANGUANGGAO(78,"朋友圈广告");
	
	 private int index;
	 private String name;  
	 
	 private UserSourceEnum(int index,String name) {
		 this.name = name;  
	     this.index = index;  
	 }
	 
	 public static String getName(int index) {  
	        for (UserSourceEnum u : UserSourceEnum.values()) {  
	            if (u.getIndex() == index) {  
	                return u.name;  
	            }  
	        }  
	        return null;  
	    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	} 
	 
}
