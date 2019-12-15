<template>
  <div>
    <el-row>
      <el-col :span="12">
        <el-card class="box-card" v-if="hasPerm('statistic:product:proportion')">
          <div slot="header" class="clearfix">
          <span>产品消费占比</span>
        </div>
          <product-sale-proportion-pie></product-sale-proportion-pie>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="box-card" v-if="hasPerm('statistic:user:growth')">
          <div slot="header" class="clearfix">
          <span>用户增长</span>
        </div>
          <user-growth-bar></user-growth-bar>
        </el-card>
      </el-col>
    </el-row>
    <el-row :span="12">
      <el-card class="box-card" v-if="hasPerm('statistic:consume-limit:growth')">
        <div slot="header" class="clearfix">
          <span>消费统计</span>
        </div>
        <consume-limit-line></consume-limit-line>
      </el-card>
    </el-row>
  </div>
</template>

<script>
  import ProductSaleProportionPie from "./ProductSaleProportionPie";
  import UserGrowthBar from "./UserGrowthBar";
  import ConsumeLimitLine from "./ConsumeLimitLine";
  export default {
    name: "Homepage",
    components: {
      ProductSaleProportionPie,
      UserGrowthBar,
      ConsumeLimitLine
    }
  };
</script>

<style scoped>
</style>
