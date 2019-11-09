package pers.yang.upload;


import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * @author yf
 * @date 2019/10/30
 */
public class UploadTest {
    @Test
    public void test01() throws Exception {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region1());
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "d0UQVTNIakyPoDRT8ZBg7uCd7Sp3eUoolkgxcbP9";
        String secretKey = "oU1GZ7UsQ_dgne5kFalpKCT_DlwwhdCCYAVag2jz";
        String bucket = "health-fly";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "C:\\Users\\my\\Desktop\\图片资源\\3bd90d2c-4e82-42a1-a401-882c88b06a1a2.jpg";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }
    @Test
    public void test02() throws Exception {
//构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "d0UQVTNIakyPoDRT8ZBg7uCd7Sp3eUoolkgxcbP9";
        String secretKey = "oU1GZ7UsQ_dgne5kFalpKCT_DlwwhdCCYAVag2jz";
        String bucket = "health-fly";

//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        try {
            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(uploadBytes, key, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (UnsupportedEncodingException ex) {
            //ignore
        }
    }

    @Test
    public void test03() throws Exception {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
//...其他参数参考类注释

        String accessKey = "d0UQVTNIakyPoDRT8ZBg7uCd7Sp3eUoolkgxcbP9";
        String secretKey = "oU1GZ7UsQ_dgne5kFalpKCT_DlwwhdCCYAVag2jz";

        String bucket = "health-fly";
        String key = "Fu3Ic6TV6wIbJt793yaGeBmCkzTX";

        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }

    }
}
