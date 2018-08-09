<template>
    <list class="list">
        <cell>
            <text @click="printLog" class="text_btn">打印日志</text>
        </cell> 
        <cell>
            <text @click="showToast" class="text_btn">Android Toast（传参示例）</text>
        </cell> 
        <cell>
            <text @click="getDeviceModel" class="text_btn">获取设备型号（回调示例）</text>
        </cell> 
        <cell>
            <text @click="runApp" class="text_btn">运行/安装 TIM</text>
        </cell> 
        <cell>
            <text @click="exit" class="text_btn">退出应用</text>
        </cell> 
    </list>
</template>
<script>
    var modal = weex.requireModule('modal')
    //加载MyModule
    var fxpModule = weex.requireModule('MyModule');

    module.exports = {
        methods: {
            // 打印日志
            printLog: function () {
                fxpModule.printLog("我是一个测试!");
            },
            // Android Toast（传参示例）
            showToast: function(){
                fxpModule.showToast("Android Toast");
            },
            // 获取设备型号（回调示例）
            getDeviceModel: function(){
                fxpModule.getDeviceModel(function (message) {
                    //回调后处理
                    modal.alert({
                        message: message,
                        duration: 0
                    }, function (value) {
                        console.log('alert callback', value)
                    })
                });
            },
            // 运行/安装 TIM
            runApp: function(){
                fxpModule.runApp("com.tencent.tim", function (message) {
                    //回调后处理
                    modal.alert({
                        message: message,
                        duration: 0
                    }, function (value) {
                        console.log('alert callback', value)
                    })
                });
            },
            // 退出应用
            exit: function(){
                fxpModule.exit();
            }

        }
    }
</script>

<style>
    .list{
        overflow: scroll;
    }

    .text_btn{
        width:600px;
        margin:10px;
        padding:5px;
        font-weight:bold;
        font-size:20px;
        line-height:35px;
        text-align:center;
        border: 2px solid #000000;
        border-radius:15px; 
        background-color:gray;
    }
</style>