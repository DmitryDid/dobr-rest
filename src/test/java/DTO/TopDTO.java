package DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TopDTO {

    @JsonProperty("company")
    CompanyDTO companyTop;

    @JsonProperty("count")
    Integer count;
}
