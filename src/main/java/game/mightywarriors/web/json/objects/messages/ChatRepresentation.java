package game.mightywarriors.web.json.objects.messages;

import game.mightywarriors.data.tables.Admin;
import game.mightywarriors.data.tables.Chat;
import game.mightywarriors.data.tables.Message;
import game.mightywarriors.data.tables.User;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ChatRepresentation {
    public long id;
    public Timestamp timeStamp;
    public Set<Admin> admins;
    public List<Message> messages;
    public Set<User> users;

    public ChatRepresentation(Chat chat) {
        this.id = chat.getId();
        this.timeStamp = chat.getCreatedDate();
        this.admins = chat.getAdmins();
        this.users = chat.getUsers();
        this.messages = new LinkedList<>(chat.getMessages());

        sortMessages();
    }

    public ChatRepresentation(long id, Timestamp timeStamp, Set<Admin> admins, Set<Message> messages, Set<User> users) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.admins = admins;
        this.messages = new LinkedList<>(messages);
        this.users = users;

        sortMessages();
    }

    public ChatRepresentation(long id, Timestamp timeStamp, Set<Admin> admins, List<Message> messages, Set<User> users) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.admins = admins;
        this.messages = messages;
        this.users = users;

        sortMessages();
    }

    private void sortMessages() {
        this.messages.sort((message, message1) -> {
            if (message.getNumber() == message1.getNumber())
                return 0;
            return message.getNumber() < message1.getNumber() ? -1 : 1;
        });
    }
}
