package integration.data.services;

import game.mightywarriors.data.repositories.ChatRepository;
import game.mightywarriors.data.repositories.UserRepository;
import game.mightywarriors.data.services.ChatService;
import game.mightywarriors.data.services.MessageService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Chat;
import game.mightywarriors.data.tables.Message;
import game.mightywarriors.data.tables.User;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class ChatServiceTest extends IntegrationTestsConfig {
    @Autowired
    private ChatService objectUnderTest;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRepository chatRepository;

    private HashSet<Chat> chats;

    @Before
    public void beforeEachTest() {
        chats = new HashSet<>();
        Chat chat = new Chat();
        chat.addMessage(new Message("b1,", "z"));
        chats.add(chat);
        chat = new Chat();
        chat.addMessage(new Message("b2,", "z"));
        chats.add(chat);
        chat = new Chat();
        chat.addMessage(new Message("b3,", "z"));
        chats.add(chat);
    }

    @After
    public void afterEachTest() {
        chats.forEach(objectUnderTest::delete);
    }

    @Test
    public void save() {
        objectUnderTest.save(chats.iterator().next());

        assertNotNull(objectUnderTest.find(chats.iterator().next()));
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(chats);

        Iterator<Chat> iterator = chats.iterator();
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
    }

    @Test
    public void findOne() {
        objectUnderTest.save(chats.iterator().next());

        assertNotNull(objectUnderTest.find(chats.iterator().next()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(chats.iterator().next());

        assertNotNull(objectUnderTest.find(chats.iterator().next().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(chats);

        Iterator<Chat> iterator = chats.iterator();
        List<Chat> list = new ArrayList<>();
        iterator.forEachRemaining(list::add);
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(0).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(1).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(2).getId())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(chats.iterator().next());

        assertNotNull(objectUnderTest.find(chats.iterator().next()));

        objectUnderTest.delete(chats.iterator().next());

        assertNull(objectUnderTest.find(chats.iterator().next()));
        chats.clear();
    }

    @Test
    public void delete1() {
        objectUnderTest.save(chats.iterator().next());

        assertNotNull(objectUnderTest.find(chats.iterator().next()));

        objectUnderTest.delete(chats.iterator().next().getId());

        assertNull(objectUnderTest.find(chats.iterator().next()));
        chats.clear();
    }

    @Test
    public void delete2() {
        objectUnderTest.save(chats);

        Iterator<Chat> iterator = chats.iterator();
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));

        objectUnderTest.delete(chats);

        iterator = chats.iterator();
        assertNull(objectUnderTest.find(iterator.next()));
        assertNull(objectUnderTest.find(iterator.next()));
        assertNull(objectUnderTest.find(iterator.next()));
        chats.clear();
    }

    @Test
    @Transactional
    public void deleteFromUser() {
        User user = new User("test" + System.currentTimeMillis(), "", "");
        userService.save(user);
        User user1 = new User("test1" + System.currentTimeMillis(), "", "");
        userService.save(user1);
        Chat chat = new Chat();
        Message message = new Message("a", "b");
        chat.addMessage(message);
        objectUnderTest.save(chat);
        user.addChat(chat);
        userService.save(user);
        user1.addChat(chat);
        userService.save(user1);
        assertEquals(2, objectUnderTest.find(chat).getUsers().size());
        assertEquals(1, userService.find(user).getChats().size());
        assertEquals(1, userService.find(user1).getChats().size());
        userService.removeChat(user1.getId(), chat.getId());
        assertEquals(1, objectUnderTest.find(chat).getUsers().size());
        assertEquals(1, userService.find(user).getChats().size());
        assertEquals(0, userService.find(user1).getChats().size());

        objectUnderTest.delete(chat.getId());

        user = userService.find(user);
        assertNull(objectUnderTest.find(chat));
    }

    @Test
    public void deleteFromUser_1() {
        User user = new User("test" + System.currentTimeMillis(), "", "");
        userService.save(user);
        User user1 = new User("test1" + System.currentTimeMillis(), "", "");
        userService.save(user1);
        Chat chat = new Chat();
        Message message = new Message("a", "b");
        chat.addMessage(message);
        objectUnderTest.save(chat);
        user.addChat(chat);
        userService.save(user);
        user1.addChat(chat);
        userService.save(user1);
        assertEquals(2, objectUnderTest.find(chat).getUsers().size());
        assertEquals(1, userService.find(user).getChats().size());
        assertEquals(1, userService.find(user1).getChats().size());

        objectUnderTest.delete(chat.getId());

        assertNull(objectUnderTest.find(chat));
        assertEquals(0, userService.find(user).getChats().size());
        assertEquals(0, userService.find(user1).getChats().size());
    }

    @Test
    public void deleteFromMessage() {
        Chat chat = new Chat();
        objectUnderTest.delete(chat);
        Message message = new Message("", "");
        chat.addMessage(message);
        objectUnderTest.save(chat);
        assertNotNull(objectUnderTest.find(chat.getId()));
        assertEquals(1, objectUnderTest.find(chat.getId()).getMessages().size());

        objectUnderTest.delete(chat.getId());

        assertNull(messageService.find(message));
        assertNull(objectUnderTest.find(chat));
    }
}
