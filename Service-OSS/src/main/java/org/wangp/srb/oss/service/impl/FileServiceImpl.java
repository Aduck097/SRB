package org.wangp.srb.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import io.swagger.annotations.ApiOperation;
import org.joda.time.DateTime;
import org.junit.runners.model.TestTimedOutException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.wangp.srb.oss.service.FileService;
import org.wangp.srb.oss.utils.OssProperties;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 2021/8/4
 *
 * @author PingW
 */
@Service
@Transactional
public class FileServiceImpl implements FileService {



    @ApiOperation("将文件存入到阿里云")
    @Override
    public void uplodeFile(String fileName, String moudle,InputStream inputStream) {
        OSS myBucket = new OSSClientBuilder().build(
                OssProperties.ENDPOINT,
                OssProperties.KEY_ID,
                OssProperties.KEY_SECRET
        );
        // 需要判断bucket是否存在
        if(!myBucket.doesBucketExist(OssProperties.BUCKET_NAME)){
            // 若没有则创建bucket
            myBucket.createBucket(OssProperties.BUCKET_NAME);
            // 开启bucket公共读的权限
            myBucket.setBucketAcl(OssProperties.BUCKET_NAME, CannedAccessControlList.PublicRead);
        }
        // 传输文件需要的url 文件需要存放在的日期文件目录
        String bucketFloderName = new DateTime().toString("yyyy/MM/dd");

        // 创建文件名 生成格式为uuid
        String fileNameUid = UUID.randomUUID().toString();
        // 拼装文件地址
        String url = moudle+"/"+ bucketFloderName+"/"+fileNameUid+fileName;

        // 需要三个参数分别时bucket名字 上传的url地址 和文件的流
        myBucket.putObject(OssProperties.BUCKET_NAME,url,inputStream);
        myBucket.shutdown();

    }

    @Override
    public void delete(String url) {
        OSS myBucket = new OSSClientBuilder().build(
                OssProperties.ENDPOINT,
                OssProperties.KEY_ID,
                OssProperties.KEY_SECRET
        );
        myBucket.setBucketAcl(OssProperties.BUCKET_NAME, CannedAccessControlList.PublicReadWrite);
        //文件名（服务器上的文件路径）
        String host = "https://" + OssProperties.BUCKET_NAME + "." + OssProperties.ENDPOINT;
        String objectName = url.substring(host.length()+2);
        System.out.println("host = " + host);
        System.out.println("host.length() = " + host.length());
        System.out.println("objectName = " + objectName);
        myBucket.deleteObject(OssProperties.BUCKET_NAME,objectName);
        System.out.println("OssProperties.BUCKET_NAME = " + OssProperties.BUCKET_NAME);
        myBucket.shutdown();

    }
}
