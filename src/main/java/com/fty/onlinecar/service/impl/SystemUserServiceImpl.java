package com.fty.onlinecar.service.impl;

import com.fty.onlinecar.base.service.AbstractService;
import com.fty.onlinecar.entity.SystemUser;
import com.fty.onlinecar.service.SystemUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SystemUserServiceImpl extends AbstractService<SystemUser> implements SystemUserService {

}
