package com.team.futurecraft;

import java.math.BigDecimal;

public class MetricUtil {
	/**
	 * Parses a string representing a metric distance and returns it's BigDecimal equivelant in meters
	 */
	public static BigDecimal multiDistanceToMeters(String multi) {
		int len = multi.length();
		
		if (len > 0) {
			if (len > 1) {
				char char1 = multi.charAt(len - 2);
				char char2 = multi.charAt(len - 1);
				boolean char1num = isNumeric(char1);
				boolean char2num = isNumeric(char2);
				double multFactor = 0;
				
				if (char1num && char2 == 'm') multFactor = 1.0;
				if (char1 == 'k' && char2 == 'm') multFactor = 1000.0;
				if (char1 == 'a' && char2 == 'u') multFactor = 147597870700.0;
				if (char1num && char2num) multFactor = 1.0; //if no modifier, default to meters
				
				String actualNumber = "";
				
				if (char1num && !char2num) actualNumber = multi.substring(0, len - 1);
				else if (char1num && char2num) actualNumber = multi;
				else if (!char1num && !char2num) {
					if (len > 2)
						actualNumber = multi.substring(0, len - 2);
					else {
						actualNumber = multi;
					}
				}
				else return null;
				
				try {
					BigDecimal dec = new BigDecimal(actualNumber);
					dec = dec.multiply(new BigDecimal(multFactor));
					return dec;
					
				} catch (NumberFormatException e) {
					return null;
				}
			}
			else {
				try {
					return new BigDecimal(multi);
				} catch (NumberFormatException e) {
					return null;
				}
			}
		}
		else {
			return null;
		}
	}
	
	public static BigDecimal multiTimeToSeconds(String multi) {
		int len = multi.length();
		
		if (len > 0) {
			if (len > 1) {
				char char1 = multi.charAt(len - 2);
				char char2 = multi.charAt(len - 1);
				boolean char1num = isNumeric(char1);
				boolean char2num = isNumeric(char2);
				double multFactor = 0;
				
				if (char1num && char2 == 's') multFactor = 1.0;
				if (char1num && char2 == 'm') multFactor = 60.0;
				if (char1num && char2 == 'h') multFactor = 60.0 * 60.0;
				if (char1num && char2 == 'd') multFactor = 60.0 * 60.0 * 24.0;
				if (char1num && char2num) multFactor = 1.0; //if no modifier, default to seconds
				
				String actualNumber = "";
				
				if (char1num && !char2num) actualNumber = multi.substring(0, len - 1);
				else if (char1num && char2num) actualNumber = multi;
				else if (!char1num && !char2num) {
					if (len > 2)
						actualNumber = multi.substring(0, len - 2);
					else {
						actualNumber = multi;
					}
				}
				else return null;
				
				try {
					BigDecimal dec = new BigDecimal(actualNumber);
					dec = dec.multiply(new BigDecimal(multFactor));
					return dec;
					
				} catch (NumberFormatException e) {
					return null;
				}
			}
			else {
				try {
					return new BigDecimal(multi);
				} catch (NumberFormatException e) {
					return null;
				}
			}
		}
		else {
			return null;
		}
	}
	
	private static boolean isNumeric(char c) {
		if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9')
			return true;
		else
			return false;
	}
}
