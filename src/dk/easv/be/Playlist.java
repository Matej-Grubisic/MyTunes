package dk.easv.be;

import javax.annotation.processing.Generated;
import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private int id=-1;
    private String playlistName;

    private List<Song> songList;

    public Playlist(int id, String playlistName){
        this.id = id;
        this.playlistName = playlistName;
    }

    public void setSongList(List<Song> songListIni){
        this.songList = songListIni;
    }

    public List<Song> getSongList(){
        return this.songList;
    }


    public int getId() {
        return id;
    }


    public String getPlaylistName(){
        return playlistName;
    }
}

