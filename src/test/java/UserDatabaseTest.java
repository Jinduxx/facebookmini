
import DOA.UserDatabase;
import dbConnectionProvider.DbConnection;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.PasswordHashing;

import static org.junit.jupiter.api.Assertions.*;

class UserDatabaseTest {

    User user = null;

    @BeforeEach
    void setup(){
        String password = PasswordHashing.encryptPassword("password");
        user = new User("firstname","lastname","email",password);
    }

    @Test
    void userCrud(){
        UserDatabase userDatabaseTest = new UserDatabase(DbConnection.getConnection());

        boolean success = userDatabaseTest.registerUser(user);
        assertTrue(success);

        User user = loginUser("email", "password");
        System.out.println(user);
        assertNotNull(user);

        assertNotNull(user.getFirstName());
        assertNotNull(user.getLastName());
        assertNotNull(user.getEmail());
        assertNotNull(user.getPassword());


//        assertEquals(user.getUserName(), "username");
//        assertEquals(user.getFirstName(), "firstname");
//        assertEquals(user.getLastName(), "lastname");
//        assertEquals(user.getEmail(), "email");
//        assertEquals(PasswordHashing.decryptPassword(user.getPassword()), "password");

        //delete
//        boolean result = deleteUser("email");
//        assertTrue(result);
    }

    User loginUser(String email, String password){
        UserDatabase userDatabaseTest = new UserDatabase(DbConnection.getConnection());
        User success = userDatabaseTest.loginUser(email, password);
        return success;
    }

//    boolean deleteUser(String data){
//        UserDatabase userDatabaseTest = new UserDatabase(DbConnection.getConnection());
//        boolean success = userDatabaseTest.deleteUser(data);
//        return success;
//    }
}