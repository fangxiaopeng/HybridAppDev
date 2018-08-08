package com.example.weex.module;

import android.widget.Toast;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;


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

    //run ui thread
    @JSMethod(uiThread = true)
    public void printLog(String msg) {
        Toast.makeText(mWXSDKInstance.getContext(),msg,Toast.LENGTH_SHORT).show();
    }



}
