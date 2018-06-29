/**
* cordova.define 的第一个参数就是cordova_plugins.js里面定义的id
* exec方法参数说明：
* 参数1：成功回调function
* 参数2：失败回调function
* 参数3：feature name，与config.xml中注册的一致
* 参数4：调用java类时的action
* 参数5：要传递的参数，json数组格式
*/
cordova.define("fxp-cordova-plugin.ToastPlugin",
    function(require, exports, module) {
        var exec = require("cordova/exec");
        module.exports = {
            showToast: function(content){
                exec(null,null,"ToastPlugin","showToast",[content]);
            }

        }
});
