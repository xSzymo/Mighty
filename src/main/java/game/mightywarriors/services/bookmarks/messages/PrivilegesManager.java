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
import game.mightywarriors.web.json.objects.bookmarks.PrivilegesInformer;
import game.mightywarriors.web.json.objects.bookmarks.PrivilegesWithOutAdminInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrivilegesManager {
    @Autowired
    private UsersRetriever retriever;
    @Autowired
    private ChatService chatService;
    @Autowired
    private MessageHelper helper;

    public void addPrivileges(String authorization, PrivilegesInformer informer) throws Exception {
        User user = retriever.retrieveUser(authorization);
        Chat chat = chatService.find(informer.chatId);
        User userToAddPrivileges = helper.getUserFromInformer(informer);

        throwExceptionIf_UserIsNotPresent(userToAddPrivileges);
        throwExceptionIf_ChatIsNotPresent(chat);
        throwExceptionIf_UserHaveNotAccessToChat(user, chat);
        throwExceptionIf_UserHaveNotAccessToChat(userToAddPrivileges, chat);
        throwExceptionIf_UserIsNotOwnerOfChat(user, chat);

        chat.getAdmins().add(new Admin((informer.admin ? ChatRole.ADMIN : ChatRole.MODIFIER), userToAddPrivileges.getLogin()));
        chatService.save(chat);
    }

    public void removePrivileges(String authorization, PrivilegesWithOutAdminInformer informer) throws Exception {
        User user = retriever.retrieveUser(authorization);
        Chat chat = chatService.find(informer.chatId);
        User userToRemovePrivileges = helper.getUserFromInformer(informer);

        throwExceptionIf_UserIsNotPresent(userToRemovePrivileges);
        throwExceptionIf_ChatIsNotPresent(chat);
        throwExceptionIf_UserHaveNotAccessToChat(user, chat);
        throwExceptionIf_UserIsNotOwnerOfChat(user, chat);

        Admin userRightsToDelete = getUserRights_OrThrowExceptionIfUserIsNotPresent(chat, userToRemovePrivileges);
        throwExceptionIf_UserIsOwnerOfRoom(userRightsToDelete);

        chat.getAdmins().remove(userRightsToDelete);
        chatService.save(chat);
    }

    private Admin getUserRights_OrThrowExceptionIfUserIsNotPresent(Chat chat, User user) throws Exception {
        Optional<Admin> first = chat.getAdmins().stream().filter(x -> x.getLogin().equals(user.getLogin())).findFirst();

        if (!first.isPresent())
            throw new NotFoundException("User have not privileges to remove");

        return first.get();
    }

    private void throwExceptionIf_ChatIsNotPresent(Chat chat) throws Exception {
        if (chat == null)
            throw new NotFoundException("Chat not found");
    }

    private void throwExceptionIf_UserIsNotPresent(User user) throws Exception {
        if (user == null)
            throw new NotFoundException("User not found");
    }

    private void throwExceptionIf_UserHaveNotAccessToChat(User user, Chat chat) throws Exception {
        if (!user.getChats().contains(chat))
            throw new NoAccessException("User have not access to chat");
    }

    private void throwExceptionIf_UserIsNotOwnerOfChat(User user, Chat chat) throws Exception {
        if (chat.getAdmins().stream().noneMatch(x -> x.getLogin().equals(user.getLogin()) && x.getChatRole().getRole().equals(ChatRole.OWNER.getRole())))
            throw new NoAccessException("You haven't permission");
    }

    private void throwExceptionIf_UserIsOwnerOfRoom(Admin admin) throws Exception {
        if (admin.getChatRole().getRole().equals(ChatRole.OWNER.getRole()))
            throw new Exception("You cant' do that to user");
    }
}
