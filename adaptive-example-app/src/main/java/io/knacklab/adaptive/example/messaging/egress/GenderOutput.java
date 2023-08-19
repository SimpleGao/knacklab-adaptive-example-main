package io.knacklab.adaptive.example.messaging.egress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenderOutput {
    private int count;
    private String name;
    private String gender;
    private float probability;
}
