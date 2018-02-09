package game.mightywarriors.data.tables;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "users_dungeons")
public class UserDungeon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "current_floor")
    private long currentFloor = 10;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @OneToOne(fetch = FetchType.EAGER)
    private Dungeon dungeon;


    public UserDungeon() {
        createdDate = new Timestamp(System.currentTimeMillis());
    }

    public UserDungeon(Dungeon dungeon) {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.dungeon = dungeon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDungeon that = (UserDungeon) o;
        return currentFloor == that.currentFloor &&
                Objects.equals(id, that.id) &&
                Objects.equals(dungeon, that.dungeon);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, currentFloor, dungeon);
    }

    public Long getId() {
        return id;
    }

    public long getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(long currentFloor) {
        this.currentFloor = currentFloor;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }
}
