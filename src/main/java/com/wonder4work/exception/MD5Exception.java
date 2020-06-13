package com.wonder4work.exception;

/**
 * @since 1.0.0 2020/5/20
 */
public class MD5Exception extends RuntimeException {


    public MD5Exception(String message) {
        super(message);
    }

    public MD5Exception(String message, Throwable cause) {
        super(message, cause);
    }
}
