package advanced.integration.services.bookmarks.messages;

import advanced.integration.config.AuthorizationConfiguration;
import game.mightywarriors.data.services.ChatService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Chat;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.ChatRole;
import game.mightywarriors.other.exceptions.NoAccessException;
import game.mightywarriors.other.exceptions.NotFoundException;
import game.mightywarriors.services.bookmarks.messages.MessagesManager;
import game.mightywarriors.services.bookmarks.messages.PrivilegesManager;
import game.mightywarriors.services.bookmarks.messages.RoomManager;
import game.mightywarriors.services.bookmarks.messages.RoomsAccessManager;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MessagesManagerTest extends AuthorizationConfiguration {
    @Autowired
    private MessagesManager objectUnderTest;
    @Autowired
    private UsersRetriever retriever;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private RoomManager roomManager;
    @Autowired
    private PrivilegesManager privilegesManager;
    @Autowired
    private RoomsAccessManager roomsAccessManager;

    private User user;
    private User user1;
    private MessageInformer informer;
    private String message = "my new message";
    private String deleteMessage = "Message was deleted";

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
    public void addMessage() throws Exception {
        prepareNewRoom();
        informer.message = message;

        objectUnderTest.addMessage(token, new AddMessageInformer(informer.chatId, informer.message));

        assertEquals(1, chatService.find(informer.chatId).getMessages().size());
        assertEquals(message, chatService.find(informer.chatId).getMessages().iterator().next().getMessage());
        assertEquals(user.getLogin(), chatService.find(informer.chatId).getMessages().iterator().next().getUserLogin());
        assertEquals(0, chatService.find(informer.chatId).getMessages().iterator().next().getNumber());
    }

    @Test
    public void addMessage_with_added_user() throws Exception {
        prepareNewRoom();
        informer.message = message;
        informer.userId = user1.getId();
        roomsAccessManager.addUserToRoom(token, new PrivilegesWithOutAdminInformer(informer.userId, informer.userLogin, informer.chatId));
        assertEquals(2, chatService.find(informer.chatId).getUsers().size());
        authorize(user1.getLogin());

        objectUnderTest.addMessage(token, new AddMessageInformer(informer.chatId, informer.message));

        assertEquals(1, chatService.find(informer.chatId).getMessages().size());
        assertEquals(message, chatService.find(informer.chatId).getMessages().iterator().next().getMessage());
        assertEquals(user1.getLogin(), chatService.find(informer.chatId).getMessages().iterator().next().getUserLogin());
        assertEquals(0, chatService.find(informer.chatId).getMessages().iterator().next().getNumber());
    }

    @Test(expected = NoAccessException.class)
    public void addMessage_with_out_access() throws Exception {
        prepareNewRoom();
        informer.message = message;
        authorize(user1.getLogin());

        objectUnderTest.addMessage(token, new AddMessageInformer(informer.chatId, informer.message));
    }

    @Test(expected = NotFoundException.class)
    public void addMessage_with_out_message() throws Exception {
        prepareNewRoom();
        authorize(user1.getLogin());

        objectUnderTest.addMessage(token, new AddMessageInformer(informer.chatId, informer.message));
    }

    @Test
    public void removeMessage_as_owner() throws Exception {
        addMessage();
        informer.messageId = chatService.find(informer.chatId).getMessages().iterator().next().getId();

        objectUnderTest.deleteMessage(token, new DeleteMessageInformer(informer.chatId, informer.messageId));

        assertEquals(1, chatService.find(informer.chatId).getMessages().size());
        assertEquals(deleteMessage, chatService.find(informer.chatId).getMessages().iterator().next().getMessage());
        assertEquals(1, userService.find(user.getId()).getChats().size());
        assertEquals(1, userService.find(user.getId()).getChats().iterator().next().getMessages().size());
    }

    @Test
    public void removeMessage_as_admin() throws Exception {
        addMessage();
        informer.messageId = chatService.find(informer.chatId).getMessages().iterator().next().getId();
        informer.userId = user1.getId();
        roomsAccessManager.addUserToRoom(token, new PrivilegesWithOutAdminInformer(informer.userId, informer.userLogin, informer.chatId));
        privilegesManager.addPrivileges(token, new PrivilegesInformer(informer.userId, informer.userLogin, informer.chatId, informer.admin));
        assertEquals(2, chatService.find(informer.chatId).getUsers().size());
        assertEquals(2, chatService.find(informer.chatId).getAdmins().size());
        authorize(user1.getLogin());

        objectUnderTest.deleteMessage(token, new DeleteMessageInformer(informer.chatId, informer.messageId));

        assertEquals(1, chatService.find(informer.chatId).getMessages().size());
        assertEquals(deleteMessage, chatService.find(informer.chatId).getMessages().iterator().next().getMessage());
        assertEquals(1, userService.find(user.getId()).getChats().size());
        assertEquals(1, userService.find(user.getId()).getChats().iterator().next().getMessages().size());
    }

    @Test
    public void removeMessage_as_modifier() throws Exception {
        addMessage();
        informer.messageId = chatService.find(informer.chatId).getMessages().iterator().next().getId();
        informer.userId = user1.getId();
        roomsAccessManager.addUserToRoom(token, new PrivilegesWithOutAdminInformer(informer.userId, informer.userLogin, informer.chatId));
        privilegesManager.addPrivileges(token, new PrivilegesInformer(informer.userId, informer.userLogin, informer.chatId, informer.admin));
        assertEquals(2, chatService.find(informer.chatId).getUsers().size());
        assertEquals(2, chatService.find(informer.chatId).getAdmins().size());
        assertTrue(chatService.find(informer.chatId).getAdmins().stream().anyMatch(x -> x.getChatRole().getRole().equals(ChatRole.MODIFIER.getRole())));
        authorize(user1.getLogin());

        objectUnderTest.deleteMessage(token, new DeleteMessageInformer(informer.chatId, informer.messageId));

        assertEquals(1, chatService.find(informer.chatId).getMessages().size());
        assertEquals(deleteMessage, chatService.find(informer.chatId).getMessages().iterator().next().getMessage());
        assertEquals(1, userService.find(user.getId()).getChats().size());
        assertEquals(1, userService.find(user.getId()).getChats().iterator().next().getMessages().size());
    }

    @Test(expected = NoAccessException.class)
    public void removeMessage_as_not_admin_or_modifier() throws Exception {
        addMessage();
        informer.messageId = chatService.find(informer.chatId).getMessages().iterator().next().getId();
        informer.userId = user1.getId();
        roomsAccessManager.addUserToRoom(token, new PrivilegesWithOutAdminInformer(informer.userId, informer.userLogin, informer.chatId));
        assertEquals(2, chatService.find(informer.chatId).getUsers().size());
        assertEquals(1, chatService.find(informer.chatId).getAdmins().size());
        authorize(user1.getLogin());

        objectUnderTest.deleteMessage(token, new DeleteMessageInformer(informer.chatId, informer.messageId));
    }

    @Test(expected = NotFoundException.class)
    public void removeMessage_with_wrong_message_id() throws Exception {
        addMessage();
        informer.messageId = 151251231;
        informer.userId = user1.getId();
        roomsAccessManager.addUserToRoom(token, new PrivilegesWithOutAdminInformer(informer.userId, informer.userLogin, informer.chatId));
        assertEquals(2, chatService.find(informer.chatId).getUsers().size());
        assertEquals(1, chatService.find(informer.chatId).getAdmins().size());
        authorize(user1.getLogin());

        objectUnderTest.deleteMessage(token, new DeleteMessageInformer(informer.userId, informer.messageId));
    }

    private void prepareNewRoom() throws Exception {
        long room = roomManager.createRoom(token);
        assertEquals(1, userService.find(user).getChats().size());
        assertEquals(user.getId(), chatService.find(room).getUsers().iterator().next().getId());
        informer.chatId = room;
    }
}