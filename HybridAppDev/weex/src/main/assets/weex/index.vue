<template>
    <list class="list">
        <cell>
            <text @click="printLog" class="text_btn">打印日志</text>
        </cell> 
        <cell>
            <text @click="showToast" class="text_btn">Android Toast</text>
        </cell> 
    </list>
</template>
<script>
    var modal = weex.requireModule('modal')
    //加载MyModule
    var fxpModule = weex.requireModule('MyModule');

    module.exports = {
        methods: {
            printLog: function () {
                fxpModule.printLog("我是一个测试!");
            },
            showToast: function(){
                fxpModule.showToast("Android Toast");
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