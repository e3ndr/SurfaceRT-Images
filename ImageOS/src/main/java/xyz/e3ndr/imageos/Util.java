package xyz.e3ndr.imageos;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Util {
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

}
