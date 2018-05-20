package com.weixindev.micro.serv.common.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EchartSeries {

	private String name;//名称
	private String type;//类型
	private String stack;//栈
	
	private List<BigDecimal> data = new ArrayList<BigDecimal>();

	public EchartSeries(String name, String type, String stack, List<BigDecimal> data) {
		super();
		this.name = name;
		this.type = type;
		this.stack = stack;
		this.data = data;
	}

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

	public String getStack() {
		return stack;
	}

	public void setStack(String stack) {
		this.stack = stack;
	}

	public List<BigDecimal> getData() {
		return data;
	}

	public void setData(List<BigDecimal> data) {
		this.data = data;
	}
	
}
