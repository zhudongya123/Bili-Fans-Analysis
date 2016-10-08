package zdy.bili.fan.ayalysis.manager;

import zdy.bili.fan.ayalysis.bean.UserInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteManager {

    private static final SQLiteManager INSTANCE = new SQLiteManager();
    private static int i = 0;

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

    public void insertUser(UserInfo userInfo) {
        String sql;
        try {
            Statement stmt = c.createStatement();
            sql = "SELECT * FROM BILI_USER_INFO_TABLE WHERE MID=" + userInfo.mid + ";";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return;
            } else {
                sql = "INSERT INTO BILI_USER_INFO_TABLE " +
                        "(MID,NAME,APPROVE,SEX,RANK," +
                        "FACE,COINS,DISPLAYRANK,REGTIME,SPACESTA," +
                        "BIRTHDAY,PLACE,DESCRIPTION,ATTENTIONS,FANS," +
                        "FRIEND,ATTENTION,SIGN,CURRENT_EXP,LEVEL," +
                        "OFFICIAL_STATUS,OFFICIAL_DESC,PLAYNUM) "
                        + "VALUES ('"
                        + userInfo.mid + "', '" + userInfo.name + "', '" + String.valueOf(userInfo.approve) + "', '"
                        + userInfo.sex + "', '" + userInfo.rank + "', '" + userInfo.face + "', '"
                        + String.valueOf(userInfo.coins) + "', '" + userInfo.DisplayRank + "', '" + userInfo.regtime + "',"
                        + userInfo.spacesta + ",'" + userInfo.birthday + "', '" + userInfo.place + "', '" + userInfo.description + "', '"
                        + userInfo.attentions.toString() + "', " + userInfo.fans + ", " + userInfo.friend + ", " + userInfo.attention + ", '"
                        + userInfo.sign + "', " + userInfo.current_exp + ", " + userInfo.level + ", " + userInfo.official_status + ", '"
                        + userInfo.official_desc + "', " + userInfo.playNum
                        + " );";
                stmt.executeUpdate(sql);
                i++;
                System.out.println("ADD BILI_INFO item" + "第 " + i + " 条数据，mid = " + userInfo.mid);
            }
            stmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void initdb() {
        try {
            Statement stmt = c.createStatement();
            String sql;
            //创建用户信息数据库
            sql = "CREATE TABLE BILI_USER_INFO_TABLE "
                    + "(MID TEXT PRIMARY KEY, " + "NAME TEXT, " + "APPROVE TEXT, " + "SEX TEXT, " + "RANK TEXT, "
                    + "FACE TEXT, " + "COINS TEXT, " + "DISPLAYRANK TEXT, " + "REGTIME TEXT, " + "SPACESTA INT, "
                    + "BIRTHDAY TEXT, " + "PLACE TEXT, " + "DESCRIPTION TEXT, " + "ATTENTIONS TEXT, " + "FANS INT, "
                    + "FRIEND INT, " + "ATTENTION INT, " + "SIGN TEXT, " + "CURRENT_EXP INT, " + "LEVEL INT, "
                    + "OFFICIAL_STATUS INT, " + "OFFICIAL_DESC TEXT, " + "PLAYNUM INT" + ")";
            stmt.executeUpdate(sql);

            //创建视频信息数据库
//            sql = "CREATE TABLE BILI_VIDEO " + "(AID INT PRIMARY KEY, " + "MID INT, " + "PLAY_COUNT INT, "
//                    + "TITLE TEXT, " + "COMMENT INT, " + "FAVORITES INT, " + "CREATED TEXT" + ")";
//            stmt.executeUpdate(sql);

            //创建番组数据库
//            sql = "CREATE TABLE BAN_GU_MI_INFO " + "(ID INT PRIMARY KEY, " + "TITLE TEXT, " + "VIEWING_TIMES TEXT, " + "WATCHED_PEOPLE TEXT, "
//                    + "DANMAKU_COUNT TEXT, " + "DATE TEXT, " + "STATUS TEXT" + ")";
//            stmt.executeUpdate(sql);

            System.out.println("Table created successfully");

            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入单条番组数据
     *
     * @param id
     * @param title
     * @param viewing_time  观看次数
     * @param watched_times 观看人数
     * @param danmaku_count 弹幕数量
     * @param date          开播日期
     * @param status        当前状态
     */
    public void insertBangumi(String id, String title, String viewing_time, String watched_times,
                              String danmaku_count, String date, String status) {
        String sql;
        try {
            Statement stmt = c.createStatement();
            sql = "INSERT INTO BAN_GU_MI_INFO (ID,TITLE,VIEWING_TIMES,WATCHED_PEOPLE,DANMAKU_COUNT,DATE,STATUS) " + "VALUES (" + id
                    + ", '" + title + "', '" + viewing_time + "', '" + watched_times + "', '" + danmaku_count + "', '" + date + "','" + status
                    + "' );";
            stmt.executeUpdate(sql);
            System.out.println("ADD BAN_GU_MI_INFO item");
            stmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 查询用户是否在数据库中
     *
     * @param mid 用户id
     * @return true表示数据库已经存在
     */
    public boolean queryUser(int mid) {
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

    /**
     * 将单个用户数据插入到数据库中
     *
     * @param mid
     * @param nickname
     * @param fans
     * @param attention
     * @param registerTime
     * @param total_num
     */
    public void insertUser(int mid, String nickname, int fans, int attention, String registerTime, int total_num) {
        String sql;
        try {
            Statement stmt = c.createStatement();
            sql = "SELECT * FROM BILI_INFO WHERE MID=" + mid + ";";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
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
