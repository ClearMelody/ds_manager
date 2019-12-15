<template>
  <div>
    <el-date-picker
      v-model="datePicker.value"
      type="daterange"
      align="right"
      unlink-panels
      range-separator="至"
      start-placeholder="开始日期"
      end-placeholder="结束日期"
      @change="dateChange"
      :picker-options="datePicker.pickerOptions">
    </el-date-picker>
    <pie ref="pie" :options="options" :auto-resize="false" style="width: 100%;"></pie>
  </div>
</template>

<script>
  import ECharts from 'vue-echarts'
  import 'echarts/lib/chart/pie'
  import 'echarts/lib/component/tooltip'
  import 'echarts/lib/component/legend'
  import api from "../../api/api_statistic"
  import dateUtil from "../../common/date_util"
  export default {
    name: "ProductSaleProportionPie",
    components: {
      pie: ECharts
    },
    data() {
      return {
        options: {},
        daterange: "date",
        datePicker: {
          value: [],
          pickerOptions: {
            shortcuts: [{
              text: '最近一周',
              onClick(picker) {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                picker.$emit('pick', [start, end]);
              }
            }, {
              text: '最近一个月',
              onClick(picker) {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                picker.$emit('pick', [start, end]);
              }
            }, {
              text: '最近三个月',
              onClick(picker) {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                picker.$emit('pick', [start, end]);
              }
            }]
          }
        }
      }
    },
    methods: {
      dateChange() {
        let _that = this;
        _that.refreshPie();
      },
      initTime() {
        let _that = this;
        const end = new Date();
        const start = new Date();
        start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
        _that.datePicker.value = [start, end];
      },
      refreshPie() {
        if (!this.hasPerm('statistic:product:proportion')) {
          this.noPermTip();
        }
        let _that = this;
        let params = {
          start: dateUtil.dateFormat(_that.datePicker.value[0], "yyyy-MM-dd"),
          end: dateUtil.dateFormat(_that.datePicker.value[1], "yyyy-MM-dd"),
        };
        api.getProductProportion(params).then(res => {
          let data = [], nameArr = [];
          res.forEach(v => {
            let value = v.sum ? (v.sum < 0 ? -v.sum : v.sum) : 0;
            let name = v.title ? v.title : "";
            data.push({
              value: (value * 1).toFixed(2),
              name
            });
            nameArr.push({
              name
            });
          });
          _that.options = {
            tooltip : {
              trigger: 'item',
              formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
              left: 'center',
              data: nameArr
            },
            series : [
              {
                name: '产品消费占比',
                type: 'pie',
                radius : '55%',
                center: ['50%', '50%'],
                data,
                itemStyle: {
                  emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                  }
                }
              }
            ]
          };
        }).catch(() => {});
      }
    },
    mounted() {
      let _that = this;
      _that.initTime();
      _that.refreshPie();
    }
  }
</script>

<style scoped>

</style>
