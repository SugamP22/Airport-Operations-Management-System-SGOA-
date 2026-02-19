package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * I use this class to create table to show data
 */
public class TablePrinter {

	private final List<String[]> rows = new ArrayList<>();
	private String[] headers;

	public TablePrinter headers(String... headers) {
		this.headers = headers;
		return this;
	}

	public TablePrinter row(String... values) {// Enrique porfavor que utiliza Java 25 el ... te acepta parametros
												// variados(diffrent quantity)
		rows.add(values);
		return this;
	}

	public void print() {
		if (headers == null || headers.length == 0) {
			throw new IllegalStateException(LanguageUtils.get("error.table.headers.required"));
		}

		int cols = headers.length;
		int[] widths = new int[cols];

		// header widths
		for (int i = 0; i < cols; i++) {
			widths[i] = headers[i] == null ? 0 : headers[i].length();
		}

		// row widths
		for (String[] row : rows) {
			for (int i = 0; i < cols; i++) {
				String val = i < row.length && row[i] != null ? row[i] : "";
				widths[i] = Math.max(widths[i], val.length());
			}
		}

		// print
		printSeparator(widths);
		printLine(headers, widths);
		printSeparator(widths);
		for (String[] row : rows) {
			printLine(row, widths);
		}
		printSeparator(widths);
	}

	private void printSeparator(int[] widths) {
		StringBuilder sb = new StringBuilder();
		sb.append("+");
		for (int w : widths) {
			sb.append("-".repeat(w + 2)).append("+");
		}
		System.out.println(sb);
	}

	private void printLine(String[] values, int[] widths) {
		StringBuilder sb = new StringBuilder();
		sb.append("|");
		for (int i = 0; i < widths.length; i++) {
			String v = (i < values.length && values[i] != null) ? values[i] : "";
			sb.append(" ").append(padRight(v, widths[i])).append(" |");
		}
		System.out.println(sb);
	}

	private String padRight(String text, int width) {
		if (text.length() >= width)
			return text;
		return text + " ".repeat(width - text.length());
	}
}