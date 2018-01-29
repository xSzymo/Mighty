package game.mightywarriors.data.tables;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url")
    private String url;
    @Column(name = "created_date")
    private Timestamp createdDate;

    public Image() {
        createdDate = new Timestamp(System.currentTimeMillis());
    }

    public Image(String url) {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }
}
