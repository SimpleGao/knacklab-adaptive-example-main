package io.knacklab.adaptive.example.messaging.egress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgeOutput {
    private int count;
    private String age;
    private String name;
}
