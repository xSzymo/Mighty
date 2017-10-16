package game.mightywarriors.data.tables;

import javax.persistence.*;

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
    @Column(name = "cookie_code")
    private String cookieCode;

    @OneToOne
    @JoinColumn(name = "champion_id")
    private Champion champion;

    public User(String login, String password, String eMail) {
        this.login = login;
        this.password = password;
        this.eMail = eMail;
    }

    public User(String login, String password, String eMail, String cookieCode, Champion champion) {
        this.login = login;
        this.password = password;
        this.eMail = eMail;
        this.cookieCode = cookieCode;
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

    public String getCookieCode() {
        return cookieCode;
    }

    public void setCookieCode(String cookieCode) {
        this.cookieCode = cookieCode;
    }

    public Champion getChampion() {
        return champion;
    }

    public void setChampion(Champion champion) {
        this.champion = champion;
    }
}
