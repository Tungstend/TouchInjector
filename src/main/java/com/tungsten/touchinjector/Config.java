/*
 * Copyright (C) 2022  Haowei Wen <yushijinhun@gmail.com> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.tungsten.touchinjector;

import static com.tungsten.touchinjector.util.Logging.Level.*;
import static com.tungsten.touchinjector.util.Logging.log;

public final class Config {

	private Config() {}

	public static boolean verboseLogging;
	public static boolean touchLogging;
	public static boolean printUntransformedClass;
	public static boolean dumpClass;

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

	public static void init() {
		initDebugOptions();
	}
}