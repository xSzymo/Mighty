package game.mightywarriors.web.rest.bookmarks.messages;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.tables.Message;
import game.mightywarriors.services.bookmarks.messages.UserDataChatProvider;
import game.mightywarriors.web.json.objects.bookmarks.MessageInformer;
import game.mightywarriors.web.json.objects.messages.ChatRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class DatProviderController {
    @Autowired
    private UserDataChatProvider userDataChatProvider;

    @PostMapping("secure/chat/chat/all")
    public LinkedList<ChatRepresentation> getAllChats(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody MessageInformer informer) throws Exception {

        return userDataChatProvider.getAllChats(authorization);
    }

    @PostMapping("secure/chat/chat/one")
    public ChatRepresentation getChat(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody MessageInformer informer) throws Exception {

        return userDataChatProvider.getChat(authorization, informer);
    }

    @PostMapping("secure/chat/chat/all/messages")
    public List<Message> getAllMessages(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody MessageInformer informer) throws Exception {

        return userDataChatProvider.getAllMessages(authorization, informer);
    }

    @PostMapping("secure/chat/chat/last")
    public List<Message> getLastMessages(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody MessageInformer informer) throws Exception {

        return userDataChatProvider.getLastMessages(authorization, informer);
    }
}
