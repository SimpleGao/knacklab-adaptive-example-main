package io.knacklab.adaptive.ingress.extractor;

public abstract class BodyExtractors {
    private static final FormExtractor formExtractor = new FormExtractor();
    private static final MultiPartExecutor multiPartExecutor = new MultiPartExecutor();

    public static BodyExtractor formDate() {
        return formExtractor;
    }

    public static BodyExtractor multiPartDate() {
        return multiPartExecutor;
    }

    public static BodyExtractor objectDate(Class<?> requestClass) {
        return new ObjectExtractor(requestClass);
    }
}
