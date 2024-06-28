package org.hackthon.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateResponse {

    private String response;

    public String getResponse() {
        return response;
    }
}
