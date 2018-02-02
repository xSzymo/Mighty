package integration.data.services;

import game.mightywarriors.data.services.ChatService;
import game.mightywarriors.data.services.MessageService;
import game.mightywarriors.data.tables.*;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class MessagesServiceTest extends IntegrationTestsConfig {
    @Autowired
    private MessageService objectUnderTest;
    @Autowired
    private ChatService chatService;

    private LinkedList<Message> messages;
    private Item item;
    private Champion champion;
    private Monster monster;

    @Before
    public void beforeEachTest() {
        messages = new LinkedList<>();
        messages.add(new Message("", "a"));
        messages.add(new Message("", "b"));
        messages.add(new Message("", "c"));
    }

    @After
    public void afterEachTest() {
        messages.forEach(objectUnderTest::delete);
    }

    @Test
    public void save() {
        objectUnderTest.save(messages.iterator().next());

        assertNotNull(objectUnderTest.find(messages.iterator().next()));
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(messages);

        Iterator<Message> iterator = messages.iterator();
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
    }

    @Test
    public void findOne() {
        objectUnderTest.save(messages.iterator().next());

        assertNotNull(objectUnderTest.find(messages.iterator().next()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(messages.iterator().next());

        assertNotNull(objectUnderTest.find(messages.iterator().next().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(messages);

        Iterator<Message> iterator = messages.iterator();
        List<Message> list = new ArrayList<>();
        iterator.forEachRemaining(list::add);
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(0).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(1).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(2).getId())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(messages.iterator().next());

        assertNotNull(objectUnderTest.find(messages.iterator().next()));

        objectUnderTest.delete(messages.iterator().next());

        assertNull(objectUnderTest.find(messages.iterator().next()));
        messages.clear();
    }

    @Test
    public void delete1() {
        objectUnderTest.save(messages.iterator().next());

        assertNotNull(objectUnderTest.find(messages.iterator().next()));

        objectUnderTest.delete(messages.iterator().next().getId());

        assertNull(objectUnderTest.find(messages.iterator().next()));
        messages.clear();
    }

    @Test
    public void delete2() {
        objectUnderTest.save(messages);

        Iterator<Message> iterator = messages.iterator();
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));

        objectUnderTest.delete(messages);

        iterator = messages.iterator();
        assertNull(objectUnderTest.find(iterator.next()));
        assertNull(objectUnderTest.find(iterator.next()));
        assertNull(objectUnderTest.find(iterator.next()));
        messages.clear();
    }

    @Test
    public void deleteFromChat() {
        Message message = messages.iterator().next();
        Chat chat = new Chat();
        chat.addMessage(message);
        chatService.save(chat);
        assertNotNull(objectUnderTest.find(message.getId()));
        assertNotNull(chatService.find(chat.getId()));
        assertEquals(objectUnderTest.find(message).getId(), chatService.find(chat).getMessages().iterator().next().getId());
        assertEquals(1, chatService.find(chat).getMessages().size());

        objectUnderTest.delete(message);

        assertNull(objectUnderTest.find(message.getId()));
        assertNotNull(chatService.find(chat.getId()));
        assertEquals(0, chatService.find(chat).getMessages().size());
    }
}
