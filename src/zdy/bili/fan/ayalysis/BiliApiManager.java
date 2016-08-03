package zdy.bili.fan.ayalysis;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BiliApiManager {

	private static final String SCHEME = "http";
	private static final String HOST = "space.bilibili.com";
	private static final String GET_INFO = "ajax/member/GetInfo";
	private static final String GET_ATTENTION = "ajax/friend/GetAttentionList";
	private static final String GET_VIDEO_LIST = "ajax/member/getSubmitVideos";

	private static final BiliApiManager INSTANCE = new BiliApiManager();

	private OkHttpClient okHttpClient;

	private BiliApiManager() {
		init();
	}

	public static BiliApiManager getInstance() {
		return INSTANCE;
	}

	private void init() {
		// TODO Auto-generated method stub
		okHttpClient = new OkHttpClient();
	}

	public void getUserInfo(int mid, Callback callback) {
		HttpUrl url = new HttpUrl.Builder().scheme(SCHEME).host(HOST).addPathSegments(GET_INFO)
				.addQueryParameter("mid", mid+"").build();

		Request request = new Request.Builder().url(url).build();
		okHttpClient.newCall(request).enqueue(callback);

	}

	public Response getUserInfo(int mid) {
		HttpUrl url = new HttpUrl.Builder().scheme(SCHEME).host(HOST).addPathSegments(GET_INFO)
				.addQueryParameter("mid", mid+"").build();

		Request request = new Request.Builder().url(url).build();
		try {
			return okHttpClient.newCall(request).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void getAttention(String mid, Callback callback) {
		HttpUrl url = new HttpUrl.Builder().scheme(SCHEME).host(HOST).addPathSegments(GET_ATTENTION)
				.addQueryParameter("mid", mid).build();

		Request request = new Request.Builder().url(url).build();
		okHttpClient.newCall(request).enqueue(callback);
	}

	public void getVideoList(int mid, Callback callback) {
		HttpUrl url = new HttpUrl.Builder().scheme(SCHEME).host(HOST).addPathSegments(GET_VIDEO_LIST)
				.addQueryParameter("mid", mid+"").addQueryParameter("order", "click").build();

		Request request = new Request.Builder().url(url).build();
		okHttpClient.newCall(request).enqueue(callback);
	}

}
