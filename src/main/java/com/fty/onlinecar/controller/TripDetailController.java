package com.fty.onlinecar.controller;

import cn.hutool.core.date.DateUtil;
import com.fty.onlinecar.entity.*;
import com.fty.onlinecar.response.Result;
import com.fty.onlinecar.response.ResultGenerator;
import com.fty.onlinecar.service.CouponDetailService;
import com.fty.onlinecar.service.CouponService;
import com.fty.onlinecar.service.TripDetailService;
import com.fty.onlinecar.service.UsersService;
import com.fty.onlinecar.utils.JSONUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



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
    @Resource
    private CouponDetailService couponDetailService;

    @Resource
    private CouponService couponService;

    @ApiOperation(value = "TripDetail添加", tags = {"TripDetail"}, notes = "TripDetail添加")
    @PostMapping(value="/add",name="TripDetail添加")
    @ResponseBody
    public Result add(@RequestBody TripDetailAndCoupon tripDetailAndCoupon) {
        TripDetail tripDetail = tripDetailAndCoupon.getTripDetail();
        if(tripDetail.getpId()!=null && !tripDetail.getpId().equals("")){
            TripDetail pTripDetail = tripDetailService.findById(tripDetail.getpId());
            if(pTripDetail == null){
                return ResultGenerator.genNoDurTripResult();
            }
            if(pTripDetail.getSurplusSeatNum()<1){
                return ResultGenerator.genTripFullResult();
            }
            if(pTripDetail.getSurplusSeatNum()<tripDetail.getAllSeatNum()){
                return ResultGenerator.genSeatLowResult();
            }
            //Todo 判断发车时间是否已经过期



            tripDetailService.confirmTrip(tripDetail,pTripDetail);

            CouponDetail couponDetail = tripDetailAndCoupon.getCoupon();
            if(null!=couponDetail && null!=couponDetail.getId()){
                couponDetail.setState("2");
                couponDetail.setTripId(tripDetail.getId());
                couponDetail.setUpdateTime(new Date());
                couponDetailService.update(couponDetail);
            }
        }else{
            Users users = usersService.findById(tripDetail.getDriverId());
            if(users ==null){
                //Todo 返回司机信息有误
                return ResultGenerator.genFailResult();
            }
            if(users.getBalance()==null || users.getBalance().equals("") ){
                return ResultGenerator.genBalanceLowResult();
            }

            if(Integer.parseInt(users.getBalance())<=0 || Integer.parseInt(users.getBalance())<(tripDetail.getAllSeatNum()*2)){
                return ResultGenerator.genBalanceLowResult();
            }

            //Todo 判断司机当天有没有发布行程


            String isReturn = tripDetailAndCoupon.getIsReturn();
            if(isReturn.equals("no")){
                String [] dates = tripDetailAndCoupon.getDates();
                for(int i=0;i<dates.length;i++){
                    TripDetail newTrip = new TripDetail();
                    newTrip.setTripsDirection(tripDetail.getTripsDirection());
                    newTrip.setAllSeatNum(tripDetail.getAllSeatNum());
                    newTrip.setState(tripDetail.getState());
                    newTrip.setDriverId(tripDetail.getDriverId());
                    newTrip.setSurplusSeatNum(tripDetail.getAllSeatNum());
                    newTrip.setDepartureTime(dates[i]+" "+tripDetail.getDepartureTime());
                    tripDetailService.save(newTrip);
                }
            }else{
                String [] dates = tripDetailAndCoupon.getDates();
                tripDetail.setSurplusSeatNum(tripDetail.getAllSeatNum());
                tripDetail.setDepartureTime(dates[0]+" "+tripDetail.getDepartureTime());
                tripDetailService.save(tripDetail);


                String returnTime = tripDetailAndCoupon.getReturnTime();
                TripDetail returnTrip = new TripDetail();
                returnTrip.setAllSeatNum(tripDetail.getAllSeatNum());
                returnTrip.setDepartureTime(dates[0]+" "+returnTime);
                returnTrip.setDriverId(tripDetail.getDriverId());
                returnTrip.setState(tripDetail.getState());
                String [] reTripsDirections = tripDetail.getTripsDirection().split("-");
                String reTripsDirection = reTripsDirections[1]+"-"+reTripsDirections[0];
                returnTrip.setTripsDirection(reTripsDirection);
                returnTrip.setSurplusSeatNum(returnTrip.getAllSeatNum());
                tripDetailService.save(returnTrip);

            }
        }
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
    @ResponseBody
    public Result update(@RequestBody TripDetail tripDetail) {



        return tripDetailService.updateTripState(tripDetail);
    }










    @ApiOperation(value = "TripDetail详细信息", tags = {"TripDetail"}, notes = "TripDetail详细信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",required=true, value = "TripDetailid", dataType = "Long", paramType = "query")
    })
    @PostMapping(value="/detail",name="TripDetail详细信息")
    @ResponseBody
    public Result detail(@RequestParam("id") String id) {
        Map<String,Object> tripDetail = tripDetailService.getById(id);
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
        List<Map<String,Object>> list = tripDetailService.driverTriplist(search);
        for (Map<String,Object> map:list ) {
            String name = map.get("name").toString();
            map.put("showName",name.substring(0,1)+"师傅");
            String phone = map.get("phone").toString();
            String phone1 = phone.substring(0,3);
            String phone2 = phone.substring(7,11);
            map.put("showPhone",phone1+"****"+phone2);
        }
        return ResultGenerator.genSuccessResult(list);
    }







    @PostMapping(value="/findCurTripByDriver",name="查询司机当前行程")
    @ResponseBody
    public Result findCurTripByDriver(@RequestBody String search) {
        Map<String, Object> params = JSONUtils.json2map(search);
        List<TripDetail> tripDetail = tripDetailService.findCurTripByDriver(params);
        if(tripDetail.isEmpty()){
            return ResultGenerator.genNoTripResult();
        }
        if(params.get("id")!=null && !params.get("id").equals("")){
            return ResultGenerator.genSuccessResult(tripDetail.get(0));
        }
        return ResultGenerator.genSuccessResult(tripDetail);
    }


    /**
     * 查询司机能否发布行程
     * @param search
     * @return
     */
    @PostMapping(value="/findDriverCanPush",name="查询司机能否发布行程")
    @ResponseBody
    public Result findDriverCanPush(@RequestBody String search) {
        Map<String, Object> params = JSONUtils.json2map(search);
        List<TripDetail> tripDetails = tripDetailService.findCurTripByDriver(params);
        if(tripDetails.isEmpty()){
            return ResultGenerator.genNoTripResult();
        }

        //阳泉特殊要求，最多能发4条行程，多了不行，少了可以
        if(tripDetails.size()>=4){
            return ResultGenerator.genSuccessResult();
        }
//        for (TripDetail tripDetail:tripDetails) {
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
//            try {
//                Date departureTime = simpleDateFormat.parse(tripDetail.getDepartureTime());
//                String newDateStr = DateUtil.format(new Date(),"yyyy-MM-dd");
//                Date newDate = simpleDateFormat.parse(newDateStr);
//                int compareTo = newDate.compareTo(departureTime);
//                if(compareTo==0){
//                    return ResultGenerator.genSuccessResult();
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//        }
        return ResultGenerator.genNoTripResult();

    }







    @PostMapping(value="/findCurTripByPassenger",name="查询乘客当前行程")
    @ResponseBody
    public Result findCurTripByPassenger(@RequestBody String search) {
        Map<String, Object> params = JSONUtils.json2map(search);
        Map<String,Object> tripDetail = tripDetailService.findCurTripByPassenger(params);
        if(tripDetail ==null){
            return ResultGenerator.genNoTripResult();
        }
        return ResultGenerator.genSuccessResult(tripDetail);
    }







    /**
     * 司机查看同行乘客
     * @returndriverTriplist
     */
    @PostMapping(value = "/findPeersPassenger", name = "Users列表信息")
    @ResponseBody
    public List<Map<String, Object>> findPeersPassenger(@RequestBody Integer tripId) {

        List<Map<String, Object>> list = tripDetailService.findPeersPassenger(tripId);
        return list;
    }








    /**
     * 取消行程
     * @return
     */
    @ApiOperation(value = "TripDetail修改", tags = {"TripDetail"}, notes = "TripDetail修改,对象主键必填")
        @PostMapping(value="/cancelTrip",name="TripDetail修改")
    @ResponseBody
    public Result cancelTrip(@RequestBody TripDetail tripDetail) {
        TripDetail oldTrip = tripDetailService.findById(tripDetail.getId());

        TripDetail pTripDetail = tripDetailService.findById(tripDetail.getpId());

        tripDetailService.update(tripDetail);

        if(oldTrip.getState().equals("1")){
            pTripDetail.setSurplusSeatNum(pTripDetail.getSurplusSeatNum()+oldTrip.getAllSeatNum());
        }

        tripDetailService.update(pTripDetail);

        Map<String, Object> params = new HashMap<>();
        params.put("tripId",oldTrip.getId());
        params.put("state","2");
        params.put("userId",oldTrip.getUserId());
        List<Map<String, Object>> couponDetails = couponDetailService.passengerCouponlist(params);
        if(!couponDetails.isEmpty()){
            if(couponDetails.size()>1){

            }else{
                CouponDetail couponDetail =couponDetailService.findById(couponDetails.get(0).get("id"));

                couponDetail.setTripId(null);
                couponDetail.setState("1");
                couponDetail.setUpdateTime(new Date());
                couponDetailService.update(couponDetail);
            }
        }

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 历史行程
     */
    @PostMapping(value = "/findHistory", name = "Users列表信息")
    @ResponseBody
    public List<Map<String, Object>> findHistory(@RequestBody String search) {
        Map<String, Object> params = JSONUtils.json2map(search);
        List<Map<String, Object>> list = tripDetailService.findHistory(params);
        for (Map<String, Object> map: list) {

            String departureTime = map.get("departureTime").toString();
            String[] departureTimes = departureTime.split(" ");
            String departureDate = departureTimes[0];
            Date date = DateUtil.parse(departureDate,"yyyy-MM-dd");
            departureDate = DateUtil.format(date,"yyyyMMdd");
            map.put("departureDate",departureDate);


            if(map.get("userId") !=null && !map.get("userId").equals("")){
                CouponDetail couponDetail = couponDetailService.findBy("tripId",map.get("id"));
                if(couponDetail!=null){
                    Coupon coupon = couponService.findById(couponDetail.getCouponId());
                    map.put("couponName",coupon.getName());
                }

            }
        }
         return list;
    }

}
