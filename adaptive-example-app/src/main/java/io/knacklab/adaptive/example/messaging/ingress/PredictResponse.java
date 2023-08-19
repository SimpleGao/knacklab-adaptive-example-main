package io.knacklab.adaptive.example.messaging.ingress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PredictResponse {
    private String age;
    private String name;
    private String gender;
}
