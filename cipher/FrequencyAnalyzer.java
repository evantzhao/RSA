package cipher;

/** Analyses the frequency of different characters in some ciphertext and
 *  constructs a plausible cipher that could have produced that ciphertext.
 */
public class FrequencyAnalyzer {
	private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public int[] distribution = new int[alphabet.length()];
	
    public FrequencyAnalyzer(String input) {
    	populate(input);
    }
    
    public void populate(String text) {
    	text = text.replaceAll("[^a-zA-Z]", "").toUpperCase();
    	for(int i = 0; i < text.length(); i++) {
    		addChar(text.charAt(i));
    	}
    }

    /**
     * Adds an occurrence of a character to the analyzer if it is a letter,
     * otherwise ignore.
     * @param c the character to be added
     */
    public void addChar(char c) {
    	for(int i = 0; i < alphabet.length(); i++) {
    		if (alphabet.charAt(i) == c) {
    			distribution[i]++;
    			break;
    		}
    	}
    }

    /**
     * Returns the given character's current frequency in the analyzer.8
     * @return the frequency of the given character
     */
    public int getFrequency(char c) {
    	for(int i = 0; i < alphabet.length(); i++) {
    		if (alphabet.charAt(i) == c) {
    			return distribution[i];
    		}
    	}
        return -1;
    }

    /**
     * Returns a Caesar cipher constructed using data from the two
     * analyzers.
     * @param sample an analyzer containing the results of scanning some
     *               plaintext.
     * @param encrypted an analyzer containing the results of scanning
     *                  encrypted text(s) in the encrypted 
     * @return a Caesar cipher that is a best estimate of how {@code
     *         encrypted} was generated.
     */
    public AbstractCipher getCipher(FrequencyAnalyzer sample,
            FrequencyAnalyzer encrypted) {
		int[][] difference = new int[sample.distribution.length][sample.distribution.length];
    	for(int i = 0; i < sample.distribution.length; i++) {
    		for(int j = 0; j < sample.distribution.length; j++) {
    			difference[j][i] = Math.abs(getFrequency(alphabet.charAt(i)) - getFrequency(alphabet.charAt((j) % 26)));
    		}
    	}
    	
    	int[] accumulate = new int[sample.distribution.length];
    	
    	for(int j = 0; j < accumulate.length; j++) {
    		accumulate[j] = 0;
    		for(int z = 0; z < difference[j].length; z++) {
    			accumulate[j] += difference[j][z];
    		}
    	}
    	
    	int shift = getMinValue(accumulate);
    	
        return new CeasarCipher(shift); 
    }
    
 // getting the miniumum value
    public int getMinValue(int[] array){  
         int minValue = array[0];  
         int mini = 0;
         for(int i=1;i<array.length;i++){  
        	 if(array[i] < minValue){  
        		 minValue = array[i]; 
        		 mini = i;
            }
         }  
        return mini;  
    }  
}
