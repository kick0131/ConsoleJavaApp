import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import base.IActor;
import base.Utility;
import logic.RssReader;
import net.arnx.jsonic.JSONException;
import samples.*;
import samples.json.*;

public class ConsoleMain {
	static Logger log = LoggerFactory.getLogger(ConsoleMain.class);

	public static void main(String[] args) throws Exception, JSONException, IOException {

		List<IActor> actor = new ArrayList<IActor>();

		// log4j設定ファイル読み込み(log4j ver1.2の場合は必須)
		// DOMConfigurator.configure("log4j.xml");

		log.info("--- {} start ---", Utility.getMethodName());

		// 基本動作
		actor.add(new BasicAction());

		// 標準入力
		// actor.add(new InputSample());

		// クラスローダー
		// actor.add(new ClassLoaderSample());

		// JSON練習
		actor.add(new JsonicSample());
		actor.add(new JacksonSample());
		actor.add(new GsonSample());

		// ハッシュチェック
		// actor.add(new CreatePasswordSample());

		// ファイルアクセス
		// actor.add(new FileAccessSample());

		// DBアクセス
		// actor.add(new PostgreSQLSample());

		// Http
		// actor.add(new HttpSample());

		// RSSリーダー
		// actor.add(new RssReader());

		// 処理実行
		for (IActor item : actor) {
			try {
				item.init();
				item.action();
			} finally {
				item.terminate();
			}
		}

		log.info("--- {} end ---", Utility.getMethodName());
	}
}
