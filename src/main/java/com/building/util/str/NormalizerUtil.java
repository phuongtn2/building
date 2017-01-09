package com.building.util.str;

import java.lang.Character.UnicodeBlock;
import java.text.Normalizer;
import java.text.Normalizer.Form;

public class NormalizerUtil {

	public static String toNormalizer(String src) {
		if(src==null) {
			return null;
		}
		final String str = Normalizer.normalize(src, Form.NFKC);//半角カナ→全角カナ・全角英数→半角英数
		return toKatakana(str);//全角ひらがな→全角カナ
	}

	public static String toKatakana(String str) {
		final char[] cs = str.toCharArray();
		for(int i=0; i<cs.length; i++) {
			if(UnicodeBlock.of(cs[i])==UnicodeBlock.HIRAGANA) {
				cs[i] = (char) (cs[i] -'あ' + 'ア');
			}

		}
		return new String(cs);
	}
}
