package DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class ChatsDTO {

    @JsonProperty("chats")
    List<ChatDTO> chats;

    @JsonProperty("lastId")
    Integer lastId;
}
