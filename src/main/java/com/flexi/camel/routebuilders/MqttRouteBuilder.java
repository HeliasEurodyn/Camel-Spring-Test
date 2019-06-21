package com.flexi.camel.routebuilders;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.paho.PahoConstants;
import org.springframework.stereotype.Component;

@Component
public class MqttRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("paho:rooms/room1/sensors/temp?brokerUrl=tcp://localhost:1883").process(print);
    }

    private Processor print = new Processor() {
        @Override
        public void process(Exchange exchange) throws Exception {
            Object header = exchange.getIn().getHeader(PahoConstants.MQTT_TOPIC);
            String topic = (String) header;
            Message camelMessage = exchange.getIn();

            byte[] body = (byte[]) camelMessage.getBody();
            String payload = new String(body, "utf-8");

            System.out.println("topic=" + topic + ", payload=" + payload);

        }
    };
}
