package occamsrazor.iot_server.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-18
 * @time : 14:25
 */
public class JsonUtil {
    /**
     * 判断是JsonObject
     *
     * @param obj
     * @return
     */
    public static boolean isJsonObject(Object obj) {
        String content = obj.toString();
        try {
            JSONObject.parseObject(content);
            return content.startsWith("{");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是JsonArray
     *
     * @param obj
     * @return
     */
    public static boolean isJsonArray(Object obj) {
        String content = obj.toString();
        try {
            JSONArray.parseArray(content);
            return content.startsWith("[");
        } catch (Exception e) {
            return false;
        }
    }
}
