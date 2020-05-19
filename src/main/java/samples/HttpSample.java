package samples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.IActor;

/**
 * HTTPアクセスクラス
 * 
 * @author chihiro
 *
 */
public class HttpSample implements IActor {

	static Logger log = LoggerFactory.getLogger(HttpSample.class);

	// プロキシを利用する場合
	private static String proxyHost = "xxxxxxxx.co.jp";
	private static int proxyPort = 8080;
	private static String proxySwitch = "0";

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void action() throws Exception {

		String url = "https://www.lifehacker.jp/feed/index.xml";
		// url = "http://www.michineko.net/work/index.html";
		// Topページの取得
		httpGet(url);

	}

	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void terminate() throws Exception {
		// TODO Auto-generated method stub

	}

	public String httpGet(String urlStr) {
		HttpURLConnection con = null;
		StringBuffer result = new StringBuffer();

		try {
			URL url;
			url = new URL(urlStr);
			if (proxySwitch.equals("1")) {
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
				con = (HttpURLConnection) url.openConnection(proxy);
			} else {
				con = (HttpURLConnection) url.openConnection();
			}
			// HTTPレスポンスコード
			final int status = con.getResponseCode();
			if (status == HttpURLConnection.HTTP_OK) {
				// 通信に成功した
				// テキストを取得する
				String encoding = con.getContentEncoding();
				if (null == encoding) {
					encoding = "UTF-8";
				}

				try (final InputStream in = con.getInputStream(); //
						final InputStreamReader inReader = new InputStreamReader(in, encoding); //
						final BufferedReader bufReader = new BufferedReader(inReader)) {
					String line = null;
					// 1行ずつテキストを読み込む
					while ((line = bufReader.readLine()) != null) {
						result.append(line);
					}
				}
			} else {
				log.debug("{0}", status);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			if (con != null) {
				// コネクションを切断
				con.disconnect();
			}
		}
		
		// 取得内容の表示
		//log.info(result.toString());
		return result.toString();
	}

	public static String callPost(String urlStr, String strContentType, String formParam) {

		HttpURLConnection con = null;
		StringBuffer result = new StringBuffer();

		try {

			URL url = new URL(urlStr);

			if (proxySwitch.equals("1")) {
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
				con = (HttpURLConnection) url.openConnection(proxy);
			} else {
				con = (HttpURLConnection) url.openConnection();
			}

			// 設定関連のAPIを行う場合はtrueにする
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", strContentType);
			try (OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream())) {
				out.write(formParam);
			}
			con.connect();

			// HTTPレスポンスコード
			final int status = con.getResponseCode();
			if (status == HttpURLConnection.HTTP_OK) {
				// 通信に成功した
				// テキストを取得する
				String encoding = con.getContentEncoding();
				if (null == encoding) {
					encoding = "UTF-8";
				}
				try (final InputStream in = con.getInputStream(); //
						final InputStreamReader inReader = new InputStreamReader(in, encoding); //
						final BufferedReader bufReader = new BufferedReader(inReader);) {
					String line = null;
					// 1行ずつテキストを読み込む
					while ((line = bufReader.readLine()) != null) {
						result.append(line);
					}
				}
			} else {
				log.debug("HttpStatus:{}", Integer.toString(status));
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (con != null) {
				// コネクションを切断
				con.disconnect();
			}
		}
		log.debug("result=" + result.toString());

		return result.toString();

	}
}
