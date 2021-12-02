package DOA;

import dbConnectionProvider.DbConnection;
import model.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * CRUD operations on the Comment Table
 */
public class CommentDatabase {

    private Connection dbConnection;

    public CommentDatabase(Connection connection) {
        this.dbConnection = connection;
    }

    /**
     * CREATE operation on Comment
     *
     * @param comment
     * @param postId
     * @param userId
     * @return boolean(true for successful creation and false on failure on create)
     */
    public boolean createComment(int userId, int postId, String comment) {
        boolean result = false;
        try {
            String query = "insert into comment(comments,post_id,comment_user_id) " +
                    "values (?,?,?)";

            PreparedStatement preparedStatement = this.dbConnection.prepareStatement(query);
            preparedStatement.setInt(2, postId);
            preparedStatement.setInt(3, userId);
            preparedStatement.setString(1, comment);

            preparedStatement.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * GET operation on Comment
     *
     * @param postId
     * @return List of comments
     */
    public static List<Comment> getComments(int postId) {
        List<Comment> comments = new ArrayList<>();
        try {
            String query = "select u.user_id, p.title, p.imagename, c.comments from comment c"
                    + "  join posts p on c.post_id=p.post_id join user u on u.user_id=c.comment_user_id" +
                    " where p.post_id=" + postId;

            PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setUserId(resultSet.getInt("user_id"));
                comment.setTitle(resultSet.getString("title"));
                comment.setPostImage(resultSet.getString("imagename"));
                comment.setComment(resultSet.getString("comments"));
                comments.add(comment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return comments;
    }


    /**
     * UPDATE operation on Comment
     *
     * @param postId
     * @return boolean(true for successful update and false for failure on update)
     */
    public boolean editComment(int userId, int postId, String comment) {
        boolean status = false;
        try {
            String query = "update comment set comments=? where post_id=? and comment_user_id=?";
            PreparedStatement prepared = this.dbConnection.prepareStatement(query);
            prepared.setString(1, comment);
            prepared.setInt(2, postId);
            prepared.setInt(3, userId);

            int result = prepared.executeUpdate();
            if (result > 0) {
                status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * DELETE operation on Comment
     *
     * @param postId
     * @param userId
     * @return boolean(true for successful deletion and false on failure to delete)
     */
    public boolean deleteComment(int postId, int userId) {
        boolean status = false;

        try {
            String query = "delete from comment where post_id=? and comment_user_id=?";
            PreparedStatement preparedStatement = this.dbConnection.prepareStatement(query);
            preparedStatement.setInt(1, postId);
            preparedStatement.setInt(2, userId);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
}
