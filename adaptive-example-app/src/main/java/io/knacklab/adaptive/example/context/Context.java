package io.knacklab.adaptive.example.context;

import lombok.Data;

import java.util.UUID;

@Data
public abstract class Context {
    private String id;

    public Context(String id) {
        this.id = id;
    }

    public Context() {
        this.id = UUID.randomUUID().toString();
    }

}
