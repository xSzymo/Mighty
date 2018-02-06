package game.mightywarriors.data.tables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import game.mightywarriors.other.enums.AuthorizationType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "authorization_codes")
public class AuthorizationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "authorizationCode", unique = true)
    @JsonIgnore
    private String authorizationCode;

    @Column(name = "new_value")
    @JsonIgnore
    private String newValue;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "type")
    private AuthorizationType type;

    public AuthorizationCode() {
        createdDate = new Timestamp(System.currentTimeMillis());
    }

    public AuthorizationCode(String authorizationCode) {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.authorizationCode = authorizationCode;
    }

    public AuthorizationCode(String authorizationCode, AuthorizationType type) {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.authorizationCode = authorizationCode;
        this.type = type;
    }

    public AuthorizationCode(String authorizationCode, AuthorizationType type, String newValue) {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.authorizationCode = authorizationCode;
        this.type = type;
        this.newValue = newValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorizationCode that = (AuthorizationCode) o;
        return Objects.equals(authorizationCode, that.authorizationCode) &&
                Objects.equals(newValue, that.newValue) &&
                type == that.type;
    }

    @Override
    public int hashCode() {

        return Objects.hash(authorizationCode, newValue, type);
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public AuthorizationType getType() {
        return type;
    }

    public void setType(AuthorizationType type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
