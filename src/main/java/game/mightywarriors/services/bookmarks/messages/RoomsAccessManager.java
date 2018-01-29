package game.mightywarriors.services.bookmarks.messages;

import game.mightywarriors.data.services.ChatService;
import game.mightywarriors.data.services.MessageService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Admin;
import game.mightywarriors.data.tables.Chat;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.ChatRole;
import game.mightywarriors.other.exceptions.NoAccessException;
import game.mightywarriors.other.exceptions.NotFoundException;
import game.mightywarriors.services.bookmarks.utilities.MessageHelper;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.MessageInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomsAccessManager {
    @Autowired
    private UserService userService;
    @Autowired
    private UsersRetriever retriever;
    @Autowired
    private ChatService chatService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageHelper helper;

    public void addUserToRoom(String authorization, MessageInformer informer) throws Exception {
        User user = retriever.retrieveUser(authorization);
        Chat chat = chatService.find(informer.chatId);
        User invitedUser = helper.getUserFromInformer(informer);

        throwExceptionIf_UserIsNotPresent(invitedUser);
        throwExceptionIf_ChatIsNotPresent(chat);
        throwExceptionIf_UserHaveNotAccessToChat(user, chat);
        throwExceptionIf_UserIsNotOwnerOrAdminOfChat(user, chat);

        invitedUser.getChats().add(chat);
        userService.save(invitedUser);
    }

    public void removeUserFromRoom(String authorization, MessageInformer informer) throws Exception {
        User user = retriever.retrieveUser(authorization);
        Chat chat = chatService.find(informer.chatId);
        User userToRemove = helper.getUserFromInformer(informer);

        throwExceptionIf_UserIsNotPresent(userToRemove);
        throwExceptionIf_ChatIsNotPresent(chat);
        throwExceptionIf_UserHaveNotAccessToChat(user, chat);
        throwExceptionIf_UserHaveNotAccessToChat(userToRemove, chat);
        throwExceptionIf_UserIsNotOwnerOrAdminOfChat(user, chat);
        throwExceptionIf_UserIsOwnerOrAdmin(chat, userToRemove);

        userService.removeChat(userToRemove.getId(), chat.getId());
    }

    public void leaveChat(String authorization, MessageInformer informer) throws Exception {
        User user = retriever.retrieveUser(authorization);

        userService.removeChat(user.getId(), informer.chatId);
    }


    private void throwExceptionIf_UserIsOwnerOrAdmin(Chat chat, User user) throws Exception {
        Optional<Admin> first = chat.getAdmins().stream().filter(x -> x.getLogin().equals(user.getLogin())).findFirst();
        if (first.isPresent()) {
            Admin admin = first.get();
            if (admin.getChatRole().getRole().equals(ChatRole.OWNER.getRole()) || admin.getChatRole().getRole().equals(ChatRole.ADMIN.getRole()))
                throw new Exception("you cant do that to user");
        }
    }

    private void throwExceptionIf_ChatIsNotPresent(Chat chat) throws Exception {
        if (chat == null)
            throw new NotFoundException("Chat not found");
    }

    private void throwExceptionIf_UserIsNotPresent(User user) throws Exception {
        if (user == null)
            throw new NotFoundException("Chat not found");
    }

    private void throwExceptionIf_UserHaveNotAccessToChat(User user, Chat chat) throws Exception {
        if (!user.getChats().contains(chat))
            throw new NoAccessException("User have not access to chat");
    }

    private void throwExceptionIf_UserIsNotOwnerOrAdminOfChat(User user, Chat chat) throws Exception {
        if (chat.getAdmins().stream().noneMatch(x -> x.getLogin().equals(user.getLogin()) && (x.getChatRole().getRole().equals(ChatRole.ADMIN.getRole()) || x.getChatRole().getRole().equals(ChatRole.OWNER.getRole()))))
            throw new NoAccessException("You haven't permission");
    }
}
