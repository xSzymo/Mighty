package game.mightywarriors.web.rest.api;


import game.mightywarriors.data.services.MissionService;
import game.mightywarriors.data.tables.Mission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
public class MissionsApiController {
    @Autowired
    MissionService missionService;

    @GetMapping("missions")
    public LinkedList<Mission> getChampions() {
        return missionService.findAll();
    }

    @GetMapping("missions/{id}")
    public Mission getChampion(@PathVariable("id") String id) {
        return missionService.findOne(Long.parseLong(id));
    }
}
