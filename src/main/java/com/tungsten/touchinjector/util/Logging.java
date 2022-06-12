package com.tungsten.touchinjector.util;

import com.tungsten.touchinjector.Config;

import static com.tungsten.touchinjector.util.Logging.Level.WARNING;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.regex.Pattern;

public final class Logging {
	private Logging() {}

	private static final Pattern CONTROL_CHARACTERS_FILTER = Pattern.compile("[\\p{Cc}&&[^\r\n\t]]");

	private static final PrintStream out = System.err;
	private static final FileChannel logfile = openLogFile();

	private static FileChannel openLogFile() {
		if (System.getProperty("touchinjector.noLogFile") != null) {
			log(Level.INFO, "Logging to file is disabled");
			return null;
		}

		Path logfilePath = Paths.get("touch-injector.log").toAbsolutePath();
		try {
			FileChannel channel = FileChannel.open(logfilePath, CREATE, WRITE);
			if (channel.tryLock() == null) {
				log(WARNING, "Couldn't lock log file [" + logfilePath + "]");
				return null;
			}
			channel.truncate(0);
			String logHeader = "Logging started at " + Instant.now() + System.lineSeparator();
			channel.write(Charset.defaultCharset().encode(logHeader));
			log(Level.INFO, "Logging file: " + logfilePath);
			return channel;
		} catch (IOException e) {
			log(WARNING, "Couldn't open log file [" + logfilePath + "]");
			return null;
		}
	}

	public static enum Level {
		DEBUG, INFO, WARNING, ERROR;
	}

	public static void log(Level level, String message) {
		log(level, message, null);
	}

	public static void log(Level level, String message, Throwable e) {
		if (level == Level.DEBUG && !Config.verboseLogging) {
			return;
		}
		String log = "[touch-injector] [" + level + "] " + message;
		if (e != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			pw.println();
			e.printStackTrace(pw);
			pw.close();
			log += sw.toString();
		}
		// remove control characters to prevent messing up the console
		log = CONTROL_CHARACTERS_FILTER.matcher(log).replaceAll("");
		out.println(log);

		if (logfile != null) {
			try {
				logfile.write(Charset.defaultCharset().encode(log + System.lineSeparator()));
			} catch (IOException ex) {
				out.println("[touch-injector] [ERROR] Error writing to log file: " + ex);
			}
		}
	}
}