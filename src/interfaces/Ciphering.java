package interfaces;

import java.security.Key;
import java.security.KeyPair;

public class Ciphering {
	
	public interface IHashable{
		String createHashString(String input);
	}
	
	public interface IKeyGenerator{
		Key getKeyFromBytes(byte[] keyBytes, int keyOption);
		KeyPair generateKeyPair();
	}
	
	public interface ICipher {
		byte[] encrypt(byte[] input);
		byte[] decrypt(byte[] input);
	}
}
