package DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserDTO {

    @JsonProperty("id")
    Integer id;

    @JsonProperty("name")
    String name;

    @JsonProperty("isMan")
    Boolean isMan;

    @JsonProperty("latitude")
    Double latitude;

    @JsonProperty("longitude")
    Double longitude;

    @JsonProperty("birthYear")
    String birthYear;

    @JsonProperty("playerId")
    String playerId;

    @JsonProperty("imageId")
    Integer imageId;

    @JsonProperty("image")
    ImageDTO image;

}
