package com.medlinchristopher.picturetoascii.util;

/**
 * Contains various static methods which utilize System.getProperty() to determine user's operating system.
 *
 * @author Christopher Medlin
 * @author Ivan Kenevich
 * @version 1.0
 * @since 0.7
*/
public class OSUtils {

	/**
	 * Checks if current user's OS is Windows based.
	 *
	 * @return Boolean value repesenting whether or not user's OS is Windows based.
	 *
	*/
	public static boolean isWindows () {
		if (System.getProperty("os.name").startsWith("Windows")) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Checks if current user's OS is Unix/Linux based.
	 *
	 * @return Boolean value representing whether or not user's OS is Unix or Linux based.
	 *
	*/
	public static boolean isUnixOrLinux () {
		String os = System.getProperty("os.name");
		if (os.startsWith("Linux") || os.startsWith("Unix")) {
			return true;
		}
		else {
			return false;
		}
	}
}
				
