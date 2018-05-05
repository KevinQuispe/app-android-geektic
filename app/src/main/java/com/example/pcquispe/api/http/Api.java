package com.example.pcquispe.api.http;

import android.content.Context;

import com.example.pcquispe.api.util.Config;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.entity.StringEntity;

/**
 * Created by PCQUISPE on 2/20/2018.
 */

public class Api {

    public static AsyncHttpClient http = new AsyncHttpClient();

    public static void get(Context context, String url, TextHttpResponseHandler responseHandler) {
        http.get(context, Config.URL_SERVER + url, null, "application/json", responseHandler);

    }

    public static void post(Context context, String url, Object data, TextHttpResponseHandler responseHandler) {
        try {
            StringEntity entity = new StringEntity(new Gson().toJson(data)); //que pasa here
            http.post(context, Config.URL_SERVER + url, entity, "application/json", responseHandler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void put(Context context, String url, Object data, TextHttpResponseHandler responseHandler) {
        try {
            StringEntity entity = new StringEntity(new Gson().toJson(data));
            http.put(context, Config.URL_SERVER + url, entity, "application/json", responseHandler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
