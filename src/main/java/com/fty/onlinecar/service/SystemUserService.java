package com.fty.onlinecar.service;

import com.fty.onlinecar.base.service.Service;
import com.fty.onlinecar.entity.SystemUser;

import java.util.List;

public interface SystemUserService extends Service<SystemUser> {

    List<SystemUser> all();
}
