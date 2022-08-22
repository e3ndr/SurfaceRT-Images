package xyz.e3ndr.imageos.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.Data;

@Data
@JsonClass(exposeAll = true)
public class Image {
    private String name;

    private String version;

    @JsonField("release_date")
    private String releaseDate;

    private long size;

    @JsonField("size_str")
    private String formattedSize;

    @JsonExclude
    private Map<String, String> links = new HashMap<>();

    @JsonDeserializationMethod("links")
    private void $deserialize_links(JsonElement e) {
        for (Entry<String, JsonElement> entry : e.getAsObject().entrySet()) {
            this.links.put(entry.getKey(), entry.getValue().getAsString());
        }
    }

}
