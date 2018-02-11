package game.mightywarriors.data.tables;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;
    @Column(name = "description")
    private String description;
    @Column(name = "created_date")
    private Timestamp createdDate;

    public Request() {
        createdDate = new Timestamp(System.currentTimeMillis());
    }

    public Request(String description) {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.description = description;
    }

    public Request(User user, String description) {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.user = user;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(id, request.id) &&
                Objects.equals(user, request.user) &&
                Objects.equals(description, request.description) &&
                Objects.equals(createdDate, request.createdDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, user, description, createdDate);
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}
