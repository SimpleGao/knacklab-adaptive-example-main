package io.knacklab.adaptive.egress.test.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgeResponse {
    private int count;
    private String age;
    private String name;
}
