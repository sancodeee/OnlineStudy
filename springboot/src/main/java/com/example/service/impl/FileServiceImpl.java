package com.example.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.example.controller.FileController;
import com.example.entity.Account;
import com.example.exception.CustomException;
import com.example.service.FileService;
import com.example.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    // 文件上传存储路径
    private static final String basePath = System.getProperty("user.dir") + File.separator + "files" + File.separator;

    @Value("${server.port:9090}")
    private String port;

    @Value("${ip}")
    private String ip;


    /**
     * 上传
     *
     * @param multipartFile 文件
     * @return {@link String} 文件下载接口
     */
    @Override
    public String upload(MultipartFile multipartFile) {
        // 文件路径构成：basePath + username文件夹 + 文件本体
        // 获取当前用户信息
        Account currentUser = TokenUtils.getCurrentUser();
        String flag;
        synchronized (FileController.class) {
            flag = String.valueOf(System.currentTimeMillis());
            ThreadUtil.sleep(1L);
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String fileName = "";
        try {
            // 文件保存路径
            if (!FileUtil.isDirectory(basePath)) {
                // 不存在则创建一个
                FileUtil.mkdir(basePath);
            }
            // 文件存储形式：时间戳-文件名
            // ***/files/username/1697438073596-avatar.png
            fileName = flag + "-" + originalFilename;
            // 保存文件
            FileUtil.writeBytes(multipartFile.getBytes(), basePath + fileName);
            log.info("{}--上传成功", originalFilename);
        } catch (Exception e) {
            log.info("{}--文件上传失败", originalFilename);
        }
        // 下载接口api
        String http = "http://" + ip + ":" + port + "/files/";
        // http://localhost:9090/files/1697438073596-avatar.png
        return http + fileName;
    }

    @Override
    public Map<String, Object> wangEditUpload(MultipartFile multipartFile) {
        return null;
    }

    /**
     * 获取文件
     *
     * @param fileName 文件名称
     * @param response 响应
     */
    @Override
    public void avatarPath(String fileName, HttpServletResponse response) {
        OutputStream os;
        try {
            Account currentUser = TokenUtils.getCurrentUser();
            if (StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                String downloadPath = basePath + fileName;
                byte[] bytes = FileUtil.readBytes(downloadPath);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("文件下载失败");
        }
    }

    @Override
    public boolean delFile(String fileName) {
        if (!FileUtil.del(basePath + fileName)) {
            log.error("删除文件" + fileName + "失败");
            throw new CustomException("500", "删除失败");
        }
        log.info("删除文件" + fileName + "成功");
        return true;
    }


}
