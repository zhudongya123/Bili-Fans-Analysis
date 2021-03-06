package zdy.bili.fan.ayalysis.test;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import zdy.bili.fan.ayalysis.JsonUtils;
import zdy.bili.fan.ayalysis.bean.UserInfo;
import zdy.bili.fan.ayalysis.manager.BiliApiManager;
import zdy.bili.fan.ayalysis.manager.SQLiteManager;

import java.io.IOException;
import java.util.Random;

/**
 * Created by Zdy on 2016/10/5.
 */
public class RandomUserTest {
    public static void main(String[] args) {
        SQLiteManager.getInstance().initdb();
        String current_mid;
        Random random = new Random();
        for (int i = 0; i < 55000; i++) {
            current_mid = String.valueOf(random.nextInt(3000000));
            BiliApiManager.getInstance().getUserInfo(current_mid, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    UserInfo userInfo = JsonUtils.getUserInfo(result);
                    SQLiteManager.getInstance().insertUser(userInfo);
                    response.close();
                }
            });
        }
    }
}
