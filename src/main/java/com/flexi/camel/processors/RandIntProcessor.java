package com.flexi.camel.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandIntProcessor implements Processor {

    private int max =3000;

    public RandIntProcessor(int max) {
        this.max = max;
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        Random random = new Random();
        int val =  random.nextInt(this.max);
        Map map = new HashMap<String,String>();
        map.put("randInt",val);
        exchange.getOut().setBody(map);

    }
}
