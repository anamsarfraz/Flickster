package com.codepath.flickster.util;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class NetworkUtil {
    private static OkHttpClient client = new OkHttpClient();

    private static Request buildRequest(String urlStr, HashMap<String, String> queryParams) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(urlStr).newBuilder();
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }

        String url = urlBuilder.build().toString();
        return new Request.Builder()
                .url(url)
                .build();
    }

    public static void getResponse(String urlStr, HashMap<String, String> queryParams, Callback responseCallback) {
        Request request = NetworkUtil.buildRequest(urlStr, queryParams);
        // Get a handler that can be used to post to the main thread
        client.newCall(request).enqueue(responseCallback);

    }
}
