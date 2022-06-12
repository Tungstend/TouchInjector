package com.tungsten.touchinjector;

import static com.tungsten.touchinjector.util.Logging.Level.*;
import static com.tungsten.touchinjector.util.Logging.log;

public final class Config {

	private Config() {}

	public static boolean verboseLogging;
	public static boolean touchLogging;
	public static boolean printUntransformedClass;
	public static boolean dumpClass;
	public static boolean regardLiquidAsBlock;

	private static void initDebugOptions() {
		String prop = System.getProperty("touchinjector.debug");
		if (prop == null) {
			// all disabled if param not specified
		} else if (prop.isEmpty()) {
			verboseLogging = true;
			touchLogging = true;
		} else {
			for (String option : prop.split(",")) {
				switch (option) {
					case "verbose":
						verboseLogging = true;
						break;
					case "touch":
						touchLogging = true;
						break;
					case "printUntransformed":
						printUntransformedClass = true;
						verboseLogging = true;
						break;
					case "dumpClass":
						dumpClass = true;
						break;
					default:
						log(ERROR, "Unrecognized debug option: " + option);
						throw new InitializationException();
				}
			}
		}
	}

	static void init() {
		initDebugOptions();

		regardLiquidAsBlock = System.getProperty("touchinjector.regardLiquidAsBlock") != null;
	}
}