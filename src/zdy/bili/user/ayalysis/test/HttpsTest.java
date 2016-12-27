package zdy.bili.user.ayalysis.test;

import okhttp3.*;
import zdy.bili.user.ayalysis.manager.CertificateManager;

import java.io.IOException;

/**
 * Created by Zdy on 2016/12/13.
 */
public class HttpsTest {

    public static void main(String[] args) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(CertificateManager.getINSTANCE().getSslSocketFactory(), CertificateManager.getINSTANCE().getTrustManager())

                .build();

//        jikeExp(okHttpClient);

        z(okHttpClient);
    }

    private static void zhihuExp(OkHttpClient okHttpClient) {
        Headers headers = new Headers.Builder()
                //.add("Accept-Encoding", "gzip")
                .add("Authorization", "Bearer 2.0AADAoJwaAAAAMAKey3n9CgwAAABgAlVNx852WAD_D8QmdVMttkeIigfu2XSFef1ItQ")
//                .add("Cookie", "capsion_ticket=2|1:0|10:1481589166|14:capsion_ticket|44:MWVhYTAzNTIzYzM3NDgwMmJhNTMyODE4MDA3MTRkYWQ=|60d0406e6bb354274c0da4e27711b7ef05c8e6a6756424df8394c3d94fbf7cdc")
//                .add("If-None-Match", "W/\\\"285d19226e5938e78ec67b8945c61072be6d860a\\\\z\\\"")
//                .add("User-Agent", "Futureve/4.12.0Mozilla/5.0(Linux;Android5.1.1;RedmiNote3Build/LMY47V;wv)AppleWebKit/537.36(KHTML,likeGecko)Version/4.0Chrome/45.0.2454.95MobileSafari/537.36Google-HTTP-Java-Client/1.22.0(gzip)x-api-version:3.0.41")
//                .add("x-app-version", " 4.12.0").
//                        add("x-app-za ,", "OS=Android&Release=5.1.1&Model=Redmi+Note+3&VersionName=4.12.0&VersionCode=450&Width=1080&Height=1920&Installer=%E5%BA%94%E7%94%A8%E5%AE%9D")
//                .add("x-app-build", "release")
//                .add("x-udid", "ADACnst5_QpLBVkbY9rJ1tx1jRWuqAEMu-Q=")
//                .add(" x-app-local-unique-id", "1052720")
//                .add("Host", "api.zhihu.com")
//                .add("Connection", "Keep-Alive")
                .build();


        HttpUrl httpUrl = new HttpUrl.Builder().scheme("HTTPS").host("api.zhihu.com").addPathSegment("feeds").build();

        Request request = new Request.Builder().headers(headers).get().url(httpUrl).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                System.out.println(string);
            }
        });
    }

    private static void jikeExp(OkHttpClient okHttpClient) {
        Headers headers = new Headers.Builder()
                .add("Cookie", "io=qsKyV6voKO-585HqAC42; jike:sess.sig=4qjJvafolRDABQR9VxdORRoLzQY; jike:sess=eyJfdWlkIjoiNTg0ZDFhYWM3N2MxNWUxMjAwNTU4ODJlIiwiX3Nlc3Npb25Ub2tlbiI6IjhxRVE5WHhxd0JHcmZtanJuTjNUdHB0Y3IifQ==; jikeSocketSticky=6d8d6981-b579-49e8-a226-b06dc2fba6e3")
                .build();

        HttpUrl httpUrl = new HttpUrl.Builder().scheme("HTTPS").host("app.jike.ruguoapp.com")
                .addPathSegments("1.0/users/topics/list")
                .addQueryParameter("ref", "NEW_TOPIC_MORE")
                .build();

        Request request = new Request.Builder().headers(headers).get().url(httpUrl).build();


        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                System.out.println(string);
            }
        });
    }

    private static void z(OkHttpClient okHttpClient) {

        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("bj.api.happyzebra.fnchi.com")
                .addPathSegments("api.php")
                .addQueryParameter("app", "sj_ar")
                .addQueryParameter("act", "zebra_api")
                .addQueryParameter("uid", "1176")
                .addQueryParameter("action", "call_zebra")
                .build();

        Request request = new Request.Builder().url(url).get().build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                System.out.println(string);
            }
        });

    }


}
