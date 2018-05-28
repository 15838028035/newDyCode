package com.lj.cloud.secrity.dal;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.weixin.WeixinArticleTask;


public interface WeixinArticleTaskMapper  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinArticleTask weixinArticleTask);

    int insertSelective(WeixinArticleTask weixinArticleTask);

    WeixinArticleTask selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinArticleTask weixinArticleTask);

    int updateBySelective(WeixinArticleTask weixinArticleTask);
    int updateByPrimaryKey(WeixinArticleTask weixinArticleTask);

   
    String selectMapKeyByExample(Object mapAndObject);
   /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<WeixinArticleTask> selectByExample(Object mapAndObject);

   /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<Map<String,Object>> selectByPageExample(Object mapAndObject);
    List<WeixinArticleTask> selectByTime(String dateStr);
}
