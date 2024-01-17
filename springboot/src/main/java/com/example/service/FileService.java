package com.example.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface FileService {

    /**
     * 上传
     *
     * @param multipartFile 多部分文件
     * @return {@link String}
     */
    String upload(MultipartFile multipartFile);

    /**
     * 获取文件
     *
     * @param fileName 文件名称
     * @param response 响应
     */
    void avatarPath(String fileName, HttpServletResponse response);

    /**
     * del文件
     *
     * @param fileName 文件名称
     */
    void delFile(String fileName);

}
