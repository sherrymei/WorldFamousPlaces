package com.example.sherry.worldsfamousplaces;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import cz.msebera.android.httpclient.*;
import cz.msebera.android.httpclient.client.*;
import cz.msebera.android.httpclient.impl.client.*;
import cz.msebera.android.httpclient.client.methods.*;

/**
 * Created by Sherry on 5/4/2017.
 */

public class JSONfunctions {

    public static JSONObject getJSONfromURL(String url) {
        InputStream is = null;
        String result = "";
        JSONObject jsonObject = null;

        try {
            HttpClient httpclient = HttpClientBuilder.create().build();
            HttpPost httppost = new HttpPost(url);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

        } catch (Exception e) {
           e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            jsonObject = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}

