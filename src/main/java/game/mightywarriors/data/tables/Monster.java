package game.mightywarriors.data.tables;

import game.mightywarriors.data.interfaces.IFighter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "monsters")
public class Monster implements IFighter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "level")
    private long level;
    @Column(name = "created_date")
    private Timestamp createdDate;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Statistic statistic;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Image image;

    public Monster() {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.level = 1;
    }

    public Monster(Statistic statistic, Image image) {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.statistic = statistic;
        this.image = image;
        this.level = 1;
    }

    public Monster(Statistic statistic) {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.statistic = statistic;
        this.level = 1;
    }

    public Image getImage() {
        return image;
    }

    public Monster setImage(Image image) {
        this.image = image;
        return this;
    }

    @Override
    public Statistic getStatistic() {
        return statistic;
    }

    public Monster setStatistic(Statistic statistic) {
        this.statistic = statistic;
        return this;
    }

    public Long getId() {
        return id;
    }

    public long getLevel() {
        return level;
    }

    public Monster setLevel(int level) {
        this.level = level;
        return this;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }
}
