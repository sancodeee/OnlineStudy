package com.example.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 文件服务
 *
 * @author wangsen
 * @date 2024/04/04
 */
public interface FileService {

    /**
     * 上传
     *
     * @param multipartFile 多部分文件
     * @return {@link String}
     */
    String upload(MultipartFile multipartFile);

    /**
     * wang-editor编辑器文件上传
     *
     * @param multipartFile 多部分文件
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> wangEditUpload(MultipartFile multipartFile);

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
    boolean delFile(String fileName);

}
