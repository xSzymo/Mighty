package game.mightywarriors.services.league;

import game.mightywarriors.configuration.system.SystemVariablesManager;
import game.mightywarriors.data.interfaces.Fighter;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.User;

public class PointsForDivisionCounter {

    public double getPointsOfFighterPower(Fighter fighter) {
        double points = 0;

        double rate = fighter.getLevel() * SystemVariablesManager.RATE;

        points += fighter.getStatistic().getArmor() * rate;
        points += fighter.getStatistic().getCriticChance() * rate;
        points += fighter.getStatistic().getIntelligence() * rate;
        points += fighter.getStatistic().getMagicResist() * rate;
        points += fighter.getStatistic().getStrength() * rate;
        points += fighter.getStatistic().getVitality() * rate;

        if (fighter instanceof Champion) {
            points += getPointsForSpecificItem(((Champion) fighter).getEquipment().getArmor()) * rate;
            points += getPointsForSpecificItem(((Champion) fighter).getEquipment().getBoots()) * rate;
            points += getPointsForSpecificItem(((Champion) fighter).getEquipment().getBracelet()) * rate;
            points += getPointsForSpecificItem(((Champion) fighter).getEquipment().getGloves()) * rate;
            points += getPointsForSpecificItem(((Champion) fighter).getEquipment().getHelmet()) * rate;
            points += getPointsForSpecificItem(((Champion) fighter).getEquipment().getLegs()) * rate;
            points += getPointsForSpecificItem(((Champion) fighter).getEquipment().getNecklace()) * rate;
            points += getPointsForSpecificItem(((Champion) fighter).getEquipment().getOffhand()) * rate;
            points += getPointsForSpecificItem(((Champion) fighter).getEquipment().getRing()) * rate;
            points += getPointsForSpecificItem(((Champion) fighter).getEquipment().getWeapon()) * rate;
        }

        return points;
    }

    public double getPointsOfFighterPower(User user) {
        double points = 0;

        for (Champion x : user.getChampions())
            points = getPointsOfFighterPower(x);

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
