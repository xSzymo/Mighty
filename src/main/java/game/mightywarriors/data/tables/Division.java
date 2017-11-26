package game.mightywarriors.data.tables;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import game.mightywarriors.other.enums.League;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
@Table(name = "divisions")
public class Division {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "league")
    private League league;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "league_id", referencedColumnName = "id")
    @JsonManagedReference
    private Collection<User> users = new LinkedHashSet<User>();

    public Division() {

    }

    public Division(League league) {
        this.league = league;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long roleid) {
        this.id = roleid;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> user) {
        this.users = user;
    }

    public void clearUsers() {
        this.users.clear();
    }
}
