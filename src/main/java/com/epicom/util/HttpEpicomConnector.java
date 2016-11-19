package com.epicom.util;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Julio on 19/11/2016.
 */
public class HttpEpicomConnector {

    // HTTP GET request
    public static HttpURLConnection sendGet(String url, String params) throws Exception {

        HttpURLConnection con;
        con = getConnection(url + params);

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("Authorization", "Basic ODk3RjhEMjFBOUY1QTpJcDE1cTZ1N1gxNUVQMjJHUzM2WG9OTHJYMkp6MHZxcQ==");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url + params);
        System.out.println("Response Code : " + responseCode);

        return con;

    }


    private static HttpURLConnection getConnection(String url) throws IOException {

        URL obj = new URL(url);

        if(url.startsWith("https")) {
            return (HttpsURLConnection) obj.openConnection();
        } else {
            return (HttpURLConnection) obj.openConnection();
        }
    }

}
