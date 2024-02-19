package com.example.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.text.CharSequenceUtil;
import com.example.exception.CustomException;
import com.example.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Value("${server.port:9090}")
    private String port;

    @Value("${ip}")
    private String ip;

    /**
     * 文件上传存储路径
     */
    private static String BASE_PATH;

    /**
     * 下载api
     */
    private static String DOWNLOAD_API;

    /**
     * 初始化
     */
    @PostConstruct
    public synchronized void init() {
        BASE_PATH = System.getProperty("user.dir") + File.separator + "files" + File.separator;
        DOWNLOAD_API = "http://" + ip + ":" + port + "/files/";
    }

    /**
     * 上传
     *
     * @param multipartFile 文件
     * @return {@link String} 文件下载接口
     */
    @Override
    public String upload(MultipartFile multipartFile) {
        String flag = String.valueOf(Instant.now().toEpochMilli());
        String originalFilename = multipartFile.getOriginalFilename();
        try {
            // 文件保存路径
            Path filePath = Paths.get(BASE_PATH, flag + "-" + originalFilename);
            Files.createDirectories(filePath.getParent());
            // 保存文件
            try (OutputStream outputStream = Files.newOutputStream(filePath)) {
                outputStream.write(multipartFile.getBytes());
            }
            log.info("{}--上传成功", originalFilename);
            // 返回文件下载链接
            return DOWNLOAD_API + filePath.getFileName().toString();
        } catch (Exception e) {
            log.error("{}--文件上传失败", originalFilename, e);
            return "文件上传失败";
        }
    }

    /**
     * 富文本编辑器文件上传
     *
     * @param multipartFile 多部分文件
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Map<String, Object> wangEditUpload(MultipartFile multipartFile) {
        String flag = String.valueOf(Instant.now().toEpochMilli());
        String originalFilename = multipartFile.getOriginalFilename();
        String fileName = "";
        try {
            // 文件保存路径
            if (!FileUtil.isDirectory(BASE_PATH)) {
                // 不存在则创建一个
                FileUtil.mkdir(BASE_PATH);
            }
            // 文件存储形式：时间戳-文件名
            fileName = flag + "-" + originalFilename;
            FileUtil.writeBytes(multipartFile.getBytes(), BASE_PATH + fileName);
            log.info("{}--上传成功", originalFilename);
            Thread.sleep(2L);
        } catch (Exception e) {
            log.info("{}--文件上传失败", originalFilename);
            // 中断线程
            Thread.currentThread().interrupt();
        }
        Map<String, Object> resMap = new HashMap<>();
        // wangEditor上传图片成功后， 需要返回的参数
        resMap.put("errno", 0);
        resMap.put("data", CollUtil.newArrayList(Dict.create().set("url", DOWNLOAD_API + fileName)));
        return resMap;
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
            if (CharSequenceUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                String downloadPath = BASE_PATH + fileName;
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

    /**
     * 删除文件
     *
     * @param fileName 文件名称
     * @return boolean
     */
    @Override
    public boolean delFile(String fileName) {
        if (!FileUtil.del(BASE_PATH + fileName)) {
            log.error("删除文件" + fileName + "失败");
            throw new CustomException("500", "删除失败");
        }
        log.info("删除文件" + fileName + "成功");
        return true;
    }


}
