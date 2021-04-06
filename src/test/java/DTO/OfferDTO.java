package DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OfferDTO {

    @JsonProperty("id")
    Integer id;

    @JsonProperty("likeCounter")
    Integer likeCounter;

    @JsonProperty("text")
    String text;

    @JsonProperty("createDate")
    String createDate;

    @JsonProperty("sendingTime")
    String sendingTime;

    @JsonProperty("dateStart")
    String dateStart;

    @JsonProperty("dateEnd")
    String dateEnd;

    @JsonProperty("timeStart")
    String timeStart;

    @JsonProperty("timeEnd")
    String timeEnd;

    @JsonProperty("company")
    CompanyDTO company;

    @JsonProperty("imageId")
    Integer imageId;

    @JsonProperty("image")
    ImageDTO image;

    @JsonProperty("percentage")
    Integer percentage;

    @JsonProperty("forMan")
    Boolean forMan;

    @JsonProperty("forWoman")
    Boolean forWoman;

    @JsonProperty("upperAgeLimit")
    Integer upperAgeLimit;

    @JsonProperty("lowerAgeLimit")
    Integer lowerAgeLimit;

    @JsonProperty("imageName")
    String imageName;

    @JsonProperty("userLike")
    Boolean userLike;
}
