package game.mightywarriors.services.bookmarks.messages;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.tables.Chat;
import game.mightywarriors.data.tables.Message;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.exceptions.NotFoundException;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.ChatInformer;
import game.mightywarriors.web.json.objects.messages.ChatRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDataChatProvider {
    private UsersRetriever retriever;

    @Autowired
    public UserDataChatProvider(UsersRetriever usersRetriever) {
        this.retriever = usersRetriever;
    }

    public LinkedList<ChatRepresentation> getAllChats(String authorization) throws Exception {
        User user = retriever.retrieveUser(authorization);

        LinkedList<ChatRepresentation> chats = new LinkedList<>();
        user.getChats().forEach(chat -> chats.add(new ChatRepresentation(chat)));

        return chats;
    }

    public ChatRepresentation getChat(String authorization, ChatInformer informer) throws Exception {
        User user = retriever.retrieveUser(authorization);

        Chat chat = getUserChat_IfChatIsNotPresentThrowException(user, informer.chatId);

        return new ChatRepresentation(chat);
    }

    public List<Message> getAllMessages(String authorization, ChatInformer informer) throws Exception {
        User user = retriever.retrieveUser(authorization);

        Chat userChat = getUserChat_IfChatIsNotPresentThrowException(user, informer.chatId);
        ChatRepresentation chat = new ChatRepresentation(userChat);

        return chat.messages;
    }

    public List<Message> getLastMessages(String authorization, ChatInformer informer) throws Exception {
        User user = retriever.retrieveUser(authorization);

        Chat userChat = getUserChat_IfChatIsNotPresentThrowException(user, informer.chatId);

        return getLastMessagesFromChat(userChat);
    }

    private List<Message> getLastMessagesFromChat(Chat userChat) {
        ChatRepresentation chat = new ChatRepresentation(userChat);
        List<Message> messages = new LinkedList<>();
        for (int i = chat.messages.size() - SystemVariablesManager.LAST_MESSAGES; i < chat.messages.size(); i++)
            messages.add(chat.messages.get(i));

        return messages;
    }

    private Chat getUserChat_IfChatIsNotPresentThrowException(User user, long chatId) throws NotFoundException {
        Optional<Chat> first = user.getChats().stream().filter(x -> x.getId().equals(chatId)).findFirst();
        if (!first.isPresent())
            throw new NotFoundException("Chat not found");

        return first.get();
    }
}
