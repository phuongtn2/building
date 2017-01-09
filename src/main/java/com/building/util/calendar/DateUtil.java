package com.building.util.calendar;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * 指定日時からmonthヶ月前の00:00の日時を取得する.
	 *
	 * @param date 指定日時
	 * @param month monthヶ月前指定
	 * @return 指定日時のmonthヶ月前の00:00の日付
	 */
	public static Date beforeMonth(Date date, int month) {
		final Calendar cal = makeTodayStart(date.getTime());
		cal.add(Calendar.MONTH, -month);
		return cal.getTime();
	}

	/**
	 * 指定日時からmonthヶ月前の00:00の日時を取得する.
	 *
	 * @param date 指定日時
	 * @param day day日前指定
	 * @return 指定日時のday日前の00:00の日付
	 */
	public static Date beforeDay(Date date, int day) {
		final Calendar cal = makeTodayStart(date.getTime());
		cal.add(Calendar.DAY_OF_MONTH, -day);
		return cal.getTime();
	}

	/**
	 * 指定日時以降かを判定する（同じ日時の場合true)
	 * @param current
	 * @param when
	 * @return
	 */
	public static boolean equalAfter(Date current, Date when) {
		return current.getTime()>=when.getTime();
	}
	/**
	 * 指定システムミリ秒の本日00:00の取得.
	 * @param systemMili システムミリ秒
	 * @return
	 */
	private static Calendar makeTodayStart(long systemMili) {
		final Calendar cal = Calendar.getInstance();
		//基準時間を設定
		cal.setTimeInMillis(systemMili);
		//時以降のフィールドをクリア(指定日の00:00)
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		return cal;
	}

	public static Calendar endOfDay(long systemMili) {
		final Calendar cal = Calendar.getInstance();
		//基準時間を設定
		cal.setTimeInMillis(systemMili);

		//時以降のフィールドをクリア(指定日の23:59:59.999)
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal;
	}

}
