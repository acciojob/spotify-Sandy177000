package com.driver;

public class Song {
    private String title;
    private int length;
    private int likes;
    private String albumName;
    private String mobile;

    public Song(){

    }

    public Song(String title, int length){
        this.title = title;
        this.length = length;
    }

    public Song(String title, String albumName, int length) {
        this.title = title;
        this.albumName= albumName;
        this.length = length;
    }

    public Song(String mobile, String songTitle) {
        this.mobile = mobile;
        this.title = songTitle;
    }



    // getters and setters

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
