package com.fty.onlinecar.controller;

import com.fty.onlinecar.entity.Users;
import com.fty.onlinecar.response.Result;
import com.fty.onlinecar.response.ResultEnum;
import com.fty.onlinecar.response.ResultGenerator;
import com.fty.onlinecar.response.Table;
import com.fty.onlinecar.service.IntegralDetailService;
import com.fty.onlinecar.service.UsersService;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * Created by wanghuiwen on 2020/08/06.
 */
@Api(value = "Users", tags = {"Users"})
@Controller
@RequestMapping("/users")
public class UsersController {
    @Resource
    private UsersService usersService;

    @Resource
    private IntegralDetailService integralDetailService;




    @ApiOperation(value = "司机添加", tags = {"Drivers"}, notes = "司机添加")
    @PostMapping(value = "/addDriver", name = "司机添加")
    @ResponseBody
    public Result addDriver(@RequestBody Users users) {
        Users users1 = usersService.findBy("phone",users.getPhone());
        if(users1 == null){
            users.setInvitationCode(users.getPhone());
            users.setType(1);
            usersService.save(users);
            return ResultGenerator.genSuccessResult();
        }else {
            if(users1.getState().equals("1")){
                return ResultGenerator.genFailResult(ResultEnum.PHONE_HAVE);
            }else if(users1.getState().equals("2")){
                return ResultGenerator.genFailResult(ResultEnum.UNDER_EVIEW);
            }else if(users1.getState().equals("3")){
                users.setId(users1.getId());
                usersService.update(users);
                return ResultGenerator.genSuccessResult();
            }

            return ResultGenerator.genFailResult();
        }

    }



    @ApiOperation(value = "乘客添加", tags = {"Drivers"}, notes = "乘客添加")
    @PostMapping(value = "/addPassenger", name = "乘客添加")
    @ResponseBody
    public Result addPassenger(@RequestBody Users users) {

        usersService.addPassenger(users);



        return ResultGenerator.genSuccessResult();
    }





    @ApiOperation(value = "Users删除", tags = {"Users"}, notes = "Users删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "Usersid", dataType = "Long", paramType = "query")
    })
    @PostMapping(value = "/delete", name = "Users删除")
    @ResponseBody
    public Result delete(@RequestParam Long id) {
        usersService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }





    @ApiOperation(value = "Users修改", tags = {"Users"}, notes = "Users修改,对象主键必填")
    @PostMapping(value = "/update", name = "Users修改")
    public Result update(@ApiParam Users users) {
        usersService.update(users);
        return ResultGenerator.genSuccessResult();
    }


    @ApiOperation(value = "Users修改", tags = {"Users"}, notes = "Users修改,对象主键必填")
    @PostMapping(value = "/shareAddIntegral", name = "Users修改")
    @ResponseBody
    public Result shareAddIntegral(@RequestParam("userId") Integer usersId) {
        Users users = usersService.findById(usersId);
        integralDetailService.addIntegral(users,1,"分享链接");
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "Users修改", tags = {"Users"}, notes = "Users修改,对象主键必填")
    @PostMapping(value = "/updatePassword", name = "Users修改")
    @ResponseBody
    public Result updatePassword(@RequestBody Object requestData) {
        Map<String,Object> map =(Map) requestData;
        Users users = usersService.findById(map.get("userId"));
        if(!users.getPassword().equals(map.get("oldPassword"))){
            return ResultGenerator.genResult(ResultEnum.OLDPASSWORD_FAIL);
        }
        users.setPassword(map.get("newPassword").toString());
        usersService.update(users);
        return ResultGenerator.genSuccessResult();
    }



    @ApiOperation(value = "充值", tags = {"Users"}, notes = "Users修改,对象主键必填")
    @PostMapping(value = "/recharge", name = "充值")
    @ResponseBody
    public Result recharge(@RequestBody Users users) {
        Users users1 = usersService.findById(users.getId());
        String balance = String.valueOf(Integer.parseInt(users1.getBalance()) + Integer.parseInt(users.getBalance()));
        users1.setBalance(balance);
        usersService.update(users1);
        return ResultGenerator.genSuccessResult();
    }


    @ApiOperation(value = "审核通过", tags = {"Users"}, notes = "Users修改,对象主键必填")
    @PostMapping(value = "/pass", name = "审核通过")
    @ResponseBody
    public Result pass(@RequestParam("id") Integer id) {
        Users users1 = usersService.findById(id);
        users1.setState("1");
        usersService.update(users1);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "审核拒绝", tags = {"Users"}, notes = "Users修改,对象主键必填")
    @PostMapping(value = "/no", name = "审核拒绝")
    @ResponseBody
    public Result no(@RequestParam("id") Integer id) {
        Users users1 = usersService.findById(id);
        users1.setState("3");
        usersService.update(users1);
        return ResultGenerator.genSuccessResult();
    }




    @ApiOperation(value = "Users详细信息", tags = {"Users"}, notes = "Users详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "Usersid", dataType = "Long", paramType = "query")
    })
    @PostMapping(value = "/getUserInfo", name = "Users详细信息")
    @ResponseBody
    public Result detail(@RequestParam("userId") Integer usersId) {
        Users users = usersService.findById(usersId);
        return ResultGenerator.genSuccessResult(users);
    }




    @ApiOperation(value = "Users列表信息", tags = {"Users"}, notes = "Users列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "search", value = "查询条件json", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "排序json", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示的条数", dataType = "String", paramType = "query", defaultValue = "10")
    })
    @PostMapping(value = "/drivers", name = "Users列表信息")
    @ResponseBody
    public Table list(@RequestParam(defaultValue = "{}") String search,
                      @RequestParam(defaultValue = "{}") String order,
                      @RequestParam(defaultValue = "0") Integer page,
                      @RequestParam(defaultValue = "10") Integer size) {

        List<Map<String, Object>> list = usersService.list(search, order, page, size);
        Table table = new Table();
        table.setData(list);
        table.setCount(list.size());
        return table;
    }


    @PostMapping(value = "/passenger", name = "Users列表信息")
    @ResponseBody
    public Table passenger(@RequestParam(defaultValue = "{}") String search,
                           @RequestParam(defaultValue = "{}") String order,
                           @RequestParam(defaultValue = "0") Integer page,
                           @RequestParam(defaultValue = "10") Integer size) {

        List<Map<String, Object>> list = usersService.list(search, order, page, size);
        Table table = new Table();
        table.setData(list);
        table.setCount(list.size());
        return table;
    }

    @GetMapping(value = "/driverManage")
    public String driverManage() {
        return "users/driverManage";
    }

    @GetMapping(value = "/passengerManage")
    public String passengerManage() {
        return "users/passengerManage";
    }

    @GetMapping(value = "/toAddDriver")
    public String toAdd() {
        return "users/addDriver";
    }




}
