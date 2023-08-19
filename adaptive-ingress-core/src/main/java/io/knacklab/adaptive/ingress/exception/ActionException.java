package io.knacklab.adaptive.ingress.exception;

import org.springframework.http.HttpStatus;

public class ActionException extends IngressException {

    private final HttpStatus status;

    public ActionException(String message) {
        this(HttpStatus.SERVICE_UNAVAILABLE, message);
    }

    public ActionException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
