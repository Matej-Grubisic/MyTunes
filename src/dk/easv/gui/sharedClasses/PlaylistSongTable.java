package dk.easv.gui.sharedClasses;

import javafx.scene.control.ListView;

// THIS IS A SINGLETON

public class PlaylistSongTable {
    // Single instance of playlistTable
    private static final PlaylistSongTable instance = new PlaylistSongTable();

    // The shared the table
    private ListView listView;


    public void setTable(ListView playlistSongList){
        listView = playlistSongList;
    }

    public ListView getPlaylistSongTable(){
        return listView;
    }

    public static PlaylistSongTable getInstance() {
        return instance;
    }
}
