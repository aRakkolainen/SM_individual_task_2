import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class UserTest {

    @Test
    public void testRegisterAndLogin() throws IOException {
        User.register("tester", "tester@email.com", "1234");
        Assertions.assertTrue(User.login("tester@email.com", "1234"));
    }
}
