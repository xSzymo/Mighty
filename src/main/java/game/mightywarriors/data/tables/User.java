package game.mightywarriors.data.tables;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.LazyInitializationException;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @JsonIgnore
    @Transient
    private boolean newToken;
    @Column(name = "token_code", unique = true)
    @JsonIgnore
    private String tokenCode;
    @Column(name = "code_to_enable_account", unique = true)
    @JsonIgnore
    private String codeToEnableAccount;

    @Column(name = "login", unique = true)
    private String login;
    @Column(name = "password")
    @JsonIgnore
    private String password;
    @Column(name = "e_mail")
    private String eMail;

    @Column(name = "is_account_enabled")
    private boolean isAccountEnabled = false;
    @Column(name = "is_account_non_locked")
    private boolean isAccountNonLocked = true;

    @Column(name = "gold")
    private BigDecimal gold = new BigDecimal("0");
    @Column(name = "created_date")
    private Timestamp createdDate;
    @Column(name = "arena_points")
    private int arenaPoints;
    @Column(name = "mission_points")
    private int missionPoints;

    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<AuthorizationCode> authorizationCodes;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Image image;

    @OneToOne(fetch = FetchType.EAGER)
    private Shop shop;

    @OneToOne(fetch = FetchType.EAGER)
    private Inventory inventory;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Set<Champion> champions = new ChampionCollection();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Set<Mission> missions = new MissionCollection();

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(name = "user_chats",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "id"))
    private Set<Chat> chats = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @JsonBackReference
    private UserRole userRole;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "division_id", referencedColumnName = "id")
    @JsonBackReference
    private Division division;

    public User() {
        createdDate = new Timestamp(System.currentTimeMillis());
    }

    public User(String login) {
        this.login = login;
        createdDate = new Timestamp(System.currentTimeMillis());
    }

    public User(String login, String password, String eMail) {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.login = login;
        this.password = password;
        this.eMail = eMail;
    }

    public User(String login, String password, String eMail, UserRole userRole) {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.login = login;
        this.password = password;
        this.eMail = eMail;
        this.userRole = userRole;
    }

    public User(String login, String password, String eMail, Image image, Champion champion, UserRole userRole) {
        createdDate = new Timestamp(System.currentTimeMillis());
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
        this.tokenCode = user.tokenCode;
        this.gold = user.gold;
        this.champions = user.champions;
        this.missions = user.missions;
        this.userRole = user.userRole;
        this.chats = user.chats;
        this.createdDate = user.createdDate;
        this.tokenCode = user.tokenCode;
        this.inventory = user.inventory;
        this.newToken = user.newToken;
        this.missionPoints = user.missionPoints;
        this.arenaPoints = user.arenaPoints;
        this.division = user.division;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(login, user.login) &&
                Objects.equals(eMail, user.eMail);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, login, eMail);
    }

    public long getId() {
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String geteMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Set<Champion> getChampions() {
        try {
            champions.isEmpty();
            return champions;
        } catch (Exception e) {
            champions = new ChampionCollection();
            return champions;
        }
    }

    public void setChampions(Set<Champion> champions) {
        this.champions = champions;
    }

    public long getUserChampionHighestLevel() {
        long highest = 0;

        for (Champion champion : getChampions())
            if (highest < champion.getLevel())
                highest = champion.getLevel();

        return highest;
    }

    public User addChampion(Champion champion) {
        this.champions.add(champion);
        return this;
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

    public User addGold(BigDecimal gold) {
        this.gold = this.gold.add(gold);
        return this;
    }

    public User subtractGold(BigDecimal gold) {
        this.gold = this.gold.subtract(gold);
        return this;
    }

    public Set<Mission> getMissions() {
        try {
            missions.isEmpty();
            return missions;
        } catch (LazyInitializationException e) {
            missions = new MissionCollection();
            return missions;
        }
    }

    public void setMissions(Set<Mission> missions) {
        this.missions = missions;
    }

    public void addMission(Mission mission) {
        try {
            missions.add(mission);
        } catch (LazyInitializationException e) {
            missions = new MissionCollection();
            missions.add(mission);
        }
    }

    public String getTokenCode() {
        return tokenCode;
    }

    public void setTokenCode(String tokenCode) {
        this.tokenCode = tokenCode;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public boolean isNewToken() {
        return newToken;
    }

    public void setNewToken(boolean newToken) {
        this.newToken = newToken;
    }

    public int getArenaPoints() {
        return arenaPoints;
    }

    public void setArenaPoints(int arenaPoints) {
        this.arenaPoints = arenaPoints;
    }

    public int getMissionPoints() {
        return missionPoints;
    }

    public void setMissionPoints(int missionPoints) {
        this.missionPoints = missionPoints;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Set<Chat> getChats() {
        return chats;
    }

    public void setChats(Set<Chat> chats) {
        this.chats = chats;
    }

    public void addChat(Chat chat) {
        this.chats.add(chat);
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public String getCodeToEnableAccount() {
        return codeToEnableAccount;
    }

    public void setCodeToEnableAccount(String codeToEnableAccount) {
        this.codeToEnableAccount = codeToEnableAccount;
    }

    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public boolean isAccountEnabled() {
        return isAccountEnabled;
    }

    public void setAccountEnabled(boolean accountEnabled) {
        isAccountEnabled = accountEnabled;
    }

    public Set<AuthorizationCode> getAuthorizationCodes() {
        return authorizationCodes;
    }

    public void setAuthorizationCodes(Set<AuthorizationCode> authorizationCodes) {
        this.authorizationCodes = authorizationCodes;
    }

    private class MissionCollection extends HashSet<Mission> {
        @Override
        public final boolean add(Mission a) {
            return this.size() <= 3 && super.add(a);
        }
    }

    private class ChampionCollection extends HashSet<Champion> {
        @Override
        public final boolean add(Champion a) {
            return this.size() <= 3 && super.add(a);
        }
    }
}
