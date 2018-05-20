package com.lj.cloud.secrity.dal;

import java.util.List;
import java.util.Map;

import com.weixindev.micro.serv.common.bean.weixin.WeixinImgtextItem;
import com.weixindev.micro.serv.common.bean.weixin.WeixinImgtextItemVO;


public interface WeixinImgtextItemMapper  {

    int deleteByPrimaryKey(Integer id);

    int insert(WeixinImgtextItem weixinImgtextItem);

    int insertSelective(WeixinImgtextItem weixinImgtextItem);

    WeixinImgtextItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinImgtextItem weixinImgtextItem);

    int updateByPrimaryKey(WeixinImgtextItem weixinImgtextItem);

   

    List<WeixinImgtextItem> selectByExample(Object mapAndObject);
    List<Map<String,Object>> selectByPageExample(Object mapAndObject);
    
    List<WeixinImgtextItemVO> selectDistinctTmpId(Object mapAndObject);

	int batchDelete(String id);

	Integer deleteByTmpId(String tempId);

	List<WeixinImgtextItem> selectByTemplateimdId(Integer templateid);
}
