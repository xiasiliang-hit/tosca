package common.ssh;

import java.io.File;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
//import org.vngx.jsch.ChannelType;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.JSch;


public class SSHRemoteFileTransfer {

	private final String knownHost = "~/.ssh/known_hosts";
	
	//private  Session session = null;
	//private boolean connected = false;
	private  String host = "";

	private String sshKey = "";
	private String sshUser = "";
	//private String privateKey = "~/work/tosca/siliang0.pem";
	
	public SSHRemoteFileTransfer(String host, String sshKey, String sshUser) {
		
//System.out.println("--debug: in SSHRemoteFileTransfer Con");
//System.out.println(host + "###" + sshKey + "###" + sshUser);
		
//System.out.println("--debug2: in SSHRemoteFileTransfer Con");
		
		//this.session = SSHUtils.createSSHSession(host, sshKey, sshUser);
		this.host = host;
		//this.connected = this.session.isConnected();
			
		this.sshKey = sshKey;
		this.sshUser = sshUser;
	}

	public void close() {
		//this.session.disconnect();
		//this.connected = false;
	}

	public void sendFile(String sourceFilename, String destFilename) throws JSchException {
				
		JSch jsch = new JSch();
		jsch.addIdentity(sshKey);
		
		//System.out.println(sshUser + "!!!!!!!!");
		//System.out.println(host + "!!!!!!!!");
		
		Session session = jsch.getSession(sshUser, host);
		jsch.setKnownHosts(knownHost);
		session.connect();
		
		if (!session.isConnected()) {
			throw new RuntimeException("Not connected to host " + this.host);
		}
		if (!(new File(sourceFilename).exists())) {
			throw new RuntimeException("File " + sourceFilename + " does not exist.");
		}

		System.out.println("### transferSingelFile from " + sourceFilename + " to " + destFilename + " on " + this.host);

		try {
			ChannelSftp c = (ChannelSftp) session.openChannel("sftp");
			c.connect();
			c.put(sourceFilename, destFilename);
			c.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		session.disconnect();
	}
}
