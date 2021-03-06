
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class CaesarCipherCrackV2 {

	public static void main(String[] args) throws IOException {		
		showInitialMessage();

		String encrypted = getUserInput(); // the encrypted text
		String[] decrypted = findAllSolutions(encrypted);
		String[] dicWords = readDictionary("google-10000-english.txt");

		crackTheCode(dicWords, decrypted);
	}

	public static void showInitialMessage() {
		System.out.println("CaesarCipherCrackV2 Copyright (C) 2017 Emil Sergiev GNU GPL 3.0");
		System.out.println("---------------------------------------------------------------");
		System.out.println("A tool to decode Caesar cipher encrypted English text");
		System.out.println("\nEnter the encrypted text bellow to be decoded:\n");
	}

	public static String getUserInput() {
		Scanner input = new Scanner(System.in);
		String encrypted = input.nextLine();
		encrypted = encrypted.toLowerCase().replaceAll("[^a-z ]", "");
		input.close();
		return encrypted;
	}

	public static String[] findAllSolutions(String encrypted) {
		String[] decrypted = new String[25]; // the 25 decoded versions
		for (byte i = 0; i < decrypted.length; i++) {
			decrypted[i] = decrypt(encrypted, (byte) (i+1));
		}
		return decrypted;
	}

	public static String[] readDictionary(String filename) throws IOException {
		String[] dicWords = new String[10000]; // the 10K English words from the dictionary
		File dic = new File(filename);
		FileInputStream fis = new FileInputStream(dic);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));

		for (int i = 0; i < dicWords.length; i++) {
			dicWords[i] = br.readLine();
		}
		br.close();
		return dicWords;
	}

	public static String decrypt(String encrypted, byte key) {
		char[] array = encrypted.toCharArray();

		for (int i = 0; i < array.length; i++) {
			char letter = array[i];

			if (letter != ' ') {
				letter = (char) (letter - key);			
				if (letter > 'z') {
					letter = (char) (letter - 26);
				}
				if (letter < 'a') {
					letter = (char) (letter + 26);
				}
			}
			array[i] = letter;
		}
		return new String(array);
	}

	public static byte findBestMatch(String[] decrypted, String[] dicWords) {
		int[] match = matchedWords(decrypted, dicWords);
		int max = match[0]; // the maximum # of matched words in each decoded version
		byte position = 0; // the position of the best match version which will be returned
		// find the most matches
		for (byte i = 1; i < match.length; i++) {
			if (match[i] > max) {
				max = match[i];
				position = i;
			}
		}
		return position;
	}

	public static int[] matchedWords(String[] decrypted, String[] dicWords) {
		int[] match = new int[decrypted.length]; // # of matched words in each decoded version
		for (byte i = 0; i < decrypted.length; i++) {
			String[] words = decrypted[i].split("\\s+");
			for (int j = 0; j < words.length; j++) {
				words[j] = words[j].replaceAll("[^\\w]", "");
				for (int x = 0; x < dicWords.length; x++) {
					if (words[j].equals(dicWords[x])) {
						match[i]++;
					}
				}
			}
		}		
		return match;
	}
	
	public static void crackTheCode(String[] dicWords, String[] decrypted) {
		byte bestMatch = findBestMatch(decrypted, dicWords);
		System.out.println("\n=======< Cracking the code >=======\n");
		System.out.println(decrypted[bestMatch]);
		System.out.println("\n============< The End >============\n");
	}

}
