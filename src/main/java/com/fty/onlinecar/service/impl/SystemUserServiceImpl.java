package com.fty.onlinecar.service.impl;

import com.fty.onlinecar.base.service.AbstractService;
import com.fty.onlinecar.dao.SystemUserDao;
import com.fty.onlinecar.entity.SystemUser;
import com.fty.onlinecar.service.SystemUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class SystemUserServiceImpl extends AbstractService<SystemUser> implements SystemUserService {

    @Resource
    private SystemUserDao systemUserDao;

    @Override
    public List<SystemUser> all() {
        return systemUserDao.all();
    }
}
