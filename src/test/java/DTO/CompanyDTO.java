package DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CompanyDTO {

    @JsonProperty("id")
    Integer id;

    @JsonProperty("address")
    String address;

    @JsonProperty("email")
    String email;

    @JsonProperty("emailConfirmed")
    Boolean emailConfirmed;

    @JsonProperty("imageId")
    Integer imageId;

    @JsonProperty("image")
    ImageDTO image;

    @JsonProperty("inn")
    String inn;

    @JsonProperty("latitude")
    Double latitude;

    @JsonProperty("longitude")
    Double longitude;

    @JsonProperty("name")
    String name;

    @JsonProperty("nameOfficial")
    String nameOfficial;

    @JsonProperty("password")
    String password;

    @JsonProperty("phone")
    String phone;

    @JsonProperty("playerId")
    String playerId;

    @JsonProperty("productCategory")
    ProductCategoryDTO productCategory;

    @JsonProperty("representative")
    String representative;

    @JsonProperty("timeOfWork")
    String timeOfWork;

    @JsonProperty("timeZone")
    String timeZone;
}
