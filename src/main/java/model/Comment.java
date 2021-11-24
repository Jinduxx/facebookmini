package model;

public class Comment {
    private String title;
    private String postImage;
    private int userId;
    private String comment;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }



    public Comment() {
    }

    public Comment(String title, String postImage, String comment) {
        this.title = title;
        this.postImage = postImage;
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "title='" + title + '\'' +
                ", postImage='" + postImage + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
