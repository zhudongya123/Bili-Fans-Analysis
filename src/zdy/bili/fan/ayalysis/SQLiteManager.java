package zdy.bili.fan.ayalysis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteManager {

	private static final SQLiteManager INSTANCE = new SQLiteManager();

	Connection c = null;
	Statement stmt = null;

	private SQLiteManager() {

	}

	public static SQLiteManager getInstance() {
		return INSTANCE;
	}

	public void initdb() {
		try {
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:test.db");
				System.out.println("Opened database successfully");

				stmt = c.createStatement();
				String sql = "CREATE TABLE BILI_INFO " + "(MID INT PRIMARY KEY, " + "NICKNAME TEXT, " + "FANS INT, "
						+ "ATTENTION INT, " + "REGISTER TEXT)";
				stmt.executeUpdate(sql);
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
			System.out.println("Table created successfully");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insert(int mid, String nickname, int fans, int attention, String registerTime) {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM BILI_INFO WHERE MID=" + mid + ";");
			if (rs.next()) {
				return;
			}

			String sql = "INSERT INTO BILI_INFO (MID,NICKNAME,FANS,ATTENTION,REGISTER) " + "VALUES (" + mid + ", '"
					+ nickname + "', " + fans + ", " + attention + ", '" + registerTime + "' );";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			stmt.close();
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
