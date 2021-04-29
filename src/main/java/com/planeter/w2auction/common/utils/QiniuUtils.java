package com.planeter.w2auction.common.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
/**
 * @description: 七牛云工具类
 * @author Planeter
 * @date 2021/4/29 20:53
 * @status dev
 */
@Slf4j
public class QiniuUtils {
    /**
     * 生成上传凭证，然后准备上传
     */
    private static final String AK = "1H0ZQ2L-dMN2_0E090sRwD1VsTd1TQV1X8cTwWcC";
    private static final String SK = "1H0ZQ2L-dMN2_0E090sRwD1VsTd1TQV1X8cTwWcC";
    private static final String bucket = "auction";
    public static final String origin="qrnf5hoyf.hn-bkt.clouddn.com";//七牛提供的外链域名, 一个月到期
    private static final Auth auth = Auth.create(AK, SK);


    public static String getUpToken() {
        return auth.uploadToken(bucket, null, 3600, new StringMap().put("insertOnly", 1));
    }

    /**
     * 业务服务器文件上传
     * @param localFilePath 文件路径
     * @param key 文件名
     * @return 文件url
     */
    public static String upload(String localFilePath,String key){

        //构造一个带指定Zone对象的配置类 zone2华南
        Configuration cfg = new Configuration(Zone.zone2());

        UploadManager uploadManager = new UploadManager(cfg);

        try {
            Response response = uploadManager.put(localFilePath, key, auth.uploadToken(bucket));
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            //返回url
            return origin+putRet.key;
        }catch(QiniuException e){
            Response r = e.response;
            log.warn(r.toString());
            try {
                return r.bodyString();
            } catch (QiniuException ex) {
                throw new RuntimeException(ex.toString());
            }
        }
    }

    /**
     * 文件流上传
     * @param file
     * @param key 文件名
     * @return
     */
    public static String InputStreamUpload(FileInputStream file, String key){

        //构造一个带指定Zone对象的配置类 zone2华南
        Configuration cfg = new Configuration(Zone.zone2());

        UploadManager uploadManager = new UploadManager(cfg);

        Auth auth = Auth.create(AK, SK);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(file,key,upToken,null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return origin+putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.warn(r.toString());
            try {
                return r.bodyString();
            } catch (QiniuException e) {
                throw new RuntimeException(e.toString());
            }
        }
    }

    /**
     * 以UUID重命名
     * @param fileName
     * @return
     */
    public static String renamePic(String fileName){
        String extName = fileName.substring(fileName.lastIndexOf("."));
        return UUID.randomUUID().toString().replace("-","")+extName;
    }

    public static String isValidImage(HttpServletRequest request, MultipartFile file){
        //最大文件大小
        long maxSize = 5242880;
        //定义允许上传的文件扩展名
        HashMap<String, String> extMap = new HashMap<String, String>();
        extMap.put("image", "gif,jpg,jpeg,png,bmp");

        if(!ServletFileUpload.isMultipartContent(request)){
            return "请选择文件";
        }

        if(file.getSize() > maxSize){
            return "上传文件大小超过5MB限制";
        }
        //检查扩展名
        String fileName=file.getOriginalFilename();
        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        if(!Arrays.<String>asList(extMap.get("image").split(",")).contains(fileExt)){
            return "上传文件扩展名是不允许的扩展名\n只允许" + extMap.get("image") + "格式";
        }
        return "valid";
    }

    /**
     * 检查文件扩展名
     * @param fileName
     * @param dirName
     * @return
     */
    public static String checkExt(String fileName,String dirName){
        //定义允许上传的文件扩展名
        HashMap<String, String> extMap = new HashMap<>();
        extMap.put("image", "gif,jpg,jpeg,png,bmp");
        extMap.put("flash", "swf,flv");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        //检查扩展名
        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        if(!Arrays.asList(extMap.get(dirName).split(",")).contains(fileExt)){
            return "上传文件扩展名是不允许的扩展名\n只允许" + extMap.get(dirName) + "格式";
        }
        return "valid";
    }
}
