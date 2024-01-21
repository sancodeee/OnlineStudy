package com.example.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.example.controller.FileController;
import com.example.exception.CustomException;
import com.example.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    // 文件上传存储路径
    private static final String filePath = System.getProperty("user.dir") + "/files/";

    @Value("${server.port}")
    private String port;

    @Value("${ip}")
    private String ip;

    /**
     * 上传
     *
     * @param file 文件
     * @return {@link String}
     */
    @Override
    public String upload(MultipartFile file) {
        String flag;
        synchronized (FileController.class) {
            flag = System.currentTimeMillis() + "";
            ThreadUtil.sleep(1L);
        }
        String fileName = file.getOriginalFilename();
        try {
            if (!FileUtil.isDirectory(filePath)) {
                FileUtil.mkdir(filePath);
            }
            // 文件存储形式：时间戳-文件名
            // ***/manager/files/1697438073596-avatar.png
            FileUtil.writeBytes(file.getBytes(), filePath + flag + "-" + fileName);
            System.out.println(fileName + "--上传成功");
        } catch (Exception e) {
            System.err.println(fileName + "--文件上传失败");
        }
        // 下载路径
        String http = "http://" + ip + ":" + port + "/files/";
        // http://localhost:9090/files/1697438073596-avatar.png
        return http + flag + "-" + fileName;
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
            if (StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(filePath + fileName);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            log.error("文件下载失败");
        }
    }

    @Override
    public boolean delFile(String fileName) {
        if (!FileUtil.del(filePath + fileName)) {
            log.error("删除文件" + fileName + "失败");
            throw new CustomException("500", "删除失败");
        }
        log.info("删除文件" + fileName + "成功");
        return true;
    }


}
