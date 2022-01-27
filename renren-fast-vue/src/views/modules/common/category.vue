<template>
  <el-tree
    @node-click="nodeClick"
    ref="menuTree"
    node-key="catId"
    :data="menus"
    :props="menusProps">
  </el-tree>
</template>

<script>
export default {
  name: 'category',
  data () {
    return {
      menus: [],
      menusProps: {
        children: 'children',
        label: 'name'
      }
    }
  },
  methods: {
    getMenus () {
      this.$http({
        url: this.$http.adornUrl('/product/category/list/tree'),
        method: 'get'
      }).then(({data}) => {
        console.log('成功获取到菜单数据...', data.data)
        this.menus = data.data
      })
    },
    nodeClick (data, node, component) {
      console.log('子组件category的节点被点击:', data, node, component)
      // 向父组件发送事件
      this.$emit('treeNodeClick', data, node, component)
    }
  },
  created () {
    this.getMenus()
  }
}
</script>

<style scoped>

</style>
