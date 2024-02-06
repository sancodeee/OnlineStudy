package com.example.controller;

import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.service.FileService;
import com.github.pagehelper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 文件接口
 *
 * @author wangsen
 * @date 2024/01/18
 */
@Slf4j
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
        log.info("下载路径：" + downloadPath);
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
    public Result<?> delFile(@PathVariable String fileName) {
        if (StringUtil.isEmpty(fileName)) {
            Result.error(ResultCodeEnum.PARAM_LOST_ERROR.code, "文件名为空");
        }
        fileService.delFile(fileName);
        return Result.success();
    }

    /**
     * wang-editor编辑器文件上传接口
     *
     * @param file 文件
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @PostMapping("/wang/upload")
    public Map<String, Object> wangEditorUpload(MultipartFile file) {
        return fileService.wangEditUpload(file);
    }


}
