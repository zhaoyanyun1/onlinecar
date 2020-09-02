package com.fty.onlinecar.controller;

import com.fty.onlinecar.entity.Users;
import com.fty.onlinecar.response.Result;
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




    @ApiOperation(value = "Drivers添加", tags = {"Drivers"}, notes = "Drivers添加")
    @PostMapping(value = "/addDriver", name = "Drivers添加")
    @ResponseBody
    public Result addDriver(@RequestBody Users users) {
        users.setInvitationCode(users.getPhone());
        users.setType(1);
        users.setPassword("123456");
        usersService.save(users);
        return ResultGenerator.genSuccessResult();
    }



    @ApiOperation(value = "Drivers添加", tags = {"Drivers"}, notes = "Drivers添加")
    @PostMapping(value = "/addPassenger", name = "Drivers添加")
    @ResponseBody
    public Result addPassenger(@RequestBody Users users) {
        users.setInvitationCode(users.getPhone());

        //判读用户是否填了邀请码，邀请人是否存在
        if(users.getInvitees()!=null){
            Users invitees = usersService.findBy("invitationCode",users.getInvitees());
            if(invitees == null){
                //Todo 返回邀请码错误
                return ResultGenerator.genFailResult();
            }

            integralDetailService.addIntegral(invitees,1,"邀请用户");

        }
        users.setState("1");
        users.setType(2);
        usersService.save(users);
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
    public Result shareAddIntegral(@RequestParam("userId") Integer usersId) {
        Users users = usersService.findById(usersId);
        integralDetailService.addIntegral(users,1,"分享链接");
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





    @ApiOperation(value = "Users详细信息", tags = {"Users"}, notes = "Users详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "Usersid", dataType = "Long", paramType = "query")
    })
    @PostMapping(value = "/detail", name = "Users详细信息")
    public Result detail(@RequestParam Integer id) {
        Users users = usersService.findById(id);
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
