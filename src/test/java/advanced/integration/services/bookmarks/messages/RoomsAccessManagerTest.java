package advanced.integration.services.bookmarks.messages;

import advanced.integration.config.AuthorizationConfiguration;
import game.mightywarriors.data.services.ChatService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Chat;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.exceptions.NoAccessException;
import game.mightywarriors.other.exceptions.NotFoundException;
import game.mightywarriors.services.bookmarks.messages.RoomManager;
import game.mightywarriors.services.bookmarks.messages.RoomsAccessManager;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.ChatInformer;
import game.mightywarriors.web.json.objects.bookmarks.MessageInformer;
import game.mightywarriors.web.json.objects.bookmarks.PrivilegesWithOutAdminInformer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class RoomsAccessManagerTest extends AuthorizationConfiguration {
    @Autowired
    private RoomsAccessManager objectUnderTest;
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
    public void addUser() throws Exception {
        prepareNewRoom();
        informer.userId = user1.getId();

        objectUnderTest.addUserToRoom(token, new PrivilegesWithOutAdminInformer(informer.userId, informer.userLogin, informer.chatId));

        assertEquals(2, chatService.find(informer.chatId).getUsers().size());
        assertEquals(1, chatService.find(informer.chatId).getAdmins().size());
        assertEquals(user.getLogin(), chatService.find(informer.chatId).getAdmins().iterator().next().getLogin());
    }

    @Test(expected = NoAccessException.class)
    public void addUser_with_no_access() throws Exception {
        addUser();
        informer.userId = user2.getId();
        authorize(user1.getLogin());

        objectUnderTest.addUserToRoom(token, new PrivilegesWithOutAdminInformer(informer.userId, informer.userLogin, informer.chatId));
    }

    @Test(expected = NotFoundException.class)
    public void addUser_with_non_existing_user() throws Exception {
        prepareNewRoom();
        informer.userId = comicalNumber;

        objectUnderTest.addUserToRoom(token, new PrivilegesWithOutAdminInformer(informer.userId, informer.userLogin, informer.chatId));
    }

    @Test
    public void removeUser() throws Exception {
        addUser();

        objectUnderTest.removeUserFromRoom(token, new PrivilegesWithOutAdminInformer(informer.userId, informer.userLogin, informer.chatId));

        assertEquals(1, chatService.find(informer.chatId).getUsers().size());
        assertNotEquals(informer.userId, chatService.find(informer.chatId).getUsers().iterator().next().getId());
        assertEquals(user.getLogin(), chatService.find(informer.chatId).getUsers().iterator().next().getLogin());
    }

    @Test(expected = NoAccessException.class)
    public void removeUser_with_no_access() throws Exception {
        addUser();
        informer.userId = user1.getId();
        authorize(user1.getLogin());

        objectUnderTest.removeUserFromRoom(token, new PrivilegesWithOutAdminInformer(informer.userId, informer.userLogin, informer.chatId));
    }

    @Test(expected = NotFoundException.class)
    public void removeUser_with_non_existing_user() throws Exception {
        addUser();
        informer.userId = comicalNumber;
        authorize(user1.getLogin());

        objectUnderTest.removeUserFromRoom(token, new PrivilegesWithOutAdminInformer(informer.userId, informer.userLogin, informer.chatId));
    }

    @Test
    public void removeCurrentUserFromRoom() throws Exception {
        addUser();
        assertEquals(2, chatService.find(informer.chatId).getUsers().size());

        objectUnderTest.leaveChat(token, new ChatInformer(informer.chatId));

        assertEquals(1, chatService.find(informer.chatId).getUsers().size());
        assertEquals(user1.getLogin(), chatService.find(informer.chatId).getUsers().stream().findFirst().get().getLogin());
        assertFalse(chatService.find(informer.chatId).getAdmins().stream().findFirst().isPresent());
    }

    @Test
    public void removeRoom() throws Exception {
        prepareNewRoom();

        objectUnderTest.leaveChat(token, new ChatInformer(informer.chatId));

        assertEquals(0, userService.find(user).getChats().size());
        assertNull(chatService.find(informer.chatId));
    }

    @Test
    public void removeRoom_which_not_exist() throws Exception {
        prepareNewRoom();
        informer.chatId = comicalNumber;

        try {
            objectUnderTest.leaveChat(token, new ChatInformer(informer.chatId));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    private void prepareNewRoom() throws Exception {
        long room = roomManager.createRoom(token);
        assertEquals(1, userService.find(user).getChats().size());
        assertEquals(user.getId(), chatService.find(room).getUsers().iterator().next().getId());
        informer.chatId = room;
    }
}