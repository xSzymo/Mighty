package game.mightywarriors.data.tables;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "dungeon_fights")
public class DungeonFight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "block_until")
    private Timestamp blockUntil;
    @Column(name = "created_date")
    private Timestamp departedTime;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    public DungeonFight() {
        departedTime = new Timestamp(System.currentTimeMillis());
    }

    public Long getId() {
        return id;
    }

    public Timestamp getBlockUntil() {
        return blockUntil;
    }

    public void setBlockUntil(Timestamp blockUntil) {
        this.blockUntil = blockUntil;
    }

    public Timestamp getDepartedTime() {
        return departedTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
