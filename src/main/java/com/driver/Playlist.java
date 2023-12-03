package com.driver;

import java.util.List;

public class Playlist {
    private String title;

    private String mobile;
    private int length;

    private List<String> songTitles;

    private List<Song> songs;

    private String playlistTitle;

    public Playlist(){

    }

    public Playlist(String title){
        this.title = title;
    }

    public Playlist(String mobile, String title, int length) {
         this.title = title;
         this.length = length;
         this.mobile = mobile;
    }
/*
    public Playlist(String mobile, String title, List<String> songTitles) {
        this.title = title;
        this.mobile = mobile;
        this.songTitles = songTitles;
    }*/

    public Playlist(String mobile, String playlistTitle) {
        this.mobile = mobile;
        this.playlistTitle = playlistTitle;
    }

    public Playlist(String mobile, String title, List<Song> songs) {
        this.mobile = mobile;
        this.title  = title;
        this.songs = songs;
    }


    // getters and setters

    public String getPlaylistTitle() {
        return playlistTitle;
    }

    public void setPlaylistTitle(String playlistTitle) {
        this.playlistTitle = playlistTitle;
    }

    public List<String> getSongTitles() {
        return songTitles;
    }

    public void setSongTitles(List<String> songTitles) {
        this.songTitles = songTitles;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

/*
    public List<String> getSongs() {
        return songs;
    }

    public void setSongs(List<String> songs) {
        this.songs = songs;
    }*/
}
