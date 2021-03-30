package DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProductCategoryDTO {

    @JsonProperty("id")
    Integer id;

    @JsonProperty("name")
    String name;

    @JsonProperty("ageLimit")
    Integer ageLimit;

    @JsonProperty("imageId")
    Integer imageId;

    @JsonProperty("image")
    ImageDTO image;
}
