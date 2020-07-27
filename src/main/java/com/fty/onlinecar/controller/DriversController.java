package com.fty.onlinecar.controller;
import com.fty.onlinecar.response.Result;
import com.fty.onlinecar.response.ResultGenerator;
import com.fty.onlinecar.entity.Drivers;
import com.fty.onlinecar.service.DriversService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;
import javax.annotation.Resource;
import java.util.List;
import io.swagger.annotations.*;



/**
* Created by wanghuiwen on 2020/07/27.
*/
@Api(value = "Drivers", tags = {"Drivers"})
@Controller
@RequestMapping("/drivers")
public class DriversController{
    @Resource
    private DriversService driversService;

    @ApiOperation(value = "Drivers添加", tags = {"Drivers"}, notes = "Drivers添加")
    @PostMapping(value="/add",name="Drivers添加")
    public Result add(@ApiParam Drivers drivers) {
        driversService.save(drivers);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "Drivers删除", tags = {"Drivers"}, notes = "Drivers删除")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",required=true, value = "Driversid", dataType = "Long", paramType = "query")
    })
    @PostMapping(value="/delete",name="Drivers删除")
    public Result delete(@RequestParam Long id) {
        driversService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "Drivers修改", tags = {"Drivers"}, notes = "Drivers修改,对象主键必填")
    @PostMapping(value="/update",name="Drivers修改")
    public Result update(@ApiParam Drivers drivers) {
        driversService.update(drivers);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "Drivers详细信息", tags = {"Drivers"}, notes = "Drivers详细信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",required=true, value = "Driversid", dataType = "Long", paramType = "query")
    })
    @PostMapping(value="/detail",name="Drivers详细信息")
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
    public Result list(@RequestParam(defaultValue = "{}") String search,
                       @RequestParam(defaultValue = "{}") String order,
                       @RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "10") Integer size) {
        return driversService.list(search, order, page, size);
    }


    @GetMapping(value = "/driversManage")
    public String toManage(){
        return "drivers/driversManage";
    }
}
