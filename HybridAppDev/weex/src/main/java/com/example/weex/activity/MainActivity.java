package com.example.weex.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.alibaba.weex.commons.adapter.ImageAdapter;
import com.example.weex.R;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.utils.WXFileUtils;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IWXRenderListener {

    public final static String ROOT = "root";
    private final static String TAG = "WEEX";
    private String URL = "http://h5.m.taobao.com/js/src/weexlist.js";
    private String PATH = "index.js";
    private WXSDKInstance mInstance;
    private LinearLayout root;
    private long startTime;
    private long endTime;
    private long duration;
    private boolean perfStart;
    private boolean perfEnd;
    private boolean isWeex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = new LinearLayout(this);
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        root.setOrientation(LinearLayout.VERTICAL);
        root.setContentDescription(ROOT);
        setContentView(root);

        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                endTime = System.currentTimeMillis();
                Log.v(TAG, "End: " + endTime);
                if (perfStart && (perfEnd||!isWeex)) {
                    perfStart = false;
                    perfEnd = false;
                    duration = (endTime - startTime);
                    Log.v(TAG, "OnGlobalLayoutListener: " + getDuration());
                }
            }
        });
        WXEnvironment.isPerf = true;
        WXSDKEngine.addCustomOptions("appName", "WXSample");
        WXSDKEngine.addCustomOptions("appGroup", "WXApp");
        WXSDKEngine.initialize(getApplication(), new InitConfig.Builder().setImgAdapter(new ImageAdapter()).build());

        // 加载网易严选
        loadWeexPageRemote("http://doc.zwwill.com/yanxuan/jsbundles/index.js");

        // 加载本地js文件
//        loadWeexPageLocal(true, PATH);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mInstance != null) {
            mInstance.onActivityStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mInstance != null) {
            mInstance.onActivityResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mInstance != null) {
            mInstance.onActivityPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mInstance != null) {
            mInstance.onActivityStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mInstance != null) {
            mInstance.onActivityDestroy();
        }
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        if (root != null) {
            root.removeAllViews();
            root.addView(view);
        }
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
        root.postDelayed(new Runnable() {
            @Override
            public void run() {
                perfEnd = true;
            }
        }, 1000);
    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {

    }

    public boolean isRenderFinish(){
        return perfEnd;
    }

    public WXSDKInstance getWXInstance() {
        return mInstance;
    }

    public void loadWeexPageLocal(final boolean weex, final String path) {
        isWeex=weex;
        perfEnd = false;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (weex) {
                    if (mInstance != null) {
                        mInstance.destroy();
                    }
                    mInstance = new WXSDKInstance(MainActivity.this);
                    Map<String, Object> options = new HashMap<>();
                    options.put(WXSDKInstance.BUNDLE_URL, "file://assets/" + path);
                    mInstance.registerRenderListener(MainActivity.this);
                    perfStart = true;
                    startTime = System.currentTimeMillis();

                    try{
                        // 渲染之前延时1秒（解决页面空白问题）
                        Thread.sleep(1000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    mInstance.render(TAG, WXFileUtils.loadAsset(path, MainActivity.this), options, null, WXRenderStrategy.APPEND_ASYNC);
                } else {
                    root.removeAllViews();
                    perfStart = true;
                    startTime = System.currentTimeMillis();
                    View.inflate(MainActivity.this, R.layout.hello_weex, root);
                }
            }
        });
    }

    public void loadWeexPageRemote(final String url) {
        perfEnd = false;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mInstance != null) {
                    mInstance.destroy();
                }
                mInstance = new WXSDKInstance(MainActivity.this);
                Map<String, Object> options = new HashMap<>();
                options.put(WXSDKInstance.BUNDLE_URL, url);
                mInstance.registerRenderListener(MainActivity.this);
                mInstance.renderByUrl(
                        TAG,
                        url,
                        options,
                        null,
                        WXRenderStrategy.APPEND_ASYNC);
            }
        });
    }

    public long getDuration() {
        return duration;
    }
}
