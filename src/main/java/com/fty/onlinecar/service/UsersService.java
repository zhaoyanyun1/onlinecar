package com.fty.onlinecar.service;

import com.fty.onlinecar.base.service.Service;
import com.fty.onlinecar.entity.Users;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by wanghuiwen on 2020/08/06.
 */
public interface UsersService extends Service<Users> {
   Page<Map<String, Object>> list(String search, String order, Integer page, Integer size);
   Users addPassenger(Users users);
}
