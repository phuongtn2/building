package com.building.util.jaxrs;

import com.building.util.core.ClassUtil;
import com.building.util.str.StringUtil;

import javax.ws.rs.core.MultivaluedMap;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;

/**
 * HTTPのMultivaluedMap<String, String>のデータをBeanに詰め替える.
 *
 * Bean側のプロパティの配列についてはプリミティティブ型配列はエラーになります。
 *
 * @author masahiro.takahashi
 *
 */
public class MultivaluedMapBeanConverter {
//	/** プリミーティブ型を参照型との対象の読み替え */
//	private static final Map<Class<?>, Class<?>> primitiveRefenceTypeMap = Collections.unmodifiableMap(new HashMap<Class<?>, Class<?>>() {{
//		put(byte.class, Byte.class);
//		put(short.class, Short.class);
//		put(int.class, Integer.class);
//		put(long.class, Long.class);
//		put(float.class, Float.class);
//		put(double.class, Double.class);
//		put(char.class, Character.class);
//		put(boolean.class, Boolean.class);
//	}});

	/**
	 * BeanにHTTPパラメータの内容を詰める.
	 *
	 * HTTPパラメータ名とBeanプロパティのsetterの名称が一致する場合に格納される
	 * @param clazz Beanクラス
	 * @param mm
	 * @return
	 */
	public static <T> T convert(Class<T> clazz, MultivaluedMap<String, String> mm) {
		final T bean;
		try {
			bean = clazz.newInstance();
		} catch (InstantiationException|IllegalAccessException e) {
			return null;
		}

		// Beanクラスのsetterメソッドを確認する
		final Map<String, Method> clazzPropSetterMap = new HashMap<String, Method>();
		for(final Method m : clazz.getMethods()) {
			final String propName = ClassUtil.parsePropertyName(m, "set");
			if(propName!=null && m.getParameterTypes().length==1) {
				clazzPropSetterMap.put(propName, m);
			}
		}

		//Beanクラスのsetterメソッドとパラメータ項目が一致するものをBeanに格納する
		for(final String propName : mm.keySet()) {
			if(clazzPropSetterMap.containsKey(propName)) {
				final Method m = clazzPropSetterMap.get(propName);

				final Class<?>[] parameterTypes = m.getParameterTypes();//パラメータ数1は確認済み

				Object v;
				try {
					v = formatParameter(m,  parameterTypes[0], mm.get(propName));
					ClassUtil.invokeMethod(bean, m, v);
				} catch (Exception e) {
					//値の評価失敗（セットしない）
				}
			}
		}
		return bean;
	}

	protected static Object formatParameter(Method m, Class<?> parameterType, List<String> list) {
		if(parameterType.isArray()) {
			final Class<?> inArrayType = parameterType.getComponentType();
			return formatParameterArray(inArrayType, list);
		} else if (parameterType == List.class) {
			return formatParameterList(m, parameterType, list);
		} else {
			return castInData(parameterType, (list!=null&&(!list.isEmpty())) ? list.get(0) : null);
		}
	}

	protected static <T> T[] formatParameterArray(Class<T> inArrayType, List<String> list) {
		@SuppressWarnings("unchecked")
		final T[] ary = (T[]) Array.newInstance(inArrayType, list.size());
		for(int i=0; i<list.size(); i++) {
			@SuppressWarnings("unchecked")
			final T v = (T) castInData(inArrayType, list.get(i));
			Array.set(ary, i, v);
		}
		return ary;
	}

	protected static Object formatParameterList(Method m, Class<?> parameterType, List<String> list) {
		final Type[] tvs = m.getGenericParameterTypes();
		final ArrayList<Object> al = new ArrayList<Object>(list.size());
		if (tvs.length == 1) {
			final ParameterizedType pt = (ParameterizedType)tvs[0];
			final Type[] inTypes = pt.getActualTypeArguments();
			if(inTypes.length==1) {
				for (final String s : list) {
					if(inTypes[0] instanceof Class) {
						al.add(castInData((Class<?>)inTypes[0], s));
					} else {//WildcardTypeなど
						al.add(s);
					}
				}
			}
		}
		return al;
	}

