package aws.ec2linux;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.jcraft.jsch.JSchException;

import common.ssh.SSHRemoteCommandExec;
import common.ssh.SSHRemoteFileTransfer;

public class EC2LinuxIAService {

	private static final String SSH_USER = "cloud-user";

	public boolean installPackage(String host, String packageName, String sshKey) throws JSchException {
		String res = this.runScript(host, "sudo yum -y install " + packageName,
				sshKey);
		System.out.println(res);
		return res.trim().endsWith("Complete!")
				|| res.trim().endsWith("Nothing to do");
	}

	public String runScript(String host, String script, String sshKey) throws JSchException {
		SSHRemoteCommandExec cmd = new SSHRemoteCommandExec(host, sshKey,
				EC2LinuxIAService.SSH_USER);
		String res = cmd.execute(script);
		cmd.close();
		return res;
	}

	/**
	 * 
	 * @param host
	 * @param fromAbsoluteLocalFileName
	 * @param toLinuxAbsolutePath
	 *            If this parameter is a path, the file is copied with its
	 *            current file name, otherwise the file is transfered with the
	 *            given file name. Path must exist on remote system.
	 * @param sshKey
	 * @return
	 * @throws JSchException 
	 */
	public boolean transferLocalFile(String host,
			String fromAbsoluteLocalFileName, String toLinuxAbsolutePath,
			String sshKey) throws JSchException {
		SSHRemoteFileTransfer t = new SSHRemoteFileTransfer(host, sshKey,
				EC2LinuxIAService.SSH_USER);
		t.sendFile(fromAbsoluteLocalFileName, toLinuxAbsolutePath);
		return true;
	}

	/**
	 * Transfers remote file to remote machine
	 * 
	 * @param host
	 * @param remoteFilePath
	 *            an absolute HTTP URI to the remote file, where a request with
	 *            "Accept: application/octet-stream" returns the desired file
	 * @param toLinuxAbsolutePath
	 * @param sshKey
	 * @return
	 * @throws JSchException 
	 */
	public boolean transferRemoteFile(String host, String remoteFilePath,
			String toLinuxAbsolutePath, String sshKey) throws JSchException {
		// download file from remoteFilePath
			System.out.println("host: " + host + " remoteFilePath: "
					+ remoteFilePath + " toLinuxAbsolutePath: "
					+ toLinuxAbsolutePath + " sshKey: " + sshKey);
			String remoteFilePathDownloaded = null;
			try {
				remoteFilePathDownloaded = this
						.downloadRemoteFile(remoteFilePath);
			} catch (InvocationTargetException e) {
				e.getTargetException().printStackTrace();
				e.printStackTrace();
			}

			System.out.println("Remote File new local Path: "
					+ remoteFilePathDownloaded);
			SSHRemoteFileTransfer t = new SSHRemoteFileTransfer(host, sshKey,
					EC2LinuxIAService.SSH_USER);
			t.sendFile(remoteFilePathDownloaded, toLinuxAbsolutePath);
		
		
		return true;
	}

	private String downloadRemoteFile(String remoteFilePath) throws InvocationTargetException {
		URL url;
		try {
			// setup request
			url = new URL(remoteFilePath);
			System.out.println("formed URL of remotefile: " + url.toString());
			HttpURLConnection httpClient = (HttpURLConnection) url
					.openConnection();
			httpClient.setRequestMethod("GET");
			httpClient.setRequestProperty("Accept", "application/octet-stream");

			// create temp file and folder
			File baseDir = new File(System.getProperty("java.io.tmpdir"));
			String baseName = System.currentTimeMillis() + "-";

			File tempDir = new File(baseDir, baseName);
			if (!(tempDir.mkdir())) {
				return null;
			}
			System.out.println("TempDir: " + tempDir.getAbsolutePath());
			File tempFile = new File(tempDir, url.getPath().substring(
					url.getPath().lastIndexOf("/")));

			System.out.println("TempFile: " + tempFile.getAbsolutePath());
			if (!tempFile.createNewFile()) {
				return null;
			}

			// dispatch request
			httpClient.connect();

			// read file to tempfile
			OutputStream fileOutputStream = new FileOutputStream(tempFile);
			InputStream is = httpClient.getInputStream();
			
			// copy from http://www.mkyong.com/java/how-to-convert-inputstream-to-file-in-java/
			int read = 0;
			byte[] bytes = new byte[1024];
	 
			while ((read = is.read(bytes)) != -1) {
				fileOutputStream.write(bytes, 0, read);
			}

			// close streams
			is.close();
			fileOutputStream.close();
			httpClient.disconnect();

			return tempFile.getAbsolutePath();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Transfers all files in a local folder to the given folder on the remote
	 * machine.
	 * 
	 * @param host
	 * @param fromAbsoluteLocaPath
	 *            Local directory containing the files to be transfered.
	 * @param toLinuxAbsolutePath
	 *            Path must exit on remote system.
	 * @param sshKey
	 * @return
	 */
	public boolean transferLocalFolder(String host,
			String fromAbsoluteLocaPath, String toLinuxAbsolutePath,
			String sshKey) {
		// Open and check local folder
		
System.out.println("-------------------debug5");
		File fromDir = new File(fromAbsoluteLocaPath);
		if (!fromDir.isDirectory()) {
			throw new RuntimeException(fromAbsoluteLocaPath
					+ " is not a directory.");
		}

		// Transfer each file in the folder
System.out.println("host: "+ host);
System.out.println("USER: " + EC2LinuxIAService.SSH_USER);
		
		
		try{
		
		SSHRemoteFileTransfer t = new SSHRemoteFileTransfer(host, sshKey,
				EC2LinuxIAService.SSH_USER);
System.out.println("-------------------debug6");
		for (File f : fromDir.listFiles()) {
			t.sendFile(f.toString(), toLinuxAbsolutePath);
		}
System.out.println("-------------------debug7");		
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}

}