package game.mightywarriors.data.tables;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.LinkedList;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "e_mail")
    private String eMail;

    @OneToMany
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Collection<Image> image = new LinkedList<>();

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @JsonBackReference
    private UserRole userRole;

    @OneToMany
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Collection<Champion> champions = new LinkedList<>();

    @Column(name = "time_stamp")
    private Timestamp timeStamp;

    public User() {
        timeStamp = new Timestamp(System.currentTimeMillis());
    }

    public User(String login, String password, String eMail, UserRole userRole) {
        timeStamp = new Timestamp(System.currentTimeMillis());
        this.login = login;
        this.password = password;
        this.eMail = eMail;
        this.userRole = userRole;
    }

    public User(String login, String password, String eMail, LinkedList<Image> image, Champion champion, UserRole userRole) {
        timeStamp = new Timestamp(System.currentTimeMillis());
        this.login = login;
        this.password = password;
        this.eMail = eMail;
        this.image = image;
        this.champions.add(champion);
        this.userRole = userRole;
    }

    public User(User user) {
        timeStamp = new Timestamp(System.currentTimeMillis());
        this.id = user.id;
        this.login = user.login;
        this.password = user.password;
        this.eMail = user.password;
        this.image = user.image;
        this.champions = user.champions;
        this.userRole = user.userRole;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public Collection<Image> getImage() {
        return image;
    }

    public void setImage(LinkedList<Image> image) {
        this.image = image;
    }


    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }


    public Collection<Champion> getChampions() {
        return champions;
    }

    public void setChampions(LinkedList<Champion> champions) {
        this.champions = champions;
    }

    public void addChampion(Champion champion) {
        this.champions.add(champion);
    }
}
