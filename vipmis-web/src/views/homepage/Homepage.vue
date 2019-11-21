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
    </div>
</template>

<script>
  import ProductSaleProportionPie from "./ProductSaleProportionPie"
  import UserGrowthBar from "./UserGrowthBar"
  export default {
    name: "Homepage",
    components: {
      ProductSaleProportionPie,
      UserGrowthBar
    }
  }
</script>

<style scoped>

</style>
