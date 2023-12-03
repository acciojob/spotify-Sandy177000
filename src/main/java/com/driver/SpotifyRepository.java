package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class SpotifyRepository {
    public HashMap<Artist, List<Album>> artistAlbumMap;
    public HashMap<Album, List<Song>> albumSongMap;
    public HashMap<Playlist, List<Song>> playlistSongMap;
    public HashMap<Playlist, List<User>> playlistListenerMap;
    public HashMap<User, Playlist> creatorPlaylistMap;
    public HashMap<User, List<Playlist>> userPlaylistMap;
    public HashMap<Song, List<User>> songLikeMap;
    public List<User> users;
    public List<Song> songs;
    public List<Playlist> playlists;
    public List<Album> albums;
    public List<Artist> artists;

    public SpotifyRepository(){
        //To avoid hitting apis multiple times, initialize all the hashmaps here with some dummy data
        artistAlbumMap = new HashMap<>();
        artistAlbumMap.put(new Artist(),new ArrayList<>());

        albumSongMap = new HashMap<>();
        albumSongMap.put(new Album(), new ArrayList<>());

        playlistSongMap = new HashMap<>();
        playlistSongMap.put(new Playlist(),new ArrayList<>());

        playlistListenerMap = new HashMap<>();
        playlistListenerMap.put(new Playlist(), new ArrayList<>());

        creatorPlaylistMap = new HashMap<>();
        creatorPlaylistMap.put(new User(), new Playlist());

        userPlaylistMap = new HashMap<>();
        userPlaylistMap.put(new User(), new ArrayList<>());

        songLikeMap = new HashMap<>();
        songLikeMap.put(new Song(), new ArrayList<>());

        users = new ArrayList<>();
        songs = new ArrayList<>();
        playlists = new ArrayList<>();
        albums = new ArrayList<>();
        artists = new ArrayList<>();
    }

    public User createUser(String name, String mobile) {

        User newUser =  new User(name, mobile);
        users.add(newUser);
        return newUser;

    }

    public Artist createArtist(String name) {

        Artist newArtist = new Artist(name);
        artists.add(newArtist);
        return newArtist;
    }

    public Album createAlbum(String title, String artistName) {
        //check artist present or not
        boolean present = false;
        for(Artist a:artists){
            if(a.getName().equals(artistName)){
                present = true;
                break;
            }
        }
        if(!present){
            Artist newArtist = new Artist(artistName);
            artists.add(newArtist);
        }

        Album newAlbum  =  new Album(title,artistName);
        albums.add(newAlbum);
        return newAlbum;
    }

    public Song createSong(String title, String albumName, int length) throws Exception{
        boolean present = false;
        for(Album a:albums){
            if(a.getTitle().equals(albumName)){
                present = true;
                break;
            }
        }
        if(!present){
            return null;
        }

        Song newSong = new Song(title,albumName,length);
        songs.add(newSong);

        // add song to the album map
        Album album = findAlbum(albumName);
        List<Song> albumSongs = albumSongMap.getOrDefault(album,new ArrayList<>());
        albumSongs.add(newSong);
        albumSongMap.put(album,albumSongs);


        return newSong;
    }

    public User findUser(String mobile){
        // finds user from users list
        for(User u:users){
            if (u.getMobile().equals(mobile)){
                return u;
            }
        }

        return null;
    }

    public void addListener(User user, Playlist pl){
        // gets the listeners of playlist and adds the given user as listener of the playlist
        List<User> listeners = playlistListenerMap.getOrDefault(pl, new ArrayList<>());
        listeners.add(user);
        playlistListenerMap.put(pl,listeners);
    }

    public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {


        //find user add it as listener
        User u = findUser(mobile);
        if(u==null) return null;

        // make list of all the songs of given length from songs list
        List<Song> SongsOfEqualLength = new ArrayList<>();

        for (Song s:songs){
            if(s.getLength()==length){
                SongsOfEqualLength.add(s);
            }
        }
        // create new playlist
        Playlist newPlayList = new Playlist(mobile,title,SongsOfEqualLength);


        // if user exists
        addListener(u,newPlayList);
        playlists.add(newPlayList);

        return newPlayList;
    }

    public Playlist createPlaylistOnName(String mobile, String title, List<String> songTitles) throws Exception {

        //find user add it as listener
        User u = findUser(mobile);
        if(u==null) return null;


        // make list of all the songs of given names from songs list
        List<Song> SongsOfGivenNames = new ArrayList<>();

        for(String songName:songTitles){
            Song s = findSong(songName);
            SongsOfGivenNames.add(s);
        }

        // create new playlist
        Playlist newPlayList = new Playlist(mobile,title,SongsOfGivenNames);

        // if user exists
        addListener(u,newPlayList);
        playlists.add(newPlayList);

        return newPlayList;

    }

    public Playlist findPlaylist(String mobile, String playlistTitle) throws Exception {

        //find the user
        User u = findUser(mobile);
        if(u==null) return null;

        // find the playlist
        Playlist playlist = null;
        for (Playlist pl:playlists){
            if(pl.getPlaylistTitle().equals(playlistTitle)){
                playlist = pl;
                break;
            }
        }

        if(playlist==null) return null;

        // add user in the listening map
        addListener(u,playlist);

        return playlist;

    }

    public Song findSong(String songtitle){

        for(Song s:songs){
            if(s.getTitle().equals(songtitle)){
                return s;
            }
        }
        return null;
    }

    public Album findAlbum(String albumName){
        for(Album al:albums){
            if (al.getTitle().equals(albumName)){
                return al;
            }
        }

        return null;
    }
    public  Artist findArtist(String artistName){
        for(Artist a:artists){
            if (a.getName().equals(artistName)){
                return a;
            }
        }

        return null;
    }
    public Song likeSong(String mobile, String songTitle) throws Exception {

        User u = findUser(mobile);
        Song s = findSong(songTitle);
        List<User> usersWhoLikeThisSong = songLikeMap.getOrDefault(s,new ArrayList<>());

        // if the user does not already like the song only then add user in the list
        if(!usersWhoLikeThisSong.contains(u))
            usersWhoLikeThisSong.add(u);

        songLikeMap.put(s,usersWhoLikeThisSong);

        // auto like artist of the song
        //  -> find album -> find artist of this song -> increment the like count

        Album al = findAlbum(s.getAlbumName());
        String artistName = al.getArtistName();

        Artist artist = findArtist(artistName);
        artist.setLikes(artist.getLikes()+1);

        return s;
    }

    public String mostPopularArtist() {

        int maxLikes = -1;
        String popularArtist = "";
        for(Artist a:artists){
            if(maxLikes<a.getLikes()){
                popularArtist = a.getName();
                maxLikes = a.getLikes();
            }
        }

        return popularArtist;
    }

    public String mostPopularSong() {
        int maxLikes = -1;
        String popularSong = "";
        for(Song s:songs){
            if(maxLikes<s.getLikes()){
                popularSong = s.getTitle();
                maxLikes = s.getLikes();
            }
        }
        return popularSong;
    }
}
