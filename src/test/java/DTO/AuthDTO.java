package DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AuthDTO {

    @JsonProperty("status")
    String status;

    @JsonProperty("user")
    UserDTO user;

    @JsonProperty("company")
    CompanyDTO company;

    @JsonProperty("access_token")
    String accessToken;

    @JsonProperty("token_type")
    String tokenType;

}
