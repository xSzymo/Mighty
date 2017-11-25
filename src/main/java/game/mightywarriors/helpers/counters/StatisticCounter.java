package game.mightywarriors.helpers.counters;

import game.mightywarriors.configuration.system.SystemVariablesManager;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.Monster;
import game.mightywarriors.data.tables.User;
import org.springframework.stereotype.Service;

@Service
public class StatisticCounter {

    public double countMonsterStatistic(Monster monster) {
        double points = 0;

        double rate = monster.getLevel() * SystemVariablesManager.RATE;

        points += monster.getStatistic().getArmor() * rate;
        points += monster.getStatistic().getCriticChance() * rate;
        points += monster.getStatistic().getIntelligence() * rate;
        points += monster.getStatistic().getMagicResist() * rate;
        points += monster.getStatistic().getStrength() * rate;
        points += monster.getStatistic().getVitality() * rate;

        return points;
    }

    public double countUserStatistic(User user) {
        double points = 0;

        for (Champion x : user.getChampions()) {
            double rate = x.getLevel() * SystemVariablesManager.RATE;

            points += x.getStatistic().getArmor() * rate;
            points += x.getStatistic().getCriticChance() * rate;
            points += x.getStatistic().getIntelligence() * rate;
            points += x.getStatistic().getMagicResist() * rate;
            points += x.getStatistic().getStrength() * rate;
            points += x.getStatistic().getVitality() * rate;

            points += getPointsForSpecificItem(x.getEquipment().getArmor()) * rate;
            points += getPointsForSpecificItem(x.getEquipment().getBoots()) * rate;
            points += getPointsForSpecificItem(x.getEquipment().getBracelet()) * rate;
            points += getPointsForSpecificItem(x.getEquipment().getGloves()) * rate;
            points += getPointsForSpecificItem(x.getEquipment().getHelmet()) * rate;
            points += getPointsForSpecificItem(x.getEquipment().getLegs()) * rate;
            points += getPointsForSpecificItem(x.getEquipment().getNecklace()) * rate;
            points += getPointsForSpecificItem(x.getEquipment().getOffhand()) * rate;
            points += getPointsForSpecificItem(x.getEquipment().getRing()) * rate;
            points += getPointsForSpecificItem(x.getEquipment().getWeapon()) * rate;
        }

        return points;
    }

    private double getPointsForSpecificItem(Item item) {
        if (item == null)
            return 0;

        double points = 0;

        points += item.getStatistic().getArmor();
        points += item.getStatistic().getCriticChance();
        points += item.getStatistic().getIntelligence();
        points += item.getStatistic().getMagicResist();
        points += item.getStatistic().getStrength();
        points += item.getStatistic().getVitality();

        return points;
    }
}
