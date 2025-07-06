package com.spg.ldp;

import java.io.IOException;
import java.io.InputStream;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.config.keys.FilePasswordProvider;
import org.apache.sshd.common.keyprovider.FileKeyPairProvider;
import org.apache.sshd.sftp.client.SftpClient;
import org.apache.sshd.sftp.client.SftpClientFactory;
import org.springframework.core.io.Resource;

import lombok.Setter;

@Setter
public class SftpService {

	private String host;
	private int port;
	private String user;
	private Resource privateKey;
	private String privateKeyPassphrase;
	private boolean allowUnknownKeys;
	private String password;
	private String baseRemoteDirectory;

	private SshClient client;

	public SftpService() {
		client = SshClient.setUpDefaultClient();
		client.start();
	}

	public void uploadFile(InputStream inputStream, String fileName) throws IOException {
		try (ClientSession session = client.connect(user, host, port).verify().getSession()) {
			if (password != null) {
				session.addPasswordIdentity(password);
				System.out.println("Authenticating with password..." + password);
				session.auth().verify();
			} else if (privateKey != null) {
				FileKeyPairProvider keyPairProvider = new FileKeyPairProvider(privateKey.getFile().toPath());
				System.out.println("Authenticating with private key...");
				if (privateKeyPassphrase != null) {
					keyPairProvider.setPasswordFinder(FilePasswordProvider.of(privateKeyPassphrase));
				}
				keyPairProvider.loadKeys(session);
				if (allowUnknownKeys) {
					client.setServerKeyVerifier((clientSession, remoteAddress, serverKey) -> true);
				}
				session.auth().verify();
			} else {
				throw new IllegalArgumentException("Either password or private key must be provided");
			}

			SftpClientFactory factory = SftpClientFactory.instance();
			try (SftpClient sftpClient = factory.createSftpClient(session)) {

				String fullRemotePath = (baseRemoteDirectory + "/" + fileName).replace("\\", "/");
				System.out.println("Uploading to remote path: " + fullRemotePath);
				sftpClient.put(inputStream, fullRemotePath);
			} finally {
				inputStream.close();
			}
		}
		System.out.println(baseRemoteDirectory);
	}
}