package logic;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WEBアプリケーション用のパスワードを生成するクラス
 *
 * 使用するライブラリ commons-lang ランダム文字列生成に使用
 *
 * @author chihiro
 *
 */
public class CreatePassword {
	static Logger log = LoggerFactory.getLogger(CreatePassword.class);

	/** パスワードを安全にするためのアルゴリズム */
	private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
	/** ストレッチング回数 */
	private static final int ITERATION_COUNT = 10000;
	/** 生成される鍵の長さ */
	private static final int KEY_LENGTH = 256;
	/** ソルト生成に使用するアルゴリズム */
	private static final String SOLT_HASH_ALGORITHM = "SHA-256";

	/**
	 * 任意の文字列のパスワードソルトを生成する<br>
	 * <p>
	 * パスワードソルトは16バイト以上が推奨されている
	 * </p>
	 *
	 * @param soltlen
	 *            戻り値の文字列長
	 * @return パスワードソルト
	 */
	public static String getHashedSalt(int soltlen) {
		return RandomStringUtils.randomAscii(soltlen);
	}

	/**
	 * 文字列からソルトを生成する。 ユーザIDを想定している。<br>
	 * ※ハッシュは16バイト以上となるアルゴリズムを使用すること<br>
	 * ※16バイトの文字列を返す
	 *
	 * @param userId
	 *            任意の文字列
	 * @return パスワードソルト
	 */
	public static String getHashedSalt(String userId) {
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance(SOLT_HASH_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		messageDigest.update(userId.getBytes());

		// 生成されたバイト配列を16進数の文字列に変換し、先頭16バイトを返す。
		int maxlen = 16;
		byte [] b = messageDigest.digest();
		StringBuilder sb = new StringBuilder(maxlen);
		for(int i=0;i<maxlen/2;i++){
			sb.append(String.format("%02x", b[i] & 0xff));

		}
		return sb.toString();

	}

	/**
	 * 平文のパスワードとソルトから安全なパスワードを生成し、返却します
	 *
	 * @param password
	 *            平文のパスワード
	 * @param salt
	 *            ソルト
	 * @return 安全なパスワード
	 */
	public static String getSafetyPassword(String password, String salt) {

		char[] passCharAry = password.toCharArray();
		String hashedSalt = getHashedSalt(salt);

		PBEKeySpec keySpec = new PBEKeySpec(passCharAry, hashedSalt.getBytes(), ITERATION_COUNT, KEY_LENGTH);

		SecretKeyFactory skf;
		try {
			skf = SecretKeyFactory.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

		SecretKey secretKey;
		try {
			secretKey = skf.generateSecret(keySpec);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
		byte[] passByteAry = secretKey.getEncoded();

		// 生成されたバイト配列を16進数の文字列に変換
		StringBuilder sb = new StringBuilder(64);
		for (byte b : passByteAry) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}
}
