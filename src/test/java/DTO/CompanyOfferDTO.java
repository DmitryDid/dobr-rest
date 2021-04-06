package DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class CompanyOfferDTO {

    @JsonProperty("preOffer")
    List<OfferDTO> preOffer;

    @JsonProperty("activeOffer")
    List<OfferDTO> activeOffer;

    @JsonProperty("nearbyOffer")
    List<OfferDTO> nearbyOffer;

    @JsonProperty("inactiveOffer")
    List<OfferDTO> inactiveOffer;
}
