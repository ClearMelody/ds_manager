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
@SuppressWarnings("ALL")
public class Result {

    public static final Integer SUCCESS = 0;
    public static final Integer ERROR = 1;

    public static final class MsgHelper {
        public static final String SUCCESS_SAVE = "保存成功";
        public static final String SUCCESS_GET = "获取成功";
        public static final String SUCCESS_DELETE = "删除成功";
        public static final String SUCCESS_EXECUTE = "执行成功";
        public static final String SUCCESS_SEND = "发送成功";
        public static final String ERROR_EXECUTE = "执行失败";
        public static final String errorParam(String paramName) {
            return paramName + "参数异常";
        }
        public static final String errorNotExist(String name) {
            return name + "不存在";
        }
    }

    /**
     * 返回结果
     */
    private Integer errCode;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private String data;

    public Result() {

    }

    public Result(Integer errCode, String msg) {
        this.errCode = errCode;
        this.msg = msg;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static Result setResult(Integer errCode, String msg) {
        Result res = new Result();
        res.setMsg(msg);
        res.setErrCode(errCode);
        return res;
    }

    public static Result setResult(Integer errCode, String msg, String data) {
        Result res = new Result();
        res.setMsg(msg);
        res.setErrCode(errCode);
        res.setData(data);
        return res;
    }

    @Override
    public String toString() {
        return "Result [result=" + errCode + ", msg=" + msg + ", data=" + data
                + "]";
    }
}
