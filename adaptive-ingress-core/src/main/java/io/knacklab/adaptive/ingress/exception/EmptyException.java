package io.knacklab.adaptive.ingress.exception;

import org.apache.logging.log4j.util.Strings;

public class EmptyException extends IngressException {

    private static EmptyException instance;

    public EmptyException() {
        super(Strings.EMPTY);
    }

    public static synchronized EmptyException build() {
        if (instance == null) {
            instance = new EmptyException();
        }
        return instance;
    }
}


