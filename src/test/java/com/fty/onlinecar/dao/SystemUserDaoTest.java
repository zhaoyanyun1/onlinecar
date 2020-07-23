package com.fty.onlinecar.dao;

import com.fty.onlinecar.entity.SystemUser;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class SystemUserDaoTest {

    @Autowired
    private SystemUserDao systemUserDao;

    @Test
    void findAll() {
        List<SystemUser> res = systemUserDao.selectAll();
        System.out.println(res);

    }
}