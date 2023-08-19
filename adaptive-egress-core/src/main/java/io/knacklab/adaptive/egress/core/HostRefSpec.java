package io.knacklab.adaptive.egress.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HostRefSpec {
    private String baseUrl;
    private String name;
}
