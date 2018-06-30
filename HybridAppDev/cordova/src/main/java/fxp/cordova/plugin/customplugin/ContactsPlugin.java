package fxp.cordova.plugin.customplugin;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import framework.src.org.apache.cordova.CallbackContext;
import framework.src.org.apache.cordova.CordovaInterface;
import framework.src.org.apache.cordova.CordovaWebView;

/**
 * Title:       ContactsPlugin
 * <p>
 * Package:     fxp.cordova.plugin.customplugin
 * <p>
 * Author:      fxp
 * <p>
 * Create at:   2018/6/29 下午3:35
 * <p>
 * Description: 自定义插件实现 - 以从通讯录选择联系人为例
 * 技术点：1，动态获取权限；    2，异步回调；
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
public class ContactsPlugin extends BasePlugin{

    private static final String TAG = ContactsPlugin.class.getSimpleName();

    private Context mContext;

    private CallbackContext callbackContext;

    final private static int REQUEST_CODE_CONTACTS = 99;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        mContext = cordova.getActivity();

    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.e(TAG, "action --> " + action + "\n args --> " + args);
        this.callbackContext = callbackContext;
        if ("getContracts".equals(action)) {
            requestPermission(REQUEST_CODE_CONTACTS, new String[]{ Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE });
        } else {
            return false;
        }
        return true;
    }

    @Override
    void onPermissionGranted(int requestCode) {
        // 请求权限成功，跳转到联系人页面
        toSystemContractsActivity();
    }

    @Override
    void onPermissionDenied(int requestCode, List<String> deniedPermission) {
        Toast.makeText(mContext, "获取权限失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    void onStartActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == Activity.RESULT_OK){
            // 获取返回结果成功，获取选择结果
            callbackContext.success(getSelectResult(mContext,intent));
        }
    }

    /**
     * @Description: 跳转到联系人选择页面
     *
     * @Author:  fxp
     * @Date:    2018/6/29   下午7:51
     * @param
     * @return   void
     * @exception/throws
     */
    private void toSystemContractsActivity(){
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            cordova.setActivityResultCallback(this);
            cordova.startActivityForResult(this,intent,REQUEST_CODE_CONTACTS);
        }catch (Exception e){
            e.printStackTrace();
            callbackContext.error("打开通讯录失败！");
        }
    }

    /**
     * @Description: 获取联系人选择结果
     *
     * @Author:  fxp
     * @Date:    2018/6/29   下午7:51
     * @param    context
     * @param    intent
     * @return   void
     * @exception/throws
     */
    public JSONObject getSelectResult(Context context,Intent  intent) {
        JSONObject contactInfo = new JSONObject();
        String name = "", telNum = "";
        try {
            ContentResolver cr = context.getContentResolver();
            Uri uri = intent.getData();
            Cursor cursor = cr.query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                //取得联系人姓名
                int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                name = cursor.getString(nameFieldColumnIndex);

                //取得电话号码
                String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
                if (phone != null) {
                    phone.moveToFirst();
                    telNum = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }

                try {
                    contactInfo.put("name", name);
                    contactInfo.put("number", telNum);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                phone.close();
                cursor.close();
            } else {
                Log.e(TAG, "联系人获取结果为空");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return contactInfo;
        }
    }

}
