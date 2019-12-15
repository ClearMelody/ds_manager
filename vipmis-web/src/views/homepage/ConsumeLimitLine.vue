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
    <chart  ref="chart1" :options="options" :auto-resize="false" style="width: 100%;"></chart>
  </div>
</template>

<script>
  import Vue from 'vue'
  import ECharts from 'vue-echarts'
  import 'echarts/lib/chart/line'
  import 'echarts/lib/component/tooltip'
  import 'echarts/lib/component/legend'
  import api from "../../api/api_statistic"
  import dateUtil from "../../common/date_util"
  Vue.component('chart', ECharts)
  export default {
    name: "ConsumeLimitLine",
    components: {
      chart: ECharts
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
        _that.refreshLine();
      },
      initTime() {
        let _that = this;
        const end = new Date();
        const start = new Date();
        start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
        _that.datePicker.value = [start, end];
      },
      refreshLine() {
        if (!this.hasPerm('statistic:consume-limit:growth')) {
          this.noPermTip();
        }
        let _that = this;
        let params = {
          start: dateUtil.dateFormat(_that.datePicker.value[0], "yyyy-MM-dd"),
          end: dateUtil.dateFormat(_that.datePicker.value[1], "yyyy-MM-dd"),
        };
        api.getConsumeLimitGrowth(params).then(res => {
          let titleList = res.titleList;
          let data = res.barResult;
          let series = [];
          for (let i = 0,len = titleList.length; i < len; i++) {
            let obj = {
              name:titleList[i],
              type:'bar',
              data:data[titleList[i]]
            }
            series.push(obj)
          }
          series.push({
            name:'日总',
            type:'line',
            data:res.lineResult
          })
          _that.options = {
            tooltip: {
              trigger: 'axis',
              axisPointer: {
                type: 'cross',
                crossStyle: {
                  color: '#999'
                }
              }
            },
            toolbox: {
              feature: {
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
              }
            },
            legend: {
              data:titleList
            },
            xAxis: [
              {
                type: 'category',
                data: res.dateList,
                axisPointer: {
                  type: 'shadow'
                }
              }
            ],
            yAxis: [
              {
                type: 'value',
                name: '额度',
                axisLabel: {
                  formatter: '{value} 元'
                }
              }
            ],
            series: series
          };
        }).catch(() => {});
      }
    },
    mounted() {
      let _that = this;
      _that.initTime();
      _that.refreshLine();
    }
  }
</script>

<style scoped>

</style>
