package com.example.weex.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.taobao.weex.adapter.DefaultWXHttpAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created by moxun on 16/12/29.
 */

public class InterceptWXHttpAdapter extends DefaultWXHttpAdapter {

    @NonNull
    @Override
    public IEventReporterDelegate getEventReporterDelegate() {
        return new IEventReporterDelegate() {

            @Override
            public void preConnect(HttpURLConnection connection, @Nullable String body) {

            }

            @Override
            public void postConnect() {

            }

            @Override
            public InputStream interpretResponseStream(@Nullable InputStream inputStream) {
                return null;
            }

            @Override
            public void httpExchangeFailed(IOException e) {

            }
        };
    }
}

