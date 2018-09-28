package de.torsm.diff.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import difflib.Delta;

import java.lang.reflect.Type;

/**
 * Serializes Deltas, but leaves out redundant chunks.
 * The original chunk is not relevant if lines have only been added, and the revised chunk is not relevant if lines have
 * been deleted.
 */
public final class DeltaSerializer implements JsonSerializer<Delta> {

    @Override
    public JsonElement serialize(Delta src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        switch (src.getType()) {
            case CHANGE:
                obj.add("original", context.serialize(src.getOriginal()));
                obj.add("revised", context.serialize(src.getRevised()));
                break;
            case INSERT:
                obj.add("inserted", context.serialize(src.getRevised()));
                break;
            case DELETE:
                obj.add("deleted", context.serialize(src.getOriginal()));
        }
        return obj;
    }
}
