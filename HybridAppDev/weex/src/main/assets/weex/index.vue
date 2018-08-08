<template>
    <list class="list">
        <cell>
            <text @click="onClick" class="text_btn">Android Toast</text>
        </cell> 

    </list>
</template>
<script>
    var modal = weex.requireModule('modal')
    //加载MyModule
    var fxpModule = weex.requireModule('MyModule');

    module.exports = {
        methods: {
            onClick: function () {
                fxpModule.printLog("我是一个测试!");
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