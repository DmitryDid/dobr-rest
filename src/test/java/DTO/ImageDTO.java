package DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ImageDTO {

    @JsonProperty("id")
    Integer id;

    @JsonProperty("bytes")
    String bytes;
}
