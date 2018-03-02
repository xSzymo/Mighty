package game.mightywarriors.services.bookmarks.guild;

import game.mightywarriors.data.services.RoleService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Admin;
import game.mightywarriors.data.tables.Chat;
import game.mightywarriors.data.tables.Guild;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.ChatRole;
import game.mightywarriors.other.enums.GuildRole;
import game.mightywarriors.other.exceptions.NoAccessException;
import game.mightywarriors.services.bookmarks.utilities.GuildHelper;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.GuildInformer;
import game.mightywarriors.web.json.objects.bookmarks.GuildMasterInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuildMasterService {
    @Autowired
    private UsersRetriever usersRetriever;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private GuildHelper helper;

    public void addNewGuildMaster(String authorization, GuildMasterInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        User newMaster = helper.retrieveUser(informer);

        throwExceptionIf_guildIsNotPresent(user);
        throwExceptionIf_userIsNotGuildOwner(user);
        throwExceptionIf_userIsNotPresent(newMaster);
        throwExceptionIf_userIsNotPartOfGuild(user.getGuild(), newMaster);

        user.setRole(roleService.find(GuildRole.MEMBER.getRole()));
        newMaster.setRole(roleService.find(GuildRole.OWNER.getRole()));
        Chat chat = newMaster.getGuild().getChat();
        chat.getAdmins().remove(chat.getAdmins().stream().filter(x -> x.getLogin().equals(user.getLogin())).findFirst().get());
        chat.getAdmins().add(new Admin(ChatRole.OWNER, newMaster.getLogin()));

        userService.save(user);
        userService.save(newMaster);
    }

    public void removeMember(String authorization, GuildMasterInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        User memberToRemove = helper.retrieveUser(informer);

        throwExceptionIf_guildIsNotPresent(user);
        throwExceptionIf_userIsNotGuildOwner(user);
        throwExceptionIf_MemberToRemoveIsOwner(memberToRemove);
        throwExceptionIf_userIsNotPresent(memberToRemove);
        throwExceptionIf_userIsNotPartOfGuild(user.getGuild(), memberToRemove);

        memberToRemove.setRole(roleService.find("user"));
        memberToRemove.setGuild(null);
        userService.save(memberToRemove);
        userService.removeChat(memberToRemove.getId(), user.getGuild().getChat().getId());
    }

    private void throwExceptionIf_userIsNotGuildOwner(User user) throws NoAccessException {
        if (!user.getRole().getRole().equals(GuildRole.OWNER.getRole()))
            throw new NoAccessException("user have no access to do that");
    }

    private void throwExceptionIf_MemberToRemoveIsOwner(User user) throws NoAccessException {
        if (user.getRole().getRole().equals(GuildRole.OWNER.getRole()))
            throw new NoAccessException("user have no access to do that");
    }

    private void throwExceptionIf_userIsNotPartOfGuild(Guild guild, User secondUser) throws Exception {
        if (!helper.isUserInGuild(guild, secondUser))
            throw new Exception("User is not member of the guild");
    }

    private void throwExceptionIf_guildIsNotPresent(User user) throws Exception {
        if (user.getGuild() == null)
            throw new Exception("You already have guild");
    }

    private void throwExceptionIf_userIsNotPresent(User user) throws Exception {
        if (user == null)
            throw new Exception("User not found");
    }
}
