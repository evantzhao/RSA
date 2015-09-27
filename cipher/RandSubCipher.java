package cipher;

import java.util.Random;

public class RandSubCipher extends AbstractCipher {
	
	public RandSubCipher(){
		genKey();
	}

	public String encrypt(String msg) {
		// TODO Auto-generated method stub
		String msgs = normalize(msg);
		s = "";
		
		for(int i = 0; i < msgs.length(); i++){
			int index = alphaArray.indexOf(msgs.charAt(i));
			if (index == -1){
				s = s + msgs.charAt(i);
			}
			else{
				s = s + key.charAt(index);
			}
		}
		return s;
	}

	public String decrypt(String ciphertext) {
		// TODO Auto-generated method stub
		s = "";
		
		for(int i = 0; i < ciphertext.length(); i++){
			int index = key.indexOf(ciphertext.charAt(i));
			if (index == -1){
				s = s + ciphertext.charAt(i);
			}
			else{
				s = s + alphaArray.charAt(index);			
			}
		}
		return s;
	}
	
	/**
	 * Generates a cipher key for the RandSubCipher to use. 
	 * The order is important in the key, as it corresponds to the alphaArray, 
	 * and is involved in encrypting and decrypting.
	 */
	protected void genKey() {
		key = "";
		boolean[] b = genBool(26);
		while (key.length() < 26) {
			int index = randInt(0,25);
			if (! b[index]){
				b[index] = true;
				key = key + alphaArray.charAt(index);
			}
		}
	}
	
	/**
	 * Finds a random number in the range [min, max] (Inclusive)
	 * @param min The minimum value of the range
	 * @param max The maximum value of the range
	 * @return Returns a random number in the defined range.
	 */
	protected static int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	/**
	 * Generates an array of booleans and fills each index with 'false'.
	 * @param leng The length of the boolean array
	 * @return An array of false.
	 */
	protected boolean[] genBool(int leng){
		boolean[] boo = new boolean[leng];
		for(int i = 0; i< boo.length;i++){
			boo[i] = false;
		}
		
		return boo;
	}
}
