package com.building.util.jaxrs;

import com.building.dto.GridRowEntry;
import com.building.util.core.ClassUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BeanListGridConverter {

	/**
	 * DTOの要素をGrid用の要素に詰め替える.
	 * @param beanClass DTOのデータ型
	 * @param dataList 変換前のデータリスト
	 * @param keyName GridのID要素名
	 * @param propNameList Gridの各列の要素名（並び順）
	 * @return Grid要素のリスト
	 */
	public static <T> List<GridRowEntry> convet(Class<? extends T> beanClass, List<T> dataList, String keyName, List<String> propNameList) {
		//リフレクションアクセス用の情報を初期化
		final HashMap<String, Method> methodMap = new HashMap<String, Method>(propNameList.size() + 1);
		try {
			methodMap.put(keyName, createGetterMethod(beanClass, keyName));
			for (final String propName : propNameList) {
				methodMap.put(propName, createGetterMethod(beanClass, propName));
			}
		} catch (Exception e) {
			//LOG 項目の取得メソッドが存在しないため処理をやめる場合
			return null;
		}

		final ArrayList<GridRowEntry> al = new ArrayList<GridRowEntry>(dataList.size());
		for (final T bean : dataList) {
			final GridRowEntry gre = new GridRowEntry();
			final Object k = ClassUtil.invokeMethod(bean, methodMap.get(keyName));
			gre.setId(Long.parseLong(k.toString()));
			for (final String propName : propNameList) {
				final Object v = ClassUtil.invokeMethod(bean, methodMap.get(propName));
				gre.addData(v == null ? null : v.toString());
			}
			al.add(gre);
		}
		return al;
	}

	/**
	 * getXXX/isXXXのリフレクションベースのメソッドを属性名から取得する.
	 *
	 * 先にgetXXXへのアクセスを探し、存在しない場合isXXX
	 * @param beanClass 対象クラス
	 * @param propName 取得対象項目名
	 * @return データ取得メソッド(どちらかのメソッドを初期化できなかった場合は例外)
	 * @throws Exception 値アクセスメソッド(getXXXX/isXXXX)の初期化ができなかった場合
	 */
	protected static Method createGetterMethod(Class<?> beanClass, String propName) throws Exception {
		Method m = ClassUtil.createMethod(beanClass, ClassUtil.formatMethodName("get", propName));
		if(m==null) {
			m = ClassUtil.createMethod(beanClass, ClassUtil.formatMethodName("is", propName));
		}
		if(m==null) {
			throw new Exception("No such property:"+propName);
		}
		return m;
	}
}
