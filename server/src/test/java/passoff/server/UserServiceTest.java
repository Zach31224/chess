package passoff.server;

import dataAccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.*;
import service.UserService;
import service.ServiceException;

public class UserServiceTest {

    private static UserService userService;
    private static UserDAO userDAO;
    private static AuthDAO authDAO;

    private static UserData defaultUser;

    @BeforeAll
    static void setUpClass() {
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        userService = new UserService(userDAO, authDAO);
    }

    @BeforeEach
    void resetBeforeTest() throws DataAccessException {
        userDAO.clear();
        authDAO.clear();
        defaultUser = new UserData("Username", "password", "email");
    }

    @Test
    @DisplayName("Create Valid User")
    void testCreateUserSuccess() throws Exception {
        AuthData auth = userService.register(defaultUser);
        Assertions.assertEquals(authDAO.getAuth(auth.authToken()), auth);
    }

    @Test
    @DisplayName("Create Duplicate User")
    void testCreateUserFailure() throws Exception {
        userService.register(defaultUser);
        Assertions.assertThrows(ServiceException.class, () -> userService.register(defaultUser));
    }

    @Test
    @DisplayName("Login with Correct Credentials")
    void testLoginSuccess() throws Exception {
        userService.register(defaultUser);
        AuthData auth = userService.login(defaultUser.username(), defaultUser.password());
        Assertions.assertEquals(authDAO.getAuth(auth.authToken()), auth);
    }

    @Test
    @DisplayName("Login with Invalid Credentials")
    void testLoginFailure() throws Exception {
        Assertions.assertThrows(ServiceException.class, () -> userService.login(defaultUser.username(), "wrongPass"));

        userService.register(defaultUser);
        Assertions.assertThrows(ServiceException.class, () -> userService.login(defaultUser.username(), "wrongPass"));
    }

    @Test
    @DisplayName("Logout with Valid Token")
    void testLogoutSuccess() throws Exception {
        AuthData auth = userService.register(defaultUser);
        userService.logout(auth.authToken());
        Assertions.assertThrows(DataAccessException.class, () -> authDAO.getAuth(auth.authToken()));
    }

    @Test
    @DisplayName("Logout with Invalid Token")
    void testLogoutFailure() throws Exception {
        userService.register(defaultUser);
        Assertions.assertThrows(ServiceException.class, () -> userService.logout("invalidToken"));
    }

    @Test
    @DisplayName("Clear All Data")
    void testClearSuccess() throws Exception {
        AuthData auth = userService.register(defaultUser);
        userService.clear();

        Assertions.assertThrows(DataAccessException.class, () -> userDAO.getUser(defaultUser.username()));
        Assertions.assertThrows(DataAccessException.class, () -> authDAO.getAuth(auth.authToken()));
    }

    @Test
    @DisplayName("Clear Without Existing Data")
    void testClearNoError() {
        Assertions.assertDoesNotThrow(() -> userService.clear());
    }
}