<template>
  <div>
    <div>你好，Hello，{{name}}</div>

    <el-radio v-model="radio" label="1">备选项</el-radio>
    <el-radio v-model="radio" label="2">备选项</el-radio>
  </div>

</template>

<script>
export default {
  name: "Hello",
  data(){
    return{
      name:"张三",
      radio: '1'
    }
  }
}
</script>

<style scoped>

</style>
