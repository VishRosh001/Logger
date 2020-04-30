package com.vishrosh.logger.tests;

import com.vishrosh.logger.core.*;

public class Main {
	
	public static void main(String[] args) {
		Logger.getLogger(Main.class).log(Level.MINOR, "Source", "This is a hello message!");
		Logger.getLogger(Main.class).log("This is a hello message!");
		Logger.getCurrentLogger().log("fdddfd");
	}

}
