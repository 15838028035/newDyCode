package com.lj.cloud.secrity.service;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.report.WeixinFansAllCount;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;

public interface WeixinFansAllCountService {

	int deleteByPrimaryKey(Integer id);

	int insert(WeixinFansAllCount weixinFansAllCount);

	int insertSelective(WeixinFansAllCount weixinFansAllCount);

	WeixinFansAllCount selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(WeixinFansAllCount weixinFansAllCount);

	int updateByPrimaryKey(WeixinFansAllCount weixinFansAllCount);

	public LayUiTableResultResponse selectByQuery(Query query);

	public List<WeixinFansAllCount> selectByExample(Query query);

	public LayUiTableResultResponse selectByPageExampleMaxTime(Query query);

	public Map<String, Object> selectFansSource();

	public LayUiTableResultResponse tableCountAll(Query query);
}
