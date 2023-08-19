package io.knacklab.adaptive.example.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KioskContext extends Context {
    private String name;
    private String kioskId;

    public KioskContext(String id, String name, String kioskId) {
        super(id);
        this.name = name;
        this.kioskId = kioskId;
    }

    public KioskContext(String name) {
        this.name = name;
    }
}
