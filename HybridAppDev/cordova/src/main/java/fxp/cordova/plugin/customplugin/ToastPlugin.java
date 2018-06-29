package fxp.cordova.plugin.customplugin;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import framework.src.org.apache.cordova.CallbackContext;
import framework.src.org.apache.cordova.CordovaInterface;
import framework.src.org.apache.cordova.CordovaPlugin;
import framework.src.org.apache.cordova.CordovaWebView;

/**
 * Title:       ToastPlugin
 * <p>
 * Package:     fxp.plugin.cordova.customplugin
 * <p>
 * Author:      fxp
 * <p>
 * Create at:   2018/6/28 下午6:21
 * <p>
 * Description: 自定义插件实现 - 以最简单的Toast为例。无同步/异步回调
 * <p>
 * <p>
 * Modification History:
 * <p>
 * Date       Author       Version      Description
 * -----------------------------------------------------------------
 * 2018/6/28    fxp       1.0         First Created
 * <p>
 * Github:  https://github.com/fangxiaopeng
 */
public class ToastPlugin extends CordovaPlugin{

    private static final String TAG = ToastPlugin.class.getSimpleName();

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

    }

    /**
     * @param action          The action to execute.
     * @param args            The exec() arguments, wrapped with some Cordova helpers.
     * @param callbackContext The callback context used when calling back into JavaScript.
     * @return
     * @throws JSONException
     */
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.e(TAG, "action --> " + action + "\n args --> " + args);
        final Context mContext = this.cordova.getActivity();
        if ("showToast".equals(action)) {
            String msg = args.getString(0);
            Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

}
