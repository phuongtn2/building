package com.building.util;

import com.building.dto.JsonErrorDto;
import com.building.services.error.ServiceException;
import com.building.services.interceptor.GhDefaultExceptionMapper;
import org.springframework.context.support.ResourceBundleMessageSource;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import java.util.Locale;

public class RestMessageUtil {
	/** メッセージリソースからエラーコードを指定する後置文字列 */
	private static final String CODE_POSTFIX = ".code";
	/** メッセージリソースからエラーメッセージを指定する後置文字列 */
	private static final String MESSAGE_POSTFIX = ".message";
	/** メッセージリソースからエラーメッセージを指定する後置文字列 */
	private static final String STATUS_POSTFIX = ".status";

	/** メッセージリソース引数なし */
	private static final Object[] NO_ARGUMENT = new Object[] {};

	/**
	 * エラーコードを再作成するためのユーティリティ.
	 * @param e 上位発生例外（null可能)
	 * @param errorCode 新しいエラーコード
	 * @param arguments 新しいエラーメッセージ引数
	 * @return 新たな例外
	 */
	public static ServiceException repack(Throwable e, String errorCode, Object ... arguments) {
		final ServiceException ne = e==null ? //
				new ServiceException(errorCode) : new ServiceException(errorCode, e);
		ne.setArguments(arguments);
		return ne;
	}
	public static Response makeResponse(ResourceBundleMessageSource messageSource, String messageCode, Object... args) {

		final Locale loc = Locale.getDefault();
		final String sc = messageSource.getMessage(messageCode + STATUS_POSTFIX, NO_ARGUMENT, loc);
		final JsonErrorDto errorDto = new JsonErrorDto();
		errorDto.setStatusCode(messageSource.getMessage(messageCode + CODE_POSTFIX, NO_ARGUMENT, loc));
		errorDto.setMessage(messageSource.getMessage(messageCode + MESSAGE_POSTFIX, formatObjectArray(args), loc));


		final Response.Status status = Response.Status.valueOf(sc);
		final ResponseBuilder rb = Response.status(status);
		rb.entity(errorDto);
		return rb.build();
	}
	/**
	 * メッセージリソースの引数で数字は、カンマ成形されると困るので文字列に置き換える
	 * @param srcs
	 * @return
	 */
	public static Object[] formatObjectArray(final Object[] srcs) {
		if(srcs==null || srcs.length==0) {
			return GhDefaultExceptionMapper.NO_ARGUMENT;
		} else {
			final Object[] ra = new Object[srcs.length];
			for(int i=0; i<srcs.length; i++) {
				final Object o = srcs[i];
				if(o instanceof Number) {
					ra[i] = ((Number)o).toString();
				} else {
					ra[i] = o;
				}
			}
			return ra;
		}
	}

}
