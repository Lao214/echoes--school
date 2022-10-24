package com.example.crmService.api;


import com.example.crmService.entity.CrmBanner;
import com.example.crmService.service.CrmBannerService;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2022-10-07
 */
@RestController
@RequestMapping("/crmService/crm-banner/front")
@CrossOrigin
public class AppCrmBannerController {

    @Autowired
    private CrmBannerService crmBannerService;

    //查询所有banner
    @GetMapping("getAllBanner")
    public Result getAllBanner(){
        List<CrmBanner> list = crmBannerService.selectAllBanner();
        return  Result.success().data("list",list);
    }

}

