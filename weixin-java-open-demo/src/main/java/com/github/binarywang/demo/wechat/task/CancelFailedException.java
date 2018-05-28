package com.github.binarywang.demo.wechat.task;

public class CancelFailedException extends Exception {
	private String msg; 
	public CancelFailedException() {
		
	}
	public CancelFailedException(String msg) {
		this.msg=msg;
	}
	public String getMsg() {
		return msg;
	}

}
