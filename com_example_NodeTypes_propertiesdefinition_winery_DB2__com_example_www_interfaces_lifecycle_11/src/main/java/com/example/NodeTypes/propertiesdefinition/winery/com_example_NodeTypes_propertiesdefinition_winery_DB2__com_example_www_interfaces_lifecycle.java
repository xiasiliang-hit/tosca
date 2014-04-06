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
public class com_example_NodeTypes_propertiesdefinition_winery_DB2__com_example_www_interfaces_lifecycle extends AbstractIAService {

	private String installSFile = "install.sh";
	private String startSFile = "start.sh";
	private String stopSFile="stop.sh";
	private String restartSFile = "restart.sh";
	private String createdbSFile = "createdb.sh";
	private String uninstallSFile = "deinstall.sh";

	
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
		String re = "";
		String success = "false";
		
		EC2LinuxIAService ser = new EC2LinuxIAService();
		ser.transferLocalFolder(targetIP, abPath, toPath, sshKey);

		try
		{
			re = ser.runScript(targetIP, "bash " + installSFile, sshKey);
			System.out.println(re);
			
			if (re.contains("The execution completed successfully"))
			{
				success = "true";
			}
			else
			{
				success = "false";
			}
		}
		catch (JSchException e)
		{
			// TODO Auto-generated catch block
			System.out.println("!!!ex: install");
			success = "false";
			e.printStackTrace();
		}


		// Output Parameter 'success' (optional)
		// TODO: Set success parameter here.
		// Do NOT delete the next line of code. Set "" as value if you want to return nothing or an empty result!
		returnParameters.put("success", success);

		// Output Parameter 'log' (optional)
		// TODO: Set log parameter here.
		// Do NOT delete the next line of code. Set "" as value if you want to return nothing or an empty result!
		returnParameters.put("log", re);

