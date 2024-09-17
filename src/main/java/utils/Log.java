package utils;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.lf5.LogLevel;

import java.io.File;

/**
 * Exposes methods to initialize logger which would write logs to standard I/O &
 * Fitnesse
 */
public class Log {

	static Logger logInstance = Logger.getLogger("file");
	// public static String appendCharacter = "";

	public static void Message(String message, LogLevel logLevel) {
		Long threadId = Thread.currentThread().getId();
		/*
		 * if (!appendCharacter.equals("")) { message = appendCharacter +
		 * message; }
		 */

		message = "[Thread Id:" + threadId + "] " + message;
		switch (logLevel.toString()) {
			case "ERROR":
				logInstance.error(message);
				break;
			case "INFO":
				logInstance.info(message);
				break;
			case "DEBUG":
				logInstance.debug(message);
				break;
		}
	}

	public static void errorMessage(String message) {
		Long threadId = Thread.currentThread().getId();
		String lines = "\n-------------------------------------------------------------------------------------------------------\n";
		message = lines + "[Thread Id:" + threadId + "] " + message + lines;
		logInstance.error(message);
	}

	/**
	 * Close logger & delete all log files.
	 */
	public static void cleanUp() {
		File[] logFiles = FileUtil.findFilesWithExtension(".", "log");
		FileUtil.deleteFiles(logFiles);
	}

	public static String getLogFilePath() {
		// FileAppender appender = (FileAppender)
		// logInstance.getAppender("rootLogger");
		Logger logger = Logger.getLogger("rootLogger"); // Defining the Logger
		FileAppender appender = (FileAppender) logger.getRootLogger().getAppender("file");
		String directory = "";
		File file = new File(appender.getFile());
		if (null != file) {
			directory = file.getPath();
			Log.Message("Log directory path is: " + directory, LogLevel.INFO);
		}
		return directory;
	}
}