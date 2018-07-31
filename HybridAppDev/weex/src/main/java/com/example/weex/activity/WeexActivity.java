package com.example.weex.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.weex.R;
import com.example.weex.fragment.WeexFragment;

public class WeexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weex);

//        loadWeexPage(0, "index.js");
        loadWeexPage(1, "http://doc.zwwill.com/yanxuan/jsbundles/index.js");
    }

    /**
     * @Description: 加载weex页面
     *
     * @Author:  fxp
     * @Date:    2018/7/31   下午5:05
     * @param    type   加载类型 【 0：本地页面， 1：网络页面 】
     * @param    path   页面路径。为本地页面时，不包含协议头部分(file:///android_asset/)
     * @return   void
     * @exception/throws
     */
    private void loadWeexPage(int type, String path){
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("path", path);
        WeexFragment weexFragment = new WeexFragment();
        weexFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_layout, weexFragment).commit();
    }
}
