package com.spg.ldp.gfbo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import lombok.Data;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "gfbo.sftp")
@Setter
@Data
public class SftpConfig {

	private String host;
	private int port;
	private String user;
	private String password;
	private String privateKey;
	private String privateKeyPassphrase;
	private boolean allowUnknownKeys;
	private String baseRemoteDirectory;

	@Bean
	public SftpService sftpService() {
		SftpService sftp = new SftpService();
		sftp.setHost(host);
		sftp.setPort(port);
		sftp.setUser(user);
		sftp.setPrivateKey(new ClassPathResource("id_rsa"));
		sftp.setAllowUnknownKeys(allowUnknownKeys);
		if (StringUtils.hasText(password)) {
			sftp.setPassword(password);
		}
		if (StringUtils.hasText(privateKeyPassphrase)) {
			sftp.setPrivateKeyPassphrase(privateKeyPassphrase);
		}
		if (StringUtils.hasText(baseRemoteDirectory)) {
			sftp.setBaseRemoteDirectory(baseRemoteDirectory);
		}
		return sftp;
	}
}
