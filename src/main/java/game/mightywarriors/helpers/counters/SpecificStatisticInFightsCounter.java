package game.mightywarriors.helpers.counters;

import game.mightywarriors.configuration.system.SystemVariablesManager;
import game.mightywarriors.data.enums.StatisticType;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.User;
import org.springframework.stereotype.Service;

@Service
public class SpecificStatisticInFightsCounter {

    public double countUserStatistic(User user, StatisticType statisticType) {
        double points = 0;

        for (Champion x : user.getChampions()) {
            double rate = x.getLevel() * SystemVariablesManager.RATE;

            if (statisticType.getType().equals(StatisticType.ARMOR.getType()))
                points += x.getStatistic().getArmor() * rate;
            if (statisticType.getType().equals(StatisticType.CRITIC_CHANCE.getType()))
                points += x.getStatistic().getCriticChance() * rate;
            if (statisticType.getType().equals(StatisticType.INTELLIGENCE.getType()))
                points += x.getStatistic().getIntelligence() * rate;
            if (statisticType.getType().equals(StatisticType.MAGIC_RESIST.getType()))
                points += x.getStatistic().getMagicResist() * rate;
            if (statisticType.getType().equals(StatisticType.STRENGTH.getType()))
                points += x.getStatistic().getStrength() * rate;
            if (statisticType.getType().equals(StatisticType.VITALITY.getType()))
                points += (x.getStatistic().getVitality() * rate) * SystemVariablesManager.HP_RATE;

            points += getPointsForSpecificItem(x.getEquipment().getArmor(), statisticType) * rate;
            points += getPointsForSpecificItem(x.getEquipment().getBoots(), statisticType) * rate;
            points += getPointsForSpecificItem(x.getEquipment().getBracelet(), statisticType) * rate;
            points += getPointsForSpecificItem(x.getEquipment().getGloves(), statisticType) * rate;
            points += getPointsForSpecificItem(x.getEquipment().getHelmet(), statisticType) * rate;
            points += getPointsForSpecificItem(x.getEquipment().getLegs(), statisticType) * rate;
            points += getPointsForSpecificItem(x.getEquipment().getNecklace(), statisticType) * rate;
            points += getPointsForSpecificItem(x.getEquipment().getOffhand(), statisticType) * rate;
            points += getPointsForSpecificItem(x.getEquipment().getRing(), statisticType) * rate;
            points += getPointsForSpecificItem(x.getEquipment().getWeapon(), statisticType) * rate;
        }


        return Math.floor(points * 100) / 100;
    }

    public double countChampionStatistic(Champion champion, StatisticType statisticType) {
        double points = 0;

            double rate = champion.getLevel() * SystemVariablesManager.RATE;

            if (statisticType.getType().equals(StatisticType.ARMOR.getType()))
                points += champion.getStatistic().getArmor() * rate;
            if (statisticType.getType().equals(StatisticType.CRITIC_CHANCE.getType()))
                points += champion.getStatistic().getCriticChance() * rate;
            if (statisticType.getType().equals(StatisticType.INTELLIGENCE.getType()))
                points += champion.getStatistic().getIntelligence() * rate;
            if (statisticType.getType().equals(StatisticType.MAGIC_RESIST.getType()))
                points += champion.getStatistic().getMagicResist() * rate;
            if (statisticType.getType().equals(StatisticType.STRENGTH.getType()))
                points += champion.getStatistic().getStrength() * rate;
            if (statisticType.getType().equals(StatisticType.VITALITY.getType()))
                points += (champion.getStatistic().getVitality() * rate) * SystemVariablesManager.HP_RATE;

            points += getPointsForSpecificItem(champion.getEquipment().getArmor(), statisticType) * rate;
            points += getPointsForSpecificItem(champion.getEquipment().getBoots(), statisticType) * rate;
            points += getPointsForSpecificItem(champion.getEquipment().getBracelet(), statisticType) * rate;
            points += getPointsForSpecificItem(champion.getEquipment().getGloves(), statisticType) * rate;
            points += getPointsForSpecificItem(champion.getEquipment().getHelmet(), statisticType) * rate;
            points += getPointsForSpecificItem(champion.getEquipment().getLegs(), statisticType) * rate;
            points += getPointsForSpecificItem(champion.getEquipment().getNecklace(), statisticType) * rate;
            points += getPointsForSpecificItem(champion.getEquipment().getOffhand(), statisticType) * rate;
            points += getPointsForSpecificItem(champion.getEquipment().getRing(), statisticType) * rate;
            points += getPointsForSpecificItem(champion.getEquipment().getWeapon(), statisticType) * rate;

        return Math.floor(points * 100) / 100;
    }

    private double getPointsForSpecificItem(Item item, StatisticType statisticType) {
        if (item == null)
            return 0;

        double points = 0;

        if (statisticType.getType().equals(StatisticType.ARMOR.getType()))
            points += item.getStatistic().getArmor();
        if (statisticType.getType().equals(StatisticType.CRITIC_CHANCE.getType()))
            points += item.getStatistic().getCriticChance();
        if (statisticType.getType().equals(StatisticType.INTELLIGENCE.getType()))
            points += item.getStatistic().getIntelligence();
        if (statisticType.getType().equals(StatisticType.MAGIC_RESIST.getType()))
            points += item.getStatistic().getMagicResist();
        if (statisticType.getType().equals(StatisticType.STRENGTH.getType()))
            points += item.getStatistic().getStrength();
        if (statisticType.getType().equals(StatisticType.VITALITY.getType()))
            points += item.getStatistic().getVitality() * SystemVariablesManager.HP_RATE;

        return points;
    }
}
