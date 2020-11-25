package com.fty.onlinecar.service.impl;

import com.fty.onlinecar.base.service.AbstractService;
import com.fty.onlinecar.dao.UsersMapper;
import com.fty.onlinecar.entity.Users;
import com.fty.onlinecar.service.UsersService;
import com.fty.onlinecar.utils.JSONUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghuiwen on 2020/08/06.
 */
@Service
@Transactional
public class UsersServiceImpl extends AbstractService<Users> implements UsersService {
    @Resource
    private UsersMapper usersMapper;

    @Override
    public Page<Map<String, Object>> list(String search, String order, Integer page, Integer size){
        Map<String, Object> params = JSONUtils.json2map(search);
        Map<String, Object> orderParams = JSONUtils.json2map(order);
        for (String key : orderParams.keySet()) {
                if (orderParams.get(key) != null && orderParams.get(key).equals("ascending")) orderParams.put(key, "asc");
                if (orderParams.get(key) != null && orderParams.get(key).equals("descending")) orderParams.put(key, "desc");
            }
        PageHelper.startPage(page, size);
        List<Map<String, Object>> res = usersMapper.list(params, orderParams);
        Page<Map<String, Object>> pageList = (Page<Map<String, Object>>) res;
        return pageList;
    }

    @Override
    public Users addPassenger(Users users) {

        users.setInvitationCode(users.getPhone());
        users.setState("1");
        users.setType(2);
        this.save(users);
        return users;
    }
}
