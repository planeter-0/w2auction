package com.planeter.w2auction.controller;

import com.planeter.w2auction.common.exception.UploadException;
import com.planeter.w2auction.common.result.ExceptionMsg;
import com.planeter.w2auction.common.result.ResponseData;
import com.planeter.w2auction.common.utils.QiniuUtils;
import com.planeter.w2auction.dao.ImageDao;
import com.planeter.w2auction.entity.Image;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@RestController
public class ImageController {
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
            //上传至业务服务器
            String filepath = request.getSession().getServletContext().getRealPath("/upload");
            String filename = QiniuUtils.renamePic(Objects.requireNonNull(uploadFile.getOriginalFilename()));//UUID
            File file = new File(filepath, filename);
            FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), file);
            //上传至七牛云服务器
            String url = QiniuUtils.upload(filepath, filename);
            if (url.contains("error")) {
                throw new UploadException("上传失败");
            }
            // 删除业务服务器文件
            if (file.isFile() && file.exists()) {
                file.delete();
            }
            //保存到数据库
            i = imageDao.save(new Image(url, type));
        } catch (UploadException | IOException e) {
            e.printStackTrace();
        }
        return new ResponseData(ExceptionMsg.SUCCESS, i.getId().toString());
    }

    /**
     * 根据图片id访问图片
     *
     * @param imageId 图片id
     * @return 图片url
     */
    @GetMapping(value = "/image/view/{imageId}")
    public String view(@PathVariable Integer imageId) {
        Image image = imageDao.getOne(imageId.longValue());
        return "redirect:/" + image.getUrl();
    }
}
