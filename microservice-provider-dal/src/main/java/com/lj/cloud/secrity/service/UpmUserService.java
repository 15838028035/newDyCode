package com.lj.cloud.secrity.service;

import com.weixindev.micro.serv.common.bean.secrity.UpmUser;

public interface UpmUserService {

	 	int deleteByPrimaryKey(Integer id);

	    int insert(UpmUser record);

	    int insertSelective(UpmUser record);

	    UpmUser selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(UpmUser record);

	    int updateByPrimaryKey(UpmUser record);
	    
	    /**
		 * @param mobile
		 * @param pwd
		 * @return
		 */
	    public UpmUser login(String mobile, String pwd);
		
	    /**
		 * @param mobile
		 * @param password
		 * @return
		 */
		public boolean signup(String mobile, String pwd);
}
