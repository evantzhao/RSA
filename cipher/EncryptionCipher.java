package cipher;

import java.io.InputStream;
import java.io.OutputStream;

/* A cipher that knows how to encrypt plaintext input, producing ciphertext. */
public interface EncryptionCipher {
    /**
     * Encrypts a message from the input stream using this cipher
     * and sends the result to the output stream.
     * @param in The InputStream the message is on
     * @param out The OutputStream to send the encrypted message to
     */
    public void encrypt(InputStream in, OutputStream out);

    /**
     * Encrypts the plaintext string {@code msg} and returns the result.
     * @param msg The plaintext to be encrypted
     * @return    An encrypted ciphertext
     */
    public String encrypt(String msg);

    // Hint: there is no reason why the two methods above should not share code.
    // But which one should be the 'real' implementation?

    /**
     * Saves this cipher to the output stream
     * 
     * @param out
     *            The OutStream to save the cipher to
     */
    public void save(OutputStream out);
}
