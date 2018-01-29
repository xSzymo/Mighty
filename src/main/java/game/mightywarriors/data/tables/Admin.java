package game.mightywarriors.data.tables;

import game.mightywarriors.other.enums.ChatRole;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "chat_role")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "time_stamp")
    private Timestamp timeStamp;
    @Column(name = "role")
    private ChatRole chatRole;
    @Column(name = "login")
    private String login;

    public Admin() {
        timeStamp = new Timestamp(System.currentTimeMillis());
    }

    public Admin(ChatRole chatRole, String login) {
        timeStamp = new Timestamp(System.currentTimeMillis());
        this.chatRole = chatRole;
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Admin)) return false;
        Admin admin = (Admin) o;
        return Objects.equals(id, admin.id) &&
                chatRole == admin.chatRole &&
                Objects.equals(login, admin.login);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, chatRole, login);
    }

    public Long getId() {
        return id;
    }

    public ChatRole getChatRole() {
        return chatRole;
    }

    public void setChatRole(ChatRole chatRole) {
        this.chatRole = chatRole;
    }
    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
