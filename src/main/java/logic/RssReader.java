package logic;

import java.util.Map;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.IActor;
import samples.HttpSample;

/**
 * RSSリーダー<br>
 * ・URLを指定してRSSフィードを取得<br>
 * ・URLとサマリを取得してログ出力<br>
 * 
 * @author chihiro
 *
 */
public class RssReader implements IActor{
	static Logger log = LoggerFactory.getLogger(RssReader.class);

	public void getSummary() throws IOException {
		String urlStr = "https://www.lifehacker.jp/feed/index.xml";
		HttpSample http = new HttpSample();

		// RSSフィードを取得
		String rssfeed = null;
		try {
			rssfeed = http.httpGet(urlStr);
		} finally {
			http.close();
			if ((rssfeed == null) || (rssfeed.isEmpty())) {
				log.error("rssfeed is empty:{}", rssfeed);
			}
		}

		// URLとサマリの組み合わせを取得
		// Map<String, SimpleEntry<String, String>> rssSummary = createRssSummaryFromString(rssfeed);
		String getRssItemXPath = "/rss/channel/item";
		// ★ToDo XPath操作がJava1.8->Java13で使用不可になっている。

		// 取得内容を表示
		// viewRssSummary(rssSummary);
	}

	/**
	 * RSSサマリ取得<br>
	 * <br>
	 * XML形式のRSSフィード文字列を解析し、以下の内容を取得<br>
	 * ・タイトル<br>
	 * ・URL<br>
	 * ・サマリ<br>
	 * 
	 * @param rssFeed
	 * @return
	 */
	private Map<String, SimpleEntry<String, String>> createRssSummaryFromString(String rssFeed) {

		return null;

	}

	/**
	 * RSSサマリ内容表示
	 * 
	 * @param rssSummary
	 */
	private void viewRssSummary(Map<String, SimpleEntry<String, String>> rssSummary) {
		rssSummary.forEach((key, value) -> {
			log.info("-----------------------------------------------");
			log.info("title:{} url:{}", key, value.getKey());
			log.info("summary:{} ", value.getValue());
		});
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void action() throws Exception {
		getSummary();
		
	}

	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void terminate() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
