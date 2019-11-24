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
    <bar ref="bar" :options="options" :auto-resize="false" style="width: 100%;"></bar>
  </div>
</template>

<script>
  import ECharts from 'vue-echarts'
  import 'echarts/lib/chart/bar'
  import 'echarts/lib/component/tooltip'
  import 'echarts/lib/component/legend'
  import api from "../../api/api_statistic"
  import dateUtil from "../../common/date_util"
  export default {
    name: "UserGrowthBar",
    components: {
      bar: ECharts
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
        let _that = this;
        let params = {
          start: dateUtil.dateFormat(_that.datePicker.value[0], "yyyy-MM-dd"),
          end: dateUtil.dateFormat(_that.datePicker.value[1], "yyyy-MM-dd"),
        };
        api.getUserGrowth(params).then(res => {
          // console.log(res);
          let data = [], nameArr = [];
          res.forEach(v => {
            let value = v.sum ? v.sum : 0;
            let name = v.name ? v.name : "";
            data.push(value);
            nameArr.push(name);
          });
          _that.options = {
            color: ['#3398DB'],
            tooltip : {
              trigger: 'axis',
              axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
              }
            },
            grid: {
              left: '3%',
              right: '4%',
              bottom: '3%',
              containLabel: true
            },
            xAxis : [
              {
                type : 'category',
                data : nameArr,
                axisTick: {
                  alignWithLabel: true
                }
              }
            ],
            yAxis : [
              {
                type : 'value'
              }
            ],
            series : [
              {
                name:'用户数',
                type:'bar',
                barWidth: '60%',
                data
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
