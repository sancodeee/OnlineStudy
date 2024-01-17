package com.example.controller;

import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.service.FileService;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件接口
 */
@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * 文件上传
     *
     * @param file 文件
     * @return {@link Result}
     */
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) {
        String downloadPath = fileService.upload(file);
        return Result.success(downloadPath);
    }

    /**
     * 获取文件
     *
     * @param response 响应
     * @param fileName 文件名称
     */
    @GetMapping("/{fileName}")   //  1697438073596-avatar.png
    public void avatarPath(@PathVariable String fileName, HttpServletResponse response) {
        fileService.avatarPath(fileName, response);
    }

    /**
     * 删除文件
     *
     * @param fileName 文件名称
     */
    @DeleteMapping("/{fileName}")
    public void delFile(@PathVariable String fileName) {
        if (StringUtil.isEmpty(fileName)) {
            Result.error(ResultCodeEnum.PARAM_LOST_ERROR.code, "文件名为空");
        }
        fileService.delFile(fileName);
    }


}
