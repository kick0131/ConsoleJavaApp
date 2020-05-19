import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import base.IActor;
import logic.RssReader;
import net.arnx.jsonic.JSONException;
import samples.BasicAction;
import samples.HttpSample;
import samples.InputSample;

@SuppressWarnings("restriction")
public class ConsoleMain {
	static Logger log = LoggerFactory.getLogger(ConsoleMain.class);

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception, JSONException, IOException {

		IActor actor = null;

		// log4j設定ファイル読み込み(log4j ver1.2の場合は必須)
		// DOMConfigurator.configure("log4j.xml");

		log.info("--- {} start ---", "main");

		// 基本形
		// BasicSample07();

		try {

			// 基本動作
			// actor = new BasicAction();

			// 標準入力
			// actor = new InputSample();

			// クラスローダー
			// actor = new ClassLoaderSample();

			// JSON練習
			// actor = new JsonSample();

			// ハッシュチェック
			// actor = new CreatePasswordSample();

			// ファイルアクセス
			// actor= new FileAccessSample();

			// XMLアクセス
			// actor = new XPathSample();

			// DBアクセス
			// actor = new PostgreSQLSample();

			// Httpアクセス
//			actor = new HttpSample();
			
			// RSSリーダー
			actor = new RssReader();

			// 処理実行
			actor.action();

		} finally {
			// 終了処理
			actor.terminate();

		}

		actor = null;
		log.info("--- main end ---");
	}

	// 日付
	public static void BasicSample01() {
		Calendar c = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c2.add(Calendar.MINUTE, -60);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH0000000");
		log.info("--- 日付のフォーマットテスト:" + sdf.format(c.getTime()));
		log.info("--- 日付のフォーマットテスト:" + sdf.format(c2.getTime()));

	}

	// 文字列置換
	public static void BasicSample02() {
		// \\ → \
		String regex = "\\\\";
		String testdata = "本日は\\\\n晴天なり";

		Pattern p = Pattern.compile(regex);
		@SuppressWarnings("unused")
		Matcher m = p.matcher(testdata);
		Matcher.quoteReplacement("\\");
		// String result = m.replace("\\");

		String result = testdata.replace(regex, "\\");
		log.info("--- Before:" + testdata);
		log.info("--- After :" + result);

		testdata = "body";
		result = testdata.replace("body", "\"body\"");
		log.info("--- Before:" + testdata);
		log.info("--- After :" + result);

	}

	// 文字列分割
	@SuppressWarnings("unchecked")
	public static void BasicSample03() {
		String regex;
		String testdata;
		String[] resultArr;

		// フォーマットチェック
		testdata = "XXX[01].YYY[987].ZZZ";
		testdata = "L2[1]";
		testdata = "M2.m2L[0]";
		testdata = "sample";
		regex = "\\.";
		int checklen = testdata.split(regex).length;
		if ((checklen <= 0) || (3 < checklen)) {
			log.info("--- NG");
		}

		// 各要素に分割できない場合はNG
		Pattern idxPtn = Pattern.compile("^.+\\[(\\d+)\\]$");
		Pattern keyPtn = Pattern.compile("\\[\\d+\\]$");
		resultArr = testdata.split(regex);
		List<Map<String, Integer>> dataidx = new LinkedList<Map<String, Integer>>();
		String keyval;
		String idxval;
		for (String item : resultArr) {
			log.info(" --- item:" + item);
			keyval = keyPtn.matcher(item).replaceAll("");
			idxval = idxPtn.matcher(item).replaceAll("$1");
			log.info(keyval);
			log.info(idxval);
			Map<String, Integer> addDataIdx = new LinkedHashMap<String, Integer>();
			try {
				Integer.parseInt(idxval);
				addDataIdx.put(keyval, Integer.parseInt(idxval));
			} catch (NumberFormatException e) {
				addDataIdx.put(keyval, null);
			}
			dataidx.add(addDataIdx);
		}

		// log.info("--- Display Start ---");
		// for(Map<String,Integer> item:dataidx){
		// for(String key:item.keySet()){
		// log.info("key:"+key+" val:"+item.get(key));
		// }
		// }

		log.info("--- Debug disp ---");
		Map<String, Object> root = new LinkedHashMap<String, Object>();
		Map<String, Object> m1 = new LinkedHashMap<String, Object>();
		Map<String, Object> m2 = new LinkedHashMap<String, Object>();
		List<String> L1 = new LinkedList<String>();
		List<String> L2 = new LinkedList<String>();
		m1.put("m11", "m1-01");
		m1.put("m12", "m1-02");
		m2.put("m21", "m2-01");
		m2.put("m22", "m2-02");
		m2.put("m2L", L1);
		L1.add("L1-01");
		L1.add("L1-02");
		L2.add("L2-01");
		L2.add("L2-02");
		root.put("M1", m1);
		root.put("M2", m2);
		root.put("L2", L2);
		root.put("sample", "sample");

		Object getval = root;
		int idx = 0;
		for (Map<String, Integer> item : dataidx) {
			// 最初の要素のみ使用
			for (String key : item.keySet()) {
				log.info("key:" + key + " val:" + item.get(key));
				keyval = key;
				if (item.get(key) == null) {
					if (getval instanceof Map) {
						getval = ((Map<String, Object>) getval).get(keyval);
					} else if (getval instanceof List) {
						getval = ((List<String>) getval).get(0);
					}
				} else {
					idx = item.get(key).intValue();
					if (getval instanceof Map) {
						getval = ((Map<String, Object>) getval).get(keyval);
					}
					if (getval instanceof List) {
						getval = ((List<String>) getval).get(idx);
					}
				}

				break;
			}
			if (getval == null) {
				log.info("--- 検索失敗:終了 ---");
				return;
			}
		}
		log.info("--- 検索成功:" + (String) getval);

	}

