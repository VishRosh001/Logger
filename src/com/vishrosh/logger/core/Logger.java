package com.vishrosh.logger.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
	
	private static Logger logger = null;
	private static String loggerName = null;
	private String source = "Unknown";
	private Level level = Level.INFO;
	private LocalDateTime date = null;
	private boolean isDateTimePrinted = false;
	private boolean isLoggerNamePrinted = false;
	
	public static final String YELLOW = "\u001B[33m";
	
	private Logger() {}
	
	public void setSource(String sourceName) {
		Logger.getCurrentLogger().source = sourceName;
	}
	
	public void setLevel(Level level) {
		Logger.getCurrentLogger().level = level;
	}
	
	public static Logger getCurrentLogger() {
		return Logger.logger;
	}
	
	public Level getCurrentLevel() {
		return Logger.getCurrentLogger().level;
	}
	
	@SuppressWarnings("rawtypes")
	public static Logger getLogger(Class name) {
		if(Logger.logger == null)Logger.logger = new Logger();
		Logger.getCurrentLogger().setLoggerName(name);
		return Logger.logger;
	}
	
	
	public void log(String message) {
		Logger.getCurrentLogger().log(Logger.getCurrentLogger().level, Logger.getCurrentLogger().source, message);
	}
	
	public void log(String source, String message) {
		Logger.getCurrentLogger().log(Logger.getCurrentLogger().level, source, message);
	}
	
	public void log(Level level, String message) {
		Logger.getCurrentLogger().log(level, Logger.getCurrentLogger().source, message);
	}
	
	public void log(Level level, String source, String message) {
		if(level == Level.FATAL) {
			Logger.getCurrentLogger().logFatal(source, message);
		}else if(level == Level.MINOR) {
			Logger.getCurrentLogger().logMinor(source, message);
		}else {
			Logger.getCurrentLogger().logInfo(source, message);
		}
	}
	
	public void logMinor(String source, String error) {
		System.err.print(Logger.getCurrentLogger().getLog(Level.MINOR, source, error));
	}
	
	public void logFatal(String source, String error) {
		System.err.print(Logger.getCurrentLogger().getLog(Level.FATAL, source, error));
		System.exit(1);
	}
	
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
	
	public void setIsDateTimePrinted(boolean isDateTimePrinted) {
		Logger.getCurrentLogger().isDateTimePrinted = isDateTimePrinted;
		if(!isDateTimePrinted)Logger.getCurrentLogger().date = null;
	}
	
	public void setIsLoggerNamePrinted(boolean isLoggerNamePrinted) {
		Logger.getCurrentLogger().isLoggerNamePrinted = isLoggerNamePrinted;
	}
	
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
