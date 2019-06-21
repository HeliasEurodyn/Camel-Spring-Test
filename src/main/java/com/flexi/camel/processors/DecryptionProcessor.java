package com.flexi.camel.processors;

import com.flexi.util.PgpEncryption;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang.SerializationUtils;

import java.io.File;

public class DecryptionProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        byte[] encryptedByteArray = (byte[]) exchange.getIn().getBody(byte[].class);
        PgpEncryption pgpEnc = new PgpEncryption();
        File privateKeyfile= new File("pgpSecretKeyRing.skr");
        byte[] dencryptedByteArray = pgpEnc.decrypt(encryptedByteArray, privateKeyfile,"123456".toCharArray());
        String dencryptedJsonString = (String) SerializationUtils.deserialize(dencryptedByteArray);
        exchange.getIn().setBody(dencryptedJsonString);

    }


}
