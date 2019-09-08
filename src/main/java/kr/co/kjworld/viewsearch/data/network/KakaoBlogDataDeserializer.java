package kr.co.kjworld.viewsearch.data.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import kr.co.kjworld.viewsearch.data.response.data.BlogData;
import kr.co.kjworld.viewsearch.data.response.data.Document;

public class KakaoBlogDataDeserializer implements JsonDeserializer<BlogData> {
    @Override
    public BlogData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!BlogData.class.getTypeName().equals(typeOfT.getTypeName()))
            return null;

        BlogData data = new BlogData();

        JsonObject imageJsonObject =  json.getAsJsonObject();
        JsonArray documentArray = imageJsonObject.getAsJsonArray("documents");

        for (int i = 0; i < documentArray.size(); i++)
        {
            Document document = new Document();
            JsonObject ownerJsonObject = documentArray.get(i).getAsJsonObject();
            document.cafename = ownerJsonObject.get("blogname").getAsString();
            document.thumbnail = ownerJsonObject.get("thumbnail").getAsString();
            document.datetime = ownerJsonObject.get("datetime").getAsString();
            document.title = ownerJsonObject.get("title").getAsString();
            document.isCafe = false;
            data.documents.add(document);
        }

        return data;
    }
}
