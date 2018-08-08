package com.example.weex.module;

import android.util.Log;
import android.widget.Toast;

import com.example.weex.utils.AppUtil;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import org.json.JSONObject;


/**
 * Module 扩展（http://weex.apache.org/cn/guide/extend-android.html）：
 * 1，Module 扩展必须继承 WXModule 类。
 * 2，扩展方法必须加上@JSMethod (uiThread = false or true) 注解。Weex 会根据注解来判断当前方法是否要运行在 UI 线程，和当前方法是否是扩展方法。
 * 3，Weex是根据反射来进行调用 Module 扩展方法，所以Module中的扩展方法必须是 public 类型。
 * 4，同样因为是通过反射调用，Module 不能被混淆。请在混淆文件中添加代码：-keep public class * extends com.taobao.weex.common.WXModule{*;}
 * 5，Module 扩展的方法可以使用 int, double, float, String, Map, List 类型的参数
 * 6，完成 Module 后一定要在初始化时注册 WXSDKEngine.registerModule("myModule", MyModule.class); 否则会报类似错误：ReportException :undefined:9: TypeError: Object #<Object> has no method 'printLog'
 */
public class MyModule extends WXModule{

    private final static String TAG = MyModule.class.getSimpleName();

    //run ui thread
    @JSMethod(uiThread = true)
    public void printLog(String msg) {
        Log.e(TAG, msg);
    }

    @JSMethod(uiThread = true)
    public void showToast(String msg){
        Toast.makeText(mWXSDKInstance.getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * @Description:    获取设备型号
     * 回调示例
     *
     * @Author:  fxp
     * @Date:    2018/8/8   下午5:56
     * @param    callback
     * @return   void
     * @exception/throws
     */
    @JSMethod(uiThread = false)
    public void getDeviceModel(JSCallback callback){
        callback.invoke(android.os.Build.MODEL);
    }

    /**
     * @Description:    运行应用
     *
     * @Author:  fxp
     * @Date:    2018/8/8   下午6:34
     * @param    packName
     * @param    callback
     * @return   void
     * @exception/throws
     */
    @JSMethod(uiThread = false)
    public void runApp(String packName, JSCallback callback){
        AppUtil appUtil = new AppUtil(this.mWXSDKInstance.getContext());
        appUtil.runApp(packName, new JSONObject());
//        callback.invoke("");
    }

}
