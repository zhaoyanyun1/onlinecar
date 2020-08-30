package com.fty.onlinecar.controller;
import com.fty.onlinecar.entity.Coupon;
import com.fty.onlinecar.entity.Users;
import com.fty.onlinecar.response.Result;
import com.fty.onlinecar.response.ResultGenerator;
import com.fty.onlinecar.entity.CouponDetail;
import com.fty.onlinecar.service.CouponDetailService;
import com.fty.onlinecar.service.CouponService;
import com.fty.onlinecar.service.IntegralDetailService;
import com.fty.onlinecar.service.UsersService;
import com.fty.onlinecar.utils.JSONUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.*;



/**
* Created by wanghuiwen on 2020/08/24.
*/
@Api(value = "CouponDetail", tags = {"CouponDetail"})
@RestController
@RequestMapping("/coupon/detail")
public class CouponDetailController{
    @Resource
    private CouponDetailService couponDetailService;
    @Resource
    private CouponService couponService;
    @Resource
    private UsersService usersService;
    @Resource
    private IntegralDetailService integralDetailService;

    @ApiOperation(value = "CouponDetail添加", tags = {"CouponDetail"}, notes = "CouponDetail添加")
    @PostMapping(value="/add",name="CouponDetail添加")
    public Result add(@RequestBody CouponDetail couponDetail) {
        if(couponDetail.getCouponId()==null || couponDetail.getUserId()==null){
            return ResultGenerator.genFailResult();
        }
        Coupon coupon = couponService.findById(couponDetail.getCouponId());
        Users users = usersService.findById(couponDetail.getUserId());
        int userIntegral =Integer.parseInt(users.getIntegral());
        int couponMoney =Integer.parseInt(coupon.getMoney());
        if(userIntegral<couponMoney){
            //Todo 返回积分不足
            return ResultGenerator.genFailResult();
        }
        couponDetail.setState("1");
        couponDetailService.save(couponDetail);
        integralDetailService.lessIntegral(users,couponMoney,"兑换优惠券");
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "CouponDetail删除", tags = {"CouponDetail"}, notes = "CouponDetail删除")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",required=true, value = "CouponDetailid", dataType = "Long", paramType = "query")
    })
    @PostMapping(value="/delete",name="CouponDetail删除")
    public Result delete(@RequestParam Long id) {
        couponDetailService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "CouponDetail修改", tags = {"CouponDetail"}, notes = "CouponDetail修改,对象主键必填")
    @PostMapping(value="/update",name="CouponDetail修改")
    public Result update(@ApiParam CouponDetail couponDetail) {
        couponDetailService.update(couponDetail);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "CouponDetail详细信息", tags = {"CouponDetail"}, notes = "CouponDetail详细信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",required=true, value = "CouponDetailid", dataType = "Long", paramType = "query")
    })
    @PostMapping(value="/detail",name="CouponDetail详细信息")
    public Result detail(@RequestParam Integer id) {
        CouponDetail couponDetail = couponDetailService.findById(id);
        return ResultGenerator.genSuccessResult(couponDetail);
    }

    @ApiOperation(value = "CouponDetail列表信息", tags = {"CouponDetail"}, notes = "CouponDetail列表信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "search", value = "查询条件json", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "排序json", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "page", value = "页码", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "size", value = "每页显示的条数", dataType = "String", paramType = "query", defaultValue = "10")
    })
    @PostMapping(value = "/list", name = "CouponDetail列表信息")
    public Result list(@RequestParam(defaultValue = "{}") String search,
                       @RequestParam(defaultValue = "{}") String order,
                       @RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "10") Integer size) {
        return couponDetailService.list(search, order, page, size);
    }


    @PostMapping(value = "/passengerCouponlist", name = "用户CouponDetail列表信息")
    @ResponseBody
    public Result list(@RequestParam(defaultValue = "{}") String search) {
        Map<String, Object> params = JSONUtils.json2map(search);
        List<Map<String, Object>>  list = couponDetailService.passengerCouponlist(params);
        return ResultGenerator.genSuccessResult(list);
    }
}
