package fxp.cordova.plugin.permission;

import java.util.List;

/**
 * Title:       PermissionListener
 * <p>
 * Package:     fxp.cordova.plugin.permission
 * <p>
 * Author:      fxp
 * <p>
 * Create at:   2018/6/29 下午4:30
 * <p>
 * Description: 动态请求权限结果监听接口
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
public interface PermissionListener {

    void onGranted(int requestCode);

    void onDenied(int requestCode, List<String> deniedPermission);

}
