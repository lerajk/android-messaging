package com.alessioiannella_leeraj_comp304lab6.managers;

import android.location.Address;
import android.location.Geocoder;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import com.alessioiannella_leeraj_comp304lab6.providers.ApiProvider;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by alessio on 14-Dec-17.
 */

public class NetworkManager {

    public String getDirections(String urlString){

        String result = null;
        ApiProvider apiProvider = new ApiProvider();

        try{
            String response = apiProvider.sendRequest(urlString);
            result = parseResponse(response);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }

    private String parseResponse(String response) {

        String directions = "";

        try {
            String str = response; //readJSONFeed(result);
            Log.d("link",str);
            // build a JSON object
            JSONObject obj = new JSONObject(str);
            if (obj.getString("status").equals("OK"))
            {
                System.out.println("OK");
                JSONArray routes=obj.getJSONArray("routes");
                JSONObject data,data1;
                JSONObject bounds;
                //System.out.println(routes.length());
                //
                for (int i=0;i<routes.length();i++) {
                    data = routes.getJSONObject(i);
                    Iterator keys = data.keys();
                    while (keys.hasNext()) {
                        System.out.println(keys.next());
                    }
                    //System.out.println(data);
                    bounds = data.getJSONObject("bounds");
                    System.out.println(bounds);
                    Iterator keys1 = bounds.keys();
                    while (keys1.hasNext()) {
                        System.out.println(keys1.next());
                    }

                    JSONArray legs = data.getJSONArray("legs");
                    //System.out.println(legs.length());
                    for (i = 0; i < legs.length(); i++) {
                        data = legs.getJSONObject(i);
                        //System.out.println(legs);
                        Iterator keys2 = data.keys();
                        while (keys2.hasNext()) {
                            System.out.println(keys2.next());

                        }
                        JSONArray steps = data.getJSONArray("steps");
                        for (int j = 0; j < steps.length(); j++) {
                            data1 = steps.getJSONObject(j);
                            //System.out.println(steps);

                            Iterator keys3 = data1.keys();
                            while (keys3.hasNext()) {
                                String key = (String) keys3.next();
                                if (key.equals("html_instructions")) {
                                    directions += "\n" +  data1.getString(key);
                                    System.out.println(data1.getString(key));
                                }

                            }
                        }

                    }

                }
            }


        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return directions;
    }

}
