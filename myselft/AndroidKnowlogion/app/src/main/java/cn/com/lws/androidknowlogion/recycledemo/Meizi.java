package cn.com.lws.androidknowlogion.recycledemo;

/**
 * 显示元素的类
 */
public class Meizi {
    private String imageUrl;

    public Meizi(String imageUrl, String title, String name, int favorites, int comments) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.name = name;
        this.favorites = favorites;
        this.comments = comments;
    }

    private String title;

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public int getFavorites() {
        return favorites;
    }

    public int getComments() {
        return comments;
    }

    private String name;
    private int favorites;
    private int comments;
}
