package game.mightywarriors.web.rest.mighty.bookmarks.kingdom;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.services.bookmarks.kingdom.KingdomAssigner;
import game.mightywarriors.web.json.objects.bookmarks.KingdomInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KingdomController {
    @Autowired
    private KingdomAssigner kingdomAssigner;

    @PostMapping("kingdom")
    public void removeCurrentUserFromRoom(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody KingdomInformer informer) throws Exception {

        kingdomAssigner.setKingdom(authorization, informer);
    }
}
