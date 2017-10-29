package implementations;

import java.security.MessageDigest;

import interfaces.Ciphering.IHashable;

public class SHA256Hasher implements IHashable {

	public String createHashString(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashedBytes = md.digest(input.getBytes());
			StringBuilder strBuilder = new StringBuilder(256);
			for(int i = 0; i < hashedBytes.length; i++){
				strBuilder.append(Integer.toHexString(0xFF & hashedBytes[i]));
			}
			return strBuilder.toString();
		}catch(Exception e){
			e.printStackTrace();
			System.err.println(e.getMessage());
			return null;
		}
	}
}
