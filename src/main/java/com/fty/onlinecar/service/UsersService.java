package com.fty.onlinecar.service;
import com.fty.onlinecar.entity.Users;
import com.fty.onlinecar.base.service.Service;
import com.fty.onlinecar.response.Result;

/**
 * Created by wanghuiwen on 2020/08/06.
 */
public interface UsersService extends Service<Users> {
   Result list(String search, String order, Integer page, Integer size);
}
