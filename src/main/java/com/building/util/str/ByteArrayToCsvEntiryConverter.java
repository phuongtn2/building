package com.building.util.str;

import com.building.services.error.ServiceException;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ByteArrayToCsvEntiryConverter {

	/**
	 * (ストリーム)バイト列データを指定したキャラクタセットで１行ずつパースし、入力データDTOにパースする.
	 *
	 * @param clazz DTOレコードクラス
	 * @param is 入力バイト列ストリーム
	 * @param cs 入力キャラクタセット
	 * @param reader CSV１行読み込み処理(この処理の戻りでnullの場合格納)
	 * @return
	 * @throws IOException
	 */
	public static <T> List<T> convert(Class<T> clazz, InputStream is, Charset cs, CSVLineReader<T> reader) throws IOException {
		final ArrayList<T> al = new ArrayList<T>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is, cs))) {
			String line = null;
			while ((line = br.readLine())!=null) {
				final T dto = reader.readData(line);
				if (dto != null) {
					al.add(dto);
				}
			}
		}
		return al;
	}

	/**
	 * マルチパートのパラメータ（複数の場合１つめ）の参照
	 * @param input マルチパートフォーム入力
	 * @param parameterName パラメータ名
	 * @param errorPrefix エラー時のエラープレフィックス
	 * @return
	 * @throws ServiceException
	 */
	public static InputStream getFirstInputStream(MultipartFormDataInput input, String parameterName, String errorPrefix) throws ServiceException {
		final Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		final List<InputPart> inputParts = uploadForm.get(parameterName);
		if (inputParts != null) {
			for (final InputPart ip : inputParts) {
				try {
					return ip.getBody(InputStream.class, null);
				} catch (IOException e) {
					throw new ServiceException(errorPrefix + ".ioerror", e);
				}
			}
		}
		throw new ServiceException(errorPrefix+".noparameter");
	}
}
