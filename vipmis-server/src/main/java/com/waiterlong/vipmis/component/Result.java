package com.waiterlong.vipmis.component;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-14 15:59
 * \*
 * \* Description:
 * \
 *
 * @author waiterlong
 */


/**
 * 响应操作结果
 * <pre>
 *  {
 *      errcode： 错误码，
 *      errmsg：错误消息，
 *      data：  响应数据
 *  }
 * </pre>
 *
 * <p>
 * 错误码：
 * <ul>
 * <li> 0，成功；
 * <li> 4xx，前端错误，说明前端开发者需要重新了解后端接口使用规范：
 * <ul>
 * <li> 401，参数错误，即前端没有传递后端需要的参数；
 * <li> 402，参数值错误，即前端传递的参数值不符合后端接收范围。
 * </ul>
 * <li> 5xx，后端错误，除501外，说明后端开发者应该继续优化代码，尽量避免返回后端错误码：
 * <ul>
 * <li> 501，验证失败，即后端要求用户登录；
 * <li> 502，系统内部错误，即没有合适命名的后端内部错误；
 * <li> 503，业务不支持，即后端虽然定义了接口，但是还没有实现功能；
 * <li> 504，更新数据失效，即后端采用了乐观锁更新，而并发更新时存在数据更新失效；
 * <li> 505，更新数据失败，即后端数据库更新失败（正常情况应该更新成功）。
 * </ul>
 * <li> 6xx，微信后端业务错误码，
 * <li> 7xx，管理后台后端业务错误码，
 * </ul>
 */
@SuppressWarnings("ALL")
public class Result {

    public static final Integer SUCCESS_CODE = 0;
    public static final Integer ERROR_CODE = -1;

    public static final String SUCCESS = "成功";
    public static final String ERROR = "错误";

    public static final String SUCCESS_SAVE = "保存成功";
    public static final String SUCCESS_GET = "获取成功";
    public static final String SUCCESS_DELETE = "删除成功";
    public static final String SUCCESS_EXECUTE = "执行成功";
    public static final String SUCCESS_SEND = "发送成功";
    public static final String ERROR_EXECUTE = "执行失败";

    public static final Integer ERROR_BAD_ARGUMENT_VALUE_CODE = 402;
    public static final Integer ERROR_BAD_ARGUMENT_CODE = 401;
    public static final Integer ERROR_UNLOGIN_CODE = 501;
    public static final Integer ERROR_SERIOUS_CODE = 502;
    public static final Integer ERROR_UNSUPPORT_CODE = 503;
    public static final Integer ERROR_UPDATED_DATA_EXPIRED_CODE = 504;
    public static final Integer ERROR_UPDATED_DATA_FAILED_CODE = 505;
    public static final Integer ERROR_UNAUTHZ_CODE = 506;

    public static final String ERROR_BAD_ARGUMENT_VALUE = "参数值不对";
    public static final String ERROR_BAD_ARGUMENT = "参数不对";
    public static final String ERROR_UNLOGIN = "请登录";
    public static final String ERROR_SERIOUS = "系统内部错误";
    public static final String ERROR_UNSUPPORT = "业务不支持";
    public static final String ERROR_UPDATED_DATA_EXPIRED = "更新数据已经失效";
    public static final String ERROR_UPDATED_DATA_FAILED = "更新数据失败";
    public static final String ERROR_UNAUTHZ = "无操作权限";

    /**
     * 返回结果
     */
    private Integer errcode;
    /**
     * 返回信息
     */
    private String errmsg;
    /**
     * 返回数据
     */
    private Object data;

    private Result() {

    }

    public Result(Integer errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Result setResult(Integer errcode, String errmsg) {
        Result res = new Result(errcode, errmsg);
        return res;
    }

    public static Result setResult(Integer errcode, String errmsg, Object data) {
        Result res = new Result(errcode, errmsg);
        res.setData(data);
        return res;
    }

    public static Result ok() {
        Result res = new Result(SUCCESS_CODE, SUCCESS);
        return res;
    }

    public static Result ok(Object data) {
        Result res = new Result(SUCCESS_CODE, SUCCESS);
        res.setData(data);
        return res;
    }

    public static Result fail() {
        Result res = new Result(ERROR_CODE, ERROR);
        return res;
    }

    public static Result fail(int errcode, String errmsg) {
        if (0 == errcode) {
            return null;
        }
        Result res = new Result(errcode, errmsg);
        return res;
    }

    public static Result badArgumentValue() {
        return fail(ERROR_BAD_ARGUMENT_VALUE_CODE, ERROR_BAD_ARGUMENT_VALUE);
    }

    public static Result badArgument() {
        return fail(ERROR_BAD_ARGUMENT_CODE, ERROR_BAD_ARGUMENT);
    }

    public static Result unlogin() {
        return fail(ERROR_UNLOGIN_CODE, ERROR_UNLOGIN);
    }

    public static Result serious() {
        return fail(ERROR_SERIOUS_CODE, ERROR_SERIOUS);
    }

    public static Result unsupport() {
        return fail(ERROR_UNSUPPORT_CODE, ERROR_UNSUPPORT);
    }

    public static Result updatedDateExpired() {
        return fail(ERROR_UPDATED_DATA_EXPIRED_CODE, ERROR_UPDATED_DATA_EXPIRED);
    }

    public static Result updatedDataFailed() {
        return fail(ERROR_UPDATED_DATA_FAILED_CODE, ERROR_UPDATED_DATA_FAILED);
    }

    public static Result unauthz() {
        return fail(ERROR_UNAUTHZ_CODE, ERROR_UNAUTHZ);
    }

    @Override
    public String toString() {
        return "Result [result=" + errcode + ", msg=" + errmsg + ", data=" + data
                + "]";
    }
}
