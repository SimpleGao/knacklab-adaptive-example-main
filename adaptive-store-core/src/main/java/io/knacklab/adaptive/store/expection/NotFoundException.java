package io.knacklab.adaptive.store.expection;

public class NotFoundException extends StoreException {
    public NotFoundException(String message) {
        super(message);
    }
}
