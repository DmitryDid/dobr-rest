package DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ImageDTO {

    @JsonProperty("id")
    Integer id;

    @JsonProperty("bytes")
    String bytes;
}
