package game.mightywarriors.data.tables;


import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "admins")
    @ElementCollection(targetClass = String.class)
    private Set<String> admins = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private List<Message> messages = new LinkedList<>();

    @ManyToMany(mappedBy = "chats", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    private Set<User> users = new HashSet<>();

    public Chat() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chat)) return false;
        Chat chat = (Chat) o;
        return Objects.equals(id, chat.id) &&
                Objects.equals(messages, chat.messages);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, messages);
    }

    public Long getId() {
        return id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public Set<String> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<String> admins) {
        this.admins = admins;
    }

    public void addAdmin(String login) {
        this.admins.add(login);
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
