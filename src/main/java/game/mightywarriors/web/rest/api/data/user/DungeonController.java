package game.mightywarriors.web.rest.api.data.user;


import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.tables.Dungeon;
import game.mightywarriors.data.tables.Floor;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.security.UsersRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DungeonController {
    @Autowired
    private UsersRetriever retriever;

    @GetMapping("secure/dungeon/floor")
    public Floor getCurrentFloor(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {
        User user = retriever.retrieveUser(authorization);

        return user.getDungeon().getDungeon().getFloors().stream().filter(x -> x.getFloor() == user.getDungeon().getCurrentFloor()).findFirst().get();
    }

    @GetMapping("secure/dungeon")
    public Dungeon getCurrentDungeon(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {
        User user = retriever.retrieveUser(authorization);

        return user.getDungeon().getDungeon();
    }
}
