package game.mightywarriors.web.rest.api.data.user;


import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.MissionFightService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.MissionFight;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.security.UsersRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MissionFightController {
    @Autowired
    private MissionFightService service;
    @Autowired
    private UsersRetriever retriever;

    @GetMapping("secure/missionFights")
    public List<MissionFight> getMissionFights(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {
        User user = retriever.retrieveUser(authorization);

        return service.findAll().stream().filter(x -> {
            for (Champion champion : x.getChampions())
                for (Champion champ : user.getChampions())
                    if (champion.getId().equals(champ.getId()))
                        return true;
            return false;
        }).collect(Collectors.toList());
    }
}
