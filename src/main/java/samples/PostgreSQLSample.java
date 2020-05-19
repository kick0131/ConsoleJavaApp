package samples;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import base.IActor;

/**
 * JDBCドライバをクラスパスに追加すること https://jdbc.postgresql.org/download.html#current
 *
 * @author hiramatsu136
 *
 */
@SuppressWarnings("unused")
public class PostgreSQLSample implements IActor {
	static Logger log = LoggerFactory.getLogger(PostgreSQLSample.class);

	@Override
	public void close() throws IOException {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void action() throws Exception {
		readSample();

	}

	@Override
	public void init() throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void terminate() throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

	private void readSample() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			log.error("--- JDBC読み込み時例外発生", e);
		}
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sampledb", "testuser", "testuser");
		} catch (SQLException e) {
			log.error("--- DB接続時例外発生", e);
		}
		Statement statement = null;
		try {
			if (connection != null) {
				statement = connection.createStatement();
			}
		} catch (SQLException e) {
			log.error("--- クエリ生成時例外発生", e);
		}
		String sql = "select * from forjava.t_user";
		ResultSet result = null;
		try {
			if (statement != null) {
				result = statement.executeQuery(sql);
			}
		} catch (SQLException e) {
			log.error("--- クエリ実行時例外発生", e);
		}

		try {
			if (result != null) {
				while (result.next()) {
					log.debug("column(1):" + result.getString(1));
					log.debug("column(2):" + result.getString(2));
					log.debug("column(3):" + result.getString(3));
					log.debug("column(4):" + result.getString(4));
				}
			}
		} catch (SQLException e) {
			log.error("--- クエリ実行時例外発生", e);
		}

		try {
			if (result != null) {
				result.close();
				connection.close();
			}
		} catch (SQLException e) {
			log.error("--- DBアクセス終了時例外発生", e);
		}

	}

	/**
	 * 任意のクエリーの指定した列を取得する(射影)
	 *
	 * @param query クエリ式
	 * @param database 接続文字列 "jdbc:postgresql://localhost:5432/sampledb",
	 * @param username ユーザ "testuser"
	 * @param password パスワード "testuser"
	 * @param columnNum 列番号
	 * @return
	 */
	private List<String> projectionQuery(String query, String database, String username, String password, Integer columnNum) {
		List<String> result = new ArrayList<String>();

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			log.error("--- JDBC読み込み時例外発生", e);
		}
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(database, username, password);
		} catch (SQLException e) {
			log.error("--- DB接続時例外発生", e);
		}
		Statement statement = null;
		try {
			if (connection != null) {
				statement = connection.createStatement();
			}
		} catch (SQLException e) {
			log.error("--- クエリ生成時例外発生", e);
		}
		String sql = "select * from forjava.t_user";
		ResultSet queryresult = null;
		try {
			if (statement != null) {
				queryresult = statement.executeQuery(sql);
			}
		} catch (SQLException e) {
			log.error("--- クエリ実行時例外発生", e);
		}

		try {
			if (queryresult != null) {
				while (queryresult.next()) {
					result.add(queryresult.getString(columnNum));
				}
			}
		} catch (SQLException e) {
			log.error("--- クエリ実行時例外発生", e);
		}

		try {
			if (queryresult != null) {
				queryresult.close();
				connection.close();
			}
		} catch (SQLException e) {
			log.error("--- DBアクセス終了時例外発生", e);
		}
		return result;
	}

}
