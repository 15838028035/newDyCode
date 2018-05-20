package com.lj.cloud.secrity.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lj.cloud.secrity.dal.SecPrevilegeRelationMapper;
import com.lj.cloud.secrity.service.SecPrevilegeRelationService;
import com.weixindev.micro.serv.common.bean.RestAPIResult2;
import com.weixindev.micro.serv.common.bean.secrity.SecPrevilegeRelation;
import com.weixindev.micro.serv.common.msg.LayUiTableResultResponse;
import com.weixindev.micro.serv.common.pagination.Query;
@Service
public class SecPrevilegeRelationServiceImpl  implements SecPrevilegeRelationService{
	private Logger logger = LoggerFactory.getLogger(SecPrevilegeRelationServiceImpl.class);

	@Autowired
	private SecPrevilegeRelationMapper secPrevilegeRelationMapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return secPrevilegeRelationMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SecPrevilegeRelation secPrevilegeRelation){
		return secPrevilegeRelationMapper.insert(secPrevilegeRelation);
	}

	@Override
	public RestAPIResult2 insertSelective(Integer groupId,String roleIds) {
		RestAPIResult2 result=new RestAPIResult2<>();
		if (roleIds.length()>0&&roleIds!="") {
			String [] ids=roleIds.split(",");
			Integer num=0;
			Map<String, Object> map = new HashMap<String,Object>();
			for (int i = 0; i < ids.length; i++) {
				map.put("secAgId", groupId);
				map.put("secPrivilegeId", ids[i]);
				Query query = new Query(map);
				List<SecPrevilegeRelation> findList=this.selectByExample(query);
				if (findList!=null&&findList.size()>0) {
					SecPrevilegeRelation secPrevilege = findList.get(0);
					secPrevilege.setState(1);
					num+=secPrevilegeRelationMapper.updateByPrimaryKeySelective(secPrevilege);
				}else {
					SecPrevilegeRelation secPrevilege = new SecPrevilegeRelation();
					secPrevilege.setSecAgId(groupId);
					secPrevilege.setSecPrivilegeId(Integer.parseInt(ids[i]));
					num+=secPrevilegeRelationMapper.insertSelective(secPrevilege);
				}
				if(num==ids.length) {
					result.setDataCode("1");
					result.setRespMsg("权限分配成功");					
				}else {
					result.setDataCode("0");
					result.setRespMsg("权限分配中断...");
				}
			}
		}
		return result;
	}

	@Override
	public SecPrevilegeRelation selectByPrimaryKey(Integer id) {
		return secPrevilegeRelationMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(SecPrevilegeRelation secPrevilegeRelation) {
		return secPrevilegeRelationMapper.updateByPrimaryKeySelective(secPrevilegeRelation);
	}

	@Override
	public int updateByPrimaryKey(SecPrevilegeRelation secPrevilegeRelation) {
		return secPrevilegeRelationMapper.updateByPrimaryKey(secPrevilegeRelation);
	}

	@Override
	 public LayUiTableResultResponse selectByQuery(Query query) {
	        com.github.pagehelper.Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
	        List<Map<String,Object>> list  = secPrevilegeRelationMapper.selectByPageExample(query);
	        return new LayUiTableResultResponse(result.getTotal(), list);
	}

	@Override
	public List<SecPrevilegeRelation> selectByExample(Query query) {
		return secPrevilegeRelationMapper.selectByExample(query);
	}
	

}
