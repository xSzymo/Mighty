package game.mightywarriors.data.tables;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

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
    @Column(name = "gold")
    private BigDecimal gold = new BigDecimal("0");
    @Column(name = "time_stamp")
    private Timestamp timeStamp;

    @OneToOne
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @JsonBackReference
    private UserRole userRole;

    @OneToMany
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<Image> image = new LinkedList<>();

    @OneToMany
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<Mission> missions = new MissionCollection();

    @OneToMany
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<Champion> champions = new ChampionCollection();

    private class MissionCollection extends LinkedList<Mission> {
        @Override
        public final boolean add(Mission a) {
            if(this.size() > 3)
                return false;
            return super.add(a);
        }
    }

    private class ChampionCollection extends LinkedList<Champion> {
        @Override
        public final boolean add(Champion a) {
            if(this.size() > 3)
                return false;
            return super.add(a);
        }
    }

    public User() {
        timeStamp = new Timestamp(System.currentTimeMillis());
    }

    public User(String login, String password, String eMail, UserRole userRole) {
        missions.add(new Mission());
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
        this.id = user.id;
        this.login = user.login;
        this.password = user.password;
        this.eMail = user.eMail;
        this.shop = user.shop;
        this.image = user.image;
        this.champions = user.champions;
        this.userRole = user.userRole;
        this.timeStamp = user.timeStamp;
    }

    public void setUser(User user) {
        this.id = user.id;
        this.login = user.login;
        this.password = user.password;
        this.eMail = user.eMail;
        this.shop = user.shop;
        this.image = user.image;
        this.champions = user.champions;
        this.userRole = user.userRole;
        this.timeStamp = user.timeStamp;
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

    public List<Image> getImage() {
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


    public List<Champion> getChampions() {
        return champions;
    }

    public void setChampions(LinkedList<Champion> champions) {
        this.champions = champions;
    }

    public void addChampion(Champion champion) {
        this.champions.add(champion);
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public BigDecimal getGold() {
        return gold;
    }

    public void setGold(BigDecimal gold) {
        this.gold = gold;
    }

    public List<Mission> getMissions() {
        return missions;
    }

    public void setMissions(LinkedList<Mission> missions) {
        this.missions = missions;
    }
}
