package samples;

import java.io.IOException;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import base.IActor;
import logic.CreatePassword;

public class CreatePasswordSample implements IActor {
	static Logger log = LoggerFactory.getLogger(CreatePasswordSample.class);

	@Override
	public void close() throws IOException {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void action() throws Exception {
		soltCheck();
		// passwordCheck();

	}

	@Override
	public void init() throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void terminate() throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

	/**
	 * パスワードソルト生成テスト
	 */
	private String soltCheck() {
		// 標準入力から読み込み
		System.out.println("ソルトを生成します。ユーザ名を入力してください");
		System.out.print("ユーザ名:");

		try (Scanner cin = new Scanner(System.in)) {
			String userid = cin.nextLine();
			// cin.close();

			log.info(String.format("userId:%s", userid));
			if (userid != null) {
				String soltedStr = CreatePassword.getHashedSalt(userid);

				log.info(String.format("solt:%s", soltedStr));

				return soltedStr;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return "";
	}

	/**
	 * パスワードハッシュ生成テスト
	 */
	@SuppressWarnings("unused")
	private void passwordCheck() {
		String solt = soltCheck();

		// 標準入力から読み込み
		System.out.println("パスワードハッシュを生成します。パスワードを入力してください");
		System.out.print("パスワード:");
		Scanner cin = new Scanner(System.in);
		String password = cin.nextLine();
		cin.close();
		String hashedPassword = CreatePassword.getSafetyPassword(password, solt);

		log.info(String.format("hashedPassword:%s", hashedPassword));
	}
}
