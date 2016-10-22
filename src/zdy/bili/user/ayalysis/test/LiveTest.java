package zdy.bili.user.ayalysis.test;

import zdy.bili.user.ayalysis.manager.BiliApiManager;
import zdy.bili.user.ayalysis.manager.SQLiteManager;

import java.util.Random;

/**
 * Created by Zdy on 2016/10/17.
 */
public class LiveTest {
    private static Random random;

    static {
        random = new Random();
    }

    public static void main(String[] args) {
      //  SQLiteManager.getInstance().initdb();
        for (int i = 0; i < 100; i++) {
            BiliApiManager.getInstance().getLiveinfo(6000 + random.nextInt(20000) + "", liveUserInfo -> {
                SQLiteManager.getInstance().insertLiveUser(liveUserInfo);
            });
        }
    }
}
