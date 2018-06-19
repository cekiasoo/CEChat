package com.ce.cechat.utils;

import android.content.Context;

import com.ce.cechat.R;
import com.hyphenate.EMError;

/**
 * @author CE Chen
 *
 * 错误码工具类
 */
public class ErrorCode {

    private ErrorCode() {
        throw new UnsupportedOperationException("工具类不需要实例对象");
    }

    public static String errorCodeToString(Context pContext, int pErrorCode) {
        switch (pErrorCode) {

            case EMError
                    .USER_AUTHENTICATION_FAILED:
                return pContext.getString(R.string.user_authentication_failed);
            case EMError
                    .USER_NOT_FOUND:
                return pContext.getString(R.string.user_not_found);
            case EMError
                    .NETWORK_ERROR:
                return pContext.getString(R.string.network_anomalies);
            case EMError
                    .USER_ALREADY_EXIST:
                return pContext.getString(R.string.User_already_exists);
            case EMError
                    .SERVER_BUSY:
                return pContext.getString(R.string.server_busy);
            case EMError
                    .SERVER_UNKNOWN_ERROR:
                return pContext.getString(R.string.server_unknown_error);
            case EMError.SERVER_NOT_REACHABLE:
                return pContext.getString(R.string.server_not_reachable);
            default:
                return pContext.getString(R.string.unknow_error);

        }
    }

}
