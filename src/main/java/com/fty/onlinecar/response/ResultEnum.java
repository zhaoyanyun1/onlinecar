package com.fty.onlinecar.response;

public enum ResultEnum implements IResultEnum {
    SUCCESS_MESSAGE("请求成功",200),//服务器成功返回用户请求的数据
    LOGIN_SUCCESS("登录成功",200),//服务器成功返回用户请求的数据
    LOGIN_OUT_SUCCESS("登录成功",200),//服务器成功返回用户请求的数据
    CREATED("新建成功",200),//用户新建或修改数据成功
    UPLOADED("上传文件成功",200),
    MODIFIED("修改成功",200),//用户新建或修改数据成功
    DELETED("删除成功",200),//用户删除数据成功
    FAIL("请求失败",400),//用户发出的请求有错误，服务器没有进行新建或修改数据的操作
    INTERNAL_SERVER_ERROR("服务器繁忙,请稍后再试",500),//服务器内部错误
    NO_CONTENT("没有找到相关数据",20010),
    NO_UNDELIVERED_MESSAGE("所有信息已经传递完毕",20011),
    UNAUTHORIZED("用户没有权限",401),
    LOGIN_FAIL("用户名或密码错误",401),
    FORBIDDEN("用户禁止访问该内容",403),//表示用户得到授权（与401错误相对），但是访问是被禁止的
    REGISTER_FAIL("注册失败，请检查用户名与密码等信息是否符合规则,或者是账户名已被注册",400101),
    CREATE_FAIL("新建数据失败",400103),
    DELETE_FAIL("无法删除数据，因为数据在其它表中仍被使用，请先删除子表中数据",400104),
    NO_IMAGE_PARAM("参数中无图片输入",400105),
    IMG_UPLOAD_FAIL("上传图片失败,请再次上传图片",400106),
    UPDATE_FAIL("更新信息失败",400107),
    DATE_ENTRY_ERROR("输入数据有误",400110),
    NULL("数据不能为空",400111),
    UPLOADED_FAIL("上传文件失败",400112),
    UPLOADED_MAX("文件过大,无法上传",400113),
    CONNECT_EXCEPTION("网络链接异常",400114),
    REDIS_CONNECTION_FAILUR("redis连接失败",400115),
    PARAMS_LACK("缺少参数",400116),
    MODULE_EXISTS("模块不存在",400117),
    DUPLICATE_KEY("违反唯一约束",400118),
    PASSWORD_ERROR("密码错误",400119),
    PHONE_NULL("手机号不能为空",500),
    PHONE_HAVE("手机号已经存在",500),
    PHONE_NO("手机号没有注册",500),
    ;

    private final String message;
    private final int code;

    ResultEnum(String message, int code) {
        this.message = message;
        this.code=code;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
