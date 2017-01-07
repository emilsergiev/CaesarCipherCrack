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

	public static void showWarranty() {
		// TODO Show the WARRANTY
		System.out.println("  THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY");
		System.out.println("APPLICABLE LAW.  EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT");
		System.out.println("HOLDERS AND/OR OTHER PARTIES PROVIDE THE PROGRAM \"AS IS\" WITHOUT WARRANTY");
		System.out.println("OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, BUT NOT LIMITED TO,");
		System.out.println("THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR");
		System.out.println("PURPOSE.  THE ENTIRE RISK AS TO THE QUALITY AND PERFORMANCE OF THE PROGRAM");
		System.out.println("IS WITH YOU.  SHOULD THE PROGRAM PROVE DEFECTIVE, YOU ASSUME THE COST OF");
		System.out.println("ALL NECESSARY SERVICING, REPAIR OR CORRECTION.");
	}
	public static void showConditions() {
		// TODO Show the TERMS AND CONDITIONS (or just point to them...)
		System.out.println("This program is free software: you can redistribute it and/or modify");
		System.out.println("it under the terms of the GNU General Public License as published by");
		System.out.println("the Free Software Foundation, either version 3 of the License, or");
		System.out.println("(at your option) any later version.");
		System.out.println("You should have received a copy of the GNU General Public License");
		System.out.println("along with this program.  If not, see http://www.gnu.org/licenses");
	}
	
	public static void extractWordsIntoArray(String[] dicWords) throws IOException {
		// TODO Extract the words from the dictionary file into the dictionary array
		File dir = new File("."); //use . to get same directory
		// the dictionary file
		File dic = new File(dir.getCanonicalPath() + File.separator + "google-10000-english.txt");				
		// construct FileInputStream from the dictionary file
		FileInputStream fis = new FileInputStream(dic);
		// construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		// populate the dictionary word array
		for (int i = 0; i < dicWords.length; i++) {
			dicWords[i] = br.readLine();
		}
		br.close();
	}
	
	public static void populateDecryptedArray(String[] decrypted, String encrypted) {
		// TODO Populate the array of decoded versions
		for (byte i = 0; i < decrypted.length; i++) {
			decrypted[i] = decrypt(encrypted, (byte) (i+1));
		}
	}
	
	public static String decrypt(String encrypted, byte key) {
		// TODO Crack the code with a given key (distance)
		char[] array = encrypted.toCharArray();

		for (int i = 0; i < array.length; i++) {
			char letter = array[i];

			if (letter != ' ') { // leaves spaces as spaces
				letter = (char) (letter - key);			
				if (letter > 'z') letter = (char) (letter - 26);
				if (letter < 'a') letter = (char) (letter + 26);
			}
			array[i] = letter;
		}
		return new String(array); // return the array as a string
	}
	
	public static byte findBestMatch(String[] decrypted, String[] dicWords) {
		// TODO Find the index position of the best match version and return it
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
	
	public static void main(String[] args) throws IOException {
		// TODO The main method goes here
		Scanner input = new Scanner(System.in);
		System.out.println("CaesarCipherCrack Copyright (C) 2017 Emil Sergiev");
		System.out.println("-------------------------------------------------");
		System.out.println("A tool to decode text which has been encrypted with the Caesar Cipher");
		System.out.println(" ___________________________________________________________________________");
		System.out.println("|                                                                           |");
		System.out.println("| This program comes with ABSOLUTELY NO WARRANTY; for details type `show w'.|");
		System.out.println("| This is free software, and you are welcome to redistribute it             |");
		System.out.println("| under certain conditions; type `show c' for details.                      |");
		System.out.println("|___________________________________________________________________________|");
		String terms = input.nextLine();
		if (terms.equals("show w")) showWarranty(); // print out warranty 
		if (terms.equals("show c")) showConditions(); // print out conditions
		System.out.println("\nEnter the encrypted text bellow to be decoded:\n");
		String encrypted = input.nextLine(); encrypted = encrypted.toLowerCase();
		String[] decrypted = new String[25]; // the 25 decoded versions
		String[] dicWords = new String[10000]; // the 10000 English words from the dictionary
		// extracts the words from the dictionary file into a word array
		extractWordsIntoArray(dicWords);
		// populate the array of decoded versions
		populateDecryptedArray(decrypted, encrypted);
		// find the best match version's index number
		byte bestMatch = findBestMatch(decrypted, dicWords);
		// print out the final result
		System.out.println("\n=======< Cracking the code >=======\n");
		System.out.println(decrypted[bestMatch]);
		// close the scanner and exit the program
		input.close();
	}

}
