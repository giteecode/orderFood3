<template>
  <div class="content">
    <div class="text main-text">欢迎使用 {{ $project.projectName }}</div>

    <!-- 统计卡片区域 -->
    <div class="stats-cards">
      <div class="stat-card">
        <div class="stat-title">订单总数</div>
        <div class="stat-value">{{ orderTotal }}</div>
      </div>
      <div class="stat-card">
        <div class="stat-title">用户总数</div>
        <div class="stat-value">{{ userTotal }}</div>
      </div>
      <div class="stat-card">
        <div class="stat-title">菜品总数</div>
        <div class="stat-value">{{ dishTotal }}</div>
      </div>
    </div>

    <!-- 图表区域 -->
    <div class="charts-container">
      <!-- 订单状态柱状图 -->
      <div class="chart-item">
        <div class="chart-title">订单状态分布</div>
        <div ref="orderStatusChart" class="chart-box"></div>
      </div>

      <!-- 销售额折线图 -->
      <div class="chart-item">
        <div class="chart-title">销售额趋势</div>
        <div ref="salesChart" class="chart-box"></div>
      </div>

      <!-- 菜品类型环状图 -->
      <div class="chart-item">
        <div class="chart-title">菜品类型占比</div>
        <div ref="dishTypeChart" class="chart-box"></div>
      </div>
    </div>
  </div>
</template>

<script>
import router from '@/router/router-static'
import echarts from 'echarts' // 确保已安装echarts: npm install echarts@4.x --save

export default {
  data() {
    return {
      // 统计数据
      orderTotal: 0,
      userTotal: 0,
      dishTotal: 0,
      // 图表实例
      orderStatusChart: null,
      salesChart: null,
      dishTypeChart: null
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      if (this.$storage.get('Token')) {
        this.$http({
          url: `${this.$storage.get('sessionTable')}/session`,
          method: "get"
        }).then(({ data }) => {
          if (data && data.code !== 0) {
            router.push({ name: 'login' })
          } else {
            // 会话验证通过后加载统计数据
            this.loadStatisticsData();
          }
        }).catch(() => {
          router.push({ name: 'login' })
        });
      } else {
        router.push({ name: 'login' })
      }
    },

    // 加载统计数据
    loadStatisticsData() {
      // 假设接口返回所有统计数据，实际项目中可根据需要拆分接口
      this.$http({
        url: '/statistics/chart', // 替换为实际接口地址
        method: 'get'
      }).then(({ data }) => {
        if (data && data.code === 0) {
          const data1 = data.data;
          // 设置基础统计数据
          this.orderTotal = data1.orderTotal || 0;
          this.userTotal = data1.userTotal || 0;
          this.dishTotal = data1.dishTotal || 0;

          // 初始化图表
          this.initOrderStatusChart(data1.orderStatusData);
          this.initSalesChart(data1.salesData);
          this.initDishTypeChart(data1.dishTypeData);
        }
      }).catch(error => {
        console.error('加载统计数据失败:', error);
      });
    },

    // 初始化订单状态柱状图
    initOrderStatusChart(data) {
      this.orderStatusChart = echarts.init(this.$refs.orderStatusChart);
      this.orderStatusChart.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: { type: 'shadow' }
        },
        grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
        xAxis: {
          type: 'category',
          data: data?.labels || ['未支付', '已支付', '已接单', '配送中', '已送达',]
        },
        yAxis: { type: 'value' ,interval: 1,},
        series: [{
          data: data?.values || [0, 0, 0, 0, 0],
          type: 'bar',
          itemStyle: {
            color: function(params) {
              const colorList = ['#FF9966', '#66B2FF', '#99CC00', '#a416d3', '#FF6666'];
              return colorList[params.dataIndex];
            }
          }
        }]
      });
    },

    // 初始化销售额折线图
    initSalesChart(data) {
      this.salesChart = echarts.init(this.$refs.salesChart);
      this.salesChart.setOption({
        tooltip: { trigger: 'axis' },
        grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: data?.dates || []
        },
        yAxis: { type: 'value', name: '销售额(元)' },
        series: [{
          name: '销售额',
          type: 'line',
          data: data?.amounts || [],
          smooth: true,
          lineStyle: { width: 3 },
          itemStyle: { color: '#3398DB' },
          areaStyle: { color: { type: 'linear', x: 0, y: 0, x2: 0, y2: 1, colorStops: [{ offset: 0, color: 'rgba(51, 152, 219, 0.3)' }, { offset: 1, color: 'rgba(51, 152, 219, 0)' }] } }
        }]
      });
    },

    // 初始化菜品类型环状图
    initDishTypeChart(data) {
      this.dishTypeChart = echarts.init(this.$refs.dishTypeChart);
      this.dishTypeChart.setOption({
        tooltip: { trigger: 'item' },
        legend: { orient: 'vertical', left: 10 },
        series: [{
          name: '菜品类型',
          type: 'pie',
          radius: ['40%', '70%'],
          avoidLabelOverlap: false,
          itemStyle: {
            borderRadius: 10,
            borderColor: '#fff',
            borderWidth: 2
          },
          label: { show: false, position: 'center' },
          emphasis: { label: { show: true, fontSize: 16, fontWeight: 'bold' } },
          labelLine: { show: false },
          data: data || []
        }]
      });
    },
    handleResize() {
      if (this.orderStatusChart) this.orderStatusChart.resize();
      if (this.salesChart) this.salesChart.resize();
      if (this.dishTypeChart) this.dishTypeChart.resize();
    }
  },
  // 窗口大小变化时重绘图表
  beforeDestroy() {
    window.removeEventListener('resize', this.handleResize);
  },
  created() {
    window.addEventListener('resize', this.handleResize);
  }
};
</script>

<style lang="scss" scoped>
.content {
  display: flex;
  align-items: center;
  flex-direction: column;
  width: 100%;
  min-height: 100vh;
  padding: 20px;
  box-sizing: border-box;
  text-align: center;

  .main-text {
    font-size: 38px;
    font-weight: bold;
    margin: 20px 0 40px;
  }

  .text {
    font-size: 24px;
    font-weight: bold;
    color: #333;
  }

  // 统计卡片样式
  .stats-cards {
    display: flex;
    justify-content: center;
    gap: 30px;
    width: 100%;
    max-width: 1200px;
    margin-bottom: 40px;

    .stat-card {
      flex: 1;
      min-width: 200px;
      background: #fff;
      border-radius: 10px;
      padding: 20px;
      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
      text-align: center;

      .stat-title {
        font-size: 18px;
        color: #666;
        margin-bottom: 10px;
      }

      .stat-value {
        font-size: 32px;
        font-weight: bold;
        color: #333;
      }
    }
  }

  // 图表容器样式
  .charts-container {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
    gap: 30px;
    width: 100%;
    max-width: 1200px;

    .chart-item {
      background: #fff;
      border-radius: 10px;
      padding: 20px;
      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);

      .chart-title {
        font-size: 18px;
        color: #333;
        margin-bottom: 15px;
        text-align: left;
      }

      .chart-box {
        width: 100%;
        height: 300px;
      }
    }
  }
}

// 响应式调整
@media (max-width: 768px) {
  .charts-container {
    grid-template-columns: 1fr;
  }

  .chart-item .chart-box {
    height: 250px;
  }

  .stats-cards {
    flex-direction: column;
  }
}
</style>
