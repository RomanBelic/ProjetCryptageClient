package models;

import java.io.Serializable;

public class Message implements Serializable {
	
	private static final long serialVersionUID = -5130689345004322457L;
	
	private String plainText;
	private byte[] data;
	private long packets;
	private String senderName;
	private int code;
	
	public Message(){
		plainText = new String();
		senderName = new String();
		packets = 0l;
		data = new byte[]{};
	}
	
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public String getPlainText() {
		return plainText;
	}
	public void setPlainText(String plainText) {
		this.plainText = plainText;
	}
	public long getPackets() {
		return packets;
	}
	public void setPackets(long packets) {
		this.packets = packets;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
}
