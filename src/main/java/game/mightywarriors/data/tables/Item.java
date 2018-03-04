package game.mightywarriors.data.tables;

import game.mightywarriors.other.enums.ItemType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "type_of_weapon")
    private ItemType itemType;
    @Column(name = "level")
    private long level;
    @Column(name = "created_date")
    private Timestamp createdDate;
    @Column(name = "gold")
    private BigDecimal gold = new BigDecimal("0");

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Statistic statistic;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Image imageLight;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Image imageDark;

    public Item() {
        createdDate = new Timestamp(System.currentTimeMillis());
    }

    public Item(ItemType itemType) {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.itemType = itemType;
    }

    public Item(ItemType itemType, Statistic statistic, long level) {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.itemType = itemType;
        this.statistic = statistic;
        this.level = level;
    }

    public Item(String name, ItemType itemType, long level) {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.itemType = itemType;
        this.name = name;
        this.level = level;
    }

    public Item(String name, ItemType itemType, Statistic statistic, long level) {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.name = name;
        this.itemType = itemType;
        this.statistic = statistic;
        this.level = level;
    }

    public Item(String name, String description, ItemType itemType, Statistic statistic, Image imageLight, long level) {
        createdDate = new Timestamp(System.currentTimeMillis());
        this.name = name;
        this.description = description;
        this.itemType = itemType;
        this.statistic = statistic;
        this.imageLight = imageLight;
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return level == item.level &&
                Objects.equals(id, item.id) &&
                Objects.equals(name, item.name) &&
                itemType == item.itemType &&
                Objects.equals(gold, item.gold);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, itemType, level, gold);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }

    public Image getImageLight() {
        return imageLight;
    }

    public void setImageLight(Image imageLight) {
        this.imageLight = imageLight;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public BigDecimal getGold() {
        return gold;
    }

    public void setGold(BigDecimal gold) {
        this.gold = gold;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public Image getImageDark() {
        return imageDark;
    }

    public void setImageDark(Image imageDark) {
        this.imageDark = imageDark;
    }
}
