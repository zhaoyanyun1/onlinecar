package com.fty.onlinecar.response;


/**
 * 响应结果生成工具
 */
public class ResultGenerator {
    public static Result genSuccessResult() {
        return new Result(ResultEnum.SUCCESS_MESSAGE,null);
    }

    public static Result genSuccessResult(Object data) {
        return new Result(ResultEnum.SUCCESS_MESSAGE,data);
    }


    public static Result genCreatedSuccessResult() {
        return new Result(ResultEnum.CREATED);
    }

    public static Result genCreatedSuccessResult(Object data) {
        return new Result(ResultEnum.CREATED,data);
    }

    public static Result genDeleteSuccessResult() {
        return new Result(ResultEnum.DELETED);
    }

    public static Result genUploadSuccessResult() {
        return new Result(ResultEnum.UPLOADED);
    }

    public static Result genFailResult() {
        return new Result(ResultEnum.FAIL);
    }
    public static Result genNoTripResult() {
        return new Result(ResultEnum.NO_TRIP);
    }

    public static Result genFailUpdateTripResult() {
        return new Result(ResultEnum.FAIL_UPDATETRIP);
    }

    public static Result genRegisterFailResult() {
        return new Result(ResultEnum.REGISTER_FAIL);
    }
    public static Result genBalanceLowResult() {
        return new Result(ResultEnum.BALANCE_LOW);
    }
    public static Result genSeatLowResult() {
        return new Result(ResultEnum.SEAT_LOW);
    }
    public static Result genTripFullResult() {
        return new Result(ResultEnum.TRIP_FULL);
    }
    public static Result genNoDurTripResult() {
        return new Result(ResultEnum.NO_DURTRIP);
    }
    public static Result genInsufficientPointsResult() {
        return new Result(ResultEnum.INSUFFICIENT_POINTS);
    }

    public static Result genFailResult(Object data) {
        return new Result(ResultEnum.FAIL,data);
    }

    public static Result genUnauthorizedResult() {
        return new Result(ResultEnum.UNAUTHORIZED);
    }

    public static Result genForbiddenResult() {
        return new Result(ResultEnum.FORBIDDEN);
    }

    public static Result genExceptionResult() {
        return new Result(ResultEnum.INTERNAL_SERVER_ERROR);
    }

    public static Result genResultAndData(ResultEnum resultEnum, Object data) {
        return new Result(resultEnum,data);
    }
    public static Result genResult(ResultEnum resultEnum) {
        return new Result(resultEnum);
    }

    public static Result genExceptionResult(Exception e) {
        return new Result(e);
    }
}
