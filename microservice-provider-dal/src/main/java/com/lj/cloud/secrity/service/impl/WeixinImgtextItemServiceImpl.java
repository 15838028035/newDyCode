package com.lj.cloud.secrity.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lj.cloud.secrity.dal.WeixinImgtextItemMapper;
import com.lj.cloud.secrity.service.WeixinImgtextItemService;
import com.weixindev.micro.serv.common.bean.weixin.WeixinImgtextItem;
import com.weixindev.micro.serv.common.bean.weixin.WeixinImgtextItemVO;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
@Service
public class WeixinImgtextItemServiceImpl  implements WeixinImgtextItemService{
	private Logger logger = LoggerFactory.getLogger(WeixinImgtextItemServiceImpl.class);

	@Autowired
	private WeixinImgtextItemMapper weixinImgtextItemMapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return weixinImgtextItemMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(WeixinImgtextItem weixinImgtextItem){
		return weixinImgtextItemMapper.insert(weixinImgtextItem);
	}

	@Override
	public int insertSelective(WeixinImgtextItem weixinImgtextItem) {
		return weixinImgtextItemMapper.insertSelective(weixinImgtextItem);
	}

	@Override
	public WeixinImgtextItem selectByPrimaryKey(Integer id) {
		return weixinImgtextItemMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(WeixinImgtextItem weixinImgtextItem) {
		return weixinImgtextItemMapper.updateByPrimaryKeySelective(weixinImgtextItem);
	}

	@Override
	public int updateByPrimaryKey(WeixinImgtextItem weixinImgtextItem) {
		return weixinImgtextItemMapper.updateByPrimaryKey(weixinImgtextItem);
	}

	@Override
	 public LayUiTableResultResponse selectByQuery(Query query) {
	        com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<Map<String,Object>> list  = weixinImgtextItemMapper.selectByPageExample(query);
	        return new LayUiTableResultResponse(result.getTotal(), list);
	}
	
	@Override
	 public LayUiTableResultResponse selectByQuery2(Query query) {
	        com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<WeixinImgtextItemVO> list  = weixinImgtextItemMapper.selectDistinctTmpId(query);
	        //获得模板信息 
	        
	        for(WeixinImgtextItemVO WeixinImgtextItemVO: list) {
	        	
	        	if(WeixinImgtextItemVO!=null){
		        	Map<String,Object> map2 = new HashMap<String,Object>();
		        	map2.put("imgTextId", WeixinImgtextItemVO.getImgTextId());
		        	Query query2 = new Query(map2);
		        	
		        	 List<WeixinImgtextItem> weixinImgtextItemList  = weixinImgtextItemMapper.selectByExample(query2);
		        	 WeixinImgtextItemVO.setWeixinImgtextItemList(weixinImgtextItemList);
	        	}
	        }
	        return new LayUiTableResultResponse(result.getTotal(), list);
	}
	
	 public LayUiTableResultResponse selectByQueryEver(Query query) {
	        com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<WeixinImgtextItemVO> list  = weixinImgtextItemMapper.selectDistinctTmpId(query);
	        //获得模板信息 
	        
	        for(WeixinImgtextItemVO WeixinImgtextItemVO: list) {
	        	
	        	if(WeixinImgtextItemVO!=null){
		        	Map<String,Object> map2 = new HashMap<String,Object>();
		        	map2.put("imgTextId", WeixinImgtextItemVO.getImgTextId());
		        	Query query2 = new Query(map2);
		        	
		        	 List<WeixinImgtextItem> weixinImgtextItemList  = weixinImgtextItemMapper.selectByExample(query2);
		        	 WeixinImgtextItemVO.setWeixinImgtextItemList(weixinImgtextItemList);
	        	}
	        }
	        return new LayUiTableResultResponse(result.getTotal(), list);
	}

	@Override
	public List<WeixinImgtextItem> selectByExample(Query query) {
		return weixinImgtextItemMapper.selectByExample(query);
	}

	@Override
	public int batchDelete(String id) {
		return weixinImgtextItemMapper.batchDelete(id);
	}

	@Override
	public Integer deleteByTmpId(String tempId) {
		return weixinImgtextItemMapper.deleteByTmpId(tempId);
	}

	@Override
	@Transactional
	public void  moveFromAToB(Integer a, Integer b) {
		
		WeixinImgtextItem WeixinImgtextItemA = selectByPrimaryKey(a);
		WeixinImgtextItem WeixinImgtextItemB = selectByPrimaryKey(b);
		
		if(WeixinImgtextItemA !=null && WeixinImgtextItemB!=null){
			WeixinImgtextItem WeixinImgtextItemC = new WeixinImgtextItem();
			
			WeixinImgtextItemC.setSortNo(WeixinImgtextItemA.getSortNo());
			
			WeixinImgtextItemA.setSortNo(WeixinImgtextItemB.getSortNo());
			WeixinImgtextItemB.setSortNo(WeixinImgtextItemC.getSortNo());
			
			updateByPrimaryKeySelective(WeixinImgtextItemA);
			updateByPrimaryKeySelective(WeixinImgtextItemB);
		}
		
	}

	@Override
	public List<WeixinImgtextItem> selectByTemplateimdId(Integer templateid) {
		// TODO Auto-generated method stub
		return weixinImgtextItemMapper.selectByTemplateimdId(templateid);
	}

}