		sendResponse(returnParameters);
	}

	
	//no implementation
	@WebMethod
	@SOAPBinding
	@Oneway
	public void configure(
		@WebParam(name="targetIP", targetNamespace="http://winery.propertiesdefinition.NodeTypes.example.com/") String targetIP
	) {
		// TODO: Implement your operation here.
	}

	@WebMethod
	@SOAPBinding
	@Oneway
	public void start(
		@WebParam(name="targetIP", targetNamespace="http://winery.propertiesdefinition.NodeTypes.example.com/") String targetIP
	) {
		final HashMap<String,String> returnParameters = new HashMap<String, String>();
		String re = "";
		// TODO: Implement your operation here.
		
		String success = "false";
		EC2LinuxIAService ser = new EC2LinuxIAService();

		try
		{
			re = ser.runScript(targetIP, "bash " + startSFile, sshKey);
			System.out.println(re);
			
			if (re.contains("DB2START processing was successful"))
			{
				success = "true";
			}
			else
			{
				success = "false";
			}
		} catch (JSchException e)
		{
			// TODO Auto-generated catch block
			System.out.println("!!!ex: start");
			success = "false";
			e.printStackTrace();
		}

		// Output Parameter 'sucess' (optional)
		// TODO: Set sucess parameter here.
		// Do NOT delete the next line of code. Set "" as value if you want to return nothing or an empty result!
		//returnParameters.put("sucess", success);
		// TODO: Implement your operation here.
		returnParameters.put("ip", targetIP);

		// Output Parameter 'port' (optional)
		// TODO: Set port parameter here.
		// Do NOT delete the next line of code. Set "" as value if you want to return nothing or an empty result!
		returnParameters.put("port", defaultPort);


		// Output Parameter 'success' (optional)
		// TODO: Set success parameter here.
		// Do NOT delete the next line of code. Set "" as value if you want to return nothing or an empty result!
		returnParameters.put("success", success);

		// Output Parameter 'log' (optional)
		// TODO: Set log parameter here.
		// Do NOT delete the next line of code. Set "" as value if you want to return nothing or an empty result!
		returnParameters.put("log", re);

		sendResponse(returnParameters);
	}

	@WebMethod
	@SOAPBinding
	@Oneway
	public void stop(
		@WebParam(name="targetIP", targetNamespace="http://winery.propertiesdefinition.NodeTypes.example.com/") String targetIP
	) {
		// TODO: Implement your operation here.
				final HashMap<String,String> returnParameters = new HashMap<String, String>();
				String re = "";	
				// TODO: Implement your operation here.
				String success = "false";
				EC2LinuxIAService ser = new EC2LinuxIAService();

				try
				{
					re = ser.runScript(targetIP, "bash " + stopSFile, sshKey);
					System.out.println(re);
					
					if (re.contains("DB2STOP processing was successful"))
					{
						success = "true";
					}
					else
					{
						success = "false";
					}
					
				} catch (JSchException e)
				{
					// TODO Auto-generated catch block
					System.out.println("!!!ex: stop");
					success = "false";
					e.printStackTrace();
				}


		// Output Parameter 'success' (optional)
		// TODO: Set success parameter here.
		// Do NOT delete the next line of code. Set "" as value if you want to return nothing or an empty result!
		returnParameters.put("success", success);

		// Output Parameter 'log' (optional)
		// TODO: Set log parameter here.
		// Do NOT delete the next line of code. Set "" as value if you want to return nothing or an empty result!
		returnParameters.put("log", re);

		sendResponse(returnParameters);
	}

	@WebMethod
	@SOAPBinding
	@Oneway
	public void uninstall(
		@WebParam(name="targetIP", targetNamespace="http://winery.propertiesdefinition.NodeTypes.example.com/") String targetIP
	) {
		final HashMap<String,String> returnParameters = new HashMap<String, String>();
		String re = "";
		// TODO: Implement your operation here.
		String success = "true";
		EC2LinuxIAService ser = new EC2LinuxIAService();

		try
		{
			re = ser.runScript(targetIP, "bash " + uninstallSFile, sshKey);
			System.out.println(re);
			
			if (re.contains("The execution completed successfully"))
			{
				success = "true";
			}
			else
			{
				success = "false";
			}
			
		} catch (JSchException e)
		{
			// TODO Auto-generated catch block
			System.out.println("!!!ex: uninstall");
			success = "false";
			e.printStackTrace();
		}



		// Output Parameter 'success' (optional)
		// TODO: Set success parameter here.
		// Do NOT delete the next line of code. Set "" as value if you want to return nothing or an empty result!
		returnParameters.put("success", success);

		// Output Parameter 'log' (optional)
		// TODO: Set log parameter here.
		// Do NOT delete the next line of code. Set "" as value if you want to return nothing or an empty result!
		returnParameters.put("log", re);

		sendResponse(returnParameters);
	}

	@WebMethod
	@SOAPBinding
	@Oneway
	public void restart(
		@WebParam(name="targetIP", targetNamespace="http://winery.propertiesdefinition.NodeTypes.example.com/") String targetIP
	) {
		//This HashMap holds the return parameters of this operation.
				final HashMap<String,String> returnParameters = new HashMap<String, String>();
				String re = "";
				// TODO: Implement your operation here.
				String success = "false";
				EC2LinuxIAService ser = new EC2LinuxIAService();

				try
				{	
					re = ser.runScript(targetIP, "bash " + restartSFile, sshKey);
					System.out.println(re);
					
					if (re.contains("The database manager is already active") || re.contains("DB2START processing was successful"))
					{
						success = "true";
					}
					else
					{
						success = "false";
					}
					
				} catch (JSchException e)
				{
					// TODO Auto-generated catch block
					System.out.println("!!!ex: restart");
					success = "false";
					e.printStackTrace();
				}


		// Output Parameter 'success' (optional)
		// TODO: Set success parameter here.
		// Do NOT delete the next line of code. Set "" as value if you want to return nothing or an empty result!
		returnParameters.put("success", success);

		// Output Parameter 'log' (optional)
		// TODO: Set log parameter here.
		// Do NOT delete the next line of code. Set "" as value if you want to return nothing or an empty result!
		returnParameters.put("log", re);

		sendResponse(returnParameters);
	}

	@WebMethod
	@SOAPBinding
	@Oneway
	public void createdb(
		@WebParam(name="targetIP", targetNamespace="http://winery.propertiesdefinition.NodeTypes.example.com/") String targetIP,
		@WebParam(name="dbname", targetNamespace="http://winery.propertiesdefinition.NodeTypes.example.com/") String dbname
	) {
		// TODO: Implement your operation here.
		
				final HashMap<String,String> returnParameters = new HashMap<String, String>();
				String re = "";
				String success = "false";
				EC2LinuxIAService ser = new EC2LinuxIAService();

				try
				{
					String cmd = "bash " + createdbSFile + " " + dbname;
					
					re = ser.runScript(targetIP, cmd, sshKey);
					System.out.println(re);
					
					if (re.contains("The CREATE DATABASE command completed successfully"))
					{
						success = "true";
					}
					else
					{
						success = "false";
					}
					
				} catch (JSchException e)
				{
					// TODO Auto-generated catch block
					System.out.println("!!!ex: createdb");
					success = "false";
					e.printStackTrace();
				}


		// Output Parameter 'success' (optional)
		// TODO: Set success parameter here.
		// Do NOT delete the next line of code. Set "" as value if you want to return nothing or an empty result!
		returnParameters.put("success", success);

		// Output Parameter 'log' (optional)
		// TODO: Set log parameter here.
		// Do NOT delete the next line of code. Set "" as value if you want to return nothing or an empty result!
		returnParameters.put("log", re);

		sendResponse(returnParameters);
	}
}
