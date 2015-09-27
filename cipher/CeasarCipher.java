package cipher;

public class CeasarCipher extends RandSubCipher {
	private int shift;
	
	public CeasarCipher(int shift) {
		this.shift = shift;
		genKey();
	}
	
	/**
	 * Generates a cipher key for the CeasarCipher to use. 
	 * The order is important in the key, as it corresponds to the alphaArray, 
	 * and is involved in encrypting and decrypting.
	 */
	@Override
	public void genKey() {
		key = "";
		for(int i = 0; i < alphaArray.length(); i++){
			int shifter = shift + i;
			while (shifter >= alphaArray.length()){
				shifter = shifter - alphaArray.length();
			}
			while (shifter < 0){
				shifter = shifter + alphaArray.length();
			}
			key = key + alphaArray.charAt(shifter);
		}
	}

}
