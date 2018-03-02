package game.mightywarriors.services.bookmarks.messages;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.ChatService;
import game.mightywarriors.data.services.MessageService;
import game.mightywarriors.data.tables.Chat;
import game.mightywarriors.data.tables.Message;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.exceptions.NoAccessException;
import game.mightywarriors.other.exceptions.NotFoundException;
import game.mightywarriors.services.bookmarks.utilities.MessageHelper;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.AddMessageInformer;
import game.mightywarriors.web.json.objects.bookmarks.DeleteMessageInformer;
import game.mightywarriors.web.json.objects.bookmarks.MessageInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagesManager {
    @Autowired
    private UsersRetriever retriever;
    @Autowired
    private ChatService chatService;
    @Autowired
    private MessageService messageService;

    public void addMessage(String authorization, AddMessageInformer informer) throws Exception {
        User user = retriever.retrieveUser(authorization);
        Chat chat = chatService.find(informer.chatId);

        throwExceptionIf_MessageIsNotPresent(informer);
        throwExceptionIf_ChatIsNotPresent(chat);
        throwExceptionIf_UserHaveNotAccessToChat(user, chat);

        chat = addMessageToChat(chat, informer.message, user.getLogin());
        chatService.save(chat);
    }

    public void deleteMessage(String authorization, DeleteMessageInformer informer) throws Exception {
        User user = retriever.retrieveUser(authorization);
        Chat chat = chatService.find(informer.chatId);
        Message message = messageService.find(informer.messageId);

        throwExceptionIf_MessageIsNotPresent(message);
        throwExceptionIf_UserIsNotOwnerOfMessageOrHaveNotRights(user, chat, message);

        message.setMessage(SystemVariablesManager.INFORMATION_OF_DELETED_MESSAGE);
        messageService.save(message);
    }

    private Chat addMessageToChat(Chat chat, String myMessage, String login) {
        Message message = new Message(myMessage, login);
        message.setNumber(chat.getMessages().size());
        chat.addMessage(message);

        return chat;
    }

    private void throwExceptionIf_MessageIsNotPresent(AddMessageInformer informer) throws Exception {
        if (informer.message == null)
            throw new NotFoundException("Message is not present");
    }

    private void throwExceptionIf_MessageIsNotPresent(Message message) throws Exception {
        if (message == null)
            throw new NotFoundException("Message not found");
    }

    private void throwExceptionIf_ChatIsNotPresent(Chat chat) throws Exception {
        if (chat == null)
            throw new NotFoundException("Chat not found");
    }

    private void throwExceptionIf_UserHaveNotAccessToChat(User user, Chat chat) throws Exception {
        if (!user.getChats().contains(chat))
            throw new NoAccessException("User have not access to chat");
    }

    private void throwExceptionIf_UserIsNotOwnerOfMessageOrHaveNotRights(User user, Chat chat, Message message) throws Exception {
        if (!message.getUserLogin().equals(user.getLogin()) && chat.getAdmins().stream().noneMatch(x -> x.getLogin().equals(user.getLogin())))
            throw new NoAccessException("You haven't permission");
    }
}
