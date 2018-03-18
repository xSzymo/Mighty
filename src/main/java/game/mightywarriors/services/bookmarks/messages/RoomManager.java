package game.mightywarriors.services.bookmarks.messages;

import game.mightywarriors.data.services.ChatService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Admin;
import game.mightywarriors.data.tables.Chat;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.ChatRole;
import game.mightywarriors.other.exceptions.NoAccessException;
import game.mightywarriors.other.exceptions.NotFoundException;
import game.mightywarriors.services.bookmarks.utilities.MessageHelper;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.ChatInformer;
import game.mightywarriors.web.json.objects.bookmarks.UserMessageInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomManager {
    private UserService userService;
    private UsersRetriever retriever;
    private ChatService chatService;
    private MessageHelper helper;

    @Autowired
    public RoomManager(UserService userService, UsersRetriever usersRetriever, ChatService chatService, MessageHelper messageHelper) {
        this.userService = userService;
        this.retriever = usersRetriever;
        this.chatService = chatService;
        this.helper = messageHelper;
    }

    public long createChat(String authorization, UserMessageInformer informer) throws Exception {
        User user = retriever.retrieveUser(authorization);
        User friend = helper.getUserFromInformer(informer);

        throwExceptionIf_UserIsNotPresent(friend);

        Chat chat = createNewChatForUsers(user, friend);
        return chat.getId();
    }

    public long createRoom(String authorization) throws Exception {
        User user = retriever.retrieveUser(authorization);

        Chat chat = createNewUsersRoom(user);
        return chat.getId();
    }

    public void deleteRoom(String authorization, ChatInformer informer) throws Exception {
        User user = retriever.retrieveUser(authorization);
        Chat chat = chatService.find(informer.chatId);

        throwExceptionIf_UserIsNotPresent(user);
        throwExceptionIf_ChatIsNotPresent(chat);
        throwExceptionIf_UserHaveNotAccessToChat(user, chat);
        throwExceptionIf_UserIsNotOwnerOfChat(user, chat);

        chatService.delete(chat.getId());
    }

    private Chat createNewChatForUsers(User user, User friend) {
        Chat chat = new Chat();
        chatService.save(chat);
        user.getChats().add(chat);
        userService.save(user);
        friend.getChats().add(chat);
        userService.save(friend);

        return chat;
    }

    private Chat createNewUsersRoom(User user) {
        Chat chat = new Chat();
        chat.getAdmins().add(new Admin(ChatRole.OWNER, user.getLogin()));
        chatService.save(chat);
        user.getChats().add(chat);
        userService.save(user);

        return chat;
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
}
