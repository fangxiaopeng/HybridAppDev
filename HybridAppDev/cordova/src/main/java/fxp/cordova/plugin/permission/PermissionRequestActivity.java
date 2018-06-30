package fxp.cordova.plugin.permission;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.cordova.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Title:       PermissionRequestActivity
 * <p>
 * Package:     fxp.cordova.plugin.permission
 * <p>
 * Author:      fxp
 * <p>
 * Create at:   2018/6/29 下午4:29
 * <p>
 * Description: 空Activity，用于动态请求权限
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
public class PermissionRequestActivity extends AppCompatActivity {

    /**
     * 动态权限申请
     */
    private static PermissionListener permissionListener;

    /**
     * @Description: 启动PermissionRequestActivity动态申请权限 - 供外部调用
     *
     * @Author:  fxp
     * @Date:    2018/6/30   下午1:03
     * @param    activity
     * @param    requestCode
     * @param    permissions
     * @param    listener
     * @return   void
     * @exception/throws
     */
    public static void startActivityForPermission(Activity activity, int requestCode, String[] permissions, PermissionListener listener) {
        permissionListener = listener;
        Intent intent = new Intent(activity, PermissionRequestActivity.class);
        intent.putExtra("requestCode", requestCode);
        intent.putExtra("permissions", permissions);
        ActivityCompat.startActivityForResult(activity, intent, requestCode, null);
        activity.overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 26){
            setTheme(R.style.NotTransparent);
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_permission);

        // 获取权限
        requestRuntimePermission(getIntent().getIntExtra("requestCode", 1), getIntent().getStringArrayExtra("permissions"), permissionListener);
    }

    /**
     * 运行权限申请
     */
    PermissionListener mListener;

    /**
     * @Description: 获取权限（先判断再获取）
     *
     * @Author:  fxp
     * @Date:    2018/6/30   下午1:09
     * @param    requestCode
     * @param    permissions
     * @param    listener
     * @return   void
     * @exception/throws
     */
    public void requestRuntimePermission(int requestCode, String[] permissions, PermissionListener listener) {
        mListener = listener;
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) { // 逐个检查是否有权限
                permissionList.add(permission);
            }
        }
        if (!permissionList.isEmpty()) {
            // 存在无权限项，请求权限
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 1);
        } else {
            // 已有全部权限，关闭当前Activity，并回调
            mListener.onGranted(requestCode);
            finish();
            overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            List<String> deniedPermissions = new ArrayList<>();
            for (int i = 0; i < grantResults.length; i++) {
                int grantResult = grantResults[i];
                String permission = permissions[i];
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissions.add(permission);
                }
            }
            if (deniedPermissions.isEmpty()) {
                mListener.onGranted(requestCode);
            } else {
                mListener.onDenied(requestCode, deniedPermissions);
            }
        }
        finish();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

}

