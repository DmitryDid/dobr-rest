package DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
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
    String image;
}
