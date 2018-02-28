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
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "stage", unique = true)
    private int stage = 0;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Image imageLight;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Image imageDark;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "dungeon_id", referencedColumnName = "id")
    private Set<Floor> floors = new HashSet<>();

    public Dungeon() {
        createdDate = new Timestamp(System.currentTimeMillis());
    }

    public Dungeon(String name, int stage, Image imageLight, Set<Floor> floors) {
        this.name = name;
        this.stage = stage;
        this.imageLight = imageLight;
        this.floors = floors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dungeon dungeon = (Dungeon) o;
        return stage == dungeon.stage &&
                Objects.equals(name, dungeon.name) &&
                Objects.equals(imageLight, dungeon.imageLight);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, stage, imageLight);
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

    public Image getImageLight() {
        return imageLight;
    }

    public void setImageLight(Image imageLight) {
        this.imageLight = imageLight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public Image getImageDark() {
        return imageDark;
    }

    public void setImageDark(Image imageDark) {
        this.imageDark = imageDark;
    }
}
