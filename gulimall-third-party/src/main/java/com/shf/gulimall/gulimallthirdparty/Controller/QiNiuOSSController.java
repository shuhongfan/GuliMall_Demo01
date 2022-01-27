package com.shf.gulimall.gulimallthirdparty.Controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.shf.common.utils.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class QiNiuOSSController {
    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    @RequestMapping("/oss/upload")
    public R upload(String localFilePath){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传

//如果是Windows情况下，格式是 D:\\qiniu\\test.png
//        String localFilePath = "D:\\DEMO-shf\\尚硅谷Java学科全套教程（总207.77GB）\\4.尚硅谷全套JAVA教程—实战项目（71.89GB）\\大型电商--谷粒商城\\1.分布式基础（全栈开发篇）\\资料源码\\docs\\pics\\0d40c24b264aa511.jpg";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String simpleUUID = IdUtil.simpleUUID();
        String ext = localFilePath.substring(localFilePath.lastIndexOf(".")+1);
        String key = simpleUUID+"."+ext;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            return R.ok().put("data", putRet);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
                return R.error(r.toString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return R.error();
    }
}
