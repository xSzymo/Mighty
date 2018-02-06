package game.mightywarriors.data.tables;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "dungeons")
public class Dungeon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Image image;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "dungeon_id", referencedColumnName = "id")
    private Set<Floor> floors = new HashSet<>();

    public Dungeon() {
        createdDate = new Timestamp(System.currentTimeMillis());
    }

    public Dungeon(Image image, Set<Floor> floors) {
        this.image = image;
        this.floors = floors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dungeon dungeon = (Dungeon) o;
        return Objects.equals(id, dungeon.id) &&
                Objects.equals(createdDate, dungeon.createdDate) &&
                Objects.equals(floors, dungeon.floors);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, createdDate, floors);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Floor> getFloors() {
        return floors;
    }

    public void setFloors(Set<Floor> floors) {
        this.floors = floors;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
