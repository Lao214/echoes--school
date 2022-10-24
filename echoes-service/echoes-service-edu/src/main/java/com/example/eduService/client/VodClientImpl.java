package com.example.eduService.client;

import com.example.utils.Result;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/10/5 4:17 PM
 */
@Component
public class VodClientImpl implements  VodClient {
    //出错之后会执行的实现类
    @Override
    public Result removeAliyunvod(String id) {
        return Result.error().msg("删除视频出错了");
    }

    @Override
    public Result deleteBatch(List<String> videoList) {
        return Result.error().msg("删除多个视频出错了");
    }
}
