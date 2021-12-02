package DOA;

import dbConnectionProvider.DbConnection;
import model.Post;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PostDatabase {

    private Connection dbConnection;

    public PostDatabase(Connection connection) {
        this.dbConnection = connection;
    }

    /**
     * CREATE operation on Post
     *
     * @param userId
     * @param post
     * @return boolean(true for successful creation and false on failure to create)
     */
    public boolean createPost(int userId, Post post) {
        boolean result = false;
        try {
            String query = "insert into posts(user_id,title,body,imagename) " +
                    "values (?,?,?,?)";

            PreparedStatement preparedStatement = this.dbConnection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, post.getTitle());
            preparedStatement.setString(3, post.getBody());
            preparedStatement.setString(4, post.getImageName());

            System.out.println(userId);
            preparedStatement.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * GET by id operation on Post
     *
     * @return post object
     * @params postId
     */
    public Post getPostById(int postId) {
        Post post = null;

        try {
            String query = "select p.post_id, p.title, p.body, p.imagename, u.email from posts p"
                    + "  join user u on p.user_id=u.user_id where p.post_id=?";
            PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, postId);

            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                post = new Post();

                post.setId(result.getInt("post_id"));
                post.setTitle(result.getString("title"));
                post.setBody(result.getString("body"));
                post.setImageName(result.getString("imagename"));
                post.setEmail(result.getString("email"));

                return post;
            }
        } catch (Exception e) {
        }

        return post;
    }


    /**
     * GET operation on Post
     *
     * @param currentUser
     * @return list of posts
     */
    public List<Post> getPosts(User currentUser) {

        List<Post> posts = new ArrayList<>();

        try {
            String query = "select p.post_id, p.title, p.body, p.imagename, u.firstname, u.lastname  from posts p"
                    + "  join user u on p.user_id=u.user_id order by p.post_id DESC";
            PreparedStatement preparedStatement = this.dbConnection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            Post post;

            while (result.next()) {
                post = new Post();
                post.setId(result.getInt("post_id"));
                post.setTitle(result.getString("title"));
                post.setBody(result.getString("body"));
                post.setImageName(result.getString("imagename"));
                post.setName(result.getString("firstname") + " " + result.getString("lastname"));

                //the total number of likes on this particular post
                String que = "select * from likes where post_id=" + post.getId();
                PreparedStatement prepared = this.dbConnection.prepareStatement(que);
                ResultSet res = prepared.executeQuery();
                res.last();
                int noOfLikes = res.getRow();
                post.setNoLikes(noOfLikes);

                //no of comments made on that particular posts
                String que1 = "select * from comment where post_id=" + post.getId();
                PreparedStatement prepared1 = this.dbConnection.prepareStatement(que1);
                ResultSet res1 = prepared1.executeQuery();
                res1.last();
                int noOfComments = res1.getRow();
                post.setNoComments(noOfComments);

                //return true if current user liked this post, else false
                String que2 = "select * from likes where post_id=" + post.getId() + " and user_id=" + currentUser.getId();
                PreparedStatement prepared2 = this.dbConnection.prepareStatement(que2);
                ResultSet res2 = prepared2.executeQuery();
                if (res2.next()) {
                    post.setLikedPost(true);
                }

                //add post to arraylist
                posts.add(post);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return posts;
    }

    /**
     * UPDATE operation on Post
     *
     * @param postId
     * @param newPost
     * @return boolean(true for successful update and false on failure to update)
     */
    public boolean updatePost(int postId, Post newPost) {
        boolean success = false;

        try {
            String query = "update posts set title=?, body=? where post_id=?";
            PreparedStatement prepared = this.dbConnection.prepareStatement(query);
            System.out.println(postId);

            prepared.setString(1, newPost.getTitle());
            prepared.setString(2, newPost.getBody());
            prepared.setInt(3, postId);

            int result = prepared.executeUpdate();

            if (result > 0) {
                success = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * DELETE operation on Post
     *
     * @param postId
     * @param userId
     * @return boolean(true for successful deletion and false on failure to delete)
     */
    public boolean deletePost(int userId, int postId) {
        boolean success = false;
        try {
            String query = "delete from posts where post_id = ? and user_id = ?";
            PreparedStatement prepared = this.dbConnection.prepareStatement(query);
            prepared.setInt(1, postId);
            prepared.setInt(2, userId);
            int result = prepared.executeUpdate();

            if (result > 0) {
                success = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }
}
