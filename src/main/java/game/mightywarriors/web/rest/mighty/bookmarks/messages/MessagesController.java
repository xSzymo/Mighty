package game.mightywarriors.web.rest.mighty.bookmarks.messages;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.services.bookmarks.messages.MessagesManager;
import game.mightywarriors.web.json.objects.bookmarks.AddMessageInformer;
import game.mightywarriors.web.json.objects.bookmarks.DeleteMessageInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagesController {
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
}
