package cipher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * 
 * Command line interface to allow users to interact with your ciphers.
 * 
 * We have provided a structure to parse most of the arguments, and it is your
 * responsibility to implement the appropriate actions according to the
 * assignment 2 specifications. You may choose to "fill in the blanks" or
 * rewrite this class.
 *
 * Regardless of which option you choose, remember to minimize repetitive code.
 * You are welcome to add additional methods or alter the provided code to
 * achieve this.
 *
 */
public class Main {

    /**
     * Create a BufferedReader that reads from a file
     * 
     * @param filename
     *            Name of file to read from
     * @return a BufferedReader to read from the given file
     * @throws FileNotFoundException
     */
    public static BufferedReader openFile(String filename) throws FileNotFoundException {
        return new BufferedReader(new FileReader(filename));
    }

    /**
     * Create a BufferedReader that write to a file
     * 
     * @param filename
     *            Name of file to write to
     * @return a BufferedReader to write to the given file
     * @throws FileNotFoundException
     */
    public static BufferedWriter writeFile(String filename) throws IOException {
        return new BufferedWriter(new FileWriter(filename));
    }
    
    public static FileInputStream openInput(String filename) throws FileNotFoundException {
    	return new FileInputStream(filename);
    }
    
    public static FileOutputStream writeOutput(String filename) throws IOException {
    	return new FileOutputStream(filename);
    }

