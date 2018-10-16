package util;

import java.io.UnsupportedEncodingException;
import java.security.*;

/**
 * The Encryption interface from the util package.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 08.05.2016
 */
public class Encryption {

	/**
	 * This method will encrypt the password.
	 * 
	 * @param text
	 *            An Array of characters which will represent the password of
	 *            the user.
	 * @return An Array of characters that will represent the length of the
	 *         password.
	 */
	public static char[] encrypt(char[] text) {
		char[] result = new char[text.length];

		for (int i = 0; i < result.length; i++) {
			if ((int) text[i] <= 64)
				result[i] = (char) ((int) text[i] * 2 + 10);
			else
				result[i] = (char) ((int) text[i] - 64);
		}

		return result;
	}

	public static boolean areEqual(byte[] v1, byte[] v2) {
		if (v1.length != v2.length)
			return false;

		for (int i = 0; i < v1.length; i++)
			if (v1[i] != v2[i])
				return false;

		return true;
	}

	public static boolean areEqual(char[] v1, char[] v2) {
		if (v1.length != v2.length)
			return false;

		for (int i = 0; i < v1.length; i++)
			if ((int) v1[i] != (int) v2[i])
				return false;

		return true;
	}

}
