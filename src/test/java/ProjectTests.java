import DOA.PostDatabase;
import DOA.UserDatabase;
import dbConnectionProvider.DbConnection;
import model.Post;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.PasswordHashing;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTests {
    String passwordOne = null;
    String passwordTwo = null;
    Post post = null;
    User user = null;

    @Test
    void userCrud() {

        String password = PasswordHashing.encryptPassword("1234");
        user = new User("King2", "Kong2", "2joe@gmail.com", password);
        UserDatabase userDatabaseTest = new UserDatabase(DbConnection.getConnection());

        boolean success = userDatabaseTest.registerUser(user);
        assertTrue(success);

        User user = loginUser("2joe@gmail.com", "1234");
        assertNotNull(user);

        assertNotNull(user.getFirstName());
        assertNotNull(user.getLastName());
        assertNotNull(user.getEmail());
        assertNotNull(user.getPassword());

        assertEquals(user.getFirstName(), "King2");
        assertEquals(user.getLastName(), "Kong2");
        assertEquals(user.getEmail(), "2joe@gmail.com");
        assertEquals(PasswordHashing.decryptPassword(user.getPassword()), "1234");

        //delete
        boolean result = deleteUser("2joe@gmail.com");
        assertTrue(result);
    }

    User loginUser(String email, String password) {
        UserDatabase userDatabaseTest = new UserDatabase(DbConnection.getConnection());
        User success = userDatabaseTest.loginUser(email, password);
        return success;
    }

    boolean deleteUser(String data) {
        UserDatabase userDatabaseTest = new UserDatabase(DbConnection.getConnection());
        boolean success = userDatabaseTest.deleteUser(data);
        return success;
    }

    @Test
    void postCrud() throws SQLException {
        post = new Post("test title", "test body", "test image");
        PostDatabase postDatabase = new PostDatabase(DbConnection.getConnection());
        User user = new User();
        user.setId(1);

        //create post
        boolean success = postDatabase.createPost(user.getId(), post);
        assertTrue(success);

        //get post
        List<Post> ls = postDatabase.getPosts(user);
        assertNotNull(ls);

        //delete post
        System.out.println(post.getImageName());
        String query = "select post_id from posts where imagename=?";
        PreparedStatement statement = DbConnection.getConnection().prepareStatement(query);
        statement.setString(1, post.getImageName());
        ResultSet resultSet = statement.executeQuery();

        int postId = 0;

        while (resultSet.next()) {
            postId = resultSet.getInt("post_id");
        }

        boolean data = postDatabase.deletePost(user.getId(), postId);
        assertTrue(data);
    }


    @Test
    void testPasswordEncryption() {
        passwordOne = "testing123";
        passwordTwo = "testing456";
        String encrypt = PasswordHashing.encryptPassword(passwordOne);
        String sameEncrypt = PasswordHashing.encryptPassword("testing123");
        String diffEncrypt = PasswordHashing.encryptPassword("testing");
        String pass2Encrypt = PasswordHashing.encryptPassword(passwordTwo);

        assertEquals(sameEncrypt, encrypt);
        assertNotEquals(diffEncrypt, encrypt);
        assertNotEquals(pass2Encrypt, encrypt);
    }

    @Test
    void testPasswordDecryption() {
        passwordOne = "testing123";
        passwordTwo = "testing456";
        String decrypt = PasswordHashing.encryptPassword(passwordTwo);
        String sameEncrypt = PasswordHashing.encryptPassword("testing456");
        String diffEncrypt = PasswordHashing.encryptPassword("testing");
        String pass2Encrypt = PasswordHashing.encryptPassword(passwordOne);

        assertEquals(sameEncrypt, decrypt);
        assertNotEquals(diffEncrypt, decrypt);
        assertNotEquals(pass2Encrypt, decrypt);
    }
}