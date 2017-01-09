package com.building.util.core;

import com.building.util.str.StringUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

public class ClassUtil {

	/**
	 * Objectリストに指定したクラスのものが存在するかを確認する.
	 *
	 * @param objCol インスタンス群.
	 * @param clazz 検索するクラス(このクラスまたは派生であること)
	 * @return 存在した場合true/存在しない場合false
	 */
	public static boolean findByDescendantClass(Collection<Object> objCol, Class<?> clazz) {
		for (final Object obj : objCol) {
			if (obj!=null && clazz.isAssignableFrom(obj.getClass())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 指定クラスのインターフェース中のアノテーションの発見
	 * @param clazz 定義元のクラス
	 * @param anno 検索するアノテーションクラス
	 * @return 型の一致するアノテーション定義(最初に発見したもの)、ない場合はnull
	 */
	public static <T> T findInterfaceAnnotation(Class<?> clazz, Class<T> anno) {
		for(final Class<?> c : clazz.getInterfaces()) {
			final T at = lookupAnnotation(c.getAnnotations(), anno);
			if(at!=null) {
				return at;
			}
		}
		return null;
	}
	/**
	 * アノテーションの配列から、指定したアノテーション型の要素が含まれるか確認する
	 * @param annos アノテーションの配列
	 * @param anno 検索するアノテーションクラス
	 * @return 型の一致するアノテーション定義（最初に発見したもの）、ない場合はnull
	 */
	public static <T> T lookupAnnotation(Annotation[] annos, Class<T> anno) {
		for(final Annotation  a : annos) {
			if(a.annotationType()==anno) {
				return anno.cast(a);
			}
		}
		return null;
	}

	/**
	 * メソッド名のフォーマット処理.
	 * アクセサのプレフィックスとプロパティ名の１文字目を大文字にして結合した文字列をメソッド名とする
	 * (プロパティの２文字目が大文字の場合はそのまま結合)
	 * @param acceser アクセサプレフィックス(getXXXやisXXXの前部分)
	 * @param propName プロパティ名
	 * @return メソッド名
	 */
	public static String formatMethodName(String acceser, String propName) {
		final StringBuilder buf = new StringBuilder(acceser.length()+propName.length()).append(acceser);
		if(StringUtil.isEmpty(propName) || (propName.length()>1 && Character.isUpperCase(propName.charAt(1)))) {
			buf.append(propName);
		} else {
			buf.append(Character.toUpperCase(propName.charAt(0))).append(propName.substring(1));
		}
		return buf.toString();
	}

	/**
	 * メソッド名の前置文字列を指定してプロパティ名を取得する
	 * @param m
	 * @param acceser
	 * @return
	 */
	public static String parsePropertyName(Method m, String acceser) {
		final String mn = m.getName();
		if(mn.startsWith(acceser)) {
			final int al = acceser.length();
			return new StringBuilder(mn.length() - al).
					append(Character.toLowerCase(mn.charAt(al))).
					append(mn.substring(al+1)).toString();
		}
		return null;
	}
	/**
	 * リフレクションによるメソッドObjectの作成.
	 *
	 * メソッド引数がない想定
	 * 例外はつぶしてnullを返す
	 * @param clazz 対象クラス
	 * @param methodName メソッド名
	 * @param parameterType パラメータ型可変引数
	 * @return メソッドObject(初期化失敗時はnull)
	 */
	public static Method createMethod(Class<?> clazz, String methodName, Class<?> ... parameterType) {
		try {
			return clazz.getMethod(methodName, parameterType);
		} catch (NoSuchMethodException|SecurityException e) {
			return null;
		}
	}

	/**
	 * リフレクションベースのメソッド実行する.
	 *
	 * メソッドは無引数の想定
	 * 実行失敗時はnullを返す
	 * @param bean 実行対象Object
	 * @param m 実行メソッド
	 * @param args 実行時引数(可変長引数)
	 * @return
	 */
	public static Object invokeMethod(Object bean, Method m, Object ... args) {
		try {
			return m.invoke(bean, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			return null;
		}
	}
}
