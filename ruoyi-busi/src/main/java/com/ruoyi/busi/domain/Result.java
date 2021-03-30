package com.ruoyi.busi.domain;

/**
 * TODO
 *
 * @author duanc
 * @version 1.0
 * @date 2021/3/29 17:36
 */
public class Result {
    public static final boolean SUCCESS=true;
    public static final boolean FAIL=false;
    private boolean state;
    private String message;

    public boolean isState() {
        return state;
    }

    public Result setState(boolean state) {
        this.state = state;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }
}
