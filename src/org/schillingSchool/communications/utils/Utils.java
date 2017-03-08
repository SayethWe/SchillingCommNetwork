package org.schillingSchool.communications.utils;


import java.util.logging.Logger;

import org.schillingSchool.communications.schillingCommNetwork.SchillingCommNetwork;

public class Utils {
	
	private static Logger logger;
	
	public static Logger getLogger() {
		if (logger == null) logger = Logger.getLogger(SchillingCommNetwork.LOGGER_NAME);
		return logger;
	}
	
	public static void log(String message, Object sender) {
		System.out.println(sender.getClass().toString() + " " + sender + " says: " + message);
	}
	
}
