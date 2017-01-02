import java.util.Arrays;
import java.util.Scanner;

public class CaesarCipherCrack {

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
		int sortedLength = 0; // the length for the new sorted array
		for (int i = 0; i < original.length; i++) {
			if (original[i] != ' ')
				sortedLength++;
		}
		char sorted[] = new char[sortedLength]; // the new sorted array
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
		if (mostPopular != 'e')
			commonKey[0] = findKey(mostPopular, 'e');
		else
			commonKey[0] = findKey(mostPopular, 't');

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
				if (letter > 'z')
					letter = (char) (letter - 26);
				if (letter < 'a')
					letter = (char) (letter + 26);
			}
			array[i] = letter;
		}
		return new String(array); // return the array as a string
	}

	public static void main(String[] args) {
		// TODO The main method
		System.out.println("Welcome to CaesarCipherCrack - by Emil Nomel");
		System.out.println("--------------------------------------------");
		System.out.println("A tool to decode text which has been encrypted with the Caesar Cipher");
		System.out.println("=====================================================================");
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the encrypted text bellow to be decoded:\n");
		String encrypted = input.nextLine();
		encrypted = encrypted.toLowerCase();
		char[] charArray = encrypted.toCharArray();
		char mostPopular = findPopular(charArray); // the most popular char in the encrypted text
		byte[] commonKey = new byte[3]; // the 3 most common keys

		setCommonKeys(mostPopular, commonKey); // set the 3 most common keys

		/******* Attempt the crack with the 3 most common keys *******/
		System.out.println("\nTesting with the 3 common keys: " + Arrays.toString(commonKey));
		System.out.println("--------------------------------------------");
		for (int i = 0; i < commonKey.length; i++) {
			System.out.println("Key " + commonKey[i] + ": " + decrypt(encrypted, commonKey[i]) + "\n");
		}
		System.out.println("============================================\n");
		
		/******* Choice to exit or do a brute force attack *******/
		System.out.println("Not satisfied with the results???");
		System.out.print("Enter 1 to exit or 2 to see the results with the rest of the 22 keys: ");
		byte choice = input.nextByte();

		while (true) {
			if (choice == 1) {
				System.out.println("Have a nice day :)");
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
