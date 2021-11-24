package model;

public class Post {
    private int id;
    private String title;
    private String body;
    private String imageName;
    private String name;
    private String email;
    private int noLikes;
    private int noComments;
    private boolean likedPost;

    public Post(String title, String body, String imageName) {
        this.title = title;
        this.body = body;
        this.imageName = imageName;
    }

    public Post(){}

    public Post(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public void setNoLikes(int noLikes) {
        this.noLikes = noLikes;
    }

    public void setNoComments(int noComments) {
        this.noComments = noComments;
    }

    public void setLikedPost(boolean likedPost) {
        this.likedPost = likedPost;
    }

    public int getNoLikes() {
        return noLikes;
    }

    public int getNoComments() {
        return noComments;
    }

    public boolean isLikedPost() {
        return likedPost;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setName(String surname) {
        this.name = surname ;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", imageName='" + imageName + '\'' +
                ", name='" + name + '\'' +
                ", noLikes=" + noLikes +
                ", noComments=" + noComments +
                ", likedPost=" + likedPost +
                '}';
    }
}
