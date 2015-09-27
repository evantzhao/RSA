package cipher;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/** A place to put some inherited code? */
public abstract class AbstractCipher implements Cipher {
	protected String alphaArray = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	protected String s = "";
	protected String key = "";
	
	protected String normalize(String input) {
		String words = input.replaceAll("[^a-zA-Z\n\t ]", "").toUpperCase();
		return words;
	}
	
	public String accessCipherText() {
		return s;
	}
	
	public String accessKey(){
		return key;
	}
	
	/**
	 * Encryption of any cipher. With input output streams.
	 */
	public void encrypt(InputStream in, OutputStream out) {
		int data;
    	ArrayList<Byte> datab = new ArrayList<Byte>();
		try {
			data = in.read();
			while(data != -1){
				datab.add((byte) data);
				data = in.read();
			}
		} catch (IOException e) {
			e.printStackTrace();
			}
		
		byte[] b = new byte[datab.size()];
		for(int i = 0; i < b.length; i++) {
			b[i] = datab.get(i);
		}
		
		String output = encrypt(new String(b));
		try{
			out.write(output.getBytes());
			out.close();
		} catch (IOException e) {
			System.out.println("Problem");
			e.printStackTrace();
		}
	}
	
	/**
	 * Decryption of any cipher. With input output streams.
	 */
	public void decrypt(InputStream in, OutputStream out) {
		int data;
    	ArrayList<Byte> datab = new ArrayList<Byte>();
		try {
			data = in.read();
			while(data != -1){
				datab.add((byte) data);
				data = in.read();
			}
		} catch (IOException e) {
			e.printStackTrace();
			}
		
		byte[] b = new byte[datab.size()];
		for(int i = 0; i < b.length; i++) {
			b[i] = datab.get(i);
		}
		
		String output = decrypt(new String(b));
		
		try{
			out.write(output.getBytes());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void save(OutputStream out) {
		byte[] b = s.getBytes(StandardCharsets.ISO_8859_1);
		
		try {
			out.write(b);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
