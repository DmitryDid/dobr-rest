package Tests;

import DTO.ChatsDTO;
import DTO.MessageDTO;
import DTO.MessagesDTO;
import Pages.Chat;
import Pages.Company;
import Pages.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static Pages.Chat.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ChatTest extends TestBase {

    @Test
    public void getChatUserById() {
        String text = "testText" + getUniqueNumber(4);
        Integer userId = User.getRandomUser().getId();
        Integer companyId = Company.getRandomCompany().getId();

        postUserMessage(userId, companyId, text);

        ChatsDTO chatsDTO = Chat.getChatUser(userId, -1);

        assertTrue(chatsDTO.getChats().size() > 0);
        assertTrue(chatsDTO.getLastId() > 0);
    }

    @Test
    public void getChatCompany() {
        String text = "testText" + getUniqueNumber(4);
        Integer userId = User.getRandomUser().getId();
        Integer companyId = Company.getRandomCompany().getId();

        postUserMessage(userId, companyId, text);

        ChatsDTO chatsDTO = Chat.getChatCompany(companyId, -1);

        assertTrue(chatsDTO.getChats().size() > 0);
        assertTrue(chatsDTO.getLastId() > 0);
    }

    @Test
    public void getChatMessage() {
        String text = "testText" + getUniqueNumber(4);
        Integer userId = User.getRandomUser().getId();
        Integer companyId = Company.getRandomCompany().getId();

        postUserMessage(userId, companyId, text);

        ArrayList<MessagesDTO> list = getMessage(userId, companyId, -1);
        assertTrue(list.size() > 0);

        for (int i = 0; i < list.size(); i++) {
            MessagesDTO messages = list.get(i);
            MessageDTO message = messages.getMessages().get(i);
            assertEquals(message.getCompany().getId(), companyId);
            assertEquals(message.getUser().getId(), userId);
            assertTrue(message.getText().startsWith("testText"));
        }
    }

    @Test
    public void postChatMessage() {
        String userText = "testText" + getUniqueNumber(4);
        String companyText = "testText" + getUniqueNumber(5);
        Integer userId = User.getRandomUser().getId();
        Integer companyId = Company.getRandomCompany().getId();

        MessageDTO userPost = postUserMessage(userId, companyId, userText);
        Date postUserDate = getDate(userPost.getSendingTime());

        assertEquals(postUserDate.getYear(), new Date().getYear());
        assertEquals(postUserDate.getMonth(), new Date().getMonth());
        assertEquals(postUserDate.getDay(), new Date().getDay());
        assertEquals(userPost.getUser().getId(), userId);
        assertEquals(userPost.getCompany().getId(), companyId);
        assertEquals(userPost.getText(), userText);
        assertEquals(userPost.getIsUserMessage(), true);

        MessageDTO companyPost = postCompanyMessage(userId, companyId, companyText);
        Date posCompanyDate = getDate(userPost.getSendingTime());

        assertEquals(posCompanyDate.getYear(), new Date().getYear());
        assertEquals(posCompanyDate.getMonth(), new Date().getMonth());
        assertEquals(posCompanyDate.getDay(), new Date().getDay());
        assertEquals(companyPost.getCompany().getId(), companyId);
        assertEquals(companyPost.getUser().getId(), userId);
        assertEquals(companyPost.getText(), companyText);
        assertEquals(companyPost.getIsUserMessage(), false);
    }
}
