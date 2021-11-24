import DOA.PostDatabase;
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



    @Test
    void postCrud() throws SQLException {
        post = new Post("test title","test body", "test image");
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

        while(resultSet.next())
        {
            postId = resultSet.getInt("post_id");
        }

        boolean data = postDatabase.deletePost(user.getId(), postId);
        assertTrue(data);
    }


    @Test
    void testPasswordEncryption(){
        passwordOne =  "testing123";
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
    void testPasswordDecryption(){
        passwordOne =  "testing123";
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