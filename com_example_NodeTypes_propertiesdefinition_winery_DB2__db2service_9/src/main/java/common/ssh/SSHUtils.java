package common.ssh;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.vngx.jsch.JSch;
import org.vngx.jsch.Session;
import org.vngx.jsch.UserInfo;
import org.vngx.jsch.config.SessionConfig;
import org.vngx.jsch.exception.JSchException;
import org.vngx.jsch.userauth.IdentityManager;
import org.vngx.jsch.userauth.UserAuth;
import org.vngx.jsch.util.HostKey;
import org.vngx.jsch.util.KeyType;

public class SSHUtils {
	
	public static Session createSSHSession(String host, String sshKey, String sshUser) {
		
		Session session = null;
		
System.out.println("-------------------debug8: SSHUtils:createSSHSession");	

		try {
System.out.println("-------------------debug9: SSHUtils:createSSHSession");	
			JSch jsch = JSch.getInstance();
			
			// Add to Host Key Repository & Idendity Manager
			// ...by putting it into a temp file
			File keyFile = File.createTempFile("ssh", ".key");
			// Delete temp file when program exits.
			keyFile.deleteOnExit();
			
			// Write to temp file
			BufferedWriter out = new BufferedWriter(new FileWriter(keyFile));
			out.write(sshKey);
			out.close();
			
			IdentityManager.getManager().removeAllIdentities();
			IdentityManager.getManager().addIdentity(keyFile.getAbsolutePath());
			HostKey hostkey = new HostKey(host, KeyType.SSH_RSA, sshKey.getBytes());
			UserInfo ui = new UserInfo() {
			
				// TODO: Control flow should never be in on of these methods,
				// maybe we should throw RuntimeExceptions
				
				@Override
				public void showMessage(String message) {
					System.out.println("UserInfo.showMessage: " + message + "\n");
				}
				
				@Override
				public boolean promptYesNo(String message) {
					System.out.println("UserInfo.promptYesNo: " + message + "\n");
					return true;
				}
				
				@Override
				public boolean promptPassword(String message) {
					System.out.println("UserInfo.promptPassword: " + message + "\n");
					return false;
				}
				
				@Override
				public boolean promptPassphrase(String message) {
					System.out.println("UserInfo.promptPassphrase: " + message + "\n");
					return false;
				}
				
				@Override
				public String getPassword() {
					System.out.println("UserInfo.getPassword\n");
					return null;
				}
				
				@Override
				public String getPassphrase() {
					System.out.println("UserInfo.getPassphrase\n");
					return null;
				}
			};
			jsch.getHostKeyRepository().add(hostkey, ui);
			
			// Create Session
			session = jsch.createSession(sshUser, host, 22);
			session.setUserInfo(ui);
			session.getConfig().setProperty(SessionConfig.STRICT_HOST_KEY_CHECKING, "no");
			session.getConfig().setProperty(SessionConfig.PREFFERED_AUTHS, UserAuth.PUBLICKEY);
			
			// Let's go
			session.connect();
				
		} catch (JSchException e) {
			
			System.out.println("------------------e1");
			e.printStackTrace();
			throw new RuntimeException(e);
			
		} catch (IOException e) {
			
			System.out.println("--------------------e2");
			
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		System.out.println("-2-SSHUtils:createSSHSession");
		return session;
		
	}
}
