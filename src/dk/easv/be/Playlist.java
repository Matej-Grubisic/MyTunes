package dk.easv.be;

import javax.annotation.processing.Generated;

public class Playlist {
    private int id=-1;
    private String playlistName;

    public Playlist(String playlistName) {
        this.playlistName = playlistName;
    }

    public Playlist(int id, String playlistName){
        this.id = id;
        this.playlistName = playlistName;
    }

    public int getId() {
        return id;
    }
    public String getPlaylistName(){
        return playlistName;
    }
}

