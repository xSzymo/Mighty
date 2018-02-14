package game.mightywarriors.data.tables;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "guilds")
public class Guild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "minimum_level")
    private int minimumLevel;
    @Column(name = "created_date")
    private Timestamp createdDate;

    @OneToOne
    private Chat chat;

    @OneToMany(mappedBy = "guild", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<User> users = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "guild_id", referencedColumnName = "id")
    private Set<Request> invites = new HashSet<>();

    public Guild() {
        createdDate = new Timestamp(System.currentTimeMillis());
    }

    public Guild(String name) {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.name = name;
    }

    public Guild(String name, int minimumLevel) {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.name = name;
        this.minimumLevel = minimumLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guild guild = (Guild) o;
        return id == guild.id &&
                minimumLevel == guild.minimumLevel &&
                Objects.equals(name, guild.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, minimumLevel);
    }

    public Long getId() {
        return id;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public Set<Request> getInvites() {
        return invites;
    }

    public void setInvites(Set<Request> invites) {
        this.invites = invites;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public int getMinimumLevel() {
        return minimumLevel;
    }

    public void setMinimumLevel(int minimumLevel) {
        this.minimumLevel = minimumLevel;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
