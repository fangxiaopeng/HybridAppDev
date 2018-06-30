package fxp.cordova.plugin.customplugin;

import android.content.Intent;

import com.example.cordova.activity.BaseActivity;

import java.util.List;

import framework.src.org.apache.cordova.CordovaInterface;
import framework.src.org.apache.cordova.CordovaPlugin;
import framework.src.org.apache.cordova.CordovaWebView;
import fxp.cordova.plugin.inter.ActivityResultListener;
import fxp.cordova.plugin.permission.PermissionListener;
import fxp.cordova.plugin.permission.PermissionRequestActivity;

/**
 * Title:       BasePlugin
 * <p>
 * Package:     fxp.cordova.plugin.customplugin
 * <p>
 * Author:      fxp
 * <p>
 * Create at:   2018/6/29 下午6:51
 * <p>
 * Description: CordovaPlugin基类，封装了动态请求权限请求、onActivityResult回调监听
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
public abstract class BasePlugin extends CordovaPlugin {

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        setActivityResultListener();
    }

    /**
     * @Description: 设置startActivityForResult回调监听
     *
     * @Author:  fxp
     * @Date:    2018/6/29   下午6:56
     * @param
     * @return   void
     * @exception/throws
     */
    private void setActivityResultListener(){
        BaseActivity.setActivityResultListener(new ActivityResultListener() {
            @Override
            public void result(int requestCode, int resultCode, Intent intent) {
                onStartActivityResult(requestCode, resultCode, intent);
            }
        });
    }

    /**
     * @Description: 请求权限
     *
     * @Author:  fxp
     * @Date:    2018/6/29   下午7:09
     * @param    requestCode
     * @param    permission
     * @return   void
     * @exception/throws
     */
    protected void requestPermission(final int requestCode, String[] permission){
        PermissionRequestActivity.startActivityForPermission(cordova.getActivity(), requestCode, permission, new PermissionListener() {
            @Override
            public void onGranted(int requestCode) {
                onPermissionGranted(requestCode);
            }

            @Override
            public void onDenied(int requestCode, List<String> deniedPermission) {
                onPermissionDenied(requestCode, deniedPermission);
            }
        });
    }

    abstract void onPermissionGranted(int requestCode);

    abstract void onPermissionDenied(int requestCode, List<String> deniedPermission);

    abstract void onStartActivityResult(int requestCode, int resultCode, Intent intent);

}
