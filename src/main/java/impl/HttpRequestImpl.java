package impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bean.HttpJsonBean;
import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;

@SuppressWarnings("deprecation")
public class HttpRequestImpl {
	static Logger log = LoggerFactory.getLogger(HttpRequestImpl.class);

	/** コンテントタイプ */
	public static final String HEADER_CONTENT_JSON = "application/json";
	/** ステータスコード */
	public static final String STATUS_CODE = "status_code";
	/** オプションキー:プロキシホスト */
	public static final String OPTION_PROXY_HOST = "proxyhost";
	/** オプションキー:プロキシポート */
	public static final String OPTION_PROXY_PORT = "proxyport";
	/** オプションキー:デバッグプリント */
	public static final String OPTION_DEBUGPRINT = "debugprint";
	/** HTTPリクエストで扱う文字コード */
	public static final String ENCODE = "UTF-8";
	/** JSON形式のHTTPリクエストメッセージの空データ({}のみ)時のサイズ */
	public static final int JSON_EMPTY_DATA = 2;
	/** HTTPステータス正常 */
	public static final int STATUS_CODE_OK = 200;

	/**
	 * HTTPリクエスト発行
	 *
	 * @param url
	 * @param method
	 * @param sendObject
	 *            JSON形式で記載されたHTTPパラメータ 以下のキーワードに対応する query HTTPクエリパラメータを定義 ※省略可
	 *            header HTTPヘッダパラメータを定義 body HTTPリクエストパラメータを定義 ※省略可
	 *
	 *            例 { query:{ "username":"test", "email":"test@example.com" },
	 *            header:{ "X-Application-Id":"12345",
	 *            "X-Application-Key":"67890" }, body:{
	 *            "query":{"channels":["chan1","chan2"]}, "message":"test
	 *            message" } }
	 * @param option
	 *            オプション 指定できるオプションは下記 key value proxyhost プロキシ使用時のホスト proxyport
	 *            プロキシ使用時のポート debugprint 出力内容を表示して終了する
	 *
	 * @return HttpResponseBean ステータスコード、HTTPレスポンス(JSON形式) エラー時は空オブジェクトを返す
	 */
	public void httpRequest(String url, String method, String sendObject, Map<String, String> option) {
		log.info("--- sendObject :" + sendObject);

		HttpJsonBean impl = new HttpJsonBean();
		impl = JSON.decode(sendObject, HttpJsonBean.class);

		// クエリパラメータ設定
		// 空データ{}時は設定対象外
		String queryStr = JSON.encode(impl.getQuery());
		if (queryStr.length() > JSON_EMPTY_DATA) {
			log.info("--- setQueryParameter len:" + queryStr.length() + " val:" + queryStr);
			url += ("?" + JsonToHttpQuery(queryStr));
		}

		// リクエストボディ設定
		log.trace("--- setBody ---");
		String bodyStr = JSON.encode(impl.getBody());
		log.info("HTTP BODY :" + bodyStr);
		HttpRequestBase request = null;
		switch (method) {
		case "GET":
			log.trace("--- HTTP GET ---");
			request = new HttpGet(url);
			break;
		case "POST":
			log.trace("--- HTTP POST ---");
			request = new HttpPost(url);
			if (bodyStr.length() > JSON_EMPTY_DATA) {
				((HttpEntityEnclosingRequestBase) request).setEntity(new StringEntity(bodyStr, ENCODE));
			}
			break;
		}

		// HTTPリクエストヘッダ設定
		log.trace("--- setHeader ---");
		for (@SuppressWarnings("rawtypes")
		Iterator i = impl.getHeader().entrySet().iterator(); i.hasNext();) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) i.next();
			log.info((String) entry.getKey() + " : " + (String) entry.getValue());
			request.setHeader((String) entry.getKey(), (String) entry.getValue());
		}

		@SuppressWarnings("resource")
		HttpClient httpClient = new DefaultHttpClient();
		Map<String, Object> httpResult = new LinkedHashMap<String, Object>();

		// オプション設定
		if (option != null) {
			// デバッグ表示オプション
			if (option.containsKey(OPTION_DEBUGPRINT)) {
				if (request != null) {
					log.info("--- URL   : " + request.getURI());
				}
				log.info("--- method: " + method);
				log.info("--- debugPrint option. method end. --- ");
				return;
			}

			// プロキシオプション
			if ((option.get(OPTION_PROXY_HOST).isEmpty() == false)
					&& (option.get(OPTION_PROXY_PORT).isEmpty() == false)) {
				log.info("--- setProxy host: " + option.get(OPTION_PROXY_HOST) + ":" + option.get(OPTION_PROXY_PORT));
				httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
						new HttpHost(option.get(OPTION_PROXY_HOST), Integer.parseInt(option.get(OPTION_PROXY_PORT))));
			}
		} else {
			log.trace("option is null ");
		}

		try {
			log.info("--- execute URL: " + request.getURI());
			HttpResponse response = httpClient.execute(request);
			// ステータスコードの設定
			if (response.getStatusLine().getStatusCode() != STATUS_CODE_OK) {
				log.error("--- " + STATUS_CODE + " : " + response.getStatusLine().getStatusCode());

			}
			// レスポンスボディの設定
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream is = entity.getContent();
				String data = istreamToString(is);
				log.info("--- response : " + data);

				try {
					Map<String, Object> dec = JSON.decode(data);
					httpResult.putAll(dec);
				} catch (JSONException e) {
					log.error(e.getMessage());
					return;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return;
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

		log.trace("--- callHttpRequest end ---");

		return;
	}

	/**
	 * JSON形式のデータをHTTPクエリ形式に変換する 対応するのは以下のフォーマットのみ 変換前
	 * {"key1":"value1","key2":"value2","keyN":"valueN"} 変換後
	 * key1=value1&key2=value2&keyN=valueN
	 *
	 * @param input
	 *            変換対象文字列
	 * @return
	 */
	private String JsonToHttpQuery(String input) {
		String queryStr = input.replaceAll("[\\{|\\}|\\\"]", "");
		String queryStr2 = queryStr.replaceAll(":", "=");
		String output = queryStr2.replaceAll(",", "&");
		return output;
	}

	/**
	 * インプットストリームを文字列に変更
	 *
	 * @param is
	 *            インプットストリーム
	 * @return 文字列
	 * @throws IOException
	 *             処理例外
	 */
	private String istreamToString(InputStream is) throws IOException {
		String data = "";
		{
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			for (;;) {
				int len = is.read(buffer);
				if (len < 0) {
					break;
				}
				bs.write(buffer, 0, len);
			}
			data = new String(bs.toByteArray(), "UTF-8");
		}
		return data;
	}

}
