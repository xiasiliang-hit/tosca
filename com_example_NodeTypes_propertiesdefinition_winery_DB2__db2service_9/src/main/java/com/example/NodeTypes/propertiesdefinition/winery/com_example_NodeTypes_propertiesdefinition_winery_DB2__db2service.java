package com.example.NodeTypes.propertiesdefinition.winery;

import java.util.HashMap;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.jcraft.jsch.JSchException;

import aws.ec2linux.EC2LinuxIAService;

@WebService
public class com_example_NodeTypes_propertiesdefinition_winery_DB2__db2service extends AbstractIAService {

	private String installSFile = "install.sh";
	private String startSFile = "start.sh";
	private String stopSFile="stop.sh";
	private String restartSFile = "restart.sh";
	private String createdbSFile = "createdb.sh";

	//static private String host = "192.168.209.227";
	//static private String abPath = "/home/danny/work/eclipse_space1/com_example_NodeTypes_propertiesdefinition_winery_DB2__db2service/py/script/";
	static private String abPath = "./DA/script/";
	static private String toPath = "./";
	
	//String privateKey = ".ssh/id_rsa";
	static private String sshKey = "./DA/siliang0.pem";
	
	private String defaultPort = "50000"; 	//should be the same as define in start.sh

	
	@WebMethod
	@SOAPBinding
	@Oneway
	public void install(
		@WebParam(name="targetIP", targetNamespace="http://winery.propertiesdefinition.NodeTypes.example.com/") String targetIP
	) {
		// This HashMap holds the return parameters of this operation.
				final HashMap<String,String> returnParameters = new HashMap<String, String>();

				// TODO: Implement your operation here.				
				String success = "true";
				EC2LinuxIAService ser = new EC2LinuxIAService();
				ser.transferLocalFolder(targetIP, abPath, toPath, sshKey);

				try
				{
					ser.runScript(targetIP, "bash " + installSFile, sshKey);
				}
				catch (JSchException e)
				{
					// TODO Auto-generated catch block
					System.out.println("ex: install");
					success = "false";
					e.printStackTrace();
				}
				
				// Output Parameter 'sucsess' (optional)
				// TODO: Set sucess parameter here.
				// Do NOT delete the next line of code. Set "" as value if you want to return nothing or an empty result!
				returnParameters.put("sucess", success);
				sendResponse(returnParameters);
	}

	@WebMethod
	@SOAPBinding
	@Oneway
	public void start(
		@WebParam(name="targetIP", targetNamespace="http://winery.propertiesdefinition.NodeTypes.example.com/") String targetIP
	) {
		// This HashMap holds the return parameters of this operation.
		final HashMap<String,String> returnParameters = new HashMap<String, String>();

		// TODO: Implement your operation here.
		
		String success = "true";
		EC2LinuxIAService ser = new EC2LinuxIAService();

		try
		{
			ser.runScript(targetIP, "bash " + startSFile, sshKey);
		} catch (JSchException e)
		{
			// TODO Auto-generated catch block
			System.out.println("ex: install");
			success = "false";
			e.printStackTrace();
		}

		// Output Parameter 'sucess' (optional)
		// TODO: Set sucess parameter here.
		// Do NOT delete the next line of code. Set "" as value if you want to return nothing or an empty result!
		returnParameters.put("sucess", success);

		// Output Parameter 'ip' (optional)
		// TODO: Set ip parameter here.
		// Do NOT delete the next line of code. Set "" as value if you want to return nothing or an empty result!
		returnParameters.put("ip", targetIP);

		// Output Parameter 'port' (optional)
		// TODO: Set port parameter here.
		// Do NOT delete the next line of code. Set "" as value if you want to return nothing or an empty result!
		returnParameters.put("port", "50000");

		sendResponse(returnParameters);
	}

	@WebMethod
	@SOAPBinding
	@Oneway
	public void stop(
		@WebParam(name="targetIP", targetNamespace="http://winery.propertiesdefinition.NodeTypes.example.com/") String targetIP
	) {
		// This HashMap holds the return parameters of this operation.
		final HashMap<String,String> returnParameters = new HashMap<String, String>();

		// TODO: Implement your operation here.
		String success = "true";
		EC2LinuxIAService ser = new EC2LinuxIAService();

		try
		{
			ser.runScript(targetIP, "bash " + stopSFile, sshKey);
		} catch (JSchException e)
		{
			// TODO Auto-generated catch block
			System.out.println("ex: stop");
			success = "false";
			e.printStackTrace();
		}

		// Output Parameter 'sucess' (optional)
		// TODO: Set sucess parameter here.
		// Do NOT delete the next line of code. Set "" as value if you want to return nothing or an empty result!
		returnParameters.put("sucess", success);

		sendResponse(returnParameters);
	}

	@WebMethod
	@SOAPBinding
	@Oneway
	public void createdb(
		@WebParam(name="targetIP", targetNamespace="http://winery.propertiesdefinition.NodeTypes.example.com/") String targetIP,
		@WebParam(name="dbName", targetNamespace="http://winery.propertiesdefinition.NodeTypes.example.com/") String dbName
	) {
		// This HashMap holds the return parameters of this operation.
		final HashMap<String,String> returnParameters = new HashMap<String, String>();
		
		String success = "true";
		EC2LinuxIAService ser = new EC2LinuxIAService();

		try
		{
			ser.runScript(targetIP, "bash " + createdbSFile, sshKey);
		} catch (JSchException e)
		{
			// TODO Auto-generated catch block
			System.out.println("ex: createdb");
			success = "false";
			e.printStackTrace();
		}
		// TODO: Implement your operation here.


		// Output Parameter 'success' (optional)
		// TODO: Set success parameter here.
		// Do NOT delete the next line of code. Set "" as value if you want to return nothing or an empty result!
		returnParameters.put("success", success);

		sendResponse(returnParameters);
	}

	@WebMethod
	@SOAPBinding
	@Oneway
	public void restart(
		@WebParam(name="targetIP", targetNamespace="http://winery.propertiesdefinition.NodeTypes.example.com/") String targetIP
	) {
		// This HashMap holds the return parameters of this operation.
		final HashMap<String,String> returnParameters = new HashMap<String, String>();

		// TODO: Implement your operation here.
		String success = "true";
		EC2LinuxIAService ser = new EC2LinuxIAService();

		try
		{
			ser.runScript(targetIP, "bash " + restartSFile, sshKey);
		} catch (JSchException e)
		{
			// TODO Auto-generated catch block
			System.out.println("ex: restart");
			success = "false";
			e.printStackTrace();
		}

		// Output Parameter 'success' (optional)
		// TODO: Set success parameter here.
		// Do NOT delete the next line of code. Set "" as value if you want to return nothing or an empty result!
		returnParameters.put("success", success);

		sendResponse(returnParameters);
	}
}
