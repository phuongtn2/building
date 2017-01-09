package com.building.util.sql;

public class SqlStringUtil {
	/**
	 * SQLの前方一致検索の記号(%)を付ける.
	 * @param str 文字列
	 * @return "文字列" + %
	 */
	public static String addForwardMatchSearchSymbol(String str) {
		if(str==null) {
			str = "";
		}
		return str + "%";
	}

	/**
	 * SQLの後方一致検索の記号(%)を付ける.
	 * @param str 文字列
	 * @return % + "文字列"
	 */
	public static String addBackwardMatchSearchSymbol(String str) {
		if(str==null) {
			str = "";
		}
		return "%" + str;
	}

	/**
	 * SQLの部分一致検索の記号(%)を付ける.
	 * @param str 文字列
	 * @return % + "文字列" + %
	 */
	public static String addPartialMatchSearchSymbol(String str) {
		return addForwardMatchSearchSymbol(addBackwardMatchSearchSymbol(str));
	}

}
