package io.knacklab.adaptive.ingress.inserter;

public abstract class BodyInserters {
    private final static ResourceInserter resourceInserter = new ResourceInserter();

    public static BodyInserter resourceDate() {
        return resourceInserter;
    }

    public static BodyInserter objectDate(Class<?> resourceClass) {
        return new ObjectInserter(resourceClass);
    }

}
