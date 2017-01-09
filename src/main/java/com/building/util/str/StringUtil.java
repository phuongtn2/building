package com.building.util.str;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {
	/** REST APIのフォームから日付形式の正規表現パターン.
	 * "yyyy/MM/dd HH:mm:ss" (秒の省略、時分秒の省略も許す)
	 */
	private static final Pattern dataPatten = Pattern.compile("(\\d{4})/(\\d{1,2})/(\\d{1,2})(?: (\\d{1,2}):(\\d{1,2})(?::(\\d{1,2}))?)?");

	public static String join(Collection<String> col, String splitter) {
		final StringBuilder buf = new StringBuilder();
		for (final String s : col) {
			if (buf.length() > 0) {
				buf.append(splitter);
			}
			buf.append(s);
		}
		return buf.toString();
	}

	public static String join(List<Integer> idList, String splitter) {
		final List<String> sl = new ArrayList<String>(idList.size());
		for(final Integer v : idList) {
			sl.add(String.valueOf(v));
		}
		return join(sl, splitter);
	}

	public static String join(String[] strs, String splitter) {
		return join(Arrays.asList(strs == null ? new String[0] : strs), splitter);
	}

	public static boolean isEmpty(String s) {
		return s==null || s.length()==0;
	}

	public static boolean notEmpty(String s) {
		return s!=null && s.length()>0;
	}

	public static String nvl(String str, String str2) {
		return str==null ? str2 : str;
	}

	/**
	 * 日付文字列のパース.
	 * "yyyy/MM/dd HH:mm:ss" (秒の省略、時分秒の省略も許す)を想定
	 * @param apiDateString 日付入力文字列
	 * @return パース結果Date
	 * @throws NumberFormatException 入力文字列の形式が想定と異なる場合
	 * @throws NullPointerException 入力文字列がNULLの場合
	 */
	public static Date parseDate(String apiDateString) throws NumberFormatException, NullPointerException {
		return new Date(parseDateStringTimeInMilis(apiDateString));
	}

	/**
	 * 日付文字列を日付(timeInMilisのLongの数字)におきかえる.
	 *
	 * yyyy/MM/dd HH:mm:ss形式(yyyy/MM/dd または yyyy/MM/dd HH:mmの形式も可)
	 * @param apiDateTimeString 日付想定の文字列
	 * @return
	 * @throws NullPointerException  文字列がnullだった場合
	 * @throws NumberFormatException 文字列のフォーマットが想定形式でない場合
	 */
	public static long parseDateStringTimeInMilis(String apiDateTimeString) throws NumberFormatException, NullPointerException {
		if (apiDateTimeString == null) {
			throw new NullPointerException("parseDateStringTimeInMilis argument is null");
		}
		final Matcher m = dataPatten.matcher(apiDateTimeString);
		if (m.find()) {
			final int[] dds = new int[6];
			for (int i = 0; i < m.groupCount() && i < dds.length; i++) {
				final String s = m.group(i + 1);
				dds[i] = isEmpty(s) ? 0 : Integer.parseInt(s);//パターンは数字のみなのでエラーは発生しない想定
			}
			final Calendar cal = Calendar.getInstance();
			cal.clear();
			cal.set(dds[0], dds[1] - 1, dds[2], dds[3], dds[4], dds[5]);//月部分は-1が必要
			cal.set(Calendar.MILLISECOND, 0);
			return cal.getTimeInMillis();
		} else {
			throw new NumberFormatException("parseDateStringTimeInMilis format error::"+apiDateTimeString);
		}
	}

	/**
	 * 日付の年月日部分を文字列化する.
	 * @param d
	 * @return
	 */
	public static String formatYMD(Date d) {
		return new SimpleDateFormat("yyyy/MM/dd").format(d);
	}

	/**
	 * 日付の年月日時分秒部分を文字列化する.
	 * @param d
	 * @return
	 */
	public static String formatDate(Date d) {
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(d);
	}


	/**
	 * 半角または全角スペース区切りで文字列分割
	 * @param str 文字列
	 * @return 半角または全角スペースで区切った文字列が含まれたリスト。もし引数が空・nullの場合は空リスト
	 */
	public static List<String> splitSpace(String str) {
		if(StringUtil.isEmpty(str)) {
			return new ArrayList<String>(0);
		}
		final String[] strs = str.split("[ 　]");
		final ArrayList<String> al = new ArrayList<String>(strs.length);
		for(final String s : strs) {
			if(StringUtil.notEmpty(s)) {
				al.add(s);
			}
		}
		return al;
	}

	public static String base64EncodeString(String str) {
		if(str==null) {
			return null;
		}

		final Base64.Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(str.getBytes(StandardCharsets.UTF_8));
	}

	public static String base64DecodeString(String encoded) {
		final Base64.Decoder decoder = Base64.getDecoder();
		return new String(decoder.decode(encoded), StandardCharsets.UTF_8);
	}


	/** 半角数値のパターン */
	private static final Pattern numberPattern = Pattern.compile("^[0-9]+$");
//	private static final Pattern hiraganaPattern = Pattern.compile("^[\u3040-\u309F]+$");
//	private static final Pattern katakanaPattern = Pattern.compile("^[\u30A0-\u30FF]+$");
	/** 全角カタカナ・ひらがなのパターン */
	private static final Pattern kanaPattern = Pattern.compile("^[\u3040-\u309F\u30A0-\u30FF]+$");
	/** 漢字のパターン(BMP部分のみ) */
	private static final Pattern kanjiPattern = Pattern.compile("^[\u4E00-\u9FFF]+$");
	/** メール形式パターン(既存の実装と同様) */
	private static final Pattern mailPattern = Pattern.compile("^[a-zA-Z0-9@._\\-]+$");

	public static boolean isNumber(String str) {
		return str==null ? false : numberPattern.matcher(str).matches();
	}
	public static boolean isKana(String str) {
		return str==null ? false : kanaPattern.matcher(str).matches();
	}
	public static boolean isKanji(String str) {
		return str==null ? false : kanjiPattern.matcher(str).matches();
	}
	public static boolean isMail(String str) {
		return str==null ? false : mailPattern.matcher(str).matches();
	}
	public static String removeHyphen(String str) {
		return str==null ? null : str.replaceAll("-", "");
	}
	public static String removeHyphenDblSp(String str) {
		return str==null ? null : str.replaceAll("[\\-\u3000]", "");
	}

	/**
	 * 文字列の先頭指定文字数（文字列が指定文字数より短い場合は全体）を返す
	 * @param body
	 * @param i
	 * @return
	 */
	public static String left(String str, int leftLen) {
		if(str==null) {
			return null;
		}
		final int len = Math.min(leftLen, str.length());
		return str.substring(0, len);
	}

	private static final long PACK_RANGE = 62;
	/**
	 * 数字のID[0-9]*の内容を[0-9a-zA-Z]*の文字列に置き換える.
	 * (10>62)
	 * @param id
	 * @return
	 */
	public static String idPacking(long id) {
		if (id < 0) {
			return "";
		}
		final StringBuilder buf = new StringBuilder();
		long v = id;
		do {
			final long ketaValue = v % PACK_RANGE;
			buf.append(toPack(ketaValue));
			v = v / PACK_RANGE;
		} while (v > 0);
		return buf.reverse().toString();
	}

	/**
	 * idPackingで置き換えた文字列を元のIDに復元する.
	 *
	 * @param str
	 * @return
	 */
	public static long idUnPacking(String str) {
		long v = 0L;
		for (final char c : str.toCharArray()) {
			v = v * PACK_RANGE + toUnPack(c);
		}
		return v;
	}

	protected static char toPack(long v) {
		if (v < 0L) {
			return '?';
		} else if (v < 10L) {
			return (char) ('0' + v);
		} else if (v < 36L) {
			return (char) ('a' + v - 10L);
		} else if (v < 62L) {
			return (char) ('A' + v - 36L);
		} else {
			return '?';
		}
	}

	protected static long toUnPack(char c) {
		if ('0' <= c && c <= '9') {
			return c - '0';
		} else if ('a' <= c && c <= 'z') {
			return c - 'a' + 10;
		} else if ('A' <= c && c <= 'Z') {
			return c - 'A' + 36;
		} else {
			return 0L;
		}
	}
	public static String capitalize(final String line) {
		   return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}

	/**
	 * 文字列を置き換えルールマップにパース
	 * @param ruleStr 文字列置き換えルール（JSON形式）
	 * @return 正規表現パターンと置き換えルールの文字列
	 */
	public static Map<Pattern, String> parseReplaceRuleMap(String ruleStr) {
		final LinkedHashMap<Pattern, String> map = new LinkedHashMap<Pattern, String>();
		if (ruleStr != null) {
			final ObjectMapper om = new ObjectMapper();
			try {
				@SuppressWarnings("unchecked")
				final Map<String, String> smap = (Map<String, String>) om.readValue(ruleStr, new TypeReference<LinkedHashMap<String,String>>(){});
				for (final Map.Entry<String, String> me : smap.entrySet()) {
					final Pattern p = Pattern.compile(me.getKey());
					map.put(p, me.getValue());
				}
			} catch (IOException e) {
				return null;
			}
		}
		return map;
	}
}
