package cipher;

import java.io.InputStream;
import java.io.OutputStream;

/* A cipher that knows how to decrypt ciphertext input, producing plaintext. */
public interface DecryptionCipher {
    /**
     * Decrypts a message from the input stream according to this cipher's
     * decryption protocol and sends the result to the output stream.
     * 
     * @param in
     *            The InputStream the message is on
     * @param out
     *            The OutputStream to send the decrypted message to
     */
    public void decrypt(InputStream in, OutputStream out);

    /**
     * Decrypts a message and returns the result.
     * 
     * @param ciphertext
     *            The ciphertext to decrypt
     * @return The decrypted plaintext
     */
    public String decrypt(String ciphertext);

    // Implementation note: It would be desirable for the two methods
    // to share code. Choose carefully how you do that!
}
