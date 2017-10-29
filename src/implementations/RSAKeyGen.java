package implementations;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

import interfaces.Ciphering.IKeyGenerator;

public class RSAKeyGen implements IKeyGenerator {

	private KeyPairGenerator keyGenerator;
	private KeyFactory keyFactory;
	private static final String algorithm = "RSA";
	
	public RSAKeyGen(){
		try {
			keyGenerator = KeyPairGenerator.getInstance(algorithm);
			keyFactory = KeyFactory.getInstance(algorithm);
			keyGenerator.initialize(1024);
		}catch (NoSuchAlgorithmException e){
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public Key getKeyFromBytes(byte[] keyBytes, int keyOption) {
		Key key = null;
		KeySpec keySpec = null;
		try {
			switch(keyOption){
				case Cipher.PRIVATE_KEY :
					keySpec = new PKCS8EncodedKeySpec(keyBytes);
					key = keyFactory.generatePrivate(keySpec);
					break;
				case Cipher.PUBLIC_KEY :
					keySpec = new X509EncodedKeySpec(keyBytes);
					key = keyFactory.generatePublic(keySpec);
					break;
				default : 
					key = null;
			}
		}catch(Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return key;
	}

	@Override
	public KeyPair generateKeyPair() {
		return keyGenerator.generateKeyPair();
	}

}
