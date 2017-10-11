package utils;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

public class KeysGenerator {
	private KeyPair keyPair;
	
	public KeysGenerator(){
		KeyPairGenerator keyGen;
		SecureRandom random = new SecureRandom();
		
		try {
			keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(1024, random);
			keyPair = keyGen.genKeyPair();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public PublicKey getPublicKeyFromKeyPair(){
		return keyPair.getPublic();
	}
	
	public PrivateKey getPrivateKeyFromKeyPair(){
		return keyPair.getPrivate();
	}
	
	public KeyPair getKeyPair() {
		return keyPair;
	}

	public void setKeyPair(KeyPair keyPair) {
		this.keyPair = keyPair;
	}

	
	
}
