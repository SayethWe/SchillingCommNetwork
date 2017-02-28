package org.schillingSchool.communications.utils;

public class Utils {
	
	public static void log(String message, Object sender) {
		System.out.println(sender.getClass().toString() + " " + sender + " says: " + message);
	}
	
}
