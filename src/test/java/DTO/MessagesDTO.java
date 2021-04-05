package DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class MessagesDTO {

    @JsonProperty("messages")
    List<MessageDTO> messages;

    @JsonProperty("day")
    String day;
}
