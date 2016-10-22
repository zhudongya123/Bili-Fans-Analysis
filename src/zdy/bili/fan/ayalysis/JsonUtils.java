package zdy.bili.fan.ayalysis;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import zdy.bili.fan.ayalysis.bean.LiveUserInfo;
import zdy.bili.fan.ayalysis.bean.UserInfo;

import java.text.SimpleDateFormat;

/**
 * Created by Zdy on 2016/10/5.
 */
public class JsonUtils {

    private static JsonParser jsonParser = new JsonParser();
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static UserInfo getUserInfo(String raw) {
        UserInfo userInfo = new UserInfo();
        JsonObject root = jsonParser.parse(raw).getAsJsonObject();
        if (!root.get("status").getAsBoolean())
            return null;
        else {
            JsonObject info = root.get("data").getAsJsonObject();
            userInfo.mid = info.get("mid").getAsString();
            userInfo.name = info.get("name").getAsString();
            userInfo.approve = info.get("approve").getAsBoolean();
            userInfo.sex = info.get("sex").getAsString();
            userInfo.rank = info.get("rank").getAsString();
            userInfo.face = info.get("face").getAsString();
            userInfo.coins = info.get("coins").getAsFloat();
            userInfo.DisplayRank = info.get("DisplayRank").getAsString();
            userInfo.regtime = simpleDateFormat.format(new java.util.Date(info.get("regtime").getAsLong() * 1000));
            userInfo.spacesta = info.get("spacesta").getAsInt();//用户状态
            userInfo.birthday = info.get("birthday").getAsString();
            userInfo.place = info.get("place").getAsString();// 所在地
            userInfo.description = info.get("description").getAsString();//描述
            userInfo.attentions = info.get("attentions").getAsJsonArray();
            userInfo.fans = info.get("fans").getAsInt();
            userInfo.friend = info.get("friend").getAsInt();
            userInfo.attention = info.get("attention").getAsInt();
            userInfo.sign = info.get("sign").getAsString();

            JsonObject level_info = info.get("level_info").getAsJsonObject();
            userInfo.level = level_info.get("current_level").getAsInt();
            userInfo.current_exp = level_info.get("current_exp").getAsInt();

            JsonObject official_verify = info.get("official_verify").getAsJsonObject();
            userInfo.official_desc = official_verify.get("desc").getAsString();
            userInfo.official_status = official_verify.get("type").getAsInt();

            userInfo.playNum = info.get("playNum").getAsInt();

            return userInfo;
        }
    }

    public static LiveUserInfo getLiveUserInfo(LiveUserInfo liveUserInfo, String raw) {
        JsonObject root = jsonParser.parse(raw).getAsJsonObject();
        JsonObject jsonObject=root.get("data").getAsJsonObject();
        liveUserInfo.areaId = jsonObject.get("AREAID").getAsInt();
        liveUserInfo.masterId=jsonObject.get("MASTERID").getAsInt();
        liveUserInfo.nickname=jsonObject.get("ANCHOR_NICK_NAME").getAsString();
        liveUserInfo.liveStatus=jsonObject.get("_status").getAsString();
        liveUserInfo.rcost=jsonObject.get("RCOST").getAsInt();
        liveUserInfo.fans_count=jsonObject.get("FANS_COUNT").getAsInt();

        return liveUserInfo;
    }
}
