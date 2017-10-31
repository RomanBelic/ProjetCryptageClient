package models;

import java.security.Key;
import java.security.KeyPair;

public class Client {

	private int id;
	private String name;
	private KeyPair clientKeyPair;
	private Key serverKey;
	
	
	public Client(){
		this.id = 0;
		this.name = new String();
		this.clientKeyPair = null;
		this.serverKey = null;
	}
	
	public KeyPair getClientKeyPair() {
		return clientKeyPair;
	}

	public void setClientKeyPair(KeyPair clientKeyPair) {
		this.clientKeyPair = clientKeyPair;
	}
	
	public Key getClientPublicKey(){
		return clientKeyPair != null ? clientKeyPair.getPublic() : null;
	}
	
	public Key getClientPrivateKey(){
		return clientKeyPair != null ? clientKeyPair.getPrivate() : null;
	}

	public Key getServerKey() {
		return serverKey;
	}

	public void setServerKey(Key serverKey) {
		this.serverKey = serverKey;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isEmpty(){
		return id <= 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Client && ((Client)obj).id == id;
	}
	
	@Override
	public int hashCode() {
		return new String(String.valueOf(id) + name).hashCode();
	}
}
