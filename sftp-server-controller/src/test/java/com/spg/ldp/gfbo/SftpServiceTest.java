package com.spg.ldp.gfbo;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") 
public class SftpServiceTest {

    @Autowired
    private SftpService sftpService;

    @Test
    public void testUploadUsingTestYmlConfig() throws Exception {
        String content = "Hello from test with application-test.yml!";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes());
        sftpService.uploadFile(inputStream, "test-application-yml.txt");
    }
}
