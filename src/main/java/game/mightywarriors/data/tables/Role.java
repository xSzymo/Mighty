package game.mightywarriors.data.tables;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "guild_roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "role")
    private String role;
    @Column(name = "created_date")
    private Timestamp createdDate;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Collection<User> user = new HashSet<>();

    public Role() {
        createdDate = new Timestamp(System.currentTimeMillis());
    }

    public Role(String role) {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public Collection<User> getUsers() {
        return user;
    }

    public void setUsers(Collection<User> user) {
        this.user = user;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }
}
