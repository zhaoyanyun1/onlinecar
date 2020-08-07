package com.fty.onlinecar.controller;

import com.fty.onlinecar.entity.Account;
import com.fty.onlinecar.entity.Drivers;
import com.fty.onlinecar.entity.Users;
import com.fty.onlinecar.response.Result;
import com.fty.onlinecar.response.ResultEnum;
import com.fty.onlinecar.response.ResultGenerator;
import com.fty.onlinecar.service.AccountService;
import com.fty.onlinecar.service.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(value = "Login", tags = {"Login"})
@Controller
@RequestMapping("/login")
public class LoginController {
    @Resource
    private AccountService accountService;
    @Resource
    private UsersService usersService;


    @ApiOperation(value = "登录", tags = {"Login"}, notes = "登录")
    @PostMapping
    @ResponseBody
    public Result Login(@RequestBody Users data) {
        if(StringUtils.isEmpty(data.getPhone())){
            return ResultGenerator.genResult(ResultEnum.PHONE_NULL);
        }
        Users account = usersService.findBy("phone",data.getPhone());
        if(account==null){
            return ResultGenerator.genResult(ResultEnum.PHONE_NO);
        }
        if(!account.getType().equals(data.getType())){
            return ResultGenerator.genResult(ResultEnum.PHONE_NO);
        }
        if(!account.getPassword().equals(data.getPassword())){
            return ResultGenerator.genResult(ResultEnum.PASSWORD_ERROR);
        }
        return ResultGenerator.genSuccessResult(account);
    }
}
