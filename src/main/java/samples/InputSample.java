package samples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import base.IActor;

/**
 * 入出力処理サンプルクラス
 *
 * @author chihiro
 *
 */
public class InputSample implements IActor {
	static Logger log = LoggerFactory.getLogger(InputSample.class);

	public void action() throws IOException {
		log.info(String.format("=== %s start ===", "action()"));
		System.in.read();
		// パターン１ BufferdReader
		bufferdreader();
		// パターン２ Scanner
		scanner1();
		scanner2();

		log.info(String.format("=== %s end ===", "action()"));
	}

	/**
	 *
	 * @throws IOException
	 */
	public void bufferdreader() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("文字列を入力してください。");
		String str = in.readLine();
		System.out.println("入力した文字列は " + str + " です。");
	}

	/**
	 *
	 */
	public void scanner1() {
		try (Scanner in = new Scanner(System.in)) {
			System.out.println("文字列を入力してください。");
			String str = in.nextLine();
			System.out.println("入力した文字列は " + str + " です。");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void scanner2() {
		try (Scanner in = new Scanner(System.in)) {
			System.out.println("文字列を入力してください。");
			String str = in.next();
			System.out.println("入力した文字列は " + str + " です。");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void close() throws IOException {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void init() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void terminate() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
