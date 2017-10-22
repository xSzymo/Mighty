package game.mightywarriors.data.tables;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
@Table(name = "user_roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "role")
    private String role;

    @Column(name = "time_stamp")
    private Timestamp timeStamp;

    @OneToMany(mappedBy = "userRole", fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonManagedReference
    private Collection<User> user = new LinkedHashSet<User>();

    private UserRole() {
        timeStamp = new Timestamp(System.currentTimeMillis());
    }

    public UserRole(String role) {
        timeStamp = new Timestamp(System.currentTimeMillis());
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

    public void setId(Long roleid) {
        this.id = roleid;
    }

    public Collection<User> getUsers() {
        return user;
    }

    public void setUsers(Collection<User> user) {
        this.user = user;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }
}
