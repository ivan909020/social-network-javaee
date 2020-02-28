package ua.ivan909020.app.util;

public final class NumberUtils {

	private NumberUtils() {
	}

	public static int parseInt(String string) {
		int result;
		try {
			result = Integer.parseInt(string);
		} catch (NumberFormatException e) {
			result = 0;
		}
		return result;
	}

	public static int parseIntFromPath(String path) {
		int result;
		try {
			result = parseInt(path.split("/")[1]);
		} catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
			result = 0;
		}
		return result;
	}

}
