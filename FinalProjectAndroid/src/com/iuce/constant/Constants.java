package com.iuce.constant;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Constants {

	//variables of command
	public static String COMMAND_RIGHT = "";
	public static String COMMAND_LEFT = "";
	public static String COMMAND_GO = "";
	public static String COMMAND_START_VIDEO_STREAM= "";
	
	//variables of socket 
	public static int PORT_ADDRESS_SERVER = 999;
	public static String IP_STRING = "";

	public static InetAddress getIP_ADDRESS_SERVER() throws UnknownHostException {

		return InetAddress.getByName(IP_STRING);
	}
	

}
