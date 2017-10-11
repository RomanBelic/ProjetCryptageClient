package interfaces;

public class Ciphering {
	
	public interface ICipher {
		byte[] encrypt(byte[] input, String key);
		byte[] decrypt(byte[] input, String key);
	}
	
	public interface IHashable{
		//CRC32 method
		long createHash(byte[] input);
		String createHashString(String input);
	}
}
