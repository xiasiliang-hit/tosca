package common.ssh;

import java.io.IOException;
import java.io.InputStream;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
//import com.jcraft.jsch.ChannelType;
import com.jcraft.jsch.Session;
//import com.jcraft.jsch.exception.JSchException;

import com.jcraft.jsch.JSch;

public class SSHRemoteCommandExec {
	
	private final String knownHost = "~/.ssh/known_hosts";
	
	//private final Session session;
	//private boolean connected = false;
	private String host = "";
	
	private String sshKey = "";
	private String sshUser = "";
	
	public SSHRemoteCommandExec(String host, String sshKey, String sshUser) {
		//this.session = SSHUtils.createSSHSession(host, sshKey, sshUser);
		
		this.host = host;
		this.sshKey = sshKey;
		this.sshUser = sshUser;
		
//		this.connected = this.session.isConnected();
	}
	
	public void close() {
		//this.session.disconnect();
		//this.connected = false;
	}
	
	public String execute(String command) throws JSchException {
		
		JSch jsch = new JSch();
		//com.jcraft.jsch.KeyPair.load(jsch, privateKey);
		jsch.addIdentity(sshKey);
		
		System.out.println(sshUser + "!!!!!!!!");
		System.out.println(host + "!!!!!!!!");
		
		Session session = jsch.getSession(sshUser, host);

		//java.util.Properties config = new java.util.Properties(); 
		//config.put("StrictHostKeyChecking", "no");
		//session.setConfig(config);
		jsch.setKnownHosts(knownHost);
		//session.setPassword("123456");
		session.connect();
		
		if (!session.isConnected()) {
			throw new RuntimeException("Not connected to host " + this.host);
		}
		System.out.println("### Executing: " + command + " on " + this.host);
		try {
			ChannelExec c = (ChannelExec) session.openChannel("exec");
			c.setPty(true);
			
			// TODO: Check if \n at the end?
			c.setCommand(command + "\n");
			
			// c.setInputStream(System.in);
			// c.setOutputStream(System.out);
			// c.setErrStream(System.err);
			// c.setExtOutputStream(System.out, true);
			InputStream is = c.getInputStream();
			
			c.connect();
			
			// Read the result
			String out = "";
			byte[] tmp = new byte[1024];
			while (true) {
				while (is.available() > 0) {
					int i = is.read(tmp, 0, 1024);
					if (i < 0) {
						break;
					}
					String s = new String(tmp, 0, i, "UTF-8");
					// System.out.print(s);
					out = out.concat(s);
				}
				if (c.isClosed()) {
					System.out.println("SSH exit-status: " + c.getExitStatus());
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			
			c.disconnect();
			
			System.out.println("Done.");
			return out.replaceAll("[\\x00-\\x09\\x11\\x12\\x14-\\x1F\\x7F]", "");
			
		} catch (JSchException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
