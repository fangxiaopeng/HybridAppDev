package com.example.cordova.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fxp.cordova.plugin.inter.ActivityResultListener;

/**
 * Title:       BaseActivity
 * <p>
 * Package:     com.example.cordova.activity
 * <p>
 * Author:      fxp
 * <p>
 * Create at:   2018/6/29 下午6:37
 * <p>
 * Description: 基类，用于统一监听startActivityForResult回调
 * <p>
 * <p>
 * Modification History:
 * <p>
 * Date       Author       Version      Description
 * -----------------------------------------------------------------
 * 2018/6/29    fxp       1.0         First Created
 * <p>
 * Github:  https://github.com/fangxiaopeng
 */
public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    private static ActivityResultListener activityResultListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * @Description: 设置startActivityForResult回调监听
     *
     * @Author:  fxp
     * @Date:    2018/6/29   下午6:33
     * @param    activityResultListener
     * @return   void
     * @exception/throws
     */
    public static void setActivityResultListener(ActivityResultListener activityResultListener){
        BaseActivity.activityResultListener = activityResultListener;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (activityResultListener != null){
            activityResultListener.result(requestCode, resultCode, intent);
        }
    }

}
