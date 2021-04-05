package DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MessageDTO {

    @JsonProperty("id")
    Integer id;

    @JsonProperty("sendingTime")
    String sendingTime;

    @JsonProperty("isUserMessage")
    Boolean isUserMessage;

    @JsonProperty("user")
    UserDTO user;

    @JsonProperty("company")
    CompanyDTO company;

    @JsonProperty("text")
    String text;
}
