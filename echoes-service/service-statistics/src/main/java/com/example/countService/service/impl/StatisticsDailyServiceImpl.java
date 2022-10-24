package com.example.countService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.countService.client.UcenterClient;
import com.example.countService.entity.StatisticsDaily;
import com.example.countService.dao.StatisticsDailyMapper;
import com.example.countService.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.utils.Result;
import com.example.vo.UcenterMemberOrder;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author 劳威锟
 * @since 2022-10-17
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void registerCount(String day) {
        //添加记录之前 先删除相同的数据
        QueryWrapper<StatisticsDaily> wrapper =new QueryWrapper<StatisticsDaily>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);
            //远程调用，得到某一天的注册人数
        Result result = ucenterClient.countRegister(day);
        Integer countRegister= (Integer) result.getData().get("countRegister");
        //把获取数据添加数据库，统计分析表
        StatisticsDaily statisticsDaily =new StatisticsDaily();
        statisticsDaily.setRegisterNum(countRegister);
        statisticsDaily.setDateCalculated(day);
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setLoginNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100,200));
        baseMapper.insert(statisticsDaily);
    }

    //图表返回，返回两部分数据，日期json数组，数量json数组
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        //根据条件查询对应的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> statisticsDailyList =baseMapper.selectList(wrapper);
        List<String> date_calculatedList =new ArrayList<>();
        List<Integer> numDataList =new ArrayList<>();

        for (int i = 0; i <statisticsDailyList.size() ; i++) {
            StatisticsDaily daily =statisticsDailyList.get(i);
            //封装日期集合
            date_calculatedList.add(daily.getDateCalculated());
            //封装对应数量
            switch (type){
                case  "login_num":  numDataList.add(daily.getLoginNum());
                    break;
                case  "register_num": numDataList.add(daily.getRegisterNum());
                    break;
                case  "video_view_num": numDataList.add(daily.getVideoViewNum());
                    break;
                case  "course_num": numDataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        Map<String,Object> map =new HashMap<>();
        map.put("date_calculatedList",date_calculatedList);
        map.put("numDataList",numDataList);
        return map;
    }
}
