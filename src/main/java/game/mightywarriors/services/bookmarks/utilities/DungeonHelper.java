package game.mightywarriors.services.bookmarks.utilities;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.tables.Dungeon;
import game.mightywarriors.data.tables.Floor;
import game.mightywarriors.data.tables.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class DungeonHelper {
    public void throwExceptionIf_DungeonsFloorsAreNotSetProperly(User user) throws Exception {
        int i = 1;
        int[] floors = new int[11];

        for (Floor floor : user.getDungeon().getDungeon().getFloors())
            floors[i++] = floor.getFloor();

        Arrays.sort(floors);

        for (i = 1; i <= SystemVariablesManager.MAX_FLOORS_PER_DUNGEON; i++)
            if (i != floors[i])
                throw new Exception("The dungeon wasn't set up properly, please contact with admin");
    }

    public Floor getCurrentFloor(User user) throws Exception {
        Dungeon dungeon = user.getDungeon().getDungeon();
        Optional<Floor> floor = dungeon.getFloors().stream().filter(x -> x.getFloor() == user.getDungeon().getCurrentFloor()).findFirst();
        if (!floor.isPresent())
            throw new Exception("The floor wasn't prepare correct, please contact with admin");

        return floor.get();
    }
}
