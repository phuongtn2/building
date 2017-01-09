package com.building.util.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionFilter {

	/**
	 * 引数のBeanからキー項目の要素を取得する.
	 * @author masahiro.takahashi
	 *
	 * @param <A> 値のBean
	 * @param <K> 値のキー内容
	 */
	public static interface KeyFinder<A,K> {
		K find(A argument);
	}
	//キーに単一の値がある場合のフィルター

	/**
	 * IDをキーにしたマップをソースにIDのリスト順にマップの値を取得する.
	 *
	 * SQLの複数項目検索でIN検索を行った結果が不定になるので指定したリスト順にそろえる処理用
	 *
	 * @param idMap IDと値の参照元マップ
	 * @param idList IDのリスト
	 * @param nullSkip マップに対応するIDの要素がないかnullの場合に結果に含めない場合true/nullのまま格納する場合false
	 * @return
	 */
	public static <K,V> List<V> filterByIdList(Map<K, V> idMap, List<K> idList, boolean nullSkip) {
		final ArrayList<V> al = new ArrayList<V>(idMap.size());
		for(final K id : idList) {
			final V dto = idMap.get(id);
			if(dto==null && nullSkip) {
				continue;
			}
			al.add(dto);
		}
		return al;
	}
	/**
	 * Beanのリストと、個別のBeanリストからキーを抜き出す処理を組み合わせてMapを作成する.
	 * Mapのキーは重複禁止。もし重複の可能性がある場合は別のフィルターやキーのユニーク生成を保証する必要がある
	 * （あと要素勝ちでマップに含まれる）
	 *
	 * @param srcList Mapに登録するBeanのリスト
	 * @param filter キーの抽出処理
	 * @return キーと対応するBeanのマップ
	 */
	public static <K,V> Map<K, V> filterIdMap(List<V> srcList, CollectionFilter.KeyFinder<V,K> filter) {
		final HashMap<K, V> map = new HashMap<K, V>();
		for(final V value : srcList) {
			map.put(filter.find(value), value);
		}
		return map;
	}

	//キーに複数の値がある場合のフィルター

	public static <K,V> Map<K, List<V>> filterIdMultiValueMap(List<V> srcList, CollectionFilter.KeyFinder<V,K> filter) {
		final HashMap<K, List<V>> map = new HashMap<K, List<V>>();
		for(final V value : srcList) {
			final K key = filter.find(value);
			final List<V> multiValueList;
			if(map.containsKey(key)) {
				multiValueList = map.get(key);
			} else {
				multiValueList = new ArrayList<V>();
				map.put(key, multiValueList);
			}
			multiValueList.add(value);
		}
		return map;
	}

	public static <K,V> List<V> filterIdMultiValueByIdList(Map<K, List<V>> idMultiValueMap, List<K> idList) {
		final ArrayList<V> al = new ArrayList<V>();
		for(final K id : idList) {
			final List<V> dtoList = idMultiValueMap.get(id);
			if(dtoList!=null) {
				al.addAll(dtoList);
			}
		}
		return al;
	}


}
