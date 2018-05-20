package com.lj.cloud.secrity.service;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.report.WeixinReportArticle;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;


public interface WeixinReportArticleService  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinReportArticle weixinReportArticle);

    int insertSelective(WeixinReportArticle weixinReportArticle);

    WeixinReportArticle selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinReportArticle weixinReportArticle);

    int updateByPrimaryKey(WeixinReportArticle weixinReportArticle);

    public LayUiTableResultResponse	 selectByQuery(Query query) ;
    
    public LayUiTableResultResponse	 selectByQuery2(Query query) ;
    
    public  List<WeixinReportArticle> selectByExample(Query query);
    
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

	public Integer selectByPageExample5(Object mapAndObject);
}
