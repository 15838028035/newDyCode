package com.lj.cloud.secrity.dal;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.report.WeixinReportArticle;
import com.weixindev.micro.serv.common.pagination.Query;


public interface WeixinReportArticleMapper  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinReportArticle weixinReportArticle);

    int insertSelective(WeixinReportArticle weixinReportArticle);

    WeixinReportArticle selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinReportArticle weixinReportArticle);

    int updateByPrimaryKey(WeixinReportArticle weixinReportArticle);


   /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<WeixinReportArticle> selectByExample(Object mapAndObject);

   /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<Map<String,Object>> selectByPageExample(Object mapAndObject);
    
    /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<Map<String,Object>> selectByPageExample2(Object mapAndObject);
    /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<Map<String,Object>> selectByPageExample3(Object mapAndObject);
    
    /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<Map<String,Object>> selectByPageExample4(Object mapAndObject);

	Integer selectByPageExample5(Query query);
    
   Integer selectByPageExample5(Object mapAndObject);
    
}
