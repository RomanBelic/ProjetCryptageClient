package utils;

import models.Client;

public class Context {
	
	private static Client currentClient = new Client();
	
	public static Client getCurrentClient(){
		return currentClient;
	}
	
	public static void setCurrentClient(Client client){
		currentClient = client;
	}
	
}
