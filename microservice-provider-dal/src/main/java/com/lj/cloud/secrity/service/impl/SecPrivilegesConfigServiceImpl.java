package com.lj.cloud.secrity.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lj.cloud.secrity.dal.SecPrivilegesConfigMapper;
import com.lj.cloud.secrity.service.SecPrivilegesConfigService;
import com.weixindev.micro.serv.common.bean.secrity.SecPrivilegesConfig;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
@Service
public class SecPrivilegesConfigServiceImpl  implements SecPrivilegesConfigService{
	private Logger logger = LoggerFactory.getLogger(SecPrivilegesConfigServiceImpl.class);

	@Autowired
	private SecPrivilegesConfigMapper secPrivilegesConfigMapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return secPrivilegesConfigMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SecPrivilegesConfig secPrivilegesConfig){
		return secPrivilegesConfigMapper.insert(secPrivilegesConfig);
	}

	@Override
	public int insertSelective(SecPrivilegesConfig secPrivilegesConfig) {
		return secPrivilegesConfigMapper.insertSelective(secPrivilegesConfig);
	}

	@Override
	public SecPrivilegesConfig selectByPrimaryKey(Integer id) {
		return secPrivilegesConfigMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(SecPrivilegesConfig secPrivilegesConfig) {
		return secPrivilegesConfigMapper.updateByPrimaryKeySelective(secPrivilegesConfig);
	}

	@Override
	public int updateByPrimaryKey(SecPrivilegesConfig secPrivilegesConfig) {
		return secPrivilegesConfigMapper.updateByPrimaryKey(secPrivilegesConfig);
	}

	@Override
	 public LayUiTableResultResponse selectByQuery(Query query,String name) {
	        com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<Map<String,Object>> list  = secPrivilegesConfigMapper.selectByPageExample(query,name);
	        return new LayUiTableResultResponse(result.getTotal(), list);
	}

	@Override
	public List<SecPrivilegesConfig> selectByExample(Query query) {
		return secPrivilegesConfigMapper.selectByExample(query);
	}

	@Override
	public int deleteById(Integer id) {
		SecPrivilegesConfig map=secPrivilegesConfigMapper.selectConfig(id);
		if(null!=map) {
			return 0;
		}else {
			return secPrivilegesConfigMapper.deleteByPrimaryKey(id);
		}
		
	}

	@Override
	public   List<Map<String,Object>> selectByQueryInfo(String name) {
		
		return secPrivilegesConfigMapper.selectByQueryInfo(name);
	}

}
