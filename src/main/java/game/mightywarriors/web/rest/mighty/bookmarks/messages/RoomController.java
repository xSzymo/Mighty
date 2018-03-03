package game.mightywarriors.web.rest.mighty.bookmarks.messages;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.services.bookmarks.messages.RoomManager;
import game.mightywarriors.web.json.objects.bookmarks.ChatInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController {
    @Autowired
    private RoomManager roomManager;

    @PostMapping("secure/chat/room/create")
    public void createRoom(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {

        roomManager.createRoom(authorization);
    }

    @PostMapping("secure/chat/room/delete")
    public void deleteRoom(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody ChatInformer informer) throws Exception {

        roomManager.deleteRoom(authorization, informer);
    }
}
