package io.knacklab.adaptive.egress.test.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenderResponse {
    private int count;
    private String name;
    private String gender;
    private float probability;
}
