package game.mightywarriors.data.tables;

import org.hibernate.LazyInitializationException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
        items = new LinkedList<>();
    }

    public Long getId() {
        return id;
    }

    public void addItem(Item item) {
        try {
            items.add(item);
        } catch (LazyInitializationException e) {
            items = new ArrayList<>();
            items.add(item);
        }
    }

    public List<Item> getItems() {
        try {
            items.isEmpty();
            return items;
        } catch (LazyInitializationException e) {
            items = new ArrayList<>();
            return items;
        }
    }

    public void setItems(LinkedList<Item> items) {
        this.items = items;
    }
}
