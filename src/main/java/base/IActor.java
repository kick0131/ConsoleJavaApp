package base;

import java.io.Closeable;

/**
 * サンプルコード共通のインタフェースクラス
 * @author chihiro
 *
 */
public interface IActor extends Closeable {

	/**
	 * 実行メソッド
	 * @throws Exception
	 */
	public void action() throws Exception;

	/**
	 * 初期化メソッド
	 * @throws Exception
	 */
	public void init() throws Exception;

	/**
	 * 終了メソッド
	 * @throws Exception
	 */
	public void terminate() throws Exception;

}
