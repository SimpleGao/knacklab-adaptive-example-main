package io.knacklab.adaptive.egress.messaging;


import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

public class EgressRequest {
    private String path;
    private Object body;
    private BodyType bodyType;
    private HttpHeaders headers;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    private EgressRequest() {
        bodyType = BodyType.None;
    }

    public static EgressRequestBuilder builder() {
        return new EgressRequestBuilder();
    }

    public static class EgressRequestBuilder {
        private final EgressRequest input;

        private EgressRequestBuilder() {
            this.input = new EgressRequest();
        }

        public EgressRequestBuilder body(Object body) {
            if (Objects.nonNull(body)) {
                this.input.setBodyType(BodyType.Value);
                this.input.setBody(body);
            }
            return this;
        }

        public EgressRequestBuilder fromBody(MultiValueMap<String, String> body) {
            if (Objects.nonNull(body)) {
                this.input.setBodyType(BodyType.Form);
                this.input.setBody(body);
            }
            return this;
        }

        public EgressRequestBuilder multipartBody(MultiValueMap<String, ?> body) {
            if (Objects.nonNull(body)) {
                this.input.setBodyType(BodyType.Multipart);
                this.input.setBody(body);
            }
            return this;
        }

        public EgressRequestBuilder path(String path) {
            if (Objects.nonNull(path)) {
                this.input.setPath(path);
            }
            return this;
        }

        public EgressRequestBuilder headers(HttpHeaders headers) {
            if (Objects.nonNull(headers)) {
                this.input.setHeaders(headers);
            }
            return this;
        }

        public EgressRequest build() {
            return this.input;
        }
    }


    public enum BodyType {
        None,
        Form,
        Value,
        Multipart
    }
}
