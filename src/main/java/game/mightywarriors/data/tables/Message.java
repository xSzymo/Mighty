package game.mightywarriors.data.tables;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "message")
    private String message;
    @Column(name = "user_login")
    private String login;
    @Column(name = "number")
    private long number;
    @Column(name = "created_date")
    private Timestamp createdDate;

    public Message() {
        createdDate = new Timestamp(System.currentTimeMillis());
    }

    public Message(String message, String login) {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.message = message;
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message1 = (Message) o;
        return Objects.equals(id, message1.id) &&
                Objects.equals(message, message1.message);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, message);
    }

    public Long getId() {
        return id;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }
}
