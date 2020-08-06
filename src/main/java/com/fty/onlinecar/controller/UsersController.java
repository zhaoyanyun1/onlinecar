package com.fty.onlinecar.controller;
import com.fty.onlinecar.response.Result;
import com.fty.onlinecar.response.ResultGenerator;
import com.fty.onlinecar.entity.Users;
import com.fty.onlinecar.service.UsersService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;
import javax.annotation.Resource;
import java.util.List;
import io.swagger.annotations.*;



/**
* Created by wanghuiwen on 2020/08/06.
*/
@Api(value = "Users", tags = {"Users"})
@RestController
@RequestMapping("/users")
public class UsersController{
    @Resource
    private UsersService usersService;

    @ApiOperation(value = "Users添加", tags = {"Users"}, notes = "Users添加")
    @PostMapping(value="/add",name="Users添加")
    @ResponseBody
    public Result add(@RequestBody Users users) {
        usersService.save(users);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "Users删除", tags = {"Users"}, notes = "Users删除")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",required=true, value = "Usersid", dataType = "Long", paramType = "query")
    })
    @PostMapping(value="/delete",name="Users删除")
    public Result delete(@RequestParam Long id) {
        usersService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "Users修改", tags = {"Users"}, notes = "Users修改,对象主键必填")
    @PostMapping(value="/update",name="Users修改")
    public Result update(@ApiParam Users users) {
        usersService.update(users);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "Users详细信息", tags = {"Users"}, notes = "Users详细信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",required=true, value = "Usersid", dataType = "Long", paramType = "query")
    })
    @PostMapping(value="/detail",name="Users详细信息")
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
    @PostMapping(value = "/list", name = "Users列表信息")
    public Result list(@RequestParam(defaultValue = "{}") String search,
                       @RequestParam(defaultValue = "{}") String order,
                       @RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "10") Integer size) {
        return usersService.list(search, order, page, size);
    }
}
