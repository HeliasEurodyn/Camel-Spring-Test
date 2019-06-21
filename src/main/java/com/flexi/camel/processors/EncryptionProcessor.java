package com.flexi.camel.processors;

import com.flexi.util.PgpEncryption;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.commons.lang.SerializationUtils;
import java.io.File;

public class EncryptionProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        Message message = exchange.getIn();
        String jsonString = message.getBody(String.class);
        byte[] jsonBytes = SerializationUtils.serialize(jsonString);
        PgpEncryption pgpEnc = new PgpEncryption();
        File publicKeyfile= new File("pgpPublicKeyRing.pkr");
        byte[] encryptedData = pgpEnc.encrypt(jsonBytes,publicKeyfile);
        exchange.getOut().setBody(encryptedData);

    }





}


