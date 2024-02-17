package fragments.objectHelper;
/**
 * Created by mt.karimi on 5/19/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.acra.sender.AcraLSender;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import fragments.FireHelper;
import fragments.objects.BackGroundProperties;
import fragments.objects.ImageProperties;
import fragments.objects.TextProperties;
import mt.karimi.ronevis.ApplicationLoader;

public class GsonParser {
    public static Map<Integer, TextProperties> jsonToMapText(String jsonString) {
        Type type = new TypeToken<Map<Integer, TextProperties>>() {
        }.getType();
        Gson gson = new Gson();
        return gson.fromJson(jsonString, type);
    }

    public static Map<Integer, ImageProperties> jsonToMapImage(String jsonString) {
        Type type = new TypeToken<Map<Integer, ImageProperties>>() {
        }.getType();
        Gson gson = new Gson();
        return gson.fromJson(jsonString, type);
    }

    public static Map<Integer, BackGroundProperties> jsonToMapBackGround(String jsonString) {
        Type type = new TypeToken<Map<Integer, BackGroundProperties>>() {
        }.getType();
        Gson gson = new Gson();
        return gson.fromJson(jsonString, type);
    }

    public static void main(String[] args) {
        try {
            print(loadJsonArray("data_array.json", true));
            print(loadJsonObject("data_object.json", true));
        } catch (Exception ignored) {
            FireHelper fireHelper = new FireHelper();
            fireHelper.SendReport(ignored);
        }
    }

    private static void print(Object object) {
        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(object));
    }

    private static Map<String, Object> loadJsonObject(String filename, boolean isResource)
            throws UnsupportedEncodingException, FileNotFoundException, JsonIOException, JsonSyntaxException, MalformedURLException {
        return jsonToMap(loadJson(filename, isResource).getAsJsonObject());
    }

    private static List<Object> loadJsonArray(String filename, boolean isResource)
            throws UnsupportedEncodingException, FileNotFoundException, JsonIOException, JsonSyntaxException, MalformedURLException {
        return jsonToList(loadJson(filename, isResource).getAsJsonArray());
    }

    private static JsonElement loadJson(String filename, boolean isResource) throws UnsupportedEncodingException, FileNotFoundException, JsonIOException, JsonSyntaxException, MalformedURLException {
        return new JsonParser().parse(new InputStreamReader(FileLoader.openInputStream(filename, isResource), "UTF-8"));
    }

    public static Object parse(JsonElement json) {
        if (json.isJsonObject()) {
            return jsonToMap((JsonObject) json);
        } else if (json.isJsonArray()) {
            return jsonToList((JsonArray) json);
        }
        return null;
    }

    private static Map<String, Object> jsonToMap(JsonObject jsonObject) {
        if (jsonObject.isJsonNull()) {
            return new HashMap<>();
        }
        return toMap(jsonObject);
    }

    private static List<Object> jsonToList(JsonArray jsonArray) {
        if (jsonArray.isJsonNull()) {
            return new ArrayList<>();
        }
        return toList(jsonArray);
    }

    private static Map<String, Object> toMap(JsonObject object) {
        Map<String, Object> map = new HashMap<>();
        for (Entry<String, JsonElement> pair : object.entrySet()) {
            map.put(pair.getKey(), toValue(pair.getValue()));
        }
        return map;
    }

    private static List<Object> toList(JsonArray array) {
        List<Object> list = new ArrayList<>();
        for (JsonElement element : array) {
            list.add(toValue(element));
        }
        return list;
    }

    private static Object toPrimitive(JsonPrimitive value) {
        if (value.isBoolean()) {
            return value.getAsBoolean();
        } else if (value.isString()) {
            return value.getAsString();
        } else if (value.isNumber()) {
            return value.getAsNumber();
        }
        return null;
    }

    private static Object toValue(JsonElement value) {
        if (value.isJsonNull()) {
            return null;
        } else if (value.isJsonArray()) {
            return toList((JsonArray) value);
        } else if (value.isJsonObject()) {
            return toMap((JsonObject) value);
        } else if (value.isJsonPrimitive()) {
            return toPrimitive((JsonPrimitive) value);
        }
        return null;
    }
}