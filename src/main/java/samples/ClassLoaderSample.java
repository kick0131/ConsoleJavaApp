package samples;

import java.io.IOException;
import java.net.URLClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import base.IActor;

/**
 * コンソールアプリケーションが利用するクラスローダの種類を調べる 作成時では以下のクラスローダが利用されていることが分かった。 アプリケーションクラスローダ(AppClassLoader) システムクラスローダ(ExtClassLoader)
 *
 * @author chihiro
 *
 */
public class ClassLoaderSample implements Runnable, IActor {
	static Logger log = LoggerFactory.getLogger(ClassLoaderSample.class);
	@Override
	public void close() throws IOException {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void action() throws Exception {
		run();

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
	 * メイン処理
	 */
	@Override
	public void run() {
		try {
			viewClassLoaderList(this.getClass().getClassLoader());
		} catch (IOException e) {
			log.error(e.getStackTrace().toString());
		}
	}

	/**
	 * 使用しているクラスローダーおよび検索URLのリストを出力する。
	 *
	 * @throws IOException
	 */
	private void viewClassLoaderList(ClassLoader cl) throws IOException {

		// 再帰終了
		if (cl == null) {
			return;
		}

		// クラスローダ出力
		log.debug("ClassLoader:{}", cl);

		// URL出力
		URLClassLoader urlcl = (URLClassLoader) cl;
		log.info("--- URLs ---");
		debugArgItems(urlcl.getURLs());

		// 再帰
		viewClassLoaderList(cl.getParent());
	}

	/**
	 * 任意の型の配列要素をログ出力
	 *
	 * @param items
	 */
	private <T> void debugArgItems(T[] items) {
		for (T item : items) {
			log.info(item.toString());
		}
	}

}
