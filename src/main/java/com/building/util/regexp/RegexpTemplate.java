package com.building.util.regexp;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文字列中の{変数名}で含まれる要素を連続変数置換する処理.
 *
 * 1. 置き換え後に含まれる変数は無視されます（もともとの文字列の変数部分のみ対応）
 * 2. 同じ変数の置き換えは複数回・変数の内容も複数置き換えします。
 * 3. 存在しない変数の文字列は置き換えません。
 * 4. 変数は単語構成文字(英大小文字・数字・_)のみで構成してください
 *
 * テンプレートメール処理用
 *
 * @author t3651121
 *
 */
public class RegexpTemplate {
	/** メールテンプレートの変数シンボル {変数名} */
	private static final String VAL_PATTERN = "\\{(\\w+)\\}";
	/** メールテンプレートの変数名のグループ番号 VAL_PATTENのグループの抽出で対応する番号 */
	private static final int VAL_PATTERN_POS = 1;
	/** メールテンプレートパターン(変数部分の発見用) */
	private static final Pattern pat = Pattern.compile(VAL_PATTERN);
	/** 変数と置き換え文字列のマップ */
	private final Map<String, String> replaceMap;

	/**
	 * 正規表現テンプレート処理クラス.
	 */
	public RegexpTemplate() {
		replaceMap = new HashMap<String, String>();
	}

	/**
	 * 1項目の置換ルールの追加.
	 * @param val ${val}で指定される名称
	 * @param replace 設定する値
	 */
	public void storeReplace(String val, String replace) {
		replaceMap.put(val, replace);
	}

	/**
	 * 一括で置換ルールを追加する
	 * @param srcMap 名称と置換値のマップ
	 */
	public void storeReplaceMap(Map<String, String> srcMap) {
		replaceMap.putAll(srcMap);
	}

	/**
	 * テンプレートデータを置き換えて、置換後文字列を取得.
	 *
	 * 置換パターンはstoreReplaceなどで設定したルール
	 * @param src テンプレートを含む文字列
	 * @return 置き換えた文字列
	 */
	public String replaceAll(String src) {
		final StringBuffer buf = new StringBuffer();
		final Matcher mat = pat.matcher(src);
		while (mat.find()) {
			final String val = mat.group(VAL_PATTERN_POS); // 正規表現でマッチした変数名

			final String rval = replaceMap.containsKey(val) ? replaceMap
					.get(val) : ("{"+val+"}"); // 変数に対応する値を取得（ない場合はそのまま)

			mat.appendReplacement(buf, rval==null ? "":rval);
		}
		mat.appendTail(buf);
		return buf.toString();
	}
}
