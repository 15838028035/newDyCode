package com.github.binarywang.demo.wechat.service.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class weixinMenuMapUtil {
	
	private String name;
	
	private String type = "1";
	
	private List<weixinMenuActlistMapUtil> act_list = new ArrayList<weixinMenuActlistMapUtil>();
	
	private List<weixinMenuMapUtil> subButtons = new ArrayList<weixinMenuMapUtil>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<weixinMenuActlistMapUtil> getAct_list() {
		return act_list;
	}

	public void setAct_list(List<weixinMenuActlistMapUtil> act_list) {
		this.act_list = act_list;
	}

	public List<weixinMenuMapUtil> getSubButtons() {
		return subButtons;
	}

	public void setSubButtons(List<weixinMenuMapUtil> subButtons) {
		this.subButtons = subButtons;
	}
	
}


