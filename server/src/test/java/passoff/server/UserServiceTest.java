package passoff.server;

import dataAccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.*;
import service.UserService;

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
    void resetBeforeTest() {
        userDAO.clear();
        authDAO.clear();
        defaultUser = new UserData("Username", "password", "email");
    }

    @Test
    @DisplayName("Create Valid User")
    void testCreateUserSuccess() throws BadRequestException, DataAccessException {
        AuthData auth = userService.createUser(defaultUser);
        Assertions.assertEquals(authDAO.getAuth(auth.authToken()), auth);
    }

    @Test
    @DisplayName("Create Duplicate User")
    void testCreateUserFailure() throws BadRequestException, DataAccessException {
        userService.createUser(defaultUser);
        Assertions.assertThrows(BadRequestException.class, () -> userService.createUser(defaultUser));
    }

    @Test
    @DisplayName("Login with Correct Credentials")
    void testLoginSuccess() throws Exception {
        userService.createUser(defaultUser);
        AuthData auth = userService.loginUser(defaultUser);
        Assertions.assertEquals(authDAO.getAuth(auth.authToken()), auth);
    }

    @Test
    @DisplayName("Login with Invalid Credentials")
    void testLoginFailure() throws BadRequestException, DataAccessException {
        Assertions.assertThrows(UnauthorizedException.class, () -> userService.loginUser(defaultUser));

        userService.createUser(defaultUser);
        var invalidUser = new UserData(defaultUser.username(), "wrongPass", defaultUser.email());
        Assertions.assertThrows(UnauthorizedException.class, () -> userService.loginUser(invalidUser));
    }

    @Test
    @DisplayName("Logout with Valid Token")
    void testLogoutSuccess() throws Exception {
        AuthData auth = userService.createUser(defaultUser);
        userService.logoutUser(auth.authToken());
        Assertions.assertThrows(DataAccessException.class, () -> authDAO.getAuth(auth.authToken()));
    }

    @Test
    @DisplayName("Logout with Invalid Token")
    void testLogoutFailure() throws BadRequestException, DataAccessException {
        userService.createUser(defaultUser);
        Assertions.assertThrows(UnauthorizedException.class, () -> userService.logoutUser("invalidToken"));
    }

    @Test
    @DisplayName("Clear All Data")
    void testClearSuccess() throws BadRequestException, DataAccessException {
        AuthData auth = userService.createUser(defaultUser);
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
