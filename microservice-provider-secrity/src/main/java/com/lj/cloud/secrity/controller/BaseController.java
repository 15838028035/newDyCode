package com.lj.cloud.secrity.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.lj.cloud.secrity.RedisBusiness;


public class BaseController {

	@Autowired
	RedisBusiness r;
	public Integer getLoginId() {
		//fixed me
		return 0;
	}

	public String getUserName() {
		String loginNo="";
		try {
			loginNo = r.get("loginNo");
			return loginNo;
		} catch (Exception e) {
			System.out.println("---------------");
			System.out.println("redis===="+r);
			e.printStackTrace();
		}
		return loginNo;
	}
}
