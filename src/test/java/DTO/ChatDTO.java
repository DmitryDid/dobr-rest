package DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ChatDTO {

    @JsonProperty("user")
    UserDTO user;

    @JsonProperty("company")
    CompanyDTO company;

    @JsonProperty("lastMessage")
    String lastMessage;

    @JsonProperty("dateTime")
    String dateTime;
}
