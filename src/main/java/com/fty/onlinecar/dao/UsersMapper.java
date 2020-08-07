package com.fty.onlinecar.dao;

import com.fty.onlinecar.base.dao.BaseDao;
import com.fty.onlinecar.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UsersMapper extends BaseDao<Users> {

    List<Users> listTest();

    List<Map<String, Object>> list(@Param("params") Map<String, Object> params, @Param("order") Map<String, Object> order);
}