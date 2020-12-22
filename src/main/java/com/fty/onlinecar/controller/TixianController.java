package com.fty.onlinecar.controller;
import com.fty.onlinecar.entity.Users;
import com.fty.onlinecar.response.Result;
import com.fty.onlinecar.response.ResultGenerator;
import com.fty.onlinecar.entity.Tixian;
import com.fty.onlinecar.response.Table;
import com.fty.onlinecar.service.TixianService;
import com.fty.onlinecar.service.UsersService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.*;



/**
* Created by wanghuiwen on 2020/12/21.
*/
@Api(value = "TiXian", tags = {"TiXian"})
@Controller
@RequestMapping("/tixian")
public class TixianController{
    @Resource
    private TixianService tixianService;
    @Resource
    private UsersService usersService;

    @ApiOperation(value = "TiXian添加", tags = {"TiXian"}, notes = "TiXian添加")
    @PostMapping(value="/add",name="TiXian添加")
    @ResponseBody
    public Result add(@RequestBody Tixian tixian) {
        Users users = usersService.findById(tixian.getUserId());
        if(users.getIntegral()<tixian.getIntegral()){
            return ResultGenerator.genFailResult();
        }

        users.setIntegral(users.getIntegral()-tixian.getIntegral());
        usersService.update(users);

        tixian.setCreateTime(new Date());
        tixian.setUpdateTime(new Date());
        tixian.setState("1");
        tixianService.save(tixian);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "TiXian删除", tags = {"TiXian"}, notes = "TiXian删除")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",required=true, value = "TiXianid", dataType = "Long", paramType = "query")
    })
    @PostMapping(value="/delete",name="TiXian删除")
    public Result delete(@RequestParam Long id) {
        tixianService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "TiXian修改", tags = {"TiXian"}, notes = "TiXian修改,对象主键必填")
    @PostMapping(value="/update",name="TiXian修改")
    public Result update(@ApiParam Tixian tixian) {
        tixianService.update(tixian);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "TiXian详细信息", tags = {"TiXian"}, notes = "TiXian详细信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",required=true, value = "TiXianid", dataType = "Long", paramType = "query")
    })
    @PostMapping(value="/detail",name="TiXian详细信息")
    public Result detail(@RequestParam Integer id) {
        Tixian tixian = tixianService.findById(id);
        return ResultGenerator.genSuccessResult(tixian);
    }

    @ApiOperation(value = "TiXian列表信息", tags = {"TiXian"}, notes = "TiXian列表信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "search", value = "查询条件json", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "排序json", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "page", value = "页码", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "size", value = "每页显示的条数", dataType = "String", paramType = "query", defaultValue = "10")
    })
    @PostMapping(value = "/list", name = "TiXian列表信息")
    @ResponseBody
    public Table list(@RequestParam(defaultValue = "{}") String search,
                       @RequestParam(defaultValue = "{}") String order,
                       @RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "10") Integer size) {

        Page<Map<String, Object>> list = tixianService.list(search, order, page, size);
        Table table = new Table();
        table.setData(list);
        table.setCount((int) list.getTotal());
        return table;

//        return tixianService.list(search, order, page, size);
    }


    @ApiOperation(value = "TiXian列表信息", tags = {"TiXian"}, notes = "TiXian列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "search", value = "查询条件json", dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/userList", name = "TiXian列表信息")
    @ResponseBody
    public Table userList(@RequestParam(defaultValue = "{}") String search) {

        Page<Map<String, Object>> list = tixianService.list(search, null, null, null);
        Table table = new Table();
        table.setData(list);
        table.setCount((int) list.getTotal());
        return table;

//        return tixianService.list(search, order, page, size);
    }

    @ApiOperation(value = "TiXian成功", tags = {"TiXian"}, notes = "TiXian成功")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",required=true, value = "TiXianid", dataType = "Long", paramType = "query")
    })
    @PostMapping(value="/success",name="TiXian成功")
    @ResponseBody
    public Result success(@RequestParam Integer id) {
        Tixian tixian = tixianService.findById(id);
        tixian.setState("2");
        tixian.setUpdateTime(new Date());
        tixianService.update(tixian);
        return ResultGenerator.genSuccessResult(tixian);
    }

    @ApiOperation(value = "TiXian失败", tags = {"TiXian"}, notes = "TiXian失败")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",required=true, value = "TiXianid", dataType = "Long", paramType = "query")
    })
    @PostMapping(value="/fail",name="TiXian失败")
    @ResponseBody
    public Result fail(@RequestParam Integer id) {
        Tixian tixian = tixianService.findById(id);
        tixian.setState("3");
        tixian.setUpdateTime(new Date());
        tixianService.update(tixian);
        Users users = usersService.findById(tixian.getUserId());
        users.setIntegral(users.getIntegral()+tixian.getIntegral());
        usersService.update(users);
        return ResultGenerator.genSuccessResult(tixian);
    }

    @GetMapping(value = "/tixianManage")
    public String driverManage() {
        return "tixian/tixian";
    }
}
