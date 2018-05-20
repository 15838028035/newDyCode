package com.lj.cloud.weixin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by dongxc on 2015/7/20. 文件上传
 */
public class FileUploadUtils {

    private static Logger log             = LoggerFactory.getLogger(FileUploadUtils.class);
    public static String  accessKeyId     = "6MqJQ1afWMwdt82V";                            // "6Horvio4CguKwkTQ";
                                                                                            // "6MqJQ1afWMwdt82V"; 信融
    public static String  accessKeySecret = "aalHStrgkbXhMvIXC96RGPRbJiavWN";              // "Y1ACw5BI1WRjiWd8G0OupsQJmpf61z";
                                                                                            // aalHStrgkbXhMvIXC96RGPRbJiavWN;信融
    public static String  endpoint        = "http://oss-cn-shanghai.aliyuncs.com";         // "http://oss-cn-hangzhou.aliyuncs.com";

    // public static String bucketName = "weidaiosstest";

    public static void uploadPicture(MultipartFile mf, String bucketName, String key) throws Exception {
        try {
            // 初始化一个OSSClient
            OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            InputStream content = mf.getInputStream();
            // 创建上传Object的Metadata
            ObjectMetadata meta = new ObjectMetadata();
            // 必须设置ContentLength
            // meta.setExpirationTime(expirationTime);
            meta.setContentLength(mf.getBytes().length);
            // 上传Object.
            PutObjectResult result = client.putObject(bucketName, key, content, meta);

            if (result != null) {
                log.info("文件上传" + result.getETag());
            }
            // 打印ETag
            // System.out.println(result.getETag());
        } catch (Exception e) {
            log.error("文件上传失败[FileUploadUtils]", e);
            throw new Exception(e.getMessage(), e);
        }
    }

    public static void main2(String[] args) {
        // TODO Auto-generated method stub
        String accessKeyId = "6Horvio4CguKwkTQ";
        String accessKeySecret = "Y1ACw5BI1WRjiWd8G0OupsQJmpf61z";
        // 以杭州为例
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";

        String bucketName = "weidaiosstest";

        // 初始化一个OSSClient
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        String filePath = "D:/bbb.png";
        // 获取指定文件的输入流
        File file = new File(filePath);
        try {
            InputStream content = new FileInputStream(file);

            // 创建上传Object的Metadata
            ObjectMetadata meta = new ObjectMetadata();

            // 必须设置ContentLength
            meta.setContentLength(file.length());

            // 上传Object.
            PutObjectResult result = client.putObject(bucketName, "SSOPic", content, meta);
            // 打印ETag
            System.out.println(result.getETag());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // 获取指定bucket下的所有Object信息
        ObjectListing listing = client.listObjects(bucketName);

        // 遍历所有Object
        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {

            String myKey = objectSummary.getKey();
            System.out.println(objectSummary.getKey());

            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, myKey);
            System.out.println("uri:" + getObjectRequest.getAbsoluteUri());
            ObjectMetadata meta = client.getObject(getObjectRequest, new File("D:/c.jpg"));

        }

    }
}
