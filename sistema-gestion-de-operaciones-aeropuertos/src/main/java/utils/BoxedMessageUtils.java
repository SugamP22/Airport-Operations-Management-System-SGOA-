package utils;

/**
 * class to create a box design in the console
 */
public class BoxedMessageUtils {

	/**
	 * i use this to center the message in the box calculating space
	 */
	public static void boxWithEvenSpacing(String message, String design) {
		int width = 50;
		String line = design.repeat(width);// to repeat the sign for exact number times

		int padding = (width - message.length()) / 2;// to put space both sides equally
		String spacing = " ".repeat(Math.max(0, padding));// to avoid getting space less then 0
		System.out.println(line);
		System.out.println(spacing + message);
		System.out.println(line);
	}

	/**
	 * i use this to put the message in the left side of the box without space
	 */
	public static void boxWithOutEvenSpacing(String message, String design) {
		int width = 50;
		String line = design.repeat(width);
		System.out.println(line);
		System.out.println(" " + message);
		System.out.println(line);
	}

	/**
	 * to give a horizontal row effectF
	 * 
	 * @param str
	 */

	public static void horizontalRow(String str) {
		String line = str.repeat(50);
		System.out.println(line);
	}

}
