package utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import implementations.HashImplementation;
import interfaces.Ciphering.IHashable;

public class Utils {
	
	private static final IHashable hasher = new HashImplementation();
	
	public static IHashable getHasherInstance(){
		return hasher;
	}
	
	public static byte[] readBytesFromFile(File file){
		byte[] buffer = new byte[2048];
		try(InputStream is = new FileInputStream(file)){
			try(ByteArrayOutputStream bas = new ByteArrayOutputStream(2048)){
				int bRead;
				while((bRead = is.read(buffer, 0, buffer.length)) > 0){
					bas.write(buffer, 0, bRead);
				}
				buffer = bas.toByteArray();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return buffer;
	}

}
