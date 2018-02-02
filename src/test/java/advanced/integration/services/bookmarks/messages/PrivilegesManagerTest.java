package advanced.integration.services.bookmarks.messages;

import advanced.integration.config.AuthorizationConfiguration;
import game.mightywarriors.data.services.ChatService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Admin;
import game.mightywarriors.data.tables.Chat;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.exceptions.NoAccessException;
import game.mightywarriors.other.exceptions.NotFoundException;
import game.mightywarriors.services.bookmarks.messages.MessagesManager;
import game.mightywarriors.services.bookmarks.messages.PrivilegesManager;
import game.mightywarriors.services.bookmarks.messages.RoomManager;
import game.mightywarriors.services.bookmarks.messages.RoomsAccessManager;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.MessageInformer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PrivilegesManagerTest extends AuthorizationConfiguration {
    @Autowired
    private PrivilegesManager objectUnderTest;
    @Autowired
    private RoomsAccessManager roomsAccessManager;
    @Autowired
    private MessagesManager messagesManager;
    @Autowired
    private UsersRetriever retriever;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private RoomManager roomManager;

    private User user;
    private User user1;
    private User user2;
    private MessageInformer informer;
    private long comicalNumber = 1251252113;

    @Before
    public void setUp() throws Exception {
        informer = new MessageInformer();
        user = retriever.retrieveUser(token);
        user1 = userService.findAll().stream().filter(x -> x.getId() != this.user.getId()).findFirst().get();
        user2 = userService.findAll().stream().filter(x -> x.getId() != this.user.getId() && x.getId() != this.user1.getId()).findFirst().get();
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
    public void addPrivileges() throws Exception {
        addUser();

        objectUnderTest.addPrivileges(token, informer);

        assertEquals(2, chatService.find(informer.chatId).getAdmins().size());
        Iterator<Admin> iterator = chatService.find(informer.chatId).getAdmins().iterator();
        Admin next = iterator.next();
        assertTrue(user.getLogin().equals(next.getLogin()) || user1.getLogin().equals(next.getLogin()));
        next = iterator.next();
        assertTrue(user.getLogin().equals(next.getLogin()) || user1.getLogin().equals(next.getLogin()));
    }

    @Test(expected = NoAccessException.class)
    public void addPrivileges_with_no_access() throws Exception {
        addUser();
        informer.userId = user1.getId();
        authorize(user1.getLogin());

        objectUnderTest.addPrivileges(token, informer);
    }

    @Test(expected = NotFoundException.class)
    public void addPrivileges_with_non_existing_user() throws Exception {
        addUser();
        informer.userId = comicalNumber;

        objectUnderTest.addPrivileges(token, informer);
    }

    @Test
    public void removePrivileges() throws Exception {
        addPrivileges();

        objectUnderTest.removePrivileges(token, informer);

        assertEquals(1, chatService.find(informer.chatId).getAdmins().size());
    }

    @Test(expected = NoAccessException.class)
    public void removePrivileges_with_no_access() throws Exception {
        addPrivileges();
        informer.userId = user1.getId();
        objectUnderTest.removePrivileges(token, informer);
        authorize(user1.getLogin());

        objectUnderTest.removePrivileges(token, informer);

        assertEquals(1, chatService.find(informer.chatId).getAdmins().size());
    }

    @Test(expected = Exception.class)
    public void removePrivileges_with_user_who_have_no_access_yet() throws Exception {
        addPrivileges();
        informer.userId = user2.getId();
        authorize(user1.getLogin());

        objectUnderTest.removePrivileges(token, informer);

        assertEquals(1, chatService.find(informer.chatId).getAdmins().size());
    }

    @Test(expected = NotFoundException.class)
    public void removePrivileges_with_user_who_have_no_access_yet_1() throws Exception {
        addPrivileges();
        informer.userId = user1.getId();
        authorize(user.getLogin());
        roomsAccessManager.removeUserFromRoom(token, informer);

        objectUnderTest.removePrivileges(token, informer);

        assertEquals(1, chatService.find(informer.chatId).getAdmins().size());
    }

    @Test(expected = NotFoundException.class)
    public void removePrivileges_with_non_existing_user() throws Exception {
        addPrivileges();
        informer.userId = comicalNumber;

        objectUnderTest.removePrivileges(token, informer);
    }

    private void addUser() throws Exception {
        prepareNewRoom();
        informer.userId = user1.getId();

        roomsAccessManager.addUserToRoom(token, informer);

        assertEquals(2, chatService.find(informer.chatId).getUsers().size());
        assertEquals(1, chatService.find(informer.chatId).getAdmins().size());
        assertEquals(user.getLogin(), chatService.find(informer.chatId).getAdmins().iterator().next().getLogin());
    }

    private void prepareNewRoom() throws Exception {
        long room = roomManager.createRoom(token);
        assertEquals(1, userService.find(user).getChats().size());
        assertEquals(user.getId(), chatService.find(room).getUsers().iterator().next().getId());
        informer.chatId = room;
    }
}