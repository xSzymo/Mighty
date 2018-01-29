package advanced.integration.services.bookmarks.messages;

import advanced.integration.config.AuthorizationConfiguration;
import game.mightywarriors.data.services.ChatService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Chat;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.exceptions.NoAccessException;
import game.mightywarriors.services.bookmarks.messages.MessagesManager;
import game.mightywarriors.services.bookmarks.messages.RoomsAccessManager;
import game.mightywarriors.services.bookmarks.messages.PrivilegesManager;
import game.mightywarriors.services.bookmarks.messages.RoomManager;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.MessageInformer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;

import static org.junit.Assert.*;

public class RoomManagerTest extends AuthorizationConfiguration {
    @Autowired
    private RoomManager objectUnderTest;
    @Autowired
    private MessagesManager messagesManager;
    @Autowired
    private UsersRetriever retriever;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private PrivilegesManager privilegesManager;
    @Autowired
    private RoomsAccessManager roomsAccessManager;

    private User user;
    private User user1;
    private MessageInformer informer;

    @Before
    public void setUp() throws Exception {
        informer = new MessageInformer();
        user = retriever.retrieveUser(token);
        user1 = userService.findAll().stream().filter(x -> x.getId() != this.user.getId()).findFirst().get();
    }

    @After
    public void cleanUp() {
        user = userService.find(user.getId());
        if (user != null)
            for (Chat chat : user.getChats())
                userService.removeChat(user.getId(), chat.getId());
        user1 = userService.find(user1.getId());
        if (user1 != null)
            for (Chat chat : user1.getChats())
                userService.removeChat(user1.getId(), chat.getId());
    }

    @Test
    public void createChat() throws Exception {
        informer.userId = user1.getId();

        Long room = objectUnderTest.createChat(token, informer);

        Iterator<User> iterator = chatService.find(room).getUsers().iterator();
        assertEquals(1, userService.find(user).getChats().size());
        assertEquals(1, userService.find(user1).getChats().size());
        long id = iterator.next().getId();
        assertTrue(user.getId() == id || user1.getId() == id);
        id = iterator.next().getId();
        assertTrue(user.getId() == id || user1.getId() == id);
        assertEquals(0, chatService.find(room).getAdmins().size());
    }

    @Test
    public void createRoom() throws Exception {

        Long room = objectUnderTest.createRoom(token);

        assertEquals(1, userService.find(user).getChats().size());
        assertEquals(user.getId(), chatService.find(room).getUsers().iterator().next().getId());
        assertEquals(1, chatService.find(room).getAdmins().size());
        assertEquals(user.getLogin(), chatService.find(room).getAdmins().iterator().next().getLogin());
    }

    @Test
    public void deleteRoom() throws Exception {
        prepareNewRoom();

        objectUnderTest.deleteRoom(token, informer);

        assertNull(chatService.find(informer.chatId));
        assertEquals(0, userService.find(user).getChats().size());
    }

    @Test(expected = NoAccessException.class)
    public void deleteRoom_with_no_access() throws Exception {
        prepareNewRoom();
        authorize(user1.getLogin());

        objectUnderTest.deleteRoom(token, informer);

        assertNull(chatService.find(informer.chatId));
        assertEquals(0, userService.find(user).getChats().size());
    }

    @Test(expected = NoAccessException.class)
    public void deleteRoom_with_no_access_1() throws Exception {
        prepareNewRoom();
        informer.userId = user1.getId();
        roomsAccessManager.addUserToRoom(token, informer);
        authorize(user1.getLogin());

        objectUnderTest.deleteRoom(token, informer);

        assertNull(chatService.find(informer.chatId));
        assertEquals(0, userService.find(user).getChats().size());
    }

    private void prepareNewRoom() throws Exception {
        long room = objectUnderTest.createRoom(token);
        assertEquals(1, userService.find(user).getChats().size());
        assertEquals(user.getId(), chatService.find(room).getUsers().iterator().next().getId());
        informer.chatId = room;
    }
}