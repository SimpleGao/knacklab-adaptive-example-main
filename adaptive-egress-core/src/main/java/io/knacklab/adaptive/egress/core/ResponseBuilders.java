package io.knacklab.adaptive.egress.core;

public abstract class ResponseBuilders {
    private static final VoidBuilder voidBuilder = new VoidBuilder();
    private static final StreamBuilder streamBuilder = new StreamBuilder();

    public static ResponseBuilder voidBuilder() {
        return voidBuilder;
    }

    public static ResponseBuilder streamBuilder() {
        return streamBuilder;
    }

    public static ResponseBuilder objectBuilder(Class<?> responseClass) {
        return new ObjectBuilder(responseClass);
    }

}
