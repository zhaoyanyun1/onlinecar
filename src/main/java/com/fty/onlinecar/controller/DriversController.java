package com.fty.onlinecar.controller;

import com.fty.onlinecar.entity.Account;
import com.fty.onlinecar.entity.Drivers;
import com.fty.onlinecar.response.Result;
import com.fty.onlinecar.response.ResultEnum;
import com.fty.onlinecar.response.ResultGenerator;
import com.fty.onlinecar.response.Table;
import com.fty.onlinecar.service.AccountService;
import com.fty.onlinecar.service.DriversService;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;



/**
* Created by wanghuiwen on 2020/07/27.
*/
@Api(value = "Drivers", tags = {"Drivers"})
@Controller
@RequestMapping("/drivers")
public class DriversController{
    @Resource
    private DriversService driversService;
    @Resource
    private AccountService accountService;

    @ApiOperation(value = "Drivers添加", tags = {"Drivers"}, notes = "Drivers添加")
    @PostMapping(value="/add",name="Drivers添加")
    @ResponseBody
    public Result add(@RequestBody Drivers data) {
        if(StringUtils.isEmpty(data.getPhone())){
            return ResultGenerator.genResult(ResultEnum.PHONE_NULL);
        }
        Account account = accountService.findBy("phone",data.getPhone());
        if(account!=null){
            return ResultGenerator.genResult(ResultEnum.PHONE_HAVE);
        }
        driversService.save(data);
        account = new Account();
        account.setPhone(data.getPhone());
        account.setPassword("123456");
        account.setState("1");
        account.setType("1");
        accountService.save(account);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "Drivers删除", tags = {"Drivers"}, notes = "Drivers删除")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",required=true, value = "Driversid", dataType = "Long", paramType = "query")
    })
    @PostMapping(value="/delete",name="Drivers删除")
    @ResponseBody
    public Result delete(@RequestParam Long id) {
        driversService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "Drivers修改", tags = {"Drivers"}, notes = "Drivers修改,对象主键必填")
    @PostMapping(value="/update",name="Drivers修改")
    @ResponseBody
    public Result update(@ApiParam Drivers drivers) {
        driversService.update(drivers);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "Drivers详细信息", tags = {"Drivers"}, notes = "Drivers详细信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",required=true, value = "Driversid", dataType = "Long", paramType = "query")
    })
    @PostMapping(value="/detail",name="Drivers详细信息")
    @ResponseBody
    public Result detail(@RequestParam Integer id) {
        Drivers drivers = driversService.findById(id);
        return ResultGenerator.genSuccessResult(drivers);
    }

    @ApiOperation(value = "Drivers列表信息", tags = {"Drivers"}, notes = "Drivers列表信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "search", value = "查询条件json", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "排序json", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "page", value = "页码", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "size", value = "每页显示的条数", dataType = "String", paramType = "query", defaultValue = "10")
    })
    @PostMapping(value = "/list", name = "Drivers列表信息")
    @ResponseBody
    public Table list(@RequestParam(defaultValue = "{}") String search,
                      @RequestParam(defaultValue = "{}") String order,
                      @RequestParam(defaultValue = "0") Integer page,
                      @RequestParam(defaultValue = "10") Integer size) {
        List<Map<String, Object>> list = driversService.list(search, order, page, size);
        Table table = new Table();
        table.setData(list);
        table.setCount(list.size());
        return table;
    }


    @GetMapping(value = "/driversManage")
    public String toManage(){
        return "drivers/driversManage";
    }

    @GetMapping(value = "/toAdd")
    public String toAdd(){
        return "drivers/add";
    }
}
