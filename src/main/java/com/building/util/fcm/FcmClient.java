package com.building.util.fcm;


import org.codehaus.jettison.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by phuongtn on 12/29/2016.
 */
public class FcmClient {
    // HTTP POST request
    public static void sendNotification(String apiKey, Map<String, String> data, String fcmToken, long ttl) throws IOException {

        String url = "https://fcm.googleapis.com/fcm/send";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "key=" + apiKey);

        Map<String, Object> postData = new HashMap<>();
        postData.put("data", data);
        postData.put("to", fcmToken);
        postData.put("time_to_live", ttl);
        JSONObject object = new JSONObject(postData);
        String urlParameters = object.toString();

        // Send post request
        con.setDoOutput(true);
        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF-8"));
        wr.write(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }
}
