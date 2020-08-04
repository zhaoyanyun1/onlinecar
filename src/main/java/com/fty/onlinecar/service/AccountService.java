package com.fty.onlinecar.service;
import com.fty.onlinecar.entity.Account;
import com.fty.onlinecar.base.service.Service;
import com.fty.onlinecar.response.Result;

/**
 * Created by wanghuiwen on 2020/08/04.
 */
public interface AccountService extends Service<Account> {
   Result list(String search, String order, Integer page, Integer size);
}
