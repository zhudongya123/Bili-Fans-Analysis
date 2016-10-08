package zdy.bili.fan.ayalysis.manager;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.Random;

public class BiliApiManager {

    private static final String SCHEME = "http";
    private static final String HOST = "space.bilibili.com";
    private static final String GET_INFO = "ajax/member/GetInfo";
    private static final String GET_ATTENTION = "ajax/friend/GetAttentionList";
    private static final String GET_VIDEO_LIST = "ajax/member/getSubmitVideos";
    private static final String GET_BAN_GU_MI = "bangumi.bilibili.com/anime/";

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
        okHttpClient = new OkHttpClient();
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

}
