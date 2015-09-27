package cipher;

public class MonoSubCipher extends RandSubCipher{
	public MonoSubCipher(String key) {
		this.key = key;
		System.out.println(key);
	}
}
