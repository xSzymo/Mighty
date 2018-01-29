package game.mightywarriors.web.rest.bookmarks.messages;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.services.bookmarks.messages.MessagesManager;
import game.mightywarriors.services.bookmarks.messages.RoomManager;
import game.mightywarriors.web.json.objects.bookmarks.MessageInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagesController {
    @Autowired
    private MessagesManager messagesManager;
    @Autowired
    private RoomManager roomManager;

    @PostMapping("secure/chat/messages/add")
    public void addMessage(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody MessageInformer informer) throws Exception {

        messagesManager.addMessage(authorization, informer);
    }

    @PostMapping("secure/chat/messages/delete")
    public void deleteMessage(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody MessageInformer informer) throws Exception {

        messagesManager.deleteMessage(authorization, informer);
    }

    @PostMapping("secure/chat/create")
    public long createChat(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody MessageInformer informer) throws Exception {

        return roomManager.createChat(authorization, informer);
    }

    @PostMapping("secure/chat/room/create")
    public void createRoom(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {

        roomManager.createRoom(authorization);
    }

    @PostMapping("secure/chat/room/delete")
    public void deleteRoom(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody MessageInformer informer) throws Exception {

        roomManager.deleteRoom(authorization, informer);
    }
}
