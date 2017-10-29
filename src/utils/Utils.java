package utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Utils {
	
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
	
	public static void writeBytesToFile(byte[] bytes, File file){
		try (OutputStream os = new FileOutputStream(file)){
			os.write(bytes, 0, bytes.length);
			os.flush();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void writeBytesToFile(byte[] bytes, String path){
		try (OutputStream os = new FileOutputStream(path)){
			os.write(bytes, 0, bytes.length);
			os.flush();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
