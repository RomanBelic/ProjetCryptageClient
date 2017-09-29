package threading;

import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import implementations.CommunicationProtocolImplementation;
import implementations.ResponseProcessorImplementation;
import interfaces.Communication.ICommunicationProtocol;
import interfaces.Communication.IEventAdapter;
import interfaces.Communication.IResponseProcessor;
import models.Message;

public class CommunicationThread extends Thread implements Runnable {

	private Socket socket;
	private volatile IEventAdapter<Message> eventAdapter;
	private volatile AtomicBoolean isActive;
	private ICommunicationProtocol<Message> commProtocol;
	private IResponseProcessor<Message> responseProcessor;
	private final int port;
	private final String host;
	
	public IEventAdapter<Message> getEventAdapter(){
		return eventAdapter;
	}
	
	public void setEventAdapter(IEventAdapter<Message> eventAdapter){
		this.eventAdapter = eventAdapter;
	}
	
	public CommunicationThread (String host, int port){
		this.host = host;
		this.port = port;
		this.isActive = new AtomicBoolean(false);
	}
	
	
	public synchronized void closeSocket(){
		commProtocol.closeSocket();
	}
	
	public synchronized void sendMessage(Message msg){
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
			closeSocket();
		}catch(Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		isActive.set(false);
	}
	

	public synchronized void stopClient(){
		if(!isActive.get())
			return;
		isActive.set(false);
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (socket == null)
			return;
		socket.close();
	}
}
