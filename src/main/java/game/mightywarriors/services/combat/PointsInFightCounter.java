package game.mightywarriors.services.combat;

import game.mightywarriors.configuration.system.SystemVariablesManager;
import game.mightywarriors.data.interfaces.IFighter;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.StatisticType;

public class PointsInFightCounter {

    public double getPointsForSpecificType(User user, StatisticType statisticType) {
        double points = 0;

        for (Champion x : user.getChampions())
            points = getPointsForSpecificType(x, statisticType);

        return Math.floor(points * 100) / 100;
    }

    public double getPointsForSpecificType(IFighter fighter, StatisticType statisticType) {
        double points = 0;

        double rate = fighter.getLevel() * SystemVariablesManager.RATE;

        if (statisticType.getType().equals(StatisticType.ARMOR.getType()))
            points += fighter.getStatistic().getArmor() * rate;
        if (statisticType.getType().equals(StatisticType.CRITIC_CHANCE.getType()))
            points += fighter.getStatistic().getCriticChance() * rate;
        if (statisticType.getType().equals(StatisticType.INTELLIGENCE.getType()))
            points += fighter.getStatistic().getIntelligence() * rate;
        if (statisticType.getType().equals(StatisticType.MAGIC_RESIST.getType()))
            points += fighter.getStatistic().getMagicResist() * rate;
        if (statisticType.getType().equals(StatisticType.STRENGTH.getType()))
            points += fighter.getStatistic().getStrength() * rate;
        if (statisticType.getType().equals(StatisticType.VITALITY.getType()))
            points += (fighter.getStatistic().getVitality() * rate) * SystemVariablesManager.HP_RATE;

        if (fighter instanceof Champion) {
            if(((Champion) fighter).getEquipment() != null) {
                if(((Champion) fighter).getEquipment().getArmor() != null)
                points += getPointsForSpecificItem(((Champion) fighter).getEquipment().getArmor(), statisticType) * rate;
                if(((Champion) fighter).getEquipment().getBoots() != null)
                points += getPointsForSpecificItem(((Champion) fighter).getEquipment().getBoots(), statisticType) * rate;
                if(((Champion) fighter).getEquipment().getBracelet() != null)
                points += getPointsForSpecificItem(((Champion) fighter).getEquipment().getBracelet(), statisticType) * rate;
                if(((Champion) fighter).getEquipment().getGloves() != null)
                points += getPointsForSpecificItem(((Champion) fighter).getEquipment().getGloves(), statisticType) * rate;
                if(((Champion) fighter).getEquipment().getHelmet() != null)
                points += getPointsForSpecificItem(((Champion) fighter).getEquipment().getHelmet(), statisticType) * rate;
                if(((Champion) fighter).getEquipment().getLegs() != null)
                points += getPointsForSpecificItem(((Champion) fighter).getEquipment().getLegs(), statisticType) * rate;
                if(((Champion) fighter).getEquipment().getNecklace() != null)
                points += getPointsForSpecificItem(((Champion) fighter).getEquipment().getNecklace(), statisticType) * rate;
                if(((Champion) fighter).getEquipment().getOffhand() != null)
                points += getPointsForSpecificItem(((Champion) fighter).getEquipment().getOffhand(), statisticType) * rate;
                if(((Champion) fighter).getEquipment().getRing() != null)
                points += getPointsForSpecificItem(((Champion) fighter).getEquipment().getRing(), statisticType) * rate;
                if(((Champion) fighter).getEquipment().getWeapon() != null)
                points += getPointsForSpecificItem(((Champion) fighter).getEquipment().getWeapon(), statisticType) * rate;
            }
        }

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
