<template>
  <el-main style="padding:20px;">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card class="card-total">
          <svg-icon class="svg_total" icon-class="data_total"/>
          <div class="total_descri">
            <div class="descri_name">数据量</div>
            <div class="descri_num">124141</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="card-total">
          <svg-icon class="svg_total" icon-class="data_resource"/>
          <div class="total_descri">
            <div class="descri_name">数据源</div>
            <div class="descri_num">8</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="card-total">
          <svg-icon class="svg_total" icon-class="data_table_static"/>
          <div class="total_descri">
            <div class="descri_name">数据资源</div>
            <div class="descri_num">5</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="card-total">
          <svg-icon class="svg_total" icon-class="data_monitor"/>
          <div class="total_descri">
            <div class="descri_name">数据监控</div>
            <div class="descri_num">3</div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card class="card-elem">
          <span class="card-elem-title">学生</span>
          <el-divider></el-divider>
          <div class="card-main">
            <div class="card-top">
              <el-row :gutter="20">
                <el-col :span="12">
                  <div class="card-item">
                    <label>资源状态</label>
                    <el-tag type="success" effect="dark" size="mini" style="margin-left: 10px;">正常</el-tag>
                  </div>
                </el-col>
                <el-col :span="12">
                  <div class="card-item">
                    <label>数据源状态</label>
                    <el-tag type="success" effect="dark" size="mini" style="margin-left: 10px;">正常</el-tag>
                  </div>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :span="12">
                  <div class="card-item">
                    <label>今日数据量</label>
                    <span class="card-item-num">12411</span>
                  </div>
                </el-col>
                <el-col :span="12">
                  <div class="card-item">
                    <label>数据更新时间</label>
                    <span class="card-item-date">2020-01-19 12:04:13</span>
                  </div>
                </el-col>
              </el-row>
            </div>
            <div style="height:210px;margin-top:15px;">
              <chart ref="line" :options="options" style="width: 100%;height:210px;"></chart>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="card-elem">
          <span class="card-elem-title">重点人员</span>
          <el-divider></el-divider>
          <div class="card-main">
            <div class="card-top">
              <el-row :gutter="20">
                <el-col :span="12">
                  <div class="card-item">
                    <label>资源状态</label>
                    <el-tag type="success" effect="dark" size="mini" style="margin-left: 10px;">正常</el-tag>
                  </div>
                </el-col>
                <el-col :span="12">
                  <div class="card-item">
                    <label>数据源状态</label>
                    <el-tag type="success" effect="dark" size="mini" style="margin-left: 10px;">正常</el-tag>
                  </div>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :span="12">
                  <div class="card-item">
                    <label>今日数据量</label>
                    <span class="card-item-num">12411</span>
                  </div>
                </el-col>
                <el-col :span="12">
                  <div class="card-item">
                    <label>数据更新时间</label>
                    <span class="card-item-date">2020-01-19 12:04:13</span>
                  </div>
                </el-col>
              </el-row>
            </div>
            <div style="height:210px;margin-top:15px;">
              <chart ref="line" :options="options" style="width: 100%;height:210px;"></chart>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="card-elem">
          <span class="card-elem-title">卡口过车</span>
          <el-divider></el-divider>
          <div class="card-main">
            <div class="card-top">
              <el-row :gutter="20">
                <el-col :span="12">
                  <div class="card-item">
                    <label>资源状态</label>
                    <el-tag type="success" effect="dark" size="mini" style="margin-left: 10px;">正常</el-tag>
                  </div>
                </el-col>
                <el-col :span="12">
                  <div class="card-item">
                    <label>数据源状态</label>
                    <el-tag type="success" effect="dark" size="mini" style="margin-left: 10px;">正常</el-tag>
                  </div>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :span="12">
                  <div class="card-item">
                    <label>今日数据量</label>
                    <span class="card-item-num">12411</span>
                  </div>
                </el-col>
                <el-col :span="12">
                  <div class="card-item">
                    <label>数据更新时间</label>
                    <span class="card-item-date">2020-01-19 12:04:13</span>
                  </div>
                </el-col>
              </el-row>
            </div>
            <div style="height:210px;margin-top:15px;">
              <chart ref="line" :options="options" style="width: 100%;height:210px;"></chart>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </el-main>
</template>

<script>
  import Vue from 'vue'
  import echarts from 'vue-echarts'
  import 'echarts/lib/chart/line'
  import 'echarts/lib/component/tooltip'
  import 'echarts/lib/component/legend'

  Vue.prototype.$echarts = echarts

  export default {
    name: "Index",
    data() {
      return {
        options: {},
        options2: {}
      }
    },
    components: {
      chart: echarts,
    },
    methods: {
      refreshLine() {
        let _this = this;
        _this.options = {
          title: {
            show: true,
            text: '每日数据流量图',
            textStyle: {
              color: 'blue'
            },
            top: 0
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              label: {
                backgroundColor: '#6a7985'
              }
            }
          },
          toolbox: {
            feature: {
              saveAsImage: {}
            }
          },
          grid: {
            left: '3%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: [
            {
              type: 'category',
              name: '日期',
              boundaryGap: false,
              data: ['2020-01-10', '2020-01-11', '2020-01-12']
            }
          ],
          yAxis: [
            {
              type: 'value',
              name: '数据量'
            }
          ],
          series: [
            {
              name: '数据量',
              type: 'line',
              stack: '总量',
              smooth: true, //是否平滑曲线显示
              showAllSymbol: true,
              label: {
                show: true,
                position: 'top',
                textStyle: {
                  color: '#009688',
                }
              },
              data: [23414, 14241, 12314],
              areaStyle: {
                normal: {
                  color: new _this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
                    {
                      offset: 0,
                      color: '#eb64fb'
                    },
                    {
                      offset: 1,
                      color: '#3fbbff'
                    }
                  ], false),
                }
              }
            }
          ]
        };
      }
    },
    mounted() {
      this.refreshLine();
    }
  }
</script>

<style scoped>
  .el-row {
    margin-bottom: 20px;
  }

  :last-child {
    margin-bottom: 0;
  }

  .el-col {
    border-radius: 4px;
  }

  .card-total {
    height: 150px;
  }

  .card-elem {
    height: 500px;
  }

  .card-elem-title {
    font-size: 25px;
  }

  .svg_total {
    font-size: 100px;
    display: block;
    float: left;
    margin-top: 5px;
  }

  .total_descri {
    float: left;
    margin-left: 20px;
  }

  .descri_name {
    height: 30px;
    line-height: 30px;
    font-size: 20px;
    color: #947d7d;
  }

  .descri_num {
    height: 70px;
    line-height: 70px;
    font-size: 40px;
    color: rgb(31, 149, 220);
    font-weight: 600;
  }

  .card-main {
    height: 385px;
  }

  .card-top {
    height: 160px;
  }

  .card-item {
    height: 80px;
    background: #f8f8f8;
  }

  .card-item label {
    display: inline-block;
    width: 100%;
    padding-left: 10px;
    height: 40px;
    line-height: 40px;
    font-size: 16px;
  }

  .card-item-num {
    padding-left: 10px;
    height: 40px;
    line-height: 30px;
    font-size: 20px;
    font-weight: 600;
    color: #458dc1;
  }

  .card-item-date {
    padding-left: 10px;
    height: 40px;
    line-height: 30px;
    font-size: 20px;
    font-weight: 600;
    color: #458dc1;
  }
</style>
