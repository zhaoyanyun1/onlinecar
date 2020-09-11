package com.fty.onlinecar.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.fty.onlinecar.entity.Users;
import com.fty.onlinecar.response.Result;
import com.fty.onlinecar.response.ResultEnum;
import com.fty.onlinecar.response.ResultGenerator;
import com.fty.onlinecar.service.AccountService;
import com.fty.onlinecar.service.UsersService;
import com.fty.onlinecar.utils.WechatUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
        if(account.getState().equals("2")){
            return ResultGenerator.genResult(ResultEnum.UNDER_EVIEW);
        }
        return ResultGenerator.genSuccessResult(account);
    }


    @ApiOperation(value = "乘客登录", tags = {"Login"}, notes = "登录")
    @PostMapping(value = "/passengerLogin", name = "乘客登录")
    @ResponseBody
    public Result passengerLogin(@RequestBody Users data) {
        if(StringUtils.isEmpty(data.getPhone())){
            return ResultGenerator.genResult(ResultEnum.PHONE_NULL);
        }
        Users account = usersService.findBy("phone",data.getPhone());
        if(account==null){
            account = usersService.addPassenger(data);
        }
        if(!account.getType().equals(data.getType())){
            return ResultGenerator.genResult(ResultEnum.PHONE_NO);
        }
        return ResultGenerator.genSuccessResult(account);
    }


    @PostMapping(value = "/getSessionKey", name = "获取登录信息")
    @ResponseBody
    public Result getSessionKey(@RequestParam("code") String code){
        String appid ="wx987e5963be72e738";
        String secret = "48ee2fe9bbf33430df9635533a7a50a4";
        String res= HttpUtil.get("https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code");
        JSONObject jsonObject = JSONObject.parseObject(res);
        return ResultGenerator.genSuccessResult(jsonObject);

    }

    @PostMapping(value = "/getEncryptedData", name = "获取登录信息")
    @ResponseBody
    public Result getEncryptedData(@RequestParam("encryptDataB64") String encryptDataB64,@RequestParam("sessionKeyB64") String sessionKeyB64,@RequestParam("ivB64") String ivB64){
        String res = WechatUtil.decryptData(encryptDataB64,sessionKeyB64,ivB64);
        JSONObject jsonObject = JSONObject.parseObject(res);
        return ResultGenerator.genSuccessResult(jsonObject);

    }
}
