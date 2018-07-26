<template>
  <div style="justify-content:center">
    <text class="label" onclick="click">testMyModule</text>
  </div>
</template>

<script>
  module.exports = {
    data: {},
    methods: {
      click: function() {
        weex.requireModule('MyModule').printLog("I am a weex Module");
      }
    },
    init: function () {
      console.log('在初始化内部变量，并且添加了事件功能后被触发');
    },
    created: function () {
      console.log('完成数据绑定之后，模板编译之前被触发');
    },
    ready: function () {
      console.log('模板已经编译并且生成了 Virtual DOM 之后被触发');
    },
    destroyed: function () {
      console.log('在页面被销毁时调用');
    }
  }
</script>

<style scoped>
  .label {
    color: #41B883;
    text-align: center;
    font-size: 100px;
    margin-top: 80px;
    margin-bottom: 100px;
  }
</style>