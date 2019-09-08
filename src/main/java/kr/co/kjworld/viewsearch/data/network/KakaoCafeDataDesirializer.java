package kr.co.kjworld.viewsearch.data.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import kr.co.kjworld.viewsearch.data.response.data.CafeData;
import kr.co.kjworld.viewsearch.data.response.data.Document;

public class KakaoCafeDataDesirializer implements JsonDeserializer<CafeData> {
    @Override
    public CafeData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!CafeData.class.getTypeName().equals(typeOfT.getTypeName()))
            return null;

        CafeData data = new CafeData();

        JsonObject imageJsonObject =  json.getAsJsonObject();
        JsonArray documentArray = imageJsonObject.getAsJsonArray("documents");

        for (int i = 0; i < documentArray.size(); i++)
        {
            Document document = new Document();
            JsonObject ownerJsonObject = documentArray.get(i).getAsJsonObject();
            document.cafename = ownerJsonObject.get("cafename").getAsString();
            document.thumbnail = ownerJsonObject.get("thumbnail").getAsString();
            document.datetime = ownerJsonObject.get("datetime").getAsString();
            document.title = ownerJsonObject.get("title").getAsString();
            document.isCafe = true;
            data.documents.add(document);
        }

        return data;
    }
}
