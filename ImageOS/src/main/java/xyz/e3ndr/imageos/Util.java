package xyz.e3ndr.imageos;

import java.io.IOException;
import java.util.List;

import co.casterlabs.rakurai.json.Rson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import xyz.e3ndr.imageos.api.ImageFlavor;

public class Util {
    // TODO directly download from mega, this may work:
    // https://gist.github.com/zanculmarktum/170b94764bd9a3da31078580ccea8d7e

    private static OkHttpClient client = new OkHttpClient();

    public static boolean hasInternetConnection() {
        try (
            Response response = client.newCall(
                new Request.Builder()
                    .url("https://neverssl.com/")
                    .build()
            ).execute()) {

            String body = response.body().string();

            if (body.contains("NeverSSL")) {
                return true;
            }
        } catch (Exception ignored) {}

        return false;
    }

    public static List<ImageFlavor> getCurrentFlavors() throws IOException {
        try (
            Response response = client.newCall(
                new Request.Builder()
                    .url("https://raw.githubusercontent.com/e3ndr/SurfaceRT-Images/main/images.json")
                    .build()
            ).execute()) {

            String body = response.body().string();

            return Rson.DEFAULT.fromJson(body, ImageFlavor.TYPE_TOKEN);
        }
    }

    public static String inflate(int length, char ch) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(ch);
        }

        return sb.toString();
    }

}
