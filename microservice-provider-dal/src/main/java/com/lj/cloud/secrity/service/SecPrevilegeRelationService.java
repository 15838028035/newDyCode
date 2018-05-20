package com.lj.cloud.secrity.service;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.secrity.SecPrevilegeRelation;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;


public interface SecPrevilegeRelationService  {

    int deleteByPrimaryKey(Integer id);

    int insert(SecPrevilegeRelation secPrevilegeRelation);

    RestAPIResult2 insertSelective(Integer groupId,String roleIds);

    SecPrevilegeRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SecPrevilegeRelation secPrevilegeRelation);

    int updateByPrimaryKey(SecPrevilegeRelation secPrevilegeRelation);

    public LayUiTableResultResponse	 selectByQuery(Query query) ;

    public  List<SecPrevilegeRelation> selectByExample(Query query);

}