    /**
     * Read a given file
     * 
     * @param filename
     *            Name of file to be read
     * @return Contents of the file as a String
     */
    public static String readFile(String filename) {
        String line = null;
        StringBuilder result = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
        } catch (IOException e) {
            // TODO Add an appropriate error message
        	System.out.println("Error message: " + e);
        }
        return result.toString();
    }

    /**
     * Prints a string to a specified file
     * 
     * @param filename
     *            File in which string will be printed
     * @param towrite
     *            String to be printed in file
     * @throws IOException
     */
    public static void writeToFile(String filename, String towrite) throws IOException {
        BufferedWriter w = writeFile(filename);
        w.write(towrite, 0, towrite.length());
        w.close();
    }

    public static OutputStream outputFunction;
    
    /**
     * Reads the cipher function from command line and performs specified task
     * 
     * @param func
     *            String specifying cipher function to be completed
     * @param c
     *            The cipher created based on first command
     * @param name
     *            The message or file name to be encrypted/decrypted 
     */
    public static void cipherFunction(String func, Cipher c, String name) {
        switch (func) {
        case "--em":
        	InputStream stream = new ByteArrayInputStream(name.getBytes(StandardCharsets.ISO_8859_1));
        	c.encrypt(stream, outputFunction);
        	break;
        case "--ef":
        	try {
            	String out = "encrypted_" + name;
            	FileOutputStream f = writeOutput(out);
				c.encrypt(openInput(name), f);
			} catch (FileNotFoundException e) {
				System.out.println("Could not find the specified file.");
				System.out.println("Error message: " + e);
			} catch (IOException e) {
				System.out.println("Error message: " + e);
			}
            break;
        case "--dm":
        	c.decrypt(name);
            break;
        case "--df":
        	try {
				c.decrypt(openInput(name), outputFunction);
			} catch (FileNotFoundException e) {
				System.out.println("Could not find the specified file.");
				System.out.println("Error message: " + e);
			}
            break;
        }
    }

    /**
     * Read in remaining output commands and performs specified tasks
     * 
     * @param args
     *            String array of commands
     * @param c
     *            Cipher that has already been created
     * @param start
     *            Index to begin processing arguments at
     */
    public static void outputOptions(String[] args, Cipher c, int start) {
    	
        if (args.length > start) {
            for (int i = start; i < args.length; i++) {
                switch (args[i]) {
                case "--print":
                    // Print result of applying the cipher to the console
                	outputFunction = System.out;
                	cipherFunction(args[start-2], c, args[start-1]);
                	System.out.println(c.accessCipherText());
                    break;
                case "--out":
                    // TODO Print result of applying the cipher to a file
                	try {
						outputFunction = new FileOutputStream(args[start+1]);
	                	cipherFunction(args[start-2], c, args[start-1]);
	                	
					} catch (FileNotFoundException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

//                	try {
//                		System.out.println(c.accessCipherText());
//						writeToFile(args[i+1], c.accessCipherText());
//					} catch (IOException e1) {
//						System.out.println("Error in out");
//						e1.printStackTrace();
//					}
                    break;
                case "--save":
                    // TODO Save current cipher to a file
                	System.out.println("Saving");
                	try {
						writeToFile(args[start+1], c.accessKey());
					} catch (IOException e) {
						System.out.println("Saving error");
						e.printStackTrace();
					}
                    break;
                case "--savePu":
                    // TODO If the current cipher is RSA, save the public key to
                    // a file
                	try {
						writeToFile(args[start+1], c.accessKey());
					} catch (IOException e) {
						System.out.println("Error message: " + e);
						e.printStackTrace();
					}
                    break;
                }
            }
        }
    }
    
    /**
     * Gathers all of the data held in the -t and -c flags
     * @param args array of all the file locations and cipher commands
     * @return String array of the concanteted strings. 0th index is -t, 1st is -c.
     */
    public static String[] crackCaesar(String[] args) {
    	String[] sArr = {"", ""};
    	for(int i = 0; i < args.length; i++){
	    	if (args[i].equals("-t")) {
	    		sArr[0] = sArr[0] + readFile(args[i+1]);
	    	}
	    	else if(args[i].equals("-c")){
	    		sArr[1] = sArr[1] + readFile(args[i+1]);
	    	}
    	}
    	return sArr;
    }

    /**
     * Read in command line functions and perform the specified actions
     * 
     * @param args
     *            String array of command line commands
     */
    public static void main(String[] args) {   
    	outputFunction = System.out;
    	System.out.println("Hi");
    	try {
			outputFunction.write("hi".getBytes());
			outputFunction.flush();
		} 
    	catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("failed");
		}
    	
    	
    	CipherFactory c = new CipherFactory();
        switch (args[0]) {
        case "--monosub":
            // TODO Create a random substitution cipher object with the given
            // alphabet
        	outputOptions(args, c.getMonoCipher(readFile(args[1])), 4);
            break;
        case "--caesar":
            // Create a Caesar cipher object with the given shift parameter
        	System.out.println("Caesar Called");
        	outputOptions(args, c.getCaesarCipher(Integer.parseInt(args[1])), 4);
            break;
        case "--random":
            // Create a random substitution cipher object
        	outputOptions(args, c.getRandomSubstitutionCipher(), 3);
            break;
        case "--crackedCaesar":
            // TODO decrypt the given file
        	String[] sArr = crackCaesar(args);
        	FrequencyAnalyzer sample = new FrequencyAnalyzer(sArr[0]);
        	FrequencyAnalyzer encrypted = new FrequencyAnalyzer(sArr[1]);
        	outputOptions(args, encrypted.getCipher(sample, encrypted), 3);
            break;
        case "--vigenere":
            // TODO Create a new Vigenere Cipher with the given key word
        	outputOptions(args, c.getVigenereCipher(args[1]), 4);
            break;
        case "--vigenereL":
            // TODO Create a new Vigenere Cipher with key word from given file
        	try {
				outputOptions(args, c.getVigenereCipher(openFile(args[1]).readLine()), 4);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            break;
        case "--rsa":
        	outputOptions(args, c.getRSACipher(), 3);
            break;
        case "--rsaPr":
        	BigInteger n = BigInteger.ZERO;
        	BigInteger pu = BigInteger.ZERO;
        	BigInteger pr = BigInteger.ZERO;
        	try {
        		BufferedReader data = openFile(args[1]);
				n = new BigInteger(data.readLine());
				pu = new BigInteger(data.readLine());
				pr = new BigInteger(data.readLine());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            // TODO Create an RSA encrypter/decrypter from private key in a file
        	outputOptions(args, c.getRSACipher(n, pu, pr), 4);
            break;
        case "--rsaPu":
            // TODO Create an encryption-only RSA cipher from public key in a
            // file
        	BigInteger base = BigInteger.ZERO;
        	BigInteger pub = BigInteger.ZERO;
        	try {
        		BufferedReader data = openFile(args[1]);
				base = new BigInteger(data.readLine());
				pub = new BigInteger(data.readLine());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("File not found.");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("There is an IOException. Your file failed to open.");
				e.printStackTrace();
			}
            // TODO Create an RSA encrypter/decrypter from private key in a file
        	outputOptions(args, c.getRSACipher(base, pub, null), 4);
            break;
        }
    }
}
