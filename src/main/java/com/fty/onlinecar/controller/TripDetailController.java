package com.fty.onlinecar.controller;
import com.fty.onlinecar.entity.Users;
import com.fty.onlinecar.response.Result;
import com.fty.onlinecar.response.ResultEnum;
import com.fty.onlinecar.response.ResultGenerator;
import com.fty.onlinecar.entity.TripDetail;
import com.fty.onlinecar.response.Table;
import com.fty.onlinecar.service.TripDetailService;
import com.fty.onlinecar.service.UsersService;
import com.fty.onlinecar.utils.JSONUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.tools.javac.comp.Todo;
import org.springframework.web.bind.annotation.*;
import sun.jvm.hotspot.asm.Register;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.*;



/**
* Created by wanghuiwen on 2020/08/13.
*/
@Api(value = "TripDetail", tags = {"TripDetail"})
@RestController
@RequestMapping("/tripDetail")
public class TripDetailController{
    @Resource
    private TripDetailService tripDetailService;
    @Resource
    private UsersService usersService;


    @ApiOperation(value = "TripDetail添加", tags = {"TripDetail"}, notes = "TripDetail添加")
    @PostMapping(value="/add",name="TripDetail添加")
    @ResponseBody
    public Result add(@RequestBody TripDetail tripDetail) {
        if(tripDetail.getpId()!=null && !tripDetail.getpId().equals("")){
            TripDetail pTripDetail = tripDetailService.findById(tripDetail.getpId());
            if(pTripDetail == null){
                //Todo 返回没有对应行程或对应行程已经取消
                return ResultGenerator.genFailResult();
            }
            if(pTripDetail.getSurplusSeatNum()<1){
                //Todo 返回对应行程已经满座
                return ResultGenerator.genFailResult();
            }
            //Todo 判断发车时间是否已经过期



            tripDetailService.confirmTrip(tripDetail,pTripDetail);


        }else{
            Users users = usersService.findById(tripDetail.getDriverId());
            if(users ==null){
                //Todo 返回司机信息有误
                return ResultGenerator.genFailResult();
            }
            if(users.getBalance()==null || users.getBalance().equals("") ){
                //Todo 返回司机余额不足
                return ResultGenerator.genFailResult();
            }

            if(Integer.parseInt(users.getBalance())<=0 || Integer.parseInt(users.getBalance())<(tripDetail.getAllSeatNum()*2)){
                //Todo 返回司机余额不足
                return ResultGenerator.genFailResult();
            }

            //Todo 判断司机当天有没有发布行程

            tripDetail.setSurplusSeatNum(tripDetail.getAllSeatNum());
            tripDetailService.save(tripDetail);
        }


        //Todo 成功同行后，积分加减
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "TripDetail删除", tags = {"TripDetail"}, notes = "TripDetail删除")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",required=true, value = "TripDetailid", dataType = "Long", paramType = "query")
    })
    @PostMapping(value="/delete",name="TripDetail删除")
    public Result delete(@RequestParam Long id) {
        tripDetailService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "TripDetail修改", tags = {"TripDetail"}, notes = "TripDetail修改,对象主键必填")
    @PostMapping(value="/update",name="TripDetail修改")
    public Result update(@ApiParam TripDetail tripDetail) {
        tripDetailService.update(tripDetail);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "TripDetail详细信息", tags = {"TripDetail"}, notes = "TripDetail详细信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",required=true, value = "TripDetailid", dataType = "Long", paramType = "query")
    })
    @PostMapping(value="/detail",name="TripDetail详细信息")
    public Result detail(@RequestParam Integer id) {
        TripDetail tripDetail = tripDetailService.findById(id);
        return ResultGenerator.genSuccessResult(tripDetail);
    }

    @ApiOperation(value = "TripDetail列表信息", tags = {"TripDetail"}, notes = "TripDetail列表信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "search", value = "查询条件json", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "排序json", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "page", value = "页码", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "size", value = "每页显示的条数", dataType = "String", paramType = "query", defaultValue = "10")
    })
    @PostMapping(value = "/list", name = "TripDetail列表信息")
    public Result list(@RequestParam(defaultValue = "{}") String search,
                       @RequestParam(defaultValue = "{}") String order,
                       @RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "10") Integer size) {
        return tripDetailService.list(search, order, page, size);
    }


    @PostMapping(value = "/driverTriplist", name = "TripDetail列表信息")
    public Result driverTriplist(@RequestBody String search) {
        List<Map<String,Object>> list = tripDetailService.driverTriplist();
        return ResultGenerator.genSuccessResult(list);
    }

    @PostMapping(value="/findCurTripByDriver",name="查询司机当前行程")
    @ResponseBody
    public Result findCurTripByDriver(@RequestBody String search) {
        Map<String, Object> params = JSONUtils.json2map(search);
        TripDetail tripDetail = tripDetailService.findCurTripByDriver(params);
        if(tripDetail ==null){
            return ResultGenerator.genNoTripResult();
        }
        return ResultGenerator.genSuccessResult(tripDetail);
    }

    @PostMapping(value="/findCurTripByPassenger",name="查询司机当前行程")
    @ResponseBody
    public Result findCurTripByPassenger(@RequestBody String search) {
        Map<String, Object> params = JSONUtils.json2map(search);
        TripDetail tripDetail = tripDetailService.findCurTripByPassenger(params);
        if(tripDetail ==null){
            return ResultGenerator.genNoTripResult();
        }
        return ResultGenerator.genSuccessResult(tripDetail);
    }


    /**
     * 司机查看同行乘客
     * @return
     */
    @PostMapping(value = "/findPeersPassenger", name = "Users列表信息")
    @ResponseBody
    public List<Map<String, Object>> findPeersPassenger(@RequestBody Integer tripId) {

        List<Map<String, Object>> list = tripDetailService.findPeersPassenger(tripId);
        return list;
    }

    /**
     * 司机查看同行乘客
     * @return
     */
    @PostMapping(value = "/cancelTrip", name = "Users列表信息")
    @ResponseBody
    public Result cancelTrip(@RequestBody Integer tripId) {
        TripDetail tripDetail = tripDetailService.findById(tripId);
        tripDetail.setState("0");
//        tripDetailService.save(tripDetail);
        return ResultGenerator.genSuccessResult();
    }
}
