package game.mightywarriors.configuration.security;


import game.mightywarriors.data.repositories.UserRepository;
import game.mightywarriors.data.repositories.UserRoleRepository;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.data.tables.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository usersService;
    @Autowired
    private UserRoleRepository userRolesService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = usersService.findByLogin(login);
        if (null == user) {
            throw new UsernameNotFoundException("No user present with username: " + login);
        } else {
            Iterable<UserRole> found = userRolesService.findAll();

            UserRole ROLEPLAYING = null;

            for (UserRole x : found)
                for (Iterator<User> iterator = x.getUsers().iterator(); iterator.hasNext(); ) {
                    User a = iterator.next();
                    if (a.getId() == user.getId()) {
                        ROLEPLAYING = x;
                    }
                }

            List<String> userRoles = new ArrayList<>();
            userRoles.add(ROLEPLAYING.getRole());

            return new CustomUserDetails(user, userRoles);
        }
    }
}
