package com.flexi.camel.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class BodySerializerProcessor implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {

      //  Object body = exchange.getIn().getBody();
        Message message = exchange.getIn();
        String bodySerialized = message.getBody(String.class);
        message.setBody(bodySerialized);

    }


}
