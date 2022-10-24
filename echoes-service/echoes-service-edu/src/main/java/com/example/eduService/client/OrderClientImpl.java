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
public class OrderClientImpl implements  OrderClient {
    //出错之后会执行的实现类
    @Override
    public boolean isBuyCourse(String courseId,String memberId) {
        return false;
    }


}
