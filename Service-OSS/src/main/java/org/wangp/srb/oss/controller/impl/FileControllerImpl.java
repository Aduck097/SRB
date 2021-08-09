package org.wangp.srb.oss.controller.impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.junit.validator.PublicClassValidator;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.wangp.common.result.R;
import org.wangp.srb.oss.controller.FileController;
import org.wangp.srb.oss.service.FileService;

import javax.annotation.Resource;
import java.io.InputStream;
import java.net.URLDecoder;

/**
 * 2021/8/4
 *
 * @author PingW
 */
@Api("阿里云文件服务")
@CrossOrigin
@RestController
@RequestMapping("/admin/oss/aliyun")
public class FileControllerImpl{

    @Resource
    private FileService fileService;
    @ApiOperation("上传Img文件图片")
    @PostMapping("/upload")
    public R uploadImg(
            @ApiParam(value = "Img文件",required = true)
            @RequestParam("file")MultipartFile file,
            @ApiParam(value = "模块",required = true)
            @RequestParam("moudle") String moudle
    ){
        try {
            // 将文件流,文件名和模块传给service模块
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            fileService.uplodeFile(originalFilename,moudle,inputStream);
            return R.ok().message("文件上传成功");
        }catch (Exception e){
            return R.error().message("文件上传失败辽QAQ~"+e.getMessage());
        }

    }
    @ApiOperation("删除阿里云上的图片")
    @DeleteMapping("/remove")
    public R deleteByUrl(
            @ApiParam(value = "img文件的路径，用来删除",required = true)
            @RequestParam("url") String url
    ){
        try {
            fileService.delete(url);
            return R.ok().message("删除成功");
        }catch (Exception e){
            return R.error().message("删除失败辽~"+e.getMessage());
        }

    }

}
