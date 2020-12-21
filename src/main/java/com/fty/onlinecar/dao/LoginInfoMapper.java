package com.fty.onlinecar.dao;

import com.fty.onlinecar.base.dao.BaseDao;
import com.fty.onlinecar.entity.LoginInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoginInfoMapper extends BaseDao<LoginInfo> {

    LoginInfo getOneByUserId(String userId);
    List<LoginInfo> findByUserId(String userId);
}