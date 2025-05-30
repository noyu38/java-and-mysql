package testApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class testApp {

	// データベース接続情報
	private static final String DB_URL = "jdbc:mysql://localhost:3306/test_app_db";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "nojiyuma.38";

	/* ユーザ情報をデータベースに登録する
	 * @param name 登録するユーザの名前
	 * @param email 登録するユーザのメールアドレス
	 * @return 登録が成功した場合はtrue, 失敗した場合はfalse
	 */
	public boolean registerUser(String name, String email) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			// データベースに接続
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// SQL文を設定
			String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
			pstmt = conn.prepareStatement(sql);

			// パラメータの設定
			pstmt.setString(1, name); // 1番目にnameをセット
			pstmt.setString(2, email); // 2番目にemailをセット

			// SQL文を実行
			int rowsAffected = pstmt.executeUpdate();

			return rowsAffected > 0;
		} catch (SQLException e) {
			System.err.println("データベース登録エラー: " + e.getMessage());
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.err.println("リソース開放エラー: " + e.getMessage());
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testApp service = new testApp();
		String testName = "テストユーザー";
		String testEmail = "test@example.com";

		if (service.registerUser(testName, testEmail)) {
			System.out.println("ユーザー登録成功: " + testName);
		} else {
			System.out.println("ユーザー登録失敗: " + testName);
		}
	}

}
