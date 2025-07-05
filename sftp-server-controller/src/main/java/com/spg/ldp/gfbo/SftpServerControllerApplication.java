package com.spg.ldp.gfbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ImportResource("classpath:sftp-config.xml")
public class SftpServerControllerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SftpServerControllerApplication.class, args);
    }
}