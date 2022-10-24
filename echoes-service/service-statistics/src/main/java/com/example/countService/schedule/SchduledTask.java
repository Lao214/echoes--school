package com.example.countService.schedule;

import com.example.countService.service.StatisticsDailyService;
import com.example.countService.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/10/17 8:19 PM
 */
@Component
public class SchduledTask {


    @Autowired
    private StatisticsDailyService statisticsDailyService;

    /**
     * @description: 每天凌晨一点执行 把前一天的数据查寻，进行添加
     * @param:
     * @return:
     * @author 劳威锟
     * @date: 2022/10/17 8:21 PM
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void TaskOne(){
        statisticsDailyService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }
}
