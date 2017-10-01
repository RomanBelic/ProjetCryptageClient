package implementations;

import java.util.zip.CRC32;

import interfaces.Ciphering.IHashable;

public class HashImplementation implements IHashable {

	@Override
	public long createHash(byte[] input) {
		CRC32 crc = new CRC32();
		crc.update(input);
		return crc.getValue();
	}

	@Override
	public String createHashString(String input) {
		CRC32 crc = new CRC32();
		crc.update(input.getBytes());
		return String.valueOf(crc.getValue());
	}
}
