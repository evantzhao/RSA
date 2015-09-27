package cipher;

/**
 * A cipher that both encrypts and decrypts.
 */
public interface Cipher extends EncryptionCipher, DecryptionCipher {
	
	/**
	 * Access a the most recently generated ciphertext.
	 * @return ciphertext of the most recently run process.
	 */
	public String accessCipherText();
	
	/**
	 * Access the most recently used Cipher Key.
	 * @return key of the most recently run cipher.
	 */
	public String accessKey();
}
