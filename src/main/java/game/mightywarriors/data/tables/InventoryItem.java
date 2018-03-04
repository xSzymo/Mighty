package game.mightywarriors.data.tables;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "inventory_item")
public class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "position")
    private Integer position;
    @Column(name = "created_date")
    private Timestamp createdDate;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER)
    private Item item;

    public InventoryItem() {
        createdDate = new Timestamp(System.currentTimeMillis());
        position = 0;
    }

    public InventoryItem(Item item) {
        this.item = item;
        position = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryItem that = (InventoryItem) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(position, that.position) &&
                Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, position, item);
    }

    public Long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
