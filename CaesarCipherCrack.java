/*******************************************************************************
 *    CaesarCipherCrack is a tool for decoding Caesar Cipher encrypted text.   *
 *    Copyright (C) 2017  Emil Sergiev <http://www.emil.free.bg>               *
 *                                                                             *
 *    This program is free software: you can redistribute it and/or modify     *
 *    it under the terms of the GNU General Public License as published by     *
 *    the Free Software Foundation, either version 3 of the License, or        *
 *    (at your option) any later version.                                      *
 *                                                                             *
 *    This program is distributed in the hope that it will be useful,          *
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of           *
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the            *
 *    GNU General Public License for more details.                             *
 *                                                                             *
 *    You should have received a copy of the GNU General Public License        *
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.    *
 *******************************************************************************/

import java.util.Arrays;
import java.util.Scanner;

public class CaesarCipherCrack {
	
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
		System.out.println("along with this program.  If not, see <http://www.gnu.org/licenses/>.");
	}

	public static char findPopular(char[] original) {
		// TODO Find the most popular char in the original text
		char sorted[] = getNewSorted(original);
		char previous = sorted[0];
		char popular = sorted[0];
		int count = 1;
		int maxCount = 1;

		for (int i = 1; i < sorted.length; i++) {
			if (sorted[i] == previous)
				count++;
			else {
				if (count > maxCount) {
					popular = sorted[i-1];
					maxCount = count;
				}
				previous = sorted[i];
				count = 1;
			}
		}
		if (count > maxCount) return sorted[sorted.length-1];
		else return popular;
	}

	public static char[] getNewSorted(char[] original) {
		// TODO Create new sorted array without the spaces and return it
		int newLength = 0; // the length for the new sorted array
		for (int i = 0; i < original.length; i++) {
			if (original[i] != ' ')
				newLength++;
		}
		char sorted[] = new char[newLength]; // the new sorted array
		int index = 0; // index positions for the sorted array
		for (int i = 0; i < original.length; i++) {
			if (original[i] != ' ') {
				sorted[index] = original[i];
				index++;
			}
		}
		Arrays.sort(sorted); // final sorting of the sorted array
		return sorted;
	}

	public static void setCommonKeys(char mostPopular, byte[] commonKey) {
		// TODO Set the 3 most common keys -- e, t and (h or a)
		if (mostPopular != 'e') commonKey[0] = findKey(mostPopular, 'e');
		else commonKey[0] = findKey(mostPopular, 't');

		if (mostPopular != 't' && mostPopular != 'e')
			commonKey[1] = findKey(mostPopular, 't');
		else
			commonKey[1] = findKey(mostPopular, 'h');

		if (mostPopular != 'h' && mostPopular != 't' && mostPopular != 'e')
			commonKey[2] = findKey(mostPopular, 'h');
		else
			commonKey[2] = findKey(mostPopular, 'a');
	}

	public static byte findKey(char popular, char common) {
		// TODO Find and return the key (the distance between the popular and the common)
		byte key; // the key that we will return
		char start = 'a'; // start from a to z
		byte popPos = 0; // the position of the most popular char in the text
		byte comPos = 0; // the position of the most common letters in English (e, t, h, or a)

		for (byte i = 1; i < 27; i++) {
			if (start == popular) popPos = i;
			if (start == common) comPos = i;
			start = (char) (97 + i); // 97 = 'a'
		}

		key = (byte) (popPos - comPos);
		if (key < 0) key = (byte) (key + 26);		
		return key;
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

	public static void main(String[] args) {
		// TODO The main method
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
		if (terms.equals("show w")) showWarranty();
		if (terms.equals("show c")) showConditions();
		System.out.println("\nEnter the encrypted text bellow to be decoded:\n");
		String encrypted = input.nextLine(); encrypted = encrypted.toLowerCase();
		char[] charArray = encrypted.toCharArray();
		char mostPopular = findPopular(charArray); // the most popular char
		byte[] commonKey = new byte[3]; // the 3 most common keys

		setCommonKeys(mostPopular, commonKey); // set the 3 most common keys

		/******* Attempt the crack with the 3 most common keys *******/
		System.out.println("\nTesting with the 3 common keys: " 
				+ Arrays.toString(commonKey));
		System.out.println("--------------------------------------------");
		for (int i = 0; i < commonKey.length; i++) {
			System.out.println
			("Key " + commonKey[i] + ": " + decrypt(encrypted, commonKey[i]) + "\n");
		}
		System.out.println("============================================\n");

		/******* Choice to exit or do a brute force attack *******/
		System.out.println("Not satisfied with the results???");
		System.out.print
		("Enter 1 to exit or 2 to see the results with the rest of the 22 keys: ");
		byte choice = input.nextByte();

		while (true) {
			if (choice == 1) {
				System.out.println("\nHave a nice day :)");
				break;
			} else if (choice == 2) {
				System.out.println("\nBrute force attack with the rest of the 22 keys:");
				System.out.println("------------------------------------------------");
				for (byte i = 1; i < 26; i++) {
					if (commonKey[0] != i && commonKey[1] != i && commonKey[2] != i)
						System.out.println("Key " + i + ": " + decrypt(encrypted, i) + "\n");
				}
				System.out.println("================= the end =================");
				break;
			} else {
				System.out.print("Please enter 1 or 2 only! ");
				choice = input.nextByte();
			}
		}
		input.close();
	}

}
