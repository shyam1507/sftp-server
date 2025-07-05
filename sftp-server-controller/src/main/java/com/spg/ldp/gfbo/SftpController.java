package com.spg.ldp.gfbo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/sftp")
public class SftpController {

    @Autowired
    private SftpService sftpService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFileToSftp(@RequestParam("file") MultipartFile file) throws IOException {
        sftpService.uploadFile(file.getInputStream(), file.getOriginalFilename());
        return ResponseEntity.ok("File uploaded successfully");
    }
}
