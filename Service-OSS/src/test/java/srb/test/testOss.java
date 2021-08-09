package srb.test;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.internal.OSSBucketOperation;
import com.aliyun.oss.model.Bucket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;

/**
 * 2021/8/4
 *
 * @author PingW
 */
//@SpringBootTest
//@RunWith(SpringRunner.class)
public class testOss {
    // Endpoint以杭州为例，其它Region请按实际情况填写。
    String endpoint = "oss-cn-shenzhen.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    String accessKeyId = "LTAI5tJihxRWcj2ru8gMonWe";
    String accessKeySecret = "qTV3PtKcDir5BsxsVI85hTDCQTKy5O";
    String bucketName = "wangping-test-test";
    @Test
    public void test01(){
        OSS build = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        Bucket bucket = build.createBucket("wangping-test-test");
        build.shutdown();


    }
    @Test
    public void test02(){
        OSS build = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String bucketFloder = "myfloder/";
        build.putObject(bucketName,bucketFloder,new ByteArrayInputStream(new byte[0]));
        build.shutdown();
    }
}
