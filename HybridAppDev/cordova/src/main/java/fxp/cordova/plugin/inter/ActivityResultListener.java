package fxp.cordova.plugin.inter;

import android.content.Intent;

/**
 * Title:       ActivityResultListener
 * <p>
 * Package:     fxp.cordova.plugin.inter
 * <p>
 * Author:      fxp
 * <p>
 * Create at:   2018/6/29 下午6:08
 * <p>
 * Description: startActivityForResult回调结果监听
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
public interface ActivityResultListener {

    void result(int requestCode, int resultCode, Intent intent);

}
