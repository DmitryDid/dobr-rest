package DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class CompanyListDTO {

    @JsonProperty("company")
    CompanyDTO company;

    @JsonProperty("stories")
    List stories;
}
