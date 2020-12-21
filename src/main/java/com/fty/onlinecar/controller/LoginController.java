package com.fty.onlinecar.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.fty.onlinecar.entity.LoginAndUsers;
import com.fty.onlinecar.entity.LoginInfo;
import com.fty.onlinecar.entity.Users;
import com.fty.onlinecar.response.Result;
import com.fty.onlinecar.response.ResultEnum;
import com.fty.onlinecar.response.ResultGenerator;
import com.fty.onlinecar.service.AccountService;
import com.fty.onlinecar.service.IntegralDetailService;
import com.fty.onlinecar.service.LoginInfoService;
import com.fty.onlinecar.service.UsersService;
import com.fty.onlinecar.utils.WechatUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "Login", tags = {"Login"})
@Controller
@RequestMapping("/login")
public class LoginController {
    @Resource
    private AccountService accountService;
    @Resource
    private UsersService usersService;

    @Resource
    private LoginInfoService loginInfoService;
    @Resource
    private IntegralDetailService integralDetailService;


    @PostMapping(value = "/getLoginInfo", name = "获取登录信息")
    @ResponseBody
    public Result getLoginInfo(@RequestParam("userId") String userId){


        LoginInfo loginInfo = loginInfoService.getOneByUserId(userId);
        return ResultGenerator.genSuccessResult(loginInfo);

    }


    @ApiOperation(value = "登录", tags = {"Login"}, notes = "登录")
    @PostMapping
    @ResponseBody
    public Result Login(@RequestBody LoginAndUsers data) {
        if(StringUtils.isEmpty(data.getUsers().getPhone())){
            return ResultGenerator.genResult(ResultEnum.PHONE_NULL);
        }
        Users account = usersService.findBy("phone",data.getUsers().getPhone());
        if(account==null){
            return ResultGenerator.genResult(ResultEnum.PHONE_NO);
        }
        if(!account.getType().equals(data.getUsers().getType())){
            return ResultGenerator.genResult(ResultEnum.PHONE_NO);
        }
        if(!account.getPassword().equals(data.getUsers().getPassword())){
            return ResultGenerator.genResult(ResultEnum.PASSWORD_ERROR);
        }
        if(account.getState().equals("2")){
            return ResultGenerator.genResult(ResultEnum.UNDER_EVIEW);
        }
        if(account.getState().equals("0")){
            return ResultGenerator.genResult(ResultEnum.PHONE_NULL);
        }

        List<LoginInfo> loginInfoList = loginInfoService.findByUserId(String.valueOf(account.getId()));
        if(!loginInfoList.isEmpty()){
            String ids = "";
            for (LoginInfo login: loginInfoList) {
                ids = ids+login.getId()+",";

            }
            ids = ids.substring(0,ids.length()-1);
            loginInfoService.deleteByIds(ids);
        }


        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserId(account.getId());
        loginInfo.setAccessToken(data.getLoginInfo().getAccessToken());
        loginInfo.setOpenId(data.getLoginInfo().getOpenId());
        loginInfoService.save(loginInfo);
        return ResultGenerator.genSuccessResult(account);
    }


    @ApiOperation(value = "乘客登录", tags = {"Login"}, notes = "登录")
    @PostMapping(value = "/passengerLogin", name = "乘客登录")
    @ResponseBody
    public Result passengerLogin(@RequestBody LoginAndUsers data) {


        if(StringUtils.isEmpty(data.getUsers().getPhone())){
            return ResultGenerator.genResult(ResultEnum.PHONE_NULL);
        }
        Users account = usersService.findBy("phone",data.getUsers().getPhone());
        if(account==null){
            System.out.println("新乘客注册:"+data.getUsers().getPhone());
            if(StringUtils.isNotEmpty(data.getUsers().getInvitees())){
                System.out.println("乘客邀请人："+data.getUsers().getInvitees()+"；");
                Users invitees = usersService.findById(data.getUsers().getInvitees());
                System.out.println("邀请人类型："+invitees.getType()+";邀请人名称："+invitees.getName()+";邀请人积分："+invitees.getIntegral());
                if(invitees!=null && invitees.getType()==1){
                    System.out.println("邀请人增加积分");
                    integralDetailService.addIntegral(invitees,1,"邀请乘客注册");
                }
            }

            account = usersService.addPassenger(data.getUsers());
        }
        if(!account.getType().equals(data.getUsers().getType())){
            return ResultGenerator.genResult(ResultEnum.PHONE_NO);
        }

        List<LoginInfo> loginInfoList = loginInfoService.findByUserId(String.valueOf(account.getId()));
        if(!loginInfoList.isEmpty()){
            String ids = "";
            for (LoginInfo login: loginInfoList) {
                ids = ids+login.getId()+",";

            }
            ids = ids.substring(0,ids.length()-1);
            loginInfoService.deleteByIds(ids);
        }

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserId(account.getId());
        loginInfo.setAccessToken(data.getLoginInfo().getAccessToken());
        loginInfo.setOpenId(data.getLoginInfo().getOpenId());
        loginInfoService.save(loginInfo);

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

    @PostMapping(value = "/getAccessToken", name = "获取登录信息")
    @ResponseBody
    public Result getAccessToken(){
        String appid ="wx987e5963be72e738";
        String secret = "48ee2fe9bbf33430df9635533a7a50a4";
        String res= HttpUtil.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret);
        JSONObject jsonObject = JSONObject.parseObject(res);
        return ResultGenerator.genSuccessResult(jsonObject);

    }



    @PostMapping(value = "/getQrcode", name = "获取登录信息")
    @ResponseBody
    public Result getQrcode(@RequestParam("access_token") String access_token,@RequestParam("userId") String userId){


        String res = WechatUtil.post(access_token,userId);
        return ResultGenerator.genSuccessResult(res);

    }


    @PostMapping(value = "/sendMessage", name = "获取登录信息")
    @ResponseBody
    public Result sendMessage(@RequestParam("access_token") String access_token,@RequestParam("openId") String openId,@RequestParam("templateId") String templateId,@RequestParam("data") String data){

        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=";
        String template_id = "p3efgYnUWStvLEKCSqznWlpq5VPnUFoJPQdKYQfThDs";

//        String data= "{\"thing1\":{\"value\":\"太原\"},\"thing3\":{\"value\":\"阳泉\"}}";

        String body = "{\n" +
                "  \"touser\": \""+openId+"\",\n" +
                "  \"template_id\": \""+templateId+"\",\n" +
                "\"page\":\"/pages/main/main\",\n"+
                "  \"miniprogram_state\":\"formal\",\n" +
                "  \"lang\":\"zh_CN\",\n" +
                "  \"data\": "+data+
                "}\n";

        System.out.println("发送消息推送：");
        System.out.println("access_token："+access_token);
        System.out.println("body："+body);
        String res= HttpUtil.post(url+access_token,body);
        System.out.println(res);
        return ResultGenerator.genSuccessResult(res);

    }
}
