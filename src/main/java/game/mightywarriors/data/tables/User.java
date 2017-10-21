package game.mightywarriors.data.tables;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.LinkedList;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "e_mail")
    private String eMail;

    @OneToMany
    @JoinColumn(name="user_id", referencedColumnName="id")
    private Collection<Image> image = new LinkedList<Image>();

    @OneToOne
    private Statistic stats;

    @OneToOne
    private Champion champion;

    @Column(name = "time_stamp")
    private Timestamp timeStamp;

    private long experience = 0;
    private long level = 0;

    public User() {
        timeStamp = new Timestamp(System.currentTimeMillis());
    }

    public User(String login, String password, String eMail) {
        timeStamp = new Timestamp(System.currentTimeMillis());
        this.login = login;
        this.password = password;
        this.eMail = eMail;
    }

    public User(String login, String password, String eMail, LinkedList<Image> image, Statistic stats, Champion champion) {
        timeStamp = new Timestamp(System.currentTimeMillis());
        this.login = login;
        this.password = password;
        this.eMail = eMail;
        this.image = image;
        this.stats = stats;
        this.champion = champion;
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

    public Statistic getStatistic() {
        return stats;
    }

    public void setStatistic(Statistic stats) {
        this.stats = stats;
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

    public Timestamp getTimeStamp() {
        return timeStamp;
    }
}
