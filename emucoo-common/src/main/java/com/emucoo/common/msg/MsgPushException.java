package com.emucoo.common.msg;

/**
 * throws by the message pushing proxy method.
 *
 * @author Shayne.Wang
 * @date 2018-06-12
 *
 */

public class MsgPushException extends Exception {
    public MsgPushException(String message) {
        super(message);
    }

    public MsgPushException(String message, Throwable e){
        super(message, e);
    }
}
