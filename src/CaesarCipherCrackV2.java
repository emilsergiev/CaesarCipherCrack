/*******************************************************************************
 *   CaesarCipherCrackV2 is a tool for decoding Caesar Cipher encrypted text.  *
 *   Copyright (C) 2017  Emil Sergiev <http://www.emil.free.bg>                *
 *                                                                             *
 *   This program is free software: you can redistribute it and/or modify      *
 *   it under the terms of the GNU General Public License as published by      *
 *   the Free Software Foundation, either version 3 of the License, or         *
 *   (at your option) any later version.                                       *
 *                                                                             *
 *   This program is distributed in the hope that it will be useful,           *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of            *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             *
 *   GNU General Public License for more details.                              *
 *                                                                             *
 *   You should have received a copy of the GNU General Public License         *
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.     *
 *******************************************************************************/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class CaesarCipherCrackV2 {
	
	public static void showInitialMessage() {
		System.out.println("CaesarCipherCrackV2 Copyright (C) 2017 Emil Sergiev GNU GPL 3.0");
		System.out.println("---------------------------------------------------------------");
		System.out.println("A tool to decode Caesar cipher encrypted English text");
		System.out.println("\nEnter the encrypted text bellow to be decoded:\n");
	}
	
	public static String getUserInput() {
		Scanner input = new Scanner(System.in);
		String encrypted = input.nextLine();
		encrypted = encrypted.toLowerCase();
		input.close();
		return encrypted;
	}
	
	public static void extractWordsIntoArray(String[] dicWords) throws IOException {
		File dir = new File("."); // use . to get same directory
		File dic = new File(dir.getCanonicalPath() + File.separator + "google-10000-english.txt");
		FileInputStream fis = new FileInputStream(dic);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		
		for (int i = 0; i < dicWords.length; i++) {
			dicWords[i] = br.readLine();
		}
		br.close();
	}
	
	public static void populateDecryptedArray(String[] decrypted, String encrypted) {
		for (byte i = 0; i < decrypted.length; i++) {
			decrypted[i] = decrypt(encrypted, (byte) (i+1));
		}
	}
	
	public static String decrypt(String encrypted, byte key) {
		char[] array = encrypted.toCharArray();
		
		for (int i = 0; i < array.length; i++) {
			char letter = array[i];

			if (letter != ' ') {
				letter = (char) (letter - key);			
				if (letter > 'z') letter = (char) (letter - 26);
				if (letter < 'a') letter = (char) (letter + 26);
			}
			array[i] = letter;
		}
		return new String(array);
	}
	
	public static byte findBestMatch(String[] decrypted, String[] dicWords) {
		char[] match = new char[decrypted.length]; // # of matched words in each decoded version
		int max = match[0]; // the maximum # of matched words in each decoded version
		byte index = 0; // the index position of the best match version which will be returned
		// populate the match array with number of matches
		for (byte i = 0; i < decrypted.length; i++) {
			String[] words = decrypted[i].split("\\s+");
			for (int j = 0; j < words.length; j++) {
			    words[j] = words[j].replaceAll("[^\\w]", ""); // replace non-word characters
			    for (int x = 0; x < dicWords.length; x++) {
			    	if (words[j].equals(dicWords[x])) {
			    		match[i]++;
			    	}
			    }
			}
		}
		// find the most matches
		for (byte i = 1; i < match.length; i++) {
			if (match[i] > max) {
				max = match[i];
				index = i;
			}
		}
		return index;
	}
	
	public static void crackTheCode(String[] dicWords, String[] decrypted) {
		byte bestMatch = findBestMatch(decrypted, dicWords);
		System.out.println("\n=======< Cracking the code >=======\n");
		System.out.println(decrypted[bestMatch]);
		System.out.println("\n============< The End >============\n");
	}

	
	public static void main(String[] args) throws IOException {		
		showInitialMessage();
		
		String encrypted = getUserInput(); // the encrypted text
		String[] decrypted = new String[25]; // the 25 decoded versions
		String[] dicWords = new String[10000]; // the 10K English words from the dictionary
		
		populateDecryptedArray(decrypted, encrypted);
		
		extractWordsIntoArray(dicWords);
		
		crackTheCode(dicWords, decrypted);
	}

}
