package com.lj.cloud.secrity.dal;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.weixindev.micro.serv.common.bean.secrity.SecGroups;

public interface SecGroupsMapper {
	
	
    int deleteByPrimaryKey(Integer id);

    int insert(SecGroups record);

    int insertSelective(SecGroups record);

    SecGroups selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SecGroups record);

    int updateByPrimaryKey(SecGroups record);
    
    /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<SecGroups> selectByExample(Object mapAndObject);
    
    /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<Map<String,Object>> selectByPageExample(Object mapAndObject);

	List<Map<String, Object>> selectGrouInfoByLogin(@Param("loginNo")String loginNo);

	List<Map<String, Object>> selectByInfoKey(Integer id);

	List<Map<String, Object>> selectByInfoKeyData();

	void deleteByPrimaryGroupId(Integer id);

	void insertURL(@Param("gid")Integer gid,@Param("ruiId") Integer ruiId);

	List<Integer> selectGrouipId(Integer id);

	List<Integer> getParentId();

	List<Map<String, Object>> getClassInfoByparaenId(Integer parentId);

	Map<String, Object> getGroupInfoByid(Integer parentId);

	List<Map<String, Object>> selectGroupInfoByPareintId(@Param("pids")List<Integer> pids);
	
	



}