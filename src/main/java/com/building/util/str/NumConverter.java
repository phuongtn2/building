package com.building.util.str;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class NumConverter {

	public static List<Integer> parseIntList(List<String> strList) {
		final ArrayList<Integer> al = new ArrayList<Integer>(strList.size());
		strList.stream().forEach(str -> al.add(toInt(str,0)));
		return al;
	}

	public static List<Long> parseLongList(List<String> strList) {
		final ArrayList<Long> al = new ArrayList<Long>(strList.size());
		strList.stream().forEach(str -> al.add(toLong(str, 0L)));
		return al;
	}

	public static int parseIntDepartmentProperty(Properties prop, String key, int department) {
		final String dkey = key+"."+department;
		if(prop.containsKey(dkey)) {
			return toInt(prop.getProperty(dkey), 0);
		}
		return toInt(prop.getProperty(key), 0);
	}
	public static long parseLongDepartmentProperty(Properties prop, String key, int department) {
		final String dkey = key+"."+department;
		if(prop.containsKey(dkey)) {
			return toLong(prop.getProperty(dkey), 0);
		}
		return toLong(prop.getProperty(key), 0);
	}
	public static boolean parseBoolDepartmentProperty(Properties prop, String key, int department) {
		final String dkey = key+"."+department;
		if(prop.containsKey(dkey)) {
			return Boolean.parseBoolean(prop.getProperty(dkey));
		}
		return Boolean.parseBoolean(prop.getProperty(key, "false"));
	}
	///
	public static int parseIntProperty(Properties prop, String key) {
		return parseIntProperty(prop, key, 0);
	}
	public static long parseLongProperty(Properties prop, String key) {
		return parseLongProperty(prop, key, 0L);
	}
	public static double parseDoubleProperty(Properties prop, String key) {
		return parseDoubleProperty(prop, key, 0.0);
	}
	public static boolean parseBoolProperty(Properties prop, String key) {
		return parseBoolProperty(prop, key, false);
	}
	public static int parseIntProperty(Properties prop, String key, int defaultValue) {
		if(prop!=null) {
			final String value = prop.getProperty(key);
			return toInt(value, defaultValue);
		}
		return defaultValue;
	}

	public static long parseLongProperty(Properties prop, String key, long defaultValue) {
		if(prop!=null) {
			final String value = prop.getProperty(key);
			return toLong(value, defaultValue);
		}
		return defaultValue;
	}

	public static double parseDoubleProperty(Properties prop, String key, double defaultValue) {
		if(prop!=null) {
			final String value = prop.getProperty(key);
			return toDouble(value, defaultValue);
		}
		return defaultValue;
	}

	public static boolean parseBoolProperty(Properties prop, String key, boolean defaultValue) {
		if(prop!=null) {
			final String value = prop.getProperty(key);
			if(value!=null) {
				return Boolean.parseBoolean(value);
			}
		}
		return defaultValue;
	}
	//
	public static String departmentProperty(Properties prop, String key, int department) {
		final String dkey = key+"."+department;
		if(prop.containsKey(dkey)) {
			return prop.getProperty(dkey);
		}
		return prop.getProperty(key, "");
	}

	protected static int toInt(String value, int defaultValue) {
		if (value != null) {
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException e) {
				//設定の数値評価失敗
			}
		}
		return defaultValue;
	}

	protected static long toLong(String value, long defaultValue) {
		if (value != null) {
			try {
				return Long.parseLong(value);
			} catch (NumberFormatException e) {
				//設定の数値評価失敗
			}
		}
		return defaultValue;
	}

	protected static double toDouble(String value, double defaultValue) {
		if (value != null) {
			try {
				return Double.parseDouble(value);
			} catch (NumberFormatException e) {
				//設定の数値評価失敗
			}
		}
		return defaultValue;
	}

}
