
import java.util.Arrays;
import java.util.Scanner;

public class CaesarCipherCrack {

	public static void main(String[] args) {
		showInitialMessage();

		Scanner input = new Scanner(System.in);
		String encrypted = getUserInput(input); // the original encrypted text
		char[] charArray = encrypted.toCharArray(); // the encrypted text converted to char array
		char mostPopular = findPopular(charArray); // the most popular char
		byte[] commonKey = findPopularKeys(mostPopular); // the 3 most common keys

		attemptCommonCrack(encrypted, commonKey);

		quitOrBruteForce(encrypted, commonKey, input);	

		input.close();
	}

	public static void showInitialMessage() {
		System.out.println("CaesarCipherCrackV2 Copyright (C) 2017 Emil Sergiev GNU GPL 3.0");
		System.out.println("---------------------------------------------------------------");
		System.out.println("A tool to crack and decode Caesar cipher encrypted text");
		System.out.println("\nEnter the encrypted text bellow to be decoded:\n");
	}

	public static String getUserInput(Scanner input) {
		String encrypted = input.nextLine();
		encrypted = encrypted.toLowerCase();
		return encrypted;
	}

	public static char findPopular(char[] original) {
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
		int newLength = 0; // the length for the new array to be sorted
		for (int i = 0; i < original.length; i++) {
			if (original[i] != ' ')
				newLength++;
		}
		char sorted[] = new char[newLength]; // the new array to be sorted
		int index = 0; // index positions for the new array to be sorted
		for (int i = 0; i < original.length; i++) {
			if (original[i] != ' ') {
				sorted[index] = original[i];
				index++;
			}
		}
		Arrays.sort(sorted);
		return sorted;
	}

	public static byte[] findPopularKeys(char mostPopular) {
		byte[] commonKey = new byte[3]; // the 3 most common keys

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

		return commonKey;
	}

	public static byte findKey(char popular, char common) {
		byte key; // the key (offset) that will be returned
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

	public static void attemptCommonCrack(String encrypted, byte[] commonKey) {
		System.out.println("\nTesting with the 3 common keys: " + Arrays.toString(commonKey));
		System.out.println("-------------------------------------------");
		for (int i = 0; i < commonKey.length; i++) {
			System.out.println("Key " + commonKey[i] + ": " + decrypt(encrypted, commonKey[i]));
		}
		System.out.println("\n===========================================\n");
	}

	public static void quitOrBruteForce(String encrypted, byte[] commonKey, Scanner input) {
		System.out.println("Not satisfied with the results???\n");
		System.out.println("For brute force attack enter 1");
		System.out.println("To exit the program enter 2\n");
		byte choice = input.nextByte();

		while (true) {
			if (choice == 1) {
				System.out.println("\nBrute force attack with the rest of the 22 keys:");
				System.out.println("------------------------------------------------");
				for (byte i = 1; i < 26; i++) {
					if (commonKey[0] != i && commonKey[1] != i && commonKey[2] != i)
						System.out.println("Key " + i + ": " + decrypt(encrypted, i) + "\n");
				}
				System.out.println("<<< the end >>>");
				break;
			} else if (choice == 2) {
				System.out.println("\nHave a nice day :)");
				break;
			} else {
				System.out.print("Please enter 1 or 2 only! ");
				choice = input.nextByte();
			}
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

}
