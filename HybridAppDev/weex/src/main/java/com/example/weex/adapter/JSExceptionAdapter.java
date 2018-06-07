package com.example.weex.adapter;

import com.taobao.weex.adapter.IWXJSExceptionAdapter;
import com.taobao.weex.common.WXJSExceptionInfo;
import com.taobao.weex.utils.WXLogUtils;

/**
 */

public class JSExceptionAdapter implements IWXJSExceptionAdapter {

    @Override
    public void onJSException(WXJSExceptionInfo exception) {
        if (exception != null) {
            WXLogUtils.d(exception.toString());
        }
    }
}