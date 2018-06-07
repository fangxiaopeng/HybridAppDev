package com.example.weex;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.example.weex.adapter.DefaultAccessibilityRoleAdapter;
import com.example.weex.adapter.DefaultWebSocketAdapterFactory;
import com.example.weex.adapter.ImageAdapter;
import com.example.weex.adapter.InterceptWXHttpAdapter;
import com.example.weex.adapter.JSExceptionAdapter;
import com.example.weex.component.RichText;
import com.example.weex.module.MyModule;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.common.WXException;

public class WXApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        initWeexSDK();

        registerExpandModule();

        registerExpandComponent();

        registerActivityLifecycleCallbacks(getActivityLifecycleCallbacks());

    }

    /**
     * @Description:    初始化WXSDKManager
     *
     * @Author:  fxp
     * @Date:    2018/6/7   上午10:32
     * @param
     * @return   void
     * @exception/throws
     */
    private void initWeexSDK(){

        InitConfig config = new InitConfig.Builder()
                .setImgAdapter(new ImageAdapter())
//                .setWebSocketAdapterFactory(new DefaultWebSocketAdapterFactory())   // 暂未实现
                .setJSExceptionAdapter(new JSExceptionAdapter())
//                .setHttpAdapter(new InterceptWXHttpAdapter())   //暂未实现
                .build();

        WXSDKEngine.initialize(this,config);

        WXSDKManager.getInstance().setAccessibilityRoleAdapter(new DefaultAccessibilityRoleAdapter());

    }

    /**
     * @Description:    注册Module拓展
     *
     * @Author:  fxp
     * @Date:    2018/6/7   上午10:36
     * @param
     * @return   void
     * @exception/throws
     */
    private void registerExpandModule(){
        try {
            // 注册MyModule
            WXSDKEngine.registerModule("MyModule", MyModule.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description:    注册Component拓展
     *
     * @Author:  fxp
     * @Date:    2018/6/7   上午10:50
     * @param
     * @return   void
     * @exception/throws
     */
    private void registerExpandComponent(){
        try {
            WXSDKEngine.registerComponent("richText", RichText.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
    }


    /**
     * @Description:  获取ActivityLifecycleCallbacks
     *
     * @Author:  fxp
     * @Date:    2018/6/7   上午10:38
     * @param
     * @return   android.app.Application.ActivityLifecycleCallbacks
     * @exception/throws
     */
    private ActivityLifecycleCallbacks getActivityLifecycleCallbacks(){

        return new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                // The demo code of calling 'notifyTrimMemory()'
                if (false) {
                    // We assume that the application is on an idle time.
                    WXSDKManager.getInstance().notifyTrimMemory();
                }
                // The demo code of calling 'notifySerializeCodeCache()'
                if (false) {
                    WXSDKManager.getInstance().notifySerializeCodeCache();
                }
            }
        };
    }

    /**
     *@param connectable debug server is connectable or not.
     *               if true, sdk will try to connect remote debug server when init WXBridge.
     *
     * @param debuggable enable remote debugger. valid only if host not to be "DEBUG_SERVER_HOST".
     *               true, you can launch a remote debugger and inspector both.
     *               false, you can  just launch a inspector.
     * @param host the debug server host, must not be "DEBUG_SERVER_HOST", a ip address or domain will be OK.
     *             for example "127.0.0.1".
     */
    private void initDebugEnvironment(boolean connectable, boolean debuggable, String host) {
        if (!"DEBUG_SERVER_HOST".equals(host)) {
            WXEnvironment.sDebugServerConnectable = connectable;
            WXEnvironment.sRemoteDebugMode = debuggable;
            WXEnvironment.sRemoteDebugProxyUrl = "ws://" + host + ":8088/debugProxy/native";
        }
    }

}
