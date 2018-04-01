package game.mightywarriors.web.rest.mighty.bookmarks.messages;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.services.bookmarks.messages.MessagesManager;
import game.mightywarriors.services.bookmarks.messages.PrivilegesManager;
import game.mightywarriors.services.bookmarks.messages.RoomManager;
import game.mightywarriors.services.bookmarks.messages.RoomsAccessManager;
import game.mightywarriors.web.json.objects.bookmarks.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    @Autowired
    private RoomsAccessManager roomsAccessManager;
    @Autowired
    private RoomManager roomManager;
    @Autowired
    private PrivilegesManager privilegesManager;
    @Autowired
    private MessagesManager messagesManager;

    @PostMapping("secure/chat/messages/add")
    public void addMessage(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody AddMessageInformer informer) throws Exception {

        messagesManager.addMessage(authorization, informer);
    }

    @PostMapping("secure/chat/messages/delete")
    public void deleteMessage(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody DeleteMessageInformer informer) throws Exception {

        messagesManager.deleteMessage(authorization, informer);
    }

    @PostMapping("secure/chat/privileges/add")
    public void addPrivileges(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody PrivilegesInformer informer) throws Exception {

        privilegesManager.addPrivileges(authorization, informer);
    }

    @PostMapping("secure/chat/privileges/delete")
    public void removePrivileges(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody PrivilegesWithOutAdminInformer informer) throws Exception {

        privilegesManager.removePrivileges(authorization, informer);
    }

    @PostMapping("secure/chat/room/user/add")
    public void addUserToRoom(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody PrivilegesWithOutAdminInformer informer) throws Exception {

        roomsAccessManager.addUserToRoom(authorization, informer);
    }

    @PostMapping("secure/chat/room/user/delete")
    public void removeUserFromRoom(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody PrivilegesWithOutAdminInformer informer) throws Exception {

        roomsAccessManager.removeUserFromRoom(authorization, informer);
    }

    @PostMapping("secure/chat/room/create")
    public void createRoom(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {

        roomManager.createRoom(authorization);
    }

    @PostMapping("secure/chat/room/delete")
    public void deleteRoom(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody ChatInformer informer) throws Exception {

        roomManager.deleteRoom(authorization, informer);
    }

    @PostMapping("secure/chat/leave")
    public void removeCurrentUserFromRoom(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody ChatInformer informer) throws Exception {

        roomsAccessManager.leaveChat(authorization, informer);
    }

    @PostMapping("secure/chat/create")
    public long createChat(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody UserMessageInformer informer) throws Exception {

        return roomManager.createChat(authorization, informer);
    }
}
