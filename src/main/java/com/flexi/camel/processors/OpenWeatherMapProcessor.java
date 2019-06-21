package com.flexi.camel.processors;


import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.*;

public class OpenWeatherMapProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String bodySerialized = exchange.getIn().getBody(String.class);
        long timeStamp = new Date().getTime() / 1000;
        long timeStamp90MinAgo = timeStamp;// - (60*90);
        long timeStamp90MinLater = timeStamp + (60 * 180);
        List<LinkedHashMap> weatherJSonDataList = JsonPath.read(bodySerialized, ".list[?(@.dt>=" + timeStamp90MinAgo + " && @.dt<=" + timeStamp90MinLater + " )]");
        for (LinkedHashMap result : weatherJSonDataList) {
            System.out.println(result.get("wind"));
            LinkedHashMap windData = (LinkedHashMap) result.get("wind");
            LinkedHashMap mainData = (LinkedHashMap) result.get("main");
            LinkedHashMap cloudsData = (LinkedHashMap) result.get("clouds");

            JSONArray weatherJsonArray = (JSONArray) result.get("weather");
            LinkedHashMap weatherData = (LinkedHashMap) weatherJsonArray.get(0);


            Map m = new HashMap<String,String>();




            m.put("deg",windData.get("deg"));
            m.put("speed",windData.get("speed"));
            m.put("temp_min",mainData.get("temp_min"));
            m.put("humidity",mainData.get("humidity"));
            m.put("clouds",cloudsData.get("all"));
            m.put("id",weatherData.get("id"));
            m.put("weathermain",weatherData.get("main"));
            m.put("weatherdescription",weatherData.get("description"));


            exchange.getOut().setBody(m);
        }

    }


}
