package xyz.e3ndr.imageos.api;

import java.util.List;

import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Data;

@Data
@JsonClass(exposeAll = true)
public class ImageFlavor {
    public static final TypeToken<List<ImageFlavor>> TYPE_TOKEN = new TypeToken<>() {
    };

    @JsonField("flavor")
    private String name;

    private List<Image> images;

}
