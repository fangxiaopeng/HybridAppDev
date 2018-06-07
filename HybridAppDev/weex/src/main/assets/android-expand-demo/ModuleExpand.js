<template>
  <div style="justify-content:center">
    <text class="label" onclick="click">testMyModule</text>
  </div>
</template>

<script>
  module.exports = {
    methods: {
      click: function() {
        weex.requireModule('MyModule').printLog("I am a weex Module");
      }
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