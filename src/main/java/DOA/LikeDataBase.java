package DOA;

import dbConnectionProvider.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;


/**
 * Create and Delete operations on the Like table
 */
public class LikeDataBase {

    private final Connection dbConnection;

    public LikeDataBase(Connection connection) {
        this.dbConnection = connection;
    }

    /**
     * Base on the action provided, 0 for delete from or 1 for create in Like table
     * operation base on the required post and the user that liked it
     *
     * @param userId
     * @param postId
     * @param action
     * @return boolean(true for successful creation and deletion and false for failure on create or delete)
     */
    public boolean likePost(int userId, int postId, int action) {
        boolean success = false;
        try {
            String query;
            PreparedStatement preparedStatement;

            if (action == 1) {
                query = "insert into likes(post_id,user_id) " +
                        "values (?,?)";

                preparedStatement = DbConnection.getConnection().prepareStatement(query);
                preparedStatement.setInt(1, postId);
                preparedStatement.setInt(2, userId);

                preparedStatement.executeUpdate();
                success = true;
            } else {
                query = "delete from likes where user_id=" + userId + " and post_id=" + postId;
                preparedStatement = DbConnection.getConnection().prepareStatement(query);
                int result = preparedStatement.executeUpdate();

                if (result > 0) {
                    success = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

}
