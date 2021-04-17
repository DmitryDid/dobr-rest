package DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class StatusDTO {

    @JsonProperty("status")
    String status;

    @JsonProperty("message")
    String message;
}
