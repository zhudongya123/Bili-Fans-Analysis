package zdy.bili.fan.ayalysis;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Test {
	static SQLiteManager manager;

	public static void main(String[] args) {
		//manager = new SQLiteManager();
		//manager.initdb();
		long num=20000000;
		for (int i = 0; i < num; i++) {
			// getInfo("2378679");
			
		}
	}

	private static void getInfo(String mid) {
		BiliApiManager.getInstance().getUserInfo(mid, new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub

				System.out.println("code= " + arg1.code() + arg1.message());
				String raw = arg1.body().string();
				JsonObject object = new JsonParser().parse(raw).getAsJsonObject().get("data").getAsJsonObject();
				int fans = object.get("fans").getAsInt();
				int attention = object.get("attention").getAsInt();
				String nickname = object.get("name").getAsString();
				String regtime = object.get("regtime").getAsString();
				manager.insert(Integer.valueOf(mid), nickname, fans, attention, regtime);
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub

			}
		});
	}

}
