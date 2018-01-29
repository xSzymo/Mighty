package unit.web.json.objects.messages;

import game.mightywarriors.data.tables.Chat;
import game.mightywarriors.data.tables.Message;
import game.mightywarriors.web.json.objects.messages.ChatRepresentation;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class ChatRepresentationTest {

    @Test
    public void test() {
        Chat chat = spy(new Chat());
        when(chat.getId()).thenReturn(1L);
        Message message;
        for (int i = 5; i >= 0; i--) {
            message = new Message();
            message.setMessage("m" + i);
            message.setNumber(i);
            message.setUserLogin("anonymous");
            chat.addMessage(message);
        }
        for (int i = 16; i <= 25; i++) {
            message = new Message();
            message.setMessage("m" + i);
            message.setNumber(i);
            message.setUserLogin("anonymous");
            chat.addMessage(message);
        }

        message = new Message();
        message.setMessage("m" + 6);
        message.setNumber(6);
        message.setUserLogin("anonymous");
        chat.addMessage(message);


        ChatRepresentation chatRepresentation = new ChatRepresentation(chat);

        for (int a = 0, i = 0; i <= 25; i++, a++) {
            assertEquals(i, chatRepresentation.messages.get(a).getNumber());
            assertEquals("m" + i, chatRepresentation.messages.get(a).getMessage());
            assertEquals("anonymous", chatRepresentation.messages.get(a).getUserLogin());

            if (i == 6)
                i = 15;
        }
    }

}