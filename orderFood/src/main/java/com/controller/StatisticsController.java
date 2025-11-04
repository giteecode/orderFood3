package com.controller;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.CaipinEntity;
import com.entity.DingdanEntity;
import com.entity.NewsEntity;
import com.entity.YonghuEntity;
import com.entity.view.NewsView;
import com.entity.vo.Statistics1VO;
import com.entity.vo.StatisticsVO;
import com.service.CaipinService;
import com.service.DingdanService;
import com.service.NewsService;
import com.service.PeisongdanService;
import com.service.UserService;
import com.service.YonghuService;
import com.utils.MPUtil;
import com.utils.PageUtils;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 新闻资讯
 * 后端接口
 * @author
 * @email
 * @date 2020-09-23 18:00:25
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    private DingdanService dingdanService;
    @Autowired
    private YonghuService yonghuService;
    @Autowired
    private CaipinService caipinService;
    @Autowired
    private PeisongdanService peisongdanService;




    /**
     * 后端列表
     */
    @GetMapping("/chart")
    public R page(){
        EntityWrapper<DingdanEntity> ew = new EntityWrapper<DingdanEntity>();
        int orderTotal = dingdanService.selectCount(ew);
        EntityWrapper<YonghuEntity> ew1 = new EntityWrapper<YonghuEntity>();
        int userTotal = yonghuService.selectCount(ew1);
        EntityWrapper<CaipinEntity> ew2 = new EntityWrapper<CaipinEntity>();
        int dishTotal = caipinService.selectCount(ew2);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("orderTotal",orderTotal);
        map.put("userTotal",userTotal);
        map.put("dishTotal",dishTotal);
        List<String> labels = new LinkedList<>();
        labels.add("未支付");
        labels.add("已支付");
        labels.add("已接单");
        labels.add("配送中");
        labels.add("已送达");
        List<Integer> values = new ArrayList<>();
        //查询订单统计
        List<StatisticsVO> statisticsVOS = dingdanService.qryCount();
        List<StatisticsVO> statisticsVOS1 = peisongdanService.qryCount();
        statisticsVOS.addAll(statisticsVOS1);
        labels.stream().forEach(statisticsVO -> {
            List<StatisticsVO> collect = statisticsVOS.stream().filter(statisticsVO1 -> statisticsVO1.getLabel().equals(statisticsVO))
                    .collect(Collectors.toList());
            if(CollectionUtils.isEmpty(collect)){
                values.add(0);
            }else {
                values.add(collect.get(0).getCount());
            }
        });
        Map<String,Object> map1 = new HashMap<>();
        map1.put("labels",labels);
        map1.put("values",values);
        map.put("orderStatusData",map1);
        // 定义格式化器，指定输出格式为 yyyy-MM-dd HH:mm:ss
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 获取当前时间的年份
        LocalDateTime now = LocalDateTime.now();
        int currentYear = now.getYear();
        // 今年的第一天：yyyy-01-01 00:00:00
        LocalDateTime firstDayOfYear = LocalDateTime.of(currentYear, 1, 1, 0, 0, 0);
        // 今年的最后一天：yyyy-12-31 23:59:59
        LocalDateTime lastDayOfYear = LocalDateTime.of(currentYear, 12, 31, 23, 59, 59);
        // 格式化输出
        String firstDayStr = firstDayOfYear.format(formatter);
        // 获取当前日期和时间
        LocalDateTime today = LocalDateTime.now();
        // 设置为当天的最后一秒：23:59:59
        LocalDateTime lastSecondOfToday = today.withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(0); // 忽略纳秒
        String result = lastSecondOfToday.format(formatter);
        List<StatisticsVO> statisticsVOS2 = dingdanService.qrySaleCount(firstDayStr,result);
        int currentMonth = today.getMonthValue(); // 当前月份（1-12，如10）
        // 定义格式化器，输出格式为 yyyy-MM
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM");
        // 存储结果的列表
        List<String> months = new ArrayList<>();
        // 从1月循环到当前月份
        for (int month = 1; month <= currentMonth; month++) {
            // 构造当月的第一天（日可以随便填，因为只需要年月）
            LocalDate date = LocalDate.of(currentYear, month, 1);
            // 格式化并添加到列表
            months.add(date.format(formatter1));
        }
        List<Integer> amounts = new ArrayList<>();
        months.stream().forEach(month -> {
            List<StatisticsVO> collect = statisticsVOS2.stream().filter(statisticsVO1 -> statisticsVO1.getLabel().equals(month))
                    .collect(Collectors.toList());
            if(CollectionUtils.isEmpty(collect)){
                amounts.add(0);
            }else {
                amounts.add(collect.get(0).getCount());
            }
        });
        Map<String,Object> map2 = new HashMap<>();
        map2.put("dates",months);
        map2.put("amounts",amounts);
        map.put("salesData",map2);
        List<Statistics1VO> statisticsVOS3 = caipinService.qryCount();
        map.put("dishTypeData",statisticsVOS3);
        return R.ok().put("data", map);
    }
}
