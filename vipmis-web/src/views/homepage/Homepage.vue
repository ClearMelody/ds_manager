<template>
    <div>
      <el-row>
        <product-sale-proportion-pie></product-sale-proportion-pie>
      </el-row>
      <el-row>
        <user-growth-bar></user-growth-bar>
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
