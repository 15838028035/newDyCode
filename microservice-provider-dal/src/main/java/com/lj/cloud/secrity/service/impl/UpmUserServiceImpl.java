package com.lj.cloud.secrity.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lj.cloud.secrity.dal.UpmUserMapper;
import com.lj.cloud.secrity.service.UpmUserService;
import com.weixindev.micro.serv.common.bean.secrity.UpmUser;

@Service
public class UpmUserServiceImpl implements UpmUserService{
	
	private Logger logger = LoggerFactory.getLogger(UpmUserServiceImpl.class);
	
	@Autowired
	private UpmUserMapper upmUserMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return upmUserMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UpmUser record) {
		return upmUserMapper.insert(record);
	}

	@Override
	public int insertSelective(UpmUser record) {
		return upmUserMapper.insertSelective(record);
	}

	@Override
	public UpmUser selectByPrimaryKey(Integer id) {
		return upmUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(UpmUser record) {
		return upmUserMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(UpmUser record) {
		return upmUserMapper.updateByPrimaryKey(record);
	}
	
	 /**
	 * @param mobile
	 * @param pwd
	 * @return
	 */
	@Override
    public UpmUser login(String mobile, String pwd){
		
		UpmUser upmUser = new UpmUser();
		upmUser.setMobile(mobile);
		upmUser.setPwd(pwd);
		List<UpmUser> list = upmUserMapper.selectByExample(upmUser);
		logger.info("{}登陆成功!",mobile);
		
		if(list==null || list.size()==0){
			return new UpmUser();
		}
		return list.get(0);
	}


	/**
	 * @param mobile
	 * @param password
	 * @return
	 */
	public boolean signup(String mobile, String pwd){
		
		UpmUser upmUser = new UpmUser();
		upmUser.setMobile(mobile);
		
		List<UpmUser> list = upmUserMapper.selectByExample(upmUser);
		if (list!=null &&list.size()>0) {
			logger.warn("{}-用户已存在，请选择其它用户名!",mobile);
			return false;
		}
		
		int result = upmUserMapper.insertSelective(upmUser);
		logger.info("{}注册成功！",mobile);
		return result > 0 ? true : false;
	}

}
