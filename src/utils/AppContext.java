package utils;

import models.Client;

public class AppContext {
	
	private static final AppContext instance = new AppContext();
	private Client currentClient;
	
	private AppContext (){
		currentClient = new Client();
	}
		
	public static Client getCurrentClient(){
		return instance.currentClient;
	}
	
	public static void setCurrentClient(Client client){
		instance.currentClient = client;
	}	
}
