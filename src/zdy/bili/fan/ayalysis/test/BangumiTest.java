package zdy.bili.fan.ayalysis.test;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import zdy.bili.fan.ayalysis.manager.BiliApiManager;
import zdy.bili.fan.ayalysis.manager.SQLiteManager;

import java.io.IOException;

public class BangumiTest {
    public static void main(String[] args) {
        SQLiteManager.getInstance().initdb();
        for (int i = 1; i < 5527; i++) {
            bangumi(String.valueOf(i));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void bangumi(String id) {
        BiliApiManager.getInstance().getBangumiInfo(id, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // TODO Auto-generated method stub
                String result = response.body().string();
                Document document = Jsoup.parse(result);
                Elements data = document.getElementsByClass("info-count");
                Elements children = data.get(0).children();


                String title = document.title();

                Element element = children.get(0);
                String viewing_time = element.child(1).html();
                viewing_time = handlerBigDecimal(viewing_time);

                element = children.get(1);
                String watched_people = element.child(1).html();
                watched_people = handlerBigDecimal(watched_people);

                element = children.get(2);
                String danmaku_count = element.child(1).html();
                danmaku_count = handlerBigDecimal(danmaku_count);

                Elements p = data.parents();
                String date = p.get(0).child(2).child(0).child(0).html();
                String status = p.get(0).child(2).child(0).child(1).html();
                SQLiteManager.getInstance().insertBangumi(id, title, viewing_time, watched_people, danmaku_count, date, status);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                // TODO Auto-generated method stub

            }
        });
    }

    private static String handlerBigDecimal(String number) {
        long bigDecimal = 0L;
        StringBuffer buffer = new StringBuffer(number);
        if (number.contains("万")) {
            buffer.delete(buffer.length() - 1, buffer.length());
            bigDecimal = (long) (Float.valueOf(buffer.toString()) * 10000f);
        }
        return String.valueOf(bigDecimal);
    }
}
