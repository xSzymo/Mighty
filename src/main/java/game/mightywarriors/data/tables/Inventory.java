package game.mightywarriors.data.tables;

import org.hibernate.LazyInitializationException;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "inventories")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Item> items;

    @OneToOne
    private User user;

    public Inventory() {
        items = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void addItem(Item item) {
        try {
            items.add(item);
        } catch (LazyInitializationException e) {
            items = new HashSet<>();
            items.add(item);
        }
    }

    public Set<Item> getItems() {
        try {
            items.isEmpty();
            return items;
        } catch (LazyInitializationException e) {
            items = new HashSet<>();
            return items;
        }
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
