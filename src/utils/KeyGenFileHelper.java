package utils;

import java.io.File;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

import implementations.RSAKeyGen;
import interfaces.Ciphering.IKeyGenerator;

public class KeyGenFileHelper {
	
	private final IKeyGenerator keyGen;
	private final String dirName;
	private static final String privateKeyName = "private_key.key";
	private static final String publicKeyName = "public_key.key";
	
	public KeyGenFileHelper(String dirName){
		this.keyGen = new RSAKeyGen();
		this.dirName = dirName;
	}
	
	public boolean hasKeys(){
		File dir = new File(dirName);
		if (!dir.exists())
			return false;
		File[]files = dir.listFiles();
		if (files == null || files.length <= 0)
			return false;
		
		int mask = 0;
		int privateFlag = 4;
		int publicFlag = 8;
		
		for (File f : files){
			if (f.getName().equals(privateKeyName)){
				mask |= privateFlag;
			}
			if (f.getName().equals(publicKeyName)){
				mask |= publicFlag;
			}
		}
		return (mask & (privateFlag | publicFlag)) == (privateFlag | publicFlag );
	}
	
	public void createKeyPairStorage (){
		File dir = new File(dirName);
		if (!dir.exists())
			dir.mkdir();
		File privateKeyFile = new File (dir.getAbsolutePath().concat(File.separator).concat(privateKeyName));
		File publicKeyFile = new File (dir.getAbsolutePath().concat(File.separator).concat(publicKeyName));
		
		KeyPair kp = keyGen.generateKeyPair();
		Utils.writeBytesToFile(kp.getPrivate().getEncoded(), privateKeyFile);
		Utils.writeBytesToFile(kp.getPublic().getEncoded(), publicKeyFile);
	}
	
	public KeyPair getKeyPairFromStorage(){
		File privateKeyFile = new File (dirName.concat(File.separator).concat(privateKeyName));
		File publicKeyFile = new File (dirName.concat(File.separator).concat(publicKeyName));
		byte[] pvKeyData = Utils.readBytesFromFile(privateKeyFile);
		byte[] pbKeyData = Utils.readBytesFromFile(publicKeyFile);
		PrivateKey pvKey = (PrivateKey)keyGen.getKeyFromBytes(pvKeyData, Cipher.PRIVATE_KEY);
		PublicKey pbKey = (PublicKey)keyGen.getKeyFromBytes(pbKeyData, Cipher.PUBLIC_KEY);
		
		return new KeyPair(pbKey, pvKey);
	}
	
	public Key getPublicKeyFromStorage(){
		File publicKeyFile = new File (dirName.concat(File.separator).concat(publicKeyName));
		byte[] pbKeyData = Utils.readBytesFromFile(publicKeyFile);
		PublicKey pbKey = (PublicKey)keyGen.getKeyFromBytes(pbKeyData, Cipher.PUBLIC_KEY);
		return pbKey;
	}
	
	public Key getPrivateKeyFromStorage(){
		File privateKeyFile = new File (dirName.concat(File.separator).concat(privateKeyName));
		byte[] pvKeyData = Utils.readBytesFromFile(privateKeyFile);
		PrivateKey pvKey = (PrivateKey)keyGen.getKeyFromBytes(pvKeyData, Cipher.PRIVATE_KEY);
		return pvKey;
	}
}
