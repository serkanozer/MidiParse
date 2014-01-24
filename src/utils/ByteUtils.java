package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class ByteUtils {

	public static byte[] bytesOfFile(String filename) {
		RandomAccessFile f;
		try {
			f = new RandomAccessFile(filename, "r");
			byte[] b = new byte[(int) f.length()];
			f.read(b);
			return b;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static int byteToInt(byte b) {
		if (b < 0) {
			return b + 256;
		} else
			return b;
	}

	public static int bytesToInt(byte[] input) {
		ByteBuffer wrapped = ByteBuffer.wrap(input);
		int length = wrapped.getInt();
		return length;
	}

	public static short bytesToShort(byte[] input) {
		ByteBuffer wrapped = ByteBuffer.wrap(input);
		short length = wrapped.getShort();
		return length;
	}

	public static String bytesToString(byte[] input) {
		return new String(input);
	}

	public static int byteCopy(byte[] large, byte[] small, int start, int offset) {

		for (int i = start; i < start + offset; i++)
			small[i - start] = large[i];
		return offset;
	}

	public static byte[] byteCopy(byte[] large, int start, int offset) {
		byte b[] = new byte[offset];
		for (int i = start; i < start + offset; i++)
			b[i - start] = large[i];
		return b;
	}

	public static long ReadVarLen(byte[] in) {
		long value;
		byte b;
		int i = 0;
		if (((value = in[0]) & 0x80) > 0) {
			value &= 0x7F;
			i++;
			do {
				value = (value << 7) + ((b = in[i]) & 0x7F);
				i++;
			} while ((b & 0x80) > 0);
		}

		return (value);
	}

	public static long ReadVarLen(byte[] in, int start) {
		long value;
		byte b;
		int i = start;
		if (((value = in[start]) & 0x80) > 0) {
			value &= 0x7F;
			i++;
			do {
				value = (value << 7) + ((b = in[i]) & 0x7F);
				i++;
			} while ((b & 0x80) > 0);
		}

		return (value);
	}

	public static short longLength(long l) {
		short i = 0;
		do {
			i++;
		} while ((l = l >> 7) > 0);
		return i;
	}
}
