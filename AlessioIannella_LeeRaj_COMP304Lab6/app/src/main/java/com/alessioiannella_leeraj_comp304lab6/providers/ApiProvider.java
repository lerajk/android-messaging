package com.alessioiannella_leeraj_comp304lab6.providers;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by alessio on 14-Dec-17.
 */

public class ApiProvider {

    public String sendRequest(String absoluteURL) throws IOException {

        URL url = null;

        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(absoluteURL);

            urlConnection = (HttpURLConnection) url.openConnection();


            InputStream content = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));

            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }

        return stringBuilder.toString();

//        StringBuilder stringBuilder = new StringBuilder();
//        HttpClient httpClient = new DefaultHttpClient();
//        Log.d("url",URL);
//        HttpGet httpGet = new HttpGet(URL);
//        try {
//            HttpResponse response = httpClient.execute(httpGet);
//            StatusLine statusLine = response.getStatusLine();
//            int statusCode = statusLine.getStatusCode();
//            if (statusCode == 200) {
//                HttpEntity entity = response.getEntity();
//                InputStream inputStream = entity.getContent();
//                BufferedReader reader = new BufferedReader(
//                        new InputStreamReader(inputStream));
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    stringBuilder.append(line);
//                }
//                inputStream.close();
//            } else {
//                Log.d("JSON", "Failed to download file");
//            }
//        } catch (Exception e) {
//            Log.d("readJSONFeed", e.getLocalizedMessage());
//        }
//        return stringBuilder.toString();
    }
}
