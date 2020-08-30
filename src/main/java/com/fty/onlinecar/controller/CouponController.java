package com.fty.onlinecar.controller;
import com.fty.onlinecar.response.Result;
import com.fty.onlinecar.response.ResultGenerator;
import com.fty.onlinecar.entity.Coupon;
import com.fty.onlinecar.response.Table;
import com.fty.onlinecar.service.CouponService;
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
* Created by wanghuiwen on 2020/07/30.
*/
@Api(value = "Coupon", tags = {"Coupon"})
@Controller
@RequestMapping("/coupon")
public class CouponController{
    @Resource
    private CouponService couponService;

    @ApiOperation(value = "Coupon添加", tags = {"Coupon"}, notes = "Coupon添加")
    @PostMapping(value="/add",name="Coupon添加")
    @ResponseBody
    public Result add(@RequestBody Coupon data) {
        couponService.save(data);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "Coupon删除", tags = {"Coupon"}, notes = "Coupon删除")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",required=true, value = "Couponid", dataType = "Long", paramType = "query")
    })
    @PostMapping(value="/delete",name="Coupon删除")
    @ResponseBody
    public Result delete(@RequestParam Long id) {
        couponService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "Coupon修改", tags = {"Coupon"}, notes = "Coupon修改,对象主键必填")
    @PostMapping(value="/update",name="Coupon修改")
    public Result update(@ApiParam Coupon coupon) {
        couponService.update(coupon);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "Coupon详细信息", tags = {"Coupon"}, notes = "Coupon详细信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",required=true, value = "Couponid", dataType = "Long", paramType = "query")
    })
    @PostMapping(value="/detail",name="Coupon详细信息")
    @ResponseBody
    public Result detail(@RequestParam Integer id) {
        Coupon coupon = couponService.findById(id);
        return ResultGenerator.genSuccessResult(coupon);
    }

    @ApiOperation(value = "Coupon列表信息", tags = {"Coupon"}, notes = "Coupon列表信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "search", value = "查询条件json", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "排序json", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "page", value = "页码", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "size", value = "每页显示的条数", dataType = "String", paramType = "query", defaultValue = "10")
    })
    @PostMapping(value = "/list", name = "Coupon列表信息")
    @ResponseBody
    public Table list(@RequestParam(defaultValue = "{}") String search,
                       @RequestParam(defaultValue = "{}") String order,
                       @RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "10") Integer size) {

        List<Map<String, Object>> list = couponService.list(search, order, page, size);
        Table table = new Table();
        table.setData(list);
        table.setCount(list.size());
        return table;
    }

    @PostMapping(value = "/webFindlist", name = "Coupon列表信息")
    @ResponseBody
    public Table webFindlist() {

        List<Map<String, Object>> list = couponService.webFindlist();
        Table table = new Table();
        table.setData(list);
        table.setCount(list.size());
        return table;
    }


    @GetMapping(value = "/couponManage")
    public String toManage(){
        return "coupon/couponManage";
    }
    @GetMapping(value = "/toAdd")
    public String toAdd(){
        return "coupon/add";
    }
}
