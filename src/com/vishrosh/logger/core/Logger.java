package com.vishrosh.logger.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This a class that can be used for logging information to the console
 * and/or a log file. This class follows the Singleton design pattern, therefore the class
 * cannot be instanced. In order the obtain the instance,
 * the method, {@link #getLogger(Class)} and/or {@link #getCurrentLogger()} can be used.
 * 
 * <br><br>
 * The logger currently contains three types of logs, {@link Level}.
 * <br><br>
 * 
 * A normal log follows the following format:<br>
 * [Date Time][The class package/loggerName][Title/source] Level: Message
 * <br>
 * Example: [29/10/2050 18:34:23][com.vishrosh.logger.test.Main.java][Test] Minor Error: This is a test message
 * 
 * @version 1.0.2
 * @author VishRosh001
 *
 */
public class Logger {
	
	private static Logger logger = null;
	private static String loggerName = null;
	private String source = "Unknown";
	private Level level = Level.INFO;
	private LocalDateTime date = null;
	private boolean isDateTimePrinted = true;
	private boolean isLoggerNamePrinted = true;
	
	public static final String YELLOW = "\u001B[33m";
	
	private Logger() {}
	
	/**
	 * Setter for source
	 * @param sourceName
	 */
	public void setSource(String sourceName) {
		Logger.getCurrentLogger().source = sourceName;
	}
	
	/**
	 * Setter for level
	 * @param level 
	 */
	public void setLevel(Level level) {
		Logger.getCurrentLogger().level = level;
	}
	
	/**
	 * 
	 * Gets the instance of logger <br>
	 * This method should only be used after the method, {@link #getLogger(Class)}, has been called.
	 * 
	 * @return instance of logger
	 */
	public static Logger getCurrentLogger() {
		return Logger.logger;
	}
	
	/**
	 * 
	 * @return the current level of the logger
	 */
	public Level getCurrentLevel() {
		return Logger.getCurrentLogger().level;
	}
	
	/**
	 * 
	 * Gets the instance of logger, it returns a new instance of logger if an instance doesn't exist
	 * 
	 * @param clazz 
	 * @return instance of logger
	 */
	@SuppressWarnings("rawtypes")
	public static Logger getLogger(Class clazz) {
		if(Logger.logger == null)Logger.logger = new Logger();
		Logger.getCurrentLogger().setLoggerName(clazz);
		return Logger.logger;
	}
	
	/**
	 * 
	 * Logs the given message using existing settings
	 * 
	 * @param message
	 */
	public void log(String message) {
		Logger.getCurrentLogger().log(Logger.getCurrentLogger().level, Logger.getCurrentLogger().source, message);
	}
	
	
	/**
	 * 
	 * Logs the given message using the given source and existing settings.<br>
	 * Note: The method updates the instance's source to the given source.
	 * 
	 * @param source
	 * @param message
	 */
	public void log(String source, String message) {
		Logger.getCurrentLogger().log(Logger.getCurrentLogger().level, source, message);
	}
	
	/**
	 * 
	 * Logs the given message using the given level and existing settings.<br>
	 * Note: The method updates the instance's level to the given level.
	 * 
	 * @param level
	 * @param message
	 */
	public void log(Level level, String message) {
		Logger.getCurrentLogger().log(level, Logger.getCurrentLogger().source, message);
	}
	
	/**
	 * 
	 * Logs the given message using the given level and source.<br>
	 * Note: The method updates the instance's level and source to the given level and source.
	 * 
	 * @param level
	 * @param source
	 * @param message
	 */
	public void log(Level level, String source, String message) {
		if(level == Level.FATAL) {
			Logger.getCurrentLogger().logFatal(source, message);
		}else if(level == Level.MINOR) {
			Logger.getCurrentLogger().logMinor(source, message);
		}else {
			Logger.getCurrentLogger().logInfo(source, message);
		}
	}
	
	/**
	 * 
	 * Logs the given error using the given source.<br>
	 * Note: This method updates the instance's level and source to {@link Level#MINOR} and the given source. 
	 * 
	 * @param source
	 * @param error
	 */
	public void logMinor(String source, String error) {
		System.err.print(Logger.getCurrentLogger().getLog(Level.MINOR, source, error));
	}
	
	/**
	 * 
	 * Logs the given error using the given source.<br>
	 * Note: This method updates the instance's level and source to {@link Level#FATAL} and the given source. 
	 * 
	 * @param source
	 * @param error
	 */
	public void logFatal(String source, String error) {
		System.err.print(Logger.getCurrentLogger().getLog(Level.FATAL, source, error));
		System.exit(1);
	}
	
	/**
	 * 
	 * Logs the given info using the given source.<br>
	 * Note: This method updates the instance's level and source to {@link Level#INFO} and the given source. 
	 * 
	 * @param source
	 * @param info
	 */
	public void logInfo(String source, String info) {
		System.out.print(Logger.getCurrentLogger().getLog(Level.INFO, source, info));
	}
	
	private String getLog(Level level, String source, String message) {
		DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		
		String errorType = level == Level.FATAL ? "Fatal Error" : "Info";
		if(errorType == "Info" && level != Level.INFO) errorType="Minor Error";
		Logger.getCurrentLogger().setLoggerValues(level, source);
		
		StringBuffer a = new StringBuffer("");
		if(Logger.getCurrentLogger().isDateTimePrinted) {
			date = LocalDateTime.now();
			a.append(Logger.formatStringWithBrackets(date.format(formatDate)));
		}
		
		if(Logger.getCurrentLogger().isLoggerNamePrinted)a.append(Logger.formatStringWithBrackets(Logger.loggerName));
		
		a.append(Logger.formatStringWithBrackets(Logger.getCurrentLogger().source));
		a.append(String.format(" %s: %s%n", errorType, message));
		return a.toString();
	}
	
	/**
	 * 
	 * @param isDateTimePrinted should the date/time printed to the console.
	 */
	public void setIsDateTimePrinted(boolean isDateTimePrinted) {
		Logger.getCurrentLogger().isDateTimePrinted = isDateTimePrinted;
		if(!isDateTimePrinted)Logger.getCurrentLogger().date = null;
	}
	
	/**
	 * 
	 * @param isLoggerNamePrinted should the package of the class be printed.
	 */
	public void setIsLoggerNamePrinted(boolean isLoggerNamePrinted) {
		Logger.getCurrentLogger().isLoggerNamePrinted = isLoggerNamePrinted;
	}
	
	/**
	 * 
	 * Setter for the loggerName
	 * 
	 * @param clazz 
	 */
	@SuppressWarnings("rawtypes")
	public void setLoggerName(Class clazz) {
		Logger.loggerName = clazz.getName() + ".java";
	}
	
	private static String formatStringWithBrackets(String str) {
		return String.format("[%s]", str);
	}
	
	private void setLoggerValues(Level level, String source) {
		Logger.getCurrentLogger().setLevel(level);
		Logger.getCurrentLogger().setSource(source);
	}
}
