package fragments.download.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by mt.karimi on 6/17/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class FileObject {
    JsonObject dlfile;

    public FileObject() {
    }

    public String getFileObject() {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();
        return gson.toJson(dlfile);
    }

    public JsonObject getKeysFromJson() {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(gson.toJson(dlfile)).getAsJsonObject();
    }

    public JsonObject getFileObjects() {
        return dlfile;
    }
}