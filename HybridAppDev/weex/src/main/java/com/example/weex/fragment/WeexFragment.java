package com.example.weex.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.weex.R;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.utils.WXFileUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Title:       WeexFragment
 * <p>
 * Package:     com.example.weex.fragment
 * <p>
 * Author:      fxp
 * <p>
 * Create at:   2018/7/31 下午4:14
 * <p>
 * Description: Fragment加载Weex页面
 * <p>
 * <p>
 * Modification History:
 * <p>
 * Date       Author       Version      Description
 * -----------------------------------------------------------------
 * 2018/7/31    fxp       1.0         First Created
 * <p>
 * Github:  https://github.com/fangxiaopeng
 */
public class WeexFragment extends Fragment implements IWXRenderListener{

    private final static String TAG = WeexFragment.class.getSimpleName();

    private Context context;

    WXSDKInstance mWXSDKInstance;

    LinearLayout weexLayout;

    private BroadcastReceiver mReloadReceiver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.weex_fragment, container, false);

        weexLayout = (LinearLayout)view.findViewById(R.id.weex_layout);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onViewCreated");

        initData();
    }

    public void initData(){

        context = this.getContext();

        mWXSDKInstance = new WXSDKInstance(this.getContext());
        mWXSDKInstance.registerRenderListener(this);

        loadWeexPage();

        mReloadReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e(TAG, "onViewCreated");
            }
        };

        LocalBroadcastManager.getInstance(this.getContext()).registerReceiver(mReloadReceiver, new IntentFilter(WXSDKEngine.JS_FRAMEWORK_RELOAD));
    }

    /**
     * @Description: 加载weex页面
     *
     * @Author:  fxp
     * @Date:    2018/7/31   下午5:07
     * @param
     * @return   void
     * @exception/throws
     */
    private void loadWeexPage(){
        try{
            // 渲染之前延时1秒（解决页面空白问题）
            Thread.sleep(1000);

            int type = getArguments().getInt("type");
            String path = getArguments().getString("path");
            if (type == 0){
                // 本地js
                loadWeexPageLocal(path);
            }else {
                // 网络js
                loadWeexPageRemote(path);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @Description: 加载本地页面
     *
     * @Author:  fxp
     * @Date:    2018/7/31   下午5:02
     * @param    path
     * @return   void
     * @exception/throws
     */
    public void loadWeexPageLocal(String path){
        Map<String, Object> options = new HashMap<>();
        options.put(WXSDKInstance.BUNDLE_URL, "file:///android_asset/" + path);
        /**
         * WXSample 可以替换成自定义的字符串，针对埋点有效。
         * template 是.we transform 后的 js文件。
         * option 可以为空，或者通过option传入 js需要的参数。例如bundle js的地址等。
         * jsonInitData 可以为空。
         * width 为-1 默认全屏，可以自己定制。
         * height =-1 默认全屏，可以自己定制。
         */
        mWXSDKInstance.render("WXSample", WXFileUtils.loadAsset(path, this.getContext()), options, null, -1, -1, WXRenderStrategy.APPEND_ASYNC);
    }

    /**
     * @Description: 加载网络页面
     *
     * @Author:  fxp
     * @Date:    2018/7/31   下午5:03
     * @param    url
     * @return   void
     * @exception/throws
     */
    public void loadWeexPageRemote(final String url) {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Map<String, Object> options = new HashMap<>();
                options.put(WXSDKInstance.BUNDLE_URL, url);
                mWXSDKInstance.renderByUrl(TAG, url, options, null, WXRenderStrategy.APPEND_ASYNC);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mWXSDKInstance!=null){
            mWXSDKInstance.onActivityResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mWXSDKInstance!=null){
            mWXSDKInstance.onActivityPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mWXSDKInstance!=null){
            mWXSDKInstance.onActivityStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mWXSDKInstance!=null){
            mWXSDKInstance.onActivityDestroy();
        }
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        Log.e("mWXSDKInstance", "onViewCreated");
        weexLayout.addView(view);
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
        Log.e("mWXSDKInstance", "onRenderSuccess");
    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
        Log.e("mWXSDKInstance", "onRefreshSuccess");
    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {
        Log.e("mWXSDKInstance", "onException errCode: " + errCode + ", msg: " + msg);
    }
}
