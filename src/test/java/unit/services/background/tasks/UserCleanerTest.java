package unit.services.background.tasks;

import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.background.tasks.UserCleaner;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

public class UserCleanerTest {
    private UserCleaner userCleaner;
    private UserService userService;
    private User user;
    private HashSet<String> objects;
    private String code = "abc";
    private Timestamp timestamp;

    @Before
    public void setUp() {
        userService = mock(UserService.class);
        user = mock(User.class);
        userCleaner = new UserCleaner(userService);

        objects = new HashSet<>();
        objects.add(code);
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    @Test
    public void deleteAllUsersWithExpiredTokens_higher_time() {
        timestamp.setTime(timestamp.getTime() - TimeUnit.MINUTES.toMillis(16));
        when(userService.findAllCodesToEnableAccount()).thenReturn(objects);
        when(userService.findByCodeToEnableAccount(code)).thenReturn(user);
        when(user.getId()).thenReturn(1L);
        when(user.getCreatedDate()).thenReturn(timestamp);

        userCleaner.deleteAllUsersWithExpiredTokens();

        verify(userService, times(1)).delete(1L);
    }

    @Test
    public void deleteAllUsersWithExpiredTokens_lower_time() {
        timestamp.setTime(timestamp.getTime() - TimeUnit.MINUTES.toMillis(14));
        when(userService.findAllCodesToEnableAccount()).thenReturn(objects);
        when(userService.findByCodeToEnableAccount(code)).thenReturn(user);
        when(user.getId()).thenReturn(1L);
        when(user.getCreatedDate()).thenReturn(timestamp);

        userCleaner.deleteAllUsersWithExpiredTokens();

        verify(userService, times(0)).delete(1L);
    }
}
