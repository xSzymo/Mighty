package game.mightywarriors.data.tables;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import org.hibernate.LazyInitializationException;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@Table(name = "inventories")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<InventoryItem> items;

    @OneToOne
    private User user;

    public Inventory() {
        items = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void addItem(InventoryItem item) throws Exception {
        if(items.size() > SystemVariablesManager.MAX_ITEMS_IN_INVENTORY)
            throw new Exception("Too many items");
        try {
            items.add(item);
        } catch (LazyInitializationException e) {
            items = new HashSet<>();
            items.add(item);
        }
    }

    public void addItem(Item item) throws Exception {
        if(items.size() > SystemVariablesManager.MAX_ITEMS_IN_INVENTORY)
            throw new Exception("Too many items");
        try {
            InventoryItem inventoryItem = new InventoryItem();
            inventoryItem.setItem(item);
            Iterator<InventoryItem> iterator = items.iterator();
            int i = 0;
            while(iterator.hasNext()) {
                InventoryItem next = iterator.next();
                if(next.getPosition() == i) {
                    i++;
                    iterator = items.iterator();
                }
            }

            inventoryItem.setPosition(i);
            items.add(inventoryItem);
        } catch (LazyInitializationException e) {
            items = new HashSet<>();
            InventoryItem inventoryItem = new InventoryItem();
            inventoryItem.setItem(item);
            Iterator<InventoryItem> iterator = items.iterator();
            int i = 0;
            while(iterator.hasNext()) {
                InventoryItem next = iterator.next();
                if(next.getPosition() == i) {
                    i++;
                    iterator = items.iterator();
                }
            }

            inventoryItem.setPosition(i);
            items.add(inventoryItem);
        }
    }

    public Set<InventoryItem> getItems() {
        try {
            items.isEmpty();
            return items;
        } catch (LazyInitializationException e) {
            items = new HashSet<>();
            return items;
        }
    }

    public void setItems(Set<InventoryItem> items) throws Exception {
        if(items.size() > SystemVariablesManager.MAX_ITEMS_IN_INVENTORY)
            throw new Exception("Too many items");
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
