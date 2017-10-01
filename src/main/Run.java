package main;

import java.awt.EventQueue;
import gui.ConnectionGUI;
import threading.CommunicationThread;

public class Run {

	public static void main(String[] args)  {
		CommunicationThread communicationThread = CommunicationThread.getInstance();
		communicationThread.connectToServer("localhost", 8888);
		EventQueue.invokeLater(() -> {
			new ConnectionGUI();
		});
	}
}
