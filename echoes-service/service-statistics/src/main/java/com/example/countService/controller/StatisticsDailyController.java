package com.example.countService.controller;


import com.example.countService.service.StatisticsDailyService;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2022-10-17
 */

@CrossOrigin
@RestController
@RequestMapping("/countService/statistics-daily")
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    //统计某一天的注册人数
    @PostMapping("registerCount/{day}")
    public Result registerCount(@PathVariable String day){
        statisticsDailyService.registerCount(day);
        return Result.success();
    }


    //图表返回，返回两部分数据，日期json数组，数量json数组
    @GetMapping("showData/{type}/{begin}/{end}")
    public Result showData(@PathVariable String type,@PathVariable String begin,@PathVariable String end){
        Map<String,Object> map = statisticsDailyService.getShowData(type,begin,end);
        return  Result.success().data(map);
    }
}

