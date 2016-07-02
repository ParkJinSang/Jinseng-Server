package main;


import java.io.IOException;
import java.io.UnsupportedEncodingException;

import common.LogReporter;
import core.http.HttpServer;
import core.http.HttpServiceRouter;
import core.http.HttpUrl;
import core.tcp.*;
import sample.http.EchoWebService;
import sample.http.RestWebService;
import sample.tcp.ChatRoomService;
import sample.tcp.FileTransferService;
import sample.tcp.PrintService;

public class Main {

	private static void LaunchPrintServer(){
		TcpServer server = new TcpServer(new PrintService());
		try {
			server.RunServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.EndServer();
	}
	
	private static void LaunchFileTransferServer(){
		TcpServer server = new TcpServer(new FileTransferService());
		try {
			server.RunServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.EndServer();
	}
	
	private static void LaunchChatRoomServer(){
		TcpServer server = new TcpServer(new ChatRoomService());
		try {
			server.RunServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.EndServer();
	}
	
	private static void LaunchEchoWebServer(){

//		HttpServiceRouter route = new HttpServiceRouter();
//		route.setRoutingMethod("*", new EchoWebService());
		
		HttpServer server = new HttpServer(new EchoWebService(), 60000);
		try {
			server.RunServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.EndServer();
	}
	
	private static void LaunchRestWebServer(){

		HttpServiceRouter route = new HttpServiceRouter();
		route.setRoutingMethod("/students/{name}/{grade}/{age}", new RestWebService());
		HttpServer server = new HttpServer(route, 60000);
		
		try {
			server.RunServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.EndServer();
	}
	
	public static void main(String[] args){
		
		System.out.println("Hello World");
		
//		LaunchChatRoomServer();
		
//		LaunchEchoWebServer();
		
		LaunchRestWebServer();

		
	}
	
}
