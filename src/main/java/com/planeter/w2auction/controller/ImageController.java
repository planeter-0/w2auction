package com.planeter.w2auction.controller;

import com.planeter.w2auction.common.enums.ExceptionMsg;
import com.planeter.w2auction.common.result.ResponseData;
import com.planeter.w2auction.common.utils.QiniuUtils;
import com.planeter.w2auction.dao.ImageDao;
import com.planeter.w2auction.entity.Image;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Planeter
 * @description: image
 * @date 2021/4/27 10:41
 * @status ok
 */
@RestController
public class ImageController {
    @Resource
    ImageDao imageDao;

    /**
     * 上传图片
     *
     * @param uploadFile
     * @param request
     * @return
     */
    @PostMapping(value = "/image/upload")
    public ResponseData uploadFile(@RequestParam("image") MultipartFile uploadFile,
                                   @RequestParam Integer type,
                                   HttpServletRequest request) {
        Image i = null;
        // 转存文件
        try {
            //重命名上传至业务服务器
            String filepath = "E:\\uploadFiles\\";
            String filename = QiniuUtils.renamePic(Objects.requireNonNull(uploadFile.getOriginalFilename()));//UUID
            File file = new File(filepath, filename);
            FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), file);
            //上传至七牛云服务器
            String url = QiniuUtils.upload(filepath + filename, filename);
            if (url.contains("error")) {
                return new ResponseData(ExceptionMsg.UploadFailed);
            }
            // 删除业务服务器文件
            if (file.isFile() && file.exists()) {
                file.delete();
                //保存到数据库
                i = imageDao.save(new Image(url, type));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert i != null;
        return new ResponseData(ExceptionMsg.SUCCESS, i.getId());
    }

    /**
     * 根据图片id访问图片
     *
     * @param imageId 图片id
     * @return 图片url
     */
    @GetMapping(value = "/image/{imageId}")
    public ResponseData view(@PathVariable Long imageId) {
        Optional<Image> image = imageDao.findById(imageId);
        if (image.isPresent()) {
            return new ResponseData(ExceptionMsg.SUCCESS,image.get().getUrl());
        }else return new ResponseData(ExceptionMsg.FAILED);
    }
}
