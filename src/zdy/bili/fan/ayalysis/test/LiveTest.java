package zdy.bili.fan.ayalysis.test;

import zdy.bili.fan.ayalysis.manager.BiliApiManager;

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
        for (int i = 0; i < 100; i++) {
            BiliApiManager.getInstance().getLiveinfo(6000+random.nextInt(20000) + "", null);
        }
    }
}
