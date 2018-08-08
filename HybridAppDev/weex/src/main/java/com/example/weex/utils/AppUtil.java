package com.example.weex.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.util.Iterator;

/**
 * Title:       AppUtil
 * <p>
 * Package:     com.example.weex.utils
 * <p>
 * Author:      fxp
 * <p>
 * Create at:   2018/8/8 下午6:23
 * <p>
 * Description:
 * <p>
 * <p>
 * Modification History:
 * <p>
 * Date       Author       Version      Description
 * -----------------------------------------------------------------
 * 2018/8/8    fxp       1.0         First Created
 * <p>
 * Github:  https://github.com/fangxiaopeng
 */
public class AppUtil {

    private final static String TAG = AppUtil.class.getSimpleName();

    private AppUtil instance;

    private Context context;

    public AppUtil(Context context){
        this.context = context;
    }

    public AppUtil getInstance(Context context){

        if (instance == null){
            instance = new AppUtil(context);
        }

        return instance;
    }

    /**
     * @Description: 执行第三方的应用，如果是传入http的远程地址，将会调用系统自带的浏览器打开远程页面
     *
     * @Author:  fxp
     * @Date:    2018/4/19   下午3:43
     * @param    appId 应用Id
     * @param    data        启动参数，格式：{"user" : "yulsh","status" : 1}
     * @return   void
     * @exception/throws
     */
    public void runApp(String appId, JSONObject data) {
        boolean isExits = isAppInstalled(appId);
        if (isExits){
            // 应用已安装，直接启动
            runAppLocal(appId,data);
        }else {
            // 应用未安装，调用系统自带的浏览器打开远程页面
            runAppRemote(appId);
        }
    }

    /**
     * @Description: 执行第三方的应用
     * 应用已安装，直接运行；
     * 应用未安装：
     * 如果传入远程apk地址，则调用系统自带的浏览器打开远程页面；
     * 如果传入本地apk路径，则本地安装
     *
     * @Author:  fxp
     * @Date:    2018/6/22   上午11:33
     * @param    appId      应用Id
     * @param    data       启动参数，格式：{"user" : "yulsh","status" : 1}
     * @param    apkPath    安装路径（网络/本地）
     * @return   void
     * @exception/throws
     */
    public void runApp(String appId, JSONObject data, String apkPath) {
        boolean isExits = isAppInstalled(appId);
        if (isExits){
            // 应用已安装，直接启动
            runAppLocal(appId,data);
        }else {
            // 应用未安装
            if (apkPath.contains("http") || apkPath.contains("https")){
                // 调用系统自带的浏览器打开远程页面
                runAppRemote(apkPath);
            }else {
                // 本地路径安装
                installApk(apkPath);
            }
        }
    }

    /**
     * @Description: 运行第三方应用（已安装）
     *
     * @Author:  fxp
     * @Date:    2018/4/21   下午3:20
     * @param    appId 应用Id
     * @param    data        启动参数，格式：{"user" : "yulsh","status" : 1}
     * @return   void
     * @exception/throws
     */
    public void runAppLocal(String appId, JSONObject data){
        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(appId);
//            intent.putExtra("data", data.toString());
            if (null != data) {
                Iterator< String > iterator = data.keys();
                while (iterator.hasNext()){
                    String key = iterator.next();
                    String value = data.getString(key);
                    intent.putExtra(key,value);
                }
            }
            context.startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @Description:  传入http的远程地址，调用系统自带的浏览器打开远程页面
     *
     * @Author:  fxp
     * @Date:    2018/4/21   下午3:24
     * @param    appUrl 应用远程地址
     * @return   void
     * @exception/throws
     */
    public void runAppRemote(String appUrl){
        if (!appUrl.equals("") && appUrl != null){
            try {
                Uri uri = Uri.parse(appUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }catch (Exception e){
                Toast.makeText(context,"appId/url有误，请确认",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }else {
            // downloadUrl为空时，启动Activity会报错
            Toast.makeText(context,"appId/url为空，请确认",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @Description:    检测应用是否安装
     *
     * @Author:  fxp
     * @Date:    2018/4/19   下午2:43
     * @param    packagename
     * @return   boolean
     * @exception/throws
     */
    public boolean isAppInstalled(String packagename) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @Description: 安装应用
     *
     * @Author:  fxp
     * @Date:    2018/4/18   下午4:43
     * @param    apkFilePath 安装包路径
     * @return   void
     * @exception/throws
     */
    public void installApk(String apkFilePath){
        try{
            File apkfile = new File(apkFilePath);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
