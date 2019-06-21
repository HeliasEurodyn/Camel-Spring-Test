package com.flexi.camel.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.logging.Logger;

public class MailProcessor implements Processor {

    @Autowired
    public JavaMailSender emailSender;

    @Override
    public void process(Exchange exchange) throws Exception {
        Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);

        Logger.getLogger(MailProcessor.class.getName()).warning("Error Message in MailProcessor : " + e.getMessage());

        String messageBody = "Exception happened in the route and the exception is  " + e.getMessage();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("karagozidis@gmail.com");
        message.setTo("helias.karagozidis@eurodyn.com");
        message.setSubject("Exception in Camel Route");
        message.setText(messageBody);

        emailSender.send(message);
    }
}
