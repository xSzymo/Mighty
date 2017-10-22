package game.mightywarriors.data.tables;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
@Table(name = "shop")
public class Shop {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @OneToMany
    private Collection<Item> items;

    public Shop() {
        items = new LinkedHashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Collection<Item> getItems() {
        return items;
    }

    public void setItems(Collection<Item> items) {
        this.items = items;
    }
}
