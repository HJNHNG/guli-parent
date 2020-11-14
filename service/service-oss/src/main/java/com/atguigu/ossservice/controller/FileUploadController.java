package com.atguigu.ossservice.controller;/*
 *
 *   @date 2020/11/13 8:28
 *
 */

import com.atguigu.commonutils.R;
import com.atguigu.ossservice.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(description = "阿里云文件管理")
@CrossOrigin
@RestController
@RequestMapping("/admin/oss/file")
public class FileUploadController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "文件上传")
    @PostMapping("upload")
    public R upload(
            @ApiParam(name = "file",value = "文件",required = true)
            @RequestParam MultipartFile file
    ){
        String uploadUrl = fileService.upload(file);
        return R.ok().message("上传成功").data("url",uploadUrl);
    }

    @GetMapping("upload")
    public R upload(){
        return R.ok().message("上传成功");
    }

}
