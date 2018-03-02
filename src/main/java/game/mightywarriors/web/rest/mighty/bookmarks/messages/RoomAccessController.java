package game.mightywarriors.web.rest.mighty.bookmarks.messages;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.services.bookmarks.messages.RoomsAccessManager;
import game.mightywarriors.web.json.objects.bookmarks.MessageInformer;
import game.mightywarriors.web.json.objects.bookmarks.PrivilegesWithOutAdminInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomAccessController {
    @Autowired
    private RoomsAccessManager roomsAccessManager;

    @PostMapping("secure/chat/room/user/add")
    public void addUserToRoom(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody PrivilegesWithOutAdminInformer informer) throws Exception {

        roomsAccessManager.addUserToRoom(authorization, informer);
    }

    @PostMapping("secure/chat/room/user/delete")
    public void removeUserFromRoom(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody PrivilegesWithOutAdminInformer informer) throws Exception {

        roomsAccessManager.removeUserFromRoom(authorization, informer);
    }
}
