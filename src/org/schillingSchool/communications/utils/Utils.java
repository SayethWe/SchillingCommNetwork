package org.schillingSchool.communications.utils;


import java.util.logging.Logger;

import org.schillingSchool.communications.schillingCommNetwork.SchillingCommNetwork;
/**
 * @author Geekman9097
 * Provides utility classes for use in other areas of the code
 * Written 14/3/2017
 *
 */
public class Utils {
	
	private static Logger logger;

	/**
	 * Logger allows the program to send a message to the console
	 * @return
	 */
	public static Logger getLogger() {
		if (logger == null) logger = Logger.getLogger(SchillingCommNetwork.LOGGER_NAME);
		return logger;
	}
}
