package game.mightywarriors.data.tables;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "shop")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToMany
    private List<Item> items;

    public Shop() {
        items = new LinkedList<Item>();
    }

    public Long getId() {
        return id;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(LinkedList<Item> items) {
        this.items = items;
    }
}
