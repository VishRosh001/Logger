package com.vishrosh.logger.core;

/**
 * 
 * This enum class is used by {@link Logger} for logging different types of information.
 * It currently comprises of three levels: {@link Level#INFO}, {@link Level#MINOR}, {@link Level#FATAL}.
 * 
 *
 * <ul>
 * 	<li>
 *		{@link Level#INFO}: It should only be used for logging trivial information.
 * 	</li>
 * 	<li>
 * 		{@link Level#MINOR} It should be used for logging mild errors that do not affect the
 * 		program considerably.
 * 	</li>
 * 	<li>
 * 		{@link Level#FATAL} It should only be used for logging severe errors that could break
 * 		the program. Logging using this level will <b>terminate</b> the program.
 * 	</li>
 * </ul>
 * 
 * @author VishRosh001
 *
 */

public enum Level {
	INFO,
	MINOR,
	FATAL,
}
