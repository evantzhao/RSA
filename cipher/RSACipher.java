package cipher;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class RSACipher extends AbstractCipher {
	private BigInteger p;
	private BigInteger q;
	private BigInteger n;
	private BigInteger phi;
	private BigInteger e;
	private BigInteger d;
	
	public RSACipher() {
		// Certainty of probablePrime is 20.
		this.p = new BigInteger(512, 20, new Random());
		this.q = new BigInteger(512, 20, new Random());
		genKey();
	}
	
	
	public RSACipher(BigInteger n, BigInteger e, BigInteger d) {
		this.n = n;
		this.e = e;
		this.d = d;
	}
	
	@Override
	/**
	 * Different accessKey code. Returns a public RSA key, the elements of which are separated by a
	 * newline.
	 * @return Public RSA key
	 */
	public String accessKey() {
		return n.toString() + "\n" + e.toString();
	}
	
	/**
	 * Generates the RSA public and private key.
	 * Writes to the instance variables at the top of this class.
	 */
	private void genKey() {
		n = p.multiply(q);
		phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		e = BigInteger.probablePrime(128, new Random());
		while(phi.gcd(e) != BigInteger.ONE && phi.compareTo(e) != 1){
			e = BigInteger.probablePrime(128, new Random());
		}
		d = e.modInverse(phi);
	}

	@Override
	/**
	 * Overriden encryption function for input and output streams.
	 * @param in InputStream holding all the data that will be later encrypted
	 * @param out OutputStream that the data will be written to. 
	 */
	public void encrypt(InputStream in, OutputStream out) {
		ChunkRead c = new ChunkRead(in, 117);
		byte[] data = new byte[c.chunkSize()];
		c.nextChunk(data);
		byte[] finalData;
		BigInteger result = BigInteger.ZERO;

		while(c.bytesRead != -1) {
			finalData = new byte[c.chunkSize() + 1];
			for(int i = 0; i < data.length; i++){
				finalData[i] = data[i];
			}
			finalData[finalData.length - 1] = (byte) c.bytesRead;
			
			reverse(finalData);
			BigInteger bigB = new BigInteger(finalData);
			result = bigB.modPow(e, n);
			c.nextChunk(data);
			byte[] done = result.toByteArray();
			reverse(done);
			try {
				out.write(done);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("The Byte array isn't printing.");
				e1.printStackTrace();
			}
		}

		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Reverses an array of bytes.
	 * @param array of bytes.
	 */
	private static void reverse(byte[] array) {
	      if (array == null) {
	          return;
	      }
	      int i = 0;
	      int j = array.length - 1;
	      byte tmp;
	      while (j > i) {
	          tmp = array[j];
	          array[j] = array[i];
	          array[i] = tmp;
	          j--;
	          i++;
	      }
	  }
	
	@Override
	/**
	 * Encrypts a string by converting it to an array of bytes, then into a BigInteger, then
	 * encrypting it, then back into a byte[], then into a string.
	 * @param msg The string to be encrypted.
	 * @return Returns an encrypted string. 
	 */
	public String encrypt(String msg) {
		InputStream stream = new ByteArrayInputStream(msg.getBytes(StandardCharsets.ISO_8859_1));
		ChunkRead c = new ChunkRead(stream, 117);
		byte[] data = new byte[c.chunkSize()];
		c.nextChunk(data);
		byte[] finalData;
		s = "";

		while(c.bytesRead != -1) {
			finalData = new byte[c.chunkSize() + 1];
			for(int i = 0; i < data.length; i++){
				finalData[i] = data[i];
			}
			finalData[finalData.length - 1] = (byte) c.bytesRead;
			reverse(finalData);
			BigInteger bigB = new BigInteger(finalData);
			BigInteger result = bigB.modPow(e, n);
			c.nextChunk(data);
			byte[] done = result.toByteArray();
			reverse(done);
			String holder = new String(done, StandardCharsets.ISO_8859_1);
			s = s + holder;

			// Delete later.
//		    StringBuilder sb = new StringBuilder();
//		    for (byte b : done) {
//		        sb.append(String.format("%02X ", b));
//		    }
//		    System.out.println(sb.toString());
		}

		return s;
	}

	@Override
	/**
	 * Decrypts, except it writes to an output stream.
	 * @param in InputStream containing the data.
	 * @param out OutputStream is where the data will be written to.
	 */
	public void decrypt(InputStream in, OutputStream out) {
		ChunkRead c = new ChunkRead(in, 128);
		byte[] data = new byte[c.chunkSize()];
		c.nextChunk(data);
		 
		while(c.bytesRead != -1) {
			reverse(data);
			BigInteger bigB = new BigInteger(data);
			BigInteger result = bigB.modPow(d, n);
			
			c.nextChunk(data);
			byte[] done = result.toByteArray();
			reverse(done);
			byte[] finallyDone;
			if (done.length != done[117]){
				finallyDone = new byte[done[117]];
				for(int i = 0; i < done[117]; i++){
					finallyDone[i] = done[i];
				}
			} else{
				finallyDone = done;
			}
			
			try {
				out.write(finallyDone);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("get rekt again m8 your decrypter byte arr ain't printing.");
				e.printStackTrace();
			}
		}
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	/**
	 * Decrypts an encyrpted message.
	 * @param ciphertext The encrypted message.
	 * @return plaintext
	 */
	public String decrypt(String ciphertext) {
		InputStream stream = new ByteArrayInputStream(ciphertext.getBytes(StandardCharsets.ISO_8859_1));
		s = "";
		
		ChunkRead c = new ChunkRead(stream, 128);
		byte[] data = new byte[c.chunkSize()];
		c.nextChunk(data);
		
		while(c.bytesRead != -1) {
			reverse(data);
			BigInteger result = (new BigInteger(data)).modPow(d, n);
			
			byte[] done = result.toByteArray();
			reverse(done);
			
			byte[] finallyDone;
			if (done.length != done[117]){
				finallyDone = new byte[done[117]];
				for(int i = 0; i < done[117]; i++){
					finallyDone[i] = done[i];
				}
			} else{
				finallyDone = done;
			}
			
			String holder = new String(finallyDone, StandardCharsets.ISO_8859_1);
			s = s + holder;
			c.nextChunk(data);
		}
		return s;
	}
}