	// 値の最小、最大
	public static void BasicSample04() {
		log.info("--- 最小・最大 ---");
		System.out.println("Short.MAX_VALUE: " + Short.MAX_VALUE);
		System.out.println("Short.MIN_VALUE: " + Short.MIN_VALUE);
		System.out.println("Integer.MAX_VALUE: " + Integer.MAX_VALUE);
		System.out.println("Integer.MIN_VALUE: " + Integer.MIN_VALUE);
		System.out.println("Long.MAX_VALUE: " + Long.MAX_VALUE);
		System.out.println("Long.MIN_VALUE: " + Long.MIN_VALUE);
		System.out.println("Float.MAX_VALUE: " + Float.MAX_VALUE);
		System.out.println("Float.MIN_VALUE: " + Float.MIN_VALUE);
		System.out.println("Double.MAX_VALUE: " + Double.MAX_VALUE);
		System.out.println("Double.MIN_VALUE: " + Double.MIN_VALUE);
	}

	// 文字列操作２
	public static void BasicSample05() {
		String[] strArr =
			{ "AAAA", "BBBB", "AAAA", "CCCC" };
		List<String> listStr = Arrays.asList(strArr);
		// 重複除去
		log.info(Arrays.toString(listStr.stream().distinct().toArray()));
		// 外側のカッコ除去
		log.info(Arrays.toString(listStr.stream().distinct().toArray()).replaceAll("(\\[|\\])", ""));

	}

	// 日付２
	public static void BasicSample06() throws ParseException {
		Calendar before = Calendar.getInstance();
		Calendar after = Calendar.getInstance();
		Calendar before2 = Calendar.getInstance();

		String datetime = "20160325123456789";
		String datetime2 = "20160325123440000";

		SimpleDateFormat normal = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

		// 文字列からDate型生成
		Date date = new Date(sdf.parse(datetime).getTime());
		before.setTime(date);

		date = new Date(sdf.parse(datetime2).getTime());
		after.setTime(date);

		// 時刻差
		long millisecond = after.getTimeInMillis() - before.getTimeInMillis();

		log.info("--- before:" + normal.format(before.getTime()));
		log.info("--- after :" + normal.format(after.getTime()));
		log.info("--- 時刻差:" + millisecond);

		// 1時間前の59分59秒
		before2.add(Calendar.HOUR, -1);
		before2.set(Calendar.MINUTE, 59);
		before2.set(Calendar.SECOND, 59);
		log.info("--- 時刻設定確認:" + normal.format(before2.getTime()));

	}

}
