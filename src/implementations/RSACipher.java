package implementations;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import interfaces.Ciphering.ICipher;

public class RSACipher implements ICipher {

	private final KeyPairGenerator kpg;
	
	public RSACipher() throws NoSuchAlgorithmException, NoSuchPaddingException {
		Cipher.getInstance("RSA");
		kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(512);
	}
	
	public KeyPair generateKeyPair(){
		return kpg.generateKeyPair();
	}
	
	@Override
	public byte[] encrypt(byte[] input, String key) {
		return null;
	}

	@Override
	public byte[] decrypt(byte[] input, String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
