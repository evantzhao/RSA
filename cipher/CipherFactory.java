package cipher;

import java.math.BigInteger;

/** Factory class for creating cipher objects. */
public class CipherFactory {
    /**
     * Returns: a monoalphabetic substitution cipher with the English alphabet
     * mapped to the provided alphabet.<br>
     * Requires: {@code encrAlp} contains exactly one occurrence of each English
     * letter and nothing more. No requirement is made on case.
     * 
     * @param encrAlp
     *            the encrypted alphabet
     */
    public Cipher getMonoCipher(String encrAlp) {
    	encrAlp = encrAlp.toUpperCase();
        return new MonoSubCipher(encrAlp);
    }

    /**
     * Returns a new Caesar cipher with the given shift parameter.
     * 
     * @param shftParam
     *            the cipher's shift parameter
     */
    public Cipher getCaesarCipher(int shftParam) {
        return new CeasarCipher(shftParam); 
    }

    /**
     * Returns a Vigenere cipher (with multiple shifts).
     * 
     * @param key
     *            the cipher's shift parameters. Note that A is a shift of 1.
     */
    public Cipher getVigenereCipher(String key) {
        return new VigenereCipher(key); 
    }

    /**
     * Returns a new monoalphabetic simple substitution cipher with a randomly
     * generated mapping.
     */
    public Cipher getRandomSubstitutionCipher() {
        return new RandSubCipher(); 
    }

    /**
     * Returns a new RSA private cipher with a randomly generated keys.
     */
    public Cipher getRSACipher() {
        return new RSACipher(); 
    }

    /**
     * Returns a new RSA cipher with given n, e, and d
     *
     * @param d
     *            may be null- this indicates the returned Cipher is for
     *            encryption only
     * @param e
     *            may be null- this indicates the returned Cipher is for
     *            decryption only
     */
    public Cipher getRSACipher(BigInteger n, BigInteger e, BigInteger d) {
        return new RSACipher(n, e, d); 
    }
    
    /**
     * Returns a new route cipher with given width
     *
     * @param width 
     * 			the width of the grid used to encrypt a message
     */
    public Cipher getRouteCipher(int width) {
        return null; //TODO implement
    }
}
