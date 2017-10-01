package threading;

import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import implementations.CommunicationProtocolImplementation;
import implementations.GUIEventAdapter;
import implementations.ResponseProcessorImplementation;
import interfaces.Communication.ICommunicationProtocol;
import interfaces.Communication.IResponseProcessor;
import models.Message;

public class CommunicationThread extends Thread implements Runnable {
	
	private Socket socket;
	private volatile AtomicBoolean isActive;
	private ICommunicationProtocol<Message> commProtocol;
	private IResponseProcessor<Message> responseProcessor;
	private GUIEventAdapter eventAdapter;
	private int port;
	private String host;
	private static final CommunicationThread instance = new CommunicationThread();
	
	public GUIEventAdapter getEventAdapter() {
		return eventAdapter;
	}
	
	public static CommunicationThread getInstance(){
		return instance;
	}
	
	private CommunicationThread(){
		this.host = "";
		this.port = 0;
		this.isActive = new AtomicBoolean(false);
		this.eventAdapter = new GUIEventAdapter();
	}
	
	public void connectToServer(String host, int port){
		this.host = host;
		this.port = port;
		start();
	}
	
	public void sendMessage(Message msg){
		if (socket == null || socket.isClosed())
			return;
		commProtocol.sendResponse(msg);
	}
	
	@Override
	public synchronized void start() {
		if (isActive.get())
			return;
		isActive.set(true);
		super.start();
	}
	
	@Override
	public void run() {
		super.run();
		try {
			socket = new Socket(host, port);
			commProtocol = new CommunicationProtocolImplementation(socket);
			responseProcessor = new ResponseProcessorImplementation(commProtocol);
			Message msg;
			while((msg = commProtocol.getResponse()) != null && isActive.get()){
				responseProcessor.processResponse(msg, eventAdapter);
			}	
		}catch(Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		stopClient();
	}
	

	public void stopClient(){
		if(!isActive.get())
			return;
		isActive.set(false);
		commProtocol.closeSocket();
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (socket == null)
			return;
		socket.close();
	}
}
