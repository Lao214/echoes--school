package com.example.eduVod.Utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/10/4 4:34 PM
 */
public class InitObject {
    /**
     * 删除视频
     * @param client 发送请求客户端
     * @return DeleteVideoResponse 删除视频响应数据
     * @throws Exception
     */
    public static DefaultAcsClient initVodClient(String accessKeyId,String accessKeySecret) throws Exception{

        String regionId ="cn-shenzhen";
        DefaultProfile profile =DefaultProfile.getProfile(regionId,accessKeyId,accessKeySecret);
        DefaultAcsClient client =new DefaultAcsClient(profile);
        return client;
    }

}
