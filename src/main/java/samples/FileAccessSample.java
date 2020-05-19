package samples;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import base.IActor;

@SuppressWarnings("unused")
public class FileAccessSample implements IActor{
	static Logger log = LoggerFactory.getLogger(FileAccessSample.class);
	private static final String DUMMYPATH = new String("abcdefg");


	@Override
	public void close() throws IOException {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void action() throws Exception {
		testCall01();

	}

	@Override
	public void init() throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void terminate() throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

	private void testCall01() {
		Path input = Paths.get(System.getProperty("user.dir") + "/sample/pushMessageHistory.json");
		Path output = Paths.get(System.getProperty("user.dir") + "/sample/output.txt");

		try {
			log.info("path:" + input.toRealPath());
		} catch (IOException e) {
			log.error(e.getMessage());
		}

		try {
			String readdata = readJson(input.toRealPath());
			readdata = readdata.replaceAll("a", "X");
			writeJson(output, readdata);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * Calender型を返す
	 */
	private static final String DATE_PATTERN1 = "yyyy-MM-dd HH:mm:ss";
	private static final String DATE_PATTERN2 = "yyyy/MM/dd HH:mm:ss";
	private static final String DATE_PATTERN3 = "yyyyMMdd HHmmss";

	private void testCall02() {
		// 確認用
		SimpleDateFormat separete_minsec = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// 現在時刻をデフォルトとする
		Calendar result = Calendar.getInstance();

		// ファイルパスは固定
		Path input = Paths.get(System.getProperty("user.dir") + "/sample/TimeSetting.txt");

		Pattern p1 = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}");
		Pattern p2 = Pattern.compile("\\d{4}/\\d{1,2}/\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}");
		Pattern p3 = Pattern.compile("\\d{8} \\d{6}");

		SimpleDateFormat dateformat = null;
		FileReader in = null;
		try {
			// 先頭行のみを判定対象とする
			in = new FileReader(input.toString());
			BufferedReader br = new BufferedReader(in);
			String line = br.readLine();
			br.close();

			// 空だった場合
			if ((line == null) || (line.isEmpty())) {

			} else {
				if (p1.matcher(line).matches()) {
					dateformat = new SimpleDateFormat(DATE_PATTERN1);

				} else if (p2.matcher(line).matches()) {
					dateformat = new SimpleDateFormat(DATE_PATTERN2);

				} else if (p3.matcher(line).matches()) {
					dateformat = new SimpleDateFormat(DATE_PATTERN3);

				} else {
					// 日付フォーマットではない場合
					// 現在時刻を使用
					log.info("時刻情報取得結果:" + separete_minsec.format(result.getTime()));
					return;
				}
				// 文字列からDate型生成
				Date date = new Date(dateformat.parse(line).getTime());
				result.setTime(date);
			}

		} catch (FileNotFoundException e) {
			// ファイルが無かった場合をはじめとしたファイルアクセスエラー
			// 現在時刻を使用

		} catch (IOException e) {
			// 異常終了
			log.error(e.getMessage());

		} catch (ParseException e) {
			// 文字列が異常であった場合
			// 現在時刻を使用

		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				// 異常終了 基本的にあり得ない
			}
		}

		log.info("時刻情報取得結果:" + separete_minsec.format(result.getTime()));
	}

	/**
	 * ファイルを読み込み、内容を文字列として返す
	 *
	 * @param jsonfilepath
	 *            Jsonファイルパス
	 * @return ファイル内容
	 * @throws IOException
	 */
	private String readJson(Path jsonfilepath) throws IOException {
		FileReader in = null;
		String json = "";
		String line = "";
		try {
			// ファイルの読み込み
			log.info("path :" + jsonfilepath);
			in = new FileReader(jsonfilepath.toString());
			BufferedReader br = new BufferedReader(in);
			while ((line = br.readLine()) != null) {
				json += line;
			}
			br.close();
			log.info("jsonData :" + json);

		} catch (FileNotFoundException e) {
			log.error("--- createJsonString ---", e);
		} catch (IOException e) {
			log.error("--- createJsonString ---", e);
		} finally {
			if (in != null) {
				in.close();
			}
		}

		return json;
	}

	/**
	 * ファイル出力処理
	 *
	 * @param jsonfilepath
	 *            Jsonファイルパス
	 * @return ファイル内容
	 * @throws IOException
	 */
	private String writeJson(Path jsonfilepath, String writeData) throws IOException {
		FileWriter in = null;
		String json = "";
		try {
			// ファイルの読み込み
			log.info("path :" + jsonfilepath);
			in = new FileWriter(jsonfilepath.toString());
			BufferedWriter bw = new BufferedWriter(in);
			PrintWriter pw = new PrintWriter(bw);
			pw.print(writeData);
			bw.close();

		} catch (FileNotFoundException e) {
			log.error("--- createJsonString ---", e);
		} catch (IOException e) {
			log.error("--- createJsonString ---", e);
		} finally {
			if (in != null) {
				in.close();
			}
		}

		return json;
	}

	/**
	 * Finalフィールドの表示処理
	 * 外部アクセスする為、publicで定義
	 */
	public void dispFinalField() {
		log.info("--- DUMMYPATH:" + DUMMYPATH);
	}

}
