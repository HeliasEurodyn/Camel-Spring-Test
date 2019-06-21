package com.flexi.camel.processors;

import org.apache.camel.Exchange;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SplitProcessor  {
    public Iterator splitMethod(Exchange exchange) {
        final String exampleMsg = exchange.getIn().getBody(String.class);

        if (exampleMsg == null)
            throw new NullPointerException();

        return new Iterator<Character>() {
            private int index = 0;

            public boolean hasNext() {
                return index < exampleMsg.length();
            }

            public Character next() {

                if (!hasNext())
                    throw new NoSuchElementException();
                return exampleMsg.charAt(index++);
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };

    }
}
