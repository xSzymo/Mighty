package advanced.integration.services.bookmarks.messages;

import advanced.integration.config.AuthorizationConfiguration;
import game.mightywarriors.data.services.ChatService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Chat;
import game.mightywarriors.data.tables.Message;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.bookmarks.messages.MessagesManager;
import game.mightywarriors.services.bookmarks.messages.RoomManager;
import game.mightywarriors.services.bookmarks.messages.UserDataChatProvider;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.AddMessageInformer;
import game.mightywarriors.web.json.objects.bookmarks.ChatInformer;
import game.mightywarriors.web.json.objects.bookmarks.MessageInformer;
import game.mightywarriors.web.json.objects.messages.ChatRepresentation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserDataChatProviderTest extends AuthorizationConfiguration {
    @Autowired
    private UserDataChatProvider objectUnderTest;
    @Autowired
    private UsersRetriever retriever;
    @Autowired
    private RoomManager roomManager;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private MessagesManager messagesManager;

    private User user;
    private MessageInformer informer;
    private String message = "bla bla";

    @Before
    public void setUp() throws Exception {
        informer = new MessageInformer();
        user = retriever.retrieveUser(token);
        prepareNewRoom();
    }

    @After
    public void cleanUp() {
        user = userService.find(user.getId());
        if (user != null)
            for (Chat chat : user.getChats())
                userService.removeChat(user.getId(), chat.getId());
    }

    @Test
    public void getAllChats() throws Exception {
        prepareNewRoom();

        LinkedList<ChatRepresentation> allChats = objectUnderTest.getAllChats(token);

        assertEquals(2, allChats.size());
    }

    @Test
    public void getChat() throws Exception {

        ChatRepresentation chat = objectUnderTest.getChat(token, new ChatInformer(informer.chatId));

        assertEquals(user.getLogin(), chat.users.iterator().next().getLogin());
    }

    @Test
    public void getAllMessages() throws Exception {
        for (int i = 0; i < 20; i++) {
            informer.message = message;
            messagesManager.addMessage(token, new AddMessageInformer(informer.chatId, informer.message));
        }

        List<Message> allMessages = objectUnderTest.getAllMessages(token, new ChatInformer(informer.chatId));

        assertEquals(20, allMessages.size());
        for (int i = 0; i < 20; i++)
            assertEquals(message, allMessages.get(i).getMessage());
    }

    @Test
    public void getLastMessages() throws Exception {
        for (int i = 0; i < 20; i++) {
            informer.message = message;
            messagesManager.addMessage(token, new AddMessageInformer(informer.chatId, informer.message));
        }

        List<Message> allMessages = objectUnderTest.getLastMessages(token, new ChatInformer(informer.chatId));

        assertEquals(10, allMessages.size());
        for (int i = 0; i < 10; i++)
            assertEquals(message, allMessages.get(i).getMessage());
    }

    private void prepareNewRoom() throws Exception {
        long room = roomManager.createRoom(token);
        assertEquals(user.getId(), chatService.find(room).getUsers().iterator().next().getId());
        informer.chatId = room;
    }
}
