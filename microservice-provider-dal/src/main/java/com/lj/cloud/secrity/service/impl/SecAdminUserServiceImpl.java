package com.lj.cloud.secrity.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lj.cloud.secrity.dal.SecAdminUserMapper;
import com.lj.cloud.secrity.service.SecAdminUserService;
import com.weixindev.micro.serv.common.bean.secrity.SecAdminUser;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
import com.weixindev.micro.serv.common.util.MapAndObject;

@Service
public class SecAdminUserServiceImpl implements SecAdminUserService{
	
	private Logger logger = LoggerFactory.getLogger(SecAdminUserServiceImpl.class);

	@Autowired
	private SecAdminUserMapper secAdminUserMapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return secAdminUserMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SecAdminUser record) {
		return secAdminUserMapper.insert(record);
	}

	@Override
	public int insertSelective(SecAdminUser record) {
		return secAdminUserMapper.insertSelective(record);
	}

	@Override
	public SecAdminUser selectByPrimaryKey(Integer id) {
		return secAdminUserMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<SecAdminUser> selectByExampleQuery(Query query) {
		List<SecAdminUser> secAdminUsersList = secAdminUserMapper.selectByExampleQuery(query);
		return secAdminUsersList;
	}

	@Override
	public int updateByPrimaryKeySelective(SecAdminUser record) {
		return secAdminUserMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SecAdminUser record) {
		return secAdminUserMapper.updateByPrimaryKey(record);
	}

	@Override
	 public SecAdminUser login(String loginiNo, String pwd){
		SecAdminUser secAdminUser = new SecAdminUser();
		secAdminUser.setLoginiNo(loginiNo);
		secAdminUser.setPwd(pwd);
		
		Map<String,Object> condition = new HashMap<String,Object>();
		
		MapAndObject mapAndObject = new MapAndObject(condition,secAdminUser);
		
		List<SecAdminUser> list = secAdminUserMapper.selectByExample(mapAndObject);
		logger.info("{}登陆成功!",loginiNo);
		if(list==null || list.size()==0){
			return new SecAdminUser();
		}
		return list.get(0);
	 }
	
	@Override
	 public LayUiTableResultResponse selectByQuery(Query query) {
	        com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<Map<String,Object>> list = secAdminUserMapper.selectByPageExample(query);
	        return new LayUiTableResultResponse(result.getTotal(), list);
	}

	@Override
	public int updateBySecGroupId(Map<String, Object> map) {
		return secAdminUserMapper.updateBySecGroupId(map);
	}

	@Override
	public List<SecAdminUser> selectByExample(SecAdminUser secAdminUser) {
		return secAdminUserMapper.updateBySecGroupId(secAdminUser);
	}

	@Override
	public SecAdminUser findAdminUserQuery(String loginNo) {
		return secAdminUserMapper.findAdminUserQuery(loginNo);
	}

	@Override
	public SecAdminUser getUserInfoByLoginNo(String loginNo) {
		
		return secAdminUserMapper.findAdminUserQuery(loginNo);
	}
	
	
}
