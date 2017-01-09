package com.building.util.str;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.Date;

public class TokenGenerator {
	/** ランダムトークン文字列の構成文字種数. */
	private static final int TOKEN_CHARS = 62; // 英小文字・英大文字・数字=26+26+10
	/** 乱数発生処理(スレッドセーフ処理が必要). */
	private static final SecureRandom random;
	/** ランダムトークンのフォーマット処理(スレッドセーフ処理が必要). */
	private static final MessageFormat tokenFormat;

	static {
		random = new SecureRandom();
		//******yyyyMMddHHmmss{20桁ランダム} ******部分は社員のID(右寄せ6桁)・日付はトークン作成日時(14桁)・残りはランダム生成(20桁)
		tokenFormat = new MessageFormat("{0,date,yyyyMMddHHmmss}{1,number,000000}{2}");
	}

	/**
	 * ランダムトークンの作成.
	 * ユーザと年月日自分秒単位が含まれるので、トークンの衝突は無視する(同一秒・同一ユーザの場合のみ想定なので衝突してもOK)
	 * @param date 作成年月日時分秒(14桁)
	 * @param userId 作成従業員ID(6桁)
	 * @param len 乱数発生桁数(20桁)
	 * @return
	 */
	public static String create(Date date, int userId, int len) {
		// 作成日時・ユーザID(数字)を含め+末尾に[A-Za-z0-9]のランダムトークン生成のパラメータ群
		final Object[] objs = new Object[] {date, userId, createRandomAkphaNum(len)};
		synchronized (tokenFormat) {
			return tokenFormat.format(objs);
		}
	}

	/**
	 * 乱数発生部分の文字列取得.
	 * @param len 桁数
	 * @return 乱数発生した文字列
	 */
	public static String createRandomAkphaNum(int len) {
		final StringBuilder buf = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			buf.append(nextAlphaNum());
		}
		return buf.toString();
	}

	/**
	 * 英小大文字・数字のいずれかの任意の１文字を乱数発生させる.
	 * @return [A-Za-z0-9]の1文字
	 */
	private static char nextAlphaNum() {
		int n = -1;
		synchronized (random) {
			n = random.nextInt(TOKEN_CHARS);
		}

		char c = ' ';
		if(n<26) {
			c = (char) ('A'+n);
		} else if(n<52) {
			c = (char) ('a'+n-26);
		} else {
			c = (char) ('0'+n-52);
		}
		return c;
	}
}
