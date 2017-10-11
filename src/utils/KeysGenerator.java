package utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

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
			// TODO Auto-generated catch blockff
			e.printStackTrace();
		}
	}
	
	public PublicKey getPublicKeyFromKeyPair(){
		return keyPair.getPublic();
	}
	
	public PrivateKey getPrivateKeyFromKeyPair(){
		return keyPair.getPrivate();
	}
	
	public static void savePrivateKey(String clientName, String path, PrivateKey privateKey) throws IOException{
		FileOutputStream fileOutputStream = new FileOutputStream(path+"/"+clientName+".txt");
		fileOutputStream.write(privateKey.getEncoded());
		fileOutputStream.close();
	}
	
	public static PrivateKey loadPrivateKey(String clientName, String path) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException{
		File filePrivateKey = new File(path+"/"+clientName+".txt");
		FileInputStream fileInputStream = new FileInputStream(path+"/"+clientName+".txt");
		
		byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
		fileInputStream.read(encodedPrivateKey);
		fileInputStream.close();
		
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(privateKeySpec);
	}
	
	public KeyPair getKeyPair() {
		return keyPair;
	}

	public void setKeyPair(KeyPair keyPair) {
		this.keyPair = keyPair;
	}

	
	
}
