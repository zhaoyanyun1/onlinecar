package com.fty.onlinecar.controller;
import com.fty.onlinecar.response.Result;
import com.fty.onlinecar.response.ResultGenerator;
import com.fty.onlinecar.entity.Trip;
import com.fty.onlinecar.response.Table;
import com.fty.onlinecar.service.TripService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.*;



/**
* Created by wanghuiwen on 2020/08/03.
*/
@Api(value = "Trip", tags = {"Trip"})
@Controller
@RequestMapping("/trip")
public class TripController{
    @Resource
    private TripService tripService;

    @ApiOperation(value = "Trip添加", tags = {"Trip"}, notes = "Trip添加")
    @PostMapping(value="/add",name="Trip添加")
    @ResponseBody
    public Result add(@RequestBody Trip data) {
        tripService.save(data);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "Trip删除", tags = {"Trip"}, notes = "Trip删除")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",required=true, value = "Tripid", dataType = "Long", paramType = "query")
    })
    @PostMapping(value="/delete",name="Trip删除")
    public Result delete(@RequestParam Long id) {
        tripService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "Trip修改", tags = {"Trip"}, notes = "Trip修改,对象主键必填")
    @PostMapping(value="/update",name="Trip修改")
    public Result update(@ApiParam Trip trip) {
        tripService.update(trip);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "Trip详细信息", tags = {"Trip"}, notes = "Trip详细信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",required=true, value = "Tripid", dataType = "Long", paramType = "query")
    })
    @PostMapping(value="/detail",name="Trip详细信息")
    public Result detail(@RequestParam Integer id) {
        Trip trip = tripService.findById(id);
        return ResultGenerator.genSuccessResult(trip);
    }

    @ApiOperation(value = "Trip列表信息", tags = {"Trip"}, notes = "Trip列表信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "search", value = "查询条件json", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "排序json", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "page", value = "页码", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "size", value = "每页显示的条数", dataType = "String", paramType = "query", defaultValue = "10")
    })
    @PostMapping(value = "/list", name = "Trip列表信息")
    @ResponseBody
    public Table list(@RequestParam(defaultValue = "{}") String search,
                       @RequestParam(defaultValue = "{}") String order,
                       @RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "10") Integer size) {
        List<Map<String, Object>> list = tripService.list(search, order, page, size);
        Table table = new Table();
        table.setData(list);
        table.setCount(list.size());
        return table;
    }

    @GetMapping(value = "/tripManage")
    public String toManage(){
        return "trip/tripManage";
    }

    @GetMapping(value = "/toAdd")
    public String toAdd(){
        return "trip/add";
    }
}
