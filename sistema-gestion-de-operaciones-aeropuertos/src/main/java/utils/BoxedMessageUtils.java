package utils;

public class BoxedMessageUtils {
	public static void boxWithEvenSpacing(String message, String design) {
		int width = 50;
		String line = design.repeat(width);

		int padding = (width - message.length()) / 2;// to put space both sides equally
		String spacing = " ".repeat(Math.max(0, padding));// to avoid getting space less then 0
		System.out.println(line);
		System.out.println(spacing + message);
		System.out.println(line);
	}

	public static void boxWithOutEvenSpacing(String message, String design) {
		int width = 50;
		String line = design.repeat(width);
		System.out.println(line);
		System.out.println(" " + message);
		System.out.println(line);
	}

	public static void horizontalRow(String str) {
		String line = str.repeat(50);
		System.out.println(line);
	}

}
