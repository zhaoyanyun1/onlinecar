package com.fty.onlinecar.dao;

import com.fty.onlinecar.base.dao.BaseDao;
import com.fty.onlinecar.entity.SystemUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemUserDao extends BaseDao<SystemUser> {
    List<SystemUser> all();

}
