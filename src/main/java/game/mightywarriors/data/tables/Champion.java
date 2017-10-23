package game.mightywarriors.data.tables;

import javax.persistence.*;

@Entity
@Table(name = "champions")
public class Champion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne
    private Statistic statistic;

    @OneToOne
    private Shop shop;

    @OneToOne
    private Equipment equipment;

    public Champion() {

    }

    public Champion(Statistic statistic, Shop shop, Equipment equipment) {
        this.statistic = statistic;
        this.shop = shop;
        this.equipment = equipment;
    }

    public Long getId() {
        return id;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
