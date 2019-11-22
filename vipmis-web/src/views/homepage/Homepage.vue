<template>
    <div>
      <el-row>
        <el-col :span="12">
          <product-sale-proportion-pie></product-sale-proportion-pie>
        </el-col>
        <el-col :span="12">
          <user-growth-bar></user-growth-bar>
        </el-col>
      </el-row>
      <el-row :span="12">
        <consume-limit-line></consume-limit-line>
      </el-row>
    </div>
</template>

<script>
  import ProductSaleProportionPie from "./ProductSaleProportionPie"
  import UserGrowthBar from "./UserGrowthBar"
  import ConsumeLimitLine from "./ConsumeLimitLine"
  export default {
    name: "Homepage",
    components: {
      ProductSaleProportionPie,
      UserGrowthBar,
      ConsumeLimitLine
    }
  }
</script>

<style scoped>

</style>
