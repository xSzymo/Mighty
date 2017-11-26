package game.mightywarriors.helpers.casters;

import game.mightywarriors.data.tables.Monster;
import game.mightywarriors.helpers.counters.SpecificStatisticInFightsCounter;
import game.mightywarriors.json.objects.fights.ChampionModel;
import game.mightywarriors.other.enums.StatisticType;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class MonsterModelCaster {
    private final SpecificStatisticInFightsCounter specificStatisticInFightsCounter = new SpecificStatisticInFightsCounter();

    public ChampionModel castChampionToChampionModel(Monster monster) {
        ChampionModel championModel = new ChampionModel();
        championModel.build()
                .setHp(specificStatisticInFightsCounter.countStatisticForSpecificType(monster, StatisticType.VITALITY))
                .setArmor(specificStatisticInFightsCounter.countStatisticForSpecificType(monster, StatisticType.ARMOR))
                .setMagicResist(specificStatisticInFightsCounter.countStatisticForSpecificType(monster, StatisticType.MAGIC_RESIST))
                .setStrength(specificStatisticInFightsCounter.countStatisticForSpecificType(monster, StatisticType.STRENGTH))
                .setIntelligence(specificStatisticInFightsCounter.countStatisticForSpecificType(monster, StatisticType.INTELLIGENCE))
                .setCriticChance(specificStatisticInFightsCounter.countStatisticForSpecificType(monster, StatisticType.CRITIC_CHANCE))
                .setLevel(monster.getLevel());

        if (monster.getId() != null)
            championModel.setId(monster.getId());

        return championModel;
    }

    public LinkedList<ChampionModel> castChampionToChampionModel(List<Monster> monsters) {
        LinkedList<ChampionModel> championModels1 = new LinkedList<>();
        for (Monster championModel : monsters)
            championModels1.add(castChampionToChampionModel(championModel));

        return championModels1;
    }
}
