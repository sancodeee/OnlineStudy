package com.example;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
@MapperScan("com.example.mapper")
public class SpringbootApplication {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext context = SpringApplication.run(SpringbootApplication.class, args);
        ConfigurableEnvironment environment = context.getEnvironment();
        String port = environment.getProperty("server.port", "8080");
        String contextPath = environment.getProperty("server.servlet.context-path", "");
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        String localPath = "http://localhost:" + port + contextPath;
        String networkPath = "http://" + hostAddress + ":" + port + contextPath;
        log.info("-local:{}", localPath);
        log.info("-Network:{}", networkPath);
    }
}
