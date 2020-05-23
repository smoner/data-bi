
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.util.Collection;
import java.util.List;
import org.cpu.common.utils.json.JsonBinder;

public class JsonUtils {
    private static JsonBinder jsonBinder = JsonBinder.buildNonNullBinder();

    public JsonUtils() {
    }

    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        return jsonBinder.fromJson(jsonString, clazz);
    }

    public static <T> List<T> fromJsonArray(String jsonArray, Class<T> clazz) {
        return jsonBinder.fromJsonArray(jsonArray, clazz);
    }

    public static Collection fromJsonArrayBy(String jsonArray, Class recordClazz, Class collectionClazz) {
        return jsonBinder.fromJsonArrayBy(jsonArray, recordClazz, collectionClazz);
    }

    public static String toJson(Object object) {
        return jsonBinder.toJson(object);
    }

    public static void setDateFormat(String pattern) {
        jsonBinder.setDateFormat(pattern);
    }

    public static String returnWhenError(String errMsg, Object data) {
        return returnResult(errMsg, data, 0);
    }

    public static String returnWhenSuccess(String errMsg, Object data) {
        return returnResult(errMsg, data, 1);
    }

    public static String returnResult(String errMsg, Object data, int status) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", Integer.valueOf(status));
        jsonObject.put("msg", errMsg);
        jsonObject.put("data", JSONObject.toJSONString(data, new SerializerFeature[]{SerializerFeature.WriteMapNullValue}));
        return jsonObject.toString();
    }
}
