package zdy.bili.user.ayalysis.test;

import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import zdy.bili.user.ayalysis.manager.BiliApiManager;
import zdy.bili.user.ayalysis.manager.SQLiteManager;

public class Test {

    public static void main(String[] args) {
        SQLiteManager.getInstance().initdb();
        try {
            getInfo("" + 2378679);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void getInfo(String mid) throws IOException {
        BiliApiManager.getInstance().getUserInfo(mid, new Callback() {

            @Override
            public void onResponse(Call arg0, Response response) throws IOException {
                // TODO Auto-generated method stub
                JsonArray currentArray;
                System.out.println("getInfo  code= " + response.code() + "  status= " + response.message());
                String raw = response.body().string();
                JsonObject object = new JsonParser().parse(raw).getAsJsonObject().get("data").getAsJsonObject();
                int fans = object.get("fans").getAsInt();
                int attention = object.get("attention").getAsInt();
                String nickname = object.get("name").getAsString();
                String regtime = object.get("regtime").getAsString();
                int playNum = object.get("playNum").getAsInt();

                currentArray = object.get("attentions").getAsJsonArray();

                int flag = 0;
                for (int i = 0; i < currentArray.size(); i++) {// 获取关注列表
                    int innerMid = currentArray.get(i).getAsInt();// 获取每一个uid
                    if (!SQLiteManager.getInstance().queryUser(innerMid)) {

                        int[] result = getInfoNotRecursion(innerMid);
                        int innerAttention = result[0];
                        int fan = result[1];
                        if (i == flag)
                            if (innerAttention > 60)
                                getInfo("" + innerMid);
                            else
                                flag++;
                        if (fan > 1000)
                            getInfo("" + innerMid);

                    }
                }

                SQLiteManager.getInstance().insertUser(Integer.valueOf(mid), nickname, fans, attention, regtime,
                        playNum);

                response.close();
            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {
                // TODO Auto-generated method stub
            }
        });

    }

    private static void getVideo(int mid) {
        // TODO Auto-generated method stub
        BiliApiManager.getInstance().getVideoList(mid, new Callback() {

            @Override
            public void onResponse(Call arg0, Response response) throws IOException {
                // TODO Auto-generated method stub
                System.out.println("getVideo  code= " + response.code() + "  status= " + response.message());
                String raw = response.body().string();
                JsonObject object = new JsonParser().parse(raw).getAsJsonObject().get("data").getAsJsonObject();
                JsonArray vlist = object.get("vlist").getAsJsonArray();
                int length;
                if (vlist.size() > 5)
                    length = 5;
                else
                    length = vlist.size();
                for (int i = 0; i < length; i++) {
                    JsonObject item = vlist.get(i).getAsJsonObject();
                    int aid = item.get("aid").getAsInt();
                    int play = item.get("play").getAsInt();
                    String title = item.get("title").getAsString();
                    int comment = item.get("comment").getAsInt();
                    int favorites = item.get("favorites").getAsInt();
                    String created = item.get("created").getAsString();
                    SQLiteManager.getInstance().insertVideo(aid, Integer.valueOf(mid), play, title, comment, favorites,
                            created);
                }
                response.close();
            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {
                // TODO Auto-generated method stub

            }
        });
    }

    /**
     * 获取用户信息无递归，请求为同步请求，返回该用户的粉丝数目
     *
     * @param mid
     * @return
     * @throws IOException
     */
    private static int[] getInfoNotRecursion(int mid) throws IOException {
        Response response = null;// BiliApiManager.getInstance().getUserInfo(mid);
        System.out.println("code= " + response.code() + "  status= " + response.message());
        String raw = response.body().string();
        JsonObject object = new JsonParser().parse(raw).getAsJsonObject().get("data").getAsJsonObject();
        int fans = object.get("fans").getAsInt();
        int attention = object.get("attention").getAsInt();
        String nickname = object.get("name").getAsString();
        String regtime = object.get("regtime").getAsString();
        int playNum = object.get("playNum").getAsInt();

        SQLiteManager.getInstance().insertUser(mid, nickname, fans, attention, regtime, playNum);
        if (fans > 500) {
            getVideo(mid);
        }
        return new int[]{attention, fans};
    }

}
