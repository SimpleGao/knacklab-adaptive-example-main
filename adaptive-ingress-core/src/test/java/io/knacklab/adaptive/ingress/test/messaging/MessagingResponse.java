package io.knacklab.adaptive.ingress.test.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessagingResponse {
    private String age;
    private String name;
    private String gender;
}
