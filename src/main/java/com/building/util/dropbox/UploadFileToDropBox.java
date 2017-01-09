package com.building.util.dropbox;

import com.dropbox.core.*;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;


/**
 * Created by PhuongTN1 on 4/1/2016.
 */

public class UploadFileToDropBox {

	private static final String SUFFIX = "/";

	Properties setting = new Properties();
	public UploadFileToDropBox(Properties setting){
		this.setting = setting;
	}

	public DbxWebAuthNoRedirect  open (){
		final String APP_KEY = setting.getProperty("drop.box.access.key.id");
		final String APP_SECRET = setting.getProperty("drop.box.secret.access.key");
		DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
		DbxRequestConfig config = new DbxRequestConfig(
				"pn/1.0", Locale.getDefault().toString());
		DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
		return  webAuth;
	}

	public DbxClientV2 getClient(){
		DbxRequestConfig config = new DbxRequestConfig("pn/1.0", "en_US");
		DbxClientV2 client = new DbxClientV2(config, "w2YU9qOHkIcAAAAAAAAAJhDbPP6COHLN2bz9KZV3Nj8UZlLYVNggCgESIfkuNr_C");
		return  client;
	}
	public String upload(InputStream data, String fileName){
		byte[] contents = new byte[0];
		try {
			contents = IOUtils.toByteArray(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		DbxClientV2 client = getClient();
		InputStream stream = new ByteArrayInputStream(contents);
		try {
			FileMetadata metadata = client.files().uploadBuilder("/" + fileName)
					.uploadAndFinish(stream);
			return metadata.getRev();
		} catch (DbxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";

	}

	public DbxDownloader<FileMetadata> download(String fileName){
		DbxClientV2 client = getClient();
		DbxDownloader<FileMetadata> fileMetadataDbxDownloader = null;
		try {
			fileMetadataDbxDownloader = client.files().download("/" + fileName);
		} catch (DbxException e) {
			e.printStackTrace();
		}
		return  fileMetadataDbxDownloader;
	}

	public void delete(String fileName){
		DbxClientV2 client = getClient();
		try {
			client.files().delete("/" + fileName);
		} catch (DbxException e) {
			e.printStackTrace();
		}
	}

}
