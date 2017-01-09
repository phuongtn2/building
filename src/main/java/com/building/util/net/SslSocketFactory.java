package com.building.util.net;

import org.springframework.core.io.Resource;

import javax.net.SocketFactory;
import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;

public class SslSocketFactory extends SSLSocketFactory {

	private static Resource keyResource_;
	private static String keypass_;
	//
	private final SSLSocketFactory sslSocketFactory;

	public SslSocketFactory(SSLSocketFactory sslSocketFactory) {
		super();
		this.sslSocketFactory = sslSocketFactory;
	}

	public static void init(Resource keyResource, String keypass) {
		keyResource_ = keyResource;
		keypass_ = keypass;
	}
    synchronized public static SocketFactory getDefault() {
    	SSLContext sslContext = null;
		if (keyResource_ != null) {
			try {
				final char[] storePass = keypass_.toCharArray();
				final KeyStore keyStore = KeyStore.getInstance("JKS");
				try (InputStream is = keyResource_.getInputStream()) {
					keyStore.load(is, storePass);
				}

				KeyManagerFactory keyManFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
				keyManFactory.init(keyStore, storePass);

				TrustManagerFactory trustManFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
				trustManFactory.init(keyStore);

				KeyManager[] keyManager = keyManFactory.getKeyManagers();
				TrustManager[] trustManager = trustManFactory.getTrustManagers();

				sslContext = SSLContext.getInstance("SSL");
				sslContext.init(keyManager, trustManager, null);
			} catch (Exception e) {
				// LOG初期化失敗
				sslContext = null;
			}
		}
		if (sslContext == null) {
			try {
				sslContext = SSLContext.getDefault();
			} catch (NoSuchAlgorithmException e) {
				return null;
			}
		}
    	return new SslSocketFactory(sslContext.getSocketFactory());
    }

	@Override
	public String[] getDefaultCipherSuites() {
		return sslSocketFactory.getDefaultCipherSuites();
	}
	@Override
	public String[] getSupportedCipherSuites() {
		return sslSocketFactory.getSupportedCipherSuites();
	}

	@Override
	public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		return sslSocketFactory.createSocket(s, host, port, autoClose);
	}

	@Override
	public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
		return sslSocketFactory.createSocket(host, port);
	}

	@Override
	public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
		return sslSocketFactory.createSocket(host, port, localHost, localPort);
	}

	@Override
	public Socket createSocket(InetAddress host, int port) throws IOException {
		return sslSocketFactory.createSocket(host, port);
	}

	@Override
	public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
		return sslSocketFactory.createSocket(address, port, localAddress, localPort);
	}

}
