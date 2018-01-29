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
    private String userLogin;
    @Column(name = "number")
    private long number;
    @Column(name = "time_stamp")
    private Timestamp timeStamp;

    public Message() {
        timeStamp = new Timestamp(System.currentTimeMillis());
    }

    public Message(String message, String userLogin) {
        timeStamp = new Timestamp(System.currentTimeMillis());
        this.message = message;
        this.userLogin = userLogin;
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

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }
}
