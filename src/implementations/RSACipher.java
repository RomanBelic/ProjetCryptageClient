package implementations;

import java.security.Key;

import javax.crypto.Cipher;

import interfaces.Ciphering.ICipher;

public class RSACipher implements ICipher {
	
	private Cipher cipher;
	private final Key key;
	
	public RSACipher(Key key){
		this.key = key;
		try {
			this.cipher = Cipher.getInstance("RSA");
		}catch(Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public byte[] encrypt(byte[] input) {
		byte[] bytes = new byte[]{};
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			bytes = cipher.doFinal(input);
		}catch (Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return bytes;
	}

	@Override
	public byte[] decrypt(byte[] input) {
		byte[] bytes = new byte[]{};
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			bytes = cipher.doFinal(input);
		}catch (Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return bytes;
	}

}
