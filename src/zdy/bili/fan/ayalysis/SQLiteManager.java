package zdy.bili.fan.ayalysis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteManager {

	private static final SQLiteManager INSTANCE = new SQLiteManager();

	Connection c = null;

	private SQLiteManager() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
			System.out.println("Opened database successfully");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static SQLiteManager getInstance() {
		return INSTANCE;
	}

	public void initdb() {
		try {
			Statement stmt = c.createStatement();
			String sql = "CREATE TABLE BILI_INFO " + "(MID INT PRIMARY KEY, " + "NICKNAME TEXT, " + "FANS INT, "
					+ "ATTENTION INT, " + "REGISTER TEXT, " + "TOTAL_NUM INT" + ")";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE BILI_VIDEO " + "(AID INT PRIMARY KEY, " + "MID INT, " + "PLAY_COUNT INT, "
					+ "TITLE TEXT, " + "COMMENT INT, " + "FAVORITES INT, " + "CREATED TEXT" + ")";
			stmt.executeUpdate(sql);

			System.out.println("Table created successfully");

			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean queryUser(int mid){
		String sql;
		try {
			Statement stmt = c.createStatement();
			sql = "SELECT * FROM BILI_INFO WHERE MID=" + mid + ";";
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("skip item");
			if (rs.next()) {
			stmt.close();
				return true;
			} else {
				stmt.close();
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public void insertUser(int mid, String nickname, int fans, int attention, String registerTime, int total_num) {
		String sql;
		try {
			Statement stmt = c.createStatement();
			sql = "SELECT * FROM BILI_INFO WHERE MID=" + mid + ";";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				System.out.println("!!!!!!BILI_INFO skip item");
				return;
			} else {
				sql = "INSERT INTO BILI_INFO (MID,NICKNAME,FANS,ATTENTION,REGISTER,TOTAL_NUM) " + "VALUES (" + mid
						+ ", '" + nickname + "', " + fans + ", " + attention + ", '" + registerTime + "'," + total_num
						+ " );";
				stmt.executeUpdate(sql);
				System.out.println("ADD BILI_INFO item");
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertVideo(int aid, int mid, int play, String title, int comment, int favorites, String created) {
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM BILI_VIDEO WHERE AID=" + aid + ";");
			if (rs.next()) {
				return;
			} else {
				String sql = "INSERT INTO BILI_VIDEO (AID,MID,PLAY_COUNT,TITLE,COMMENT,FAVORITES,CREATED) " + "VALUES ("
						+ aid + "," + mid + "," + play + ", '" + title + "', " + comment + ", " + favorites + ", '"
						+ created + "' );";
				stmt.executeUpdate(sql);
				System.out.println("ADD BILI_VIDEO item");
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
