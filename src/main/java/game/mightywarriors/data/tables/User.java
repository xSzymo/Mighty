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
    private Collection<Image> image = new LinkedList<Image>();

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @JsonBackReference
    private UserRole userRole;

    @OneToOne
    private Champion champion;

    @Column(name = "time_stamp")
    private Timestamp timeStamp;

    @Column(name = "experience")
    private long experience = 0;

    @Column(name = "level")
    private long level = 0;

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

    public User(String login, String password, String eMail, LinkedList<Image> image, Statistic stats, Champion champion, UserRole userRole) {
        timeStamp = new Timestamp(System.currentTimeMillis());
        this.login = login;
        this.password = password;
        this.eMail = eMail;
        this.image = image;
        this.champion = champion;
        this.userRole = userRole;
    }

    public User(User user) {
        timeStamp = new Timestamp(System.currentTimeMillis());
        this.id = user.id;
        this.login = user.login;
        this.password = user.password;
        this.eMail = user.password;
        this.image = user.image;
        this.champion = user.champion;
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

    public Champion getChampion() {
        return champion;
    }

    public void setChampion(Champion champion) {
        this.champion = champion;
    }

    public long getExperience() {
        return experience;
    }

    public void setExperience(long experience) {
        this.experience = experience;
    }

    public long getLevel() {
        return level;
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
}
