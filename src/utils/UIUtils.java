package utils;

import gui.ConnectionGUI;
import gui.RegisterGUI;

public class UIUtils {
	
	private static ConnectionGUI connUI;
	private static RegisterGUI regUI;
	
	public static void initializeUI(){
		if (connUI == null)
			connUI = new ConnectionGUI();
		if (regUI == null)
			regUI = new RegisterGUI();
	}
	
	public static ConnectionGUI getConnectionGUI(){
		if (connUI == null)
			connUI = new ConnectionGUI();
		return connUI;
	}
	
	public static RegisterGUI getRegisterGUI(){
		if (regUI == null)
			regUI = new RegisterGUI();
		return regUI;
	}
	
}
