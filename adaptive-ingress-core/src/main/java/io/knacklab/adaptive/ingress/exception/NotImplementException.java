package io.knacklab.adaptive.ingress.exception;

public class NotImplementException extends IngressException {
    private static final String MESSAGE_NOT_IMPLEMENT = "must override superclass methods";

    public NotImplementException() {
        super(MESSAGE_NOT_IMPLEMENT);
    }
}
