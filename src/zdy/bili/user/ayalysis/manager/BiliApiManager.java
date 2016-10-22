package zdy.bili.user.ayalysis.manager;

import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import rx.Observable;
import rx.functions.Action1;
import zdy.bili.user.ayalysis.JsonUtils;
import zdy.bili.user.ayalysis.bean.LiveUserInfo;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.Random;

public class BiliApiManager {

    private static final String SCHEME = "http";
    private static final String HOST = "space.bilibili.com";
    private static final String GET_INFO = "ajax/member/GetInfo";
    private static final String GET_ATTENTION = "ajax/friend/GetAttentionList";
    private static final String GET_VIDEO_LIST = "ajax/member/getSubmitVideos";
    private static final String GET_BAN_GU_MI = "bangumi.bilibili.com/anime/";

    private static final String LIVE_HOST = "live.bilibili.com";
    private static final String LIVE_USER_INFO = "live/getInfo";

    private static final BiliApiManager INSTANCE = new BiliApiManager();

    private OkHttpClient okHttpClient;
    private Random random;

    private BiliApiManager() {
        init();
    }

    public static BiliApiManager getInstance() {
        return INSTANCE;
    }

    private void init() {
        // TODO Auto-generated method stub
        okHttpClient = new OkHttpClient.Builder().build();
        random = new Random();
    }

    public void getBangumiInfo(String id, Callback callback) {
        HttpUrl url = HttpUrl.parse(SCHEME + "://" + GET_BAN_GU_MI + id);
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public void getUserInfo(String mid, Callback callback) {
        HttpUrl url = new HttpUrl.Builder().scheme(SCHEME).host(HOST).addPathSegments(GET_INFO)
                .addQueryParameter("mid", mid + "").build();

        FormBody formBody = new FormBody.Builder().add("mid", mid).build();
        Headers headers = getHeaders("" + random.nextInt(10000000));
        Request request = new Request.Builder().headers(headers).url(url).post(formBody).build();
        okHttpClient.newCall(request).enqueue(callback);

    }


    private Headers getHeaders() {
        return getHeaders(null);
    }

    private Headers getHeaders(String mid) {
        Headers headers = new Headers.Builder().set("Accept", "application/json, text/javascript, */*; q=0.01")
                .set("AlexaToolbar-ALX_NS_PH", "AlexaToolbar/alx-4.0")
                .set("Referer", "http://space.bilibili.com/" + (mid == null ? "1/" : (mid + "/")))
                .set("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36")
                .set("Content-Type", "application/x-www-form-urlencoded").set("DNT", "1").build();
        return headers;
    }

    // public Response getUserInfo(int mid) {
    // HttpUrl url = new
    // HttpUrl.Builder().scheme(SCHEME).host(HOST).addPathSegments(GET_INFO)
    // .addQueryParameter("mid", mid + "").build();
    //
    // Request request = new Request.Builder().url(url).build();
    // try {
    // return okHttpClient.newCall(request).execute();
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // return null;
    // }

    public interface LiveInfoCallBack {
        void onResponse(LiveUserInfo liveUserInfo);
    }

    public void getLiveinfo(String liveId, LiveInfoCallBack callback) {
        final LiveUserInfo[] liveUserInfo = {new LiveUserInfo()};

        HttpUrl prepareUrl = new HttpUrl.Builder().scheme(SCHEME).host(LIVE_HOST).addPathSegment(liveId).build();
        Request prepareRequest = new Request.Builder().url(prepareUrl).build();

        Action1<Integer> liveInfoAction = integer -> {
            HttpUrl url = new HttpUrl.Builder().scheme(SCHEME).host(LIVE_HOST)
                    .addPathSegments(LIVE_USER_INFO).addQueryParameter("roomid", String.valueOf(integer)).build();
            Request request = new Request.Builder().url(url).build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    response.body().close();
                    System.out.println(result);
                    liveUserInfo[0] = JsonUtils.getLiveUserInfo(liveUserInfo[0], result);
                    callback.onResponse(liveUserInfo[0]);
                }
            });
        };

        okHttpClient.newCall(prepareRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("");
                System.out.println("liveid = " + liveId);
                if (response.code() == 404) {
                    System.out.println("liveid = " + "不存在");
                    return;
                }
                String htmlString = response.body().string();
                response.body().close();
                Document document = Jsoup.parse(htmlString);
                String result = document.head().getAllElements().get(18).childNodes().get(0).attributes().get("data");
                StringBuilder builder = new StringBuilder(result);
                builder.indexOf("");
                liveUserInfo[0].roomId = Integer.valueOf(builder.substring(builder.indexOf("=") + 2, builder.indexOf(";")));
                builder.delete(0, builder.indexOf(";") + 2);
                liveUserInfo[0].danmakuRnd = Integer.valueOf(builder.substring(builder.indexOf("=") + 2, builder.indexOf(";")));
                builder.delete(0, builder.indexOf(";") + 2);
                builder.delete(0, builder.indexOf(";") + 2);
                liveUserInfo[0].url = Integer.valueOf(builder.substring(builder.indexOf("=") + 2, builder.indexOf(";")));

                Observable.just(liveUserInfo[0].roomId).subscribe(liveInfoAction);
            }
        });

    }

    public void getAttention(String mid, Callback callback) {
        HttpUrl url = new HttpUrl.Builder().scheme(SCHEME).host(HOST).addPathSegments(GET_ATTENTION)
                .addQueryParameter("mid", mid).build();

        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public void getVideoList(int mid, Callback callback) {
        HttpUrl url = new HttpUrl.Builder().scheme(SCHEME).host(HOST).addPathSegments(GET_VIDEO_LIST)
                .addQueryParameter("mid", mid + "").addQueryParameter("order", "click").build();

        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public SSLSocketFactory setCertificates(InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);
            sslContext.init
                    (
                            null,
                            trustManagerFactory.getTrustManagers(),
                            new SecureRandom()
                    );
            return sslContext.getSocketFactory();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