	/**
	 * DTOのsetterに渡すパラメータのキャスト処理.
	 * @param pt setterに渡す型
	 * @param s APIで受け取った値の文字列
	 * @return
	 */
	public static Object castInData(Class<?> pt, String s) {
		if(pt==String.class) {
			if (s != null && s.trim().toLowerCase().equals("null")) {
				s = null;
			}
			return s;
		} else if(pt==Integer.TYPE || pt==Integer.class) {
			return Integer.parseInt(s);
		} else if(pt==Long.TYPE || pt==Long.class) {
			return Long.parseLong(s);
		} else if(pt==Byte.TYPE || pt==Byte.class) {
			return Byte.parseByte(s);
		} else if(pt==Short.TYPE || pt==Short.class) {
			return Short.parseShort(s);
		} else if(pt==Boolean.TYPE || pt==Boolean.class) {
			return Boolean.parseBoolean(s);
		} else if(pt==BigDecimal.class) {
			return StringUtil.isEmpty(s)?null: new BigDecimal(s);
		} else if(Date.class.isAssignableFrom(pt)) {
			return StringUtil.isEmpty(s)?null: StringUtil.parseDate(s);
		}
		return null;
	}

	/**
	 * FormからLongビット列の配列で入力された要素を重ね合わせて、１項目のLongにパックして格納する.
	 *
	 * @param bean Bean(Longのビット配列をプロパティと結果のパックしたビットマスクを格納するプロパティを持つこと）
	 * @param srcPropertyName ビットパターンのLong配列を保持するプロパティ名(Long[]の値を保持)
	 * @param destPropertyName Long配列の各要素をORで演算した結果を格納するプロパティ名
	 */
	public static void packBitPattern(Object bean, String srcPropertyName, String destPropertyName) {
		final Method getter = ClassUtil.createMethod(bean.getClass(), ClassUtil.formatMethodName("get", srcPropertyName));
		final Method setter = ClassUtil.createMethod(bean.getClass(), ClassUtil.formatMethodName("set", destPropertyName), long.class);

		final Object g = ClassUtil.invokeMethod(bean, getter);
		final Long[] data = (Long[])g;
		long bitPattern = 0L;
		for(final Long p : data) {
			bitPattern |= p;
		}
		ClassUtil.invokeMethod(bean, setter, bitPattern);
	}

	/**
	 * DBの1項目Longを各ビットで1の要素を分割して、FormのLongビット列にアンパックした配列に格納する。
	 *
	 * @param bean Bean(Longのビット配列をプロパティと結果のパックしたビットマスクを格納するプロパティを持つこと）
	 * @param srcPropertyName DBに存在するパックされたLong配列の各要素をORで演算した結果を格納するプロパティ名
	 * @param destPropertyName ビットパターンのLong配列を保持するプロパティ名(Long[]の値を保持)
	 */
	public static void unpackFormArray(Object bean, String srcPropertyName, String destPropertyName) {
		final Method getter = ClassUtil.createMethod(bean.getClass(), ClassUtil.formatMethodName("get", srcPropertyName));
		final Method setter = ClassUtil.createMethod(bean.getClass(), ClassUtil.formatMethodName("set", destPropertyName), Long[].class);

		final Object g = ClassUtil.invokeMethod(bean, getter);
		long data = (long)g;
		final ArrayList<Long> patternList = new ArrayList<Long>();
		for(long i=0; i<64; i++) {
			final long p = data&(1L<<i);
			if(p!=0L) {
				patternList.add(p);
			}
		}
		ClassUtil.invokeMethod(bean, setter, new Object[] {patternList.toArray(new Long[patternList.size()])});
	}
}
