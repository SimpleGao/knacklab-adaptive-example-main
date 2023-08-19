package io.knacklab.adaptive.example.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserContext extends Context {
    public UserContext(String id, String name, String customerNum) {
        super(id);
        this.customerNum = customerNum;
        this.name = name;
    }

    private String name;
    private String customerNum;
}
