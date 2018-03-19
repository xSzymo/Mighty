package game.mightywarriors.web.rest.mighty.data.app;


import game.mightywarriors.data.services.DungeonService;
import game.mightywarriors.data.tables.Dungeon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@RestController("dungeonsController")
public class ApiDungeon {
    @Autowired
    private DungeonService dungeonService;

    @GetMapping("dungeons")
    public HashSet<Dungeon> getAllDungeons() {

        return dungeonService.findAll();
    }
}
