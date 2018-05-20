package com.lj.cloud.secrity.dal;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.secrity.SecPrevilegeRelation;


public interface SecPrevilegeRelationMapper  {

    int deleteByPrimaryKey(Integer id);

    int insert(SecPrevilegeRelation secPrevilegeRelation);

    int insertSelective(SecPrevilegeRelation secPrevilegeRelation);

    SecPrevilegeRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SecPrevilegeRelation secPrevilegeRelation);

    int updateByPrimaryKey(SecPrevilegeRelation secPrevilegeRelation);

   

   /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<SecPrevilegeRelation> selectByExample(Object mapAndObject);

   /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<Map<String,Object>> selectByPageExample(Object mapAndObject);

}
