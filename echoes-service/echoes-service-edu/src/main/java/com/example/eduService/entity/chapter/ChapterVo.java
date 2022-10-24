package com.example.eduService.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/9/30 6:27 PM
 */
@Data
public class ChapterVo {


    private  String id;

    private  String title;

    //表示小节
    private List<VideoVo> children =new ArrayList<>();
}
