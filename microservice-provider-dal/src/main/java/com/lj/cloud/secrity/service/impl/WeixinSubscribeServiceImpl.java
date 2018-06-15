package com.lj.cloud.secrity.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lj.cloud.secrity.dal.WeixinSubscribeMapper;
import com.lj.cloud.secrity.service.WeixinSubscribeService;
import com.weixindev.micro.serv.common.bean.weixin.WeixinSubscribe;

@Service
public class WeixinSubscribeServiceImpl implements WeixinSubscribeService {

	@Autowired
	private WeixinSubscribeMapper weixinSubscribeMapper;
	@Override
	public int insert(WeixinSubscribe weixinSubscribe) {
		return weixinSubscribeMapper.insert(weixinSubscribe);
	}

	@Override
	public List<WeixinSubscribe> select(Map<String,Object> map) {
		return weixinSubscribeMapper.select(map);
	}

}
