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
import kr.co.kjworld.viewsearch.data.response.data.KakaoData;

public class KakaoDataDeserializer implements JsonDeserializer<KakaoData> {
    @Override
    public KakaoData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!KakaoData.class.getTypeName().equals(typeOfT.getTypeName()))
            return null;

        KakaoData data = new KakaoData();

        JsonObject imageJsonObject =  json.getAsJsonObject();
        JsonArray documentArray = imageJsonObject.getAsJsonArray("documents");

        for (int i = 0; i < documentArray.size(); i++)
        {
            Document document = new Document();
            JsonObject ownerJsonObject = documentArray.get(i).getAsJsonObject();
            if (ownerJsonObject.get("blogname") != null) {
                document.name = ownerJsonObject.get("blogname").getAsString();
                document.thumbnail = ownerJsonObject.get("thumbnail").getAsString();
                document.datetime = ownerJsonObject.get("datetime").getAsString();
                document.title = ownerJsonObject.get("title").getAsString();
                document.isCafe = false;
            } else if (ownerJsonObject.get("cafename") != null) {
                document.name = ownerJsonObject.get("cafename").getAsString();
                document.thumbnail = ownerJsonObject.get("thumbnail").getAsString();
                document.datetime = ownerJsonObject.get("datetime").getAsString();
                document.title = ownerJsonObject.get("title").getAsString();
                document.isCafe = false;
            }

            data.documents.add(document);
        }

        return data;
    }
}
