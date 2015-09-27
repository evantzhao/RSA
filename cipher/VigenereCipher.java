package cipher;

public class VigenereCipher extends AbstractCipher {
	protected String key = "";
	
	public VigenereCipher(String rawKey) {
		this.key = translate(rawKey);
	}
	
	public String translate(String rawKey) {
		String holder = "";
		for(int i = 0; i < rawKey.length(); i++) {
			int index = alphaArray.indexOf(rawKey.charAt(i)) + 1;
			holder = holder + index;
		}
		return holder;
	}

	public String encrypt(String msg) {
		return crypt(msg, "encrypt");
	}

	public String decrypt(String ciphertext) {
		return crypt(ciphertext, "decrypt");
	}

	/**
	 * A method that can encrypt and decrypt. It just doesn't know which one to do.
	 * @param msg The string to decrypt or encrypt
	 * @param action The thing to do. Takes only "encrypt" or "decrypt".
	 * @return A cipherText or plainText string.
	 */
	private String crypt(String msg, String action) {
		String msgs = normalize(msg);
		s = "";
		
		for(int i = 0; i < msgs.length(); i++){
			int index = alphaArray.indexOf(msgs.charAt(i));
			if (index == -1) {
				s = s + msgs.charAt(i);
			}
			else{
				int checker = i;
				while (checker >= key.length()) {
					checker = checker - key.length();
				}
				int stepper = index + Character.getNumericValue(key.charAt(checker));

				while (stepper > alphaArray.length()){
					if (action.equals("encrypt")){
						stepper = stepper - alphaArray.length();
					}
					else if (action.equals("decrypt")) {
						stepper = alphaArray.length() - stepper;
					}
				}
				s = s + alphaArray.charAt(stepper);
			}
		}
		return s;
	}
	
}
